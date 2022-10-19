/**
 * Copyright (c) 2022, WSO2 LLC. (https://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */


package com.wso2.identity.outbound.oidc.auth.service.authenticator;

import com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser;
import com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext;
import com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatorFlowStatus;
import com.wso2.identity.outbound.oidc.auth.service.rpc.InitAuthRequest;
import com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest;
import com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthResponse;
import com.wso2.identity.outbound.oidc.auth.service.rpc.Request;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthClientResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.utils.JSONUtils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * OIDC Authenticator.
 */
public class OIDCAuthenticator {

    private static final Log log = LogFactory.getLog(OIDCAuthenticator.class);
    private static final String DYNAMIC_PARAMETER_LOOKUP_REGEX = "\\$\\{(\\w+)\\}";
    private static Pattern pattern = Pattern.compile(DYNAMIC_PARAMETER_LOOKUP_REGEX);
    private static final String[] NON_USER_ATTRIBUTES = new String[]{"at_hash", "iss", "iat", "exp", "aud", "azp"};

    public static boolean canHandle(Request request) {

        Map<String, List<String>> requestParams = getRequestParams(request);

        if (OIDCAuthenticatorConstants.LOGIN_TYPE.equals(getLoginType(requestParams))) {
            return true;
        }
        return false;
    }

    public static String initiateAuthRequest(InitAuthRequest initAuthRequest) {

        String redirectURL = null;
        try {
            Map<String, String> authenticatorProperties = initAuthRequest.getAuthenticationContext()
                    .getAuthenticatorPropertiesMap();
            if (MapUtils.isNotEmpty(authenticatorProperties)) {
                String clientId = authenticatorProperties.get(OIDCAuthenticatorConstants.CLIENT_ID);
                String authorizationEP = authenticatorProperties.get(OIDCAuthenticatorConstants.OAUTH2_AUTHZ_URL);
                String callbackUrl = authenticatorProperties.get(OIDCAuthenticatorConstants.IdPConfParams.CALLBACK_URL);
                String state = initAuthRequest.getAuthenticationContext().getContextIdentifier() + ","
                        + OIDCAuthenticatorConstants.LOGIN_TYPE;
                String queryString = authenticatorProperties.get(OIDCAuthenticatorConstants.QUERY_PARAMS);
                Map<String, List<String>> requestParams = getRequestParams(initAuthRequest.getRequest());
                queryString = interpretQueryString(initAuthRequest.getAuthenticationContext(), queryString,
                        requestParams);

                Map<String, String> paramValueMap = new HashMap<>();

                if (StringUtils.isNotBlank(queryString)) {
                    String[] params = queryString.split("&");
                    for (String param : params) {
                        String[] intParam = param.split("=");
                        if (intParam.length >= 2) {
                            paramValueMap.put(intParam[0], intParam[1]);
                        }
                    }
                }
                paramValueMap.put("scope", "openid");

                String scope = paramValueMap.get(OIDCAuthenticatorConstants.OAuth20Params.SCOPE);

                OAuthClientRequest authzRequest;
                if (StringUtils.isNotBlank(queryString) && queryString.toLowerCase().contains("scope=") && queryString
                        .toLowerCase().contains("redirect_uri=")) {
                    authzRequest = OAuthClientRequest.authorizationLocation(authorizationEP).setClientId(clientId)
                            .setResponseType(OIDCAuthenticatorConstants.OAUTH2_GRANT_TYPE_CODE).setState(state)
                            .buildQueryMessage();
                } else if (StringUtils.isNotBlank(queryString) && queryString.toLowerCase().contains("scope=")) {
                    authzRequest = OAuthClientRequest.authorizationLocation(authorizationEP).setClientId(clientId)
                            .setRedirectURI(callbackUrl)
                            .setResponseType(OIDCAuthenticatorConstants.OAUTH2_GRANT_TYPE_CODE).setState(state)
                            .buildQueryMessage();
                } else if (StringUtils.isNotBlank(queryString) && queryString.toLowerCase().contains("redirect_uri=")) {
                    authzRequest = OAuthClientRequest.authorizationLocation(authorizationEP).setClientId(clientId)
                            .setResponseType(OIDCAuthenticatorConstants.OAUTH2_GRANT_TYPE_CODE)
                            .setScope(OIDCAuthenticatorConstants.OAUTH_OIDC_SCOPE).setState(state).buildQueryMessage();

                } else {
                    authzRequest = OAuthClientRequest.authorizationLocation(authorizationEP).setClientId(clientId)
                            .setRedirectURI(callbackUrl)
                            .setResponseType(OIDCAuthenticatorConstants.OAUTH2_GRANT_TYPE_CODE).setScope(scope)
                            .setState(state).buildQueryMessage();
                }

                redirectURL = authzRequest.getLocationUri();
                String domain = null;
                if (requestParams.get("domain") != null) {
                    domain = requestParams.get("domain").get(0);
                }


                if (StringUtils.isNotBlank(domain)) {
                    redirectURL = redirectURL + "&fidp=" + domain;
                }

                if (StringUtils.isNotBlank(queryString)) {
                    if (!queryString.startsWith("&")) {
                        redirectURL = redirectURL + "&" + queryString;
                    } else {
                        redirectURL = redirectURL + queryString;
                    }
                }
            }
        } catch (OAuthSystemException e) {
            log.error("Error occurred while initiating authentication request.", e);
        }
        return redirectURL;
    }

    public static ProcessAuthResponse processAuthenticationResponse(ProcessAuthRequest processAuthRequest) {

        try {
            Map<String, String> requestQueryParamMap =
                    getRequestQueryParamMap(processAuthRequest.getRequest().getQueryString());
            OAuthClientRequest accessTokenRequest = getAccessTokenRequest(processAuthRequest.
                    getAuthenticationContext().getAuthenticatorPropertiesMap(), requestQueryParamMap.get("code"));

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthClientResponse tokenResponse = oAuthClient.accessToken(accessTokenRequest);

            String accessToken = tokenResponse.getParam(OIDCAuthenticatorConstants.ACCESS_TOKEN);
            String idToken = tokenResponse.getParam(OIDCAuthenticatorConstants.ID_TOKEN);

            Map<String, Object> idTokenAttributes = getIdTokenClaims(idToken);

            Map<String, String> userAttributes = idTokenAttributes.entrySet().stream()
                    .filter(entry -> !ArrayUtils.contains(NON_USER_ATTRIBUTES, entry.getKey()))
                    .collect(Collectors.toMap(entry -> entry.getKey(), entry -> String.valueOf(entry.getValue())));
            AuthenticatedUser authenticatedUser = AuthenticatedUser.newBuilder()
                    .setIsFederatedUser(true)
                    .putAllUserAttributes(userAttributes).build();
            ProcessAuthResponse processAuthResponse = ProcessAuthResponse.newBuilder()
                    .setAuthenticationStatus(AuthenticatorFlowStatus.SUCCESS_COMPLETED)
                    .putAuthenticationData(OIDCAuthenticatorConstants.ACCESS_TOKEN, accessToken)
                    .putAuthenticationData(OIDCAuthenticatorConstants.ID_TOKEN, idToken)
                    .setAuthenticatedUser(authenticatedUser)
                    .build();
            return processAuthResponse;
        } catch (UnsupportedEncodingException | OAuthProblemException | OAuthSystemException e) {
            log.error("Error occurred while processing authentication response.", e);
            return null;
        }
    }

    private static OAuthClientRequest getAccessTokenRequest(Map<String, String> authenticatorProperties,
                                                            String authCode) {

        String clientId = authenticatorProperties.get(OIDCAuthenticatorConstants.CLIENT_ID);
        String clientSecret = authenticatorProperties.get(OIDCAuthenticatorConstants.CLIENT_SECRET);
        String tokenEndPoint = authenticatorProperties.get(OIDCAuthenticatorConstants.OAUTH2_TOKEN_URL);
        String callbackUrl = authenticatorProperties.get(OIDCAuthenticatorConstants.IdPConfParams.CALLBACK_URL);
        boolean isHTTPBasicAuth = Boolean.parseBoolean(authenticatorProperties.get(OIDCAuthenticatorConstants
                .IS_BASIC_AUTH_ENABLED));
        OAuthClientRequest accessTokenRequest = null;
        try {
            if (isHTTPBasicAuth) {
                if (log.isDebugEnabled()) {
                    log.debug("Authenticating to token endpoint: " + tokenEndPoint + " with HTTP basic " +
                            "authentication scheme.");
                }
                accessTokenRequest = OAuthClientRequest.tokenLocation(tokenEndPoint).setGrantType(GrantType
                                .AUTHORIZATION_CODE).setRedirectURI(callbackUrl).setCode(authCode)
                        .buildBodyMessage();
                String base64EncodedCredential = new String(Base64.encodeBase64((clientId + ":" +
                        clientSecret).getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
                accessTokenRequest.addHeader(OAuth.HeaderType.AUTHORIZATION, "Basic " + base64EncodedCredential);
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Authenticating to token endpoint: " + tokenEndPoint + " including client credentials "
                            + "in request body.");
                }
                accessTokenRequest = OAuthClientRequest.tokenLocation(tokenEndPoint).setGrantType(GrantType
                        .AUTHORIZATION_CODE).setClientId(clientId).setClientSecret(clientSecret).setRedirectURI
                        (callbackUrl).setCode(authCode).buildBodyMessage();
            }
            // set 'Origin' header to access token request.
            if (accessTokenRequest != null) {
                // fetch the 'Hostname' configured in carbon.xml
                accessTokenRequest.addHeader(OIDCAuthenticatorConstants.HTTP_ORIGIN_HEADER,
                        new URI(callbackUrl).getHost());
            }
        } catch (OAuthSystemException | URISyntaxException e) {
            log.error("Error while retrieving access token.", e);
        }
        return accessTokenRequest;
    }

    private static Map<String, Object> getIdTokenClaims(String idToken) {

        String base64Body = idToken.split("\\.")[1];
        byte[] decoded = Base64.decodeBase64(base64Body.getBytes(StandardCharsets.UTF_8));
        return JSONUtils.parseJSON(new String(decoded, StandardCharsets.UTF_8));
    }

    private static Map<String, String> getRequestQueryParamMap(String queryString) throws UnsupportedEncodingException {

        if (StringUtils.isEmpty(queryString)) {
            return Collections.EMPTY_MAP;
        }
        Map<String, String> queryParams = new HashMap<>();
        String[] pairs = queryString.split("&");
        for (String pair : pairs) {
            String[] keyValuePair = pair.split("=");
            queryParams.put(URLDecoder.decode(keyValuePair[0], StandardCharsets.UTF_8.name()),
                    URLDecoder.decode(keyValuePair[1], StandardCharsets.UTF_8.name()));
        }
        return queryParams;
    }

    private static Map<String, List<String>> getRequestParams(Request request) {

        if (request.getRequestParamsList().isEmpty()) {
            return new HashMap<>();
        }
        return request.getRequestParamsList().stream().collect(
                Collectors.toMap(Request.RequestParam::getParamName, Request.RequestParam::getParamValueList));
    }

    private static String getLoginType(Map<String, List<String>> requestParams) {

        if (requestParams.get(OIDCAuthenticatorConstants.OAUTH2_PARAM_STATE) != null) {
            String state = requestParams.get(OIDCAuthenticatorConstants.OAUTH2_PARAM_STATE).get(0);
            if (state != null) {
                String[] stateElements = state.split(",");
                if (stateElements.length > 1) {
                    return stateElements[1];
                }
            }
        }
        return null;
    }

    private static String interpretQueryString(AuthenticationContext context, String queryString,
                                               Map<String, List<String>> parameters) {

        if (StringUtils.isBlank(queryString)) {
            return null;
        }
        if (queryString.contains(OIDCAuthenticatorConstants.AUTH_PARAM)) {
            queryString = getQueryStringWithAuthenticatorParam(context, queryString);
        }
        Matcher matcher = pattern.matcher(queryString);
        while (matcher.find()) {
            String name = matcher.group(1);
            List<String> values = parameters.get(name);
            String value = "";
            if (values != null && values.size() > 0) {
                value = values.get(0);
            }
            if (log.isDebugEnabled()) {
                log.debug("InterpretQueryString name: " + name + ", value: " + value);
            }
            queryString = queryString.replaceAll("\\$\\{" + name + "}", Matcher.quoteReplacement(value));
        }
        if (log.isDebugEnabled()) {
            log.debug("Output QueryString: " + queryString);
        }
        return queryString;
    }

    /**
     * To capture the additional authenticator params from the adaptive script and interpret the query string with
     * additional params.
     *
     * @param context     Authentication context
     * @param queryString the query string with additional param
     * @return interpreted query string
     */
    private static String getQueryStringWithAuthenticatorParam(AuthenticationContext context, String queryString) {

        Matcher matcher = Pattern.compile(OIDCAuthenticatorConstants.DYNAMIC_AUTH_PARAMS_LOOKUP_REGEX)
                .matcher(queryString);
        Map<String, String> authenticatorParams = context.getAuthenticatorParamsMap();
        String value = "";
        while (matcher.find()) {
            String paramName = matcher.group(1);
            if (StringUtils.isNotEmpty(authenticatorParams.get(paramName))) {
                value = authenticatorParams.get(paramName);
            }
            try {
                value = URLEncoder.encode(value, StandardCharsets.UTF_8.name());
                if (log.isDebugEnabled()) {
                    log.debug("InterpretQueryString with authenticator param: " + paramName + "," +
                            " value: " + value);
                }
            } catch (UnsupportedEncodingException e) {
                log.error("Error while encoding the authenticator param: " + paramName +
                        " with value: " + value, e);
            }
            queryString = queryString.replaceAll("\\$authparam\\{" + paramName + "}",
                    Matcher.quoteReplacement(value));
        }
        if (log.isDebugEnabled()) {
            log.debug("Output QueryString with Authenticator Params : " + queryString);
        }
        return queryString;
    }
}
