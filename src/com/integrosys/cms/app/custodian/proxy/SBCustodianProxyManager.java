/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/proxy/SBCustodianProxyManager.java,v 1.36 2005/10/14 02:27:05 whuang Exp $
 */
package com.integrosys.cms.app.custodian.proxy;

//java
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJBObject;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.cms.app.custodian.bus.CustodianException;
import com.integrosys.cms.app.custodian.bus.CustodianSearchCriteria;
import com.integrosys.cms.app.custodian.bus.ICustodianDoc;
import com.integrosys.cms.app.custodian.bus.IMemo;
import com.integrosys.cms.app.custodian.bus.OBCustAuthorize;
import com.integrosys.cms.app.custodian.trx.ICustodianTrxValue;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelopeItem;

/**
 * Session bean remote interface for the services provided by the custodian
 * proxy manager
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.36 $
 * @since $Date: 2005/10/14 02:27:05 $ Tag: $Name: $
 */
public interface SBCustodianProxyManager extends EJBObject {
	/**
	 * Create a custodian doc as draft
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianDoc - ICustodianDoc
	 * @return ICustodianDoc - the custodian doc that has been saved as draft
	 * @throws CustodianException if errors
	 * @throws RemoteException
	 */
	public ICustodianDoc createDraftCustodianDoc(ITrxContext anITrxContext, ICustodianDoc anICustodianDoc)
			throws CustodianException, RemoteException;

	/**
	 * Receive the custodian and submit the draft
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the trx containing the draft custodian doc
	 *         submit
	 * @throws CustodianException if errors
	 * @throws RemoteException
	 */
	public ICustodianTrxValue receiveDraftCustodianDoc(ITrxContext anITrxContext,
			ICustodianTrxValue anICustodianTrxValue) throws CustodianException, RemoteException;

	/**
	 * Maker Create a borrower custodian doc that is not in a checkList manually
	 * @param anITrxContext - long
	 * @param anICustodianDoc - long
	 * @param anICustodianDoc - ICustodianDoc
	 * @return ICustodianDoc - the biz interface containing the info of the
	 *         custodian doc created
	 * @throws CustodianException
	 * @throws RemoteException
	 */
	public ICustodianDoc createDocMaker(ITrxContext anITrxContext, ICustodianDoc anICustodianDoc, String borrowerTrxId)
			throws CustodianException, RemoteException;

	/**
	 * Checker Approve the creation of a borrower custodian doc that is created
	 * manually
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the trx interface containing the info of the
	 *         custodian doc created
	 * @throws CustodianException
	 * @throws RemoteException
	 */
	public ICustodianTrxValue createDocChecker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException, RemoteException;

	/**
	 * Create a borrower custodian doc either manually or thru' checkList
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianDoc - ICustodianDoc
	 * @return ICustodianDoc - the biz interface containing the info of the
	 *         custodian doc created
	 * @throws CustodianException
	 * @throws RemoteException
	 */
	public ICustodianDoc createDocByBorrower(ITrxContext anITrxContext, ICustodianDoc anICustodianDoc)
			throws CustodianException, RemoteException;

	/**
	 * Create a collateral custodian doc thru' checklist
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianDoc - ICustodianDoc
	 * @return ICustodianDoc - the biz object containing the info of the
	 *         custodian doc created
	 * @throws CustodianException
	 * @throws RemoteException
	 */
	public ICustodianDoc createDocByCollateral(ITrxContext anITrxContext, ICustodianDoc anICustodianDoc)
			throws CustodianException, RemoteException;

	/**
	 * Maker Lodge a custodian doc
	 * @param anITrxContext - ITrxContext
	 * @return OBCustodianDoc - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 * @throws RemoteException
	 */
	public ICustodianDoc lodgeDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException, RemoteException;

	/**
	 * Maker Lodge a custodian doc
	 * @param anITrxContext of ITrxContext type
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianDocTrxValue - the custodian doc trx that is to be
	 *         lodged
	 * @throws CustodianException if errors
	 * @throws RemoteException
	 */
	public ICustodianTrxValue lodgeDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue,
			ICustodianDoc anICustodianDoc) throws CustodianException, RemoteException;

	/**
	 * Checker approve lodgement of a custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 * @throws RemoteException
	 */
	public ICustodianTrxValue lodgeDocChecker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException, RemoteException;

	/*
	 * Maker Update a custodian doc. This will cater to lodge, maintain, temp
	 * uplift, relodge and perm uplift use case
	 * 
	 * @param anITrxContext - ITrxContext
	 * 
	 * @param anICustodianDoc - ICustodianDoc
	 * 
	 * @return ICustodianDoc - the biz interace containing the info of the
	 * custodian doc updated
	 * 
	 * @throws CustodianException
	 * 
	 * @throws RemoteException
	 */
	/*
	 * public ICustodianDoc updateDocMaker(ITrxContext anITrxContext,
	 * ICustodianDoc anICustodianDoc) throws CustodianException,
	 * RemoteException;
	 */
	/**
	 * Checker Approve updating a custodian doc. This will cater to the maintain
	 * custodian doc use case
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return OBCustodianTrxValue - the trx object containing the info of the
	 *         custodian doc updated
	 * @throws CustodianException
	 * @throws RemoteException
	 */
	public ICustodianTrxValue updateDocChecker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException, RemoteException;



    public SearchResult getPendingReversalList(long aLimitProfileID) throws CustodianException, RemoteException;

    public SearchResult getPendingReversalList(ITrxContext aTrxContext, long aLimitProfileID)
              throws CustodianException, RemoteException;

	public SearchResult getPendingReversalListForNonBorrower(long aCustomerID) throws CustodianException,
			RemoteException;

	public SearchResult getPendingReversalListForNonBorrower(ITrxContext aTrxContext, long aCustomerID)
			throws CustodianException, RemoteException;

	public SearchResult getPendingReversalListForNonBorrower(long aLimitProfileID, long aCustomerID)
			throws CustodianException, RemoteException;


	public SearchResult getPendingReversalListForNonBorrower(ITrxContext aTrxContext, long aLimitProfileID,
			long aCustomerID) throws CustodianException, RemoteException;


    /**
	 * Get the list of custodian doc that are pending lodgement for lodgement
	 * memo printing
	 * @param aLimitProfileID - the limit profile ID
	 * @return SearchResult - the list of custodian doc that is pending
	 *         lodgement
	 * @throws CustodianException
	 * @throws RemoteException
	 */
	public SearchResult getPendingLodgementList(long aLimitProfileID) throws CustodianException, RemoteException;

	/**
	 * Get the list of custodian doc that are pending lodgement for lodgement
	 * memo printing
	 * @param aTrxContext - the transaction context
	 * @param aLimitProfileID - the limit profile ID
	 * @return ICustodianDoc[] - the list of custodian doc that is pending
	 *         lodgement
	 * @throws CustodianException
	 * @throws RemoteException
	 */
	public SearchResult getPendingLodgementList(ITrxContext aTrxContext, long aLimitProfileID)
			throws CustodianException, RemoteException;

	/**
	 * Get the list of custodian doc that are pending lodgement for lodgement
	 * memo printing
	 * @param aCustomerID - the customer identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         lodgement
	 * @throws CustodianException
	 * @throws RemoteException
	 */
	public SearchResult getPendingLodgementListForNonBorrower(long aCustomerID) throws CustodianException,
			RemoteException;

	/**
	 * Get the list of custodian doc that are pending lodgement for lodgement
	 * memo printing
	 * @param aTrxContext - the transaction context
	 * @param aCustomerID - the customer identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         lodgement
	 * @throws CustodianException
	 * @throws RemoteException
	 */
	public SearchResult getPendingLodgementListForNonBorrower(ITrxContext aTrxContext, long aCustomerID)
			throws CustodianException, RemoteException;

	/**
	 * Get the list of custodian doc that are pending lodgement for lodgement
	 * memo printing This will take into consideration those deleted checklist
	 * as well
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @return SearchResult - the list of custodian docs that are pending
	 *         lodgement
	 * @throws CustodianException on errors
	 * @throws RemoteException on remote errors
	 */
	public SearchResult getPendingLodgementListForNonBorrower(long aLimitProfileID, long aCustomerID)
			throws CustodianException, RemoteException;

	/**
	 * Get the list of custodian doc that are pending lodgement for lodgement
	 * memo printing This will take into consideration those deleted checklist
	 * as well
	 * @param aTrxContext the transaction context
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @return SearchResult - the list of custodian docs that are pending
	 *         lodgement
	 * @throws CustodianException on errors
	 * @throws RemoteException on remote errors
	 */
	public SearchResult getPendingLodgementListForNonBorrower(ITrxContext aTrxContext, long aLimitProfileID,
			long aCustomerID) throws CustodianException, RemoteException;

	/**
	 * Get the list of custodian doc that are pending withdrawal for withdrawal
	 * memo printing
	 * @param aLimitProfileID the limit profile identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         withdrawal
	 * @throws CustodianException
	 * @throws RemoteException
	 */
	public SearchResult getPendingWithdrawalList(long aLimitProfileID) throws CustodianException, RemoteException;

	/**
	 * Get the list of custodian doc that are pending withdrawal for withdrawal
	 * memo printing
	 * @param aTrxContext the transaction context
	 * @param aLimitProfileID the limit profile identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         withdrawal
	 * @throws CustodianException
	 * @throws RemoteException
	 */
	public SearchResult getPendingWithdrawalList(ITrxContext aTrxContext, long aLimitProfileID)
			throws CustodianException, RemoteException;

	/**
	 * Get the list of custodian doc that are pending withdrawal for withdrawal
	 * memo printing
	 * @param aCustomerID the customer identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         withdrawal
	 * @throws CustodianException
	 * @throws RemoteException
	 */
	public SearchResult getPendingWithdrawalListForNonBorrower(long aCustomerID) throws CustodianException,
			RemoteException;

	/**
	 * Get the list of custodian doc that are pending withdrawal for withdrawal
	 * memo printing
	 * @param aTrxContext the transaction context
	 * @param aCustomerID the customer identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         withdrawal
	 * @throws CustodianException
	 * @throws RemoteException
	 */
	public SearchResult getPendingWithdrawalListForNonBorrower(ITrxContext aTrxContext, long aCustomerID)
			throws CustodianException, RemoteException;

	/**
	 * Get the list of custodian doc that are pending withdrawal memo printing
	 * This will take into consideration those custodian doc belonging to
	 * deleted checklists as well
	 * @param aLimitProfileID the limit profile identifier
	 * @param aCustomerID the customer identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         withdrawal
	 * @throws CustodianException on errors
	 * @throws RemoteException on errors
	 */
	public SearchResult getPendingWithdrawalListForNonBorrower(long aLimitProfileID, long aCustomerID)
			throws CustodianException, RemoteException;

	/**
	 * Get the list of custodian doc that are pending withdrawal memo printing
	 * This will take into consideration those custodian doc belonging to
	 * deleted checklists as well
	 * @param aTrxContext the transaction context
	 * @param aLimitProfileID the limit profile identifier
	 * @param aCustomerID the customer identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         withdrawal
	 * @throws CustodianException on errors
	 * @throws RemoteException on errors
	 */
	public SearchResult getPendingWithdrawalListForNonBorrower(ITrxContext aTrxContext, long aLimitProfileID,
			long aCustomerID) throws CustodianException, RemoteException;

	/**
	 * To get the custodian document using the custodian document ID
	 * @param aCustodianDocID - long
	 * @return ICustodianDoc - the object containing the custodian document info
	 * @throws CustodianException
	 * @throws RemoteException
	 */
	public ICustodianDoc getDocByID(long aCustodianDocID) throws CustodianException, RemoteException;

	/**
	 * To get the custodian document trx using the custodian document ID
	 * @param aCustodianDocID - long
	 * @return ICustodianTrxValue - the interface containing the custodian trx
	 *         document info
	 * @throws CustodianException
	 * @throws RemoteException
	 */
	public ICustodianTrxValue getTrxCustodianDoc(long custodianID) throws CustodianException, RemoteException;

	/**
	 * Get the custodian doc transaction object based on the transaction ID The
	 * object contains the before/after image depending on the indicator
	 * @param aTransactionID - String
	 * @return ICustodianTrxValue - the transaction object for the custodian
	 *         document
	 * @throws CustodianException
	 * @throws RemoteException
	 */
	public ICustodianTrxValue getDocByTrxID(String aTransactionID) throws CustodianException, RemoteException;

	public HashMap getDocListWithOwnerInfo(CustodianSearchCriteria aCustodianSearchCriteria) throws CustodianException,
			RemoteException;

	/**
	 * Get the list of custodian document that satisfy the criteria
	 * @param aCustodianSearchCriteria - CustodianSearchCriteria
	 * @return SearchResult - the search result containing the list of custodian
	 *         doc
	 * @throws CustodianException
	 * @throws RemoteException
	 */
	public SearchResult getDocList(CustodianSearchCriteria aCustodianSearchCriteria) throws CustodianException,
			RemoteException;

    /**
	 * Get the list of envelope barcode for specific customer baseed on limitprofile
	 * @param long - limitprofile
     * @param String - envBarcode
	 * @return boolean - of envelope barcode
	 * @throws CustodianException
	 * @throws RemoteException
	 */
	public boolean getCheckEnvelopeBarcodeExist(long limitprofile, String envBarcode) throws CustodianException,
			RemoteException;

    /**
	 * Get boolean to check whether document item barcode is unique
	 * @param String - document item barcode
	 * @return boolean - returning true/false on whether document item barcode exist
	 * @throws CustodianException
	 */
	public boolean getCheckDocItemBarcodeExist(String docItemBarcode, long checkListItemRefID) throws CustodianException,
			RemoteException;

    /**
	 * Get ISecEnvelopeItem to with location base on barcode
	 * @param String - document item barcode
	 * @return ISecEnvelopeItem - detail of OBSecEnvelopeItem
	 * @throws CustodianException
	 */
    public ISecEnvelopeItem getSecEnvItemLoc(String docItemBarcode) throws CustodianException,
			RemoteException;

	/**
	 * Perm Uplift a custodian doc
	 * @param anITrxContext - ITrxContext
	 * @return OBCustodianDoc - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 * @throws RemoteException
	 */
	public ICustodianDoc permUplift(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException, RemoteException;

	/**
	 * Maker perm uplift a custodian doc
	 * @param anITrxContext of ITrxContext type
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 * @throws RemoteException
	 */
	public ICustodianTrxValue permUplift(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue,
			ICustodianDoc anICustodianDoc) throws CustodianException, RemoteException;

	/**
	 * Checker approve perm uplift of a custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 * @throws RemoteException
	 */
	public ICustodianTrxValue approvePermUplift(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException, RemoteException;

	/**
	 * Maker temp uplift a custodian doc
	 * @param anITrxContext - ITrxContext
	 * @return OBCustodianDoc - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 * @throws RemoteException
	 */
	public ICustodianDoc tempUplift(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException, RemoteException;

	/**
	 * Maker temp uplift a custodian doc
	 * @param anITrxContext -\of ITrxContext type
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianTrxValue - the custodian doc trx that is to be temp
	 *         uplifted
	 * @throws CustodianException if errors
	 * @throws RemoteException
	 */
	public ICustodianTrxValue tempUplift(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue,
			ICustodianDoc anICustodianDoc) throws CustodianException, RemoteException;

	/**
	 * Checker approve temp uplift of a custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 * @throws RemoteException
	 */
	public ICustodianTrxValue approveTempUplift(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException, RemoteException;

	/**
	 * Maker authz for temp uplift ot a custodian doc..
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 * @throws RemoteException
	 */
	public ICustodianTrxValue authzTempUpliftDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException, RemoteException;

	/**
	 * Maker authz temp uplift of custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianDoc - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 * @throws RemoteException
	 */
	public ICustodianTrxValue authzTempUpliftDocMaker(ITrxContext anITrxContext,
			ICustodianTrxValue anICustodianTrxValue, ICustodianDoc anICustodianDoc) throws CustodianException,
			RemoteException;

	/**
	 * Checker approve the authorization to Temp uplift the custodian doc..
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 * @throws RemoteException
	 */
	public ICustodianTrxValue authzTempUpliftChecker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException, RemoteException;

	/**
	 * Maker authz for perm uplift ot a custodian doc..
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 * @throws RemoteException
	 */
	public ICustodianTrxValue authzPermUpliftDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException, RemoteException;

	/**
	 * Maker authz perm uplift of custodian doc
	 * @param anITrxContext of ITrxContext type
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianTrxValue - the custodian doc trx that is to be perm
	 *         uplift authz
	 * @throws CustodianException if errors
	 * @throws RemoteException
	 */
	public ICustodianTrxValue authzPermUpliftDocMaker(ITrxContext anITrxContext,
			ICustodianTrxValue anICustodianTrxValue, ICustodianDoc anICustodianDoc) throws CustodianException,
			RemoteException;

	/**
	 * Checker approve the authorization to perm uplift the custodian doc..
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 * @throws RemoteException
	 */
	public ICustodianTrxValue authzPermUpliftChecker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException, RemoteException;

	/**
	 * Maker authz for relodge ot a custodian doc..
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 * @throws RemoteException
	 */
	public ICustodianTrxValue authzRelodgeDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException, RemoteException;

	/**
	 * Maker authz relodge of custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianDoc - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 * @throws RemoteException
	 */
	public ICustodianTrxValue authzRelodgeDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue,
			ICustodianDoc anICustodianDoc) throws CustodianException, RemoteException;

	/**
	 * Checker approve the authorization to relodge the custodian doc..
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 * @throws RemoteException
	 */
	public ICustodianTrxValue authzRelodgeChecker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException, RemoteException;

	/**
	 * checker reject the custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 * @throws RemoteException
	 */
	public ICustodianTrxValue rejectCustodian(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException, RemoteException;

	/**
	 * maker cancel the rejected custodian..
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 * @throws RemoteException
	 */
	public ICustodianTrxValue cnclCustodian(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException, RemoteException;

	/**
	 * maker edit the rejected custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 * @throws RemoteException
	 */
	public ICustodianTrxValue editCustodian(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException, RemoteException;

	/**
	 * maker edit the rejected custodian doc
	 * @param anITrxContext of ITrxContext type
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianTrxValue - the custodian doc trx that is edited
	 * @throws CustodianException if errors
	 * @throws RemoteException
	 */
	public ICustodianTrxValue editCustodian(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue,
			ICustodianDoc anICustodianDoc) throws CustodianException, RemoteException;

	/**
	 * Persist custodian print authorization details...
	 * @param custAuthz - OBCustAuthorize[]
	 * @throws CustodianException if errors
	 * @throws RemoteException
	 */
	public void persistPrintAuthzDetails(OBCustAuthorize[] custAuthz) throws CustodianException, RemoteException;

	/**
	 * Persist custodian print authorization details...
	 * @param customer - ICMSCustomer
	 * @param checklistMap - HashMap with key as CheckListID and value as list
	 *        of CheckListItemRef for this checklist
	 * @return IMemo
	 * @throws CustodianException if errors
	 */
	public IMemo getPrintMemo(ICMSCustomer customer, HashMap checkListMap) throws CustodianException, RemoteException;

	/**
	 * Get the list of custodian doc by collateral ID
	 * @param aCollateralID - long
	 * @return ICustodianDoc[] - the list of custodian doc. Null if no custodian
	 *         doc found
	 * @throws CustodianException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICustodianDoc[] getCustodianDoc(long aCollateralID) throws CustodianException, RemoteException;

	/**
	 * Get the custodian doc by checklist ID
	 * @param aCheckListID of long type
	 * @return ICustodianDoc - the custodian doc with the checklist ID
	 * @throws CustodianException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICustodianDoc getCustodianDocByCheckList(long aCheckListID) throws CustodianException, RemoteException, FinderException;

	// public ICustodianTrxValue getNewCustodianDoc(long aCheckListItemID)
	// throws CustodianException, RemoteException;

	// new custodian methods
	/*
	 * public ICustodianTrxValue makerCreateCustodianDoc(ITrxContext trxContext,
	 * ICustodianTrxValue value) throws CustodianException, RemoteException;
	 * public ICustodianTrxValue checkerApproveCreateCustodianDoc(ITrxContext
	 * trxContext, ICustodianTrxValue value) throws CustodianException,
	 * RemoteException; public ICustodianTrxValue
	 * checkerRejectCreateCustodianDoc(ITrxContext trxContext,
	 * ICustodianTrxValue value) throws CustodianException, RemoteException;
	 * public ICustodianTrxValue makerCloseCreateCustodianDoc(ITrxContext
	 * trxContext, ICustodianTrxValue value) throws CustodianException,
	 * RemoteException;
	 */

	/**
	 * Maker saves the custodian doc as draft.
	 * 
	 * @param ctx transaction context
	 * @param trxVal custodian transaction value
	 * @return the draft custodian transaction value
	 * @throws CustodianException on errors encountered
	 * @throws RemoteException on remote errors
	 */
	public ICustodianTrxValue makerSaveCustodianDoc(ITrxContext trxContext, ICustodianTrxValue value)
			throws CustodianException, RemoteException;

	/**
	 * Maker updates custodian doc.
	 * 
	 * @param ctx transaction context
	 * @param trxVal custodian transaction value
	 * @return newly updated custodian transaction value
	 * @throws CustodianException on errors encountered
	 * @throws RemoteException on remote errors
	 */
	public ICustodianTrxValue makerUpdateCustodianDoc(ITrxContext trxContext, ICustodianTrxValue value)
			throws CustodianException, RemoteException;

	/**
	 * Maker cancel rejected or draft custodian doc.
	 * 
	 * @param ctx transaction context
	 * @param trxVal custodian transaction value
	 * @return cancelled custodian transaction value
	 * @throws CustodianException on errors encountered
	 * @throws RemoteException on remote errors
	 */
	public ICustodianTrxValue makerCloseCustodianDoc(ITrxContext trxContext, ICustodianTrxValue value)
			throws CustodianException, RemoteException;

	/**
	 * Checker approves custodian doc updated/created by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal custodian transaction value
	 * @return approved custodian transaction value
	 * @throws CustodianException on errors encountered
	 * @throws RemoteException on remote errors
	 */
	public ICustodianTrxValue checkerApproveCustodianDoc(ITrxContext trxContext, ICustodianTrxValue value)
			throws CustodianException, RemoteException;

	/**
	 * Checker rejects custodian doc updated/created by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal custodian transaction value
	 * @return approved custodian transaction value
	 * @throws CustodianException on errors encountered
	 * @throws RemoteException on remote errors
	 */
	public ICustodianTrxValue checkerRejectCustodianDoc(ITrxContext trxContext, ICustodianTrxValue value)
			throws CustodianException, RemoteException;

	public ICustodianTrxValue readCustodianDoc(ITrxContext trxContext, ICustodianTrxValue value)
			throws RemoteException, CustodianException;

	/**
	 * To replace getDocListWithOwnerInfo Get the custodian trx value and
	 * related owner information.
	 * 
	 * @param searchCriteria - CustodianSearchCriteria
	 * @return HashMap - map containing the following key, value pair : <br>
	 *         1. "trxValue", ICustodianTrxValue <br>
	 *         2. ICMSConstant.CC_OWNER, CCCustodianInfo <br>
	 *         3. ICMSConstant.SEC_OWNER, CollateralCustodianInfo <br>
	 *         4. ICMSConstant.SUB_CATEGORY, String representing subcategory <br>
	 * @throws CustodianException
	 */
	public HashMap getDocWithOwnerInfo(CustodianSearchCriteria searchCriteria) throws RemoteException,
			CustodianException;

	/**
	 * Get the custodian trx value given a checklist ID with no custodian items
	 * created.
	 * 
	 * @param checkListID - long
	 * @return ICustodianTrxValue
	 * @throws CustodianException
	 */
	public ICustodianTrxValue getNewDocTrxValue(long checkListID) throws RemoteException, CustodianException;

	/**
	 * Get the custodian trx ID given a set of search criteria
	 * 
	 * @param searchCriteria - CustodianSearchCriteria
	 * @return long - the custodian trx ID
	 * @return ICMSConstant.LONG_INVALID_VALUE if no such custodian trx found.
	 */
	public long getTrxID(CustodianSearchCriteria searchCriteria) throws RemoteException, CustodianException;

	/**
	 * update transaction
	 * @param trxID
	 * @throws RemoteException
	 * @throws CustodianException
	 */
	public void updateTransaction(ITrxValue trxValue) throws RemoteException, CustodianException;
}
