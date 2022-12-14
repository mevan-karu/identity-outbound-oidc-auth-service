/**
 * Copyright (c) 2022, WSO2 LLC. (https://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.identity.outbound.oidc.auth.service;

import com.wso2.identity.outbound.oidc.auth.service.exception.OutboundOIDCServiceException;
import com.wso2.identity.outbound.oidc.auth.service.interceptor.RequestInterceptor;
import com.wso2.identity.outbound.oidc.auth.service.models.ServerConfig;
import com.wso2.identity.outbound.oidc.auth.service.util.ConfigBuilder;
import com.wso2.identity.outbound.oidc.auth.service.util.Constants;
import io.grpc.Grpc;
import io.grpc.Server;
import io.grpc.ServerInterceptors;
import io.grpc.TlsServerCredentials;
import io.grpc.protobuf.services.ProtoReflectionService;
import io.grpc.services.HealthStatusManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * This is the main class of the Outbound OIDC Service. This starts the gRPC service with mTLS security.
 */
public class OutboundOIDCServer {

    private static final Log log = LogFactory.getLog(OutboundOIDCServer.class);
    private final ServerConfig serverConfig;
    private Server server;
    private HealthStatusManager healthStatusManager;

    /**
     * Constructor of Outbound OIDC service server.
     *
     * @param serverConfig Outbound OIDC server configuration.
     */
    public OutboundOIDCServer(ServerConfig serverConfig) {

        this.serverConfig = serverConfig;
    }

    /**
     * Main method of the server class.
     *
     * @param args Main method arguments.
     */
    public static void main(String[] args) {

        log.info("Starting Outbound OIDC Service...");
        try {
            ServerConfig serverConfig = new ConfigBuilder().build();
            setSystemTruststore(serverConfig);
            final OutboundOIDCServer server = new OutboundOIDCServer(serverConfig);
            server.start();
            server.blockUntilShutdown();
        } catch (OutboundOIDCServiceException | InterruptedException | IOException e) {
            log.error("Error starting the Outbound OIDC Service.", e);
        }
    }

    private static KeyManager[] getKeyManagers(String path, String password, String type)
            throws OutboundOIDCServiceException {

        try {
            Path keystoreFilePath = Paths.get(path);
            KeyStore keystore;
            try (InputStream in = Files.newInputStream(keystoreFilePath)) {
                keystore = KeyStore.getInstance(type);
                keystore.load(in, password.toCharArray());
            }
            KeyManagerFactory keyManagerFactory = KeyManagerFactory
                    .getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keystore, password.toCharArray());
            return keyManagerFactory.getKeyManagers();
        } catch (UnrecoverableKeyException | CertificateException |
                IOException | KeyStoreException | NoSuchAlgorithmException e) {
            throw new OutboundOIDCServiceException(Constants.ErrorMessages.ERROR_GETTING_KEY_MANAGER_FILES.getMessage(),
                    Constants.ErrorMessages.ERROR_GETTING_KEY_MANAGER_FILES.getCode(), e);
        }
    }

    private static TrustManager[] getTrustManagers(String path, String password, String type)
            throws OutboundOIDCServiceException {

        try {
            Path truststoreFilePath = Paths.get(path);
            KeyStore truststore;
            try (InputStream in = Files.newInputStream(truststoreFilePath)) {
                truststore = KeyStore.getInstance(type);
                truststore.load(in, password.toCharArray());

            }
            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(truststore);
            return trustManagerFactory.getTrustManagers();
        } catch (CertificateException | IOException | KeyStoreException | NoSuchAlgorithmException e) {
            throw new OutboundOIDCServiceException(Constants.ErrorMessages.ERROR_GETTING_TRUST_MANAGERS.getMessage(),
                    Constants.ErrorMessages.ERROR_GETTING_TRUST_MANAGERS.getCode(), e);
        }
    }

    private static void setSystemTruststore(ServerConfig serverConfig) {

        String sysPropertyTruststorePath = "javax.net.ssl.trustStore";
        String sysPropertyTruststorePassword = "javax.net.ssl.trustStorePassword";
        String sysPropertyTruststoreType = "javax.net.ssl.trustStoreType";
        if (StringUtils.isBlank(System.getProperty(sysPropertyTruststorePath))) {
            System.setProperty(sysPropertyTruststorePath, serverConfig.getTruststorePath());
        }
        if (StringUtils.isBlank(System.getProperty(sysPropertyTruststorePassword))) {
            System.setProperty(sysPropertyTruststorePassword, serverConfig.getTruststorePassword());
        }
        if (StringUtils.isBlank(System.getProperty(sysPropertyTruststoreType))) {
            System.setProperty(sysPropertyTruststoreType, serverConfig.getTruststoreType());
        }
    }

    private void start() throws OutboundOIDCServiceException, IOException {

        healthStatusManager = new HealthStatusManager();
        int port = serverConfig.getServerPort();

        TlsServerCredentials.ClientAuth clientAuth = TlsServerCredentials.ClientAuth.REQUIRE;
        if (!serverConfig.isAuthenticateClient()) {
            log.warn("Disabling client authentication due to the configuration: \"server.auth.client=false\"");
            clientAuth = TlsServerCredentials.ClientAuth.NONE;
        }
        TlsServerCredentials.Builder tlsBuilder = TlsServerCredentials.newBuilder()
                .keyManager(getKeyManagers(serverConfig.getKeystorePath(),
                        serverConfig.getKeystorePassword(),
                        serverConfig.getKeystoreType()))
                .trustManager(getTrustManagers(serverConfig.getTruststorePath(),
                        serverConfig.getTruststorePassword(),
                        serverConfig.getTruststoreType()))
                .clientAuth(clientAuth);

        // TODO: Reflection service support is added temporarily to be removed after testing.
        server = Grpc.newServerBuilderForPort(port, tlsBuilder.build())
                .addService(healthStatusManager.getHealthService())
                .addService(ServerInterceptors.intercept(new OutboundOIDCServiceImpl(),
                        new RequestInterceptor()))
                .addService(ProtoReflectionService.newInstance())
                .build()
                .start();
        log.info("Outbound OIDC Service started successfully. Listening on port " + port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutting down Outbound OIDC Service since JVM is shutting down.");
            try {
                OutboundOIDCServer.this.stop();
            } catch (InterruptedException e) {
                log.error("Error while shutting down Outbound OIDC Service.", e);
            }
            log.info("Outbound OIDC Service shutdown complete.");
            // Shutdown logger.
            LogManager.shutdown();
        }));
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {

        if (server != null) {
            server.awaitTermination();
        }
    }

    private void stop() throws InterruptedException {

        if (healthStatusManager != null) {
            healthStatusManager.enterTerminalState();
        }
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }
}
