/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/EBCustodianDocLocalHome.java,v 1.2 2005/03/11 10:28:57 wltan Exp $
 */
package com.integrosys.cms.app.custodian.bus;

//java
import java.util.Collection;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelopeItem;

/**
 * Local home interface for the custodian doc entity bean
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/03/11 10:28:57 $ Tag: $Name: $
 */

public interface EBCustodianDocLocalHome extends EJBLocalHome {
	/**
	 * Create a custodian doc
	 * @param anICustodianDoc - ICustodianDoc
	 * @return EBCustodianDoc - the local handler for the created custodian doc
	 * @throws CreateException if creation fails
	 */
	public EBCustodianDocLocal create(ICustodianDoc anICustodianDoc) throws CreateException;

	/**
	 * Find by primary Key, the custodian doc ID
	 * @param aPK - Long
	 * @return EBCustodianDoc - the local handler for the custodian doc that has
	 *         the PK as specified
	 * @throws FinderException
	 */
	public EBCustodianDocLocal findByPrimaryKey(Long aPK) throws FinderException;

	/**
	 * Find by collateral ID
	 * @param aCollateralID - Long
	 * @return Collection - the collection of local handlers for the custodian
	 *         doc that has the collateralID as specified
	 * @throws FinderException
	 */
	public Collection findByCollateralID(Long aCollateralID) throws FinderException;

	/**
	 * Find by checklist ID
	 * @param aCheckListID of Long type
	 * @return EBCustodianDoc - the local handlers for the custodian doc with
	 *         the checklist ID
	 * @throws FinderException
	 */
	public EBCustodianDocLocal findByCheckList(Long aCheckListID) throws FinderException;

	/**
	 * Get the list of custodian doc that satisfy the search criteria
	 * @param aCustodianSearchCriteria - CustodianSearchCriteria
	 * @return SearchResult - contains the list of custodian doc that satisfy
	 *         the criteria
	 * @throws SearchDAOException
	 */
	public SearchResult getDocList(CustodianSearchCriteria aCustodianSearchCriteria) throws SearchDAOException;

    /**
	 * Get boolean to check whether envelope barcode exist
	 * @param long - limitprofile
     * @param String - envelope barcode
	 * @return boolean - return true/false on whether envelope barcode exist
	 * @throws SearchDAOException
	 */
	public boolean getCheckEnvelopeBarcodeExist(long limitprofile, String envBarcode) throws SearchDAOException;

    /**
	 * Get boolean to check whether document item barcode is unique
	 * @param String - document item barcode
	 * @return boolean - returning true/false on whether document item barcode exist
	 * @throws CustodianException
	 */
	public boolean getCheckDocItemBarcodeExist(String envBarcode, long checkListItemRefID) throws SearchDAOException;   

	// public ICustodianDoc getNewCustodianDoc(long aCheckListItemID) throws
	// SearchDAOException;

    /**
	 * Get ISecEnvelopeItem to with location base on barcode
	 * @param String - document item barcode
	 * @return ISecEnvelopeItem - detail of OBSecEnvelopeItem
	 * @throws CustodianException
	 */
    public ISecEnvelopeItem getSecEnvItemLoc(String limitprofile) throws SearchDAOException;

	/**
	 * Get a new custodian doc given a checklist ID with no custodian items
	 * created.
	 * 
	 * @param checkListID - long
	 * @return ICustodianDoc
	 * @throws SearchDAOException
	 */
	public ICustodianDoc getNewDoc(long checkListID) throws SearchDAOException;

	/**
	 * Get a list of new custodian doc items not yet created given a custodian
	 * doc ID.
	 * 
	 * @param custodianDocID - long
	 * @return ICustodianDocItem[]
	 * @throws SearchDAOException
	 */
	public ICustodianDocItem[] getNewItems(long custodianDocID) throws SearchDAOException;

	/**
	 * Get the custodian trx ID given a set of search criteria
	 * 
	 * @param searchCriteria - CustodianSearchCriteria
	 * @return long - the custodian trx ID
	 * @return ICMSConstant.LONG_INVALID_VALUE if no such custodian trx found.
	 * @throws SearchDAOException
	 */
	public long getTrxID(CustodianSearchCriteria searchCriteria) throws SearchDAOException;
}