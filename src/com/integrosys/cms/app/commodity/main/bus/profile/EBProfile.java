/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/profile/EBProfile.java,v 1.2 2004/06/04 04:53:01 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.profile;

/**
 * Defines Profile methods for clients.
 *
 * @author  $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since   $Date: 2004/06/04 04:53:01 $
 * Tag:     $Name:  $
 */

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.commodity.main.CommodityException;

public interface EBProfile extends EJBObject {
	/**
	 * Retrieve an instance of a cc certificate
	 * @return IProfile - the object encapsulating the cc certificate info
	 * @throws RemoteException on remote errors
	 * @throws CommodityException - wrapper of any exceptions within.
	 */
	public IProfile getValue() throws CommodityException, RemoteException;

	/**
	 * Set the cc certificate object
	 * @param value - an object of IProfile
	 * @throws RemoteException
	 * @throws ConcurrentUpdateException thrown when more than one client
	 *         accessing the method same time.
	 */
	public void setValue(IProfile value) throws CommodityException, ConcurrentUpdateException,
			VersionMismatchException, RemoteException;

}