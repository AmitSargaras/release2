/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;

/**
 * Remote interface to EBGMRADeal.
 * 
 * @author $Author: Pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBGMRADeal extends EJBObject {
	/**
	 * Get the GMRA Deal business object.
	 * 
	 * @return IGMRADeal
	 * @throws RemoteException on error during remote method call
	 */
	public IGMRADeal getValue() throws RemoteException;

	/**
	 * Set the GMRA Deal to this entity.
	 * 
	 * @param gmraDeal is of type IGMRADeal
	 * @throws VersionMismatchException if the GMRA Deal is invalid
	 * @throws RemoteException on error during remote method call
	 */
	public void setValue(IGMRADeal gmraDeal) throws VersionMismatchException, RemoteException;

	/**
	 * Set the status to deleted for GMRA Deal.
	 * 
	 * @param gmraDeal of type IGMRADeal
	 * @throws VersionMismatchException if the GMRA Deal is invalid
	 * @throws RemoteException on error during remote method call
	 */
	public void setStatusDeleted(IGMRADeal gmraDeal) throws VersionMismatchException, RemoteException;

	/**
	 * Set the market price to NPV base value for GMRA Deal.
	 * 
	 * @param dealVal of type IDealValuation
	 * @throws VersionMismatchException if the GMRA Deal is invalid
	 * @throws RemoteException on error during remote method call
	 */
	public void setNPVBaseValue(IDealValuation dealVal) throws VersionMismatchException, RemoteException;

}