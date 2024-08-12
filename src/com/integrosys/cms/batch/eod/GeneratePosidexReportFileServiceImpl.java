package com.integrosys.cms.batch.eod;

import java.text.SimpleDateFormat;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.util.MakerCheckerUserUtil;
import com.integrosys.cms.app.eod.bus.IPosidexFileGenDao;
import com.integrosys.cms.app.ftp.PosidexFtpService;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.ws.jax.common.MasterAccessUtility;
import com.integrosys.cms.batch.common.BatchResourceFactory;
import com.integrosys.cms.batch.common.filereader.ProcessPosidexOutData;


public class GeneratePosidexReportFileServiceImpl implements IGeneratePosidexReportFileService,IPosidexFileGenConstants {

	SimpleDateFormat newDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

	private IGeneralParamDao generalParam;
	private IPosidexFileGenDao posidexFileGen;	
	private ProcessPosidexOutData processPosidexOutData ;

	private StringBuffer log = new StringBuffer();
	String logfileName;
	
	MasterAccessUtility accessUtility= (MasterAccessUtility)BeanHouse.get("masterAccessUtility");
	MakerCheckerUserUtil mcUtil = (MakerCheckerUserUtil) BeanHouse.get("makerCheckerUserUtil");
	BatchResourceFactory batchResourceFactory = new BatchResourceFactory();

//	private static String TEMPLATE_INITIAL = "POSIDEX";


	public ProcessPosidexOutData getProcessPosidexOutData() {
		return processPosidexOutData;
	}
	public void setProcessPosidexOutData(ProcessPosidexOutData processPosidexOutData) {
		this.processPosidexOutData = processPosidexOutData;
	}

	public IGeneralParamDao getGeneralParam() {
		return generalParam;
	}

	public void setGeneralParam(IGeneralParamDao generalParam) {
		this.generalParam = generalParam;
	}
	
	public IPosidexFileGenDao getPosidexFileGen() {
		return posidexFileGen;
	}
	public void setPosidexFileGen(IPosidexFileGenDao posidexFileGen) {
		this.posidexFileGen = posidexFileGen;
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


	public  StringBuffer generateFile(){
		log.append("\n----- Generate Posidex File : Start ----");
		DefaultLogger.debug(this, "\n----- generatePosidexFile() : Posidex File Generation started ----");
		
 		PosidexFtpService posidexFtpService = new PosidexFtpService();
		try {
			//Clean local upload
			DefaultLogger.debug(this, "\n----- posidexFtpService.cleanLocalDir() ----");
			posidexFtpService.cleanLocalDir();
			//Archive remote upload directory
			DefaultLogger.debug(this, "\n----- posidexFtpService.archiveClimsUploadFile() ----");
			posidexFtpService.archiveClimsUploadFile();
			//generate upload files for modified master along with control file.
			DefaultLogger.debug(this, "\n----- generatePosidexFile() ----");
			generatePosidexFile();
			//upload master files and control file to remote upload directory
			DefaultLogger.debug(this, "\n----- posidexFtpService.uploadClimsMasterFiles() ----");
			posidexFtpService.uploadPosidexFileToFTP();
			//log upload status
			/*DefaultLogger.debug(this, "\n----- getEodSyncStatusProxy().logEODSyncStatus() ----");
			getEodSyncStatusProxy().logEODSyncStatus(SYNC_DIRECTION_CLIMSTOCPS);
			//reset master eodSync status
			DefaultLogger.debug(this, "\n----- getEodSyncStatusProxy().resetEODSyncStatus() ----");
			getEodSyncStatusProxy().resetEODSyncStatus(SYNC_DIRECTION_CLIMSTOCPS);*/
			DefaultLogger.debug(this, "\n----- performEODSyncClimsToCps() : EOD Master Syncronization End ----");
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return log;
	}




	private void generatePosidexFile() {
		DefaultLogger.debug(this, "inside method generatePosidexFile()..");
		try {
			getProcessPosidexOutData().generatePosidexFile();
		}
		catch (Exception e) {
//			syncStatus.setProcessStatus(EODSyncProcessStatus.FAILED.name());
//			syncStatus.setProcessException(e.getMessage());
//			getEodSyncStatusProxy().updateEODSyncActivity(syncStatus);
//			DefaultLogger.error(this, "exception occurred during syncClimsToCps() execution! ");
			e.printStackTrace();
		}
		DefaultLogger.debug(this, "Control File generated - CLIMS to cPS..");
	}





	/*private HashMap<String,OBEODSyncStatus> getMastersToBeMigrate() {
		HashMap<String,OBEODSyncStatus> climsToCPSTaskMap = new HashMap<String,OBEODSyncStatus>();
		List<OBEODSyncStatus> climsToCpsMasterList = getEodSyncStatusProxy().findEODSyncActivitiesBySyncDirection(SYNC_DIRECTION_CLIMSTOCPS);
		for (OBEODSyncStatus syncStatus : climsToCpsMasterList) {
			climsToCPSTaskMap.put(syncStatus.getProcessKey(), syncStatus);
		}

		return climsToCPSTaskMap;
	}


	private void updateSyncStatus(IEODSyncStatus syncStatus,String processingKey,String status, OBCpsToClimsMaster obCpsToClimsMaster) {
		if(syncStatus==null){
			syncStatus = getEodSyncStatusProxy().findEODSyncActivityByProcessingKey(processingKey);
			syncStatus.setProcessStartTime(new Date());
		}
		syncStatus.setCurrentDate(new Date());

		if(EODSyncProcessStatus.PROCESSING.name().equals(status)){
			syncStatus.setApplicationDate(new Date(getApplicationDate()));
			syncStatus.setProcessStatus(EODSyncProcessStatus.PROCESSING.name());
		}else if(EODSyncProcessStatus.SUCCESS.name().equals(status)){
			syncStatus.setTotalCount(obCpsToClimsMaster.getRecordCount());
			syncStatus.setSuccessCount(obCpsToClimsMaster.getSuccessfullRecordCount());
			syncStatus.setFailedCount(obCpsToClimsMaster.getFailureRecordCount());
			syncStatus.setProcessStatus(EODSyncProcessStatus.SUCCESS.name());
			syncStatus.setProcessEndTime(new Date());
		}else if(EODSyncProcessStatus.FAILED.name().equals(status)){
			syncStatus.setProcessEndTime(new Date());
			syncStatus.setProcessStatus(EODSyncProcessStatus.FAILED.name());
		}else if(EODSyncProcessStatus.NA.name().equals(status)){
			syncStatus.setApplicationDate(new Date(getApplicationDate()));
			syncStatus.setProcessStatus(EODSyncProcessStatus.NA.name());
			syncStatus.setProcessEndTime(new Date());
		}
		getEodSyncStatusProxy().updateEODSyncActivity(syncStatus);
	}*/

	/*private OBCpsToClimsMaster setRecordCount(OBCpsToClimsMaster cpsToClimsMasterOB,Long successCount,Long failedCount) {
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
			String message="Control File Not found.";
			DATReader.generateFile(SYNC_DIRECTION_CPSTOCLIMS,IEndOfDaySyncMastersService.CPS_CLIMS_CONTROL_FILE+"_" +
					CommonUtil.getCurrentDateTimeStamp()+
					"_ACK.dat", message);
			return taskMap;
		}
		//getBasePath+controlFileName
		String controlFileNameWithPath =getQualifiedFileName(controlFileName,SYNC_DIRECTION_CPSTOCLIMS);
		File controlFile=new File(controlFileNameWithPath);
		int counter=1;
		if(controlFile.exists()){
			ProcessDataFileEOD dataFile = new ProcessDataFileEOD();
			ArrayList<OBCpsToClimsMaster> obCpsToClimsMasterList = dataFile.processFile(controlFile,CPS_CLIMS_CONTROL_FILE);
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
			DefaultLogger.error(this, "Error encountered during control file processing.");
		}
		return taskMap;
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
		if(SYSTEM_BANK_EOD_UPLOAD.equalsIgnoreCase(masterName)){
			processSystemBankBranchRecord(obToStore);
		}else if(PARTY_GROUP_EOD_UPLOAD.equalsIgnoreCase(masterName)){
			processPartyGroupRecord(obToStore);
		}else if(FACILITY_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)){
			processFacilityMasterRecord(obToStore);
		}else if(COMMODITY_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)
					|| INDUSTRY_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)
					|| RBI_INDUSTRY_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)
					|| FACILITY_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)
					|| SECTOR_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)){
			processCommonCodeEntryMasterRecord(obToStore);
		}else if(SECURITY_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)){
			processSecurityMasterRecord(obToStore);
		}else if(USER_MASTER_EOD_UPLOAD.equalsIgnoreCase(masterName)){
			OBRMAndCreditApprover  ob= (OBRMAndCreditApprover)obToStore;
			
			if(ob.getDpValue()!=null && !"".equals(ob.getDpValue())){
				processCreditApproverRecord(obToStore);	
			}
			else{
				processRelationshipMgrRecord(obToStore);	
			}

		}

	}*/








	private String getApplicationDate() {

		IGeneralParamEntry generalParamEntry = getGeneralParam().getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
		String applicationDate = generalParamEntry.getParamValue();
		return applicationDate;
	}
}