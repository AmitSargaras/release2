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
 * Remote home interface for EBISDACSADeal.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBISDACSADealHome extends EJBHome {
	/**
	 * Called by the client to create an ISDA CSA deal EJB object.
	 * 
	 * @param isdaDeal of type IISDACSADeal
	 * @return EBISDACSADeal
	 * @throws CreateException on error while creating the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBISDACSADeal create(IISDACSADeal isdaDeal) throws CreateException, RemoteException;

	/**
	 * Find the local ejb object by primary key, the CMS Deal ID.
	 * 
	 * @param dealIDPK CMS Deal ID
	 * @return EBISDACSADeal
	 * @throws FinderException on error while finding the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBISDACSADeal findByPrimaryKey(Long dealIDPK) throws FinderException, RemoteException;

	/**
	 * Find the ISDC CSA deal ejb object by agreement ID, excluded specific
	 * status.
	 * 
	 * @param agreementType GMRA agreement type
	 * @param agreementID agreement ID
	 * @param excludeStatus the status to be excluded
	 * @return a collection of <code>EBISDACSADeal</code>s
	 * @throws FinderException on error while finding the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public Collection findByAgreementID(String agreementType, long agreementID, String excludeStatus)
			throws FinderException, RemoteException;

	/**
	 * Find all ISDA CSA Deal.
	 * 
	 * @return a collection of <code>EBISDACSADeal</code>s
	 * @throws FinderException on error while finding the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public Collection findAll(String agreementType) throws FinderException, RemoteException;
}