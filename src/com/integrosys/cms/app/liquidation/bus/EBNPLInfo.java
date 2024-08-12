/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;

/**
 * Remote interface to EBNPLInfo.
 * 
 * @author $Author: lini $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBNPLInfo extends EJBObject {
	/**
	 * Get the NPL Info business object.
	 * 
	 * @return NPL Info object
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public INPLInfo getValue() throws RemoteException;

	/**
	 * Set the NPL info to this entity.
	 * 
	 * @param nPLInfo is of type INPLInfo
	 * @throws com.integrosys.base.businfra.common.exception.VersionMismatchException
	 *         if the NPLInfo is invalid
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public void setValue(INPLInfo nPLInfo) throws VersionMismatchException, RemoteException;

}