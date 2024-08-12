/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/accessprofile/bus/SBAccessProfileBusManager.java,v 1.4 2005/09/28 07:36:19 lyng Exp $
 */
package com.integrosys.cms.app.accessprofile.bus;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.EJBObject;

/**
 * EJB session bean remote interface.
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.4 $
 * @since $Date: 2005/09/28 07:36:19 $ Tag: $Name: $
 */
public interface SBAccessProfileBusManager extends EJBObject {

	public boolean isFunctionAllowedByActionEventRole(String action, String event, long roleType)
			throws RemoteException;

	/**
	 * Gets a Colection of IAccessProfile objects.
	 * 
	 * @return a Collection of {@link IAccessProfile} objects
	 * @throws RemoteException on any unexpected exception during remote method
	 *         call
	 */
	public Collection getAccessProfileCollection() throws RemoteException;
	
	public void setUserLastAccessTime(String loginID) throws RemoteException;
}
