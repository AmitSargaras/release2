/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.businfra.currency.Amount;

/**
 * Remote interface to EBISDACSADeal.
 * 
 * @author $Author: Pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBISDACSADeal extends EJBObject {
	/**
	 * Get the ISDA CSA Deal business object.
	 * 
	 * @return IISDACSADeal
	 * @throws RemoteException on error during remote method call
	 */
	public IISDACSADeal getValue() throws RemoteException;

	/**
	 * Set the ISDA CSA Deal to this entity.
	 * 
	 * @param isdaDeal is of type IISDACSADeal
	 * @throws VersionMismatchException if the ISDA CSA Deal is invalid
	 * @throws RemoteException on error during remote method call
	 */
	public void setValue(IISDACSADeal isdaDeal) throws VersionMismatchException, RemoteException;

	/**
	 * Set the NPV base value for ISDA CSA Deal.
	 * 
	 * @param isdaDeal of type IISDACSADeal
	 * @throws VersionMismatchException if the ISDA CSA Deal is invalid
	 * @throws RemoteException on error during remote method call
	 */
	public void setNPVBaseValue(IDealValuation isdaDeal, Amount NPVRefAmt) throws VersionMismatchException,
			RemoteException;
}