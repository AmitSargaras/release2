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
 * Remote home interface for EBGMRADealHome.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBGMRADealHome extends EJBHome {
	/**
	 * Called by the client to create a GMRA deal EJB object.
	 * 
	 * @param agreementID agreement ID
	 * @param gmraDeal of type IGMRADeal
	 * @return EBGMRADeal
	 * @throws CreateException on error while creating the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBGMRADeal create(long agreementID, IGMRADeal gmraDeal) throws CreateException, RemoteException;

	/**
	 * Find the GMRA deal ejb object by primary key, the CMS Deal ID.
	 * 
	 * @param dealIDPK CMS Deal ID
	 * @return EBGMRADeal
	 * @throws FinderException on error while finding the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBGMRADeal findByPrimaryKey(Long dealIDPK) throws FinderException, RemoteException;

	/**
	 * Find the GMRA deal ejb object by agreement ID, excluded specific status.
	 * 
	 * @param agreementType GMRA agreement type
	 * @param agreementID agreement ID
	 * @param excludeStatus the status to be excluded
	 * @return a collection of <code>EBGMRADeal</code>s
	 * @throws FinderException on error while finding the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public Collection findByAgreementID(String agreementType, long agreementID, String excludeStatus)
			throws FinderException, RemoteException;

	/**
	 * Find all GMRA Deal.
	 * 
	 * @return a collection of <code>EBGMRADeal</code>s
	 * @throws FinderException on error while finding the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public Collection findAll(String agreementType) throws FinderException, RemoteException;
}