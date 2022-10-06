/**
 * Copyright (c) 2022, WSO2 LLC. (https://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.identity.outbound.oidc.auth.service;

import com.wso2.identity.outbound.oidc.auth.service.authenticator.OIDCAuthenticator;
import com.wso2.identity.outbound.oidc.auth.service.rpc.CanHandleResponse;
import com.wso2.identity.outbound.oidc.auth.service.rpc.InitAuthRequest;
import com.wso2.identity.outbound.oidc.auth.service.rpc.InitAuthResponse;
import com.wso2.identity.outbound.oidc.auth.service.rpc.OutboundOIDCServiceGrpc;
import com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest;
import com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthResponse;
import com.wso2.identity.outbound.oidc.auth.service.rpc.Request;
import io.grpc.stub.StreamObserver;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Outbound OIDC authenticator service implementation.
 */
public class OutboundOIDCServiceImpl extends OutboundOIDCServiceGrpc.OutboundOIDCServiceImplBase {

    private static final Log LOG = LogFactory.getLog(OutboundOIDCServiceImpl.class);

    @Override
    public void canHandle(Request request, StreamObserver<CanHandleResponse> responseObserver) {

        boolean canHandle = OIDCAuthenticator.canHandle(request);
        CanHandleResponse canHandleResponse = CanHandleResponse.newBuilder().setCanHandle(canHandle).build();
        responseObserver.onNext(canHandleResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void initiateAuthentication(InitAuthRequest request, StreamObserver<InitAuthResponse> responseObserver) {

        boolean isRedirect = false;
        String redirectURL = OIDCAuthenticator.initiateAuthRequest(request);
        if (StringUtils.isNotBlank(redirectURL)) {
            isRedirect = true;
        }
        InitAuthResponse initAuthResponse = InitAuthResponse.newBuilder().setIsRedirect(isRedirect)
                .setRedirectUrl(redirectURL).build();
        responseObserver.onNext(initAuthResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void processAuthenticationResponse(ProcessAuthRequest request,
                                              StreamObserver<ProcessAuthResponse> responseObserver) {

        ProcessAuthResponse processAuthResponse = OIDCAuthenticator
                .processAuthenticationResponse(request);
        responseObserver.onNext(processAuthResponse);
        responseObserver.onCompleted();
    }
}
