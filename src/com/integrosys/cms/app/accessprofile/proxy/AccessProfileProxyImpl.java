/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/accessprofile/proxy/AccessProfileProxyImpl.java,v 1.4 2005/10/27 06:27:50 lyng Exp $
 */
package com.integrosys.cms.app.accessprofile.proxy;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.accessprofile.bus.AccessProfileBusManagerFactory;
import com.integrosys.cms.app.accessprofile.bus.AccessProfileException;
import com.integrosys.cms.app.accessprofile.bus.IAccessProfileBusManager;

/**
 * Default impl, using the EJB session bean access profile manager. Queries the
 * database actively.
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.4 $
 * @since $Date: 2005/10/27 06:27:50 $ Tag: $Name: $
 */
public class AccessProfileProxyImpl implements IAccessProfileProxy {
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
	public boolean isFunctionAccessAllowed(String action, String event, long roleType) throws AccessProfileException {
		DefaultLogger.info(this, "action=" + action + " event=" + event + " roleType=" + roleType);
		IAccessProfileBusManager mgr = AccessProfileBusManagerFactory.getAccessProfileBusManager();
		return mgr.isFunctionAllowedByActionEventRole(action, event, roleType);
	}

	public void setUserLastAccessTime(String loginID) throws AccessProfileException {
		IAccessProfileBusManager mgr = AccessProfileBusManagerFactory.getAccessProfileBusManager();
		mgr.setUserLastAccessTime(loginID);
	}

	public void refreshFunctionAccess() {
		// nothing to be done as this class implementation always retrieved from
		// the backend
	}
}
