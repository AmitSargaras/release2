/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/proxy/ICollateralProxy.java,v 1.77 2006/11/10 08:59:12 jzhai Exp $
 */
package com.integrosys.cms.app.collateral.proxy;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.CollateralSearchCriteria;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralParameter;
import com.integrosys.cms.app.collateral.bus.ICollateralPledgor;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.bus.ICollateralType;
import com.integrosys.cms.app.collateral.bus.IPledgor;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This interface defines the services that are available in CMS with respect to
 * the collateral life cycle.
 * 
 * @author jzhai
 * @author Andy Wong
 * @author Chong Jun Yong
 * @since 2006/11/10
 */
public interface ICollateralProxy {

	/**
	 * Get security sub type ID for a collateral transaction.
	 * 
	 * @param aTrxID - primitive long denoting the collateral transaction
	 * @return String denoting the security sub type
	 * @throws CollateralException on errors encountered
	 */
	public String[] getSecuritySubTypeForTrxByTrxID(long aTrxID) throws CollateralException;

	/**
	 * Get the collateral transaction value given its collateral id.
	 * 
	 * @param ctx transaction context
	 * @param collateralID collateral id in CMS
	 * @return collateral trasaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralTrxValue getCollateralTrxValue(ITrxContext ctx, long collateralID) throws CollateralException;

	public ICollateralTrxValue[] getCollateralTrxValues(ITrxContext[] ctx, long[] collateralID)
			throws CollateralException;

	/**
	 * Get the collateral transaction value given the transaction id.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @return collateral transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralTrxValue getCollateralTrxValue(ITrxContext ctx, String trxID) throws CollateralException;

	public ICollateralTrxValue[] getCollateralTrxValues(ITrxContext ctx, String trxID) throws CollateralException;

	/**
	 * Search collateral based on the criteria specified.
	 * 
	 * @param ctx transaction context
	 * @param criteria of type CollateralSearchCriteria
	 * @return search result
	 * @throws CollateralException on errors encountered
	 */
	public SearchResult searchCollateral(ITrxContext ctx, CollateralSearchCriteria criteria) throws CollateralException;

	/**
	 * Get collateral business object given its collateral id.
	 * 
	 * @param collateralID collateral id
	 * @param includeDetails true to get complete collateral, otherwise false
	 * @return ICollateral
	 * @throws CollateralException on errors encountered
	 */
	public ICollateral getCollateral(long collateralID, boolean includeDetails) throws CollateralException;

	/**
	 * Subscribe collateral created in SCI to CMS.
	 * 
	 * @param trxVal collateral transaction value
	 * @return collateral transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralTrxValue subscribeCreateCollateral(ICollateralTrxValue trxVal) throws CollateralException;

	/**
	 * Subscribe collateral deleted in SCI to CMS.
	 * 
	 * @param trxVal collateral transaction value
	 * @return collateral transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralTrxValue subscribeDeleteCollateral(ICollateralTrxValue trxVal) throws CollateralException;

	/**
	 * Maker update collateral.
	 * 
	 * @param ctx transaction context object
	 * @param trxVal collateral transaction value
	 * @param col collateral to be updated
	 * @return collateral transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralTrxValue makerUpdateCollateral(ITrxContext ctx, ICollateralTrxValue trxVal, ICollateral col)
			throws CollateralException;

	public ICollateralTrxValue[] makerUpdateCollaterals(ITrxContext[] ctx, ICollateralTrxValue[] trxVal,
			ICollateral[] col) throws CollateralException;

	/**
	 * Checker verify collateral updated by maker.
	 * 
	 * @param ctx transaction context object
	 * @param trxVal collateral transaction value
	 * @return collateral transaction value
	 * @throws CollateralException on errors encountered
	 */

	public ICollateralTrxValue checkerVerifyCollateral(ITrxContext ctx, ICollateralTrxValue trxVal)
			throws CollateralException;

	public ICollateralTrxValue[] checkerVerifyUpdateCollaterals(ITrxContext[] ctx, ICollateralTrxValue[] trxVal)
			throws CollateralException;

	/**
	 * Save a collateral, but not submitted.
	 * 
	 * @param ctx transaction context object
	 * @param trxVal collateral transaction value
	 * @param col collateral to be saved
	 * @return collateral transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralTrxValue makerSaveCollateral(ITrxContext ctx, ICollateralTrxValue trxVal, ICollateral col)
			throws CollateralException;

	public ICollateralTrxValue[] makerSaveCollaterals(ITrxContext[] ctx, ICollateralTrxValue[] trxVal, ICollateral[] col)
			throws CollateralException;

	/**
	 * Cancel a collateral draft.
	 * 
	 * @param ctx transaction context
	 * @param trxVal collateral transaction value
	 * @return collateral transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralTrxValue makerCancelCollateral(ITrxContext ctx, ICollateralTrxValue trxVal)
			throws CollateralException;

	public ICollateralTrxValue[] makerCancelCollaterals(ITrxContext[] ctx, ICollateralTrxValue[] trxVal)
			throws CollateralException;

	/**
	 * System cancels collateral.
	 * 
	 * @param ctx transaction context
	 * @param trxVal collateral transaction value
	 * @return collateral transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralTrxValue systemCancelCollateral(ITrxContext ctx, ICollateralTrxValue trxVal)
			throws CollateralException;

	/**
	 * System updates collateral or Stp collateral to pending retry due to host
	 * technical error
	 * 
	 * @param ctx transaction context
	 * @param trxVal collateral transaction value
	 * @return collateral transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralTrxValue systemUpdateCollateral(ITrxContext ctx, ICollateralTrxValue trxVal)
			throws CollateralException;

	/**
	 * System updates collateral's charges. It will delete the security limit
	 * map and its charge if the limit id has only one charge.
	 * 
	 * @param ctx transaction context
	 * @param collaterals a list of collaterals
	 * @param limitID limit id
	 * @return a list of collateral transaction values
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralTrxValue[] systemUpdateCollateralCharge(ITrxContext ctx, ICollateral[] collaterals, long limitID)
			throws CollateralException;

	/**
	 * Host updates collateral.
	 * 
	 * @param ctx transaction context
	 * @param trxVal collateral transaction value
	 * @return collateral transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralTrxValue hostUpdateCollateral(ITrxContext ctx, ICollateralTrxValue trxVal)
			throws CollateralException;

	/**
	 * Checker rejects collateral updated by maker.
	 * 
	 * @param ctx transaction context object
	 * @param trxVal collateral transaction value
	 * @return collateral transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralTrxValue checkerRejectCollateral(ITrxContext ctx, ICollateralTrxValue trxVal)
			throws CollateralException;

	public ICollateralTrxValue[] checkerRejectUpdateCollaterals(ITrxContext[] ctx, ICollateralTrxValue[] trxVal)
			throws CollateralException;

	/**
	 * Get a list of pledgors belong to a collateral.
	 * 
	 * @param collateralID cms collateral ID
	 * @return a list of ICollateralPledgor objects
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralPledgor[] getCollateralPledgors(long collateralID) throws CollateralException;

	/**
	 * Get security parameter value given the country code and security subtype
	 * code.
	 * 
	 * @param countryCode country ISO code
	 * @param subTypeCode security subtype code
	 * @return security parameter
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralParameter getCollateralParameter(String countryCode, String subTypeCode)
			throws CollateralException;

	/**
	 * Get a list of collateral subtypes belong to the type given.
	 * 
	 * @param colType of type ICollateralType
	 * @return a list of collateral subtypes
	 * @throws CollateralException on error finding the collateral subtypes
	 */
	public ICollateralSubType[] getCollateralSubTypeByType(ICollateralType colType) throws CollateralException;

	/**
	 * Get all collateral types avaiable in CMS based on the criteria.
	 * 
	 * @return a list of collateral types
	 * @throws CollateralException on error getting the collateral types
	 */
	public ICollateralType[] getAllCollateralTypes() throws CollateralException;

	/**
	 * Get a list of collateral subtypes in CMS.
	 * 
	 * @return ICollateralSubType[]
	 * @throws CollateralException on error getting the collateral subtypes
	 */
	public ICollateralSubType[] getAllCollateralSubTypes() throws CollateralException;

	/**
	 * Get the pledgor info based on the pledgor ID
	 * 
	 * @param aPledgorID of long type
	 * @return pledgor information
	 * @throws CollateralException on errors getting the pledgor info
	 */
	public IPledgor getPledgor(long aPledgorID) throws CollateralException;

	/**
	 * Get the pledgor info based on the pledgor ID from SCI
	 * 
	 * @param sciPledgorID pledgor id from SCI of long type
	 * @return pledgor information
	 * @throws CollateralException on errors getting the pledgor info
	 */
	public IPledgor getPledgorBySCIPledgorID(long sciPledgorID) throws CollateralException;

	/**
	 * Check if the collateral checklist is completed.
	 * 
	 * @param col of type ICollateral
	 * @return true if security checklist is completed, otherwise false
	 */
	public boolean isCollateralCheckListCompleted(ICollateral col);

	/**
	 * Get the number of securities a pledgor has.
	 * 
	 * @param pledgorID pledgor id
	 * @return security count
	 * @throws CollateralException on errors
	 */
	public int getCollateralCountForPledgor(long pledgorID) throws CollateralException;

	/**
	 * Get the collateral valuer of the security location
	 * 
	 * @param countryCode security location
	 * @return Hashamap which contains the list of valuer code and name
	 * @throws CollateralException on errors
	 */
	public Map getCollateralValuer(String countryCode) throws CollateralException;

	/**
	 * Given the collateral id, check if its custodian documents are still in
	 * vault.
	 * 
	 * @param collateralID of type long
	 * @return false if no document is in vault, otherwise true
	 * @throws CollateralException on errors encountered
	 */
	public boolean isCollateralCustodianDocInVault(long collateralID) throws CollateralException;

	/**
	 * To check if a collateral still has checklist related to it.
	 * 
	 * @param collateralID of type long
	 * @return true if all checklist have been deleted, otherwise false
	 * @throws CollateralException on errors encountered
	 */
	public boolean isCollateralNoChecklist(long collateralID) throws CollateralException;

	/**
	 * Get all booking locations.
	 * 
	 * @return a list of IBookingLocation objects
	 * @throws CollateralException on error getting the booking location list
	 */
	public IBookingLocation[] getAllBookingLocation() throws CollateralException;

	/**
	 * Check if the collateral given is shared by multiple BCAs.
	 * 
	 * @param col of type ICollateral
	 * @return true if it is shared collateral, otherwise false.
	 * @throws CollateralException on any errors encountered
	 */
	public boolean isCollateralShared(ICollateral col) throws CollateralException;

	/**
	 * Get the DocumentNumber count given the document number
	 * 
	 * @param String docNo
	 * @param boolean to mention if create or update
	 * @param long insurancePolicyId to be used if it is update.
	 * @param long collateralId
	 * @return int- DocumentNumber count
	 * @throws CollateralException on errors encountered
	 */

	public int getDocumentNoCount(String docNo, boolean isCreate, long insPolicyId, long collateralId)
			throws CollateralException;

	public List getDisTaskBcaLocationList(long collateralId) throws CollateralException;

	/**
	 * Get the customer name of the collateral
	 * 
	 * @param collateralID
	 * @return String - customer name
	 * @throws CollateralException on errors encountered
	 */
	public String getCustomerNameByCollateralID(long collateralID) throws CollateralException;

	public IValuationModel getCollateralCMVFSV(ICollateral col) throws CollateralException;

	/**
	 * update security perfection date by AA Number
	 * 
	 * @param aANumber AA Number
	 * @return updatedrecord count
	 * @throws CollateralException on errors
	 */
	public int updateSecPerfectDateByAANumber(long aANumber) throws CollateralException;

	/**
	 * Check if Liquidation Button to be shown or not ( i.e If NPL records exist
	 * )
	 * 
	 * @param collateralId
	 * @return boolean
	 * @throws CollateralException on errors
	 */
	public String getLiquidationIsNPL(long collateralId) throws CollateralException;

	public ICollateralTrxValue makerDeleteCollateral(ITrxContext ctx, ICollateralTrxValue trxVal)
			throws CollateralException;

	/**
	 * System approve Stp collateral creation, update or delete, actual copy
	 * will be mirrored from staging copy in the trx value supplied.
	 * 
	 * @param context trx context information, storing collateral information
	 * @param trxValue transaction value object contains every information that
	 *        will be updated, most important is the actual (can be null) and
	 *        staging collateral
	 * @return updated transaction value with state changed, and reference id
	 *         populated (for creation)
	 * @throws CollateralException if there is any error occured in bus or trx
	 *         package.
	 */
	public ICollateralTrxValue systemApproveCollateral(ITrxContext context, ICollateralTrxValue trxValue)
			throws CollateralException;

	/**
	 * System reject Stp collateral transaction from create, update or deletion,
	 * result in different state of reject.
	 * 
	 * @param context trx context information, storing collateral information
	 * @param trxValue transaction value object contains every information that
	 *        will be updated
	 * @return updated transaction value object with status changed only
	 * @throws CollateralException if there is any error occured in bus or trx
	 *         package.
	 */
	public ICollateralTrxValue systemRejectCollateral(ITrxContext context, ICollateralTrxValue trxValue)
			throws CollateralException;

	/**
	 * To update or insert stp allowed indicator for a collateral transaction.
	 * Ideally, check against staging copy, because it always have the latest
	 * copy.
	 * 
	 * @param trxValue the collateral trx value
	 * @param isStpAllowed to indicate whether collateral is allowed to be stp
	 */
	public void updateOrInsertStpAllowedIndicator(ICollateralTrxValue trxValue, boolean isStpAllowed);

	/**
	 * Get the policy no is already exist in the database or not
	 * 
	 * @param String policyNo the policy number to be checked against the
	 *        uniqueness
	 * @param insurancePolicyReferenceId the insurance policy reference id,
	 *        which is the key between actual sand staging, useful when resubmit
	 *        case for the same insurance policy
	 * @return boolean - policy no exist = true, else false
	 * @throws CollateralException on errors encountered
	 */
	public boolean getPolicyNumber(String policyNo, String insurancePolicyReferenceId) throws CollateralException;

    /**
     * Get the collateral name is already exist in the database or not
     *
     * @param collateralName the collateral name to be checked against the
	 *        uniqueness
     * @param cmsCollateralId
     * @return boolean - collateral name no exist = true, else false
     * @throws CollateralException on errors encountered
     */
	public boolean getCollateralName(String collateralName, long cmsCollateralId) throws CollateralException;
	/**
	 * Get the customer id of the collateral
	 * 
	 * @param collateralID
	 * @return String - customer name
	 * @throws CollateralException on errors encountered
	 */
	public String getCustomerIDByCollateralID(long collateralID) throws CollateralException;
  
	public ICollateralTrxValue createLien(ICollateralTrxValue value) throws CollateralException;//changed

	/**
	 * method to retrieve the recurrent due date list by customer id and collateral id
	 * 
	 * @param customerID
	 * @param cmsCollatralId
	 * @return dueDateList
	 */
	public List getRecurrentDueDateListByCustomerAndCollatralID(long customerID, long cmsCollatralId);
	
	public ILimit getReleasableAmountByCollateralID(long collateralID) throws CollateralException, SearchDAOException;
	//	Added for GC by Anil 
	public String getStatementNameByDocCode(String docCode) throws CollateralException, SearchDAOException;
	// Santosh IRB
	public String getDpCalculateManuallyByDateAndDocCode(Date date,String selectedDocCode,Long cmsCollateralId) throws CollateralException, SearchDAOException;
	//Stock DP calculation
	public List getStockAndDateDetailsWithAssetId(Long cmsCollateralId) throws CollateralException, SearchDAOException;
	public List getStockAndDateDetailsForEachAssetId(String assetId) throws CollateralException, SearchDAOException;
	public List getLocationForEachAssetId(String assetId) throws CollateralException, SearchDAOException;
	public List getStockDisplayList(String assetId) throws CollateralException, SearchDAOException;

	public ICollateralTrxValue updateCollateralWithApprovalThroughREST(ITrxContext context, ICollateralTrxValue trxValue, ICollateral col) throws CollateralException;
	
	public ICollateralTrxValue deleteCollateralWithApprovalThroughREST(ITrxContext context, ICollateralTrxValue trxValue, ICollateral col) throws CollateralException;
	
}
