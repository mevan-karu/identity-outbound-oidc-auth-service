/**
 * Copyright (c) 2022, WSO2 LLC. (https://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.identity.outbound.oidc.auth.service.util;

import com.wso2.identity.outbound.oidc.auth.service.exception.OutboundOIDCServiceException;
import com.wso2.identity.outbound.oidc.auth.service.models.ServerConfig;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/**
 * Configurations builder class for the Outbound OIDC service.
 */
public class ConfigBuilder {

    private static final Log log = LogFactory.getLog(ConfigBuilder.class);

    /**
     * Build the service configuration.
     *
     * @return Service configuration instance.
     * @throws OutboundOIDCServiceException If an error occurred while reading the configuration.
     */
    public ServerConfig build() throws OutboundOIDCServiceException {

        Parameters params = new Parameters();
        // Read data from this file.
        File propertiesFile = new File(Constants.CONF_FILE);
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.fileBased().setFile(propertiesFile));
        Configuration fileBasedConfig;
        String keystorePassword;
        String truststorePassword;
        try {
            fileBasedConfig = builder.getConfiguration();

            String keystoreCredentialFilePath =
                    Paths.get(fileBasedConfig.getString(Constants.CONF_KEYSTORE_CREDENTIAL_PATH)).toString();
            File keystoreCredentialFile = new File(keystoreCredentialFilePath);
            keystorePassword = FileUtils.readFileToString(keystoreCredentialFile, StandardCharsets.UTF_8).trim();

            String truststoreCredentialFilePath =
                    Paths.get(fileBasedConfig.getString(Constants.CONF_TRUSTSTORE_CREDENTIAL_PATH)).toString();
            File truststoreCredentialFile = new File(truststoreCredentialFilePath);
            truststorePassword = FileUtils.readFileToString(truststoreCredentialFile, StandardCharsets.UTF_8).trim();
        } catch (ConfigurationException | IOException e) {
            throw new OutboundOIDCServiceException(Constants.ErrorMessages.ERROR_READING_CONFIGURATION_FILES
                    .getMessage(), Constants.ErrorMessages.ERROR_READING_CONFIGURATION_FILES.getCode(), e);
        }

        ServerConfig serverConfig = new ServerConfig.Builder()
                .setServerPort(fileBasedConfig.getInt(Constants.CONF_SERVER_PORT))
                .setKeystorePath(Paths.get(fileBasedConfig.getString(Constants.CONF_KEYSTORE_PATH)).toString())
                .setKeystorePassword(keystorePassword)
                .setKeystoreType(fileBasedConfig.getString(Constants.CONF_KEYSTORE_TYPE))
                .setTruststorePath(Paths.get(fileBasedConfig.getString(Constants.CONF_TRUSTSTORE_PATH)).toString())
                .setTruststorePassword(truststorePassword)
                .setTruststoreType(fileBasedConfig.getString(Constants.CONF_TRUSTSTORE_TYPE))
                .setAuthenticateClient(fileBasedConfig.getBoolean(Constants.CONF_AUTH_CLIENT, true)).build();
        if (log.isDebugEnabled()) {
            log.debug("Server configuration: " + serverConfig.toString());
        }
        return serverConfig;
    }
}
