/**
 * Copyright (c) 2022, WSO2 LLC. (https://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.identity.outbound.oidc.auth.service.util;

import io.grpc.Metadata;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

/**
 * Constants for Outbound OIDC service.
 */
public class Constants {

    public static final String CONF_FILE = "application.properties";
    public static final String ACCESS_LOGGER = "ACCESS_LOG";
    public static final String CORRELATION_ID_KEY = "Correlation-ID";
    public static final String USER_AGENT_KEY = "user-agent";
    public static final Metadata.Key<String> CORRELATION_ID_METADATA_KEY = Metadata.Key.of(CORRELATION_ID_KEY,
            ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> USER_AGENT_METADATA_KEY = Metadata.Key.of(USER_AGENT_KEY,
            ASCII_STRING_MARSHALLER);
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss Z";

    public static final String CONF_KEYSTORE_CREDENTIAL_PATH = "keystore.credential.file.path";
    public static final String CONF_TRUSTSTORE_CREDENTIAL_PATH = "truststore.credential.file.path";
    public static final String CONF_SERVER_PORT = "server.port";
    public static final String CONF_KEYSTORE_PATH = "server.keystore.path";
    public static final String CONF_KEYSTORE_TYPE = "server.keystore.type";
    public static final String CONF_TRUSTSTORE_PATH = "server.truststore.path";
    public static final String CONF_TRUSTSTORE_TYPE = "server.truststore.type";
    public static final String CONF_AUTH_CLIENT = "server.auth.client";


    // Error constants.
    public static final String ERROR_CODE = "ERROR_CODE";
    public static final String ERROR_MESSAGE = "ERROR_MESSAGE";
    public static final String ERROR_DESCRIPTION = "ERROR_DESCRIPTION";
    public static final String ERROR_TRACE_ID = "ERROR_TRACE_ID";


    /**
     * Error messages.
     */
    public enum ErrorMessages {

        ERROR_READING_CONFIGURATION_FILES("15016", "Error reading configuration files",
                "Error reading configuration files"),
        ERROR_GETTING_TRUST_MANAGERS("10002", "Error getting trust managers",
                "Error getting trust managers"),
        ERROR_GETTING_KEY_MANAGER_FILES("10001", "Error getting key manager files",
                "Error getting key manager files");

        private final String code;
        private final String message;
        private final String description;
        private final String errorPrefix = "ASG-SS-"; // Outbound OIDC Service.

        ErrorMessages(String code, String message, String description) {

            this.code = errorPrefix + code;
            this.message = message;
            this.description = description;
        }

        public String getCode() {

            return code;
        }

        public String getMessage() {

            return message;
        }

        public String getDescription() {

            return description;
        }

        @Override
        public String toString() {

            return code + " : " + message + " : " + description;
        }
    }

}
