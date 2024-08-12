/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/EBCustodianDocHome.java,v 1.10 2005/02/22 10:19:24 wltan Exp $
 */
package com.integrosys.cms.app.custodian.bus;

//java
import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;

/**
 * Home interface for the custodian doc entity bean
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2005/02/22 10:19:24 $ Tag: $Name: $
 */

public interface EBCustodianDocHome extends EJBHome {
	/**
	 * Create a custodian doc
	 * @param anICustodianDoc - ICustodianDoc
	 * @return EBCustodianDoc - the local handler for the created custodian doc
	 * @throws CreateException if creation fails
	 */
	public EBCustodianDoc create(ICustodianDoc anICustodianDoc) throws CreateException, RemoteException;

	/**
	 * Find by primary Key, the custodian doc ID
	 * @param aPK - Long
	 * @return EBCustodianDoc - the local handler for the custodian doc that has
	 *         the PK as specified
	 * @throws FinderException
	 */
	public EBCustodianDoc findByPrimaryKey(Long aPK) throws FinderException, RemoteException;

	/**
	 * Find by collateral ID
	 * @param aCollateralID - Long
	 * @return Collection - the collection of local handlers for the custodian
	 *         doc that has the collateralID as specified
	 * @throws FinderException
	 */
	public Collection findByCollateralID(Long aCollateralID) throws FinderException, RemoteException;

	/**
	 * Find by checklist item ID
	 * @param aCheckListID of Long type
	 * @return EBCustodianDoc - the local handlers for the custodian doc with
	 *         the checklist ID
	 * @throws FinderException
	 */
	public EBCustodianDoc findByCheckList(Long aCheckListID) throws FinderException, RemoteException;

	/**
	 * Get the list of custodian doc that satisfy the search criteria
	 * @param aCustodianSearchCriteria - CustodianSearchCriteria
	 * @return SearchResult - contains the list of custodian doc that satisfy
	 *         the criteria
	 * @throws SearchDAOException
	 */
	public SearchResult getDocList(CustodianSearchCriteria aCustodianSearchCriteria) throws SearchDAOException,
			RemoteException;

	// public ICustodianDoc getNewCustodianDoc(long aCheckListItemID) throws
	// SearchDAOException;

	/**
	 * Get a new custodian doc given a checklist ID with no custodian items
	 * created.
	 * 
	 * @param checkListID - long
	 * @return ICustodianDoc
	 * @throws SearchDAOException
	 */
	public ICustodianDoc getNewDoc(long checkListID) throws SearchDAOException, RemoteException;

	/**
	 * Get the custodian trx ID given a set of search criteria
	 * 
	 * @param searchCriteria - CustodianSearchCriteria
	 * @return long - the custodian trx ID
	 * @return ICMSConstant.LONG_INVALID_VALUE if no such custodian trx found.
	 * @throws SearchDAOException
	 */
	public long getTrxID(CustodianSearchCriteria searchCriteria) throws SearchDAOException, RemoteException;
}