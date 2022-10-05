/**
 * Copyright (c) 2022, WSO2 LLC. (https://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */


package com.wso2.identity.outbound.oidc.auth.service.authenticator;

import java.util.regex.Pattern;

/**
 * constants.
 */
public class OIDCAuthenticatorConstants {

    public static final String AUTHENTICATOR_NAME = "OpenIDConnectAuthenticator";
    public static final String AUTHENTICATOR_FRIENDLY_NAME = "openidconnect";
    public static final String LOGIN_TYPE = "OIDC";
    public static final String OAUTH_OIDC_SCOPE = "openid";
    public static final String OAUTH2_GRANT_TYPE_CODE = "code";
    public static final String OAUTH2_PARAM_STATE = "state";
    public static final String OAUTH2_ERROR = "error";
    public static final String REDIRECT_URI = "redirect_uri";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String ID_TOKEN = "id_token";
    public static final String CLIENT_ID = "ClientId";
    public static final String CLIENT_SECRET = "ClientSecret";
    public static final String OAUTH2_AUTHZ_URL = "OAuth2AuthzEPUrl";
    public static final String OAUTH2_TOKEN_URL = "OAuth2TokenEPUrl";
    public static final String IS_BASIC_AUTH_ENABLED = "IsBasicAuthEnabled";
    public static final String OIDC_QUERY_PARAM_MAP_PROPERTY_KEY = "oidc:param.map";
    public static final String HTTP_ORIGIN_HEADER = "Origin";
    public static final String POST_LOGOUT_REDIRECT_URI = "post_logout_redirect_uri";
    public static final String ID_TOKEN_HINT = "id_token_hint";
    public static final String AUTH_PARAM = "$authparam";
    public static final String DYNAMIC_AUTH_PARAMS_LOOKUP_REGEX = "\\$authparam\\{(\\w+)\\}";
    public static final String LOGOUT_TOKEN = "logout_token";
    public static final Pattern OIDC_BACKCHANNEL_LOGOUT_ENDPOINT_URL_PATTERN = Pattern.compile("(.*)/identity/oidc" +
            "/slo(.*)");
    public static final String QUERY_PARAMS = "commonAuthQueryParams";

    private OIDCAuthenticatorConstants() {

    }

    /**
     * OAuth 2.0 request params.
     */
    public static class OAuth20Params {
        public static final String SCOPE = "scope";
        public static final String PROMPT = "prompt";
        public static final String NONCE = "nonce";
        public static final String DISPLAY = "display";
        public static final String ID_TOKEN_HINT = "id_token_hint";
        public static final String LOGIN_HINT = "login_hint";
        public static final String AUTH_TIME = "auth_time";
        public static final String ESSENTIAL = "essential";
        public static final String USERINFO = "userinfo";
        public static final String CLIENT_ID = "client_id";
        public static final String REDIRECT_URI = "redirect_uri";

        private OAuth20Params() {
        }
    }

    /**
     * Authenticator config constants.
     */
    public class AuthenticatorConfParams {

        public static final String DEFAULT_IDP_CONFIG = "DefaultIdPConfig";

        private AuthenticatorConfParams() {

        }
    }

    /**
     * IDP config constants.
     */
    public class IdPConfParams {

        public static final String CLIENT_ID = "ClientId";
        public static final String CLIENT_SECRET = "ClientSecret";
        public static final String AUTHORIZATION_EP = "AuthorizationEndPoint";
        public static final String TOKEN_EP = "TokenEndPoint";
        public static final String OIDC_LOGOUT_URL = "OIDCLogoutEPUrl";
        public static final String USER_INFO_EP = "UserInfoEndPoint";
        public static final String CALLBACK_URL = "callbackUrl";

        private IdPConfParams() {

        }
    }
}
