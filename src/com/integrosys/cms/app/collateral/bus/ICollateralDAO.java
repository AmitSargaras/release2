/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/ICollateralDAO.java,v 1.12 2006/11/10 09:01:11 jzhai Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibm.db2.jcc.am.SqlException;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.type.cash.OBCashDeposit;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.eod.bus.IEODStatus;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.valuationAmountAndRating.bus.OBValuationAmountAndRating;
import com.integrosys.cms.businfra.LabelValue;

/**
 * DAO for collateral.
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2006/11/10 09:01:11 $ Tag: $Name: $
 */
public interface ICollateralDAO {
	/**
	 * Get all collateral types avaiable in CMS based on the criteria.
	 * 
	 * @return a list of collateral types
	 * @throws SearchDAOException on error searching the collateral type
	 */
	public ICollateralType[] getAllCollateralTypes() throws SearchDAOException;

	/**
	 * Get collateral and related LimitCharges
	 * 
	 * @return collateral with LimitCharges
	 * @throws SearchDAOException on error searching the collateral collateral
	 */
	public ICollateral getCollateralLimitChanges(long collateralID) throws SearchDAOException;
	
	public List selectCashFdList(String sql,String receiptNo) throws SearchDAOException;
	
	public String fillFD(String cashDepID) throws SearchDAOException;
	
	public double getAllTotalLienAmount(String receiptNo) throws SearchDAOException;

	public double getCurrentLienAmount(String cashDepID,String receiptNo) throws SearchDAOException;
	
	public double getNonDepositCurrentLienAmount(String cashDepID,String receiptNo) throws SearchDAOException;
	
	public double getStagingCurrentLienAmount(String cashDepID,String receiptNo) throws SearchDAOException;
	
	public double getNonStagingCurrentLienAmount(String cashDepID,String receiptNo) throws SearchDAOException;
	/**
	 * Search collateral based on the given criteria.
	 * 
	 * @param criteria of type CollateralSearchCriteria
	 * @return search result that contains a list of OBCollateralSearchResult
	 * @throws SearchDAOException on error searching the collateral
	 */
	public SearchResult searchCollateral(CollateralSearchCriteria criteria) throws SearchDAOException;

	/**
	 * Get all collateral sub types given the type code.
	 * 
	 * @param typeCode security type code
	 * @return a list of collateral sub types
	 * @throws SearchDAOException on error searching the collateral subtypes
	 */
	public ICollateralSubType[] getCollateralSubTypesByTypeCode(String typeCode) throws SearchDAOException;

	/**
	 * Get the collateral sub type given the sub type code.
	 * 
	 * @param subTypeCode security sub type code
	 * @return the collateral sub type
	 * @throws SearchDAOException on error searching the collateral subtypes
	 */
	public ICollateralSubType getCollateralSubTypesBySubTypeCode(String subTypeCode) throws SearchDAOException;

	/**
	 * Get the number of securities a pledgor has.
	 * 
	 * @param pledgorID pledgor id
	 * @return security count
	 * @throws SearchDAOException on error getting the security count
	 */
	public int getCollateralCountForPledgor(long pledgorID) throws SearchDAOException;

	/**
	 * Get if BCA Completed for the given collateral id.
	 * 
	 * @param collateralID collateral id
	 * @return true if the bca is completed, otherwise false.
	 * @throws SearchDAOException on error getting the bca flag
	 */
	public boolean isBCACompleted(long collateralID) throws SearchDAOException;

	/**
	 * Get collateral valuer list by security location
	 * 
	 * @param countryCode security location
	 * @return Hashmap with valuer code and name
	 * @throws SearchDAOException on error getting valuer list
	 */
	public Map getCollateralValuer(String countryCode) throws SearchDAOException;

	/**
	 * Get a list of collateral pledgors.
	 * 
	 * @param collateralID cms collateral id
	 * @return a list of ICollateralPledgor objects
	 * @throws SearchDAOException on error getting the collateral pledgors
	 */
	public ICollateralPledgor[] getCollateralPledgors(long collateralID) throws SearchDAOException;

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
			throws SearchDAOException;

	public List getDisTaskBcaLocationList(long collateralId) throws SearchDAOException;

	/**
	 * Get the customer name of the collateral
	 * 
	 * @param collateralID
	 * @return String - customer name
	 * @throws CollateralException on errors encountered
	 */
	public String getCustomerNameByCollateralID(long collateralID) throws SearchDAOException;

	/**
	 * update security perfection date by AA Number
	 * 
	 * @param aANumber AA Number
	 * @return updatedrecord count
	 * @throws SearchDAOException on error updating security perfection date
	 */
	public int updateSecPerfectDateByAANumber(long aANumber) throws SearchDAOException;

	/**
	 * Retrieving different types of Valuation
	 * @param collateralId
	 * @return
	 * @throws SearchDAOException
	 */

	public IValuation getSourceValuation(long collateralId, String SourceType) throws SearchDAOException;

	public IValuation[] getValuationFromLOS(long collateralId) throws SearchDAOException;

	public IValuation getStageSourceValuation(long collateralId, String SourceType) throws SearchDAOException;

	public IValuation[] getStageValuationFromLOS(long collateralId) throws SearchDAOException;

	public String getLiquidationIsNPL(long collateralID) throws SearchDAOException;

	public boolean canFullDeleteCollateral(long collateralId, String securityId) throws SearchDAOException;

	public List getSecSystemName(long collateralId) throws SearchDAOException;

	/**
	 * Get the collateral sub type ID by collateral ID.
	 * 
	 * @param colID collateral ID
	 * @return the collateral sub type
	 * @throws SearchDAOException on error searching the collateral subtype
	 */
	public ICollateralSubType getCollateralSubTypeByCollateralID(Long colID) throws SearchDAOException;
	
	public ICollateralSubType getCollateralByCollateralID(Long colID) throws SearchDAOException;
	

	/**
	 * Get the charge details ID if charge details of the collateral is found
	 * and it is not link to the limit.
	 * 
	 * @param cmsLimitID cms Limit ID
	 * @param collateralID cms collateral ID
	 * @return the charge details ID of type Long
	 * @throws SearchDAOException on error searching the charge details
	 */
	public Long getUnlinkChargeDetailID(long cmsLimitID, long collateralID) throws SearchDAOException;

	/**
	 * Get the limit charge map if charge details of the collateral is found and
	 * it is link to the limit.
	 * 
	 * @param cmsLimitID cms Limit ID
	 * @param collateralID cms collateral ID
	 * @return the limit charge map of type ILimitChargeMap
	 * @throws SearchDAOException on error searching the limit charge map
	 */
	public ILimitChargeMap getLimitChargeMapToUnlink(long cmsLimitID, long collateralID) throws SearchDAOException;

	public SearchResult searchCollateralByIdSubtype(LmtColSearchCriteria criteria) throws SearchDAOException;

	public List getSecSubtypeList() throws SearchDAOException;

	public IBookingLocation[] getAllBookingLocation() throws SearchDAOException;

	/**
	 * Update or insert Stp Ready get triggered from creation/update of
	 * collateral. And the stp ready status will be used later, eg, to display
	 * the stp ready status.
	 * 
	 * @param transactionId transaction id for the collateral transaction
	 * @param isStpReady indicate whether the collateral is ready to be stp
	 */
	public void updateOrInsertStpReadyStatus(String transactionId, boolean isStpReady);
	
	public void purgeLadData();

	/**
	 * Get the policy no is already exist in the database or not
	 * 
	 * @param String policyNo the policy number to be checked against the
	 *        uniqueness
	 * @param insurancePolicyReferenceId the insurance policy reference id,
	 *        which is the key between actual sand staging, useful when resubmit
	 *        case for the same insurance policy
	 * @return boolean - policy no exist = true, else false
	 * @throws SearchDAOException on errors encountered
	 */
	public boolean getPolicyNumber(String policyNo, String insurancePolicyReferenceId) throws SearchDAOException;

    /**
     * Get the collateral name is already exist in the database or not
     *
     * @param collateralName the collateral name to be checked against the
	 *        uniqueness
     * @param cmsCollateralId
     * @return boolean - collateral name no exist = true, else false
     * @throws SearchDAOException on errors encountered
     */
	public boolean getCollateralName(String collateralName, long cmsCollateralId) throws SearchDAOException;   
	
	public List getCollateralCodeBySubTypes(String typeCode) throws SearchDAOException;
	
	public ICountry[] getListAllCountry() throws SearchDAOException;
	
	public ISystemBankBranch[] getListAllSystemBankBranch(String country) throws SearchDAOException;
	
	public IForexFeedEntry[] getCurrencyList() throws SearchDAOException;
	
	public String getMaxBaselSerialNo(OBCashDeposit obcashDep) throws SearchDAOException;
	
	public void setAllFixedFd(ICashDeposit fd) throws SearchDAOException;
	
	public void setAllFixedFdByReceiptNo(ICashDeposit fd,String receiptNo) throws SearchDAOException;
	
	/*
	 * Add by Govind S:07/09/2011
	 * Get System Bank Branch By Country Code and Branch Code
	 */
	public ISystemBankBranch getSysBankBranchByCuntryAndBranchCode(String country , String branchCode) throws SearchDAOException;
	
	public String getPartyNamebyCode(long partyCode) throws SearchDAOException;
	
	public List getSystemList(long partyCode) throws SearchDAOException;
	
	public List getLineList(long partyCode) throws SearchDAOException;
	
	//Get Country Name by Country Code
	public ICountry[] getCountryNamebyCode(String countryCode) throws SearchDAOException;
	
	/*
	 * Govind S:07/09/2011:End line here
	 */
	
	/**
	 * Get the customer id of the collateral
	 * 
	 * @param collateralID
	 * @return String - customer name
	 * @throws CollateralException on errors encountered
	 */
	public String getCustomerIDByCollateralID(long collateralID) throws SearchDAOException;
	
	
	public String getCustomerCIFIDByCollateralID(long collateralID) throws SearchDAOException;
	
	/**
	 * method to retrieve the recurrent due date list by customer id and collateral id
	 * 
	 * @param customerID
	 * @param cmsCollatralId
	 * @return
	 */
	public List getRecurrentDueDateListByCustomerAndCollatralID(long customerID, long cmsCollatralId);

	/*
	 * Add by Govind S:25/10/2011
	 * Get Collateral Code with Description
	 */
	public List getCollateralCodeDesc() throws SearchDAOException;
	
	/*
	 * Add by Govind S:31/10/2011
	 * Get Country Name List with Code
	 */
	public List getCountryNameList() throws SearchDAOException;
	
	/*
	 * Add by Govind S:31/10/2011
	 * Get SysBankBranch Name List with Code
	 */
	public List getSysBankBranchNameList() throws SearchDAOException;
	
	
	public String getCamIdByCollateralID(String collateralID) throws SearchDAOException;
	
	public String getCamIdByCustomerID(String customerID) throws SearchDAOException;
	
	/*
	 * Added by Anil
	 * for notification to retrieve the releasable amount of facility by collateral ID
	 */
	public ILimit getReleasableAmountByCollateralID(long collateralID) throws SearchDAOException;

	/*
	 * Added by Anil
	 * for GC to retrieve the Statement Name from Recurrent master by doccode 
	 */
	public String getStatementNameByDocCode(String docCode)throws SearchDAOException; 
	
	
	public void removeExtraEntryFromLSM(String partyId) throws SqlException ;

	public String getCustomerLimitProfileIDByCollateralID(long collateralID) throws SearchDAOException ;
	
	public void updateStatusOfChecklistItemForGC(long checklistId, String documentCode, String date) throws SqlException ;
	
	public void executeReceivedStatementProc(long collateralId) throws Exception ;
	
	public IEODStatus executeReceivedStatementProc(IEODStatus eodStatus)  ;
	
	public IEODStatus executeFdDeletedBackupProc(IEODStatus eodStatus)  ;
	
	public boolean executeLADExpiryUpdate()  ;

	public IEODStatus executeFacChargeIdProc(IEODStatus eodStatus)  ;
	
	public String getReceiptNoByDepositID(String cashDepID);
	
	public String getStageReceiptNoByDepositID(String cashDepID);
	
	public double getTotalLienAmountByCollID(String CollId);
	
	public int getNoOfDepositByDepositReceiptNo(String receiptNo)	throws SearchDAOException;
	
	public IEODStatus executeSPInactiveFdProc(IEODStatus eodStatus);
	
	public String getSerchFdFlagByDepositID(String cashDepID,String receiptNo);
	public void executeEOYProc() throws Exception ;
	public void executeFacSecNewMviewProc()  ;
	//public void executeRecurNewMviewProc()  ;
	public void executeCustLimitMviewProc();


	public String getFdEnableFlag() throws SearchDAOException;

	//Start: Uma Khot:added as LAD expiry taking time for exec. So Splitting into 2 parts.
	public void executeLADExpiryUpdateStage();
	//End: Uma Khot:added as LAD expiry taking time for exec. So Splitting into 2 parts.
	

	public void executeCmsInterfaceLogBackupProc();
	
	public void executeIfscCodeInterfaceLogBackupProc();
	
	public List getSeurityCodeAndType(String securityCode) throws SearchDAOException ;
	
	//Added by Uma Khot: PHASE 3 CR Start:For Create Multiple Security – validation Popup message on 21-OCT-2015  
	public List getSecurityTypeSubTypeForParty(long limitProfileId) throws SearchDAOException;
	//Added by Uma Khot: PHASE 3 CR End:For Create Multiple Security – validation Popup message on 21-OCT-2015  
	
	// Phase 3 CR :Limit Calculation Dashboard 
	public double getAllTotalLienAmountBySubProfileId(String partyId,String lmtId,String lineNo)throws SearchDAOException;

	public List getSeurityNames() throws SearchDAOException ;
	
	// Santosh IRB
	public String getDpCalculateManuallyByDateAndDocCode(Date selectedDueDate, String selectedDocCode,Long cmsCollateralId);
	

	//Stock DP calculation
	public List getStockAndDateDetailsWithAssetId(Long cmsCollateralId);
	public List getStockAndDateDetailsForEachAssetId(String  cmsCollateralId);
	public List getLocationForEachAssetId(String  cmsCollateralId);
	public List getStockDisplayList(String  cmsCollateralId);

	public IEODStatus executeFCUBSActivities(Date startDate,Date endDate, IEODStatus eodStatus);
	
	public Map getCollateralCategoryAndCersaiInd(Long cmsCollateralId);

	public void executeFacilityIntLogBkpProc();

	public void executeLiabilityIntLogBkpProc();

	public double getCalculateddpValueByDueDate(String valueOf);

	public void executePSRActivities(Date startDate,Date endDate);

	public void updateLatestValId(String collateralId,String valuationId,String columnName);
	public void createPreviuosMortgageData(final IPropertyCollateral iPropertyCollateral,final String id);

	public Long getNextSequnceNumber(String squenceName);

	public int CheckPreviousMortData(long collateralID, String salePurchaseDate);
	public void updatePreviousMortagageData(final IPropertyCollateral iPropertyCollateral);
	public List getPreviousValList(long collateralId);
	public List getPreMortgageCreationList(long collateralId);
	public List getPreMortgageData(String preMortgageDate, String collateralID );
	public List getPreviousVal3List(long collateralId);
	public List getPreviousValList2(long collateralId);
	int syncPropertyValuation(long collateralId, String valNo, boolean isMaster);

	public String getPartyIdAndGrade(String collateralId);
	public List<OBValuationAmountAndRating> getValuationByRamRating(String grade);
	public String isValuation2Mandatory(String partyId, String totalPropertyAmt, List<OBValuationAmountAndRating> dataResult);

	public int getLimitIdByMapping(String collateralId);
	public String getPartyIdAndGradeFromStage(String collateralId);

	public String getVersion(long collateralId,int version ) ;

	public List getFacilityList();
	
	public ArrayList getXrefIdList(long collateralId);

	public void updateStatusSuccessToPending(ArrayList xrefIdList,ArrayList sourceRefNoList, String stockdocMonth, String stockdocYear);

	public void updateStagingStatusSuccessToPending(ArrayList xrefIdList,ArrayList sourceRefNoList, String stockdocMonth, String stockdocYear);

	public List<HashMap<String, String>> getDetailsForFccColFdileUploadJob(String systenName);
	

	public List getFacilityNameList(long partyCode);
	public List getFacilityIdList(String partyCode, String facilityName);
	
	List<HashMap<String, String>> getDetailsForSBBGSBLCFileUploadJob();
	List<HashMap<String, String>> getDetailsForMfEquityFileUploadJob();
	List<HashMap<String, String>> getDetailsForFdDepositFileUploadJob();
	
	public List<LabelValue> getStockDocLocations(String docCode, long collateralId);
	
	public List<LabelValue> getDueDateList(Long customerId);
	
	public Map<String, String> getStatementNames(Long stageCollateralId) throws SearchDAOException;
	
	public Map<String, String> getNonApprovedLinkedFacilities(Long collateralId);
	
	public BigDecimal getTotalLimitReleasedAmtForLinkedFacilities(Long collateralId);

	public void updateStagingLoccalCads(String relationshipMgrCode);

	public void updateActualLoccalCadsDelete(String relationshipMgrCode,String CadEmployeeId,String localCADStatus);

	public void updateStagingLoccalCadsDelete(String relationshipMgrCode,String CadEmployeeId,String localCADStatus);

	public void updateStagingLoccalCadsCreate(String relationshipMgrCode, String localCADEmployeeCode,
			String localCADStatus);

	public void updateStageNewCreatedLocalCadsDelete(String relationshipMgrCode, String localCADEmployeeCode,
			String localCADStatus);

	public void updateStatusSuccessToPendingThroughFacility(ArrayList xrefIdList, ArrayList sourceRefNoList,
			String stockdocMonth, String stockdocYear);

	public void updateStagingStatusSuccessToPendingThroughFacility(ArrayList xrefIdList, ArrayList sourceRefNoList,
			String stockdocMonth, String stockdocYear);
	
	public ArrayList getXrefIdListFromLmtId(String facilityLmtId);
	public ICollateralAllocation[] retrieveCollateralList(StringBuffer sb, ICollateralAllocation[] allocList);
	
	public String getCustomerLimitProfileIDByStagingCollateralID(long collateralID) throws SearchDAOException ;	
	public List getGoodsParentCodeList();
	
	public List getRestrictionTypeList();
	public ArrayList getChildsAgaintParentGoodsCode(String goodsParentCode);
	public List getFacilityNameList() throws SearchDAOException;

	public String getMigrationflagStatusUsingDuedate(String selectedDueDate, String selectedDocCode,Long cmsCollateralId);
	
	BigDecimal getCalculatedDpForGCCollateral(Long collateralId);
	
	public boolean checkSecurityId(String collateralId) ;

	public OBCollateral searchCollateralByIdSubtypeRest(LmtColSearchCriteria crit);
	
	public String[] getCollateralTypeAndSubtype(String CMS_COLLATERAL_ID) throws SearchDAOException;
	
	public String[] getColCatAndCersaiFlag(String CMS_COLLATERAL_ID) throws SearchDAOException ;
	
	public String getSubtypeCodeFromSecurity(String CMS_COLLATERAL_ID);
	
	public boolean checkInsurancePolicyId(String insurancePolicyId,String collateralId);
	
	public boolean checkUniqueInsuranceId(String uniqueInsuranceId,String collateralId) ;
	
	public boolean checkCollateralIdCount(String collateralId, String camId);
	
	public boolean checkPreviousMortCreationDate(String collateralId,String prevDate);
	
	public boolean checkAddDocFacDetailsId(String adddocfacID,String collateralId);
	
	public boolean checkuniqueAddDocFacDetailsId(String docfacId,String collateralId);
	
	public boolean checkSecBranchCount(String secBranch, String secCountry) ;

	public String getSecLocFromSecurity(String CMS_COLLATERAL_ID)
			throws SearchDAOException;

	public boolean checkUniqueStockId(String uniqueStockId, String securityId);
	public boolean checkUniqueStockLineId(String uniqueStockId, String securityId);

	public boolean checkStockExchange(String entryCode);

	public List getstockLinelList(String string) throws SearchDAOException ;

	public String getInsuranceStatus(String insurancePolicyID)
			throws SearchDAOException;

	public String getSubProfileIdFromSecurityId(String secId) throws SearchDAOException;

	public String[] getCustomerNameAndEntity(String securityId);
	
	public void executeDeferralInsurancePolicy();
	public void executeDeferralGCPolicy();
	public void insertDeferralChecklistProperty();
	
	public String getGcInsuranceStatus(String insurancePolicyID) throws SearchDAOException;

	List securityCoverageByCollateralId(Long collateralId, String limitId);

	void updateSecurityCoverage(long chargeId, int securityCoverage);

	void updateSecurityCoverageStg(long chargeId, int securityCoverage);
	public void updateActualCollateral(ICollateral actualColMap);
}
