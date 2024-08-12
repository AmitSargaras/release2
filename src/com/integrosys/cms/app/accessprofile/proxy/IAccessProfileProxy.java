/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/accessprofile/proxy/IAccessProfileProxy.java,v 1.4 2005/10/27 06:27:00 lyng Exp $
 */
package com.integrosys.cms.app.accessprofile.proxy;

import com.integrosys.cms.app.accessprofile.bus.AccessProfileException;

/**
 * Represents an access profile proxy.
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.4 $
 * @since $Date: 2005/10/27 06:27:00 $ Tag: $Name: $
 */
public interface IAccessProfileProxy {
	/**
	 * Checks whether the action + event is allowed to be accessed by roletype.
	 * There will be some combinations generated for matching purposes.
	 * 
	 * @param action function action, cannot be <code>null</code> or empty
	 * @param event function event, cannot be <code>null</code> or empty
	 * @param roleType user role type
	 * @return true if function access is allowed, otherwise false
	 * @throws AccessProfileException on error checking for function access
	 *         protection
	 */
	public boolean isFunctionAccessAllowed(String action, String event, long roleType) throws AccessProfileException;

	public void setUserLastAccessTime(String loginID) throws AccessProfileException;

	/**
	 * Refresh function access from the persistence storage.
	 */
	public void refreshFunctionAccess();
}
