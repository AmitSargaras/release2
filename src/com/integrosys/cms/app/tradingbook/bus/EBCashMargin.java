/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;

/**
 * Remote interface to EBCashMarginBean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBCashMargin extends EJBObject {
	/**
	 * Get the cash margin business object.
	 * 
	 * @return cash margin object
	 * @throws RemoteException on error during remote method call
	 */
	public ICashMargin getValue() throws RemoteException;

	/**
	 * Set the cash margin to this entity.
	 * 
	 * @param cashMargin is of type ICashMargin
	 * @throws VersionMismatchException if the cash margin is invalid
	 * @throws RemoteException on error during remote method call
	 */
	public void setValue(ICashMargin cashMargin) throws VersionMismatchException, RemoteException;

	/**
	 * Set the NAP value for cash margin.
	 * 
	 * @param cashMargin of type ICashMargin
	 * @throws VersionMismatchException if the cash margin is invalid
	 * @throws RemoteException on error during remote method call
	 */
	public void setNAPValue(ICashMargin cashMargin) throws VersionMismatchException, RemoteException;

	/**
	 * Set the status to deleted for cash margin.
	 * 
	 * @param cashMargin of type ICashMargin
	 * @throws VersionMismatchException if the cash margin is invalid
	 * @throws RemoteException on error during remote method call
	 */
	public void setStatusDeleted(ICashMargin cashMargin) throws VersionMismatchException, RemoteException;

}