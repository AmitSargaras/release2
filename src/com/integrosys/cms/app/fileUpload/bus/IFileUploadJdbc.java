package com.integrosys.cms.app.fileUpload.bus;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.eod.bus.IEODStatus;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.generalparam.bus.OBGeneralParamEntry;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.batch.ramRatingDetails.schedular.RamRatingDetails;
import com.integrosys.cms.ui.createfacupload.OBCreatefacilitylineFile;
import com.integrosys.cms.ui.fileUpload.CheckerApproveFileUploadCmd;
import com.integrosys.cms.ui.fileUpload.UploadAcknowledgmentFileCmd;
import com.integrosys.cms.ui.fileUpload.UploadReleaselinedetailsFileCmd;
import com.integrosys.cms.ui.fileUpload.UploadBahrainFileCmd;
import com.integrosys.cms.ui.fileUpload.UploadFacilitydetailsFileCmd;
import com.integrosys.cms.ui.fileUpload.UploadFdFileCmd;
import com.integrosys.cms.ui.fileUpload.UploadHongKongFileCmd;
import com.integrosys.cms.ui.fileUpload.UploadPartyCamFileCmd;
import com.integrosys.cms.ui.fileUpload.UploadReleaselinedetailsFileCmd;
import com.integrosys.cms.ui.fileUpload.UploadUbsFileCmd;
import com.integrosys.cms.ui.fileUpload.UploadfinWareFileCmd;
import com.integrosys.cms.ui.partycamupload.CheckerApprovePartyCamFileUploadCmd;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.cms.ui.acknowledgmentupload.AcknowledgmentFileUploadCmd;
import com.integrosys.cms.ui.bulkudfupdateupload.OBTempBulkUDFFileUpload;
import com.integrosys.cms.ui.fileUpload.UploadFacilitydetailsFileCmd;
import com.integrosys.cms.ui.createfacupload.OBCreatefacilitylineFile;
import com.integrosys.base.businfra.search.SearchDAOException;

public interface IFileUploadJdbc {
	public int getUplodCount(String fileType) throws FileUploadException;
	//Modified By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION 
	public HashMap insertUbsfile( ArrayList result,UploadUbsFileCmd cmd,String fileName, ICommonUser user,Date date,ConcurrentMap<String, String> dataFromCacheView,ConcurrentMap<String, String> dataFromUpdLineFacilityMV);
	public HashMap insertHongKongfile( ArrayList result,UploadHongKongFileCmd cmd,String fileName, ICommonUser user,Date date,ConcurrentMap<String, String> dataFromCacheView ,ConcurrentMap<String, String> dataFromUpdLineFacilityMV) ;
	public HashMap insertFinwareFile( ArrayList result,UploadfinWareFileCmd cmd,String fileName, ICommonUser user,Date date,ConcurrentMap<String, String> dataFromCacheView, ConcurrentMap<String, String> dataFromUpdLineFacilityMV) ;
	public HashMap insertBahrainfile( ArrayList result,UploadBahrainFileCmd cmd,String fileName, ICommonUser user,Date date,ConcurrentMap<String, String> dataFromCacheView, ConcurrentMap<String, String> dataFromUpdLineFacilityMV);
	public HashMap insertAcknowledgmentfile( ArrayList result,UploadAcknowledgmentFileCmd cmd,String fileName, ICommonUser user,Date date,ConcurrentMap<String, String> dataFromCacheView, ConcurrentMap<String, String> dataFromUpdLineFacilityMV);
	public HashMap insertReleaselinedetailsfile( ArrayList result,UploadReleaselinedetailsFileCmd cmd,String fileName, ICommonUser user,Date date,ConcurrentMap<String, String> dataFromCacheView, ConcurrentMap<String, String> dataFromUpdLineFacilityMV);
	public HashMap insertPartyCamfile( ArrayList result,UploadPartyCamFileCmd cmd,String fileName, ICommonUser user,Date date,ConcurrentMap<String, String> dataFromCacheView,ConcurrentMap<String, String> dataFromUpdLineFacilityMV);
	public HashMap insertFacilitydetailsfile( ArrayList result,UploadFacilitydetailsFileCmd cmd,String fileName, ICommonUser user,Date date,ConcurrentMap<String, String> dataFromCacheView, ConcurrentMap<String, String> dataFromUpdLineFacilityMV);

	public SearchResult getAllUbsFile(String id) throws FileUploadException;
	public SearchResult getAllHongKongFile(String id) throws FileUploadException;
	public SearchResult getAllFinwareFile(String id) throws FileUploadException;
	public SearchResult getAllBahrainFile(String id) throws FileUploadException;
	public SearchResult getAllPartyCamFile(String id) throws FileUploadException;
	public SearchResult getAllBulkUDFFile(String id) throws FileUploadException;
	public SearchResult getAllLeiDetailsFile(String id) throws FileUploadException;
	public SearchResult getAllAcknowledgmentFile(String id) throws FileUploadException;
	public SearchResult getAllReleaselinedetailsFile(String id) throws FileUploadException;
	public SearchResult getAllFacilitydetailsFile(String id) throws FileUploadException;
	
	public HashMap insertUbsfileActual( ArrayList result,CheckerApproveFileUploadCmd cmd,String fileName, ICommonUser user,Date date,IFileUploadTrxValue trxValue,ConcurrentMap<String, String> dataFromUpdLineFacilityActual);
	public HashMap insertHongKongfileActual( ArrayList result,CheckerApproveFileUploadCmd cmd,String fileName, ICommonUser user,Date date,IFileUploadTrxValue trxValue ,ConcurrentMap<String, String> dataFromUpdLineFacilityActual);
	public HashMap insertFinwarefileActual( ArrayList result,CheckerApproveFileUploadCmd cmd,String fileName, ICommonUser user,Date date,IFileUploadTrxValue trxValue ,ConcurrentMap<String, String> dataFromUpdLineFacilityActual);
	public HashMap insertBahrainfileActual( ArrayList result,CheckerApproveFileUploadCmd cmd,String fileName, ICommonUser user,Date date,IFileUploadTrxValue trxValue,ConcurrentMap<String, String> dataFromUpdLineFacilityActual);
	public HashMap insertPartyCamfileActual( ArrayList result,CheckerApprovePartyCamFileUploadCmd cmd,String fileName, ICommonUser user,Date date,IFileUploadTrxValue trxValue,ConcurrentMap<String, String> dataFromUpdLineFacilityActual);
	
	public SearchResult getAllActualUbsFile(String id) throws FileUploadException;
	public SearchResult getAllActualHongKongFile(String id) throws FileUploadException;
	public SearchResult getAllActualFinwareFile(String id) throws FileUploadException;
	public SearchResult getAllActualBahrainFile(String id) throws FileUploadException;
	public SearchResult getAllActualPartyCamFile(String id) throws FileUploadException;
	public SearchResult getAllActualLeiDetailsFile(String id) throws FileUploadException;
	public SearchResult getAllActualAcknowledgmentFile(String id) throws FileUploadException;
	public SearchResult getAllActualReleaselinedetailsFile(String id) throws FileUploadException;
	public SearchResult getAllActualFacilitydetailsFile(String id) throws FileUploadException;
	
	public void createEntireUbsStageFile(List<OBUbsFile> objectList)throws FileUploadException,SQLException;
	public void insertReport(final ArrayList<OBExchangeRateAutoUpload> repostList)throws FileUploadException,SQLException;
	public void createEntireUbsActualFile(List<OBUbsFile> objectList)throws FileUploadException,SQLException;
	public void createEntireFinwareStageFile(List<OBFinwareFile> objectList)throws FileUploadException,SQLException;
	public void createEntireFinwareActualFile(List<OBFinwareFile> objectList)throws FileUploadException,SQLException;
	public void createEntireHongkongStageFile(List<OBHongKongFile> objList)throws FileUploadException,SQLException;
	public void createEntireHongkongActualFile(List<OBHongKongFile> objList)throws FileUploadException,SQLException;
	public void createEntireBahrainStageFile(List<OBBahrainFile> objectList)throws FileUploadException,SQLException;
	public void createEntireBahrainActualFile(List<OBBahrainFile> objectList)throws FileUploadException,SQLException;
	public void createEntirePartyCamStageFile(List<OBPartyCamFile> objectList)throws FileUploadException,SQLException;
	public void createEntirePartyCamActualFile(List<OBPartyCamFile> objectList)throws FileUploadException,SQLException;
	public void createEntireAcknowledgmentStageFile(List<OBAcknowledgmentFile> objectList)throws FileUploadException,SQLException;
	public void createEntireLeiDetailsStageFile(List objList)throws FileUploadException,SQLException;
	public void createEntireLeiDetailsActualFile(List objList)throws FileUploadException,SQLException;
	public void createEntireAcknowledgmentActualFile(List<OBAcknowledgmentFile> objectList)throws FileUploadException,SQLException;
	public void createEntireReleaselinedetailsStageFile(List<OBReleaselinedetailsFile> objectList)throws FileUploadException,SQLException;
	public void createEntireReleaselinedetailsActualFile(List<OBReleaselinedetailsFile> objectList)throws FileUploadException,SQLException;
	public void createEntireFacilitydetailsStageFile(List<OBFacilitydetailsFile> objectList)throws FileUploadException,SQLException;
	public void createEntireFacilitydetailsActualFile(List<OBFacilitydetailsFile> objectList)throws FileUploadException,SQLException;

	// Added By Dayananda Laishram for Basel Report Generation on 12-May-2015 BASEL_REPORT_ON_EOD
	public List<OBBaselUploadFile> reportList();
	
	public void updateXrefStageAmount(List<OBCommonFile> objectList,String facilitySystem)throws FileUploadException,SQLException;
	public void updateXrefActualAmount(List<OBCommonFile> objectList,String facilitySystem)throws FileUploadException,SQLException;
	
	public void updateXrefFinwareStageAmount(final List<OBCommonFile> objectList,String facilitySystem)throws FileUploadException,SQLException;
	public void updateXrefFinwareActualAmount(final List<OBCommonFile> objectList,String facilitySystem)throws FileUploadException,SQLException;
	
	
	public IEODStatus executeFileUploadCleanup(IEODStatus eodStatus)  ;
	// Added By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION | Start
	public ConcurrentMap<String,String> cacheDataFromMaterializedView(String materializedView);
	public String spUploadTransaction(String fileUploadSystem) ;
	public ConcurrentMap<String,String>  cacheDataFromUpdLineFacilityMV(String materializedViewName,String facilitySystem);
	public ConcurrentMap<String,String>  cacheDataFromUpdLineActual(String facilitySystem);
	// Added By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION | End
	
	// Added By Dayananda Laishram on 31/03/2015 || SP_FACILITY_RELEASE | Start
	public void spUpdateReleasedAmount(final String facilitySystem,final String updateType);
	// Added By Dayananda Laishram on 31/03/2015 || SP_FACILITY_RELEASE | Ends
	
	
	//Added by Uma to Support FD Upload
	public HashMap insertFdfile( ArrayList result,UploadFdFileCmd cmd,String fileName,Set<String> dataFromCacheView);
	public Set<String> cacheDataFromFdMaterializedView(String materializedViewName);
	public void createEntireFdStageFile(List<OBFdFile> objList)throws FileUploadException,SQLException;
	public void createEntireFdActualFile(List<OBFdFile> objectList)throws FileUploadException,SQLException;
	public HashMap insertFdfileActual( ArrayList result,CheckerApproveFileUploadCmd cmd,String fileName, Set<String> dataFromFdCacheView);
	public SearchResult getAllFdFile(String id) throws FileUploadException;
	public SearchResult getAllActualFdFile(String id) throws FileUploadException;
	
	//Start:Uma Khot:21/01/2016 Added for FD Upload NEw Logic as FD Upload was taking time
	void updateCashDepositToNull(String tableName);
	void updateStageFdUploadStatus();
	void updateStageFdDetails(long fileId);
	void updateTempFdDetails(long fileId);
	long getCount(long fileId, String tableName, String status);
	List<OBFdFile> getTotalUploadedList(long fileId,String tableName);
	
	void updateActualFdUploadStatus();
	void insertFileDataToTempActualTable(long actualFileId,String stageFileId);
	void updateActualFdDetails(long fileId);
	void updateTempActualFdDetails(long fileId);
	void insertTempActualFdDetails(long fileId);
	List<OBFdFile> getTotalUploadedFileList(long fileId,String tableName);
	//End:Uma Khot:21/01/2016 Added for FD Upload NEw Logic as FD Upload was taking time
	
	//Start:24-05-2016 Uma added to update FD status as active in FD upload process
	void updateStageFdDetailsWithStatusActive(long fileId,String appDate);
	void updateActualFdDetailsWithStatusActive(long fileId,String appDate);
	//End:24-05-2016 Uma added to update FD status as active in FD upload process
	
	//Uma Khot:Cam upload and Dp field calculation CR
	public Set<String> cacheDataFromPartyCamUploadMV(String string);
	public void updateUploadStatusToNull(String tableName);
	public void spUpdatePartyCamUpload(String fileId, String table);
	public void spBaselMonthlyNpaTrackNew();
	public String updatePartyfundStgtoMain(long fileId,String partyId,long id);
	public int getPartyCamUplodCount()throws FileUploadException;
	public int getAcknowledgmentUplodCount()throws FileUploadException;
	public int getLeiDetailsUplodCount()throws FileUploadException;
	public int getReleaselinedetailsUplodCount()throws FileUploadException;
	public int getFacilitydetailsUplodCount()throws FileUploadException;
	public Set<String> getRiskGrade();
	
	public Set<String> getAlreadyDownloadedFileNamesByBatch(String fileType, Date batchdate);
	public Set<String> getAlreadyDownloadedFileNamesByBatchForHRMS(String fileType, Date batchdate);
	public Map getSystemUploadFileNameAndUploadTime(String stagingReferenceId);
	public Set<String> getAlreadyDownloadedExchangeReportFileNamesByBatch(Date batchdate);

	//RAM RAting CR (Deferral Extension) 
	public HashMap insertDeferralExtnsionFile(ArrayList resultList, Date applicationDate, String fileName);
	public void createEntireDeferralExtensionFile(List<OBDeferralExtensionFileUpload> objectList)throws FileUploadException,SQLException;
	public void approveEntireDeferralExtensionFile(List<OBDeferralExtensionFileUpload> batchList)throws FileUploadException,SQLException;
	public HashMap insertRAMRatingfile(ArrayList resultList, RamRatingDetails ramRatingDetails, String fileName,
			String string, Date applicationDate);
	public Set<String> getRatingType();
	public void spUpdateRAMRatingUpload();
	public void spBeforeUpdateRAMRating();
	
	public ArrayList<OBAcknowledgmentFile> getAllCMSSecurityFile(Object object) throws FileUploadException,SQLException;
	public ArrayList<OBLeiDetailsFile> getAllCMSLeiSecurityFile(Object id) throws FileUploadException,SQLException;
	public void insertAcknowledgmentStageSecurity(final OBAcknowledgmentFile obAcknowledgmentFile) throws FileUploadException,SQLException;
	public void insertLeiDetailsStageSecurity(final OBLeiDetailsFile obLeiDetailsFile) throws FileUploadException,SQLException;
	public void createRejectAcknowledgmentActualFile(final OBAcknowledgmentFile obAcknowledgmentFile) throws FileUploadException,SQLException;
	public void createRejectLeiDetailsActualFile(final OBLeiDetailsFile obLeiDetailsFile)throws FileUploadException,SQLException;
	public void updateAcknowledgmentSecurity(OBAcknowledgmentFile obAcknowledgmentFile) throws FileUploadException,SQLException;
	public void updateLeiDetailsSecurity(OBLeiDetailsFile obLeiDetailsFile) throws FileUploadException,SQLException;


	
	//public ArrayList<OBReleaselinedetailsFile> getAllCMSSecurityFile(Object object) throws FileUploadException,SQLException;
	public void updateReleaseAmountStage(final OBReleaselinedetailsFile obReleaselinedetailsFile) throws FileUploadException,SQLException;
	public void updateReleaseAmountActual(final OBReleaselinedetailsFile obReleaselinedetailsFile) throws FileUploadException,SQLException;
	
	public void updateReleaseAmountStageforNull(final OBReleaselinedetailsFile obReleaselinedetailsFile) throws FileUploadException,SQLException;
	public void updateTotalReleasedAmtStageforNull(final OBReleaselinedetailsFile obReleaselinedetailsFile) throws FileUploadException,SQLException;
	
	public void updateTotalReleasedAmtStage(final OBReleaselinedetailsFile obReleaselinedetailsFile) throws FileUploadException,SQLException;
	public void updateTotalReleasedAmtActual(final OBReleaselinedetailsFile obReleaselinedetailsFile) throws FileUploadException,SQLException;
	
	public String generateSourceRefNo();
	//public void createRejectReleaselinedetailsActualFile(final OBReleaselinedetailsFile obReleaselinedetailsFile) throws FileUploadException,SQLException;
	//public void updateReleaselinedetailsSecurity(OBReleaselinedetailsFile obReleaselinedetailsFile) throws FileUploadException,SQLException;
	public boolean getFacilittIdValidOrNot(String facilityID)throws FileUploadException;
	public List getLimitSummaryListByAA(String aaId) throws LimitException ;
	void updateSanctionAmt(OBFacilitydetailsFile obFacilitydetailsFile) throws FileUploadException, SQLException;
	void updateReleasableAmt(OBFacilitydetailsFile obFacilitydetailsFile) throws FileUploadException, SQLException;
	void updateReleasableAmtActual(OBFacilitydetailsFile obFacilitydetailsFile)
			throws FileUploadException, SQLException;
	void updateSanctionAmtActual(OBFacilitydetailsFile obFacilitydetailsFile) throws FileUploadException, SQLException;
	void updateLimitExpDate(OBReleaselinedetailsFile releaseAmountFile) throws FileUploadException, SQLException;
	public void updateLimitExpDateforNull(OBReleaselinedetailsFile releaseAmountFile) throws FileUploadException, SQLException;
	void updateLimitExpDateActual(OBReleaselinedetailsFile releaseAmountFile) throws FileUploadException, SQLException;
	public void updateLimitExpDateActualforNull(OBReleaselinedetailsFile releaseAmountFile) throws FileUploadException, SQLException;
	long getFacilittId(final OBReleaselinedetailsFile releaseAmountFile) throws FileUploadException;
	public BigDecimal getSerialAmt(final OBReleaselinedetailsFile releaseAmountFile)throws FileUploadException;
	public Double getTotalFundedAmount(String partyID);
	public void updateReleaseLineUploadFile(OBReleaselinedetailsFile obReleaselinedetailsFile); 
	
	public void createFacilitylineFile(List<OBCreatefacilitylineFile> objectList) throws FileUploadException,SQLException;
	public void updatePublicInputLEI(final OBLeiDetailsFile obLeiDetailsFile);
	public void updateSubProfile(final OBLeiDetailsFile obLeiDetailsFile);
	public ArrayList getAutoUpdationResultList(String appDate,String flag);
	public SearchResult getAllAutoupdationLmtsFile(String stagingReferenceID);
	public void createEntireAutoupdationLmtsStageFile(List<OBAutoupdationLmtsFile> batchList) throws FileUploadException,SQLException;
	public int getAutoupdationLmtsUplodCount()  throws FileUploadException ;
	public void createRejectAutoupdationLmtsActualFile(OBAutoupdationLmtsFile autoupdationlmtsFile) throws FileUploadException,SQLException;
	public void createEntireAutoupdationLmtsActualFile(List<OBAutoupdationLmtsFile> batchList) throws FileUploadException,SQLException;
	public void updateAutoupdationLmtsStage(OBAutoupdationLmtsFile obAutoupdationLmtsData)throws FileUploadException,SQLException;
	public void updateAutoupdationLmtsActual(OBAutoupdationLmtsFile obAutoupdationLmtsFile)throws FileUploadException,SQLException;
	public void updateAutoupdationLmtsDpAmtActual(OBAutoupdationLmtsFile obAutoupdationLmtsFile)throws FileUploadException,SQLException;
	public void updateAutoupdationLmtsDpAmtStage(OBAutoupdationLmtsFile obAutoupdationLmtsData)throws FileUploadException,SQLException;
	public float getReleasedAmount(OBAutoupdationLmtsFile obAutoupdationLmtsFile);
	public void updateTotalReleasedAmountActual(OBAutoupdationLmtsFile obAutoupdationLmtsFile,float releaseAmt,OBAutoupdationLmtsFile obAutoupdationLmtsFileNew)throws FileUploadException,SQLException;
	String getFacilityTransactionStatus(OBReleaselinedetailsFile obj) throws FileUploadException;
	boolean isMFSchemaDetailsUploadTrxPendingOrRejected();
	Map<String,Boolean> existsPartyIds(List<String> partyIds);
	Map<String,ICollateral> existsSecurityIds(List<String> securityIds);
	void createEntireMFSchemaDetailsFile(List<OBMFSchemaDetailsFile> objectList, boolean isMaster) throws SQLException;
	boolean doesSecurityBelongsToParty(String partyId,Long securityId);
	public List<OBMFSchemaDetailsFile> getAllMFSchemaDetailsFile(long id);
	public int createSchemaDetail(IMarketableEquity equity, long collateralId, boolean isMaster);
	
	void createEntireStockDetailsFile(List<OBStockDetailsFile> objectList, boolean isMaster) throws SQLException;
	public int createStockDetails(IMarketableEquity equity, long collateralId, boolean isMaster);
	public List<OBStockDetailsFile> getAllStockDetailsFile(long id);
	boolean isStockDetailsUploadTrxPendingOrRejected();
	
	boolean isBondDetailsUploadTrxPendingOrRejected();
	void createEntireBondDetailsFile(List<OBBondDetailsFile> objectList, boolean isMaster) throws SQLException;
	List<OBBondDetailsFile> getAllBondDetailsFile(long id);
	int createBondDetail(IMarketableEquity equity, long collateralId, boolean isMaster);
	public HashMap insertHRMSfile(ArrayList resultList, String fileName, 
			Date applicationDate, ConcurrentMap<String, String> dataFromCacheView,
			ConcurrentMap<String, String> dataFromUpdLineFacilityMV);
	
	public  void updatePartyRMDetails(String empCode, IRegion region);
	public int getBulkUDFUploadCount();
	public void createEntireBulkUDFTempFile(List<OBTempBulkUDFFileUpload> batchList) throws FileUploadException,SQLException;
	public int getPartyIDCount(String string);
	public String getPartyIdFromMain(String string);
	public int getCamNoCount(String string);
	public String getCamIdFromMain(String string);
	public int getLimitCount(String systemId, String LineNo, String serailNo, String liabBranch);
	public String getLimitIdForLine(String systemId,String lineNo, String serialNo, String liabBranch);
	public int isValidUDFSequence(String TypeofUDF, String sequence, String fieldName);
	public void insertOrUpdateBulkUDFStageFile(List<OBTempBulkUDFFileUpload> batchList);
	public List<OBTempBulkUDFFileUpload> updateStagingCompBulkUDFTemp();
	public void updatePartyTempBulkUdf(List<OBTempBulkUDFFileUpload> obTempBulkUDFFileUpload);
	public void updateCamTempBulkUdf(List<OBTempBulkUDFFileUpload> obTempBulkUDFFileUpload);
	public void updateLimitTempBulkUdf(List<OBTempBulkUDFFileUpload> obTempBulkUDFFileUpload);
	public void updateTempToStageBulkUdfParty(OBTempBulkUDFFileUpload obTempBulkUDFFileUpload, String str);
	public void updateTempToStageBulkUdfCam(OBTempBulkUDFFileUpload obTempBulkUDFFileUpload, String str);
	public void updateTempToStageBulkUdfLimits(OBTempBulkUDFFileUpload obTempBulkUDFFileUpload, String str);
	public boolean checkauth(String type,String s);
	public void rollback();
	public int getCountPassFromTemp();
	public void updateLineAsPendingIfSystemUbs(List<OBTempBulkUDFFileUpload> nw);
	public String getLeiCode(String partyId);
	public String getIsLeiValidated(String partyId);
	public void updateSubProfileforValidatedLei(final OBLeiDetailsFile obLeiDetailsFile);
	public void updateLeiValidationStatusforScheduler(String partyId,String leiCode);
	public int getPslValueCheckWithLine(OBReleaselinedetailsFile obj);
	public String getRuleValueCheckWithLine(OBReleaselinedetailsFile obj);
	public String checkauthForLine(String systemId,String lineNo, String serialNo, String liabBranch);
	public String checkauthForLimit(String systemId,String lineNo, String serialNo, String liabBranch);
	public void updatePSLFlagValue(OBReleaselinedetailsFile obReleaselinedetailsFile) throws FileUploadException, SQLException;
	public void updatePSLFlagValueActual(OBReleaselinedetailsFile obReleaselinedetailsFile) throws FileUploadException, SQLException;
	public void updateRuleID(OBReleaselinedetailsFile obReleaselinedetailsFile) throws FileUploadException, SQLException;
	public void updateRuleIDActual(OBReleaselinedetailsFile obReleaselinedetailsFile) throws FileUploadException, SQLException;
	public String getReleaselinedetailsLiabBranchID(String string);
	public OBGeneralParamEntry getAppDate() throws Exception;
	public String getCheckListItemStatusFromDocCode(Long checkListID,Long docItemRef,String docCode) throws SearchDAOException;
}
