/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/proxy/GenerateRequestProxyManagerFactory.java,v 1.1 2003/09/11 05:49:40 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.proxy;

/**
 * Factory class that instantiate the IGenerateRequestProxyManager.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:49:40 $ Tag: $Name: $
 */
public class GenerateRequestProxyManagerFactory {
	/**
	 * Default Constructor
	 */
	public GenerateRequestProxyManagerFactory() {
	}

	/**
	 * Get the generate request proxy manager.
	 * @return IGenerateRequestProxyManager - the generate request proxy manager
	 */
	public static IGenerateRequestProxyManager getProxyManager() {
		return new GenerateRequestProxyManagerImpl();
	}
}