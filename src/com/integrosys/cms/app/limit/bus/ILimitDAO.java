package com.integrosys.cms.app.limit.bus;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.aurionpro.clims.rest.dto.CommonCodeRestRequestDTO;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.cms.app.FileUploadLog.OBFileUploadLog;
import com.integrosys.cms.app.chktemplate.bus.OBDocumentItem;
import com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntry;
import com.integrosys.cms.app.customer.bus.ILineCovenant;
import com.integrosys.cms.app.customer.bus.ICoBorrowerDetails;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.customer.bus.OBCustomerSysXRef;
import com.integrosys.cms.app.json.dto.IJsInterfaceLog;
import com.integrosys.cms.app.json.dto.OBJsInterfaceLog;
import com.integrosys.cms.app.json.line.dto.RetrieveScmLineRequest;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.OBNpaProvisionJob;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.ws.dto.DigitalLibraryRequestDTO;
import com.integrosys.cms.app.ws.dto.DigitalLibraryRequestDTOForV2;
import com.integrosys.cms.app.ws.dto.OBFCUBSDataLog;
import com.integrosys.cms.app.ws.dto.OBPSRDataLog;
import com.integrosys.cms.batch.dfso.borrower.OBDFSOLog;
import com.integrosys.cms.ui.createfacupload.OBCreatefacilitylineFile;

import java.util.Date;

public interface ILimitDAO {
	/**
	 * Get all collateral types avaiable in CMS based on the criteria.
	 * 
	 * @return a list of collateral types
	 * @throws SearchDAOException on error searching the collateral type
	 */
	public ILimitProfile getLimitProfileLimitOnly(long limitprofileID) throws LimitException;

	public java.util.HashMap getLimitProfileIds(long collateralId) throws LimitException;

	/**
	 * Checks AA number must be unique in the system.
	 * 
	 * @param bcaRefNo
	 * @param lmtProfileID
	 * @return boolean true if there is duplicate AA number exists, otherwise
	 *         false
	 * @throws LimitException
	 */
	public boolean checkDuplicateAANumber(String bcaRefNo, long lmtProfileID) throws LimitException;

	/**
	 * Checks the specified customer has active limit profile link to it.
	 * 
	 * @param customerID the customer ID
	 * @param lmtProfileID the limit profile ID
	 * @return boolean true if there is active limit profile link to the
	 *         customer, otherwise false
	 * @throws LimitException
	 */
	public boolean checkCustHasLimitProfile(long customerID, long lmtProfileID) throws LimitException;

	/**
	 * Get limit product type based on limit ID list
	 * 
	 * @param limitIDList the list of limit ID
	 * @return Hashmap of the limit ID as key and product type as value
	 * @throws LimitException
	 */
	public HashMap getLimitProductTypeByLimitIDList(ArrayList limitIDList) throws LimitException;

	/**
	 * Check if a limit profile, all it's facility, collateral has completed the
	 * stp process.
	 * 
	 * @param limitProfile limit profile to be checked
	 * @return whether the STP process of all the facility and collateral of the
	 *         limit profile provided has been completed
	 */
	public boolean checkLimitProfileStpComplete(ILimitProfile limitProfile);

	public HashMap getAccountInfoByLimitId(long limitId);

	/**
	 * <p>
	 * Check whether the limit profile has response back to the source system
	 * after all the stp process has completed.
	 * <p>
	 * {@link #checkLimitProfileStpComplete(ILimitProfile)} will be called after
	 * this method is invoked.
	 * 
	 * @param cmsLimitProfileId CMS Limit Profile internal key
	 * @return whether the limit profile has responsed back to source system
	 */
	public boolean checkLimitProfileHasResponseToSource(long cmsLimitProfileId);

	/**
	 * Insert a response log which required to be response back to LOS, or else
	 * clear the indicator which will let the scheduler to pick up to fire the
	 * response back to LOS.
	 * @param limitProfile a limit profile object, minimum consist of limit
	 *        profile id, LOS AA Number, and Host AA Number
	 */
	public void insertOrUpdateLimitProfileResponseLog(ILimitProfile limitProfile);

	/**
	 * To retrieve list of CMS Limit Profile internal keys which not yet fire
	 * response to source
	 * @return a list of CMS Limit Profile internal keys
	 */
	public List retrieveListOfCmsLimitProfileIdNoResponseToSource();

	/**
	 * Retrieve the latest transaction value record (either Collateral or
	 * Facility) that belong to the Limit Profile with the key supplied.
	 * 
	 * @param cmsLimitProfileId CMS Limit Profile internal key
	 * @return latest transaction record, <code>null</code> will be sent back to
	 *         caller if there is no facility or collateral (but shouldn't be
	 *         that case)
	 */
	public ICMSTrxValue retrieveLatestCollateralOrFacilityTrxValueByCmsLimitProfileId(long cmsLimitProfileId);

	/**
	 * Update Limit Profile response log after the response has been fired to
	 * source. Response Fire date will be updated to current date.
	 * 
	 * @param limitProfile Limit Profile object, the important one is the CMS
	 *        internal key.
	 */
	public void updateLimitProfileResponseLogAfterFired(ILimitProfile limitProfile);

	/**
	 * <p>
	 * Retrieve list of CMS collateral internal key based on the CMS Limit
	 * Profile id provided. Which mean all the collaterals that belong to the
	 * Limit Profile
	 * <p>
	 * Can provided list of filtered facility codes of which facilities that
	 * collaterals pledged to.
	 * @param cmsLimitProfileId CMS Limit Profile internal key
	 * @param filteredFacilityCodes To filter collateral that it's linked
	 *        facility codes meet this parameters, <b>optional</b>
	 * @return list of CMS collateral internal key
	 */
	public List retrieveUniqueCmsCollateralIdListbyLimitProfileId(long cmsLimitProfileId, String[] filteredFacilityCodes);

	/**
	 * Retrieve AA basic information by CMS Limit Profile Id, such as AA number,
	 * customer id, booking location, application type, CIF/LE Id
	 * @param cmsLimitProfileId CMS Limit Profile Id
	 * @return AA with basic information
	 */
	public ILimitProfile retrieveLimitProfileByCmsLimitProfileId(Long cmsLimitProfileId);

	
	//Start:Uma Khot:Don't Delete the facility if facility doc pending in case creation.
	public boolean getActualCaseCreationDetails(String apprLmtId) throws SearchDAOException, RemoteException;
	
	public boolean getStageCaseCreationDetails(String lmtID)throws SearchDAOException, RemoteException;
	//End:Uma Khot:Don't Delete the facility if facility doc pending in case creation.
	
	//Start:Uma Khot:Don't Delete the facility if facility doc pending in facility checklist.
	public boolean getPendingFacilityDocCount(String apprLmtId);
	//End:Uma Khot:Don't Delete the facility if facility doc pending in facility checklist.
	
	public boolean isCpsSecurityIdUnique(String cpsId) ;
	
	public void updateSanctionedLimitToZero(String camId);
	
	public List getFacNameList() throws SearchDAOException;
	
	public boolean checkFacilityDocumentsIsPendingReceived(String apprLmtId) throws SearchDAOException, RemoteException;

	public boolean getPendingPropertySecCount(List<String> collteralIds);
	public boolean getLimitSecurityCount(long limitProfileId, String collteralId,long limitID);


	public String[] getListOfMappedSecuritiesWithFacility(String apprLmtId) throws SearchDAOException, RemoteException;
	
	public boolean checkFacilityDocumentsIsReceived(String apprLmtId) throws SearchDAOException, RemoteException;
	

	public OBCustomerSysXRef[] getLimitProfileforFCUBSFile() throws LimitException;
	
	public OBCustomerSysXRef[] getCoBorrowerList( OBCustomerSysXRef obCustomerSysXRef, long xrefId) throws LimitException;
	
	public void updateStageLineDetails(final String sourceRefNo,final String segment1Flag,final String prioritySecFlag,final String esatateTypeFlag,final String capitalFlag, final String unconFlag,final String xrefId,final String branchAllFlag,final String productAllFlag,final String currencyAllFlag, final String limitRestrFlag ,final String sendToCore ,final String commRealEstateTypeFlag);
	public int generateSourceSeqNo();

	public List getCurrencyList();
	public String getRuleId(String facilityCode);
	public String getCurrencyRestriction(String facilityCode);
	public String getRevolvingLine(String facilityCode);
	public String getScmFlag(String facilityCode);
	public String getBorrowerScmFlag(String custId);
	public String getBorrowerScmFlagForAlert(String custId);
	public double getReleaseAmountForParty(String partyId);
	public String getLineCurrency(String facilityCode);
	public String getIntradayLimit(String facilityCode);
	public String getLineDescription(String facilityCode);
	public Date getCamExtentionDate(String limitProfileIDStr);
	public Date getCamDate(String limitProfileIDStr);
	public String getSeqNoForFile() throws Exception;
	public String getProductCodeFromId(String productId)throws SearchDAOException;
	public String[] getUDFDetails(String udfField,long xrefId)throws SearchDAOException;
	public String[] getUDFDetails2(String udfField,long xrefId)throws SearchDAOException;

	public OBFCUBSDataLog fetchPartyDetails(OBCustomerSysXRef obCustomerSysXRef, OBFCUBSDataLog fcubsObj) throws Exception;
	public OBFCUBSDataLog fetchMakerCheckerDetails(OBCustomerSysXRef obCustomerSysXRef, OBFCUBSDataLog fcubsObj) throws Exception;
	public void updateStatus(long xrefId, String sourceRefNo) throws DBConnectionException, SQLException,Exception;
	
	// Duplicate record sent to core issue
	public void updateStatusSchedulerProgress(long xrefId, String sourceRefNo) throws DBConnectionException, SQLException,Exception;
	public void updateStatusSchedulerCompleted(long xrefId, String sourceRefNo) throws DBConnectionException, SQLException,Exception;
	
	public void clearUDFFields(String sourceRef) throws DBConnectionException, SQLException,Exception;
	public void updateFCUBSDataLog(String sourceRef, Map<String, String> map,
			String fcubsStatusSuccess,Date responseDate) throws Exception;
	public void updateLineDetails(String sourceRef, Map<String, String> map,
			String fcubsStatusSuccess) throws DBConnectionException, SQLException,Exception;
	
	public String getBranchCodeFromId(String branchId)throws SearchDAOException;
	public String getDescFromCode(String code)throws SearchDAOException;
	public ArrayList<String[]> getFileNames() throws SearchDAOException,Exception;
	
	public void updateStageSendToCore(final String xrefId,final String sendToCore);
	
	
	public void updateStageLineDetails(final String sourceRefNo,final String segment1Flag,final String prioritySecFlag,final String esatateTypeFlag,final String capitalFlag, final String unconFlag,final String xrefId,final String branchAllFlag,final String productAllFlag,final String currencyAllFlag, final String limitRestrFlag,final String sendToCore,
			final String status,final String action,final String coreRejectReason,final String udfDelete,final String commRealEstateTypeFlag,final String udfDelete2);
	

	public void updateFCUBSDataLogDuringCoreDown(String sourceRef,String serialNo,
			String rejectReason,String fcubsStatusSuccess) throws Exception;

	public List<String> getlmtId(long limitProfileId, String facilitySystem, String lineNumbers);

	public void updateStageActualLine(String available, String status, Date newExtendedNextReviewDate, Map<String, String> sysXrefIdmap, String name);
	

	public String getProductIdList(String productCodes);

	
	public String getStatusFromFcubs(String sourceRefNo);
	public Map<String,String >  getRejectedFcubsMap(String sourceRefNo);
	public Map<String,String >  getSuccessFcubsMap(String sourceRefNo);
	public Map<String,String >  getRejectedPSRMap(String sourceRefNo);
	public Map<String,String >  getSuccessPSRMap(String sourceRefNo);
	
	public BigDecimal getSubLimitReleasedAmt(String lmtId,String system);
	public String getActiveProductCodeFromId(String productId)throws SearchDAOException ;

	public String getChecklistId(long limitProfileID);

	public void updateChecklistDetails(String checkListId,String todayYear,String ramRatingYear);

	public String getChecklistIdByPartyId(String partyId);

	public String getLimitProfileID(String partyId);
	
	public String getCustomerFyClosure(long limitProfileID);
	
	public void updateRAMChecklistDetails(String checkListId);

	public OBDocumentItem getAllRamratingDocument();
	
	public String getDocSeqId();
	
	public void insertRAMStatement(String customerFyClosure, String docId, OBDocumentItem ramRatingChecklist, String checkListId, String docRefId, String year);
	
	public String getOldRAMYear(long limitProfileID);

	public void disableRAMChecklistDetails(String checkListId);
	public boolean getNewPendingFacilityDocCount(String apprLmtId);
	public String  getFacLiabErrorDesc(String errorCode);
	public String getFacDetFromMaster(OBCreatefacilitylineFile oBCreatefacilitylineFile);
	public Set getCommonCode(String categoryCode);
	public Set getCurrencySet();
	public Set getValidPartySet();
	public HashMap getFacMaster();
	public List getPartySystemId(String system, String partyId) throws SearchDAOException;
	public BigDecimal getfundedNonfunAmt(String facilityType,String partyId);
	public List getSanctionedAmtOfLmt(String partyId,String facilityType);

	public OBDocumentItem getSCODDocument();
	public void insertSCODChecklistItem(String docId, OBDocumentItem scodDocument, long checkListId, String docRefId, String remarks);

	public void updateFlagsFlagForLMTSTP(ILimitTrxValue lmtTrxObj);
	
	public List getImageIdList(String code, DigitalLibraryRequestDTO digitalLibraryRequestDTO) throws SearchDAOException;
	
	public Boolean getPartyId(String code) throws SearchDAOException;
	
	public Boolean getPanNo(String panNo) throws SearchDAOException;
	
	public Boolean getPartyName(String code) throws SearchDAOException;
	
	public Boolean getPartyNameAndPartyID(String partyName,String PartyID) throws SearchDAOException;
	
	public Boolean getPartyNameAndSystemID(String partyName,String SystemId) throws SearchDAOException;
	
	public Boolean getPanNoAndSystemId(String panNo,String systemId) throws SearchDAOException;
	
	public Boolean getSystemIDAndPartyIDAndPanNo(String systemId,String partyId,String panNo) throws SearchDAOException;
	
	public Boolean getSystemIDAndPartyID(String systemId,String partyId) throws SearchDAOException;
	
	public Boolean getPanNoAndPartyID(String panNo,String partyId) throws SearchDAOException;

	public String getPartyIdByPanNo(String panNo)throws SearchDAOException;
	
	public String getPartyIdBySystemId(String systemId)throws SearchDAOException;

	public boolean getSystemName(String systemName)throws SearchDAOException;
	
	public String getEntryCode(String entryName,String categoryCode)throws SearchDAOException;

	//For PSR Limit hand off
	public OBCustomerSysXRef[] getLimitProfileforPSRFile() throws LimitException;
	public String getSeqNoForPSRFile() throws Exception;
	public Map<String,String> getDataforPSRFile(long xref_ID) throws Exception;
	public OBPSRDataLog fetchPartyDetails(OBCustomerSysXRef obCustomerSysXRef, OBPSRDataLog psrObj) throws Exception;
	public OBPSRDataLog fetchMakerCheckerDetails(OBCustomerSysXRef obCustomerSysXRef, OBPSRDataLog psrObj) throws Exception;
	public ArrayList<String[]> getPSRFileNames() throws SearchDAOException,Exception;
	public void updatePSRLineDetails(String sourceRef, Map<String, String> map, String psrFileStatus) throws DBConnectionException, SQLException,Exception;
	public void updatePSRDataLog(String sourceRef, Map<String, String> map, String psrFileStatus,Date responseDate) throws Exception;
	public List<String> getPSRlmtId(long limitProfileId, String facilitySystem);
	public void updatePSRStageActualLine(final String available, final String status,
			final Date newExtendedNextReviewDate, Map<String, String> sysXrefIdmap, String name);
	public ArrayList getCollateralIdList(long xrefId) throws Exception ;

	public String getStockDocMonthByColId(String colId) throws Exception ;

	public String getStockDocYearByColId(String colId) throws Exception ;
	public OBNpaProvisionJob[] getNpaProvisionFileDetails();

	public List getFacNameList(String facCat, String system) throws SearchDAOException;
	
	public List getNpaDailyStampingCount() throws Exception ;
	
	public List getEwsStockDeferral() throws Exception ;

	public String getSeqNoForDocBarCode()  throws Exception;;

	//For SCM scheduler
	public List<Long> getFailedReleaseLineRequestforScm();
	public List<OBJsInterfaceLog> getInterfaceLogDetailsForLine(Long id);
	public void updateTheFailedResponseLog(OBJsInterfaceLog log) ;
	
	//For SCM STP
	public String getScmFlagStgforStp (String srcRefNo);
	public String getScmFlagMainforStp (String srcRefNo);
	public void insertLogForSTP(OBJsInterfaceLog log);
	//public RetrieveScmLineRequest getLineDetailsforStp(String srcRefId);
	public List<RetrieveScmLineRequest>  getLineDetailsforStp(String srcRefId) ;
	public String getLatestOperationStatus(String limitProfileId,String LineNo,String SerialNo ) ;
	public String generateSourceSeqNoForStp();
	public String getSeqNoForOtherCovenant() throws Exception;
	//public String checkUserRoleAccess(String role, String moduleId, String operation) throws SearchDAOException;

	public boolean getCheckDocumentCode(String docCode) throws SearchDAOException;

	public boolean getEmployeeCodeRM(String employeeCodeRM) throws SearchDAOException;

	
	public List<ICoBorrowerDetails> getPartyCoBorrowerDetails(Long customerId);

	public List getRestrictedCoBorrowerForLine(long lmtID)  throws SearchDAOException;;
	public boolean isCoBorrowerAddedInFacility(String coBorrowerId, long profileId);
	
	public String getActualLineXrefIdNew(long cmssid)  throws SearchDAOException;

	public void insertLineCoBorrowers(String xrefId, String borroId, String borrowName);

	public String checkUserRoleAccess(String role, String moduleId, String operation) throws SearchDAOException;

	public List getStockStatementDPYes();
	public List getStockStatementDPNo();
	public List getDiscrepencyDeferralFacility();
	public List getDiscrepencyDeferralGeneral();
	public List getDFSOJobDetails();
	public void insertLogForDFSO(OBDFSOLog log);
	public String generateSeqForDFSO();
	
	public List getScfStatusForLineById(String limitProfileId, String LineNo, String SerialNo);
	public List getImageIdListForV2(String code, DigitalLibraryRequestDTO digitalLibraryRequestDTO,String imgid) throws SearchDAOException;
	
	public List getImageIdListForV2(DigitalLibraryRequestDTO digitalLibraryRequestDTO,String code,String imgid) throws SearchDAOException;


	
	//Covenant
	public ILimitCovenant[] getCovenantData(String LmtId,String tableType) throws LimitException;
	public ILineCovenant[] getLineCovenantData(String LmtId,String tableType) throws LimitException;
	public ILineCovenant[] getCovenantDataForMissingLine(String lmtID,String tableType) throws LimitException;
	public List getCountryList() throws LimitException, RemoteException;
	public List getBankList() throws LimitException, RemoteException;
	public List getCurrList() throws LimitException, RemoteException;
	public List getGoodsList() throws LimitException, RemoteException;
	public List getRestrictedCountryList(String LmtId) throws SearchDAOException;
	public List getRestrictedCurrencyList(String LmtId) throws SearchDAOException;
	public List getRestrictedBankList(String LmtId) throws SearchDAOException;
	public List getRestrictedCustomerList(String LmtId) throws SearchDAOException;

	public List getRestrictedCountryListForLine(String LmtId) throws SearchDAOException;
	public List getRestrictedCurrencyListForLine(String LmtId) throws SearchDAOException;
	public List getRestrictedBankListForLine(String LmtId) throws SearchDAOException;
	public List getRestrictedCustomerListForLine(String LmtId) throws SearchDAOException;
	
	public String getIncoDesc(String incoTerm) throws SearchDAOException;
	public List getIncoTermList() throws SearchDAOException;
	
	public ILineCovenant[] getLineCovenantDataForStp(long xrefId) throws LimitException;
	public HashMap getCountryCodeList() throws SearchDAOException;

	public void clearMainCovenant(String string);
	public String getFCUBSCountryCode(String code)throws SearchDAOException ;
	
	public String getExclusionLine() throws SearchDAOException;

	
	public Boolean getEntry_id(String entry_id) throws SearchDAOException;
	public Boolean getEntry_name(String entry_name) throws SearchDAOException;
	public Boolean getEntry_code(String entry_code) throws SearchDAOException;
	public Boolean getCategory_code(String category_code) throws SearchDAOException;

	public List<OBCommonCodeEntry> getCommonCodeList(CommonCodeRestRequestDTO commonCodeRestRequestDTO)throws SearchDAOException; 
	
	
	public void executeSpCollateralMove(String PartyId) throws SQLException;
	public String getPartyIdUsingLimitProfileId(String LimitId);
	
	
	public boolean checkVendorCount(String camId, String vendorName) ;
	
	
	public boolean checkLiabBrancProdCurrCount(String module, String code);
	
	public boolean checkSystemCount(String camId, String sysId) ;
	
	public boolean checkMainLineCount(String camId);
	public boolean checkpartyCount(String camId, String partyName) ;
	public boolean checkLiabilityID(String subProfileId, String liabilityId) ;
	
	public boolean checkFacNameGuar(String facNameGuar, String mainFacId );

	public Set<String> getCheckDocumentCodeForV2(String docCode);

	public String getDocumentCodeType(String docCode);
	
	public String getCamDetails(String customerId, String PanNo, String SystemiId);

	public List getImageDetails(String tagId);
	
	public StringBuilder getTagIdwithCamDetails(String customerId, String PanNo, String SystemiId);
	public int checkFacilityReceiptPending(String  limitProfileID);
	public void insertLimitProfileID(String  limitProfileID);
	public String checkLimitProfileID();
	public void deleteLimitProfileID();

	public void updateStageLineCheckerIdNewDetails(ICustomerSysXRef xref, String loginID, Date applicationDate);
 	public void updateStageLineStatus(String sid);
	public void updateSCFJSLogTableForLine();
 	public String getSeqNoForPanRequest();
	public int insertLineToInterfaceLogBackupTable();
	
}
