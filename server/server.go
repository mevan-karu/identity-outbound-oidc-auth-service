package main

import (
	"context"
	"encoding/base64"
	"encoding/json"
	"net/url"
	"os"
	"path/filepath"

	"flag"
	"fmt"
	"log"
	"net"
	"regexp"
	"strconv"
	"strings"

	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials"
	"google.golang.org/grpc/peer"

	"golang.org/x/exp/slices"
	"golang.org/x/oauth2"

	"wso2-enterprise/identity-outbound-oidc-auth-service/data"
	pb "wso2-enterprise/identity-outbound-oidc-auth-service/outboundserver"
)

var (
	app_prop_file       = flag.String("app_prop_file", "", "The application properties file")
	NON_USER_ATTRIBUTES = []string{"at_hash", "iss", "iat", "exp", "aud", "azp"}
)

// basepath is the root directory of this package.
var basepath string

const (
	// Application property names.
	PORT                 = "server.port"
	SERVER_CERT_PATH     = "server.crt.path"
	SERVER_KEY_PATH      = "server.key.path"
	SERVER_CA_CERTS_PATH = "server.ca.crts.path"
	CLIENT_AUTH_ENABLED  = "server.client.auth.enabled"

	// OIDC Authenticator constants.
	CLIENT_ID                        = "ClientId"
	CLIENT_SECRET                    = "ClientSecret"
	OAUTH2_AUTHZ_URL                 = "OAuth2AuthzEPUrl"
	OAUTH2_TOKEN_EP_URL              = "OAuth2TokenEPUrl"
	CALLBACK_URL                     = "callbackUrl"
	LOGIN_TYPE                       = "OIDC"
	OAUTH2_PARAM_STATE               = "state"
	COMMON_AUTH_QUERY_PARAMS         = "commonAuthQueryParams"
	OAUTH2_PARAM_SCOPE               = "scope"
	OAUTH2_PARAM_CODE                = "code"
	AUTH_PARAM                       = "$authparam"
	DYNAMIC_AUTH_PARAMS_LOOKUP_REGEX = "\\$authparam\\{(\\w+)\\}"
	IS_BASIC_AUTH_ENABLED            = "IsBasicAuthEnabled"
)

type OutboundOIDCService struct {
	pb.UnimplementedOutboundOIDCServiceServer
}

// Implementation of canHandle method of the authenticator.
// This returns true if authenticator can handle the authentication request.
func (s *OutboundOIDCService) CanHandle(ctx context.Context, canHandleReq *pb.Request) (*pb.CanHandleResponse, error) {

	requestParams := getRequestParamsMap(canHandleReq.GetRequestParams())

	if LOGIN_TYPE == getLoginType(requestParams[OAUTH2_PARAM_STATE][0]) {
		return &pb.CanHandleResponse{CanHandle: true}, nil
	}
	return &pb.CanHandleResponse{CanHandle: false}, nil
}

// Implementation of InitiateAuthentication function of the authenticator.
// This function returns OAuth2 authorize endpoint with required params to initiate federated login.
func (s *OutboundOIDCService) InitiateAuthentication(ctx context.Context, initAuthReq *pb.InitAuthRequest) (*pb.InitAuthResponse, error) {

	var redirectUrl string
	isRedirect := false
	authenticatorProperties := initAuthReq.GetAuthenticationContext().GetAuthenticatorProperties()
	if len(authenticatorProperties) > 0 {
		clientId := authenticatorProperties[CLIENT_ID]
		authEndpoint := authenticatorProperties[OAUTH2_AUTHZ_URL]
		callbackURL := authenticatorProperties[CALLBACK_URL]
		state := authenticatorProperties[OAUTH2_PARAM_STATE]
		authParamQueryString := authenticatorProperties[COMMON_AUTH_QUERY_PARAMS]
		requestParams := getRequestParamsMap(initAuthReq.GetRequest().GetRequestParams())
		queryString := interpretQueryString(initAuthReq.GetAuthenticationContext(), authParamQueryString, requestParams)
		paramValueMap := make(map[string]string)
		if queryString != "" {
			params := strings.Split(queryString, "&")
			for _, param := range params {
				paramNameValueArray := strings.Split(param, "=")
				if len(paramNameValueArray) == 2 {
					paramValueMap[paramNameValueArray[0]] = paramNameValueArray[1]
				}
			}
		}
		scope := paramValueMap[OAUTH2_PARAM_SCOPE]
		scopes := strings.Split(scope, " ")
		oauthConfig := &oauth2.Config{
			ClientID:    clientId,
			RedirectURL: callbackURL,
			Scopes:      scopes,
			Endpoint: oauth2.Endpoint{
				AuthURL: authEndpoint,
			},
		}
		redirectUrl = oauthConfig.AuthCodeURL(state)
		isRedirect = true
	}
	return &pb.InitAuthResponse{IsRedirect: isRedirect, RedirectUrl: redirectUrl}, nil
}

// Implementation of ProcessAuthenticationResponse function of the authenticator.
// This function returns authenticated user data + additional authentication data.
func (s *OutboundOIDCService) ProcessAuthenticationResponse(ctx context.Context, processAuthRequest *pb.ProcessAuthRequest) (*pb.ProcessAuthResponse, error) {

	requestQueryParamMap := getQueryParamMap(processAuthRequest.GetRequest().GetQueryString())
	code, err := url.PathUnescape(requestQueryParamMap[OAUTH2_PARAM_CODE])
	if err != nil {
		err = fmt.Errorf("Error while decoding auth code. %s", err)
		return handleError(err), nil
	}
	tokenResp, err := getTokenResponse(processAuthRequest.GetAuthenticationContext().GetAuthenticatorProperties(), code)
	if err != nil {
		err = fmt.Errorf("Error occurred while retrieving token response. %s", err)
		return handleError(err), nil
	}
	authenticationData := make(map[string]string)
	authenticationData["access_token"] = tokenResp.AccessToken
	idToken := tokenResp.Extra("id_token").(string)
	authenticationData["id_token"] = idToken
	userAttributes, err := getUserAttributesFromIDToken(idToken)
	if err != nil {
		return handleError(err), nil
	}
	return &pb.ProcessAuthResponse{
		AuthenticationStatus: pb.AuthenticatorFlowStatus_SUCCESS_COMPLETED,
		AuthenticatedUser:    &pb.AuthenticatedUser{IsFederatedUser: true, UserAttributes: userAttributes},
		AuthenticationData:   authenticationData}, nil
}

func handleError(err error) *pb.ProcessAuthResponse {

	return &pb.ProcessAuthResponse{
		AuthenticationStatus: pb.AuthenticatorFlowStatus_FAIL_COMPLETED,
	}
}

func getUserAttributesFromIDToken(idToken string) (map[string]string, error) {

	userAttributes := make(map[string]string)
	tokenBody := strings.Split(idToken, ".")[1]
	decodedTokenBody, err := base64.RawURLEncoding.DecodeString(tokenBody)
	if err != nil {
		err = fmt.Errorf("Error decoding token body. %s", err)
		return nil, err
	}
	var decodedTokenData map[string]interface{}
	err = json.Unmarshal(decodedTokenBody, &decodedTokenData)
	if err != nil {
		err = fmt.Errorf("Error decoding id token. %s", err)
		return nil, err
	}
	for key, val := range decodedTokenData {
		if !slices.Contains(NON_USER_ATTRIBUTES, key) {
			userAttributes[key] = fmt.Sprintf("%v", val)
		}
	}
	return userAttributes, nil
}

func getTokenResponse(authenticatorProps map[string]string, authCode string) (*oauth2.Token, error) {

	clientId := authenticatorProps[CLIENT_ID]
	clientSecret := authenticatorProps[CLIENT_SECRET]
	callbackURL := authenticatorProps[CALLBACK_URL]
	tokenEndpoint := authenticatorProps[OAUTH2_TOKEN_EP_URL]
	isHTTPBasicAuth, _ := strconv.ParseBool(authenticatorProps[IS_BASIC_AUTH_ENABLED])
	authStyle := oauth2.AuthStyleInParams
	if isHTTPBasicAuth {
		authStyle = oauth2.AuthStyleInHeader
	}
	oauthConfig := &oauth2.Config{
		ClientID:     clientId,
		ClientSecret: clientSecret,
		Endpoint: oauth2.Endpoint{
			TokenURL:  tokenEndpoint,
			AuthStyle: authStyle,
		},
		RedirectURL: callbackURL,
	}
	token, err := oauthConfig.Exchange(context.Background(), authCode)
	if err != nil {
		return nil, err
	}
	return token, nil
}

func getQueryParamMap(queryString string) map[string]string {

	queryParamMap := make(map[string]string)
	if queryString != "" {
		paramValuePairs := strings.Split(queryString, "&")
		for _, paramValuePairEl := range paramValuePairs {
			paramValuePair := strings.Split(paramValuePairEl, "=")
			queryParamMap[paramValuePair[0]] = paramValuePair[1]
		}
	}
	return queryParamMap
}

func interpretQueryString(authContext *pb.AuthenticationContext, authParamQueryString string, requestParams map[string][]string) string {

	if authParamQueryString == "" || !strings.Contains(authParamQueryString, AUTH_PARAM) {
		return authParamQueryString
	}
	re, _ := regexp.Compile(DYNAMIC_AUTH_PARAMS_LOOKUP_REGEX)
	authParamsNameMap := re.FindAllStringSubmatch(authParamQueryString, -1)
	authParamQueryString = getQueryStringWithAuthParams(authContext, authParamQueryString, authParamsNameMap)
	for _, authParamsList := range authParamsNameMap {
		if requestParams[authParamsList[1]] != nil && requestParams[authParamsList[1]][0] != "" {
			authParamQueryString = strings.ReplaceAll(authParamQueryString, authParamsList[0], requestParams[authParamsList[1]][0])
		}
	}
	return authParamQueryString
}

// ToDo: Need to implement this functionality.
// Populate query string with values for $authParam{param_name}.
func getQueryStringWithAuthParams(authContext *pb.AuthenticationContext, queryString string, authParamsNameMap [][]string) string {

	authenticatorParams := authContext.GetAuthenticatorParams()
	if queryString == "" || len(authenticatorParams) == 0 {
		return queryString
	}
	for _, authParamsList := range authParamsNameMap {
		if authenticatorParams[authParamsList[1]] != "" {
			queryString = strings.ReplaceAll(queryString, authParamsList[0], authenticatorParams[authParamsList[1]])
		}
	}
	return queryString
}

func getLoginType(stateParam string) string {

	loginType := ""
	if len(stateParam) != 0 {
		loginType = strings.Split(stateParam, ",")[1]
	}
	return loginType
}

func getRequestParamsMap(requestParams []*pb.Request_RequestParam) map[string][]string {

	requestParamsMap := make(map[string][]string)
	if len(requestParams) != 0 {
		for _, requestParam := range requestParams {
			for _, paramValue := range requestParam.GetParamValue() {
				requestParamsMap[requestParam.GetParamName()] = append(requestParamsMap[requestParam.GetParamName()], paramValue)
			}
		}
	}
	return requestParamsMap
}

func logClientInfo(ctx context.Context, req interface{}, info *grpc.UnaryServerInfo, handler grpc.UnaryHandler) (resp interface{}, err error) {
	// Log client tls info
	if p, ok := peer.FromContext(ctx); ok {
		if mtls, ok := p.AuthInfo.(credentials.TLSInfo); ok {
			for _, item := range mtls.State.PeerCertificates {
				log.Println("Request certificate subject : ", item.Subject)
			}
		}
	}
	return handler(ctx, req)
}

func main() {

	flag.Parse()
	ex, _ := os.Executable()
	basepath = filepath.Dir(ex)
	if *app_prop_file == "" {
		*app_prop_file = data.Path("application.properties", basepath)
	}
	serverProperties, err := data.ReadApplicationPropertiesFile(*app_prop_file)
	port, err := strconv.Atoi(serverProperties[PORT])
	serverCrtPath := data.Path(serverProperties[SERVER_CERT_PATH], basepath)
	serverKeyPath := data.Path(serverProperties[SERVER_KEY_PATH], basepath)
	clientCrtsPath := data.Path(serverProperties[SERVER_CA_CERTS_PATH], basepath)
	log.Printf("Client authentication is set to : %s", serverProperties[CLIENT_AUTH_ENABLED])
	isClientAuthEnabled, err := strconv.ParseBool(serverProperties[CLIENT_AUTH_ENABLED])
	credentials := data.GetServerTLSConfig(isClientAuthEnabled, serverCrtPath, serverKeyPath, clientCrtsPath)
	opts := []grpc.ServerOption{grpc.Creds(credentials)}
	if isClientAuthEnabled {
		opts = []grpc.ServerOption{
			grpc.Creds(credentials),
			grpc.UnaryInterceptor(logClientInfo),
		}
	}
	lis, err := net.Listen("tcp", fmt.Sprintf("localhost:%d", port))
	if err != nil {
		log.Fatalf("Failed to listen : %v", err)
	}
	grpcServer := grpc.NewServer(opts...)
	pb.RegisterOutboundOIDCServiceServer(grpcServer, &OutboundOIDCService{})
	log.Println("GRPC server started!")
	grpcServer.Serve(lis)
}
