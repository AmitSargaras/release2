/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/proxy/DDNProxyManagerFactory.java,v 1.1 2003/08/13 11:27:58 hltan Exp $
 */
package com.integrosys.cms.app.ddn.proxy;

/**
 * Factory class that instantiate the IDDNProxyManager.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/13 11:27:58 $ Tag: $Name: $
 */
public class DDNProxyManagerFactory {
	/**
	 * Default Constructor
	 */
	public DDNProxyManagerFactory() {
	}

	/**
	 * Get the ddn proxy manager.
	 * @return IDDNProxyManager - the ddn proxy manager
	 */
	public static IDDNProxyManager getDDNProxyManager() {
		return new DDNProxyManagerImpl();
	}
}