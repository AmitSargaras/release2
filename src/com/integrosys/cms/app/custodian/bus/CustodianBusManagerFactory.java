/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/CustodianBusManagerFactory.java,v 1.1 2003/06/09 11:38:02 hltan Exp $
 */
package com.integrosys.cms.app.custodian.bus;

/**
 * Factory class that instantiate the ICustodianBusManager.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/09 11:38:02 $ Tag: $Name: $
 */
public class CustodianBusManagerFactory {
	/**
	 * Default Constructor
	 */
	public CustodianBusManagerFactory() {
	}

	/**
	 * Get the custodian bus manager.
	 * @return ICustodianBusManager - the custodian bus manager
	 */
	public static ICustodianBusManager getCustodianBusManager() {
		return new CustodianBusManagerImpl();
	}
}