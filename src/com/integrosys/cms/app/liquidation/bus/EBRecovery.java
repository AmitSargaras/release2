/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;

/**
 * Remote interface to EBRecoveryBean.
 * 
 * @author $Author: lini $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBRecovery extends EJBObject {
	/**
	 * Get the Recovery business object.
	 * 
	 * @return Recovery object
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public IRecovery getValue() throws RemoteException;

	/**
	 * Set the Recovery to this entity.
	 * 
	 * @param recovery is of type IRecovery
	 * @throws com.integrosys.base.businfra.common.exception.VersionMismatchException
	 *         if the NPLInfo is invalid
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public void setValue(IRecovery recovery) throws VersionMismatchException, RemoteException;

	public void setStatus(String status) throws RemoteException;
}