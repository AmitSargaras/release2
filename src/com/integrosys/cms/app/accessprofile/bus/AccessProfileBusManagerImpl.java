/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/accessprofile/bus/AccessProfileBusManagerImpl.java,v 1.5 2005/09/28 07:36:19 lyng Exp $
 */
package com.integrosys.cms.app.accessprofile.bus;

import java.rmi.RemoteException;
import java.util.Collection;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * @author $Author: lyng $
 * @version $Revision: 1.5 $
 * @since $Date: 2005/09/28 07:36:19 $ Tag: $Name: $
 */
public class AccessProfileBusManagerImpl implements IAccessProfileBusManager {
	/**
	 * Checks whether the <code>action</code> and <code>event</code> are allowed
	 * according to the <code>roleType</code>.
	 * 
	 * @param action The action string.
	 * @param event The event string.
	 * @param roleType The roletype string.
	 * @return True if allowed, otherwise false.
	 * @throws AccessProfileException
	 */
	public boolean isFunctionAllowedByActionEventRole(String action, String event, long roleType)
			throws AccessProfileException {
		try {
			return getAccessProfileBusManagerEjb().isFunctionAllowedByActionEventRole(action, event, roleType);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new AccessProfileException("caught remote exception", e);
		}
	}

	/**
	 * Gets a Colection of IAccessProfile objects.
	 * 
	 * @return a Collection of {@link IAccessProfile} objects
	 * @throws AccessProfileException on error getting access profile
	 */
	public Collection getAccessProfileCollection() throws AccessProfileException {
		try {
			return getAccessProfileBusManagerEjb().getAccessProfileCollection();
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new AccessProfileException("Caught Remote Exception", e);
		}
	}
	
	public void setUserLastAccessTime(String loginID) throws AccessProfileException {
		try {
			getAccessProfileBusManagerEjb().setUserLastAccessTime(loginID);
		} catch (RemoteException e) {
			throw new AccessProfileException ("Caught Remote Exception", e);
		}
	}

	private SBAccessProfileBusManager getAccessProfileBusManagerEjb() {
		return (SBAccessProfileBusManager) BeanController.getEJB(ICMSJNDIConstant.SB_ACCESS_PROFILE_JNDI,
				SBAccessProfileBusManagerHome.class.getName());
	}
}
