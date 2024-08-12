/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;

/**
 * Remote interface to EBLiquidationBean.
 * 
 * @author $Author: Siew Kheat $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBLiquidation extends EJBObject {

	/**
	 * Get the ILiquidation business object.
	 * 
	 * @return Liquidation object
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public ILiquidation getValue() throws RemoteException;

	/**
	 * Set the Liquidation to this entity.
	 * 
	 * @param liquidation is of type ILiquidation
	 * @throws com.integrosys.base.businfra.common.exception.VersionMismatchException
	 *         if the ILiquidation is invalid
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public void setValue(ILiquidation liquidation) throws VersionMismatchException, RemoteException;
}
