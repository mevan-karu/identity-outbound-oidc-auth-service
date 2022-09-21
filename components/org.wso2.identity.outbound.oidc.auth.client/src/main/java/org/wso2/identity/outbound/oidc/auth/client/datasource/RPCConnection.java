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

package org.wso2.identity.outbound.oidc.auth.client.datasource;

import io.grpc.Grpc;
import io.grpc.ManagedChannel;
import io.grpc.TlsChannelCredentials;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.core.util.KeyStoreManager;
import org.wso2.carbon.utils.multitenancy.MultitenantConstants;

import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;

/**
 * Class containing the gRPC connection building logic.
 */
public class RPCConnection {

    private static final Log LOG = LogFactory.getLog(RPCConnection.class);

    private static volatile RPCConnection instance = new RPCConnection();


    /**
     * Get a RPCConnection connection instance.
     *
     * @return RPCConnection object.
     */
    public static RPCConnection getInstance() {

        if (instance == null) {
            instance = new RPCConnection();
        }
        return instance;
    }

    /**
     * Get gRPC channel for oidc authenticator.
     *
     * @return gRPC channel.
     */
    public ManagedChannel getOutboundAuthenticatorChannel(String connectionTarget) {

        ManagedChannel outboundAuthenticatorChannel;
        KeyStoreManager tenantKSM = KeyStoreManager.getInstance(MultitenantConstants.SUPER_TENANT_ID);
        KeyManagerFactory keyManagerFactory;
        try {
            KeyStore keyStore = tenantKSM.getPrimaryKeyStore();
            keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, tenantKSM.getPrimaryPrivateKeyPasssword().toCharArray());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving keys for building grpc connection channel.", e);
        }

        TlsChannelCredentials.Builder tlsBuilder = TlsChannelCredentials.newBuilder();
        tlsBuilder.keyManager(keyManagerFactory.getKeyManagers());

        outboundAuthenticatorChannel = Grpc.newChannelBuilder(connectionTarget, tlsBuilder.build()).build();

        if (LOG.isDebugEnabled()) {
            LOG.debug("Initiated grpc connection channel.");
        }
        return outboundAuthenticatorChannel;
    }
}
