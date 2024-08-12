/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/SBCustodianBusManager.java,v 1.17 2005/03/11 10:28:57 wltan Exp $
 */
package com.integrosys.cms.app.custodian.bus;

//java
import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.EJBObject;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelopeItem;

/**
 * Session bean remote interface for the services provided by the custodian bus
 * manager
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.17 $
 * @since $Date: 2005/03/11 10:28:57 $ Tag: $Name: $
 */
public interface SBCustodianBusManager extends EJBObject {
	/**
	 * Create a custodian document in the custodian registry.
	 * @param anICustodianDoc - ICustodianDoc
	 * @return ICustodianDoc - the created custodian doc
	 * @throws CustodianException
	 * @throws RemoteException
	 */
	public ICustodianDoc create(ICustodianDoc anICustodianDoc) throws CustodianException, RemoteException;

	/**
	 * Update a custodian that is already in the custodian registry.
	 * @param anICustodianDoc - ICustodianDoc
	 * @return ICustodianDoc - the updated custodian doc
	 * @throws ConcurrentUpdateException
	 * @throws CustodianException
	 * @throws RemoteException
	 */
	public ICustodianDoc update(ICustodianDoc anICustodianDoc) throws ConcurrentUpdateException, CustodianException,
			RemoteException;

	/**
	 * Delete a custodian document from the custodian registry.
	 * @param aCustodianDocID - long
	 * @throws ConcurrentUpdateException
	 * @throws CustodianException
	 * @throws RemoteException
	 */
	public void delete(long aCustodianDocID) throws ConcurrentUpdateException, CustodianException, RemoteException;

	/**
	 * Retrieve a custodian document from the custodian registry.
	 * @param aCustodianID - long
	 * @return ICustodianDoc - the object encapsulating the custodian document
	 *         info
	 * @throws CustodianException
	 * @throws RemoteException
	 */
	public ICustodianDoc getCustodianDoc(long aCustodianID) throws CustodianException, RemoteException;

	/**
	 * Retrieve a list of custodian documents from the custodian registry.
	 * @param aCustodianSearchCriteria - CustodianSearchCriteria
	 * @return SearchResult - contains the list of custodian document retrieved
	 *         based on the search criteria
	 * @throws CustodianException
	 * @throws RemoteException
	 */
	public SearchResult getCustodianDocList(CustodianSearchCriteria aCustodianSearchCriteria)
			throws CustodianException, RemoteException;

   /**
	 * Get boolean to check whether envelope barcode exist
	 * @param long - limitprofile
     * @param String - envelope barcode
	 * @return boolean - return true/false on whether envelope barcode exist
	 * @throws CustodianException
	 */
	public boolean getCheckEnvelopeBarcodeExist(long limitprofile, String envBarcode)
			throws CustodianException, RemoteException;

   /**
	 * Get boolean to check whether document item barcode is unique
	 * @param String - document item barcode
	 * @return boolean - returning true/false on whether document item barcode exist
	 * @throws CustodianException
	 */
	public boolean getCheckDocItemBarcodeExist(String docItemBarcode, long checkListItemRefID)
			throws CustodianException, RemoteException;

    /**
	 * Get ISecEnvelopeItem to with location base on barcode
	 * @param String - document item barcode
	 * @return ISecEnvelopeItem - detail of OBSecEnvelopeItem
	 * @throws CustodianException
	 */
    public ISecEnvelopeItem getSecEnvItemLoc(String docItemBarcode)
			throws CustodianException, RemoteException;

	/**
	 * Persist custodian print authorization details...
	 * @param custAuthz - OBCustAuthorize[]
	 * @throws CustodianException if errors
	 * @throws RemoteException
	 */
	public void persistPrintAuthzDetails(OBCustAuthorize[] custAuthz) throws CustodianException, RemoteException;

	/**
	 * Persist custodian print authorization details...
	 * @param custodianDocIds - String[]
	 * @return IMemo
	 * @throws CustodianException if errors
	 * @throws RemoteException
	 */
	// public IMemo getPrintMemo(ICMSCustomer customer, Long[] custodianDocIds)
	// throws CustodianException, RemoteException;
	/**
	 * Get the list of custodian doc by collateral ID
	 * @param aCollateralID - long
	 * @return ICustodianDoc[] - the list of custodian doc. Null if no custodian
	 *         doc found
	 * @throws CustodianException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICustodianDoc[] getCustodianDocsByCollateralID(long aCollateralID) throws CustodianException,
			RemoteException;

	/**
	 * Get the custodian doc by checklist item ID
	 * @param aCheckListID of long type
	 * @return ICustodianDoc - the custodian doc with the checklist ID
	 * @throws CustodianException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICustodianDoc getCustodianDocByCheckList(long aCheckListID) throws CustodianException, RemoteException, FinderException;

	public ICustodianDoc setOwnerInfo(ICustodianDoc anICustodianDoc) throws CustodianException, RemoteException;

	// public ICustodianDoc getNewCustodianDoc(long aCheckListItemID) throws
	// CustodianException, RemoteException;

	/**
	 * Get a new custodian doc given a checklist ID with no custodian items
	 * created.
	 * 
	 * @param checkListID - long
	 * @return ICustodianDoc
	 * @throws CustodianException
	 * @throws RemoteException on remote errors
	 */
	public ICustodianDoc getNewDoc(long checkListID) throws CustodianException, RemoteException;

	/**
	 * Get a list of new custodian doc items not yet created given a custodian
	 * doc ID.
	 * 
	 * @param custodianDocID - long
	 * @return ICustodianDocItem[]
	 * @throws CustodianException
	 * @throws RemoteException on remote errors
	 */
	public ICustodianDocItem[] getNewItems(long custodianDocID) throws CustodianException, RemoteException;

	/**
	 * Get the custodian trx ID given a set of search criteria
	 * 
	 * @param searchCriteria - CustodianSearchCriteria
	 * @return long - the custodian trx ID
	 * @return ICMSConstant.LONG_INVALID_VALUE if no such custodian trx found.
	 * @throws RemoteException on remote errors
	 */
	public long getTrxID(CustodianSearchCriteria searchCriteria) throws CustodianException, RemoteException;
}
