/**
 * Copyright (c) 2022, WSO2 LLC. (https://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.identity.outbound.oidc.auth.client.constants;

/**
 * Constants for Custom OIDC Authenticator.
 */
public class CustomOIDCAuthenticatorConstants {

    public static final String LOGIN_TYPE = "OIDC";

    public static final String OAUTH_OIDC_SCOPE = "openid";
    public static final String OAUTH2_GRANT_TYPE_CODE = "code";
    public static final String OAUTH2_PARAM_STATE = "state";

    public static final String ACCESS_TOKEN = "access_token";
    public static final String ID_TOKEN = "id_token";

    public static final String CLIENT_ID = "ClientId";
    public static final String CLIENT_SECRET = "ClientSecret";
    public static final String CALLBACK_URL = "callbackUrl";
    public static final String OAUTH2_AUTHZ_URL = "OAuth2AuthzEPUrl";
    public static final String OAUTH2_TOKEN_URL = "OAuth2TokenEPUrl";

    public static final String HTTP_ORIGIN_HEADER = "Origin";
    public static final String SUB = "sub";

    public static final String OIDC_DIALECT = "http://wso2.org/oidc/claim";
}
