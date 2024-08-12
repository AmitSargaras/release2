/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * Remote home interface for EBCashMargin.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBCashMarginHome extends EJBHome {
	/**
	 * Called by the client to create a cash margin ejb object.
	 * 
	 * @param cashMargin of type ICashMargin
	 * @return EBCashMargin
	 * @throws CreateException on error while creating the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBCashMargin create(long agreementID, ICashMargin cashMargin) throws CreateException, RemoteException;

	/**
	 * Find the cash margin ejb object by primary key, the cash margin ID.
	 * 
	 * @param cashMarginIDPK cash margin ID of type Long
	 * @return EBCashMargin
	 * @throws FinderException on error while finding the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBCashMargin findByPrimaryKey(Long cashMarginIDPK) throws FinderException, RemoteException;

	/**
	 * Find the cash margin ejb object by group ID.
	 * 
	 * @param groupID group ID of type long
	 * @return a collection of <code>EBCashMargin</code>s
	 * @throws FinderException on error while finding the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public Collection findByGroupID(long groupID) throws FinderException, RemoteException;

	/**
	 * Find all cash margin.
	 * 
	 * @return a collection of <code>EBCashMargin</code>s
	 * @throws FinderException on error while finding the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public Collection findAll() throws FinderException, RemoteException;

}