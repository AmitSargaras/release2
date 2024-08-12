/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/proxy/CustodianProxyManagerFactory.java,v 1.2 2003/07/14 03:59:24 ravi Exp $
 */
package com.integrosys.cms.app.custodian.proxy;

/**
 * Factory class that instantiate the ICustodianProxyManager.
 * 
 * @author $Author: ravi $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/14 03:59:24 $ Tag: $Name: $
 */
public class CustodianProxyManagerFactory {
	/**
	 * Default Constructor
	 */
	public CustodianProxyManagerFactory() {
	}

	/**
	 * Get the custodian proxy manager.
	 * @return ICustodianProxyManager - the custodian proxy manager
	 */
	public static ICustodianProxyManager getCustodianProxyManager() {
		return new CustodianProxyManagerImpl();
	}
}