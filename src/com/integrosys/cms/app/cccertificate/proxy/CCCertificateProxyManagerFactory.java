/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/proxy/CCCertificateProxyManagerFactory.java,v 1.1 2003/08/04 12:55:05 hltan Exp $
 */
package com.integrosys.cms.app.cccertificate.proxy;

/**
 * Factory class that instantiate the ICertificateProxyManager.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/04 12:55:05 $ Tag: $Name: $
 */
public class CCCertificateProxyManagerFactory {
	/**
	 * Default Constructor
	 */
	public CCCertificateProxyManagerFactory() {
	}

	/**
	 * Get the certificate proxy manager.
	 * @return ICertificateProxyManager - the certificate proxy manager
	 */
	public static ICCCertificateProxyManager getCCCertificateProxyManager() {
		return new CCCertificateProxyManagerImpl();
	}
}