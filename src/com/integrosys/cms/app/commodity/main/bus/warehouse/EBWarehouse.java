/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/warehouse/EBWarehouse.java,v 1.2 2004/06/04 04:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.warehouse;

/**
 * Defines EBWarehouse home methods for clients.
 *
 * @author  $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since   $Date: 2004/06/04 04:53:33 $
 * Tag:     $Name:  $
 */

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.commodity.main.CommodityException;

public interface EBWarehouse extends EJBObject {
	/**
	 * Retrieve an instance of a cc certificate
	 * @return IEBWarehouse - the object encapsulating the cc certificate info
	 * @throws RemoteException on remote errors
	 * @throws CommodityException - wrapper of any exceptions within.
	 */
	public IWarehouse getValue() throws CommodityException, RemoteException;

	/**
	 * Set the cc certificate object
	 * @param value - an object of IEBWarehouse
	 * @throws RemoteException
	 * @throws ConcurrentUpdateException thrown when more than one client
	 *         accessing the method same time.
	 */
	public void setValue(IWarehouse value) throws CommodityException, ConcurrentUpdateException,
			VersionMismatchException, RemoteException;

}