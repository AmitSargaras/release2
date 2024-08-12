/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/accessprofile/bus/SBAccessProfileBusManagerBean.java,v 1.4 2005/09/28 07:36:19 lyng Exp $
 */
package com.integrosys.cms.app.accessprofile.bus;

import java.util.Collection;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * EJB session bean impl.
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.4 $
 * @since $Date: 2005/09/28 07:36:19 $ Tag: $Name: $
 */
public class SBAccessProfileBusManagerBean implements SessionBean, IAccessProfileBusManager {
	/**
	 * Checks whether the <code>action</code> and <code>event</code> are allowed
	 * according to the <code>roleType</code>.
	 * 
	 * @param action The action string.
	 * @param event The event string.
	 * @param roleType The roletype string.
	 * @return True if allowed, otherwise false.
	 */
	public boolean isFunctionAllowedByActionEventRole(String action, String event, long roleType) {
		Object[][] arr = AccessProfileUtils.getEvaluations(action, event, roleType);

		// There will be no treatment for the case of any action and a specific
		// event because an event is binded to an action. Identical event
		// names may have different meanings under different actions.

		// Also no treatment for the case of any action, any event, any user
		// to prevent careless mistakes. Goal is not to liberate access!
		// To achieve full liberation, use specific action + any event + any
		// role explicitly.

		IAccessProfileDAO dao = AccessProfileDAOFactory.getAccessProfileDAO();
		IAccessProfile[] authoArr = null;

		for (int i = 0; i < arr.length; i++) {
			authoArr = dao.getAccessProfiles((String) arr[i][0], (String) arr[i][1], ((Long) arr[i][2]).longValue());
			if (authoArr.length > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets a Colection of IAccessProfile objects.
	 * 
	 * @return a Collection of {@link IAccessProfile} objects
	 */
	public Collection getAccessProfileCollection() {
		IAccessProfileDAO dao = AccessProfileDAOFactory.getAccessProfileDAO();
		return dao.getAccessProfileCollection();
	}
	
	/**
	 * Set user last access time
	 * 
	 * @param loginID the user login ID
	 */
	public void setUserLastAccessTime(String loginID) {
		IAccessProfileDAO dao = AccessProfileDAOFactory.getAccessProfileDAO();
		dao.setUserLastAccessTime(loginID);
	}

	public void ejbCreate() {

	}

	public void setSessionContext(SessionContext sessionContext) throws EJBException {
	}

	public void ejbRemove() throws EJBException {
	}

	public void ejbActivate() throws EJBException {
	}

	public void ejbPassivate() throws EJBException {
	}
}
