/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/accessprofile/bus/AccessProfileBusManagerFactory.java,v 1.1 2003/10/20 11:04:13 btchng Exp $
 */
package com.integrosys.cms.app.accessprofile.bus;

/**
 * Factory for producing access profile managers.
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/10/20 11:04:13 $ Tag: $Name: $
 */
public class AccessProfileBusManagerFactory {

	/**
	 * Gets the access profile BUS manager.
	 * @return
	 */
	public static IAccessProfileBusManager getAccessProfileBusManager() {

		return new AccessProfileBusManagerImpl();
	}

}
