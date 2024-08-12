/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.bus;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * Remote home interface for EBInterestRate.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBInterestRateHome extends EJBHome {
	/**
	 * Called by the client to create an local EJB object.
	 * 
	 * @param intRate of type IInterestRate
	 * @return interest rate ejb object
	 * @throws CreateException on error while creating the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBInterestRate create(IInterestRate intRate) throws CreateException, RemoteException;

	/**
	 * Find the local ejb object by primary key, the interest rate ID.
	 * 
	 * @param intRateID interest rate ID
	 * @return local interestrate subtype ejb object
	 * @throws FinderException on error while finding the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBInterestRate findByPrimaryKey(Long intRateID) throws FinderException, RemoteException;

	/**
	 * Find all interest rate.
	 * 
	 * @return a collection of <code>EBInterestRate</code>s
	 * @throws FinderException on error finding interest rate
	 * @throws RemoteException on error during remote method call
	 */
	public Collection findAll() throws FinderException, RemoteException;

	/**
	 * Find interest rate its group id.
	 * 
	 * @param groupID group id
	 * @return a collection of <code>EBInterestRate</code>s
	 * @throws FinderException on error finding the interest rate
	 * @throws RemoteException on error during remote method call
	 */
	public Collection findByGroupID(long groupID) throws FinderException, RemoteException;
}