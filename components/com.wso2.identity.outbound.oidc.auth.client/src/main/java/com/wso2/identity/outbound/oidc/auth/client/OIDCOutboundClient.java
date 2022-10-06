/**
 * Copyright (c) 2022, WSO2 LLC. (https://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.identity.outbound.oidc.auth.client;

import com.wso2.identity.outbound.oidc.auth.client.datasource.RPCConnection;
import com.wso2.identity.outbound.oidc.auth.service.rpc.CanHandleResponse;
import com.wso2.identity.outbound.oidc.auth.service.rpc.InitAuthRequest;
import com.wso2.identity.outbound.oidc.auth.service.rpc.InitAuthResponse;
import com.wso2.identity.outbound.oidc.auth.service.rpc.OutboundOIDCServiceGrpc;
import com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest;
import com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthResponse;
import com.wso2.identity.outbound.oidc.auth.service.rpc.Request;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * OIDCOutbound client which contains the implementation to connect to the microservice.
 */
public class OIDCOutboundClient {

    private static final Log log = LogFactory.getLog(OIDCOutboundClient.class);

    /**
     * OIDCOutboundClient lazy holder.
     */
    private static class OIDCOutboundClientLazyHolder {

        static final OIDCOutboundClient INSTANCE = new OIDCOutboundClient();
    }

    /**
     * Get an OIDCOutboundClient instance.
     *
     * @return OIDCOutboundClient object.
     */
    public static OIDCOutboundClient getInstance() {

        return OIDCOutboundClientLazyHolder.INSTANCE;
    }

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

    public ProcessAuthResponse processAuthenticationResponse(ProcessAuthRequest processAuthRequest,
                                                             String connectionTarget) {

        ManagedChannel managedChannel = RPCConnection.getInstance().getOutboundAuthenticatorChannel(connectionTarget);
        OutboundOIDCServiceGrpc.OutboundOIDCServiceBlockingStub stub
                = OutboundOIDCServiceGrpc.newBlockingStub(managedChannel);

        try {
            return stub.processAuthenticationResponse(processAuthRequest);
        } catch (StatusRuntimeException ex) {
            log.error("Error connecting OIDC Outbound service", ex);
        }
        return null;
    }
}
