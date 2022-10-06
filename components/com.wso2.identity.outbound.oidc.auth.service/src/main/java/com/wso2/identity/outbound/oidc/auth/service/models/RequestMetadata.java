/**
 * Copyright (c) 2022, WSO2 LLC. (https://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.identity.outbound.oidc.auth.service.models;

import com.wso2.identity.outbound.oidc.auth.service.util.Constants;
import io.grpc.Grpc;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Holds the gRPC request metadata.
 */
public class RequestMetadata {

    private static final Log LOG = LogFactory.getLog(RequestMetadata.class);
    private final String correlationId;
    private final Instant receivedTime;
    private final long callStart;
    private final String remoteIP;
    private final String callMethod;
    private final String userAgent;
    private final String methodType;

    /**
     * Constructor for request metadata with server call and metadata attribute set.
     *
     * @param serverCall Server call.
     * @param metadata   Metadata.
     */
    public RequestMetadata(ServerCall serverCall, Metadata metadata) {

        correlationId = processCorrelationId(metadata.get(Constants.CORRELATION_ID_METADATA_KEY));
        receivedTime = Instant.now();
        callStart = System.nanoTime();
        callMethod = serverCall.getMethodDescriptor().getBareMethodName();
        Object remoteAddr = serverCall.getAttributes().get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR);
        if (remoteAddr instanceof InetSocketAddress) {
            InetAddress inetAddress = ((InetSocketAddress) Objects.requireNonNull(serverCall.getAttributes()
                    .get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR))).getAddress();
            if (inetAddress != null) {
                remoteIP = inetAddress.getHostAddress();
            } else {
                remoteIP = StringUtils.EMPTY;
            }
        } else {
            remoteIP = StringUtils.EMPTY;
        }
        userAgent = metadata.get(Constants.USER_AGENT_METADATA_KEY);
        methodType = serverCall.getMethodDescriptor().getType().name();
    }

    public String getCorrelationId() {

        return correlationId;
    }

    /**
     * Return correlationId if not null, generate a correlationId if null.
     *
     * @param correlationId correlationId metadata from the request.
     * @return processed correlationId.
     */
    private String processCorrelationId(String correlationId) {

        if (StringUtils.isNotBlank(correlationId)) {
            return correlationId;
        }
        correlationId = UUID.randomUUID().toString();
        if (LOG.isDebugEnabled()) {
            LOG.debug(Constants.CORRELATION_ID_KEY + " not available in request. Setting correlation id to: "
                    + correlationId);
        }
        return correlationId;
    }

    public Instant getReceivedTime() {

        return receivedTime;
    }

    /**
     * Returns UTC Date Time string from the received time.
     *
     * @return Date Time string.
     */
    public String getReceivedDateTime() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_TIME_PATTERN)
                .withZone(ZoneId.from(ZoneOffset.UTC));
        return formatter.format(receivedTime);
    }

    public long getCallStart() {

        return callStart;
    }

    public String getRemoteIP() {

        return remoteIP;
    }

    public String getCallMethod() {

        return callMethod;
    }

    /**
     * Returns the duration in milliseconds.
     *
     * @return Duration in milliseconds.
     */
    public long getDurationInMillis() {

        long callEnd = System.nanoTime();
        return TimeUnit.NANOSECONDS.toMillis(callEnd - callStart);
    }

    public String getUserAgent() {

        return userAgent;
    }

    public String getMethodType() {

        return methodType;
    }

    @Override
    public String toString() {

        return "RequestMetadata{" +
                "correlationId='" + correlationId + '\'' +
                ", receivedTime=" + receivedTime +
                ", callStart=" + callStart +
                ", remoteIP='" + remoteIP + '\'' +
                ", callMethod='" + callMethod + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", methodType='" + methodType + '\'' +
                '}';
    }
}
