package com.integrosys.cms.batch.eod;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.UnhandledException;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.collateralNewMaster.bus.CollateralNewMasterException;
import com.integrosys.cms.app.collateralNewMaster.bus.OBCollateralNewMaster;
import com.integrosys.cms.app.collateralNewMaster.proxy.ICollateralNewMasterProxyManager;
import com.integrosys.cms.app.collateralNewMaster.trx.ICollateralNewMasterTrxValue;
import com.integrosys.cms.app.collateralNewMaster.trx.OBCollateralNewMasterTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.common.util.MakerCheckerUserUtil;
import com.integrosys.cms.app.commoncode.bus.CommonCodeDaoImpl;
import com.integrosys.cms.app.commoncode.bus.ICommonCodeDao;
import com.integrosys.cms.app.commoncodeentry.bus.CommonCodeEntriesException;
import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntries;
import com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntry;
import com.integrosys.cms.app.commoncodeentry.proxy.CommonCodeEntriesProxyManagerFactory;
import com.integrosys.cms.app.commoncodeentry.proxy.ICommonCodeEntriesProxy;
import com.integrosys.cms.app.commoncodeentry.trx.ICommonCodeEntriesTrxValue;
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalException;
import com.integrosys.cms.app.creditApproval.bus.OBCreditApproval;
import com.integrosys.cms.app.creditApproval.proxy.ICreditApprovalProxy;
import com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue;
import com.integrosys.cms.app.creditApproval.trx.OBCreditApprovalTrxValue;
import com.integrosys.cms.app.eod.bus.IEODStatusJdbc;
import com.integrosys.cms.app.eod.bus.IEODSyncOutMasterDao;
import com.integrosys.cms.app.eod.bus.OBClimsToCPSMaster;
import com.integrosys.cms.app.eod.bus.OBCpsToClimsMaster;
import com.integrosys.cms.app.eod.sync.bus.EODSyncProcessStatus;
//import com.integrosys.cms.app.eod.sync.bus.EODSyncStatusDaoImpl;
import com.integrosys.cms.app.eod.sync.bus.IEODSyncStatus;
import com.integrosys.cms.app.eod.sync.bus.IEODSyncStatus;
import com.integrosys.cms.app.eod.sync.bus.IEODSyncStatusDao;
import com.integrosys.cms.app.eod.sync.bus.OBEODSyncStatus;
import com.integrosys.cms.app.eod.sync.proxy.IEODSyncStatusProxy;
import com.integrosys.cms.app.eventmonitor.enabledisableuser.MonEnableDisableUser;
import com.integrosys.cms.app.facilityNewMaster.bus.FacilityNewMasterException;
import com.integrosys.cms.app.facilityNewMaster.bus.OBFacilityNewMaster;
import com.integrosys.cms.app.facilityNewMaster.proxy.IFacilityNewMasterProxyManager;
import com.integrosys.cms.app.facilityNewMaster.trx.IFacilityNewMasterTrxValue;
import com.integrosys.cms.app.facilityNewMaster.trx.OBFacilityNewMasterTrxValue;
import com.integrosys.cms.app.ftp.MasterSyncFtpService;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.geography.city.bus.OBCity;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.city.trx.ICityTrxValue;
import com.integrosys.cms.app.geography.city.trx.OBCityTrxValue;
import com.integrosys.cms.app.geography.country.bus.CountryException;
import com.integrosys.cms.app.geography.country.bus.OBCountry;
import com.integrosys.cms.app.geography.country.proxy.ICountryProxyManager;
import com.integrosys.cms.app.geography.country.trx.ICountryTrxValue;
import com.integrosys.cms.app.geography.country.trx.OBCountryTrxValue;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
import com.integrosys.cms.app.geography.region.proxy.IRegionProxyManager;
import com.integrosys.cms.app.geography.region.trx.IRegionTrxValue;
import com.integrosys.cms.app.geography.region.trx.OBRegionTrxValue;
import com.integrosys.cms.app.geography.state.bus.OBState;
import com.integrosys.cms.app.geography.state.proxy.IStateProxyManager;
import com.integrosys.cms.app.geography.state.trx.IStateTrxValue;
import com.integrosys.cms.app.geography.state.trx.OBStateTrxValue;
import com.integrosys.cms.app.holiday.bus.IHolidayDao;
import com.integrosys.cms.app.holiday.bus.IHolidayJdbc;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.partygroup.bus.OBPartyGroup;
import com.integrosys.cms.app.partygroup.bus.PartyGroupException;
import com.integrosys.cms.app.partygroup.proxy.IPartyGroupProxyManager;
import com.integrosys.cms.app.partygroup.trx.IPartyGroupTrxValue;
import com.integrosys.cms.app.partygroup.trx.OBPartyGroupTrxValue;
import com.integrosys.cms.app.relationshipmgr.bus.OBRelationshipMgr;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.relationshipmgr.bus.UserException;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue;
import com.integrosys.cms.app.relationshipmgr.trx.OBRelationshipMgrTrxValue;
import com.integrosys.cms.app.systemBankBranch.bus.OBSystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.bus.SystemBankBranchException;
import com.integrosys.cms.app.systemBankBranch.proxy.ISystemBankBranchProxyManager;
import com.integrosys.cms.app.systemBankBranch.trx.ISystemBankBranchTrxValue;
import com.integrosys.cms.app.systemBankBranch.trx.OBSystemBankBranchTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.ws.jax.common.CommonValidationHelper;
import com.integrosys.cms.app.ws.jax.common.MasterAccessUtility;
import com.integrosys.cms.batch.common.BatchResourceFactory;
import com.integrosys.cms.batch.common.filereader.DATReader;
import com.integrosys.cms.batch.common.filereader.ProcessDataFileEOD;
import com.integrosys.cms.batch.common.filereader.ProcessMasterSyncOutData;
import com.integrosys.cms.batch.eod.bus.LocationException;
import com.integrosys.cms.ui.rmAndCreditApprover.OBRMAndCreditApprover;


public class EndOfDaySyncMastersServiceImpl implements IEndOfDaySyncMastersService,IEodSyncConstants {

	SimpleDateFormat newDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	private IGeneralParamDao generalParam;
	private IHolidayDao holidayDao;
	private ICreditApprovalProxy creditApprovalProxy;
	private IHolidayJdbc holidayJdbc;
	private IEODSyncStatusProxy eodSyncStatusProxy;
	private IRelationshipMgrProxyManager relationshipMgrProxyManager;
	private ISystemBankBranchProxyManager systemBankBranchProxy;
	private IFacilityNewMasterProxyManager  facilityNewMasterProxy;
	private IPartyGroupProxyManager  partyGroupProxy;
	private IEODSyncOutMasterDao eodSyncOutMasterDao;
	private MonEnableDisableUser monEnableDisableUser;
	private ICountryProxyManager  countryProxy;
	private IRegionProxyManager regionProxy;
	private IStateProxyManager stateProxy;
	private ICityProxyManager cityProxy;
	private IEODSyncOutMasterDao eodSyncOutMaster;	


	private StringBuffer log = new StringBuffer();
	String logfileName;
	IEODStatusJdbc statusJdbc = (IEODStatusJdbc) BeanHouse.get("eodStatusJdbc");
	MasterAccessUtility accessUtility= (MasterAccessUtility)BeanHouse.get("masterAccessUtility");
	MakerCheckerUserUtil mcUtil = (MakerCheckerUserUtil) BeanHouse.get("makerCheckerUserUtil");
	BatchResourceFactory batchResourceFactory = new BatchResourceFactory();
	private ProcessMasterSyncOutData processMasterSyncOutData;
	private ICollateralNewMasterProxyManager collateralNewMasterProxy; 
	
	IEODSyncOutMasterDao eodSyncOutMasterDaoNew = (IEODSyncOutMasterDao)BeanHouse.get("eodSyncOutMasterDao");

	private static HashMap<String, String> cpsToClimsMasterMap= new HashMap<String, String>();
	private static HashMap<String, String> climsToCPSMasterMap= new HashMap<String, String>();
	static{
		cpsToClimsMasterMap.put("CPS_BranchLocation",SYSTEM_BANK_EOD_UPLOAD);
		cpsToClimsMasterMap.put("CPS_BusGroup_Mst",PARTY_GROUP_EOD_UPLOAD);
		cpsToClimsMasterMap.put("CPS_Facility_Mst",FACILITY_MASTER_EOD_UPLOAD);
		cpsToClimsMasterMap.put("CPS_User_Mst",USER_MASTER_EOD_UPLOAD);
		cpsToClimsMasterMap.put("CPS_Commodity_Mst",COMMODITY_MASTER_EOD_UPLOAD);
		cpsToClimsMasterMap.put("CPS_Industry_Mst",INDUSTRY_MASTER_EOD_UPLOAD);
		cpsToClimsMasterMap.put("CPS_Ascrom_Industry_Mst",RBI_INDUSTRY_MASTER_EOD_UPLOAD);
		cpsToClimsMasterMap.put("CPS_Security_Mst",SECURITY_MASTER_EOD_UPLOAD);
		cpsToClimsMasterMap.put("CPS_Sector_Mst",SECTOR_MASTER_EOD_UPLOAD);
		
		cpsToClimsMasterMap.put("CPS_Country_Mst",COUNTRY_MASTER_EOD_UPLOAD);
		cpsToClimsMasterMap.put("CPS_Region_Mst",REGION_MASTER_EOD_UPLOAD);
		cpsToClimsMasterMap.put("CPS_State_Mst",STATE_MASTER_EOD_UPLOAD);
		cpsToClimsMasterMap.put("CPS_City_Mst",CITY_MASTER_EOD_UPLOAD);		
		cpsToClimsMasterMap.put("CPS_Currency_Mst",CURRENCY_MASTER_EOD_UPLOAD);			
		cpsToClimsMasterMap.put("CPS_BankingMethod_Mst",BANKING_METHOD_MASTER_EOD_UPLOAD);		
		cpsToClimsMasterMap.put("CPS_MinorityCommunity_Mst",MINORITY_COMMUNITY_MASTER_EOD_UPLOAD);			
		cpsToClimsMasterMap.put("CPS_NbfcA_Mst",NBFC_A_MASTER_EOD_UPLOAD);
		cpsToClimsMasterMap.put("CPS_NbfcB_Mst",NBFC_B_MASTER_EOD_UPLOAD);		
		cpsToClimsMasterMap.put("CPS_RamRating_Mst",RAM_RATING_MASTER_EOD_UPLOAD);
		cpsToClimsMasterMap.put("CPS_RbiAssetClassification_Mst",RBI_ASSET_CLASSIFICATION_MASTER_EOD_UPLOAD);		
		cpsToClimsMasterMap.put("CPS_RelatedType_Mst",RELATED_TYPE_MASTER_EOD_UPLOAD);
		cpsToClimsMasterMap.put("CPS_RatingType_Mst",RATING_TYPE_MASTER_EOD_UPLOAD);
		cpsToClimsMasterMap.put("CPS_RelationshipType_Mst",RELATIONSHIP_TYPE_MASTER_EOD_UPLOAD);
		cpsToClimsMasterMap.put("CPS_WeakerSection_Mst",WEAKER_SECTION_MASTER_EOD_UPLOAD);
		cpsToClimsMasterMap.put("CPS_MsmeClassification_Mst",MSME_CLASSIFICATION_MASTER_EOD_UPLOAD);
		cpsToClimsMasterMap.put("CPS_PslMisCode_Mst",PSL_MIS_CODE_MASTER_EOD_UPLOAD);
		cpsToClimsMasterMap.put("CPS_InfrastructureFinance_Mst",INFRASTRUCTURE_FINANCE_MASTER_EOD_UPLOAD);
		cpsToClimsMasterMap.put("CPS_MarketSegment_Mst",MARKET_SEGMENT_MASTER_EOD_UPLOAD);		
		
		climsToCPSMasterMap.put("CLIMS_DocumnetGloble_Mst","DOC_CHECKLIST_GLOBAL_EOD");
		climsToCPSMasterMap.put("CLIMS_FacilityGlobal_Mst","DOC_CHECKLIST_FACILITY_GLOBAL_EOD");
		climsToCPSMasterMap.put("CLIMS_SecurityGlobal_Mst","DOC_CHECKLIST_SECURITY_GLOBAL_EOD");
		climsToCPSMasterMap.put("CLIMS_CAM_Mst","DOC_CHECKLIST_CAM_EOD");
		climsToCPSMasterMap.put("CLIMS_Other_Mst","DOC_CHECKLIST_OTHER_EOD");
		climsToCPSMasterMap.put("CLIMS_OtherBankBranch_Mst","OTHERBANK_BRANCH_EOD");
		climsToCPSMasterMap.put("CLIMS_OtherBank_Mst","OTHERBANK_EOD");
		climsToCPSMasterMap.put("CLIMS_StatementType_Mst","STATEMENT_TYPE_EOD");
	}

	/**
	 	 * @return the eodSyncOutMaster
	 */
	public IEODSyncOutMasterDao getEodSyncOutMaster() {
		return eodSyncOutMaster;
	}
	/**
	 * @param eodSyncOutMaster the eodSyncOutMaster to set
	 */
	public void setEodSyncOutMaster(IEODSyncOutMasterDao eodSyncOutMaster) {
		this.eodSyncOutMaster = eodSyncOutMaster;
	}


	/**

	 * @return the processMasterSyncOutData
	 */
	public ProcessMasterSyncOutData getProcessMasterSyncOutData() {
		return processMasterSyncOutData;
	}

	/**
	 * @param processMasterSyncOutData the processMasterSyncOutData to set
	 */
	public void setProcessMasterSyncOutData(ProcessMasterSyncOutData processMasterSyncOutData) {
		this.processMasterSyncOutData = processMasterSyncOutData;
	}

	/**
	 * @return the eodSyncStatusProxy
	 */
	public IEODSyncStatusProxy getEodSyncStatusProxy() {
		return eodSyncStatusProxy;
	}

	/**
	 * @param eodSyncStatusProxy the eodSyncStatusProxy to set
	 */
	public void setEodSyncStatusProxy(IEODSyncStatusProxy eodSyncStatusProxy) {
		this.eodSyncStatusProxy = eodSyncStatusProxy;
		if(eodSyncStatusProxy==null)
			this.eodSyncStatusProxy = (IEODSyncStatusProxy) BeanHouse.get("eodSyncStatusProxyManager");
	}

	public ISystemBankBranchProxyManager getSystemBankBranchProxy() {
		return systemBankBranchProxy;
	}

	public void setSystemBankBranchProxy(
			ISystemBankBranchProxyManager systemBankBranchProxy) {
		this.systemBankBranchProxy = systemBankBranchProxy;
	}

	public IFacilityNewMasterProxyManager getFacilityNewMasterProxy() {
		return facilityNewMasterProxy;
	}

	public void setFacilityNewMasterProxy(
			IFacilityNewMasterProxyManager facilityNewMasterProxy) {
		this.facilityNewMasterProxy = facilityNewMasterProxy;
	}

	public MonEnableDisableUser getMonEnableDisableUser() {
		return monEnableDisableUser;
	}

	public void setMonEnableDisableUser(MonEnableDisableUser monEnableDisableUser) {
		this.monEnableDisableUser = monEnableDisableUser;
	}

	public IGeneralParamDao getGeneralParam() {
		return generalParam;
	}

	public void setGeneralParam(IGeneralParamDao generalParam) {
		this.generalParam = generalParam;
		if(generalParam==null)
			this.generalParam = (IGeneralParamDao) BeanHouse.get("generalParam");
	}

	public IHolidayDao getHolidayDao() {
		return holidayDao;
	}

	public void setHolidayDao(IHolidayDao holidayDao) {
		this.holidayDao = holidayDao;
	}

	public IHolidayJdbc getHolidayJdbc() {
		return holidayJdbc;
	}

	public void setHolidayJdbc(IHolidayJdbc holidayJdbc) {
		this.holidayJdbc = holidayJdbc;
	}

	/**
	 * @return the batchResourceFactory
	 */
	public BatchResourceFactory getBatchResourceFactory() {
		return batchResourceFactory;
	}

	/**
	 * @param batchResourceFactory the batchResourceFactory to set
	 */
	public void setBatchResourceFactory(BatchResourceFactory batchResourceFactory) {
		this.batchResourceFactory = batchResourceFactory;
	}


	public ICollateralNewMasterProxyManager getCollateralNewMasterProxy() {
		return collateralNewMasterProxy;
	}

	public IPartyGroupProxyManager getPartyGroupProxy() {
		return partyGroupProxy;
	}

	public void setPartyGroupProxy(IPartyGroupProxyManager partyGroupProxy) {
		this.partyGroupProxy = partyGroupProxy;
	}

	public void setCollateralNewMasterProxy(
			ICollateralNewMasterProxyManager collateralNewMasterProxy) {
		this.collateralNewMasterProxy = collateralNewMasterProxy;
	}


	public ICreditApprovalProxy getCreditApprovalProxy() {
		return creditApprovalProxy;
	}

	public void setCreditApprovalProxy(ICreditApprovalProxy creditApprovalProxy) {
		this.creditApprovalProxy = creditApprovalProxy;
	}


	public IRelationshipMgrProxyManager getRelationshipMgrProxyManager() {
		return relationshipMgrProxyManager;
	}

	public void setRelationshipMgrProxyManager(
			IRelationshipMgrProxyManager relationshipMgrProxyManager) {
		this.relationshipMgrProxyManager = relationshipMgrProxyManager;
	}
	
	
	public ICountryProxyManager getCountryProxy() {
		return (ICountryProxyManager) BeanHouse.get("countryProxy");
	}

	public void setCountryProxy(ICountryProxyManager countryProxy) {
		this.countryProxy = countryProxy;
	}

public IRegionProxyManager getRegionProxy() {
		return (IRegionProxyManager) BeanHouse.get("regionProxy");
	}

	public void setRegionProxy(IRegionProxyManager regionProxy) {
		this.regionProxy = regionProxy;
	}
public IStateProxyManager getStateProxy() {
		return (IStateProxyManager) BeanHouse.get("stateProxy");
	}

	public void setStateProxy(IStateProxyManager stateProxy) {
		this.stateProxy = stateProxy;
	}
public ICityProxyManager getCityProxy() {
		return (ICityProxyManager) BeanHouse.get("cityProxy");
	}

	public void setCityProxy(ICityProxyManager cityProxy) {
		this.cityProxy = cityProxy;
	}
	
	public  StringBuffer performEODSyncClimsToCps() {
		log.append("\n----- EOD Master Syncronization Begin ----");
		DefaultLogger.debug(this, "performEODSyncClimsToCps() : EOD Master Syncronization Begin ----");
		
 		MasterSyncFtpService masterSyncFtpService = new MasterSyncFtpService();
		try {
			if(!"DEV".equals(PropertyManager.getValue("deployment.environment"))) {
			//Clean local upload and acknowledgement directory
			DefaultLogger.debug(this, "\n----- masterSyncFtpService.cleanLocalDir() ----");
			System.out.println("\n----- masterSyncFtpService.cleanLocalDir() ----");
			masterSyncFtpService.cleanLocalDir(SYNC_DIRECTION_CLIMSTOCPS);
			//Archive remote upload directory
			DefaultLogger.debug(this, "\n----- masterSyncFtpService.archiveClimsUploadFile() ----");
			System.out.println("\n----- masterSyncFtpService.archiveClimsUploadFile() ----");
			masterSyncFtpService.archiveClimsUploadFile();
			// Download and archive CLIMS acknowledgement directory
			DefaultLogger.debug(this, "\n----- masterSyncFtpService.downlodClimsAckFilesToSync() ----");
			System.out.println("\n----- masterSyncFtpService.downlodClimsAckFilesToSync() ----");
			masterSyncFtpService.downlodClimsAckFilesToSync();
			System.out.println("after downlodClimsAckFilesToSync()");
			}
			//process acknowledgement
			DefaultLogger.debug(this, "\n----- processClimsToCPSAcknowlegement() ----");
			processClimsToCPSAcknowlegement();
			//generate upload files for modified master along with control file.
			DefaultLogger.debug(this, "\n----- syncClimsToCps() ----");
			syncClimsToCps();
			//upload master files and control file to remote upload directory
			DefaultLogger.debug(this, "\n----- masterSyncFtpService.uploadClimsMasterFiles() ----");
			masterSyncFtpService.uploadClimsMasterFiles();
			//log upload status
			DefaultLogger.debug(this, "\n----- getEodSyncStatusProxy().logEODSyncStatus() ----");
			getEodSyncStatusProxy().logEODSyncStatus(SYNC_DIRECTION_CLIMSTOCPS);
			//reset master eodSync status
			DefaultLogger.debug(this, "\n----- getEodSyncStatusProxy().resetEODSyncStatus() ----");
			getEodSyncStatusProxy().resetEODSyncStatus(SYNC_DIRECTION_CLIMSTOCPS);
			DefaultLogger.debug(this, "\n----- performEODSyncClimsToCps() : EOD Master Syncronization End ----");
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return log;
	}

	public  StringBuffer performEODSyncCpsToClims() {
		log.append("\n----- EOD Master Syncronization Begin ----");
		
		MasterSyncFtpService masterSyncFtpService = new MasterSyncFtpService();
		try {
			if(!"DEV".equals(PropertyManager.getValue("deployment.environment"))) {
			//Clean local upload and acknowledgement directory
			masterSyncFtpService.cleanLocalDir(SYNC_DIRECTION_CPSTOCLIMS);	
			System.out.println("11111111111111111 after cleanLocalDir()");
			//Downloads the remote CPS LatestData to local CPS latest Data and archive the old data
			masterSyncFtpService.downlodMasterFilesToSync(); 
			System.out.println("22222222222 after downlodMasterFilesToSync()");
			//Archieve the remote and local ack directory
			masterSyncFtpService.archiveAckFilesToSync();
			}
			log.append("\n----- Folders are in sync ----");
			System.out.println("syncCpsToClims() start");

			syncCpsToClims();
			System.out.println("syncCpsToClims() end");

			//uploading Ack files
			masterSyncFtpService.uploadAckFiles();
			System.out.println("uploadAckFiles() end");

			getEodSyncStatusProxy().logEODSyncStatus(SYNC_DIRECTION_CPSTOCLIMS);
			getEodSyncStatusProxy().resetEODSyncStatus(SYNC_DIRECTION_CPSTOCLIMS);
			System.out.println("sssssssssssssssss performEODSyncCpsToClims() end");

		}
		catch (Exception e) {
			System.out.println("uploadAckFiles() exception"+e.getMessage());

			DefaultLogger.error(this, "Error while synchronizing EOD Masters from CPS to CLIMS", e);
		}
		return log;
	}

	/*public  StringBuffer performEODSync() {
		log.append("\n----- EOD Master Syncronization Begin ----");
		
		MasterSyncFtpService masterSyncFtpService = new MasterSyncFtpService();
		try {
			//Downloads the remote CPS LatestData to local CPS latest Data and archive the old data
			masterSyncFtpService.downlodMasterFilesToSync();
			//Archieve the remote and local ack directory
			masterSyncFtpService.archiveAckFilesToSync();
			log.append("\n----- Folders are in sync ----");
			syncCpsToClims();
			//uploading Ack files
			masterSyncFtpService.uploadAckFiles();
			
			masterSyncFtpService.cleanLocalUploadDir();
			masterSyncFtpService.downlodClimsAckFilesToSync();
			processClimsToCPSAcknowlegement();
			syncClimsToCps();
			//uploading CLIMS master files
			masterSyncFtpService.uploadClimsMasterFiles();
			
			getEodSyncStatusProxy().logEODSyncStatus();
			getEodSyncStatusProxy().resetEODSyncStatus();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return log;
	}*/

	private void processClimsToCPSAcknowlegement() {

		DefaultLogger.debug(this, "inside processClimsToCPSAcknowlegement() method:::");
		try {
		HashMap<String, OBClimsToCPSMaster> taskMap= new HashMap<String, OBClimsToCPSMaster>();
		String controlFileName = getClimsControlFileName();
		DefaultLogger.debug(this, "controlFileName:::"+controlFileName);
		if(controlFileName==null || "".equals(controlFileName)){
			DefaultLogger.debug(this, "Clims to CPS control file for Acknowlegement file not found.");
			return;
		}
		//getBasePath+controlFileName
		String controlFileNameWithPath =getQualifiedFileName(controlFileName,SYNC_DIRECTION_CLIMSTOCPS);
		File controlFile=new File(controlFileNameWithPath);
		if(controlFile.exists()){
			ProcessDataFileEOD dataFile = new ProcessDataFileEOD();
			ArrayList<OBClimsToCPSMaster> obClimsToCPSMasterList = dataFile.processAckFile(controlFile,"CLIMS_CPS_CONTROL_FILE");
			String masterName="";
			String ackFileName="";
			for (OBClimsToCPSMaster climsToCpsMaster : obClimsToCPSMasterList) {
				dataFile = new ProcessDataFileEOD();
				ackFileName="";
				masterName="";
				ackFileName=getQualifiedFileName(climsToCpsMaster.getFileName(),SYNC_DIRECTION_CLIMSTOCPS);
				File ackFile= new File(ackFileName);

				for(String masterFileNameKey :climsToCPSMasterMap.keySet()){
					if(climsToCpsMaster.getFileName().startsWith(masterFileNameKey)){
						masterName=climsToCPSMasterMap.get(masterFileNameKey);
						break;
					}
				}
				
				if(ackFile.exists()){
					DefaultLogger.debug(this, "Processing Master Ack :"+masterName);
					//This will process the ack file and update the record status
					dataFile.processAckFile(ackFile,masterName);
					//Update Status for Master
					//getEodSyncOutMaster().updateEodSyncStatus(masterName,EODSyncRecordStatus.SUCCESSFUL);
					
				}
				
			}
			DefaultLogger.debug(this, "processClimsToCPSAcknowlegement(): Control File processing done!");
		}else{
			DefaultLogger.debug(this, "Clims To CPS Acknowlegement :Control File Not found. File Name : "+controlFileName);
		}
		}
		catch (Exception e) {
			//Catching exception if any while processing ack
			DefaultLogger.error(this, "processClimsToCPSAcknowlegement()::  Error encountered during control file process. ");
			e.printStackTrace();
		}
	
		
	}


	private void syncClimsToCps() {
		DefaultLogger.debug(this, "inside method syncClimsToCps()..");
		HashMap<String,OBEODSyncStatus> climsToCPSTaskMap=getMastersToBeMigrate();
		if(!climsToCPSTaskMap.isEmpty()){
			try {
				for(OBEODSyncStatus syncStatus :climsToCPSTaskMap.values()){
					syncStatus.setApplicationDate(new Date(getApplicationDate()));
					syncStatus.setCurrentDate(new Date());
					syncStatus.setProcessStartTime(new Date());
					syncStatus.setProcessStatus(EODSyncProcessStatus.PROCESSING.name());
					getEodSyncStatusProxy().updateEODSyncActivity(syncStatus);
					try {
						//will generate the file and update the total record count
						getProcessMasterSyncOutData().generateMasterFile(syncStatus);

						if (syncStatus.getTotalCount()>0) {
							syncStatus.setProcessStatus(EODSyncProcessStatus.SUCCESS.name());
						}else{
							syncStatus.setProcessStatus(EODSyncProcessStatus.NA.name());
						}
						syncStatus.setProcessEndTime(new Date());
						getEodSyncStatusProxy().updateEODSyncActivity(syncStatus);
					}
					catch (Exception e) {
						syncStatus.setProcessStatus(EODSyncProcessStatus.FAILED.name());
						syncStatus.setProcessException(e.getMessage());
						getEodSyncStatusProxy().updateEODSyncActivity(syncStatus);
						DefaultLogger.error(this, "exception occurred during syncClimsToCps() execution! ");
						e.printStackTrace();
					}
				}			
			}
			catch (Exception e) {
				DefaultLogger.error(this, "exception occurred during syncClimsToCps() execution 2 ::! ");
				throw new UnhandledException(e);
			}
			ProcessDataFileEOD dataFile = new ProcessDataFileEOD();
			dataFile.generateClimsControlFile(climsToCPSTaskMap);
			DefaultLogger.debug(this, "Control File generated - CLIMS to cPS..");
		}
	}
	private HashMap<String,OBEODSyncStatus> getMastersToBeMigrate() {
		HashMap<String,OBEODSyncStatus> climsToCPSTaskMap = new HashMap<String,OBEODSyncStatus>();
		List<OBEODSyncStatus> climsToCpsMasterList = getEodSyncStatusProxy().findEODSyncActivitiesBySyncDirection(SYNC_DIRECTION_CLIMSTOCPS);
		for (OBEODSyncStatus syncStatus : climsToCpsMasterList) {
			climsToCPSTaskMap.put(syncStatus.getProcessKey(), syncStatus);
		}

		return climsToCPSTaskMap;
	}

	private void syncCpsToClims() {
		HashMap<String, OBCpsToClimsMaster> taskMap = getMastersToBeProcessed();
		DefaultLogger.debug(this, "Size of Masters to be processed: "+taskMap.size());
		System.out.println("Size of Masters to be processed: "+taskMap.size());
		if (!taskMap.isEmpty()) {
			if (taskMap.containsKey(SYSTEM_BANK_EOD_UPLOAD)) {
				OBCpsToClimsMaster systemBankBranchMaster = (OBCpsToClimsMaster) taskMap.get(SYSTEM_BANK_EOD_UPLOAD);
				String processFileName = getQualifiedFileName(systemBankBranchMaster.getFileName(),SYNC_DIRECTION_CPSTOCLIMS);
				DefaultLogger.debug(this, "File name: "+systemBankBranchMaster.getFileName());
				IEODSyncStatus syncStatus = getEodSyncStatusProxy().findEODSyncActivityByProcessingKey(SYSTEM_BANK_EOD_UPLOAD);
				try{
				syncStatus.setProcessStartTime(new Date());
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("Exception in  setProcessStartTime "+e.getMessage());
				}
				File read = new File(processFileName);
				ProcessDataFileEOD dataFile = new ProcessDataFileEOD();
				System.out.println("syncCpsToClims() sssssssssssssssssssss11111111");
				if (read.exists()) {
					try {
						System.out.println("syncCpsToClims() 22222222222222222222");

						updateSyncStatus(syncStatus, SYSTEM_BANK_EOD_UPLOAD, EODSyncProcessStatus.PROCESSING.name(), systemBankBranchMaster);
						dataFile.setAckFileName(systemBankBranchMaster.getFileName());
						dataFile.setFileRecordCount(systemBankBranchMaster.getRecordCount());
						ArrayList<OBSystemBankBranch> obSystemBankBranchList = dataFile.processFile(read, SYSTEM_BANK_EOD_UPLOAD);
						systemBankBranchMaster = setRecordCount(systemBankBranchMaster, dataFile.getSuccessfullRecordCount(), dataFile
								.getFailureRecordCount());
						taskMap.put(SYSTEM_BANK_EOD_UPLOAD, systemBankBranchMaster);
						System.out.println("syncCpsToClims() 3333333333333333");

						updateSyncStatus(syncStatus, SYSTEM_BANK_EOD_UPLOAD, EODSyncProcessStatus.SUCCESS.name(), systemBankBranchMaster);
						System.out.println("syncCpsToClims() 44444444444444");

					}
					catch (Exception e) {
						System.out.println("syncCpsToClims() catch exception"+e.getMessage());

						DefaultLogger.error(this, "Error while synchronizing "+SYSTEM_BANK_EOD_UPLOAD, e);
						systemBankBranchMaster = setRecordCount(systemBankBranchMaster, 0l, systemBankBranchMaster.getRecordCount());
						syncStatus.setProcessException(e.getMessage());
						updateSyncStatus(syncStatus, SYSTEM_BANK_EOD_UPLOAD, EODSyncProcessStatus.FAILED.name(), null);
						getEodSyncStatusProxy().updateEODSyncActivity(syncStatus);

						taskMap.put(SYSTEM_BANK_EOD_UPLOAD, systemBankBranchMaster);
					}
				} else {
					System.out.println("syncCpsToClims() 5555555555");

					systemBankBranchMaster = setRecordCount(systemBankBranchMaster, 0l, systemBankBranchMaster.getRecordCount());
					syncStatus.setProcessException("File Not Found :" + systemBankBranchMaster.getFileName());
					updateSyncStatus(syncStatus, SYSTEM_BANK_EOD_UPLOAD, EODSyncProcessStatus.FAILED.name(), null);
					dataFile.setAckFileName(systemBankBranchMaster.getFileName());
					// to generate empty Ack File
					System.out.println("syncCpsToClims() else111 6666666666666666");

					ArrayList<OBSystemBankBranch> obSystemBankBranchList = dataFile.processFile(null, SYSTEM_BANK_EOD_UPLOAD);
					taskMap.put(SYSTEM_BANK_EOD_UPLOAD, systemBankBranchMaster);
				}
			} else {
				System.out.println("syncCpsToClims() else2 77777777");

				updateSyncStatus(null, SYSTEM_BANK_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
			}

			if (taskMap.containsKey(PARTY_GROUP_EOD_UPLOAD)) {
				System.out.println("syncCpsToClims() 888888888888");

				OBCpsToClimsMaster partyGroupMaster = (OBCpsToClimsMaster) taskMap.get(PARTY_GROUP_EOD_UPLOAD);
				String processFileName = getQualifiedFileName(partyGroupMaster.getFileName(),SYNC_DIRECTION_CPSTOCLIMS);
				DefaultLogger.debug(this, "File name: "+partyGroupMaster.getFileName());
				IEODSyncStatus syncStatus = getEodSyncStatusProxy().findEODSyncActivityByProcessingKey(PARTY_GROUP_EOD_UPLOAD);
				try{
				syncStatus.setProcessStartTime(new Date());
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("Exception in  setProcessStartTime "+e.getMessage());
				}
				ProcessDataFileEOD dataFile = new ProcessDataFileEOD();
				File read = new File(processFileName);
				if (read.exists()) {
					try {
						updateSyncStatus(syncStatus, PARTY_GROUP_EOD_UPLOAD, EODSyncProcessStatus.PROCESSING.name(), partyGroupMaster);
						dataFile.setFileRecordCount(partyGroupMaster.getRecordCount());
						dataFile.setAckFileName(partyGroupMaster.getFileName());
						ArrayList<OBPartyGroup> obPartyGroupList = dataFile.processFile(read, PARTY_GROUP_EOD_UPLOAD);
						partyGroupMaster = setRecordCount(partyGroupMaster, dataFile.getSuccessfullRecordCount(), dataFile.getFailureRecordCount());
						updateSyncStatus(syncStatus, PARTY_GROUP_EOD_UPLOAD, EODSyncProcessStatus.SUCCESS.name(), partyGroupMaster);
						taskMap.put(PARTY_GROUP_EOD_UPLOAD, partyGroupMaster);
					}
					catch (Exception e) {
						DefaultLogger.error(this, "Error while synchronizing "+PARTY_GROUP_EOD_UPLOAD, e);
						syncStatus.setProcessException(e.getMessage());
						updateSyncStatus(syncStatus, PARTY_GROUP_EOD_UPLOAD, EODSyncProcessStatus.FAILED.name(), null);
						partyGroupMaster = setRecordCount(partyGroupMaster, 0l, partyGroupMaster.getRecordCount());
						taskMap.put(PARTY_GROUP_EOD_UPLOAD, partyGroupMaster);
					}
				} else {
					partyGroupMaster = setRecordCount(partyGroupMaster, 0l, partyGroupMaster.getRecordCount());
					// to generate empty Ack File
					syncStatus.setProcessException("File Not Found :" + partyGroupMaster.getFileName());
					updateSyncStatus(syncStatus, PARTY_GROUP_EOD_UPLOAD, EODSyncProcessStatus.FAILED.name(), null);
					dataFile.setAckFileName(partyGroupMaster.getFileName());
					dataFile.processFile(null, PARTY_GROUP_EOD_UPLOAD);
					taskMap.put(PARTY_GROUP_EOD_UPLOAD, partyGroupMaster);
				}

			} else {
				updateSyncStatus(null, PARTY_GROUP_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
			}

			if (taskMap.containsKey(USER_MASTER_EOD_UPLOAD)) {
				System.out.println("syncCpsToClims() 8888888888888889");

				OBCpsToClimsMaster userMaster = (OBCpsToClimsMaster) taskMap.get(USER_MASTER_EOD_UPLOAD);
				String processFileName = getQualifiedFileName(userMaster.getFileName(),SYNC_DIRECTION_CPSTOCLIMS);
				DefaultLogger.debug(this, "File name: "+userMaster.getFileName());
				IEODSyncStatus syncStatus = getEodSyncStatusProxy().findEODSyncActivityByProcessingKey(USER_MASTER_EOD_UPLOAD);
				try{
				syncStatus.setProcessStartTime(new Date());
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("Exception in  setProcessStartTime "+e.getMessage());
				}
				File read = new File(processFileName);
				ProcessDataFileEOD dataFile = new ProcessDataFileEOD();
				if (read.exists()) {
					try {
						updateSyncStatus(syncStatus, USER_MASTER_EOD_UPLOAD, EODSyncProcessStatus.PROCESSING.name(), userMaster);
						dataFile.setAckFileName(userMaster.getFileName());
						dataFile.setFileRecordCount(userMaster.getRecordCount());
						ArrayList<OBRMAndCreditApprover> obRMAndCreditApproverList = dataFile.processFile(read, USER_MASTER_EOD_UPLOAD);
						userMaster = setRecordCount(userMaster, dataFile.getSuccessfullRecordCount(), dataFile
								.getFailureRecordCount());
						taskMap.put(USER_MASTER_EOD_UPLOAD, userMaster);
						updateSyncStatus(syncStatus, USER_MASTER_EOD_UPLOAD, EODSyncProcessStatus.SUCCESS.name(), userMaster);

					}
					catch (Exception e) {
						DefaultLogger.error(this, "Error while synchronizing "+USER_MASTER_EOD_UPLOAD, e);
						userMaster = setRecordCount(userMaster, 0l, userMaster.getRecordCount());
						syncStatus.setProcessException(e.getMessage());
						updateSyncStatus(syncStatus, USER_MASTER_EOD_UPLOAD, EODSyncProcessStatus.FAILED.name(), null);
						getEodSyncStatusProxy().updateEODSyncActivity(syncStatus);

						taskMap.put(USER_MASTER_EOD_UPLOAD, userMaster);
					}
				} else {
					userMaster = setRecordCount(userMaster, 0l, userMaster.getRecordCount());
					syncStatus.setProcessException("File Not Found :" + userMaster.getFileName());
					updateSyncStatus(syncStatus, USER_MASTER_EOD_UPLOAD, EODSyncProcessStatus.FAILED.name(), null);
					dataFile.setAckFileName(userMaster.getFileName());
					// to generate empty Ack File
					ArrayList<OBFacilityNewMaster> obFacilityMasterList = dataFile.processFile(null, USER_MASTER_EOD_UPLOAD);
					taskMap.put(USER_MASTER_EOD_UPLOAD, userMaster);
				}

			} else {
				updateSyncStatus(null, USER_MASTER_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
			}
			if (taskMap.containsKey(COMMODITY_MASTER_EOD_UPLOAD)) {
				processCommonCodeEntryMasterFile(taskMap,COMMODITY_MASTER_EOD_UPLOAD);
			} else {
				updateSyncStatus(null, COMMODITY_MASTER_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
			}		
			
			if (taskMap.containsKey(CURRENCY_MASTER_EOD_UPLOAD)) {
				processCommonCodeEntryMasterFile(taskMap,CURRENCY_MASTER_EOD_UPLOAD);
			} else {
				updateSyncStatus(null, CURRENCY_MASTER_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
			}
			
			if (taskMap.containsKey(BANKING_METHOD_MASTER_EOD_UPLOAD)) {
				processCommonCodeEntryMasterFile(taskMap,BANKING_METHOD_MASTER_EOD_UPLOAD);
			} else {
				updateSyncStatus(null, BANKING_METHOD_MASTER_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
				}			
			
			if (taskMap.containsKey(MINORITY_COMMUNITY_MASTER_EOD_UPLOAD)) {
				processCommonCodeEntryMasterFile(taskMap,MINORITY_COMMUNITY_MASTER_EOD_UPLOAD);
			} else {
				updateSyncStatus(null, MINORITY_COMMUNITY_MASTER_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
			}
			if (taskMap.containsKey(NBFC_A_MASTER_EOD_UPLOAD)) {
				processCommonCodeEntryMasterFile(taskMap,NBFC_A_MASTER_EOD_UPLOAD);
			} else {
				updateSyncStatus(null, NBFC_A_MASTER_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
			}
			if (taskMap.containsKey(NBFC_B_MASTER_EOD_UPLOAD)) {
				processCommonCodeEntryMasterFile(taskMap,NBFC_B_MASTER_EOD_UPLOAD);
			} else {
				updateSyncStatus(null, NBFC_B_MASTER_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
			}
			if (taskMap.containsKey(RAM_RATING_MASTER_EOD_UPLOAD)) {
				processCommonCodeEntryMasterFile(taskMap,RAM_RATING_MASTER_EOD_UPLOAD);
			} else {
				updateSyncStatus(null, RAM_RATING_MASTER_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
			}
			if (taskMap.containsKey(RBI_ASSET_CLASSIFICATION_MASTER_EOD_UPLOAD)) {
				processCommonCodeEntryMasterFile(taskMap,RBI_ASSET_CLASSIFICATION_MASTER_EOD_UPLOAD);
			} else {
				updateSyncStatus(null, RBI_ASSET_CLASSIFICATION_MASTER_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
			}
			if (taskMap.containsKey(RELATED_TYPE_MASTER_EOD_UPLOAD)) {
				processCommonCodeEntryMasterFile(taskMap,RELATED_TYPE_MASTER_EOD_UPLOAD);
			} else {
				updateSyncStatus(null, RELATED_TYPE_MASTER_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
			}
			if (taskMap.containsKey(RATING_TYPE_MASTER_EOD_UPLOAD)) {
				processCommonCodeEntryMasterFile(taskMap,RATING_TYPE_MASTER_EOD_UPLOAD);
			} else {
				updateSyncStatus(null, RATING_TYPE_MASTER_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
			}
			if (taskMap.containsKey(RELATIONSHIP_TYPE_MASTER_EOD_UPLOAD)) {
				processCommonCodeEntryMasterFile(taskMap,RELATIONSHIP_TYPE_MASTER_EOD_UPLOAD);
			} else {
				updateSyncStatus(null, RELATIONSHIP_TYPE_MASTER_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
			}
			if (taskMap.containsKey(WEAKER_SECTION_MASTER_EOD_UPLOAD)) {
				processCommonCodeEntryMasterFile(taskMap,WEAKER_SECTION_MASTER_EOD_UPLOAD);
			} else {
				updateSyncStatus(null, WEAKER_SECTION_MASTER_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
			}
			if (taskMap.containsKey(MSME_CLASSIFICATION_MASTER_EOD_UPLOAD)) {
				processCommonCodeEntryMasterFile(taskMap,MSME_CLASSIFICATION_MASTER_EOD_UPLOAD);
			} else {
				updateSyncStatus(null, MSME_CLASSIFICATION_MASTER_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
			}
			if (taskMap.containsKey(PSL_MIS_CODE_MASTER_EOD_UPLOAD)) {
				processCommonCodeEntryMasterFile(taskMap,PSL_MIS_CODE_MASTER_EOD_UPLOAD);
			} else {
				updateSyncStatus(null, PSL_MIS_CODE_MASTER_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
			}
			if (taskMap.containsKey(INFRASTRUCTURE_FINANCE_MASTER_EOD_UPLOAD)) {
				processCommonCodeEntryMasterFile(taskMap,INFRASTRUCTURE_FINANCE_MASTER_EOD_UPLOAD);
			} else {
				updateSyncStatus(null, INFRASTRUCTURE_FINANCE_MASTER_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
			}
			if (taskMap.containsKey(MARKET_SEGMENT_MASTER_EOD_UPLOAD)) {
				processCommonCodeEntryMasterFile(taskMap,MARKET_SEGMENT_MASTER_EOD_UPLOAD);
			} else {
				updateSyncStatus(null, MARKET_SEGMENT_MASTER_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
			}			
			
			if (taskMap.containsKey(INDUSTRY_MASTER_EOD_UPLOAD)) {
				processCommonCodeEntryMasterFile(taskMap,INDUSTRY_MASTER_EOD_UPLOAD);
			} else {
				updateSyncStatus(null, INDUSTRY_MASTER_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
			}
			if (taskMap.containsKey(RBI_INDUSTRY_MASTER_EOD_UPLOAD)) {
				processCommonCodeEntryMasterFile(taskMap,RBI_INDUSTRY_MASTER_EOD_UPLOAD);
			} else {
				updateSyncStatus(null, RBI_INDUSTRY_MASTER_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
			}
			if (taskMap.containsKey(FACILITY_MASTER_EOD_UPLOAD)) {
				processCommonCodeEntryMasterFile(taskMap,FACILITY_MASTER_EOD_UPLOAD);
			} else {
				updateSyncStatus(null, FACILITY_MASTER_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
			}
			if (taskMap.containsKey(SECURITY_MASTER_EOD_UPLOAD)) {

				OBCpsToClimsMaster securityMaster = (OBCpsToClimsMaster) taskMap.get(SECURITY_MASTER_EOD_UPLOAD);
				String processFileName = getQualifiedFileName(securityMaster.getFileName(),SYNC_DIRECTION_CPSTOCLIMS);
				DefaultLogger.debug(this, "File name: "+securityMaster.getFileName());
				IEODSyncStatus syncStatus = getEodSyncStatusProxy().findEODSyncActivityByProcessingKey(SECURITY_MASTER_EOD_UPLOAD);
				try{
				syncStatus.setProcessStartTime(new Date());
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("Exception in  setProcessStartTime "+e.getMessage());
				}
				File read = new File(processFileName);
				ProcessDataFileEOD dataFile = new ProcessDataFileEOD();
				if (read.exists()) {
					try {
						System.out.println("syncCpsToClims() s99999999999999999");

						updateSyncStatus(syncStatus, SECURITY_MASTER_EOD_UPLOAD, EODSyncProcessStatus.PROCESSING.name(), securityMaster);
						dataFile.setAckFileName(securityMaster.getFileName());
						dataFile.setFileRecordCount(securityMaster.getRecordCount());
						dataFile.processFile(read, SECURITY_MASTER_EOD_UPLOAD);
						securityMaster = setRecordCount(securityMaster, dataFile.getSuccessfullRecordCount(), dataFile
								.getFailureRecordCount());
						taskMap.put(SECURITY_MASTER_EOD_UPLOAD, securityMaster);
						updateSyncStatus(syncStatus, SECURITY_MASTER_EOD_UPLOAD, EODSyncProcessStatus.SUCCESS.name(), securityMaster);

					}
					catch (Exception e) {
						DefaultLogger.error(this, "Error while synchronizing "+SECURITY_MASTER_EOD_UPLOAD, e);
						securityMaster = setRecordCount(securityMaster, 0l, securityMaster.getRecordCount());
						syncStatus.setProcessException(e.getMessage());
						updateSyncStatus(syncStatus, SECURITY_MASTER_EOD_UPLOAD, EODSyncProcessStatus.FAILED.name(), null);
						getEodSyncStatusProxy().updateEODSyncActivity(syncStatus);
						taskMap.put(SECURITY_MASTER_EOD_UPLOAD, securityMaster);
					}
				} else {
					securityMaster = setRecordCount(securityMaster, 0l, securityMaster.getRecordCount());
					syncStatus.setProcessException("File Not Found :" + securityMaster.getFileName());
					updateSyncStatus(syncStatus, SECURITY_MASTER_EOD_UPLOAD, EODSyncProcessStatus.FAILED.name(), null);
					dataFile.setAckFileName(securityMaster.getFileName());
					// to generate empty Ack File
					ArrayList<OBCollateralNewMaster> collateralNewMasterList = dataFile.processFile(null, SECURITY_MASTER_EOD_UPLOAD);
					taskMap.put(SECURITY_MASTER_EOD_UPLOAD, securityMaster);
				}
			} else {
				updateSyncStatus(null, SECURITY_MASTER_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
			}
			if (taskMap.containsKey(SECTOR_MASTER_EOD_UPLOAD)) {
				processCommonCodeEntryMasterFile(taskMap,SECTOR_MASTER_EOD_UPLOAD);
			} else {
				updateSyncStatus(null, SECTOR_MASTER_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
			}
			
			if (taskMap.containsKey(COUNTRY_MASTER_EOD_UPLOAD)) {
				OBCpsToClimsMaster countryMaster = (OBCpsToClimsMaster) taskMap.get(COUNTRY_MASTER_EOD_UPLOAD);
				System.out.println("syncCpsToClims() 100000000000000");

				String processFileName = getQualifiedFileName(countryMaster.getFileName(),SYNC_DIRECTION_CPSTOCLIMS);
				DefaultLogger.debug(this, "File name: "+countryMaster.getFileName());
				IEODSyncStatus syncStatus = getEodSyncStatusProxy().findEODSyncActivityByProcessingKey(COUNTRY_MASTER_EOD_UPLOAD);
				try{
				syncStatus.setProcessStartTime(new Date());
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("Exception in  setProcessStartTime "+e.getMessage());
				}
				ProcessDataFileEOD dataFile = new ProcessDataFileEOD();
				File read = new File(processFileName);
				log.append("read exists: "+read.exists());
				if (read.exists()) {
					try {
						updateSyncStatus(syncStatus, COUNTRY_MASTER_EOD_UPLOAD, EODSyncProcessStatus.PROCESSING.name(), countryMaster);
						dataFile.setFileRecordCount(countryMaster.getRecordCount());
						dataFile.setAckFileName(countryMaster.getFileName());
						ArrayList<OBPartyGroup> obcountryList = dataFile.processFile(read, COUNTRY_MASTER_EOD_UPLOAD);
						countryMaster = setRecordCount(countryMaster, dataFile.getSuccessfullRecordCount(), dataFile.getFailureRecordCount());
						updateSyncStatus(syncStatus, COUNTRY_MASTER_EOD_UPLOAD, EODSyncProcessStatus.SUCCESS.name(), countryMaster);
						taskMap.put(COUNTRY_MASTER_EOD_UPLOAD, countryMaster);
					}
					catch (Exception e) {
						DefaultLogger.error(this, "Error while synchronizing "+COUNTRY_MASTER_EOD_UPLOAD, e);
						syncStatus.setProcessException(e.getMessage());
						updateSyncStatus(syncStatus, COUNTRY_MASTER_EOD_UPLOAD, EODSyncProcessStatus.FAILED.name(), null);
						countryMaster = setRecordCount(countryMaster, 0l, countryMaster.getRecordCount());
						taskMap.put(COUNTRY_MASTER_EOD_UPLOAD, countryMaster);
					}
				} else {
					countryMaster = setRecordCount(countryMaster, 0l, countryMaster.getRecordCount());
					// to generate empty Ack File
					syncStatus.setProcessException("File Not Found :" + countryMaster.getFileName());
					updateSyncStatus(syncStatus, COUNTRY_MASTER_EOD_UPLOAD, EODSyncProcessStatus.FAILED.name(), null);
					dataFile.setAckFileName(countryMaster.getFileName());
					dataFile.processFile(null, COUNTRY_MASTER_EOD_UPLOAD);
					taskMap.put(COUNTRY_MASTER_EOD_UPLOAD, countryMaster);
				}

			} else {
				updateSyncStatus(null, COUNTRY_MASTER_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
			}
			if (taskMap.containsKey(REGION_MASTER_EOD_UPLOAD)) {
				OBCpsToClimsMaster regionMaster = (OBCpsToClimsMaster) taskMap.get(REGION_MASTER_EOD_UPLOAD);
				String processFileName = getQualifiedFileName(regionMaster.getFileName(),SYNC_DIRECTION_CPSTOCLIMS);
				DefaultLogger.debug(this, "File name: "+regionMaster.getFileName());
				IEODSyncStatus syncStatus = getEodSyncStatusProxy().findEODSyncActivityByProcessingKey(REGION_MASTER_EOD_UPLOAD);
				try{
				syncStatus.setProcessStartTime(new Date());
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("Exception in  setProcessStartTime "+e.getMessage());
				}
				ProcessDataFileEOD dataFile = new ProcessDataFileEOD();
				File read = new File(processFileName);
				if (read.exists()) {
					try {
						updateSyncStatus(syncStatus, REGION_MASTER_EOD_UPLOAD, EODSyncProcessStatus.PROCESSING.name(), regionMaster);
						dataFile.setFileRecordCount(regionMaster.getRecordCount());
						dataFile.setAckFileName(regionMaster.getFileName());
						ArrayList<OBPartyGroup> obcountryList = dataFile.processFile(read, REGION_MASTER_EOD_UPLOAD);
						regionMaster = setRecordCount(regionMaster, dataFile.getSuccessfullRecordCount(), dataFile.getFailureRecordCount());
						updateSyncStatus(syncStatus, REGION_MASTER_EOD_UPLOAD, EODSyncProcessStatus.SUCCESS.name(), regionMaster);
						taskMap.put(REGION_MASTER_EOD_UPLOAD, regionMaster);
					}
					catch (Exception e) {
						DefaultLogger.error(this, "Error while synchronizing "+REGION_MASTER_EOD_UPLOAD, e);
						syncStatus.setProcessException(e.getMessage());
						updateSyncStatus(syncStatus, REGION_MASTER_EOD_UPLOAD, EODSyncProcessStatus.FAILED.name(), null);
						regionMaster = setRecordCount(regionMaster, 0l, regionMaster.getRecordCount());
						taskMap.put(REGION_MASTER_EOD_UPLOAD, regionMaster);
					}
				} else {
					regionMaster = setRecordCount(regionMaster, 0l, regionMaster.getRecordCount());
					// to generate empty Ack File
					syncStatus.setProcessException("File Not Found :" + regionMaster.getFileName());
					updateSyncStatus(syncStatus, REGION_MASTER_EOD_UPLOAD, EODSyncProcessStatus.FAILED.name(), null);
					dataFile.setAckFileName(regionMaster.getFileName());
					dataFile.processFile(null, REGION_MASTER_EOD_UPLOAD);
					taskMap.put(REGION_MASTER_EOD_UPLOAD, regionMaster);
				}

			} else {
				updateSyncStatus(null, REGION_MASTER_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
			}
			if (taskMap.containsKey(STATE_MASTER_EOD_UPLOAD)) {
				OBCpsToClimsMaster stateMaster = (OBCpsToClimsMaster) taskMap.get(STATE_MASTER_EOD_UPLOAD);
				String processFileName = getQualifiedFileName(stateMaster.getFileName(),SYNC_DIRECTION_CPSTOCLIMS);
				DefaultLogger.debug(this, "File name: "+stateMaster.getFileName());
				IEODSyncStatus syncStatus = getEodSyncStatusProxy().findEODSyncActivityByProcessingKey(STATE_MASTER_EOD_UPLOAD);
				try{
				syncStatus.setProcessStartTime(new Date());
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("Exception in  setProcessStartTime "+e.getMessage());
				}
				ProcessDataFileEOD dataFile = new ProcessDataFileEOD();
				File read = new File(processFileName);
				if (read.exists()) {
					try {
						updateSyncStatus(syncStatus, STATE_MASTER_EOD_UPLOAD, EODSyncProcessStatus.PROCESSING.name(), stateMaster);
						dataFile.setFileRecordCount(stateMaster.getRecordCount());
						dataFile.setAckFileName(stateMaster.getFileName());
						ArrayList<OBPartyGroup> obstateList = dataFile.processFile(read, STATE_MASTER_EOD_UPLOAD);
						stateMaster = setRecordCount(stateMaster, dataFile.getSuccessfullRecordCount(), dataFile.getFailureRecordCount());
						updateSyncStatus(syncStatus, STATE_MASTER_EOD_UPLOAD, EODSyncProcessStatus.SUCCESS.name(), stateMaster);
						taskMap.put(STATE_MASTER_EOD_UPLOAD, stateMaster);
					}
					catch (Exception e) {
						DefaultLogger.error(this, "Error while synchronizing "+STATE_MASTER_EOD_UPLOAD, e);
						syncStatus.setProcessException(e.getMessage());
						updateSyncStatus(syncStatus, STATE_MASTER_EOD_UPLOAD, EODSyncProcessStatus.FAILED.name(), null);
						stateMaster = setRecordCount(stateMaster, 0l, stateMaster.getRecordCount());
						taskMap.put(STATE_MASTER_EOD_UPLOAD, stateMaster);
					}
				} else {
					stateMaster = setRecordCount(stateMaster, 0l, stateMaster.getRecordCount());
					// to generate empty Ack File
					syncStatus.setProcessException("File Not Found :" + stateMaster.getFileName());
					updateSyncStatus(syncStatus, STATE_MASTER_EOD_UPLOAD, EODSyncProcessStatus.FAILED.name(), null);
					dataFile.setAckFileName(stateMaster.getFileName());
					dataFile.processFile(null, STATE_MASTER_EOD_UPLOAD);
					taskMap.put(STATE_MASTER_EOD_UPLOAD, stateMaster);
				}

			} else {
				updateSyncStatus(null, STATE_MASTER_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
			}
			if (taskMap.containsKey(CITY_MASTER_EOD_UPLOAD)) {
				OBCpsToClimsMaster cityMaster = (OBCpsToClimsMaster) taskMap.get(CITY_MASTER_EOD_UPLOAD);
				String processFileName = getQualifiedFileName(cityMaster.getFileName(),SYNC_DIRECTION_CPSTOCLIMS);
				DefaultLogger.debug(this, "File name: "+cityMaster.getFileName());
				IEODSyncStatus syncStatus = getEodSyncStatusProxy().findEODSyncActivityByProcessingKey(CITY_MASTER_EOD_UPLOAD);
				try{
				syncStatus.setProcessStartTime(new Date());
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("Exception in  setProcessStartTime "+e.getMessage());
				}
				ProcessDataFileEOD dataFile = new ProcessDataFileEOD();
				File read = new File(processFileName);
				if (read.exists()) {
					try {
						updateSyncStatus(syncStatus, CITY_MASTER_EOD_UPLOAD, EODSyncProcessStatus.PROCESSING.name(), cityMaster);
						dataFile.setFileRecordCount(cityMaster.getRecordCount());
						dataFile.setAckFileName(cityMaster.getFileName());
						ArrayList<OBPartyGroup> obcityList = dataFile.processFile(read, CITY_MASTER_EOD_UPLOAD);
						cityMaster = setRecordCount(cityMaster, dataFile.getSuccessfullRecordCount(), dataFile.getFailureRecordCount());
						updateSyncStatus(syncStatus, CITY_MASTER_EOD_UPLOAD, EODSyncProcessStatus.SUCCESS.name(), cityMaster);
						taskMap.put(CITY_MASTER_EOD_UPLOAD, cityMaster);
					}
					catch (Exception e) {
						DefaultLogger.error(this, "Error while synchronizing "+CITY_MASTER_EOD_UPLOAD, e);
						syncStatus.setProcessException(e.getMessage());
						updateSyncStatus(syncStatus, CITY_MASTER_EOD_UPLOAD, EODSyncProcessStatus.FAILED.name(), null);
						cityMaster = setRecordCount(cityMaster, 0l, cityMaster.getRecordCount());
						taskMap.put(CITY_MASTER_EOD_UPLOAD, cityMaster);
					}
				} else {
					cityMaster = setRecordCount(cityMaster, 0l, cityMaster.getRecordCount());
					// to generate empty Ack File
					syncStatus.setProcessException("File Not Found :" + cityMaster.getFileName());
					updateSyncStatus(syncStatus, CITY_MASTER_EOD_UPLOAD, EODSyncProcessStatus.FAILED.name(), null);
					dataFile.setAckFileName(cityMaster.getFileName());
					dataFile.processFile(null, CITY_MASTER_EOD_UPLOAD);
					taskMap.put(CITY_MASTER_EOD_UPLOAD, cityMaster);
				}

			} else {
				updateSyncStatus(null, CITY_MASTER_EOD_UPLOAD, EODSyncProcessStatus.NA.name(), null);
			}
			// Generating Control Ack
			ProcessDataFileEOD dataFile = new ProcessDataFileEOD();
			dataFile.generateControlFileAck(taskMap,getCpsControlFileName());
			System.out.println("syncCpsToClims() ddddddd11111111111111ss");

	}
	}
	
	/*for testing only*/
	//IEODSyncStatusDao eodSyncStatusDao;
	ILimitDAO eodSyncStatusDao;
	public IEODSyncStatusDao getEodSyncStatusDao() {
		//return new EODSyncStatusDaoImpl();
		return (IEODSyncStatusDao) BeanHouse.get("eodSyncStatusDao");
	}
	/*public ILimitDAO getEodSyncStatusDao() {
		return new LimitDAO();
	}*/
	private void processCommonCodeEntryMasterFile(HashMap<String, OBCpsToClimsMaster> taskMap,final String masterNameKey) {
		OBCpsToClimsMaster masterObj = (OBCpsToClimsMaster) taskMap.get(masterNameKey);
		String processFileName = getQualifiedFileName(masterObj.getFileName(),SYNC_DIRECTION_CPSTOCLIMS);
		DefaultLogger.debug(this,"Master File ----" + processFileName);
		IEODSyncStatus syncStatus=getEodSyncStatusProxy().findEODSyncActivityByProcessingKey(masterNameKey);
		syncStatus.setProcessStartTime(new Date());
		File read = new File(processFileName);
		DefaultLogger.debug(this,"File Exist? "+processFileName);
		ProcessDataFileEOD dataFile = new ProcessDataFileEOD();
		if (read.exists()) {
			try {
				updateSyncStatus(syncStatus, masterNameKey, EODSyncProcessStatus.PROCESSING.name(), masterObj);
				dataFile.setAckFileName(masterObj.getFileName());
				dataFile.setFileRecordCount(masterObj.getRecordCount());
				DefaultLogger.debug(this, masterNameKey+" Status: "+EODSyncProcessStatus.PROCESSING.name());
				dataFile.processFile(read, masterNameKey);
				masterObj = setRecordCount(masterObj, dataFile.getSuccessfullRecordCount(), dataFile
						.getFailureRecordCount());
				taskMap.put(masterNameKey, masterObj);
				updateSyncStatus(syncStatus, masterNameKey, EODSyncProcessStatus.SUCCESS.name(), masterObj);

			}
			catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.error(this,e.getMessage());
				masterObj = setRecordCount(masterObj, 0l, masterObj.getRecordCount());
				syncStatus.setProcessException(e.getMessage());
				updateSyncStatus(syncStatus, masterNameKey, EODSyncProcessStatus.FAILED.name(), null);
				getEodSyncStatusProxy().updateEODSyncActivity(syncStatus);
				taskMap.put(masterNameKey, masterObj);
			}
		} else {
			masterObj = setRecordCount(masterObj, 0l, masterObj.getRecordCount());
			syncStatus.setProcessException("File Not Found :" + masterObj.getFileName());
			updateSyncStatus(syncStatus, masterNameKey, EODSyncProcessStatus.FAILED.name(), null);
			dataFile.setAckFileName(masterObj.getFileName());
			// to generate empty Ack File
			dataFile.processFile(null, masterNameKey);
			taskMap.put(masterNameKey, masterObj);
		}
		
		DefaultLogger.info(this, masterNameKey + " Status: " + syncStatus.getProcessStatus() + ", ProcessException: "
						+ syncStatus.getProcessException() + " RecordCount" + masterObj.getRecordCount()
						+ " FailedRecordCount: " + masterObj.getFailureRecordCount());
	}

	private void updateSyncStatus(IEODSyncStatus syncStatus,String processingKey,String status, OBCpsToClimsMaster obCpsToClimsMaster) {
		DefaultLogger.info(this,"syncStatus: " + syncStatus + ", processingKey: " + processingKey + ", status: " + status
				+ ", ob: " + obCpsToClimsMaster);
		if(null == syncStatus){
			syncStatus = getEodSyncStatusProxy().findEODSyncActivityByProcessingKey(processingKey);
			System.out.println("findEODSyncActivityByProcessingKey=="+syncStatus);
			try{
			syncStatus.setProcessStartTime(new Date());
			syncStatus.setCurrentDate(new Date());
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("Exception in updateSyncStatus():: setProcessStartTime "+e.getMessage());
			}
		}
		

		if(EODSyncProcessStatus.PROCESSING.name().equals(status)){
			syncStatus.setApplicationDate(new Date(getApplicationDate()));
			syncStatus.setProcessStatus(EODSyncProcessStatus.PROCESSING.name());
		}else if(EODSyncProcessStatus.SUCCESS.name().equals(status)){
			syncStatus.setTotalCount(obCpsToClimsMaster.getRecordCount());
			syncStatus.setSuccessCount(obCpsToClimsMaster.getSuccessfullRecordCount());
			syncStatus.setFailedCount(obCpsToClimsMaster.getFailureRecordCount());
			syncStatus.setProcessStatus(EODSyncProcessStatus.SUCCESS.name());
			try{
			syncStatus.setProcessEndTime(new Date());
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("Exception in updateSyncStatus():: setProcessStartTime "+e.getMessage());
			}
		}else if(EODSyncProcessStatus.FAILED.name().equals(status)){
			try{
			syncStatus.setProcessEndTime(new Date());
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("Exception in updateSyncStatus():: setProcessStartTime "+e.getMessage());
			}
			syncStatus.setProcessStatus(EODSyncProcessStatus.FAILED.name());
		}else if(EODSyncProcessStatus.NA.name().equals(status)){
			try{
			syncStatus.setApplicationDate(new Date(getApplicationDate()));
			syncStatus.setProcessStatus(EODSyncProcessStatus.NA.name());
			syncStatus.setProcessEndTime(new Date());
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("Exception in updateSyncStatus():: setProcessStartTime "+e.getMessage());
			}
		}
		getEodSyncStatusProxy().updateEODSyncActivity(syncStatus);
	}

	private OBCpsToClimsMaster setRecordCount(OBCpsToClimsMaster cpsToClimsMasterOB,Long successCount,Long failedCount) {
		cpsToClimsMasterOB.setSuccessfullRecordCount(successCount);
		cpsToClimsMasterOB.setFailureRecordCount(failedCount);
		return cpsToClimsMasterOB;
	}
	private String getQualifiedFileName(String entityName,String syncDirection){
		String fileName="";
		if(SYNC_DIRECTION_CPSTOCLIMS.equals(syncDirection)){
			String cpsDataDirectory = PropertyManager.getValue(FTP_MASTER_DOWNLOAD_LOCAL_DIR);
			fileName = cpsDataDirectory+entityName;
			
		}else if(SYNC_DIRECTION_CLIMSTOCPS.equals(syncDirection)){
			String climsLocalAckDirectory = PropertyManager.getValue(FTP_MASTER_DOWNLOAD_LOCAL_CLIMS_ACK_DIR);
			fileName = climsLocalAckDirectory+entityName;
		}
		return fileName;
	}
	private HashMap<String, OBCpsToClimsMaster> getMastersToBeProcessed() {
		HashMap<String, OBCpsToClimsMaster> taskMap= new HashMap<String, OBCpsToClimsMaster>();
		
		try {
		String controlFileName = getCpsControlFileName();

		if(controlFileName==null || "".equals(controlFileName)){
			return taskMap;
		}
		//getBasePath+controlFileName
		String controlFileNameWithPath =getQualifiedFileName(controlFileName,SYNC_DIRECTION_CPSTOCLIMS);
		
		File controlFile=new File(controlFileNameWithPath);
		int counter=1;
		DefaultLogger.debug(this,"Control File exist? "+controlFile.exists()+", in path: "+controlFileNameWithPath);
		System.out.println("Control File exist? "+controlFile.exists()+", in path: "+controlFileNameWithPath);

		if(controlFile.exists()){
			ProcessDataFileEOD dataFile = new ProcessDataFileEOD();
			ArrayList<OBCpsToClimsMaster> obCpsToClimsMasterList = dataFile.processFile(controlFile,CPS_CLIMS_CONTROL_FILE);
			DefaultLogger.debug(this,"CPS to CLIMS Master list size: "+obCpsToClimsMasterList.size());
		System.out.println("CPS to CLIMS Master list size: "+obCpsToClimsMasterList.size());
			String key="";
			for (OBCpsToClimsMaster cpsToClimsMaster : obCpsToClimsMasterList) {

				String masterFileName = cpsToClimsMaster.getFileName();
				for(String masterFileNameKey :cpsToClimsMasterMap.keySet()){
					if(masterFileName!=null && masterFileName.startsWith(masterFileNameKey)){
						key=cpsToClimsMasterMap.get(masterFileNameKey);
						break;
					}
				}
				
				if(key!=null &&!"".equals(key) && cpsToClimsMaster.isValidRecord()){
					taskMap.put(key, cpsToClimsMaster);
				}else{
					cpsToClimsMaster.setFailureRecordCount(cpsToClimsMaster.getRecordCount()==null?0:cpsToClimsMaster.getRecordCount());
					cpsToClimsMaster.setRecordCount(cpsToClimsMaster.getRecordCount()==null?0:cpsToClimsMaster.getRecordCount());
					taskMap.put("INVALID_MASTER"+counter, cpsToClimsMaster);
					DefaultLogger.debug(this, "Key not found for file "+masterFileName);
					System.out.println("Key not found for file "+masterFileName);
					String message=" Error in Control file.";
					if(!cpsToClimsMaster.isValidRecord()){
						message +=" Row : "+cpsToClimsMaster.getRowIndex()+", File Name : "+cpsToClimsMaster.getFileName()+", "+cpsToClimsMaster.getErrorMessage();
					}else{
						message +=" Row : "+cpsToClimsMaster.getRowIndex()+", File Name : "+cpsToClimsMaster.getFileName()+",  Invalid Master File Name.";
					}
					if(masterFileName!=null && masterFileName.contains(".")){
						masterFileName = masterFileName.substring(0,masterFileName.indexOf("."));
					}
					if(masterFileName==null){
						masterFileName="INVALID_MASTER"+counter;
					}
					DATReader.generateFile(SYNC_DIRECTION_CPSTOCLIMS,masterFileName+"_ACK.dat", message);
					counter++;
				}
			}
		}else{
			String message="Control File Not found. File Name : "+controlFileName;
			DATReader.generateFile(SYNC_DIRECTION_CPSTOCLIMS,IEndOfDaySyncMastersService.CPS_CLIMS_CONTROL_FILE+"_" +
					CommonUtil.getCurrentDateTimeStamp()+
					"_ACK.dat", message);
		}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("exception=========="+e.getMessage());
			DefaultLogger.error(this, "Error encountered during control file processing. ", e);
		}
		
		return taskMap;
	}

	private String getCpsControlFileName() {
		String controlFileName="";
		String errorMessage="";
		String localDataDirPath = PropertyManager.getValue(FTP_MASTER_DOWNLOAD_LOCAL_DIR);
		DefaultLogger.debug(this,"Local data directory path: "+localDataDirPath);
		File localDataDirFileList= new File(localDataDirPath);
		String[] ackFileList = localDataDirFileList.list();
		int controlFileCount=0;
		if(ackFileList!=null){
			for(String fileName : ackFileList){
				if(fileName.startsWith(CPS_CLIMS_CONTROL_FILE) && fileName.toLowerCase().endsWith(".dat")){
					controlFileName=fileName;
					controlFileCount++;
				}
			}
		}
		
		if(controlFileCount<1){
			errorMessage="Control file not found starting with "+CPS_CLIMS_CONTROL_FILE+" and having '.dat' extenstion.";
		}else if(controlFileCount>1){
			errorMessage="Multiple Control File found.";
		}
		
		if(controlFileCount!=1){
			controlFileName="";
			DATReader.generateFile(SYNC_DIRECTION_CPSTOCLIMS,IEndOfDaySyncMastersService.CPS_CLIMS_CONTROL_FILE+"_" +
					CommonUtil.getCurrentDateTimeStamp()+
					"_ACK.dat", errorMessage);
		}
		DefaultLogger.debug(this,"Local data directory path: "+localDataDirPath+", Control Filename: "+controlFileName);
		return controlFileName;
	}
	private String getClimsControlFileName() {
		String controlFileName="";
		String localDataDirPath = PropertyManager.getValue(FTP_MASTER_DOWNLOAD_LOCAL_CLIMS_ACK_DIR);
		File localDataDirFileList= new File(localDataDirPath);
		String[] ackFileList = localDataDirFileList.list();
		int controlFileCount=0;
		if(ackFileList!=null){
			for(String fileName : ackFileList){
				if(fileName.startsWith(CLIMS_CPS_CONTROL_FILE)){
					controlFileName=fileName;
					controlFileCount++;
				}
			}
		}
		if(controlFileCount<1){
			DefaultLogger.debug(this,"Clims to CPS control file for acknowledgement file not found.");
		}else if(controlFileCount>1){
			controlFileName="";
			DefaultLogger.debug(this,"Clims to CPS control file for acknowlegement multiple file found.");
		}
		return controlFileName;
	}
	
	public void processRecord(String masterName, Object obToStore) throws Exception{
		DefaultLogger.info(this, "Process Master: "+masterName);
		if(SYSTEM_BANK_EOD_UPLOAD.equalsIgnoreCase(masterName)){
			processSystemBankBranchRecord(obToStore);
		}else if(PARTY_GROUP_EOD_UPLOAD.equalsIgnoreCase(masterName)){
			processPartyGroupRecord(obToStore);
		}else if(COUNTRY_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)){
		    processCountryRecord(obToStore);
		}else if(REGION_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)){
			System.out.println(" processCountryRecord START masterName=========="+masterName);
			processRegionRecord(obToStore);
			System.out.println("processCountryRecord END==========");
		}else if(STATE_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)){
			processStateRecord(obToStore);
		} else if(CITY_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)){
			processCityRecord(obToStore);
		}else if(COMMODITY_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)
					|| INDUSTRY_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)
					|| RBI_INDUSTRY_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)
					|| FACILITY_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)
					|| SECTOR_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)
					|| CURRENCY_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)
					|| BANKING_METHOD_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)
					|| MINORITY_COMMUNITY_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)
					|| NBFC_A_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)
					|| NBFC_B_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)
					|| RAM_RATING_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)
					|| RBI_ASSET_CLASSIFICATION_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)
					|| RELATED_TYPE_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)
					|| RATING_TYPE_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)
					|| RELATIONSHIP_TYPE_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)
					|| WEAKER_SECTION_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)
					|| MSME_CLASSIFICATION_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)
					|| PSL_MIS_CODE_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)
					|| INFRASTRUCTURE_FINANCE_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)
					|| MARKET_SEGMENT_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)){
			processCommonCodeEntryMasterRecord(obToStore);
		}else if(SECURITY_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)){
			processSecurityMasterRecord(obToStore);
		}else if(USER_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)){
			OBRMAndCreditApprover  ob= (OBRMAndCreditApprover)obToStore;
			
			if(null != ob.getUserUnitType() && !"".equals(ob.getUserUnitType()) && null != ob.getUserRole() && !"".equals(ob.getUserRole())){
				
				if ("Business Unit".equals(ob.getUserUnitType()) && "Business".equals(ob.getUserRole())) {
					processCreditApproverRecord(obToStore);
					processRelationshipMgrRecord(obToStore);
				} else if(ob.getUserUnitType().contains("Credit") || ob.getUserRole().contains("Credit")){
					processCreditApproverRecord(obToStore);
				} else if("Business Unit".equals(ob.getUserUnitType()) && ("Relationship Manager".equals(ob.getUserRole()) || "Secretary".equals(ob.getUserRole()))) {
					processRelationshipMgrRecord(obToStore);
				}else {
					throw new UserException("This is not belongs to RM/Credit Approval");
				}
			}

		}

	}

	public void processSystemBankBranchRecord(Object obToStore){

		if(obToStore!=null){
			OBSystemBankBranch bankBranchOB=(OBSystemBankBranch)obToStore;
			OBTrxContext trxContextForMasterMaker = mcUtil.setContextForMasterMaker();
			OBTrxContext trxContextForMasterChecker = mcUtil.setContextForMasterChecker();
			DefaultLogger.info(this, "Code: " + bankBranchOB.getSystemBankBranchCode() + ", Action: " + bankBranchOB.getOperationName()
					+ ", CPS Id: " + bankBranchOB.getCpsId());
			try {
				if(SYNC_OPERATION_INSERT.equals(bankBranchOB.getOperationName())){
					// Duplicate CPS ID check
					try {
						
						boolean isRecordDuplicate = accessUtility.isRecordDuplicate("actualSystemBankBranch", String.valueOf(bankBranchOB.getCpsId()));
						if(isRecordDuplicate){
							throw new SystemBankBranchException("System bank branch cps_id is duplicate.");
						}
					} catch (SystemBankBranchException e) {
						throw new SystemBankBranchException(e.getMessage());
					}
					catch(Exception e){
						throw new SystemBankBranchException("Error in retiving System bank branch.");
					}
					//
					if(bankBranchOB.getSystemBankBranchCode()!=null && bankBranchOB.getSystemBankBranchCode().trim()!=""){
						try {
							boolean isDuplicateCode = getSystemBankBranchProxy().isUniqueCode("system_bank_branch_code",bankBranchOB.getSystemBankBranchCode());
							if(isDuplicateCode){
								throw new SystemBankBranchException("System bank branch code is duplicate.");
							}
						} catch (SystemBankBranchException e) {
							throw new SystemBankBranchException(e.getMessage());
						}
						catch(Exception e){
							throw new SystemBankBranchException("Error in retiving System bank branch code.");
						}
					}
					if(bankBranchOB.getRbiCode()!=null && bankBranchOB.getRbiCode().trim()!=""){
						try {
							boolean isDuplicateCode = getSystemBankBranchProxy().isUniqueCode("rbi_code",bankBranchOB.getRbiCode());
							if(isDuplicateCode){
								throw new SystemBankBranchException("RBI code is duplicate.");
							}
						} catch (SystemBankBranchException e) {
							throw new SystemBankBranchException(e.getMessage());
						}
						catch(Exception e){
							throw new SystemBankBranchException("Error in retiving RBI CODE.");
						}
					}
					if(bankBranchOB.getSystemBankBranchName()!=null && bankBranchOB.getSystemBankBranchName().trim()!=""){
						try {
							boolean isDuplicateCode = getSystemBankBranchProxy().isUniqueCode("system_bank_branch_name",bankBranchOB.getSystemBankBranchName());
							if(isDuplicateCode){
								throw new SystemBankBranchException("System bank branch name is duplicate.");
							}
						} catch (SystemBankBranchException e) {
							throw new SystemBankBranchException(e.getMessage());
						}
						catch(Exception e){
							throw new SystemBankBranchException("Error in retiving System Bank Branch Name.");
						}
					}
					bankBranchOB.setDeprecated("N");
					//
					ISystemBankBranchTrxValue trxValueCreate = getSystemBankBranchProxy().makerCreateSystemBankBranch(trxContextForMasterMaker,bankBranchOB);
					ISystemBankBranchTrxValue systemBankBranchByTrxID = getSystemBankBranchProxy().getSystemBankBranchByTrxID(trxValueCreate.getTransactionID());
					ISystemBankBranchTrxValue trxValueApprove=getSystemBankBranchProxy().checkerApproveSystemBankBranch(trxContextForMasterChecker,systemBankBranchByTrxID);
				}else if(SYNC_OPERATION_UPDATE.equals(bankBranchOB.getOperationName())){
					OBSystemBankBranch bankBranchOBToUpdate = (OBSystemBankBranch) accessUtility.getObjByEntityNameAndCPSId("actualSystemBankBranch", String.valueOf(bankBranchOB.getCpsId()));
					if(bankBranchOBToUpdate!=null){

						long bankBranchId = bankBranchOBToUpdate.getId();

						ISystemBankBranchTrxValue trxValue = (OBSystemBankBranchTrxValue) getSystemBankBranchProxy().getSystemBankBranchTrxValue(bankBranchId);
						OBSystemBankBranch systemBankBranch = (OBSystemBankBranch) trxValue.getSystemBankBranch();
						if((trxValue.getStatus().equals("PENDING_CREATE"))
								||(trxValue.getStatus().equals("PENDING_UPDATE"))
								||(trxValue.getStatus().equals("PENDING_DELETE"))
								||(trxValue.getStatus().equals("REJECTED"))
								||(trxValue.getStatus().equals("DRAFT"))
								||(trxValue.getStatus().equals("DELETED")))
						{
							throw new SystemBankBranchException("Unable to update due to invalid transaction Status : "+trxValue.getStatus());
						}
						bankBranchOB.setDeprecated("N");
						bankBranchOB.setId(systemBankBranch.getId());
						// ==============Duplicate validation=======================
							try {
								boolean isDuplicateCode=false;
								String oldSystemBankBranchCode=bankBranchOBToUpdate.getSystemBankBranchCode();
								String newSystemBankBranchCode=bankBranchOB.getSystemBankBranchCode();
								if(!oldSystemBankBranchCode.equals(newSystemBankBranchCode))
									isDuplicateCode = getSystemBankBranchProxy().isUniqueCode("system_bank_branch_code",bankBranchOB.getSystemBankBranchCode());
								if(isDuplicateCode){
									throw new SystemBankBranchException("System bank branch code is duplicate.");
								}
							} catch (SystemBankBranchException e) {
								throw new SystemBankBranchException(e.getMessage());
							}
							catch(Exception e){
								throw new SystemBankBranchException("Error in retiving System bank branch code.");
							}
							
						if(bankBranchOB.getRbiCode()!=null && bankBranchOB.getRbiCode().trim()!=""){
							try {
								boolean isDuplicateCode=false;
								String oldRBICode=bankBranchOBToUpdate.getRbiCode();
								String newRBICode=bankBranchOB.getRbiCode();
								
								if(!oldRBICode.equals(newRBICode))
									isDuplicateCode = getSystemBankBranchProxy().isUniqueCode("rbi_code",bankBranchOB.getRbiCode());
								if(isDuplicateCode){
									throw new SystemBankBranchException("RBI code is duplicate.");
								}
							} catch (SystemBankBranchException e) {
								throw new SystemBankBranchException(e.getMessage());
							}
							catch(Exception e){
								throw new SystemBankBranchException("Error in retiving RBI CODE.");
							}
						}
						if(bankBranchOB.getSystemBankBranchName()!=null && bankBranchOB.getSystemBankBranchName().trim()!=""){
							try {
								boolean isDuplicateCode=false;
								String oldSystemBankBranchName=bankBranchOBToUpdate.getSystemBankBranchName();
								String newSystemBankBranchName=bankBranchOB.getSystemBankBranchName();
								
								if(!oldSystemBankBranchName.equals(newSystemBankBranchName))
									isDuplicateCode = getSystemBankBranchProxy().isUniqueCode("system_bank_branch_name",bankBranchOB.getSystemBankBranchName());
								if(isDuplicateCode){
									throw new SystemBankBranchException("System bank branch name is duplicate.");
								}
							} catch (SystemBankBranchException e) {
								throw new SystemBankBranchException(e.getMessage());
							}
							catch(Exception e){
								throw new SystemBankBranchException("Error in retiving System Bank Branch Name.");
							}
						}
						//==========================================================
						
						
						
						//Maker Edits
						ISystemBankBranchTrxValue editTrxVal = getSystemBankBranchProxy().makerUpdateSystemBankBranch(trxContextForMasterMaker, trxValue, bankBranchOB);
						//Retrieve
						trxValue = (OBSystemBankBranchTrxValue) getSystemBankBranchProxy().getSystemBankBranchByTrxID(editTrxVal.getTransactionID());
						//Checker approve
						ISystemBankBranchTrxValue trxValueOut = getSystemBankBranchProxy().checkerApproveSystemBankBranch(trxContextForMasterChecker, trxValue);
					}else{
						throw new SystemBankBranchException("Record not found for CPS ID :"+bankBranchOB.getCpsId());
					}


				}else if(SYNC_OPERATION_DELETE.equals(bankBranchOB.getOperationName())){

					OBSystemBankBranch bankBranchOBToDelete = (OBSystemBankBranch) accessUtility.getObjByEntityNameAndCPSId("actualSystemBankBranch", String.valueOf(bankBranchOB.getCpsId()));
					if(bankBranchOBToDelete!=null){
						long bankBranchId = bankBranchOBToDelete.getId();

						ISystemBankBranchTrxValue trxValue = (OBSystemBankBranchTrxValue) getSystemBankBranchProxy().getSystemBankBranchTrxValue(bankBranchId);
						OBSystemBankBranch systemBankBranch = (OBSystemBankBranch) trxValue.getSystemBankBranch();
						if((trxValue.getStatus().equals("PENDING_CREATE"))
								||(trxValue.getStatus().equals("PENDING_UPDATE"))
								||(trxValue.getStatus().equals("PENDING_DELETE"))
								||(trxValue.getStatus().equals("REJECTED"))
								||(trxValue.getStatus().equals("DRAFT"))
								||(trxValue.getStatus().equals("DELETED")))
						{
							throw new SystemBankBranchException("Unable to delete due to invalid transaction Status :"+trxValue.getStatus());
						}
						bankBranchOB.setId(systemBankBranch.getId());
						bankBranchOB.setDeprecated("Y");
						//maker Delete
						ISystemBankBranchTrxValue makerDeleteSystemBankBranch = getSystemBankBranchProxy().makerDeleteSystemBankBranch(trxContextForMasterMaker, trxValue, bankBranchOB);
						//Retrieve
						trxValue = (OBSystemBankBranchTrxValue) getSystemBankBranchProxy().getSystemBankBranchByTrxID(makerDeleteSystemBankBranch.getTransactionID());
						//Checker approve
						ISystemBankBranchTrxValue trxValueOut = getSystemBankBranchProxy().checkerApproveSystemBankBranch(trxContextForMasterChecker, trxValue);
					}else{
						throw new SystemBankBranchException("Record not found for CPS ID :"+bankBranchOB.getCpsId());
					}


				}else{
					DefaultLogger.debug(this, "processSystemBankBranchRecord::Invalid Operation for Sync.. ");
					throw (new CommandProcessingException("processSystemBankBranchRecord::Invalid Operation for Sync.."));
				}

			}catch (SystemBankBranchException ex) {
				DefaultLogger.debug(this, "got SystemBankBranchException in performSystemBankBranchSync" + ex);
				ex.printStackTrace();
				throw (new CommandProcessingException(ex.getMessage()));
			}catch (Exception ex) {
				DefaultLogger.debug(this, "got exception in performSystemBankBranchSync" + ex);
				throw (new CommandProcessingException(ex.getMessage()));
			}
		}else{
			DefaultLogger.debug(this, "No records to sync");
		}
	}


	public void processCreditApproverRecord(Object obToStore){

		if(obToStore!=null){
			OBRMAndCreditApprover  ob= (OBRMAndCreditApprover)obToStore;
			OBCreditApproval creditApp = new OBCreditApproval();
			creditApp.setApprovalName(ob.getUserName());
			//creditApp.setApprovalCode("APP000002");
			creditApp.setRegionId(Long.parseLong(ob.getRegion()));
			creditApp.setDeprecated(ob.getDeprecated());
			creditApp.setEmail(ob.getUserEmail());
			creditApp.setEmployeeId(ob.getLoginId());
			creditApp.setSenior(ob.getSeniorApproval());
			creditApp.setMinimumLimit(BigDecimal.valueOf(0l));
			creditApp.setMaximumLimit(BigDecimal.valueOf(Double.parseDouble(ob.getDpValue())));
			creditApp.setDeferralPowers("N");
			creditApp.setWaivingPowers("N");
			creditApp.setRisk("N");
			creditApp.setOperationName(ob.getOperationName());
			creditApp.setCreateBy(ob.getCreateBy());
			creditApp.setLastUpdateBy(ob.getLastUpdateBy());
			 IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
             IGeneralParamEntry generalParamEntries= generalParamDao.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
 			Date  applicationDate= new Date(generalParamEntries.getParamValue());
 			creditApp.setCreationDate(applicationDate);
 			creditApp.setLastUpdateDate(applicationDate);
 			creditApp.setStatus(ob.getStatus());
 			creditApp.setCpsId(ob.getCpsId());
 			ICreditApprovalTrxValue trxVal = new OBCreditApprovalTrxValue();
 			OBTrxContext trxContextForMasterMaker = mcUtil.setContextForMasterMaker();
			OBTrxContext trxContextForMasterChecker = mcUtil.setContextForMasterChecker();
			try {
				if(SYNC_OPERATION_INSERT.equals(creditApp.getOperationName())){
					//
					boolean isRecordDuplicate = false;
						try {
						
							 isRecordDuplicate = accessUtility.isRecordDuplicate("actualCreditApproval", String.valueOf(creditApp.getCpsId()));
							if(isRecordDuplicate){
								if(!("Business Unit".equals(ob.getUserUnitType()) && "Business".equals(ob.getUserRole()))) {
								throw new CreditApprovalException("Credit Approver cps_id is duplicate.");
							}
							}
						} catch (CreditApprovalException e) {
							throw new CreditApprovalException(e.getMessage());
						}
						catch(Exception e){
							throw new CreditApprovalException("Error in retiving credit approver");
						}

					//
					creditApp.setDeprecated("N");
					if(!("Business Unit".equals(ob.getUserUnitType()) && "Business".equals(ob.getUserRole()) && isRecordDuplicate == true)) {
					ICreditApprovalTrxValue trxValueCreate = getCreditApprovalProxy().makerSubmitCreditApproval(trxContextForMasterMaker,trxVal,creditApp);
					ICreditApprovalTrxValue creditAppByTrxID = getCreditApprovalProxy().getCreditApprovalByTrxID(Long.parseLong(trxValueCreate.getTransactionID()));
					ICreditApprovalTrxValue trxValueApprove=getCreditApprovalProxy().checkerApproveCreateCreditApproval(trxContextForMasterChecker,creditAppByTrxID);
					}
				}else if(SYNC_OPERATION_UPDATE.equals(creditApp.getOperationName())){


					OBCreditApproval creditApprover = (OBCreditApproval) accessUtility.getObjByEntityNameAndCPSId("actualCreditApproval", String.valueOf(creditApp.getCpsId()));

					if(creditApprover==null)
					{
						throw new CreditApprovalException("Record not found for CPS ID :"+creditApp.getCpsId());
					}
					
					ICreditApprovalTrxValue trxValue = (OBCreditApprovalTrxValue) getCreditApprovalProxy().getCreditApprovalTrxValue(creditApprover.getId());

					if((trxValue.getStatus().equals("PENDING_CREATE"))
							||(trxValue.getStatus().equals("PENDING_UPDATE"))
							||(trxValue.getStatus().equals("PENDING_DELETE"))
							||(trxValue.getStatus().equals("REJECTED"))
							||(trxValue.getStatus().equals("DRAFT"))
							||(trxValue.getStatus().equals("DELETED")))
					{
						throw new CreditApprovalException("Unable to update due to invalid transaction Status :"+trxValue.getStatus());
					}

					
					creditApprover.setEmail(creditApp.getEmail());
					creditApprover.setEmployeeId(creditApp.getEmployeeId());
					creditApprover.setSenior(creditApp.getSenior());
					//creditApprover.setRegionId(creditApp.getRegionId());
					creditApprover.setApprovalName(creditApp.getApprovalName());
					creditApprover.setDeprecated("N");
					creditApprover.setMaximumLimit(creditApp.getMaximumLimit()); 
					//Maker Edits
					ICreditApprovalTrxValue editTrxVal = getCreditApprovalProxy().makerUpdateCreditApproval(trxContextForMasterMaker, trxValue, creditApprover);
					//Retrieve
					trxValue = (OBCreditApprovalTrxValue) getCreditApprovalProxy().getCreditApprovalByTrxID(Long.parseLong(editTrxVal.getTransactionID()));
					//Checker approve
					ICreditApprovalTrxValue trxValueOut = getCreditApprovalProxy().checkerApproveUpdateCreditApproval(trxContextForMasterChecker, trxValue);

				}else if(SYNC_OPERATION_DELETE.equals(creditApp.getOperationName())){


					OBCreditApproval creditApprover = (OBCreditApproval) accessUtility.getObjByEntityNameAndCPSId("actualCreditApproval", String.valueOf(creditApp.getCpsId()));
					
					if(creditApprover==null)
					{
						throw new CreditApprovalException("Record not found for CPS ID :"+creditApp.getCpsId());
					}

					ICreditApprovalTrxValue trxValue = (OBCreditApprovalTrxValue) getCreditApprovalProxy().getCreditApprovalTrxValue(creditApprover.getId());

					if((trxValue.getStatus().equals("PENDING_CREATE"))
							||(trxValue.getStatus().equals("PENDING_UPDATE"))
							||(trxValue.getStatus().equals("PENDING_DELETE"))
							||(trxValue.getStatus().equals("REJECTED"))
							||(trxValue.getStatus().equals("DRAFT"))
							||(trxValue.getStatus().equals("DELETED")))
					{
						throw new CreditApprovalException("Unable to delete due to invalid transaction Status :"+trxValue.getStatus());
					}
					creditApprover.setDeprecated("Y");
					//maker Delete
					ICreditApprovalTrxValue makerDeleteCreditApprover = getCreditApprovalProxy().makerDeleteCreditApproval(trxContextForMasterMaker, trxValue, creditApprover);
					//Retrieve
					trxValue = (OBCreditApprovalTrxValue) getCreditApprovalProxy().getCreditApprovalByTrxID(Long.parseLong(makerDeleteCreditApprover.getTransactionID()));
					//Checker approve
					ICreditApprovalTrxValue trxValueOut = getCreditApprovalProxy().checkerApproveUpdateCreditApproval(trxContextForMasterChecker, trxValue);



				}else{
					DefaultLogger.debug(this, "processCreditApproverRecord::Invalid Operation for Sync.. ");
					throw (new CommandProcessingException("processCreditApproverRecord::Invalid Operation for Sync.."));
				}

			}catch (CreditApprovalException ex) {
				DefaultLogger.debug(this, "got CreditApprovalException in performFacilityNewMasterSync" + ex);
				ex.printStackTrace();
				throw (new CommandProcessingException(ex.getMessage()));
			}catch (Exception ex) {
				DefaultLogger.debug(this, "got exception in performCreditApproverSync" + ex);
				throw (new CommandProcessingException(ex.getMessage()));
			}
		}else{
			DefaultLogger.debug(this, "No records to sync");
		}
	}

	public void processRelationshipMgrRecord(Object obToStore){

		if(obToStore!=null){
			OBRMAndCreditApprover  ob= (OBRMAndCreditApprover)obToStore;
			OBRelationshipMgr relMgr = new OBRelationshipMgr();
			relMgr.setRelationshipMgrName(ob.getUserName());
			relMgr.setRegion(new OBRegion());
			relMgr.getRegion().setIdRegion(Long.parseLong(ob.getRegion()));
			relMgr.setDeprecated(ob.getDeprecated());
			relMgr.setRelationshipMgrMailId(ob.getUserEmail());
			relMgr.setEmployeeId(ob.getLoginId());
			relMgr.setReportingHeadName(ob.getSupervisorId());
			//relMgr.setRelationshipMgrCode("RM00001");
			relMgr.setOperationName(ob.getOperationName());
			relMgr.setCreatedBy(ob.getCreateBy());
			relMgr.setLastUpdateBy(ob.getLastUpdateBy());
			relMgr.setOperationName(ob.getOperationName());
			 IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
             IGeneralParamEntry generalParamEntries= generalParamDao.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
 			Date  applicationDate= new Date(generalParamEntries.getParamValue());
 			relMgr.setCreationDate(applicationDate);
 			relMgr.setLastUpdateDate(applicationDate);
 			relMgr.setStatus(ob.getStatus());
 			relMgr.setCpsId(ob.getCpsId());
			
 			OBTrxContext trxContextForMasterMaker = mcUtil.setContextForMasterMaker();
			OBTrxContext trxContextForMasterChecker = mcUtil.setContextForMasterChecker();
			try {
				if(SYNC_OPERATION_INSERT.equals(relMgr.getOperationName())){
					//
					boolean isRecordDuplicate = false;
						try {
							  isRecordDuplicate = accessUtility.isRecordDuplicate("actualRelationshipMgr", String.valueOf(relMgr.getCpsId()));
							if(isRecordDuplicate){
								if(!("Business Unit".equals(ob.getUserUnitType()) && "Business".equals(ob.getUserRole()))) {
								throw new RelationshipMgrException("Relationship Mgr cps_id is duplicate.");
							}
							}

						} catch (RelationshipMgrException e) {
							throw new RelationshipMgrException(e.getMessage());
						}
						catch(Exception e){
							throw new RelationshipMgrException("Error in retiving relationship Mgr code.");
						}
						relMgr.setDeprecated("N");
					if(!("Business Unit".equals(ob.getUserUnitType()) && "Business".equals(ob.getUserRole()) && isRecordDuplicate == true)) {
					IRelationshipMgrTrxValue trxValueOut = new OBRelationshipMgrTrxValue();
					IRelationshipMgrTrxValue trxValueCreate = getRelationshipMgrProxyManager().makerCreateRelationshipMgr(trxContextForMasterMaker,trxValueOut,relMgr);
					IRelationshipMgrTrxValue rmByTrxID = getRelationshipMgrProxyManager().getRelationshipMgrByTrxID(trxValueCreate.getTransactionID());
					IRelationshipMgrTrxValue trxValueApprove=getRelationshipMgrProxyManager().checkerApproveRelationshipMgr(trxContextForMasterChecker,rmByTrxID);
					}
				}else if(SYNC_OPERATION_UPDATE.equals(relMgr.getOperationName())){


					OBRelationshipMgr rmMaster = (OBRelationshipMgr) accessUtility.getObjByEntityNameAndCPSId("actualRelationshipMgr", String.valueOf(relMgr.getCpsId()));

					if(rmMaster == null){
						throw new RelationshipMgrException("Record not found for CPS ID :"+relMgr.getCpsId());
					}
					
					IRelationshipMgrTrxValue trxValue = (OBRelationshipMgrTrxValue) getRelationshipMgrProxyManager().getRelationshipMgrTrxValue(rmMaster.getId());

					if((trxValue.getStatus().equals("PENDING_CREATE"))
							||(trxValue.getStatus().equals("PENDING_UPDATE"))
							||(trxValue.getStatus().equals("PENDING_DELETE"))
							||(trxValue.getStatus().equals("REJECTED"))
							||(trxValue.getStatus().equals("DRAFT"))
							||(trxValue.getStatus().equals("DELETED")))
					{
						throw new RelationshipMgrException("Unable to update due to invalid transaction Status :"+trxValue.getStatus());
					}
	
					//rmMaster.setRegion(new OBRegion());
					//rmMaster.getRegion().setIdRegion(relMgr.getRegion().getIdRegion());
					rmMaster.setReportingHeadName(relMgr.getReportingHeadName());
					rmMaster.setRelationshipMgrMailId(relMgr.getRelationshipMgrMailId());
					rmMaster.setEmployeeId(relMgr.getEmployeeId());
					rmMaster.setRelationshipMgrName(relMgr.getRelationshipMgrName());
					rmMaster.setDeprecated("N");
					//Maker Edits
					IRelationshipMgrTrxValue editTrxVal = getRelationshipMgrProxyManager().makerUpdateRelationshipMgr(trxContextForMasterMaker, trxValue, rmMaster);
					//Retrieve
					trxValue = (OBRelationshipMgrTrxValue) getRelationshipMgrProxyManager().getRelationshipMgrByTrxID(editTrxVal.getTransactionID());
					//Checker approve
					IRelationshipMgrTrxValue trxValueOut = getRelationshipMgrProxyManager().checkerApproveRelationshipMgr(trxContextForMasterChecker, trxValue);

				}else if(SYNC_OPERATION_DELETE.equals(relMgr.getOperationName())){


					OBRelationshipMgr rmMaster = (OBRelationshipMgr) accessUtility.getObjByEntityNameAndCPSId("actualRelationshipMgr", String.valueOf(relMgr.getCpsId()));
					
					if(rmMaster == null){
						throw new RelationshipMgrException("Record not found for CPS ID :"+relMgr.getCpsId());
					}
					
					IRelationshipMgrTrxValue trxValue = (OBRelationshipMgrTrxValue) getRelationshipMgrProxyManager().getRelationshipMgrTrxValue(rmMaster.getId());

					if((trxValue.getStatus().equals("PENDING_CREATE"))
							||(trxValue.getStatus().equals("PENDING_UPDATE"))
							||(trxValue.getStatus().equals("PENDING_DELETE"))
							||(trxValue.getStatus().equals("REJECTED"))
							||(trxValue.getStatus().equals("DRAFT"))
							||(trxValue.getStatus().equals("DELETED")))
					{
						throw new RelationshipMgrException("Unable to delete due to invalid transaction Status :"+trxValue.getStatus());
					}
					rmMaster.setDeprecated("Y");
					//maker Delete
					IRelationshipMgrTrxValue makerDeleteRM = getRelationshipMgrProxyManager().makerDeleteRelationshipMgr(trxContextForMasterMaker, trxValue, rmMaster);
					//Retrieve
					trxValue = (OBRelationshipMgrTrxValue) getRelationshipMgrProxyManager().getRelationshipMgrByTrxID(makerDeleteRM.getTransactionID());
					//Checker approve
					IRelationshipMgrTrxValue trxValueOut = getRelationshipMgrProxyManager().checkerApproveRelationshipMgr(trxContextForMasterChecker, trxValue);



				}else{
					DefaultLogger.debug(this, "processRelMgrMasterRecord::Invalid Operation for Sync.. ");
					throw (new CommandProcessingException("processRelMgrMasterRecord::Invalid Operation for Sync.."));
				}

			}catch (FacilityNewMasterException ex) {
				DefaultLogger.debug(this, "got RelMgr in performRelMgrMasterSync" + ex);
				ex.printStackTrace();
				throw (new CommandProcessingException(ex.getMessage()));
			}catch (Exception ex) {
				DefaultLogger.debug(this, "got exception in performRelMgrMasterSync" + ex);
				throw (new CommandProcessingException(ex.getMessage()));
			}
		}else{
			DefaultLogger.debug(this, "No records to sync");
		}
	}

	public void processFacilityMasterRecord(Object obToStore){

		if(obToStore!=null){
			OBFacilityNewMaster facilityMasterOB=(OBFacilityNewMaster)obToStore;
			OBTrxContext trxContextForMasterMaker = mcUtil.setContextForMasterMaker();
			OBTrxContext trxContextForMasterChecker = mcUtil.setContextForMasterChecker();
		
			try {
				if(SYNC_OPERATION_INSERT.equals(facilityMasterOB.getOperationName())){
					//
					CommonValidationHelper helper = new CommonValidationHelper();
					StringBuffer errorMsg = helper.isDataProper(facilityMasterOB);
					if(errorMsg!=null){
						throw new FacilityNewMasterException(errorMsg.toString());
					}
					facilityMasterOB.setDeprecated("N");
					IFacilityNewMasterTrxValue trxValueCreate = getFacilityNewMasterProxy().makerCreateFacilityNewMaster(trxContextForMasterMaker,facilityMasterOB);
					IFacilityNewMasterTrxValue facilityNewMasterByTrxID = getFacilityNewMasterProxy().getFacilityNewMasterByTrxID(trxValueCreate.getTransactionID());
					IFacilityNewMasterTrxValue trxValueApprove=getFacilityNewMasterProxy().checkerApproveFacilityNewMaster(trxContextForMasterChecker,facilityNewMasterByTrxID);
				}else if(SYNC_OPERATION_UPDATE.equals(facilityMasterOB.getOperationName())){


					OBFacilityNewMaster facilityNewMaster = (OBFacilityNewMaster) accessUtility.getObjByEntityNameAndCPSId("actualFacilityNewMaster", String.valueOf(facilityMasterOB.getCpsId()));
					if(facilityNewMaster!=null){
	                    boolean isFacilityNameUnique = false;
	                    boolean isLineNoUnique = false;
	                    
	                	MasterAccessUtility accessUtility= (MasterAccessUtility)BeanHouse.get("masterAccessUtility");
	    				//Category Code == SYSTEM
						OBCommonCodeEntry otherSystem = (OBCommonCodeEntry) accessUtility.getObjByEntityNameAndCPSId("actualEntryCode", String.valueOf(facilityMasterOB.getNewFacilitySystem()),"SYSTEM");
						
						 if(otherSystem!=null && !"".equals(otherSystem)){
					     	   facilityMasterOB.setNewFacilitySystem(otherSystem.getEntryCode());
					        }
					        else{
					     	   throw new FacilityNewMasterException("System_ID is Invalid");	     
					     	 
					        }
	                    
	                     String  oldFacilityName = facilityNewMaster.getNewFacilityName();
	                     String  newFacilityName = facilityMasterOB.getNewFacilityName();
						if(!newFacilityName.equals(oldFacilityName))
							isFacilityNameUnique = getFacilityNewMasterProxy().isFacilityNameUnique(newFacilityName.trim());
						
						if(isFacilityNameUnique != false){
							throw new FacilityNewMasterException("Facility Master Name is Duplicate.");
						}
						
					String 	oldLineNo = facilityNewMaster.getLineNumber();
					String 	newLineNo = facilityMasterOB.getLineNumber();
					String 	newSystem = facilityMasterOB.getNewFacilitySystem();
					
						if ( newLineNo != null && !newLineNo.equals("") ) {
							if(!newLineNo.equals(oldLineNo))
								isLineNoUnique = getFacilityNewMasterProxy().isUniqueCode(newLineNo.trim(),newSystem.trim());
							
							if(isLineNoUnique != false){
								throw new FacilityNewMasterException("Facility Line No.-System Name combination is duplicate.");
							}
						}
						if(facilityMasterOB.getNewFacilityType()!=null &&
								!(facilityMasterOB.getNewFacilityType().equalsIgnoreCase("FUNDED")
								||facilityMasterOB.getNewFacilityType().equalsIgnoreCase("NON_FUNDED")
								||facilityMasterOB.getNewFacilityType().equalsIgnoreCase("MEMO_EXPOSURE")))
						{
							throw new FacilityNewMasterException("FACILITY_TYPE is Invalid");
							
						}
						if(facilityMasterOB.getPurpose()!=null &&
								!(facilityMasterOB.getPurpose().equalsIgnoreCase("WORKING_CAPITAL")
								||facilityMasterOB.getPurpose().equalsIgnoreCase("BLANK")
								||facilityMasterOB.getPurpose().equalsIgnoreCase("OTHERS")))
						{
							throw new FacilityNewMasterException("FACILITY_FBTYPE is Invalid");
							
						}
						
					
						
						IFacilityNewMasterTrxValue trxValue = (OBFacilityNewMasterTrxValue) getFacilityNewMasterProxy().getFacilityNewMasterTrxValue(facilityNewMaster.getId());
	
						if((trxValue.getStatus().equals("PENDING_CREATE"))
								||(trxValue.getStatus().equals("PENDING_UPDATE"))
								||(trxValue.getStatus().equals("PENDING_DELETE"))
								||(trxValue.getStatus().equals("REJECTED"))
								||(trxValue.getStatus().equals("DRAFT"))
								||(trxValue.getStatus().equals("DELETED")))
						{
							throw new FacilityNewMasterException("Unable to update due to invalid transaction Status :"+trxValue.getStatus());
						}
	
						facilityNewMaster.setNewFacilityName(facilityMasterOB.getNewFacilityName());
						facilityNewMaster.setPurpose(facilityMasterOB.getPurpose());
						facilityNewMaster.setNewFacilitySystem(facilityMasterOB.getNewFacilitySystem());
						facilityNewMaster.setLineNumber(facilityMasterOB.getLineNumber());
						facilityNewMaster.setNewFacilityType(facilityMasterOB.getNewFacilityType());
						facilityNewMaster.setDeprecated("N");
						//Maker Edits
						IFacilityNewMasterTrxValue editTrxVal = getFacilityNewMasterProxy().makerUpdateFacilityNewMaster(trxContextForMasterMaker, trxValue, facilityNewMaster);
						//Retrieve
						trxValue = (OBFacilityNewMasterTrxValue) getFacilityNewMasterProxy().getFacilityNewMasterByTrxID(editTrxVal.getTransactionID());
						//Checker approve
						IFacilityNewMasterTrxValue trxValueOut = getFacilityNewMasterProxy().checkerApproveFacilityNewMaster(trxContextForMasterChecker, trxValue);
				}else{
					throw new FacilityNewMasterException("Record not found for CPS ID :"+facilityMasterOB.getCpsId());
				}
				}else if(SYNC_OPERATION_DELETE.equals(facilityMasterOB.getOperationName())){


					OBFacilityNewMaster facilityNewMaster = (OBFacilityNewMaster) accessUtility.getObjByEntityNameAndCPSId("actualFacilityNewMaster", String.valueOf(facilityMasterOB.getCpsId()));
					if(facilityNewMaster!=null){
						IFacilityNewMasterTrxValue trxValue = (OBFacilityNewMasterTrxValue) getFacilityNewMasterProxy().getFacilityNewMasterTrxValue(facilityNewMaster.getId());
	
						if((trxValue.getStatus().equals("PENDING_CREATE"))
								||(trxValue.getStatus().equals("PENDING_UPDATE"))
								||(trxValue.getStatus().equals("PENDING_DELETE"))
								||(trxValue.getStatus().equals("REJECTED"))
								||(trxValue.getStatus().equals("DRAFT"))
								||(trxValue.getStatus().equals("DELETED")))
						{
							throw new FacilityNewMasterException("Unable to delete due to invalid transaction Status :"+trxValue.getStatus());
						}
						facilityNewMaster.setDeprecated("Y");
						//maker Delete
						IFacilityNewMasterTrxValue makerDeleteFacilityNewMaster = getFacilityNewMasterProxy().makerDeleteFacilityNewMaster(trxContextForMasterMaker, trxValue, facilityNewMaster);
						//Retrieve
						trxValue = (OBFacilityNewMasterTrxValue) getFacilityNewMasterProxy().getFacilityNewMasterByTrxID(makerDeleteFacilityNewMaster.getTransactionID());
						//Checker approve
						IFacilityNewMasterTrxValue trxValueOut = getFacilityNewMasterProxy().checkerApproveFacilityNewMaster(trxContextForMasterChecker, trxValue);
					}else{
						throw new FacilityNewMasterException("Record not found for CPS ID :"+facilityMasterOB.getCpsId());
					}
				}else{
					DefaultLogger.debug(this, "processFacilityNewMasterRecord::Invalid Operation for Sync.. ");
					throw (new CommandProcessingException("processFacilityNewMasterRecord::Invalid Operation for Sync.."));
				}

			}catch (FacilityNewMasterException ex) {
				DefaultLogger.debug(this, "got FacilityNewMasterException in performFacilityNewMasterSync" + ex);
				ex.printStackTrace();
				throw (new CommandProcessingException(ex.getMessage()));
			}catch (Exception ex) {
				DefaultLogger.debug(this, "got exception in performFacilityNewMasterSync" + ex);
				throw (new CommandProcessingException(ex.getMessage()));
			}
		}else{
			DefaultLogger.debug(this, "No records to sync");
		}
	}


	public void processPartyGroupRecord(Object obToStore){

		if(obToStore!=null){
			OBPartyGroup partyGroupOB=(OBPartyGroup)obToStore;
			OBTrxContext trxContextForMasterMaker = mcUtil.setContextForMasterMaker();
			OBTrxContext trxContextForMasterChecker = mcUtil.setContextForMasterChecker();
			try {
				if(SYNC_OPERATION_INSERT.equals(partyGroupOB.getOperationName())){
					
					
					try {
						
						boolean isRecordDuplicate = accessUtility.isRecordDuplicate("actualOBPartyGroup", String.valueOf(partyGroupOB.getCpsId()));
						if(isRecordDuplicate){
							throw new PartyGroupException("party group is duplicate.");
						}
					} catch (PartyGroupException e) {
						throw new PartyGroupException(e.getMessage());
					}
					catch(Exception e){
						throw new PartyGroupException("Error in retiving party group");
					}
					partyGroupOB.setDeprecated("N");
					IPartyGroupTrxValue trxValueCreate = getPartyGroupProxy().makerCreatePartyGroup(trxContextForMasterMaker,partyGroupOB);
					IPartyGroupTrxValue partyGroupByTrxID = getPartyGroupProxy().getPartyGroupByTrxID(trxValueCreate.getTransactionID());
					IPartyGroupTrxValue trxValueApprove =getPartyGroupProxy().checkerApprovePartyGroup(trxContextForMasterChecker,partyGroupByTrxID);
				}else if(SYNC_OPERATION_UPDATE.equals(partyGroupOB.getOperationName())){
					OBPartyGroup partyGroup = (OBPartyGroup) accessUtility.getObjByEntityNameAndCPSId("actualOBPartyGroup", String.valueOf(partyGroupOB.getCpsId()));
					if(partyGroup!=null){
						IPartyGroupTrxValue trxValue = (OBPartyGroupTrxValue) getPartyGroupProxy().getPartyGroupTrxValue(partyGroup.getId());
	
						if((trxValue.getStatus().equals("PENDING_CREATE"))
								||(trxValue.getStatus().equals("PENDING_UPDATE"))
								||(trxValue.getStatus().equals("PENDING_DELETE"))
								||(trxValue.getStatus().equals("REJECTED"))
								||(trxValue.getStatus().equals("DRAFT"))
								||(trxValue.getStatus().equals("DELETED")))
						{
							throw new PartyGroupException("Unable to update due to invalid transaction Status :"+trxValue.getStatus());
						}
	
						partyGroup.setPartyName(partyGroupOB.getPartyName());
						partyGroup.setDeprecated("N");
						//Maker Edits
						IPartyGroupTrxValue editTrxVal = getPartyGroupProxy().makerUpdatePartyGroup(trxContextForMasterMaker, trxValue, partyGroup);
						//Retrieve
						trxValue = (IPartyGroupTrxValue) getPartyGroupProxy().getPartyGroupByTrxID(editTrxVal.getTransactionID());
						//Checker approve
						IPartyGroupTrxValue trxValueOut = getPartyGroupProxy().checkerApprovePartyGroup(trxContextForMasterChecker, trxValue);
					}else{
						throw new PartyGroupException("Record not found for CPS ID :"+partyGroupOB.getCpsId());
					}
				}else if(SYNC_OPERATION_DELETE.equals(partyGroupOB.getOperationName())){
					OBPartyGroup partyGroup = (OBPartyGroup) accessUtility.getObjByEntityNameAndCPSId("actualOBPartyGroup", String.valueOf(partyGroupOB.getCpsId()));
					if(partyGroup!=null){
						IPartyGroupTrxValue trxValue = (OBPartyGroupTrxValue) getPartyGroupProxy().getPartyGroupTrxValue(partyGroup.getId());
	
						if((trxValue.getStatus().equals("PENDING_CREATE"))
								||(trxValue.getStatus().equals("PENDING_UPDATE"))
								||(trxValue.getStatus().equals("PENDING_DELETE"))
								||(trxValue.getStatus().equals("REJECTED"))
								||(trxValue.getStatus().equals("DRAFT"))
								||(trxValue.getStatus().equals("DELETED")))
						{
							throw new PartyGroupException("Unable to delete due to invalid transaction Status :"+trxValue.getStatus());
						}
						partyGroup.setDeprecated("Y");
						//maker Delete
						IPartyGroupTrxValue makerDeletePartyGroup = getPartyGroupProxy().makerDeletePartyGroup(trxContextForMasterMaker, trxValue, partyGroup);
						//Retrieve
						trxValue = (OBPartyGroupTrxValue) getPartyGroupProxy().getPartyGroupByTrxID(makerDeletePartyGroup.getTransactionID());
						//Checker approve
						IPartyGroupTrxValue trxValueOut = getPartyGroupProxy().checkerApprovePartyGroup(trxContextForMasterChecker, trxValue);
					}else{
						throw new PartyGroupException("Record not found for CPS ID :"+partyGroupOB.getCpsId());
					}
				}else{
					DefaultLogger.debug(this, "processPartyGroupRecord::Invalid Operation for Sync.. ");
					throw (new CommandProcessingException("processPartyGroupRecord::Invalid Operation for Sync.."));
				}

			}catch (PartyGroupException ex) {
				DefaultLogger.debug(this, "got PartyGroupException in performPartyGroupSync" + ex);
				ex.printStackTrace();
				throw (new CommandProcessingException(ex.getMessage()));
			}catch (Exception ex) {
				DefaultLogger.debug(this, "got exception in performPartyGroupSync" + ex);
				throw (new CommandProcessingException(ex.getMessage()));
			}
		}else{
			DefaultLogger.debug(this, "No records to sync");
		}
	}
		
	private static void checkTrxStatus(ICMSTrxValue trxValue) {
		if((trxValue.getStatus().equals("PENDING_CREATE"))
				||(trxValue.getStatus().equals("PENDING_UPDATE"))
				||(trxValue.getStatus().equals("PENDING_DELETE"))
				||(trxValue.getStatus().equals("REJECTED"))
				||(trxValue.getStatus().equals("DRAFT"))
				||(trxValue.getStatus().equals("DELETED")))
		{
			throw new CountryException("Unable to update due to invalid transaction Status :"+trxValue.getStatus());
		}	
	}
	/*For Country Master*/	
	
	public void processCountryRecord(Object obToStore){

		if(obToStore!=null){
			OBCountry countryOB=(OBCountry)obToStore;
			OBTrxContext trxContextForMasterMaker = mcUtil.setContextForMasterMaker();
			OBTrxContext trxContextForMasterChecker = mcUtil.setContextForMasterChecker();
			
			try {
				if(SYNC_OPERATION_INSERT.equals(countryOB.getOperationName())){				
					
					boolean isRecordDuplicate = accessUtility.isRecordDuplicate("actualCountry", String.valueOf(countryOB.getCpsId()));
					if (isRecordDuplicate) {
						throw new CountryException("country is duplicate.");
					}
					                  
					countryOB.setDeprecated("N");					
					ICountryTrxValue trxValueCreate = getCountryProxy().makerCreateCountry(trxContextForMasterMaker, null, countryOB);
					ICountryTrxValue countryByTrxID = getCountryProxy().getCountryByTrxID(trxValueCreate.getTransactionID());
					getCountryProxy().checkerApproveCountry(trxContextForMasterChecker, countryByTrxID);
				}else if(SYNC_OPERATION_UPDATE.equals(countryOB.getOperationName())){
					OBCountry country = (OBCountry) accessUtility.getObjByEntityNameAndCPSId("actualCountry", String.valueOf(countryOB.getCpsId()));
					if(country!=null){
						ICountryTrxValue trxValue = (OBCountryTrxValue) getCountryProxy().getCountryTrxValue(country.getIdCountry());
						checkTrxStatus(trxValue);
						country.setCountryName(countryOB.getCountryName());
						country.setDeprecated("N");
						ICountryTrxValue editTrxVal=getCountryProxy().makerUpdateCountry(trxContextForMasterMaker, trxValue, country);
						trxValue=getCountryProxy().getCountryByTrxID(editTrxVal.getTransactionID());	
						getCountryProxy().checkerApproveCountry(trxContextForMasterChecker, trxValue);
					}else{
						throw new CountryException("Record not found for CPS ID :"+countryOB.getCpsId());
					}
				}else if(SYNC_OPERATION_DELETE.equals(countryOB.getOperationName())){
					OBCountry country = (OBCountry) accessUtility.getObjByEntityNameAndCPSId("actualCountry", String.valueOf(countryOB.getCpsId()));
					if(country!=null){
						ICountryTrxValue trxValue = (OBCountryTrxValue) getCountryProxy().getCountryTrxValue(country.getIdCountry());	
						checkTrxStatus(trxValue);
						//maker Delete
						ICountryTrxValue makerDeleteCountry = getCountryProxy().makerDeleteCountry(trxContextForMasterMaker, trxValue, country);
						//Retrieve
						trxValue = (OBCountryTrxValue) getCountryProxy().getCountryByTrxID(makerDeleteCountry.getTransactionID());
						//Checker approve						
						getCountryProxy().checkerApproveCountry(trxContextForMasterChecker, trxValue);
					}else{
						throw new CountryException("Record not found for CPS ID :"+countryOB.getCpsId());
					}
				}else{
					DefaultLogger.error(this, "processCountryRecord::Invalid Operation for Sync.. ");
					throw (new CommandProcessingException("processCountryRecord::Invalid Operation for Sync.."));
				}

			}catch (CountryException ex) {
				DefaultLogger.error(this, "got CountryException in performCountrySync", ex);
				throw (new CommandProcessingException(ex.getMessage()));
			}catch (Exception ex) {
				DefaultLogger.error(this, "got exception in performCountrySync", ex);
				throw (new CommandProcessingException(ex.getMessage()));
			}
		}else{
			DefaultLogger.debug(this, "No records to sync");
		}
	}

	
	/*For Region Master*/
	public void processRegionRecord(Object obToStore){
		
		
		if(obToStore!=null){
			OBRegion regionOB=(OBRegion)obToStore;
			System.out.println("111 processRegionRecord() ");
			OBTrxContext trxContextForMasterMaker = mcUtil.setContextForMasterMaker();
			System.out.println("222 processRegionRecord() ");
			OBTrxContext trxContextForMasterChecker = mcUtil.setContextForMasterChecker();
			System.out.println("333 INSIDE  processRegionRecord()");
			try {
				
				long parentId = 0;
				if(regionOB.getCountryId()!=null && regionOB.getCountryId().getIdCountry()>0){
					System.out.println("INSIDE if  processRegionRecord()");
					Long countryId = Long.valueOf(regionOB.getCountryId().getIdCountry());
					System.out.println(" countryId========"+countryId);
					parentId = eodSyncOutMasterDaoNew.getCountryId(countryId);
					System.out.println(" parentId========"+parentId);
				}
				if(parentId>0){
					regionOB.getCountryId().setIdCountry(parentId);
					System.out.println(" parentId========"+parentId);
				}else {	
					System.out.println(" else No Country/Parent reference for Region Code:");
					throw new Exception("No Country/Parent reference for Region Code: "
							+ regionOB.getRegionCode() + ", CPS Id:" + regionOB.getCpsId());
				}
				
				if(SYNC_OPERATION_INSERT.equals(regionOB.getOperationName())){		
					try {
						System.out.println("inside SYNC_OPERATION_INSERT ");
						boolean isRecordDuplicate = accessUtility.isRecordDuplicate("actualRegion", String.valueOf(regionOB.getCpsId()));
						if(isRecordDuplicate){
							System.out.println("region is duplicate.");
							throw new CountryException("region is duplicate.");
						}
					} 
					catch(Exception e){
						System.out.println("region is duplicate."+e.getMessage());
						DefaultLogger.error(this, "Error while checking duplication for region: "+regionOB.getRegionCode(), e);
						throw new CountryException("Error in retiving region");
					}
					System.out.println("@@@@@@######");
					regionOB.setDeprecated("N");
					System.out.println("Logger for UAT ====line no 2240"+parentId);
					IRegionTrxValue trxValueCreate=	getRegionProxy().makerCreateRegion(trxContextForMasterMaker, null, regionOB);
					System.out.println("Logger for UA afetr trxValueCreateT ====line no 2242");
					IRegionTrxValue partyGroupByTrxID = getRegionProxy().getRegionByTrxID(trxValueCreate.getTransactionID());
					System.out.println("Logger for UA afetr partyGroupByTrxID ====line no 2244");
					getRegionProxy().checkerApproveRegion(trxContextForMasterChecker, partyGroupByTrxID);
					System.out.println("Logger for UA afetr partyGroupByTrxID ====line no 2246");
					
				}else if(SYNC_OPERATION_UPDATE.equals(regionOB.getOperationName())){
					System.out.println("inside else -SYNC_OPERATION_UPDATE ");
					OBRegion region = (OBRegion) accessUtility.getObjByEntityNameAndCPSId("actualRegion", String.valueOf(regionOB.getCpsId()));
					System.out.println("inside else -SYNC_OPERATION_UPDATE region ");
					if(region!=null){
						IRegionTrxValue trxValue = (OBRegionTrxValue) getRegionProxy().getRegionTrxValue(region.getIdRegion());
						checkTrxStatus(trxValue);
						region.setRegionName(regionOB.getRegionName());
						region.setDeprecated("N");
						//Maker Edits
						IRegionTrxValue	editTrxVal=getRegionProxy().makerUpdateRegion(trxContextForMasterMaker, trxValue, region);						
						//Retrieve
						trxValue=getRegionProxy().getRegionByTrxID(editTrxVal.getTransactionID());	
						//Checker approve
						getRegionProxy().checkerApproveRegion(trxContextForMasterChecker, trxValue);
						}else{
						throw new CountryException("Record not found for CPS ID :"+regionOB.getCpsId());
					}
				}else if(SYNC_OPERATION_DELETE.equals(regionOB.getOperationName())){
					System.out.println("inside else -SYNC_OPERATION_DELETE ");
					OBRegion region = (OBRegion) accessUtility.getObjByEntityNameAndCPSId("actualRegion", String.valueOf(regionOB.getCpsId()));
					System.out.println("inside else -SYNC_OPERATION_DELETE region");
					if(region!=null){
						IRegionTrxValue trxValue = (OBRegionTrxValue) getRegionProxy().getRegionTrxValue(region.getIdRegion());
						checkTrxStatus(trxValue);
						region.setDeprecated("Y");
						//maker Delete
						IRegionTrxValue makerDeletePartyGroup=getRegionProxy().makerDeleteRegion(trxContextForMasterMaker, trxValue, region);
						//Retrieve
						trxValue = (OBRegionTrxValue) getRegionProxy().getRegionByTrxID(makerDeletePartyGroup.getTransactionID());
						//Checker approve						
						getRegionProxy().checkerApproveRegion(trxContextForMasterChecker, trxValue);
					}else{
						System.out.println("else Record not found for CPS ID");
						throw new CountryException("Record not found for CPS ID :"+regionOB.getCpsId());
					}
				}else{
					DefaultLogger.error(this, "processRegionRecord::Invalid Operation for Sync.. ");
					System.out.println("inside else");
					throw (new CommandProcessingException("processRegionRecord::Invalid Operation for Sync.."));
				}

			}catch (CountryException ex) {
				DefaultLogger.error(this, "got RegionException in performRegionSync" + ex);
				System.out.println("exception in catch1 "+ex.getMessage());
				ex.printStackTrace();
				throw (new CommandProcessingException(ex.getMessage()));
			}catch (Exception ex) {
				DefaultLogger.error(this, "got exception in performRegionSync" + ex);
				System.out.println("exception in catch2 "+ex.getMessage());
				throw (new CommandProcessingException(ex.getMessage()));
			}
		}else{
			DefaultLogger.debug(this, "No records to sync");
		}
	}
	/*For State Master*/
	
	
	public void processStateRecord(Object obToStore){

		if(obToStore!=null){
			
			OBState stateOB=(OBState) obToStore;
			OBTrxContext trxContextForMasterMaker = mcUtil.setContextForMasterMaker();
			OBTrxContext trxContextForMasterChecker = mcUtil.setContextForMasterChecker();
			
			try {
				long parentId = 0;
				if(stateOB.getRegionId()!=null && stateOB.getRegionId().getIdRegion()>0){
					Long regionId = Long.valueOf(stateOB.getRegionId().getIdRegion());
					parentId = eodSyncOutMasterDaoNew.getRegionId(regionId);
				}
				if(parentId>0){
					stateOB.getRegionId().setIdRegion(parentId);
				}else {	
					throw new LocationException("No Country/Parent reference for Region Code: "
							+ stateOB.getStateCode() + ", CPS Id:" + stateOB.getCpsId());
				}

				if(SYNC_OPERATION_INSERT.equals(stateOB.getOperationName())){
					try {
						
						boolean isRecordDuplicate = accessUtility.isRecordDuplicate("actualState", String.valueOf(stateOB.getCpsId()));
						if(isRecordDuplicate){
							throw new CountryException("state is duplicate.");
						}
					} catch (CountryException e) {
						throw new CountryException(e.getMessage());
					}
					catch(Exception e){
						throw new CountryException("Error in retiving state");
					}
					stateOB.setDeprecated("N");
						
					IStateTrxValue trxValueCreate=getStateProxy().makerCreateState(trxContextForMasterMaker, null, stateOB);
					IStateTrxValue	stateByTrxID=getStateProxy().getStateByTrxID(trxValueCreate.getTransactionID());					
					getStateProxy().checkerApproveState(trxContextForMasterChecker, stateByTrxID);
					
					
				}else if(SYNC_OPERATION_UPDATE.equals(stateOB.getOperationName())){
					OBState state = (OBState) accessUtility.getObjByEntityNameAndCPSId("actualState", String.valueOf(stateOB.getCpsId()));					
					if(state!=null){	
						IStateTrxValue trxValue = (OBStateTrxValue) getStateProxy().getStateTrxValue(state.getIdState());
						checkTrxStatus(trxValue);
					    state.setStateName(stateOB.getStateName());
					    state.setDeprecated("N");
						//Maker Edits
						IStateTrxValue	editTrxVal=getStateProxy().makerUpdateState(trxContextForMasterMaker, trxValue, state);						
						//Retrieve
						trxValue=getStateProxy().getStateByTrxID(editTrxVal.getTransactionID());	
						
						//Checker approve
						getStateProxy().checkerApproveState(trxContextForMasterChecker, trxValue);
						
					}else{
						throw new CountryException("Record not found for CPS ID :"+stateOB.getCpsId());
					}
				}else if(SYNC_OPERATION_DELETE.equals(stateOB.getOperationName())){
				    OBState state = (OBState) accessUtility.getObjByEntityNameAndCPSId("actualState", String.valueOf(stateOB.getCpsId()));
					if(state!=null){		
				    IStateTrxValue trxValue = (OBStateTrxValue) getStateProxy().getStateTrxValue(state.getIdState());
				    checkTrxStatus(trxValue);
					  state.setDeprecated("Y");
						//maker Delete
						IStateTrxValue makerDeleteState=getStateProxy().makerDeleteState(trxContextForMasterMaker, trxValue, state);						
						//Retrieve
						trxValue = (OBStateTrxValue) getStateProxy().getStateByTrxID(makerDeleteState.getTransactionID());
						//Checker approve						
					    getStateProxy().checkerApproveState(trxContextForMasterChecker, trxValue);
					}else{
						throw new CountryException("Record not found for CPS ID :"+stateOB.getCpsId());
					}
				}else{
					DefaultLogger.error(this, "processStateRecord::Invalid Operation for Sync.. ");
					throw (new CommandProcessingException("processStateRecord::Invalid Operation for Sync.."));
				}

			}catch (CountryException ex) {
				DefaultLogger.error(this, "got RegionException in performStateSync" + ex);
				ex.printStackTrace();
				throw (new CommandProcessingException(ex.getMessage()));
			}catch (Exception ex) {
				DefaultLogger.error(this, "got exception in performStateSync" + ex);
				throw (new CommandProcessingException(ex.getMessage()));
			}
		}else{
			DefaultLogger.debug(this, "No records to sync");
		}
	}
	
/*City Master Test*/
	
	
	public void processCityRecord(Object obToStore){

		if(obToStore!=null){
			OBCity cityOB=(OBCity)obToStore;
			OBTrxContext trxContextForMasterMaker = mcUtil.setContextForMasterMaker();
			OBTrxContext trxContextForMasterChecker = mcUtil.setContextForMasterChecker();
			try {
				
				long parentId = 0;
				if(cityOB.getStateId()!=null && cityOB.getStateId().getIdState()>0){
					Long stateId = Long.valueOf(cityOB.getStateId().getIdState());
					parentId = eodSyncOutMasterDaoNew.getStateId(stateId);
				}
				if(parentId>0){
					cityOB.getStateId().setIdState(parentId);
				}else {	
					throw new LocationException("No Country/Parent reference for Region Code: "
							+ cityOB.getCityCode() + ", CPS Id:" + cityOB.getCpsId());
				}
				if(SYNC_OPERATION_INSERT.equals(cityOB.getOperationName())){		
					
					try {
						
						boolean isRecordDuplicate = accessUtility.isRecordDuplicate("actualCity", String.valueOf(cityOB.getCpsId()));
						if(isRecordDuplicate){
							throw new PartyGroupException("City is duplicate.");
						}
					} catch (CountryException e) {
						throw new CountryException(e.getMessage());
					}
					catch(Exception e){
						throw new CountryException("Error in retiving City");
					}
					cityOB.setDeprecated("N");					
					ICityTrxValue trxValueCreate=getCityProxy().makerCreateCity(trxContextForMasterMaker, null, cityOB);
					ICityTrxValue cityByTrxID=getCityProxy().getCityByTrxID(trxValueCreate.getTransactionID());
					getCityProxy().checkerApproveCity(trxContextForMasterChecker, cityByTrxID);					
				}else if(SYNC_OPERATION_UPDATE.equals(cityOB.getOperationName())){
					OBCity city = (OBCity) accessUtility.getObjByEntityNameAndCPSId("actualCity", String.valueOf(cityOB.getCpsId()));
					if(city!=null){
						ICityTrxValue trxValue =(OBCityTrxValue)getCityProxy().getCityTrxValue(city.getIdCity());
						checkTrxStatus(trxValue);	
						city.setCityName(cityOB.getCityName());
						city.setDeprecated("N");
						//Maker Edits
						ICityTrxValue editTrxVal=getCityProxy().makerUpdateCity(trxContextForMasterMaker, trxValue,city);
						//Retrieve
						trxValue=getCityProxy().getCityByTrxID(editTrxVal.getTransactionID());
						//Checker approve
						getCityProxy().checkerApproveCity(trxContextForMasterChecker, trxValue);						
					}else{
						throw new CountryException("Record not found for CPS ID :"+cityOB.getCpsId());
					}
				}else if(SYNC_OPERATION_DELETE.equals(cityOB.getOperationName())){
					OBCity city = (OBCity) accessUtility.getObjByEntityNameAndCPSId("actualCity", String.valueOf(cityOB.getCpsId()));
					if(city!=null){
						ICityTrxValue trxValue = (OBCityTrxValue) getCityProxy().getCityTrxValue(city.getIdCity());
						checkTrxStatus(trxValue);
						city.setDeprecated("Y");
						//maker Delete
						ICityTrxValue makerDeletePartyGroup = getCityProxy().makerDeleteCity(trxContextForMasterMaker, trxValue, city);
						//Retrieve
						trxValue = (OBCityTrxValue) getCityProxy().getCityByTrxID(makerDeletePartyGroup.getTransactionID());
						//Checker approve
					    getCityProxy().checkerApproveCity(trxContextForMasterChecker, trxValue);
					}else{
						throw new CountryException("Record not found for CPS ID :"+cityOB.getCpsId());
					}
				}else{
					DefaultLogger.debug(this, "processCity::Invalid Operation for Sync.. ");
					throw (new CommandProcessingException("process City Record::Invalid Operation for Sync.."));
				}

			}catch (CountryException ex) {
				DefaultLogger.error(this, "got City Exception in performCitySync" + ex);
				ex.printStackTrace();
				throw (new CommandProcessingException(ex.getMessage()));
			}catch (Exception ex) {
				DefaultLogger.error(this, "got exception in performCitySync" + ex);
				throw (new CommandProcessingException(ex.getMessage()));
			}
		}else{
			DefaultLogger.debug(this, "No records to sync");
		}
	}	
	/*End test*/
	public void processSecurityMasterRecord(Object obToStore){

		if(obToStore!=null){
			OBCollateralNewMaster collateralNewMstInstance =(OBCollateralNewMaster)obToStore;
			OBTrxContext trxContextForMasterMaker = mcUtil.setContextForMasterMaker();
			OBTrxContext trxContextForMasterChecker = mcUtil.setContextForMasterChecker();
			try {
				
				/*
				 * Validation Moved to Validator class
				 * 
				 * CommonCodeDaoImpl commonCodeDaoImpl = (CommonCodeDaoImpl)BeanHouse.get("commonCodeDao");

				if(!commonCodeDaoImpl.checkEntryCodeAvailable("entryCode", "54",collateralNewMstInstance.getNewCollateralSubType())){
					throw new CollateralNewMasterException("Security Sub type not available : "+collateralNewMstInstance.getNewCollateralSubType());
				}*/
				
				if(SYNC_OPERATION_INSERT.equals(collateralNewMstInstance.getOperationName().trim())){
					if(collateralNewMstInstance.getCpsId()!=null && !collateralNewMstInstance.getCpsId().trim().isEmpty()){
						try {
							boolean isDuplicateRecord = getCollateralNewMasterProxy().isDuplicateRecord(collateralNewMstInstance.getCpsId());
							if(isDuplicateRecord){
								throw new CollateralNewMasterException("Duplicate Record: "+collateralNewMstInstance.getCpsId());
							}
							boolean isCollateralNameUnique = getCollateralNewMasterProxy().isCollateraNameUnique(collateralNewMstInstance.getNewCollateralDescription());
							if(isCollateralNameUnique){
								throw new CollateralNewMasterException("Duplicate Collateral Name: "+collateralNewMstInstance.getNewCollateralDescription());
							}
						} catch (CollateralNewMasterException e) {
							throw new CollateralNewMasterException(e.getMessage());
						}
						catch(Exception e){
							throw new CollateralNewMasterException("Error in retiving Record.");
						}
					}
					collateralNewMstInstance.setDeprecated("N");
					ICollateralNewMasterTrxValue trxValueCreate = getCollateralNewMasterProxy().makerCreateCollateralNewMaster(trxContextForMasterMaker,collateralNewMstInstance);
					ICollateralNewMasterTrxValue collateralNewMasterTrxValueByTrxID = getCollateralNewMasterProxy().getCollateralNewMasterByTrxID(trxValueCreate.getTransactionID());
					getCollateralNewMasterProxy().checkerApproveCollateralNewMaster(trxContextForMasterChecker,collateralNewMasterTrxValueByTrxID);
				}else if(SYNC_OPERATION_UPDATE.equals(collateralNewMstInstance.getOperationName().trim())){

					OBCollateralNewMaster collateralNewMasterForUpdate = (OBCollateralNewMaster) accessUtility.getObjByEntityNameAndCPSId("actualCollateralNewMaster", collateralNewMstInstance.getCpsId());
					 boolean isCollateralNameUnique = false;
					if(collateralNewMasterForUpdate!=null){
						
						String  oldCollateralName = collateralNewMasterForUpdate.getNewCollateralDescription();
	                     String  newCollateralName = collateralNewMstInstance.getNewCollateralDescription();
						if(!newCollateralName.equals(oldCollateralName))
							isCollateralNameUnique = getCollateralNewMasterProxy().isCollateraNameUnique(newCollateralName);
						
						if(isCollateralNameUnique != false){
							throw new CollateralNewMasterException("Collateral Name is Duplicate.");
						}
						
						long collNewMstUpdateId = collateralNewMasterForUpdate.getId();
						ICollateralNewMasterTrxValue trxValue = (ICollateralNewMasterTrxValue) getCollateralNewMasterProxy().getCollateralNewMasterTrxValue(collNewMstUpdateId);
						OBCollateralNewMaster collateralNewMasterObj = (OBCollateralNewMaster) trxValue.getCollateralNewMaster();
						if((trxValue.getStatus().equals("PENDING_CREATE"))
								||(trxValue.getStatus().equals("PENDING_UPDATE"))
								||(trxValue.getStatus().equals("PENDING_DELETE"))
								||(trxValue.getStatus().equals("REJECTED"))
								||(trxValue.getStatus().equals("DRAFT"))
								||(trxValue.getStatus().equals("DELETED")))
						{
							throw new SystemBankBranchException("Unable to update due to invalid transaction Status :"+trxValue.getStatus());
						}
						collateralNewMstInstance.setId(collateralNewMasterObj.getId());
						collateralNewMstInstance.setDeprecated("N");
						//Maker Edits
						ICollateralNewMasterTrxValue editTrxVal = getCollateralNewMasterProxy().makerUpdateCollateralNewMaster(trxContextForMasterMaker, trxValue, collateralNewMstInstance);
						//Retrieve
						trxValue = (OBCollateralNewMasterTrxValue) getCollateralNewMasterProxy().getCollateralNewMasterByTrxID(editTrxVal.getTransactionID());
						//Checker approve
						getCollateralNewMasterProxy().checkerApproveCollateralNewMaster(trxContextForMasterChecker, trxValue);
					}else{
						throw new CollateralNewMasterException("Record not found for CPS ID :"+collateralNewMstInstance.getCpsId());
					}
				}else if(SYNC_OPERATION_DELETE.equals(collateralNewMstInstance.getOperationName().trim())){

					OBCollateralNewMaster collateralNewMasterToDelete = (OBCollateralNewMaster) accessUtility.getObjByEntityNameAndCPSId("actualCollateralNewMaster", collateralNewMstInstance.getCpsId());
					if(collateralNewMasterToDelete!=null){
						long collNewMstDelId = collateralNewMasterToDelete.getId();

						ICollateralNewMasterTrxValue trxValue = (ICollateralNewMasterTrxValue) getCollateralNewMasterProxy().getCollateralNewMasterTrxValue(collNewMstDelId);
						OBCollateralNewMaster collateralNewMasterObj = (OBCollateralNewMaster) trxValue.getCollateralNewMaster();
						if((trxValue.getStatus().equals("PENDING_CREATE"))
								||(trxValue.getStatus().equals("PENDING_UPDATE"))
								||(trxValue.getStatus().equals("PENDING_DELETE"))
								||(trxValue.getStatus().equals("REJECTED"))
								||(trxValue.getStatus().equals("DRAFT"))
								||(trxValue.getStatus().equals("DELETED")))
						{
							throw new CollateralNewMasterException("Unable to delete due to invalid transaction Status :"+trxValue.getStatus());
						}
						collateralNewMstInstance.setId(collateralNewMasterObj.getId());
						collateralNewMstInstance.setDeprecated("Y");
						//maker Delete
						ICollateralNewMasterTrxValue makerDeleteCollNewMstTrxValue = getCollateralNewMasterProxy().makerDeleteCollateralNewMaster(trxContextForMasterMaker, trxValue, collateralNewMstInstance);
						//Retrieve
						trxValue = (OBCollateralNewMasterTrxValue) getCollateralNewMasterProxy().getCollateralNewMasterByTrxID(makerDeleteCollNewMstTrxValue.getTransactionID());
						//Checker approve
						getCollateralNewMasterProxy().checkerApproveCollateralNewMaster(trxContextForMasterChecker, trxValue);
					}else{
						throw new CollateralNewMasterException("Record not found for CPS ID :"+collateralNewMstInstance.getCpsId());
					}
				}else{
					DefaultLogger.debug(this, "processSecurityMasterRecord::Invalid Operation for Sync.. ");
					throw (new CommandProcessingException("Invalid Operation for Sync.."));
				}
			}catch (CollateralNewMasterException ex) {
				DefaultLogger.debug(this, "got CollateralException in performSecurityMasterSync" + ex);
				ex.printStackTrace();
				throw (new CommandProcessingException(ex.getMessage()));
			}catch (Exception ex) {
				DefaultLogger.debug(this, "got exception in performSecurityMasterSync" + ex);
				throw (new CommandProcessingException(ex.getMessage()));
			}
		}else{
			DefaultLogger.debug(this, "No records to sync");
		}
	}

public void processCommonCodeEntryMasterRecord(Object obToStore){
		
		if(obToStore!=null){
			OBTrxContext trxContextForMasterMaker = mcUtil.setContextForMasterMaker();
			OBTrxContext trxContextForMasterChecker = mcUtil.setContextForMasterChecker();
			try {
				OBCommonCodeEntry cmnCodeEntryMstInstance =(OBCommonCodeEntry)obToStore;
				ICommonCodeEntriesProxy proxy = CommonCodeEntriesProxyManagerFactory.getICommonCodeEntriesProxy();
				DefaultLogger.info(this, "Category Code: " + cmnCodeEntryMstInstance.getCategoryCode() 
								+ ", Entry Code: " + cmnCodeEntryMstInstance.getEntryCode() 
								+ ", Action: " + cmnCodeEntryMstInstance.getOperationName()
								+ ", CPS Id: "+cmnCodeEntryMstInstance.getCpsId());
				if(SYNC_OPERATION_INSERT.equals(cmnCodeEntryMstInstance.getOperationName().trim())){
					
					long categoryCodeId = ICMSConstant.LONG_INVALID_VALUE;
				//	CommonCodeDaoImpl commonCodeDaoImpl = (CommonCodeDaoImpl)BeanHouse.get("commonCodeDao");
					ICommonCodeDao commonCodeDaoImpl = (ICommonCodeDao)BeanHouse.get("commonCodeDao");

					if(cmnCodeEntryMstInstance.getCpsId()!=null){
						try {
							Boolean isDuplicate = commonCodeDaoImpl.isDuplicateRecord("entryCode",cmnCodeEntryMstInstance.getCategoryCode() , cmnCodeEntryMstInstance.getCpsId());
							if(isDuplicate){
								throw new CommonCodeEntriesException("Duplicate Record : "+cmnCodeEntryMstInstance.getCpsId());
							}
						}catch (CommonCodeEntriesException e) {
							DefaultLogger.error(this,e.getMessage(),e);
							throw new CommonCodeEntriesException(e.getMessage());
						}
						catch(Exception e){
							DefaultLogger.error(this,e.getMessage(),e);
							throw new CommonCodeEntriesException("Error in retiving Record.");
						}
					}

					if(cmnCodeEntryMstInstance.getCategoryCode()!=null && !cmnCodeEntryMstInstance.getCategoryCode().isEmpty()){
						categoryCodeId = commonCodeDaoImpl.getCategoryCodeId("actualCommonCodeCategory",cmnCodeEntryMstInstance.getCategoryCode());
					}
					
					if(ICMSConstant.LONG_INVALID_VALUE != categoryCodeId){
						ICommonCodeEntriesTrxValue trxValue = (ICommonCodeEntriesTrxValue)proxy.getCategoryId(categoryCodeId);
						trxValue.setTrxContext(trxContextForMasterMaker);
					
						List list = (ArrayList)trxValue.getCommonCodeEntries().getEntries();
						list.add(cmnCodeEntryMstInstance);
						trxValue.getCommonCodeEntries().setEntries(list);
						trxValue.setStagingCommonCodeEntries(trxValue.getCommonCodeEntries());

						//Update Common Code Record after inserting Common Code Entry
						trxValue = proxy.makerUpdateCategory(trxContextForMasterMaker, trxValue, trxValue.getStagingCommonCodeEntries());
						
						//Approve Record 
						ICommonCodeEntriesTrxValue updatedTrxValue = proxy.getCategoryTrxId(trxValue.getTransactionID());
						proxy.checkerApproveCategory(trxContextForMasterChecker, updatedTrxValue);
					}else{
						DefaultLogger.error(this, "processCommonCodeEntryMasterRecord:: Invalid value for categoryCodeId ");
						throw (new CommonCodeEntriesException("processCommonCodeEntryMasterRecord::Insert Operation Not performed,issue encountered "));
					}
					
				}else if(SYNC_OPERATION_UPDATE.equals(cmnCodeEntryMstInstance.getOperationName().trim())
						|| SYNC_OPERATION_DELETE.equals(cmnCodeEntryMstInstance.getOperationName().trim())){
					
					OBCommonCodeEntry obCommonCodeEntryForUpdate = (OBCommonCodeEntry) accessUtility.getObjByEntityNameAndCPSId("actualEntryCode", cmnCodeEntryMstInstance.getCpsId(),cmnCodeEntryMstInstance.getCategoryCode());
					
					if(obCommonCodeEntryForUpdate!=null){
						long categoryCodeIdForUpdate = obCommonCodeEntryForUpdate.getCategoryCodeId();
						ICommonCodeEntriesTrxValue trxValue = (ICommonCodeEntriesTrxValue) proxy.getCategoryId(categoryCodeIdForUpdate);
						ICommonCodeEntries commonCodeEntriesObj = (ICommonCodeEntries) trxValue.getCommonCodeEntries();
						if((trxValue.getStatus().equals("PENDING_CREATE"))
								||(trxValue.getStatus().equals("PENDING_UPDATE"))
								||(trxValue.getStatus().equals("PENDING_DELETE"))
								||(trxValue.getStatus().equals("REJECTED"))
								||(trxValue.getStatus().equals("DRAFT"))
								||(trxValue.getStatus().equals("DELETED")))
						{
							throw new CommonCodeEntriesException("Unable to update due to invalid transaction Status :"+trxValue.getStatus());
						}
						
						List<OBCommonCodeEntry> cmnCodeEntryList = new ArrayList<OBCommonCodeEntry>(commonCodeEntriesObj.getEntries());
						for(OBCommonCodeEntry cmnCodeEntry:cmnCodeEntryList){
							if(cmnCodeEntry.getCpsId()!=null && cmnCodeEntryMstInstance.getCpsId()!=null
									&& cmnCodeEntryMstInstance.getCpsId().equalsIgnoreCase(cmnCodeEntry.getCpsId()) ){
								if(SYNC_OPERATION_UPDATE.equals(cmnCodeEntryMstInstance.getOperationName().trim())){
									cmnCodeEntry.setEntryCode(cmnCodeEntry.getEntryCode());
									cmnCodeEntry.setEntryName(cmnCodeEntryMstInstance.getEntryName());
									cmnCodeEntry.setActiveStatus(true);
								}
								else if(SYNC_OPERATION_DELETE.equals(cmnCodeEntryMstInstance.getOperationName().trim())){
									cmnCodeEntry.setActiveStatus(false);
								}
								cmnCodeEntry.setUpdateFlag('U');
							}
						}
						
						trxValue.setStagingCommonCodeEntries(commonCodeEntriesObj);
						trxValue = proxy.makerUpdateCategory(trxContextForMasterMaker, trxValue, trxValue.getStagingCommonCodeEntries());
						
						//Approve Record 
						ICommonCodeEntriesTrxValue updatedTrxValue = proxy.getCategoryTrxId(trxValue.getTransactionID());
						proxy.checkerApproveCategory(trxContextForMasterChecker, updatedTrxValue);
					}else{
						throw new CommonCodeEntriesException("Record not found for CPS ID :"+cmnCodeEntryMstInstance.getCpsId());
					}
				}else{
					DefaultLogger.debug(this, "processCommonCodeEntryMasterRecord::Invalid Operation for Sync.. ");
					throw (new CommonCodeEntriesException("Invalid Operation for Sync.."));
				}
			}catch (CommonCodeEntriesException e) {
				DefaultLogger.error(this,e.getMessage(),e);
				throw new CommandProcessingException(e.getMessage());
			}catch (Exception ex) {
				DefaultLogger.debug(this, "got exception in processCommonCodeEntryMasterRecord"+ ex);
				throw new CommandProcessingException(ex.getMessage());
			}
		}else{
			DefaultLogger.debug(this, "No records to sync");
		}
	}
	private String getApplicationDate() {
		ILimitDAO limitDao=new LimitDAO();
		IGeneralParamEntry generalParamEntry = getGeneralParam().getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
		String applicationDate = generalParamEntry.getParamValue();
		return applicationDate;
	}

	public IEODSyncOutMasterDao getEodSyncOutMasterDao() {
		return eodSyncOutMasterDao;
	}

	public void setEodSyncOutMasterDao(IEODSyncOutMasterDao eodSyncOutMasterDao) {
		this.eodSyncOutMasterDao = eodSyncOutMasterDao;
		if(this.eodSyncOutMasterDao==null)
			this.eodSyncOutMasterDao = (IEODSyncOutMasterDao) BeanHouse.get("eodSyncOutMasterDao");
	}
	
}