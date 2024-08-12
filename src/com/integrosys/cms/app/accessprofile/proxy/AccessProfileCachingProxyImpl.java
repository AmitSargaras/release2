/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/accessprofile/proxy/AccessProfileCachingProxyImpl.java,v 1.4 2005/10/27 06:28:24 lyng Exp $
 */
package com.integrosys.cms.app.accessprofile.proxy;

import java.util.Collection;
import java.util.Iterator;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.accessprofile.bus.AccessProfileBusManagerFactory;
import com.integrosys.cms.app.accessprofile.bus.AccessProfileException;
import com.integrosys.cms.app.accessprofile.bus.AccessProfileUtils;
import com.integrosys.cms.app.accessprofile.bus.IAccessProfile;
import com.integrosys.cms.app.accessprofile.bus.IAccessProfileBusManager;

/**
 * Grabs all access profile records on first invocation and caches them for
 * matching later on. No facility to purge cache yet.
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.4 $
 * @since $Date: 2005/10/27 06:28:24 $ Tag: $Name: $
 */
public class AccessProfileCachingProxyImpl implements IAccessProfileProxy {
	/**
	 * Contains a Collection of all access profile records. Will be generated
	 * the first time
	 * {@link #isFunctionAccessAllowed (String, String, long, String[], String[])}
	 * is invoked.
	 */
	private Collection accessProfileList = null;

	private Object accessProfileListMonitorObject = new Object();

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

		if (accessProfileList == null) {
			IAccessProfileBusManager mgr = AccessProfileBusManagerFactory.getAccessProfileBusManager();
			accessProfileList = mgr.getAccessProfileCollection();
		}
		Iterator iterator = accessProfileList.iterator();

 		while (iterator.hasNext()) {
			IAccessProfile fap = (IAccessProfile) iterator.next();
			if (AccessProfileUtils.isAccessAllowed(fap, action, event, roleType)) {
				return true;
			}
		}
		return false;
	}

	public void setUserLastAccessTime(String loginID) throws AccessProfileException {
		if (loginID != null) {
			IAccessProfileBusManager mgr = AccessProfileBusManagerFactory.getAccessProfileBusManager();
			mgr.setUserLastAccessTime(loginID);
		}

	}

	public void refreshFunctionAccess() {
		synchronized (accessProfileListMonitorObject) {
			if (accessProfileList != null) {
				accessProfileList.clear();
				IAccessProfileBusManager mgr = AccessProfileBusManagerFactory.getAccessProfileBusManager();
				accessProfileList = mgr.getAccessProfileCollection();
			}
		}
	}
}
