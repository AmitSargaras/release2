/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/proxy/ICustodianProxyManager.java,v 1.39 2005/10/14 02:26:51 whuang Exp $
 */
package com.integrosys.cms.app.custodian.proxy;

import java.util.HashMap;
import java.util.List;

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

import javax.ejb.FinderException;

/**
 * This interface defines the list of methods pertaining to the custodian that
 * is required to realize the use case
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.39 $
 * @since $Date: 2005/10/14 02:26:51 $ Tag: $Name: $
 */
public interface ICustodianProxyManager {
	/**
	 * Create a custodian doc as draft
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianDoc - ICustodianDoc
	 * @return ICustodianDoc - the custodian doc that has been saved as draft
	 * @throws CustodianException if errors
	 */
	public ICustodianDoc createDraftCustodianDoc(ITrxContext anITrxContext, ICustodianDoc anICustodianDoc)
			throws CustodianException;

	/**
	 * Receive the custodian and submit the draft
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the trx interface containing the info of the
	 *         custodian doc created
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue receiveDraftCustodianDoc(ITrxContext anITrxContext,
			ICustodianTrxValue anICustodianTrxValue) throws CustodianException;

	/**
	 * Maker Create a borrower custodian doc that is not in a checkList manually
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianDoc - ICustodianDoc
	 * @return ICustodianDoc - the biz interface containing the info of the
	 *         custodian doc created
	 * @throws CustodianException
	 */
	public ICustodianDoc createDocMaker(ITrxContext anITrxContext, ICustodianDoc anICustodianDoc, String borrowerTrxId)
			throws CustodianException;

	/**
	 * Checker Approve the creation of a borrower custodian doc that is created
	 * manually
	 * @param anITrxContext - ITrxContext
	 * @return ICustodianTrxValue - the trx interface containing the info of the
	 *         custodian doc created
	 * @throws CustodianException
	 */
	public ICustodianTrxValue createDocChecker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException;

	/**
	 * Create a borrower custodian doc thru' checkList
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianDoc - ICustodianDoc
	 * @return ICustodianDoc - the biz interface containing the info of the
	 *         custodian doc created
	 * @throws CustodianException
	 */
	public ICustodianDoc createDocByBorrower(ITrxContext anITrxContext, ICustodianDoc anICustodianDoc)
			throws CustodianException;

	/**
	 * Create a collateral custodian doc thru' checklist
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianDoc - ICustodianDoc
	 * @return ICustodianDoc - the biz interface containing the info of the
	 *         custodian doc created
	 * @throws CustodianException if errors
	 */
	public ICustodianDoc createDocByCollateral(ITrxContext anITrxContext, ICustodianDoc anICustodianDoc)
			throws CustodianException;

	/**
	 * Maker re Lodge a custodian doc
	 * @param anITrxContext - ITrxContext
	 * @return ICustodianDoc - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 * @deprecated
	 */
	public ICustodianDoc reLodgeDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException;

	/**
	 * Maker re Lodge a custodian doc
	 * @param anITrxContext of ITrxContext type
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianTrxValue - the custodian doc trx that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue reLodgeDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue,
			ICustodianDoc anICustodianDoc) throws CustodianException;

	/**
	 * Maker Lodge a custodian doc
	 * @param anITrxContext - ITrxContext
	 * @return ICustodianDoc - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 * @deprecated
	 */
	public ICustodianDoc lodgeDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException;

	/**
	 * Maker Lodge a custodian doc
	 * @param anITrxContext of ITrxContext type
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @return ICustodianTrxValue - the custodian doc trx that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue lodgeDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue,
			ICustodianDoc anICustodianDoc) throws CustodianException;

	/**
	 * Checker approve lodgement of a custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue lodgeDocChecker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException;

	/**
	 * Checker approve relodgement of a custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue reLodgeDocChecker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException;

	/**
	 * Checker Approve updating a custodian doc. This will cater to the maintain
	 * custodian doc use case
	 * @param anICustodianTrxValue - ITrxValue
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the trx interface containing the info of the
	 *         custodian doc updated
	 * @throws CustodianException
	 */
	public ICustodianTrxValue updateDocChecker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException;

    public SearchResult getPendingReversalList(long aLimitProfileID) throws CustodianException;

    public SearchResult getPendingReversalList(ITrxContext aTrxContext, long aLimitProfileID)
			throws CustodianException;

    public SearchResult getPendingReversalListForNonBorrower(long aCustomerID) throws CustodianException;

    public SearchResult getPendingReversalListForNonBorrower(ITrxContext aTrxContext, long aCustomerID)
                throws CustodianException;

    public SearchResult getPendingReversalListForNonBorrower(long aLimitProfileID, long aCustomerID)
			throws CustodianException;

    public SearchResult getPendingReversalListForNonBorrower(ITrxContext aTrxContext, long aLimitProfileID,
                long aCustomerID) throws CustodianException;


    /**
	 * Get the list of custodian doc that are pending lodgement for lodgement
	 * memo printing
	 * @param aCustomerID - the customer identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         lodgement
	 * @throws CustodianException
	 */
    /**
	 * Get the list of custodian doc that are pending lodgement for lodgement
	 * memo printing
	 * @param aLimitProfileID - the limit profile ID
	 * @return SearchResult - the list of custodian doc that is pending
	 *         lodgement
	 * @throws CustodianException
	 */
	public SearchResult getPendingLodgementList(long aLimitProfileID) throws CustodianException;

	/**
	 * Get the list of custodian doc that are pending lodgement for lodgement
	 * memo printing
	 * @param aTrxContext - the transaction context
	 * @param aLimitProfileID - the limit profile ID
	 * @return SearchResult - the list of custodian doc that is pending
	 *         lodgement
	 * @throws CustodianException
	 */
	public SearchResult getPendingLodgementList(ITrxContext aTrxContext, long aLimitProfileID)
			throws CustodianException;

	/**
	 * Get the list of custodian doc that are pending lodgement for lodgement
	 * memo printing
	 * @param aCustomerID - the customer identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         lodgement
	 * @throws CustodianException
	 */
	public SearchResult getPendingLodgementListForNonBorrower(long aCustomerID) throws CustodianException;

	/**
	 * Get the list of custodian doc that are pending lodgement for lodgement
	 * memo printing
	 * @param aTrxContext - the transaction context
	 * @param aCustomerID - the customer identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         lodgement
	 * @throws CustodianException
	 */
	public SearchResult getPendingLodgementListForNonBorrower(ITrxContext aTrxContext, long aCustomerID)
			throws CustodianException;

	/**
	 * Get the list of custodian doc that are pending lodgement for lodgement
	 * memo printing This will take into consideration those custodian doc
	 * belonging to deleted checklists as well
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @return SearchResult - the list of custodian docs that are pending
	 *         lodgement
	 * @throws CustodianException on errors
	 */
	public SearchResult getPendingLodgementListForNonBorrower(long aLimitProfileID, long aCustomerID)
			throws CustodianException;

	/**
	 * Get the list of custodian doc that are pending lodgement for lodgement
	 * memo printing This will take into consideration those custodian doc
	 * belonging to deleted checklists as well
	 * @param aTrxContext - the transaction context
	 * @param aLimitProfileID - of long type
	 * @param aCustomerID - of long type
	 * @return SearchResult - the list of custodian docs that are pending
	 *         lodgement
	 * @throws CustodianException on errors
	 */
	public SearchResult getPendingLodgementListForNonBorrower(ITrxContext aTrxContext, long aLimitProfileID,
			long aCustomerID) throws CustodianException;

	/**
	 * Get the list of custodian doc that are pending withdrawal for withdrawal
	 * memo printing
	 * @param aLimitProfileID the limit profile identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         withdrawal
	 * @throws CustodianException
	 */
	public SearchResult getPendingWithdrawalList(long aLimitProfileID) throws CustodianException;

	/**
	 * Get the list of custodian doc that are pending withdrawal for withdrawal
	 * memo printing
	 * @param aTrxContext the transaction context
	 * @param aLimitProfileID the limit profile identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         withdrawal
	 * @throws CustodianException
	 */
	public SearchResult getPendingWithdrawalList(ITrxContext aTrxContext, long aLimitProfileID)
			throws CustodianException;

	/**
	 * Get the list of custodian doc that are pending withdrawal for withdrawal
	 * memo printing
	 * @param aCustomerID the customer identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         withdrawal
	 * @throws CustodianException
	 */
	public SearchResult getPendingWithdrawalListForNonBorrower(long aCustomerID) throws CustodianException;

	/**
	 * Get the list of custodian doc that are pending withdrawal for withdrawal
	 * memo printing
	 * @param aTrxContext the transaction context
	 * @param aCustomerID the customer identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         withdrawal
	 * @throws CustodianException
	 */
	public SearchResult getPendingWithdrawalListForNonBorrower(ITrxContext aTrxContext, long aCustomerID)
			throws CustodianException;

	/**
	 * Get the list of custodian doc that are pending withdrawal memo printing
	 * This will take into consideration those custodian doc belonging to
	 * deleted checklists as well
	 * @param aLimitProfileID the limit profile identifier
	 * @param aCustomerID the customer identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         withdrawal
	 * @throws CustodianException on errors
	 */
	public SearchResult getPendingWithdrawalListForNonBorrower(long aLimitProfileID, long aCustomerID)
			throws CustodianException;

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
	 */
	public SearchResult getPendingWithdrawalListForNonBorrower(ITrxContext aTrxContext, long aLimitProfileID,
			long aCustomerID) throws CustodianException;

	/**
	 * To get the custodian document using the custodian document ID
	 * @param aCustodianDocID - long
	 * @return ICustodianDoc - the interface containing the custodian document
	 *         info
	 * @throws CustodianException
	 */
	public ICustodianDoc getDocByID(long aCustodianDocID) throws CustodianException;

	/**
	 * To get the custodian document trx using the custodian document ID
	 * @param aCustodianDocID - long
	 * @return ICustodianTrxValue - the interface containing the custodian trx
	 *         document info
	 * @throws CustodianException
	 */
	public ICustodianTrxValue getTrxCustodianDoc(long aCustodianDocID) throws CustodianException;

	/**
	 * Get the custodian doc transaction object based on the transaction ID The
	 * object contains the before/after image depending on the indicator
	 * @param aTransactionID - String
	 * @return ICustodianTrxValue - the transaction interface for the custodian
	 *         document
	 * @throws CustodianException
	 */
	public ICustodianTrxValue getDocByTrxID(String aTransactionID) throws CustodianException;

	public HashMap getDocListWithOwnerInfo(CustodianSearchCriteria aCustodianSearchCriteria) throws CustodianException;

	/**
	 * Get the list of custodian document that satisfy the criteria
	 * @param aCustodianSearchCriteria - CustodianSearchCriteria
	 * @return SearchResult - the search result containing the list of custodian
	 *         doc
	 * @throws CustodianException
	 */
	public SearchResult getDocList(CustodianSearchCriteria aCustodianSearchCriteria) throws CustodianException;

    /**
	 * Get boolean to check whether envelope barcode exist
	 * @param long - customer limit profile
     * @param String - envelope barcode
	 * @return boolean - returning true/false on whether envelope barcode exist
	 * @throws CustodianException
	 */
    public boolean getCheckEnvelopeBarcodeExist(long limitprofile, String envBarcode) throws CustodianException;

    /**
	 * Get boolean to check whether document item barcode is unique
	 * @param String - document item barcode
	 * @return boolean - returning true/false on whether document item barcode exist
	 * @throws CustodianException
	 */
    public boolean getCheckDocItemBarcodeExist(String docItemBarcode, long checkListItemRefID) throws CustodianException;

    /**
	 * Get ISecEnvelopeItem to with location base on barcode
	 * @param String - document item barcode
	 * @return ISecEnvelopeItem - detail of OBSecEnvelopeItem
	 * @throws CustodianException
	 */
    public ISecEnvelopeItem getSecEnvItemLoc(String docItemBarcode) throws CustodianException;

	/**
	 * Maker perm uplift a custodian doc
	 * @param anITrxContext - ITrxContext
	 * @return ICustodianDoc - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 * @deprecated
	 */

	public ICustodianDoc permUplift(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException;

	/**
	 * Maker perm uplift a custodian doc
	 * @param anITrxContext of ITrxContext type
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue permUplift(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue,
			ICustodianDoc anICustodianDoc) throws CustodianException;

	/**
	 * Checker approve perm uplift of a custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue approvePermUplift(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException;

	/**
	 * Maker temp uplift a custodian doc
	 * @param anITrxContext - ITrxContext
	 * @return ICustodianDoc - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 * @deprecated
	 */
	public ICustodianDoc tempUplift(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException;

	/**
	 * Maker temp uplift a custodian doc
	 * @param anITrxContext -\of ITrxContext type
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianTrxValue - the custodian doc trx that is to be temp
	 *         uplifted
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue tempUplift(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue,
			ICustodianDoc anICustodianDoc) throws CustodianException;

	/**
	 * Checker approve temp uplift of a custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue approveTempUplift(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException;

	/**
	 * Maker authz temp uplift of custodian doc
	 * @param anITrxContext - ITrxContext
	 * @return ICustodianDoc - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 * @deprecated
	 */
	public ICustodianTrxValue authzTempUpliftDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException;

	/**
	 * Maker authz temp uplift of custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianDoc - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue authzTempUpliftDocMaker(ITrxContext anITrxContext,
			ICustodianTrxValue anICustodianTrxValue, ICustodianDoc anICustodianDoc) throws CustodianException;

	/**
	 * Checker approve authz temp uplift of a custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue authzTempUpliftChecker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException;

	/**
	 * Maker authz perm uplift of custodian doc
	 * @param anITrxContext - ITrxContext
	 * @return ICustodianDoc - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 * @deprecated
	 */
	public ICustodianTrxValue authzPermUpliftDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException;

	/**
	 * Maker authz perm uplift of custodian doc
	 * @param anITrxContext of ITrxContext type
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianTrxValue - the custodian doc trx that is to be perm
	 *         uplift authz
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue authzPermUpliftDocMaker(ITrxContext anITrxContext,
			ICustodianTrxValue anICustodianTrxValue, ICustodianDoc anICustodianDoc) throws CustodianException;

	/**
	 * Checker approve authz perm uplift of a custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue authzPermUpliftChecker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException;

	/**
	 * Maker authz relodge of custodian doc
	 * @param anITrxContext - ITrxContext
	 * @return ICustodianDoc - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 * @deprecated
	 */
	public ICustodianTrxValue authzRelodgeDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException;

	/**
	 * Maker authz relodge of custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianDoc - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue authzRelodgeDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue,
			ICustodianDoc anICustodianDoc) throws CustodianException;

	/**
	 * Checker approve authz relodge of a custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue authzRelodgeChecker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException;

	/**
	 * Checker reject a custodian doc action..
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue rejectCustodian(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException;

	/**
	 * maker edit the rejected custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 * @deprecated
	 */
	public ICustodianTrxValue editCustodian(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException;

	/**
	 * maker edit the rejected custodian doc
	 * @param anITrxContext of ITrxContext type
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianTrxValue - the custodian doc trx that is edited
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue editCustodian(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue,
			ICustodianDoc anICustodianDoc) throws CustodianException;

	/**
	 * Maker cancel the rejected custodian doc
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue cnclCustodian(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException;

	/**
	 * Persist custodian print authorization details...
	 * @param custAuthz - OBCustAuthorize[]
	 * @throws CustodianException if errors
	 */
	public void persistPrintAuthzDetails(OBCustAuthorize[] custAuthz) throws CustodianException;

	/**
	 * Persist custodian print authorization details...
	 * @param customer - ICMSCustomer
	 * @param checkListMap - HashMap with key as CheckListID and value as list
	 *        of CheckListItemRef for this checklist
	 * @return IMemo
	 * @throws CustodianException if errors
	 */
	public IMemo getPrintMemo(ICMSCustomer customer, HashMap checkListMap) throws CustodianException;

	/**
	 * Get the list of custodian doc by collateral ID
	 * @param aCollateralID - long
	 * @return ICustodianDoc[] - the list of custodian doc. Null if no custodian
	 *         doc found
	 * @throws CustodianException on errors
	 */
	public ICustodianDoc[] getCustodianDoc(long aCollateralID) throws CustodianException;

	/**
	 * Get the custodian doc by checklist ID
	 * @param aCheckListID of long type
	 * @return ICustodianDoc - the custodian doc with the checklist ID
	 * @throws CustodianException on errors
	 */
	public ICustodianDoc getCustodianDocByCheckList(long aCheckListID) throws CustodianException, FinderException;

	/**
	 * Maker saves the custodian doc as draft.
	 * 
	 * @param ctx transaction context
	 * @param trxVal custodian transaction value
	 * @return the draft custodian transaction value
	 * @throws CustodianException on errors encountered
	 */
	public ICustodianTrxValue makerSaveCustodianDoc(ITrxContext ctx, ICustodianTrxValue trxVal)
			throws CustodianException;

	/**
	 * Maker updates custodian doc.
	 * 
	 * @param ctx transaction context
	 * @param trxVal custodian transaction value
	 * @return newly updated custodian transaction value
	 * @throws CustodianException on errors encountered
	 */
	public ICustodianTrxValue makerUpdateCustodianDoc(ITrxContext ctx, ICustodianTrxValue trxVal)
			throws CustodianException;

	/**
	 * Maker cancel rejected or draft custodian doc.
	 * 
	 * @param ctx transaction context
	 * @param trxVal custodian transaction value
	 * @return cancelled custodian transaction value
	 * @throws CustodianException on errors encountered
	 */
	public ICustodianTrxValue makerCloseCustodianDoc(ITrxContext ctx, ICustodianTrxValue trxVal)
			throws CustodianException;

	/**
	 * Checker approves custodian doc updated/created by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal custodian transaction value
	 * @return approved custodian transaction value
	 * @throws CustodianException on errors encountered
	 */
	public ICustodianTrxValue checkerApproveCustodianDoc(ITrxContext ctx, ICustodianTrxValue trxVal)
			throws CustodianException;

	/**
	 * Checker rejects custodian doc updated/created by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal custodian transaction value
	 * @return approved custodian transaction value
	 * @throws CustodianException on errors encountered
	 */
	public ICustodianTrxValue checkerRejectCustodianDoc(ITrxContext ctx, ICustodianTrxValue trxVal)
			throws CustodianException;

	public ICustodianTrxValue readCustodianDoc(ITrxContext trxContext, ICustodianTrxValue trxValue)
			throws CustodianException;

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
	public HashMap getDocWithOwnerInfo(CustodianSearchCriteria searchCriteria) throws CustodianException;

	/**
	 * Get the custodian trx value given a checklist ID with no custodian items
	 * created.
	 * 
	 * @param checkListID - long
	 * @return ICustodianTrxValue
	 * @throws CustodianException
	 */
	public ICustodianTrxValue getNewDocTrxValue(long checkListID) throws CustodianException;

	/**
	 * Get the custodian trx ID given a set of search criteria
	 * 
	 * @param searchCriteria - CustodianSearchCriteria
	 * @return long - the custodian trx ID
	 * @return ICMSConstant.LONG_INVALID_VALUE if no such custodian trx found.
	 * @throws CustodianException
	 */
	public long getTrxID(CustodianSearchCriteria searchCriteria) throws CustodianException;

	/**
	 * update Transaction
	 * @param trxID
	 * @throws CustodianException
	 */
	public void updateTransaction(ITrxValue trxValue) throws CustodianException;
}
