/*
 * Copyright (c) 2022, WSO2 LLC. (http://www.wso2.com).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.identity.outbound.oidc.auth.client;

import com.nimbusds.jose.util.JSONObjectUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAuthzResponse;
import org.apache.oltu.oauth2.client.response.OAuthClientResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.wso2.carbon.identity.application.authentication.framework.AbstractApplicationAuthenticator;
import org.wso2.carbon.identity.application.authentication.framework.FederatedApplicationAuthenticator;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.exception.AuthenticationFailedException;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;
import org.wso2.carbon.identity.application.common.model.Property;
import org.wso2.carbon.identity.core.util.IdentityUtil;
import org.wso2.identity.outbound.oidc.auth.client.constants.CustomOIDCAuthenticatorConstants;
import org.wso2.identity.outbound.oidc.auth.service.rpc.InitAuthRequest;
import org.wso2.identity.outbound.oidc.auth.service.rpc.InitAuthResponse;
import org.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest;
import org.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthResponse;
import org.wso2.identity.outbound.oidc.auth.service.rpc.Request;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * CustomOIDC Authenticator to connect to the external outbound oidc microservice.
 */
public class CustomOIDCAuthenticator extends AbstractApplicationAuthenticator
        implements FederatedApplicationAuthenticator {

    private static final Log log = LogFactory.getLog(CustomOIDCAuthenticator.class);


    @Override
    public boolean canHandle(HttpServletRequest request) {

        log.debug("Invoking canHandle method in remote microservice.");
        Request req = Request.newBuilder()
                .setRequestURL(request.getRequestURI())
                .setQueryString(request.getQueryString())
                .addRequestParams(
                        Request.RequestParam.newBuilder()
                        .setParamName(CustomOIDCAuthenticatorConstants.OAUTH2_PARAM_STATE)
                        .addParamValue(getRequestState(request)).build())
                .build();

        return OIDCOutboundClient.getInstance().canHandle(req, getAuthenticatorConfig().getParameterMap().get("url"));
    }

    @Override
    public String getFriendlyName() {

        return "microservice-authenticator";
    }

    @Override
    public String getName() {

        return "MicroServiceAuthenticator";
    }

    @Override
    public String getClaimDialectURI() {

        // Get the claim dialect URI if this authenticator receives claims in a standard dialect.
        return CustomOIDCAuthenticatorConstants.OIDC_DIALECT;
    }

    @Override
    public List<Property> getConfigurationProperties() {

        // Get the required configuration properties.
        List<Property> configProperties = new ArrayList<>();
        Property clientId = new Property();
        clientId.setName(CustomOIDCAuthenticatorConstants.CLIENT_ID);
        clientId.setDisplayName("Client Id");
        clientId.setRequired(true);
        clientId.setDescription("Enter OAuth2/OpenID Connect client identifier value");
        clientId.setType("string");
        clientId.setDisplayOrder(1);
        configProperties.add(clientId);

        Property clientSecret = new Property();
        clientSecret.setName(CustomOIDCAuthenticatorConstants.CLIENT_SECRET);
        clientSecret.setDisplayName("Client Secret");
        clientSecret.setRequired(true);
        clientSecret.setDescription("Enter OAuth2/OpenID Connect client secret value");
        clientSecret.setType("string");
        clientSecret.setDisplayOrder(2);
        clientSecret.setConfidential(true);
        configProperties.add(clientSecret);

        Property callbackUrl = new Property();
        callbackUrl.setDisplayName("Callback URL");
        callbackUrl.setName(CustomOIDCAuthenticatorConstants.CALLBACK_URL);
        callbackUrl.setDescription("The callback URL used to partner identity provider credentials.");
        callbackUrl.setDisplayOrder(3);
        configProperties.add(callbackUrl);

        Property authzEpUrl = new Property();
        authzEpUrl.setName(CustomOIDCAuthenticatorConstants.OAUTH2_AUTHZ_URL);
        authzEpUrl.setDisplayName("Authorization Endpoint URL");
        authzEpUrl.setRequired(true);
        authzEpUrl.setDescription("Enter OAuth2/OpenID Connect authorization endpoint URL value");
        authzEpUrl.setType("string");
        authzEpUrl.setDisplayOrder(4);
        configProperties.add(authzEpUrl);

        Property tokenEpUrl = new Property();
        tokenEpUrl.setName(CustomOIDCAuthenticatorConstants.OAUTH2_TOKEN_URL);
        tokenEpUrl.setDisplayName("Token Endpoint URL");
        tokenEpUrl.setRequired(true);
        tokenEpUrl.setDescription("Enter OAuth2/OpenID Connect token endpoint URL value");
        tokenEpUrl.setType("string");
        tokenEpUrl.setDisplayOrder(5);
        configProperties.add(tokenEpUrl);

        return configProperties;
    }

    @Override
    protected void initiateAuthenticationRequest(HttpServletRequest request, HttpServletResponse response,
                                                 AuthenticationContext context) throws AuthenticationFailedException {

        try {
            Map<String, String> authenticatorProperties = context.getAuthenticatorProperties();
            org.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext authenticationContext
                    = org.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext.newBuilder()
                    .putAllAuthenticatorProperties(authenticatorProperties)
                    .setContextIdentifier(context.getContextIdentifier())
                    .putAllAuthenticatorParams(context.getAuthenticatorParams(context.getCurrentAuthenticator()))
                    .build();

            InitAuthRequest initAuthRequest = InitAuthRequest.newBuilder()
                    .setAuthenticationContext(authenticationContext).build();

            InitAuthResponse initAuthResponse = OIDCOutboundClient.getInstance().initiateAuthenticationRequest(
                    initAuthRequest, getAuthenticatorConfig().getParameterMap().get("url"));
            if (initAuthResponse != null && initAuthResponse.getIsRedirect()) {
                response.sendRedirect(initAuthResponse.getRedirectUrl());
            } else {
                throw new AuthenticationFailedException("Exception while handling initiateAuthenticationRequest.");
            }
        } catch (IOException e) {
            throw new AuthenticationFailedException("Exception while building authorization code request", e);
        }
    }

    @Override
    protected void processAuthenticationResponse(HttpServletRequest request, HttpServletResponse response,
                                                 AuthenticationContext context) throws AuthenticationFailedException {

        Request req = Request.newBuilder()
                .setRequestURL(request.getRequestURI())
                .setQueryString(request.getQueryString())
                .addRequestParams(
                        Request.RequestParam.newBuilder()
                        .setParamName(CustomOIDCAuthenticatorConstants.OAUTH2_PARAM_STATE)
                        .addParamValue(getRequestState(request)).build())
                .build();

        Map<String, String> authenticatorProperties = context.getAuthenticatorProperties();
        org.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext authenticationContext
                = org.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext.newBuilder()
                .putAllAuthenticatorProperties(authenticatorProperties)
                .setContextIdentifier(context.getContextIdentifier())
                .putAllAuthenticatorParams(context.getAuthenticatorParams(context.getCurrentAuthenticator()))
                .build();

        ProcessAuthRequest processAuthRequest = ProcessAuthRequest.newBuilder()
                .setAuthenticationContext(authenticationContext)
                .setRequest(req)
                .build();

        ProcessAuthResponse processAuthResponse = OIDCOutboundClient.getInstance().processAuthenticationResponse(
                processAuthRequest, getAuthenticatorConfig().getParameterMap().get("url"));

        String accessToken = processAuthResponse.getAuthenticationDataOrDefault(
                CustomOIDCAuthenticatorConstants.ACCESS_TOKEN, StringUtils.EMPTY);
        if (StringUtils.isBlank(accessToken)) {
            throw new AuthenticationFailedException("Access token is empty or null");
        }

        context.setProperty(CustomOIDCAuthenticatorConstants.ACCESS_TOKEN, accessToken);

        String idToken = processAuthResponse.getAuthenticationDataOrDefault(
                CustomOIDCAuthenticatorConstants.ID_TOKEN, StringUtils.EMPTY);
        if (StringUtils.isBlank(idToken)) {
            throw new AuthenticationFailedException("Id token is required and is missing in OIDC response");
        }

        AuthenticatedUser authenticatedUser;
        Map<String, Object> jsonObject = new HashMap<>();

        if (StringUtils.isNotBlank(idToken)) {
            jsonObject = getIdTokenClaims(context, idToken);
            String authenticatedUserId = getAuthenticateUser(jsonObject);
            if (authenticatedUserId == null) {
                throw new AuthenticationFailedException("Cannot find the userId from the id_token sent " +
                        "by the federated IDP.");
            }
            authenticatedUser = AuthenticatedUser
                    .createFederateAuthenticatedUserFromSubjectIdentifier(authenticatedUserId);
        } else {
            authenticatedUser = AuthenticatedUser.createFederateAuthenticatedUserFromSubjectIdentifier(
                    getAuthenticateUser(jsonObject));
        }
        context.setSubject(authenticatedUser);
    }

    @Override
    public String getContextIdentifier(HttpServletRequest request) {

        String state = request.getParameter(CustomOIDCAuthenticatorConstants.OAUTH2_PARAM_STATE);
        if (state != null) {
            return state.split(",")[0];
        } else {
            return null;
        }
    }

    private String getAuthenticateUser(Map<String, Object> oidcClaims) {

        // Get the authenticated user's user Id from the id_token by the sub claim value.
        return (String) oidcClaims.get(CustomOIDCAuthenticatorConstants.SUB);
    }

    private Map<String, Object> getIdTokenClaims(AuthenticationContext context, String idToken) {

        context.setProperty(CustomOIDCAuthenticatorConstants.ID_TOKEN, idToken);
        String base64Body = idToken.split("\\.")[1];
        byte[] decoded = Base64.decodeBase64(base64Body.getBytes(StandardCharsets.UTF_8));
        Set<Map.Entry<String, Object>> jwtAttributeSet = new HashSet<>();
        try {
            jwtAttributeSet = JSONObjectUtils.parse(new String(decoded, StandardCharsets.UTF_8)).entrySet();
        } catch (ParseException e) {
            log.error("Error occurred while parsing JWT provided by federated IDP: ", e);
        }
        Map<String, Object> jwtAttributeMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : jwtAttributeSet) {
            jwtAttributeMap.put(entry.getKey(), entry.getValue());
        }
        return jwtAttributeMap;
    }

    private OAuthClientRequest getAccessTokenRequest(AuthenticationContext context, OAuthAuthzResponse
            authzResponse) throws AuthenticationFailedException {

        // Extract the authentication properties from the context.
        Map<String, String> authenticatorProperties = context.getAuthenticatorProperties();
        String clientId = authenticatorProperties.get(CustomOIDCAuthenticatorConstants.CLIENT_ID);
        String clientSecret = authenticatorProperties.get(CustomOIDCAuthenticatorConstants.CLIENT_SECRET);
        String tokenEndPoint = authenticatorProperties.get(CustomOIDCAuthenticatorConstants.OAUTH2_TOKEN_URL);
        String callbackUrl = authenticatorProperties.get(CustomOIDCAuthenticatorConstants.CALLBACK_URL);

        OAuthClientRequest accessTokenRequest;
        try {
            // Build access token request
            accessTokenRequest = OAuthClientRequest.tokenLocation(tokenEndPoint).setGrantType(GrantType
                    .AUTHORIZATION_CODE).setClientId(clientId).setClientSecret(clientSecret).setRedirectURI
                    (callbackUrl).setCode(authzResponse.getCode()).buildBodyMessage();
            if (accessTokenRequest != null) {
                String serviceUrl = IdentityUtil.getServicePath();
                String serverURL = IdentityUtil.getServerURL(serviceUrl, true, true);
                accessTokenRequest.addHeader(CustomOIDCAuthenticatorConstants.HTTP_ORIGIN_HEADER, serverURL);
            }
        } catch (OAuthSystemException e) {
            throw new AuthenticationFailedException("Error while building access token request", e);
        }
        return accessTokenRequest;
    }

    private OAuthClientResponse getOauthResponse(OAuthClient oAuthClient, OAuthClientRequest accessRequest)
            throws AuthenticationFailedException {

        OAuthClientResponse oAuthResponse;
        try {
            oAuthResponse = oAuthClient.accessToken(accessRequest);
        } catch (OAuthSystemException | OAuthProblemException e) {
            throw new AuthenticationFailedException("Exception while requesting access token");
        }
        return oAuthResponse;
    }

    private String getRequestState(HttpServletRequest request) {

        // Return EMPTY string if the state parameter in the request is null.
        String state = request.getParameter(CustomOIDCAuthenticatorConstants.OAUTH2_PARAM_STATE);
        if (StringUtils.isNotEmpty(state)) {
            return state;
        }
        return StringUtils.EMPTY;
    }
}
