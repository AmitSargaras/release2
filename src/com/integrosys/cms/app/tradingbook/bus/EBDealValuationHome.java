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
 * Remote home interface for EBDealValuation.
 * 
 * @author $Author: Pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBDealValuationHome extends EJBHome {
	/**
	 * Called by the client to create a deal valuation EJB object.
	 * 
	 * @param isdaDeal of type IDealValuation
	 * @return EBDealValuation
	 * @throws CreateException on error while creating the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBDealValuation create(long cmsDealID, IDealValuation isdaDeal) throws CreateException, RemoteException;

	/**
	 * Find the deal valuation ejb object by primary key, the deal valuation ID.
	 * 
	 * @param dealIDPK deal valuation ID of type Long
	 * @return EBDealValuation
	 * @throws FinderException on error while finding the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBDealValuation findByPrimaryKey(Long dealIDPK) throws FinderException, RemoteException;

	/**
	 * Find the deal valuation ejb object by CMS deal ID.
	 * 
	 * @param cmsDealID CMS deal ID of type long
	 * @return EBDealValuation
	 * @throws FinderException on error while finding the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBDealValuation findByDealID(long cmsDealID) throws FinderException, RemoteException;

	/**
	 * Find the deal valuation ejb object by group ID, excluded specific status.
	 * 
	 * @param groupID the group ID of type long
	 * @param excludeStatus the status to be excluded of type String
	 * @return a collection of <code>EBDealValuation</code>s
	 * @throws FinderException on error while finding the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public Collection findByGroupID(long groupID, String excludeStatus) throws FinderException, RemoteException;

	/**
	 * Find all Deal Valuation.
	 * 
	 * @return a collection of <code>EBDealValuation</code>s
	 * @throws FinderException on error while finding the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public Collection findAll() throws FinderException, RemoteException;
}