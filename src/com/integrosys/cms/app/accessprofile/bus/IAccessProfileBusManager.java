/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/accessprofile/bus/IAccessProfileBusManager.java,v 1.4 2005/09/28 07:36:19 lyng Exp $
 */
package com.integrosys.cms.app.accessprofile.bus;

import java.util.Collection;

/**
 * Contains the access profile logic.
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.4 $
 * @since $Date: 2005/09/28 07:36:19 $ Tag: $Name: $
 */
public interface IAccessProfileBusManager {
	/**
	 * Checks whether the <code>action</code> and <code>event</code> are allowed
	 * according to the <code>roleType</code>.
	 * 
	 * @param action The action string.
	 * @param event The event string.
	 * @param roleType The roletype string.
	 * @return True if allowed, otherwise false.
	 * @throws AccessProfileException on any errors encountered
	 */
	public boolean isFunctionAllowedByActionEventRole(String action, String event, long roleType)
			throws AccessProfileException;

	/**
	 * Gets a Colection of IAccessProfile objects.
	 * 
	 * @return a Collection of {@link IAccessProfile} objects
	 * @throws AccessProfileException on any errors encountered
	 */
	public Collection getAccessProfileCollection() throws AccessProfileException;
	
	/**
	 * Set user last access time
	 * 
	 * @param loginID the user login ID
	 */	
	public void setUserLastAccessTime(String loginID) throws AccessProfileException;
}
