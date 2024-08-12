package com.integrosys.cms.ui.securityenvelope;

import com.integrosys.base.uiinfra.common.AbstractCommand;

import com.integrosys.cms.app.securityenvelope.proxy.ISecEnvelopeProxyManager;

/**
 * Title: CMS
 * Description:
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Erene Wong
 * Date: Jan 29, 2010
 */
public abstract class SecEnvelopeCmd extends AbstractCommand {

	private ISecEnvelopeProxyManager secEnvelopeProxy;

    public ISecEnvelopeProxyManager getSecEnvelopeProxy() {
        return secEnvelopeProxy;
    }

    public void setSecEnvelopeProxy(ISecEnvelopeProxyManager secEnvelopeProxy) {
        this.secEnvelopeProxy = secEnvelopeProxy;
    }
}