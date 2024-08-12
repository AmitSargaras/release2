/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/proxy/SCCertificateProxyManagerFactory.java,v 1.1 2003/08/08 12:44:58 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.proxy;

/**
 * Factory class that instantiate the ISCCertificateProxyManager.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/08 12:44:58 $ Tag: $Name: $
 */
public class SCCertificateProxyManagerFactory {
	/**
	 * Default Constructor
	 */
	public SCCertificateProxyManagerFactory() {
	}

	/**
	 * Get the certificate proxy manager.
	 * @return ICertificateProxyManager - the certificate proxy manager
	 */
	public static ISCCertificateProxyManager getSCCertificateProxyManager() {
		return new SCCertificateProxyManagerImpl();
	}
}