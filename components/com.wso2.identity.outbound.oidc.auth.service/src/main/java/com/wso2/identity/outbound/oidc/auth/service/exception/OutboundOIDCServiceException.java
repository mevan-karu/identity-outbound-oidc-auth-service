/**
 * Copyright (c) 2022, WSO2 LLC. (https://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */


package com.wso2.identity.outbound.oidc.auth.service.exception;

/**
 * Outbound OIDC service base exception class.
 */
public class OutboundOIDCServiceException extends Exception {
    private static final long serialVersionUID = -3973370923387458257L;
    private String errorCode = null;

    /**
     * Plain constructor for OutboundOIDCServiceException.
     */
    public OutboundOIDCServiceException() {

        super();
    }

    /**
     * Constructor for OutboundOIDCServiceException with error message and error code.
     *
     * @param message   Error message.
     * @param errorCode Error code.
     */
    public OutboundOIDCServiceException(String message, String errorCode) {

        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Constructor for OutboundOIDCServiceException with error message, error code and cause.
     *
     * @param message   Error message.
     * @param errorCode Error code.
     * @param cause     Throwable exception.
     */
    public OutboundOIDCServiceException(String message, String errorCode, Throwable cause) {

        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Constructor for OutboundOIDCServiceException with error message and cause.
     *
     * @param message Error message.
     * @param cause   Throwable exception.
     */
    public OutboundOIDCServiceException(String message, Throwable cause) {

        super(message, cause);
    }

    /**
     * Constructor for OutboundOIDCServiceException with cause.
     *
     * @param cause Throwable exception.
     */
    public OutboundOIDCServiceException(Throwable cause) {

        super(cause);
    }

    public String getErrorCode() {

        return errorCode;
    }

    @Override
    public String toString() {

        return errorCode + " - " + super.toString() + "\n";
    }
}
