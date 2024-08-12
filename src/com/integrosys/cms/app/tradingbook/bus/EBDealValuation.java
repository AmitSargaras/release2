/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;

/**
 * Remote interface to EBDealValuationBean.
 * 
 * @author $Author: Pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBDealValuation extends EJBObject {
	/**
	 * Get the deal valuation business object.
	 * 
	 * @return IDealValuation
	 * @throws RemoteException on error during remote method call
	 */
	public IDealValuation getValue() throws RemoteException;

	/**
	 * Set the deal valuation to this entity.
	 * 
	 * @param dealVal is of type IDealValuation
	 * @throws VersionMismatchException if the deal valuation is invalid
	 * @throws RemoteException on error during remote method call
	 */
	public void setValue(IDealValuation dealVal) throws VersionMismatchException, RemoteException;

	/**
	 * Set the market value for deal valuation.
	 * 
	 * @param dealVal of type IDealValuation
	 * @throws VersionMismatchException if the deal valuation is invalid
	 * @throws RemoteException on error during remote method call
	 */
	public void setDealMarketValue(IDealValuation dealVal) throws VersionMismatchException, RemoteException;

	/**
	 * Set the group ID for deal valuation.
	 * 
	 * @param dealVal of type IDealValuation
	 * @throws VersionMismatchException if the deal valuation is invalid
	 * @throws RemoteException on error during remote method call
	 */
	public void setGroupIDValue(IDealValuation dealVal) throws VersionMismatchException, RemoteException;

	/**
	 * Set the status to Deleted for deal valuation.
	 * 
	 * @param dealVal of type IDealValuation
	 * @throws VersionMismatchException if the deal valuation is invalid
	 * @throws RemoteException on error during remote method call
	 */
	public void setStatusDeleted(IDealValuation dealVal) throws VersionMismatchException, RemoteException;

}