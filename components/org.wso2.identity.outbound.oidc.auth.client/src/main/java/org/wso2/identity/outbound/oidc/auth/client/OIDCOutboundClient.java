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

import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.identity.outbound.oidc.auth.client.datasource.RPCConnection;
import org.wso2.identity.outbound.oidc.auth.service.rpc.CanHandleResponse;
import org.wso2.identity.outbound.oidc.auth.service.rpc.InitAuthRequest;
import org.wso2.identity.outbound.oidc.auth.service.rpc.InitAuthResponse;
import org.wso2.identity.outbound.oidc.auth.service.rpc.OutboundOIDCServiceGrpc;
import org.wso2.identity.outbound.oidc.auth.service.rpc.Request;

/**
 * OIDCOutbound client which contains the implementation to connect to the microservice.
 */
public class OIDCOutboundClient {

    private static final Log log = LogFactory.getLog(OIDCOutboundClient.class);


    public Boolean canHandle(Request request, String connectionTarget) {

        ManagedChannel managedChannel = RPCConnection.getInstance().getOutboundAuthenticatorChannel(connectionTarget);
        OutboundOIDCServiceGrpc.OutboundOIDCServiceBlockingStub stub
                = OutboundOIDCServiceGrpc.newBlockingStub(managedChannel);
        try {
            CanHandleResponse canHandleResponse = stub.canHandle(request);
            log.info("Remote canHandle method response received.");
            return canHandleResponse.getCanHandle();
        } catch (StatusRuntimeException ex) {
            log.error("Error connecting OIDC Outbound service", ex);
        }
        return false;
    }

    public InitAuthResponse initiateAuthenticationRequest(InitAuthRequest authRequest, String connectionTarget) {

        ManagedChannel managedChannel = RPCConnection.getInstance().getOutboundAuthenticatorChannel(connectionTarget);
        OutboundOIDCServiceGrpc.OutboundOIDCServiceBlockingStub stub
                = OutboundOIDCServiceGrpc.newBlockingStub(managedChannel);

        try {
            InitAuthResponse response = stub.initiateAuthentication(authRequest);
            return response;
        } catch (StatusRuntimeException ex) {
            log.error("Error connecting OIDC Outbound service", ex);
        }
        return null;
    }
}
