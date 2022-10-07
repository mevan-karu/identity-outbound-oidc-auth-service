/**
 * Copyright (c) 2022, WSO2 LLC. (https://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.identity.outbound.oidc.auth.client.internal;

import com.wso2.identity.outbound.oidc.auth.client.CustomOIDCAuthenticator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.wso2.carbon.identity.application.authentication.framework.ApplicationAuthenticator;

/**
 * Service component for CustomOIDCAuthenticator component.
 * Used to register CustomOIDC Authenticator.
 */
@Component(
        name = "com.wso2.identity.outbound.oidc.auth.client",
        immediate = true
)
public class OIDCOutboundClientComponent {

    private static final Log log = LogFactory.getLog(OIDCOutboundClientComponent.class);

    @Activate
    protected void activate(ComponentContext ctxt) {

        try {
            ctxt.getBundleContext().registerService(ApplicationAuthenticator.class.getName(),
                    new CustomOIDCAuthenticator(), null);
            if (log.isDebugEnabled()) {
                log.debug("OIDC Outbound Client bundle is activated");
            }
        } catch (Throwable e) {
            log.fatal(" Error while activating OIDC Outbound Client ", e);
        }
    }

}
