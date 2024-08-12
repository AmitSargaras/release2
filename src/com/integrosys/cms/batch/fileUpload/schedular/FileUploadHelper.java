package com.integrosys.cms.batch.fileUpload.schedular;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jndi.JndiTemplate;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadDao;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.bus.OBBahrainFile;
import com.integrosys.cms.app.fileUpload.bus.OBFdFile;
import com.integrosys.cms.app.fileUpload.bus.OBFileUpload;
import com.integrosys.cms.app.fileUpload.bus.OBFinwareFile;
import com.integrosys.cms.app.fileUpload.bus.OBHongKongFile;
import com.integrosys.cms.app.fileUpload.bus.OBUbsFile;
import com.integrosys.cms.app.fileUpload.proxy.IFileUploadProxyManager;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.ftp.FileUploadFtpService;
import com.integrosys.cms.app.ftp.IFileUploadConstants;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.relationshipmgr.bus.IHRMSData;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.app.relationshipmgr.bus.OBHRMSData;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrDAOImpl;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.batch.common.filereader.ProcessDataFile;
import com.integrosys.cms.batch.ubs.IUbsErrorLog;
import com.integrosys.cms.batch.ubs.OBUbsErrorLog;
import com.integrosys.cms.ui.fileUpload.CheckerApproveFileUploadCmd;
import com.integrosys.cms.ui.fileUpload.UploadBahrainFileCmd;
import com.integrosys.cms.ui.fileUpload.UploadFdFileCmd;
import com.integrosys.cms.ui.fileUpload.UploadHongKongFileCmd;
import com.integrosys.cms.ui.fileUpload.UploadUbsFileCmd;
import com.integrosys.cms.ui.fileUpload.UploadfinWareFileCmd;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

public class FileUploadHelper{
	
	private static IFileUploadProxyManager fileUploadProxy = null;
	private static IFileUploadJdbc fileUploadJdbc = null;

	public static IFileUploadProxyManager getFileUploadProxy() {
		if (fileUploadProxy == null) {
			fileUploadProxy = (IFileUploadProxyManager) BeanHouse.get("fileUploadProxy");
        }
		return fileUploadProxy;
	}
	
	public static IFileUploadJdbc getFileUploadJdbc() {
		if (fileUploadJdbc == null) {
			fileUploadJdbc = (IFileUploadJdbc) BeanHouse.get("fileUploadJdbc");
        }
		return fileUploadJdbc;
	}
	
	public static Map downloadFilesFromRemoteLocation(String systemFileUploadType) {
		
		// Get Files from Remote location
		System.out.println("::: In FileUploadHelper :: Start downloadFilesFromRemoteLocation for systemFileUploadType: "+systemFileUploadType);
	    DefaultLogger.info("::: In FileUploadHelper :: Start downloadFilesFromRemoteLocation for systemFileUploadType: ",systemFileUploadType);
		Map resultMap = new ConcurrentHashMap();
		ConcurrentMap<String, String> dataFromCacheView = new ConcurrentHashMap<String,String>();
		ConcurrentMap<String, String> dataFromUpdLineFacilityMV = new ConcurrentHashMap<String,String>();
		String refreshStatus = getFileUploadJdbc().spUploadTransaction(systemFileUploadType);
		List<File> fileInfo=new ArrayList<File>();
		
		Date applicationDate=new Date();
		Boolean isHBConnectionException = Boolean.FALSE;
		IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
		try {
			System.out.println("::: In FileUploadHelper ::  line:-112  start  generalParamDao try block");
			IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
			IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
			
			for(int i=0;i<generalParamEntries.length;i++){
				if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
					applicationDate=new Date(generalParamEntries[i].getParamValue());
					
					if (systemFileUploadType != null && IFileUploadConstants.FILEUPLOAD_FD.equalsIgnoreCase(systemFileUploadType)) {
						Date date = new Date();
						applicationDate = new Date(date.getTime());
					}
					System.out.println(" applicationDate:: "+applicationDate);

				}
			}
			System.out.println("::: In FileUploadHelper ::  line:-128  end  generalParamDao try block ");
		}catch(Exception e) {
			isHBConnectionException = Boolean.TRUE;
			System.out.println("::: In FileUploadHelper ::  line:-130  inside catch isHBConnectionException : "+isHBConnectionException);
			e.printStackTrace();
		}
		
		if(isHBConnectionException) {
			try {
				System.out.println("::: In FileUploadHelper ::  line:-137  inside try isHBConnectionException : "+isHBConnectionException);
				IGeneralParamEntry generalParamEntries;
				generalParamEntries = getFileUploadJdbc().getAppDate();
				applicationDate=new Date(generalParamEntries.getParamValue());
				System.out.println("::: In FileUploadHelper ::  line:-142  applicationDate : "+applicationDate);
				if (systemFileUploadType != null && IFileUploadConstants.FILEUPLOAD_FD.equalsIgnoreCase(systemFileUploadType)) {
					Date date = new Date();
					applicationDate = new Date(date.getTime());
				}
				
			} catch (Exception e) {
				System.out.println("::: In FileUploadHelper ::  line:-148  inside catch() : ");
				e.printStackTrace();
			}
		}
		
/*		IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
		IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
		IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
		Date applicationDate=new Date();
		for(int i=0;i<generalParamEntries.length;i++){
			if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
				applicationDate=new Date(generalParamEntries[i].getParamValue());
				
				if (systemFileUploadType != null && IFileUploadConstants.FILEUPLOAD_FD.equalsIgnoreCase(systemFileUploadType)) {
					Date date = new Date();
					applicationDate = new Date(date.getTime());
				}
				System.out.println(" applicationDate:: "+applicationDate);

			}
		}*/
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String checkDate=df.format(applicationDate);
		FileUploadFtpService fileUploadFtpService = new FileUploadFtpService();
		//below code create master transaction
		try{
			if(systemFileUploadType!=null && systemFileUploadType.equalsIgnoreCase(IFileUploadConstants.FILEUPLOAD_UBS)){
				
				if(null!= refreshStatus && refreshStatus.equalsIgnoreCase("1")){
					dataFromCacheView = getFileUploadJdbc().cacheDataFromMaterializedView("UBS_UPLOAD_STATUS_MV");
					dataFromUpdLineFacilityMV = getFileUploadJdbc().cacheDataFromUpdLineFacilityMV("UPD_LINE_FAC_MV","UBS-LIMITS");
					//System.out.println("::: In FileUploadHelper [dataFromUpdLineFacilityMV] value is: "+dataFromUpdLineFacilityMV);
					//DefaultLogger.info("::: In FileUploadHelper [dataFromUpdLineFacilityMV] value is: ",dataFromUpdLineFacilityMV);
				}
				//Set<String> alreadyDownloadedFiles = getFileUploadJdbc().getAlreadyDownloadedFileNamesByBatch(IFileUploadConstants.FILEUPLOAD_UBS_TRANS_SUBTYPE, applicationDate);
				
				ResourceBundle bundle = ResourceBundle.getBundle("ofa");
				fileUploadFtpService.downlodUploadedFiles(IFileUploadConstants.FILEUPLOAD_UBS);

				File path=new File(bundle.getString(IFileUploadConstants.FTP_FILEUPLOAD_DOWNLOAD_UBS_LOCAL_DIR));
				File list[]=path.listFiles();
				
				DefaultLogger.info("In FileUploadHelper :: File list size is ::",list.length);
				DefaultLogger.info("In FileUploadHelper :: File list is ::",list.toString());

				for(int i=list.length-1;i>=0;i--){ 
					File sortFile=list[i];
					DefaultLogger.info("In FileUploadHelper FILEUPLOAD_UBS :: sortFile.getName() is ::",sortFile.getName());
					DefaultLogger.info("In FileUploadHelper FILEUPLOAD_UBS :: prefix is ::",IFileUploadConstants.FTP_FINWARE_FILE_NAME);
					DefaultLogger.info("In FileUploadHelper FILEUPLOAD_UBS :: checkDate is ::",checkDate);
					//DefaultLogger.info("In FileUploadHelper FILEUPLOAD_UBS :: alreadyDownloadedFiles is ::",alreadyDownloadedFiles);
					if(sortFile.getName().contains(IFileUploadConstants.FCCNCB_FILE_NAME+checkDate) 
							&& (sortFile.getName().endsWith(".csv") ||  sortFile.getName().endsWith(".CSV")) 
								/*&& alreadyDownloadedFiles != null && !alreadyDownloadedFiles.contains(sortFile.getName())*/
							){
						fileInfo.add(list[i]);
						DefaultLogger.info("In FileUploadHelper FILEUPLOAD_UBS :: Successfully Added in fileInfo ::",sortFile.getName());
					}
				}
			}
			else if(systemFileUploadType!=null && systemFileUploadType.equalsIgnoreCase(IFileUploadConstants.FILEUPLOAD_FINWARE)){
				
				if(null!= refreshStatus && refreshStatus.equalsIgnoreCase("1")){
					dataFromCacheView = getFileUploadJdbc().cacheDataFromMaterializedView("FINWARE_UPLOAD_STATUS_MV");
					dataFromUpdLineFacilityMV = getFileUploadJdbc().cacheDataFromUpdLineFacilityMV("UPD_LINE_FAC_MV","FINWARE");
					//DefaultLogger.info("::: In FileUploadHelper [dataFromUpdLineFacilityMV] valuse is: ", dataFromUpdLineFacilityMV);
					//System.out.println("::: In FileUploadHelper [dataFromUpdLineFacilityMV] value is: "+dataFromUpdLineFacilityMV);
				}
				Set<String> alreadyDownloadedFiles = getFileUploadJdbc().getAlreadyDownloadedFileNamesByBatch(IFileUploadConstants.FILEUPLOAD_FINWARE_TRANS_SUBTYPE, applicationDate);

				ResourceBundle bundle = ResourceBundle.getBundle("ofa");
				fileUploadFtpService.downlodUploadedFiles(IFileUploadConstants.FILEUPLOAD_FINWARE);

				File path=new File(bundle.getString(IFileUploadConstants.FTP_FILEUPLOAD_DOWNLOAD_FINWARE_LOCAL_DIR));
				File list[]=path.listFiles();

				DefaultLogger.info("In FileUploadHelper FILEUPLOAD_FINWARE :: File list size is ::",list.length);
				DefaultLogger.info("In FileUploadHelper FILEUPLOAD_FINWARE :: File list is ::",list.toString());
				
				for(int i=list.length-1;i>=0;i--){ 
					File sortFile=list[i];
					DefaultLogger.info("In FileUploadHelper FILEUPLOAD_FINWARE :: sortFile.getName() is ::",sortFile.getName());
					DefaultLogger.info("In FileUploadHelper FILEUPLOAD_FINWARE :: prefix is ::",IFileUploadConstants.FTP_FINWARE_FILE_NAME);
					DefaultLogger.info("In FileUploadHelper FILEUPLOAD_FINWARE :: checkDate is ::",checkDate);
					DefaultLogger.info("In FileUploadHelper FILEUPLOAD_FINWARE :: alreadyDownloadedFiles is ::",alreadyDownloadedFiles);
					if(sortFile.getName().contains(IFileUploadConstants.FTP_FINWARE_FILE_NAME+checkDate) 
							&& (sortFile.getName().endsWith(".csv") ||  sortFile.getName().endsWith(".CSV")) 
								&& alreadyDownloadedFiles != null && !alreadyDownloadedFiles.contains(sortFile.getName()))
					{
						fileInfo.add(list[i]);
						DefaultLogger.info("In FileUploadHelper FILEUPLOAD_FINWARE :: Successfully Added in fileInfo ::",sortFile.getName());
					}
				}
			}
			else if(systemFileUploadType!=null && systemFileUploadType.equalsIgnoreCase(IFileUploadConstants.FILEUPLOAD_HONGKONG)){
				
				if(null!= refreshStatus && refreshStatus.equalsIgnoreCase("1")){
					dataFromCacheView = getFileUploadJdbc().cacheDataFromMaterializedView("HONGKONG_UPLOAD_STATUS_MV");
				    dataFromUpdLineFacilityMV = getFileUploadJdbc().cacheDataFromUpdLineFacilityMV("UPD_LINE_FAC_MV","HONGKONG");
				    //System.out.println("::: In FileUploadHelper [dataFromUpdLineFacilityMV] value is: "+dataFromUpdLineFacilityMV);
				    //DefaultLogger.info("::: In FileUploadHelper [dataFromUpdLineFacilityMV] value is: ",dataFromUpdLineFacilityMV);
				}
				Set<String> alreadyDownloadedFiles = getFileUploadJdbc().getAlreadyDownloadedFileNamesByBatch(IFileUploadConstants.FILEUPLOAD_HONGKONG_TRANS_SUBTYPE, applicationDate);

				ResourceBundle bundle = ResourceBundle.getBundle("ofa");
				fileUploadFtpService.downlodUploadedFiles(IFileUploadConstants.FILEUPLOAD_HONGKONG);

				File path=new File(bundle.getString(IFileUploadConstants.FTP_FILEUPLOAD_DOWNLOAD_HONGKONG_LOCAL_DIR));
				File list[]=path.listFiles();
				
				DefaultLogger.info("In FileUploadHelper FILEUPLOAD_HONGKONG :: File list size is ::",list.length);
				DefaultLogger.info("In FileUploadHelper FILEUPLOAD_HONGKONG :: File list is ::",list.toString());

				for(int i=list.length-1;i>=0;i--){ 
					File sortFile=list[i];
					DefaultLogger.info("In FileUploadHelper FILEUPLOAD_HONGKONG :: sortFile.getName() is ::",sortFile.getName());
					DefaultLogger.info("In FileUploadHelper FILEUPLOAD_HONGKONG :: prefix is ::",IFileUploadConstants.FTP_HONGKONG_FILE_NAME);
					DefaultLogger.info("In FileUploadHelper FILEUPLOAD_HONGKONG :: checkDate is ::",checkDate);
					DefaultLogger.info("In FileUploadHelper FILEUPLOAD_HONGKONG :: alreadyDownloadedFiles is ::",alreadyDownloadedFiles);
					if(sortFile.getName().contains(IFileUploadConstants.FTP_HONGKONG_FILE_NAME+checkDate) 
							&& (sortFile.getName().endsWith(".xls") ||  sortFile.getName().endsWith(".XLS") 
									|| sortFile.getName().endsWith(".xlsx") || sortFile.getName().endsWith(".XLSX"))
										&& alreadyDownloadedFiles != null && !alreadyDownloadedFiles.contains(sortFile.getName())){
						fileInfo.add(list[i]);
						DefaultLogger.info("In FileUploadHelper FILEUPLOAD_HONGKONG :: Successfully Added in fileInfo ::",sortFile.getName());
					}
				}
			
			}
			else if(systemFileUploadType!=null && systemFileUploadType.equalsIgnoreCase(IFileUploadConstants.FILEUPLOAD_BAHRAIN)){
				
				if(null!= refreshStatus && refreshStatus.equalsIgnoreCase("1")){
					dataFromCacheView = getFileUploadJdbc().cacheDataFromMaterializedView("BAHRAIN_UPLOAD_STATUS_MV");
					dataFromUpdLineFacilityMV = getFileUploadJdbc().cacheDataFromUpdLineFacilityMV("UPD_LINE_FAC_MV","BAHRAIN");
					//DefaultLogger.info("::: In FileUploadHelper [dataFromUpdLineFacilityMV] value is: ", dataFromUpdLineFacilityMV);
					//System.out.println("::: In FileUploadHelper [dataFromUpdLineFacilityMV] value is: "+dataFromUpdLineFacilityMV);
				}
				Set<String> alreadyDownloadedFiles = getFileUploadJdbc().getAlreadyDownloadedFileNamesByBatch(IFileUploadConstants.FILEUPLOAD_BAHRAIN_TRANS_SUBTYPE, applicationDate);
				
				ResourceBundle bundle = ResourceBundle.getBundle("ofa");
				fileUploadFtpService.downlodUploadedFiles(IFileUploadConstants.FILEUPLOAD_BAHRAIN);

				File path=new File(bundle.getString(IFileUploadConstants.FTP_FILEUPLOAD_DOWNLOAD_BAHRAIN_LOCAL_DIR));
				File list[]=path.listFiles();

				DefaultLogger.info("In FileUploadHelper FILEUPLOAD_BAHRAIN :: File list size is ::",list.length);
				DefaultLogger.info("In FileUploadHelper FILEUPLOAD_BAHRAIN :: File list is ::",list.toString());
				
				for(int i=list.length-1;i>=0;i--){ 
					File sortFile=list[i];
					DefaultLogger.info("In FileUploadHelper FILEUPLOAD_BAHRAIN :: sortFile.getName() is ::",sortFile.getName());
					DefaultLogger.info("In FileUploadHelper FILEUPLOAD_BAHRAIN :: prefix is ::",IFileUploadConstants.FTP_BAHRAIN_FILE_NAME);
					DefaultLogger.info("In FileUploadHelper FILEUPLOAD_BAHRAIN :: checkDate is ::",checkDate);
					DefaultLogger.info("In FileUploadHelper FILEUPLOAD_BAHRAIN :: alreadyDownloadedFiles is ::",alreadyDownloadedFiles);
					if(sortFile.getName().contains(IFileUploadConstants.FTP_BAHRAIN_FILE_NAME+checkDate) 
							&& (sortFile.getName().endsWith(".xls") ||  sortFile.getName().endsWith(".XLS")
							|| sortFile.getName().endsWith(".xlsx") || sortFile.getName().endsWith(".XLSX"))  
								&& alreadyDownloadedFiles != null && !alreadyDownloadedFiles.contains(sortFile.getName())){
						fileInfo.add(list[i]);
						DefaultLogger.info("In FileUploadHelper FILEUPLOAD_BAHRAIN :: Successfully Added in fileInfo ::",sortFile.getName());
					}
				}
			}
			
			else if (systemFileUploadType != null && IFileUploadConstants.FILEUPLOAD_FD.equalsIgnoreCase(systemFileUploadType)) {

				Set<String> alreadyDownloadedFiles = getFileUploadJdbc().getAlreadyDownloadedFileNamesByBatch(IFileUploadConstants.FILEUPLOAD_FD_TRANS_SUBTYPE, applicationDate);
				ResourceBundle bundle = ResourceBundle.getBundle("ofa");
				fileUploadFtpService.downlodUploadedFiles(IFileUploadConstants.FILEUPLOAD_FD);

				File path = new File(bundle.getString(IFileUploadConstants.FTP_FILEUPLOAD_DOWNLOAD_FD_LOCAL_DIR));
				if(null!=path){
				File list[] = path.listFiles();

				if(null != list && list.length>0) {
					for (int i = list.length - 1; i >= 0; i--) {
						File sortFile = list[i];
						if (sortFile.getName().contains(bundle.getString(IFileUploadConstants.FTP_FD_FILE_NAME ))
							&& (sortFile.getName().endsWith(".csv") || sortFile.getName().endsWith(".CSV"))
								&& alreadyDownloadedFiles != null && !alreadyDownloadedFiles.contains(sortFile.getName())){
							fileInfo.add(list[i]);
							}
						}
					}
				}
			
			}else if (systemFileUploadType != null && IFileUploadConstants.FILEDOWNLOAD_HRMS.equalsIgnoreCase(systemFileUploadType)) {

//				System.out.println("Inside if condition for downlodUploadedFiles of FILEDOWNLOAD_HRMS Completed.");
				Set<String> alreadyDownloadedFiles = 
						getFileUploadJdbc().getAlreadyDownloadedFileNamesByBatchForHRMS(IFileUploadConstants.FILEDOWNLOAD_HRMS_TRANS_SUBTYPE, applicationDate);
				ResourceBundle bundle = ResourceBundle.getBundle("ofa");
//				System.out.println("Going for downlodUploadedFiles of FILEDOWNLOAD_HRMS.");
				System.out.println("alreadyDownloadedFiles =>"+alreadyDownloadedFiles);
				fileUploadFtpService.downlodUploadedFiles(IFileUploadConstants.FILEDOWNLOAD_HRMS);

				System.out.println("After downlodUploadedFiles of FILEDOWNLOAD_HRMS Completed.");
					String localDataDir = bundle.getString("ftp.hrms.download.local.dir");
					
//					System.out.println("After downlodUploadedFiles localDataDir.="+localDataDir);
					
					File ackFiles = new File(localDataDir.trim());
					File[] files = ackFiles.listFiles();
					if (null != files) {
						for (File sortFile : files) {
							if (sortFile.getName().contains(bundle.getString("ftp.hrms.upload.filename"))
									&& (sortFile.getName().endsWith(".csv") || sortFile.getName().endsWith(".CSV"))
										&& alreadyDownloadedFiles != null && !alreadyDownloadedFiles.contains(sortFile.getName())){
								try {
									System.out.println("Gong for readingFile sortFile.getName()=>"+sortFile.getName());
									readingFile(localDataDir + sortFile.getName());
									
									System.out.println("Gong for insertFile sortFile.getName()=>"+sortFile.getName());
									insertFile(sortFile.getName(), IFileUploadConstants.FILEDOWNLOAD_HRMS);
									
									System.out.println("Gong for moveFile sortFile.getName()=>"+sortFile.getName());
									moveFile(bundle, sortFile, sortFile.getName());
									sortFile.delete();
							} catch (Exception e) {
								System.out.println("Exception in readingFile or insertFile or moveFile.");
								e.printStackTrace();
							}

							finally {

								/*try {
//									moveFile(bundle, file, file.getName());
//									file.delete();
									moveFile(bundle, sortFile, sortFile.getName());
									sortFile.delete();
								} catch (Exception e) {
									System.out.println("Exception caught while HRMS moveFile(bundle, sortFile, sortFile.getName()");
									e.printStackTrace();
								}*/
							}
									}
//						
					}
				}
			}
		}
		catch(FileUploadException ex){
			DefaultLogger.error("Exception caught in FileUploadHelper : getFilesFromRemoteLocation : ", ex.getMessage());
			System.out.println("Exception caught in FileUploadHelper : getFilesFromRemoteLocation : "+ex.getMessage());
			ex.printStackTrace();
		} 
		catch (Exception e) { 
			DefaultLogger.error(" Exception caught in FileUploadHelper : getFilesFromRemoteLocation :" , e.getMessage());
			System.out.println("Exception caught in FileUploadHelper : getFilesFromRemoteLocation : "+e.getMessage());
			e.printStackTrace();
		}
		
		resultMap.put("systemFileUploadType", systemFileUploadType);
		resultMap.put("fileInfo", fileInfo);
		resultMap.put("dataFromCacheView",dataFromCacheView);
		resultMap.put("dataFromUpdLineFacilityMV",dataFromUpdLineFacilityMV);
		
		if(null != fileInfo) {
			DefaultLogger.debug(":::In FileUploadHelper :: downloadFilesFromRemoteLocation :: fileInfo size is : ",fileInfo.size());
			System.out.println(":::In FileUploadHelper :: downloadFilesFromRemoteLocation ::  fileInfo size is : "+fileInfo.size());
		}
		
		
		System.out.println("::: In FileUploadHelper :: End downloadFilesFromRemoteLocation for systemFileUploadType: "+systemFileUploadType);
	    DefaultLogger.info("::: In FileUploadHelper :: End downloadFilesFromRemoteLocation for systemFileUploadType: ",systemFileUploadType);
		
		return resultMap;
	}
	
	private static void insertFile(String file, String filedownloadHrms) {
		try {
//			JndiTemplate jndiTemplate = new JndiTemplate();
//
//			String dataSourceJndiName = PropertyManager.getValue("dbconfig.weblogic.datasource.jndiname");
//			DataSource ofaDataSource = (DataSource) jndiTemplate
//					.lookup(dataSourceJndiName, javax.sql.DataSource.class);
//			JdbcTemplate jdbc = new JdbcTemplate(ofaDataSource);
			
//			DBUtil dbUtil = new DBUtil();
//			DBUtil dbUtil1 = new DBUtil();
			
//			long nextSequenceSql = getSequence("CMS_FILEUPLOAD_SEQ");
//			long nextSequenceStageSql = getSequence("CMS_STG_FILEUPLOAD_SEQ");
//			String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
			
			IFileUpload mainFile = new OBFileUpload();
			IFileUpload stageFile = new OBFileUpload();
			
			mainFile.setFileType(filedownloadHrms);
			mainFile.setFileName(file);
			mainFile.setTotalRecords("0");
			mainFile.setApproveRecords("0");
			mainFile.setUploadBy("A");
			mainFile.setApproveBy("A");
			mainFile.setUploadTime(new Date());
			
//			String query = "insert into CMS_FILE_UPLOAD (ID, FILETYPE,FILENAME,TOTALRECORDS,APPROVERECORDS,UPLOADBY,APPROVEBY,UPLOADTIME)"
//					+ " values("+nextSequenceSql+",'"+filedownloadHrms+"','"+file+"','0','0','a','a','"+date+"')";
//			String stageQuery = "insert into STAGE_FILE_UPLOAD (ID, FILETYPE,FILENAME,TOTALRECORDS,APPROVERECORDS,UPLOADBY,APPROVEBY,UPLOADTIME)"
//					+ " values("+nextSequenceStageSql+",'"+filedownloadHrms+"','"+file+"','0','0','a','a','"+date+"')";			
//			jdbc.execute(query);
//			jdbc.execute(stageQuery);

			IRelationshipMgrDAO relationshipMgrDAOImpl = (IRelationshipMgrDAO)BeanHouse.get("relationshipMgrDAO");
			relationshipMgrDAOImpl.insertData(mainFile);

//			dbUtil.setSQL(query);
//			dbUtil.executeQuery();
//			dbUtil.close();
//
//			dbUtil1.setSQL(stageQuery);
//			dbUtil1.executeQuery();
//			dbUtil1.close();
			
			
			
		}catch(Exception e) {
			System.out.println("Exception in insertFile.");
			e.printStackTrace();
		}
		
	}

	public static Map makerSubmitFileUpload(Map resultMap) {
		 
		ConcurrentMap<String, String> dataFromCacheView = (ConcurrentMap<String, String>) resultMap.get("dataFromCacheView");
		ConcurrentMap<String, String> dataFromUpdLineFacilityMV = (ConcurrentMap<String, String>) resultMap.get("dataFromUpdLineFacilityMV");
		String systemFileUploadType = (String) resultMap.get("systemFileUploadType");
		List<File> fileInfo = (List<File>) resultMap.get("fileInfo");
		
		System.out.println("::: In FileUploadHelper :: Start makerSubmitFileUpload for systemFileUploadType: "+systemFileUploadType);
	    DefaultLogger.info("::: In FileUploadHelper :: Start makerSubmitFileUpload for systemFileUploadType: ",systemFileUploadType);
		
		if(null != dataFromCacheView) {
			DefaultLogger.debug(":::In FileUploadHelper :: makerSubmitFileUpload :: size of dataFromCacheView map is : ",dataFromCacheView.size());
			System.out.println(":::In FileUploadHelper :: makerSubmitFileUpload :: size of dataFromCacheView map is : "+dataFromCacheView.size());
		}
		if(null != dataFromUpdLineFacilityMV) {
			DefaultLogger.debug(":::In FileUploadHelper :: makerSubmitFileUpload :: size of dataFromUpdLineFacilityMV map is : ",dataFromUpdLineFacilityMV.size());
			System.out.println(":::In FileUploadHelper :: makerSubmitFileUpload :: size of dataFromUpdLineFacilityMV map is : "+dataFromUpdLineFacilityMV.size());
		}
		if(null != fileInfo) {
			DefaultLogger.debug(":::In FileUploadHelper :: makerSubmitFileUpload :: fileInfo size is : ",fileInfo.size());
			System.out.println(":::In FileUploadHelper :: makerSubmitFileUpload ::  fileInfo size is : "+fileInfo.size()+ " : FOR :: "+systemFileUploadType);
		}
		
		if(null != fileInfo && fileInfo.size()>0) {
			ConcurrentMap<File, ITrxContext> trxContextMap = new ConcurrentHashMap<File,ITrxContext>();
			ConcurrentMap<File, IFileUploadTrxValue> fileUploadtrxValueMap = new ConcurrentHashMap<File,IFileUploadTrxValue>();
			
			int countPass =0;
			int countFail =0;
			List finalList = new ArrayList();
			ArrayList totalUploadedList=new ArrayList();
			ArrayList consolidateList=new ArrayList();
			
			HashMap mp = new HashMap();
			ArrayList resultList=new ArrayList();
			IUbsErrorLog objUbsErrorLog = new OBUbsErrorLog();
			String fileType = "CSV";
			IFileUploadTrxValue trxValueOut = new OBFileUploadTrxValue();
			if(IFileUploadConstants.FILEUPLOAD_UBS.equals(systemFileUploadType)) {
				trxValueOut.setTransactionSubType(IFileUploadConstants.FILEUPLOAD_UBS_TRANS_SUBTYPE);
			}
			else if(IFileUploadConstants.FILEUPLOAD_FINWARE.equals(systemFileUploadType)) {
				trxValueOut.setTransactionSubType(IFileUploadConstants.FILEUPLOAD_FINWARE_TRANS_SUBTYPE);
			}
			else if(IFileUploadConstants.FILEUPLOAD_HONGKONG.equals(systemFileUploadType)) {
				trxValueOut.setTransactionSubType(IFileUploadConstants.FILEUPLOAD_HONGKONG_TRANS_SUBTYPE);
			}
			else if(IFileUploadConstants.FILEUPLOAD_BAHRAIN.equals(systemFileUploadType)) {
				trxValueOut.setTransactionSubType(IFileUploadConstants.FILEUPLOAD_BAHRAIN_TRANS_SUBTYPE);
			}
			else if(IFileUploadConstants.FILEUPLOAD_FD.equals(systemFileUploadType)) {
				trxValueOut.setTransactionSubType(IFileUploadConstants.FILEUPLOAD_FD_TRANS_SUBTYPE);
			}
			else if(IFileUploadConstants.FILEDOWNLOAD_HRMS.equals(systemFileUploadType)) {
				trxValueOut.setTransactionSubType(IFileUploadConstants.FILEDOWNLOAD_HRMS_TRANS_SUBTYPE);
			}
			
			OBTrxContext ctx = new OBTrxContext();
			//below code create master transaction		
			if(null != fileInfo && fileInfo.size()>0 && !fileInfo.isEmpty()) {
				for(File file : fileInfo) {
					try{
						String fileName=file.getName();
						DefaultLogger.debug("#####################In FileUploadHelper filename:: " ,fileName);
						IFileUploadDao dao=(IFileUploadDao)BeanHouse.get("fileUploadDao");	
						Date applicationDate=new Date();
						Boolean isHBConnectionException = Boolean.FALSE;
						IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
						try {
							System.out.println("::: In FileUploadHelper ::  line:-541 Maker start  generalParamDao try block");
							IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
							IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
							
							for(int i=0;i<generalParamEntries.length;i++){
								if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
									applicationDate=new Date(generalParamEntries[i].getParamValue());
									
									if (systemFileUploadType != null && IFileUploadConstants.FILEUPLOAD_FD.equalsIgnoreCase(systemFileUploadType)) {
										Date date = new Date();
										applicationDate = new Date(date.getTime());
									}
									System.out.println(" applicationDate:: "+applicationDate);

								}
							}
							System.out.println("::: In FileUploadHelper ::  line:-557  end  generalParamDao try block ");
						}catch(Exception e) {
							isHBConnectionException = Boolean.TRUE;
							System.out.println("::: In FileUploadHelper ::  line:-560  inside catch isHBConnectionException : "+isHBConnectionException);
							e.printStackTrace();
						}
						
						if(isHBConnectionException) {
							try {
								System.out.println("::: In FileUploadHelper ::  line:-566  inside try isHBConnectionException : "+isHBConnectionException);
								IGeneralParamEntry generalParamEntries;
								generalParamEntries = getFileUploadJdbc().getAppDate();
								applicationDate=new Date(generalParamEntries.getParamValue());
								System.out.println("::: In FileUploadHelper ::  line:-570  applicationDate : "+applicationDate);
								if (systemFileUploadType != null && IFileUploadConstants.FILEUPLOAD_FD.equalsIgnoreCase(systemFileUploadType)) {
									Date date = new Date();
									applicationDate = new Date(date.getTime());
								}
								
							} catch (Exception e) {
								System.out.println("::: In FileUploadHelper ::  line:-577  inside catch() : ");
								e.printStackTrace();
							}
						}	
			
						
/*						IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");					
						IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
						IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
						Date applicationDate=new Date();
						for(int i=0;i<generalParamEntries.length;i++){
							if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
								applicationDate=new Date(generalParamEntries[i].getParamValue());
								if (systemFileUploadType != null && IFileUploadConstants.FILEUPLOAD_FD.equalsIgnoreCase(systemFileUploadType)) {
									Date date = new Date();
									applicationDate = new Date(date.getTime());
								}
								System.out.println("applicationDate inside FD::  "+applicationDate);

							}
						}*/
						
						Date d = DateUtil.getDate();
						applicationDate.setHours(d.getHours());
						applicationDate.setMinutes(d.getMinutes());
						applicationDate.setSeconds(d.getSeconds());
						
						if(fileName.endsWith(".CSV") || fileName.endsWith(".csv")){
							if(IFileUploadConstants.FILEUPLOAD_UBS.equals(systemFileUploadType)) {
								
								ArrayList<OBUbsFile> errorList=new ArrayList();
								ProcessDataFile dataFile = new ProcessDataFile();
								resultList = dataFile.processFileUpload(file, UploadUbsFileCmd.UBS_UPLOAD);
								HashMap eachDataMap = (HashMap)dataFile.getErrorList();
								
								List list = new ArrayList(eachDataMap.values());
								for(int i =0;i<list.size();i++){
									String[][] errList = (String[][])list.get(i);
									String str = "";
									for(int p=0;p<6;p++){
										for(int j=0;j<4;j++){
											str = str+((errList[p][j]==null)?"":errList[p][j]+";");
										}
									str=str+"||";
									}
									finalList.add(str);
								}
								if(resultList.size()>0){
									DefaultLogger.debug("In FileUploadHelper :: ",resultList.size());
									mp = (HashMap)getFileUploadJdbc().insertUbsfile(resultList, null,fileName,null,applicationDate,dataFromCacheView,dataFromUpdLineFacilityMV);
									totalUploadedList = (ArrayList) mp.get("totalUploadedList");
									
									errorList = (ArrayList) mp.get("errorList");
									if(totalUploadedList.size()>0){
										IFileUpload fileObj=new OBFileUpload();
										fileObj.setFileType(IFileUploadConstants.FILEUPLOAD_UBS_TRANS_SUBTYPE);
										fileObj.setUploadBy("SYSTEM");
										fileObj.setUploadTime(applicationDate);
										fileObj.setFileName(fileName);
										fileObj.setTotalRecords(String.valueOf(resultList.size()));     
										for(int i=0;i<totalUploadedList.size();i++){
											OBUbsFile ubsRecord=(OBUbsFile)totalUploadedList.get(i);
											if(ubsRecord.getStatus().equals("PASS")){
												countPass++;
											}else if(ubsRecord.getStatus().equals("FAIL")){
												countFail++;
											}
										}
										fileObj.setApproveRecords(String.valueOf(countPass));
										DefaultLogger.debug("FileUploadHelper","##########4###########before makerCreateFile #######:: ");
										//STAGE_FILE_UPLOAD
										trxValueOut = getFileUploadProxy().makerCreateFile(ctx,fileObj);
										DefaultLogger.debug("FileUploadHelper","##########5###########after makerCreateFile ######:: ");
										if(trxValueOut!=null){
											for(int i=0;i<totalUploadedList.size();i++){
												OBUbsFile ubsRecord=(OBUbsFile)totalUploadedList.get(i);
												ubsRecord.setFileId(Long.parseLong(trxValueOut.getStagingReferenceID()));
												consolidateList.add(ubsRecord);
											}
											for(int i=0;i<errorList.size();i++){
												OBUbsFile ubsRecord=(OBUbsFile)errorList.get(i);
												ubsRecord.setFileId(Long.parseLong(trxValueOut.getStagingReferenceID()));
												consolidateList.add(ubsRecord);
											}
											DefaultLogger.debug("##########6###########consolidateList ############:: ",consolidateList.size());
											int batchSize = 200;
											for (int j = 0; j < consolidateList.size(); j += batchSize) {
												List<OBUbsFile> batchList = consolidateList.subList(j, j + batchSize > consolidateList.size() ? consolidateList.size() : j + batchSize);
												getFileUploadJdbc().createEntireUbsStageFile(batchList);
											}
											DefaultLogger.debug("##########6###########done errorList ############:: ","");
										}
									}
								}
							}
							else if(IFileUploadConstants.FILEUPLOAD_FINWARE.equals(systemFileUploadType)) {
								  
									ArrayList<OBFinwareFile> errorList=new ArrayList();
									ProcessDataFile dataFile = new ProcessDataFile();
									resultList = dataFile.processFileUpload(file, ICMSConstant.FINWARE_UPLOAD);
									HashMap eachDataMap = (HashMap)dataFile.getErrorList();
									
									List list = new ArrayList(eachDataMap.values());
									for(int i =0;i<list.size();i++){
										String[][] errList = (String[][])list.get(i);
										String str = "";
										for(int p=0;p<5;p++){
											for(int j=0;j<4;j++){
												str = str+((errList[p][j]==null)?"":errList[p][j]+";");
											}
										str=str+"||";
										}
										finalList.add(str);
									}
									if(resultList.size()>0){
										mp = (HashMap)getFileUploadJdbc().insertFinwareFile(resultList, null,fileName,null,applicationDate,dataFromCacheView,dataFromUpdLineFacilityMV);
										totalUploadedList = (ArrayList) mp.get("totalUploadedList");
										
										errorList = (ArrayList) mp.get("errorList");
										if(totalUploadedList.size()>0){
											IFileUpload fileObj=new OBFileUpload();
											fileObj.setFileType(IFileUploadConstants.FILEUPLOAD_FINWARE_TRANS_SUBTYPE);
											fileObj.setUploadBy("SYSTEM");
											fileObj.setUploadTime(applicationDate);
											fileObj.setFileName(fileName);
											fileObj.setTotalRecords(String.valueOf(resultList.size())); 
											for(int i=0;i<totalUploadedList.size();i++){
												OBFinwareFile finwareRecord=(OBFinwareFile)totalUploadedList.get(i);
												if(finwareRecord.getStatus().equals("PASS")){
													countPass++;
												}else if(finwareRecord.getStatus().equals("FAIL")){
													countFail++;
												}
											}
											fileObj.setApproveRecords(String.valueOf(countPass));
											trxValueOut = getFileUploadProxy().makerCreateFile(ctx,fileObj);
											if(trxValueOut!=null){
												for(int i=0;i<totalUploadedList.size();i++){
													OBFinwareFile finwareRecord=(OBFinwareFile)totalUploadedList.get(i);
													finwareRecord.setFileId(Long.parseLong(trxValueOut.getStagingReferenceID()));
													consolidateList.add(finwareRecord);
													}
												for(int i=0;i<errorList.size();i++){
													OBFinwareFile finwareRecord=(OBFinwareFile)errorList.get(i);
													finwareRecord.setFileId(Long.parseLong(trxValueOut.getStagingReferenceID()));
													consolidateList.add(finwareRecord);
												}
												int batchSize = 200;
												for (int j = 0; j < consolidateList.size(); j += batchSize) {
													List<OBFinwareFile> batchList = consolidateList.subList(j, j + batchSize > consolidateList.size() ? consolidateList.size() : j + batchSize);
													getFileUploadJdbc().createEntireFinwareStageFile(batchList);
												}
											}
										}
									}
							}
							else if(IFileUploadConstants.FILEUPLOAD_FD.equals(systemFileUploadType)) {

								ProcessDataFile dataFile = new ProcessDataFile();
								resultList = dataFile.processFileUpload(file, UploadFdFileCmd.FD_UPLOAD);
								HashMap eachDataMap = (HashMap) dataFile.getErrorList();

								DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
								String appDate=df.format(applicationDate);
								
								List list = new ArrayList(eachDataMap.values());
								for (int i = 0; i < list.size(); i++) {
									String[][] errList = (String[][]) list.get(i);
									String str = "";
									for (int p = 0; p < 6; p++) {
										for (int j = 0; j < 4; j++) {
											str = str
													+ ((errList[p][j] == null) ? ""
															: errList[p][j] + ";");
										}
										str = str + "||";
									}
									finalList.add(str);
								}
								if (resultList.size() > 0) {
									DefaultLogger.debug("","#####################In FileUploadHelper ::FD ############:: "+ resultList.size());
									
									//create stage entry for file id
										IFileUpload fileObj = new OBFileUpload();
										fileObj.setFileType(IFileUploadConstants.FILEUPLOAD_FD_TRANS_SUBTYPE);
										fileObj.setUploadBy("SYSTEM");
										fileObj.setUploadTime(applicationDate);
										fileObj.setFileName(fileName);
										fileObj.setTotalRecords(String.valueOf(resultList.size()));
										trxValueOut = getFileUploadProxy().makerCreateFile(ctx,fileObj);
										if (trxValueOut != null) {
											SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
											long fileId=Long.parseLong(trxValueOut.getStagingReferenceID());
											for (int index = 0; index < resultList.size(); index++) {
												HashMap eachDataMap1 = (HashMap) resultList.get(index);
												OBFdFile obj=new OBFdFile();
												obj.setDepositNumber(((String) eachDataMap1.get("DEPOSIT_NUMBER")).trim());
												if(null!=eachDataMap1.get("DATE_OF_DEPOSIT")&& !eachDataMap1.get("DATE_OF_DEPOSIT").toString().isEmpty()){
													obj.setDateOfDeposit(new java.sql.Date((simpleDateFormat.parse(eachDataMap1.get("DATE_OF_DEPOSIT").toString()).getTime())));
												}
												if(null!=eachDataMap1.get("DATE_OF_MATURITY")&& !eachDataMap1.get("DATE_OF_MATURITY").toString().isEmpty()){
													obj.setDateOfMaturity(new java.sql.Date((simpleDateFormat.parse(eachDataMap1.get("DATE_OF_MATURITY").toString()).getTime())));
												}
												if(null!=eachDataMap1.get("INTEREST_RATE")&& !eachDataMap1.get("INTEREST_RATE").toString().isEmpty()){
													obj.setInterestRate(new Double(Double.parseDouble(eachDataMap1.get("INTEREST_RATE").toString())));	
												}
												obj.setUploadStatus("Y");
												obj.setReason("FD Details Present in File but not in CLIMS");
												obj.setStatus("FAIL");
												obj.setFileId(fileId);
												totalUploadedList.add(obj);
										}
										DefaultLogger.debug("","##################### totalUploadedList ############:: "+ totalUploadedList.size());
										int batchSize = 200;
										for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
											List<OBFdFile> batchList = totalUploadedList
													.subList(j,j + batchSize > totalUploadedList.size() ? totalUploadedList.size() : j + batchSize);
											getFileUploadJdbc().createEntireFdStageFile(batchList);
										}
										DefaultLogger.debug("","########## File Data is dumped into Stage Table for FD Upload##################:: ");
										
										getFileUploadJdbc().updateCashDepositToNull("CMS_STAGE_CASH_DEPOSIT");
										getFileUploadJdbc().updateStageFdUploadStatus();
										getFileUploadJdbc().updateStageFdDetailsWithStatusActive(fileId, appDate);
										getFileUploadJdbc().updateStageFdDetails(fileId);
										getFileUploadJdbc().updateTempFdDetails(fileId);
										}
									}
							}
						}
						else if(fileName.endsWith(".xls")||fileName.endsWith(".XLS") || fileName.endsWith(".xlsx") || fileName.endsWith(".XLSX")){  
							if(IFileUploadConstants.FILEUPLOAD_HONGKONG.equals(systemFileUploadType)) {
								  
								ArrayList<OBHongKongFile> errorList=new ArrayList();
								ProcessDataFile dataFile = new ProcessDataFile();
								resultList = dataFile.processFileUpload(file, ICMSConstant.HONGKONG_UPLOAD);
								HashMap eachDataMap = (HashMap)dataFile.getErrorList();
								
								List list = new ArrayList(eachDataMap.values());
								if(list!=null && list.size()>0){
									for(int i =0;i<list.size();i++){
										String[][] errList = (String[][])list.get(i);
										String str = "";
										for(int p=0;p<6;p++){
											for(int j=0;j<4;j++){
												str = str+((errList[p][j]==null)?"":errList[p][j]+";");
											}
										str=str+"||";
										}
										finalList.add(str);
									}
								}
								if(resultList.size()>0){
									mp = (HashMap)getFileUploadJdbc().insertHongKongfile(resultList, null,fileName,null,applicationDate,dataFromCacheView,dataFromUpdLineFacilityMV);
									totalUploadedList = (ArrayList) mp.get("totalUploadedList");
									errorList = (ArrayList) mp.get("errorList");
									
									if(totalUploadedList.size()>0){
										IFileUpload fileObj=new OBFileUpload();
										fileObj.setFileType(IFileUploadConstants.FILEUPLOAD_HONGKONG_TRANS_SUBTYPE);
										fileObj.setUploadBy("SYSTEM");
										fileObj.setUploadTime(applicationDate);
										fileObj.setFileName(fileName);
										fileObj.setTotalRecords(String.valueOf(resultList.size()));    
										for(int i=0;i<totalUploadedList.size();i++){
											OBHongKongFile hongkongRecord=(OBHongKongFile)totalUploadedList.get(i);
											DefaultLogger.debug("##### FileUploadHelper :: hongkongRecord status #######:: ",hongkongRecord.getStatus());
											if(hongkongRecord.getStatus().equals("PASS")){
												countPass++;
											}else if(hongkongRecord.getStatus().equals("FAIL")){
												countFail++;
											}
										}
										fileObj.setApproveRecords(String.valueOf(countPass));
										trxValueOut = getFileUploadProxy().makerCreateFile(ctx,fileObj);
										if(trxValueOut!=null){
											for(int i=0;i<totalUploadedList.size();i++){
												OBHongKongFile hongkongRecord=(OBHongKongFile)totalUploadedList.get(i);
												hongkongRecord.setFileId(Long.parseLong(trxValueOut.getStagingReferenceID()));
												consolidateList.add(hongkongRecord);
												}
											for(int i=0;i<errorList.size();i++){
												OBHongKongFile hongkongRecord=(OBHongKongFile)errorList.get(i);
												hongkongRecord.setFileId(Long.parseLong(trxValueOut.getStagingReferenceID()));
												consolidateList.add(hongkongRecord);
											}
											
											int batchSize = 200;
											for (int j = 0; j < consolidateList.size(); j += batchSize) {
												List<OBHongKongFile> batchList = consolidateList.subList(j, j + batchSize > consolidateList.size() ? consolidateList.size() : j + batchSize);
												getFileUploadJdbc().createEntireHongkongStageFile(batchList);
											}
										}
									}
								}
							
							}
							else if(IFileUploadConstants.FILEUPLOAD_BAHRAIN.equals(systemFileUploadType)) {
								 
								ArrayList<OBBahrainFile> errorList=new ArrayList();
								ProcessDataFile dataFile = new ProcessDataFile();
								resultList = dataFile.processFileUpload(file, ICMSConstant.BAHRAIN_UPLOAD);
								DefaultLogger.debug("#####################In FileUploadHelper ##### dataFile : ErrorList : ",dataFile.getErrorList());
								if(resultList!=null)
									DefaultLogger.debug("########### resultList size is :##########In FileUploadHelper ##### line no 144 : ",resultList.size());
								HashMap eachDataMap = (HashMap)dataFile.getErrorList();
								List list = new ArrayList(eachDataMap.values());
								if(list!=null) {
									DefaultLogger.debug("#####################In FileUploadHelper ##### list size : ",list.size());
								}
								if(list!=null && list.size()>0){
									for(int i =0;i<list.size();i++){
										DefaultLogger.debug("#########Inside for loop when list.size()>0 ############In FileUploadHelper ##### list size: ",list.size());
										String[][] errList = (String[][])list.get(i);
										String str = "";
										for(int p=0;p<6;p++){
											for(int j=0;j<4;j++){
												str = str+((errList[p][j]==null)?"":errList[p][j]+";");
											}
										str=str+"||";
										}
										finalList.add(str);
									}
								}
								
								if(resultList!= null && resultList.size()>0){
									DefaultLogger.debug("#########Inside if condition resultList.size() is: ##########In FileUploadHelper ##### : ",resultList.size());
									mp = (HashMap)getFileUploadJdbc().insertBahrainfile(resultList, null,fileName,null,applicationDate,dataFromCacheView,dataFromUpdLineFacilityMV);
									totalUploadedList = (ArrayList) mp.get("totalUploadedList");
									
									errorList = (ArrayList) mp.get("errorList");
									if(totalUploadedList!= null && totalUploadedList.size()>0){
										DefaultLogger.debug("#####################In FileUploadHelper ##### totalUploadedList size : ",totalUploadedList.size());
										IFileUpload fileObj=new OBFileUpload();
										fileObj.setFileType(IFileUploadConstants.FILEUPLOAD_BAHRAIN_TRANS_SUBTYPE);
										fileObj.setUploadBy("SYSTEM");
										fileObj.setUploadTime(applicationDate);
										fileObj.setFileName(fileName);
										fileObj.setTotalRecords(String.valueOf(resultList.size()));  
										for(int i=0;i<totalUploadedList.size();i++){
											OBBahrainFile bahrainRecord=(OBBahrainFile)totalUploadedList.get(i);
											if(bahrainRecord.getStatus().equals("PASS")){
												countPass++;
											}else if(bahrainRecord.getStatus().equals("FAIL")){
												countFail++;
											}
										}
										fileObj.setApproveRecords(String.valueOf(countPass));
										trxValueOut = getFileUploadProxy().makerCreateFile(ctx,fileObj);
										if(trxValueOut!=null){
											for(int i=0;i<totalUploadedList.size();i++){
												OBBahrainFile bahrainRecord=(OBBahrainFile)totalUploadedList.get(i);
												bahrainRecord.setFileId(Long.parseLong(trxValueOut.getStagingReferenceID()));
												consolidateList.add(bahrainRecord);
											}
											for(int i=0;i<errorList.size();i++){
												OBBahrainFile bahrainRecord=(OBBahrainFile)errorList.get(i);
												bahrainRecord.setFileId(Long.parseLong(trxValueOut.getStagingReferenceID()));
												consolidateList.add(bahrainRecord);
											}
											
											int batchSize = 200;
											for (int j = 0; j < consolidateList.size(); j += batchSize) {
												List<OBBahrainFile> batchList = consolidateList.subList(j, j + batchSize > consolidateList.size() ? consolidateList.size() : j + batchSize);
												getFileUploadJdbc().createEntireBahrainStageFile(batchList);
											}
										}
									}
									DefaultLogger.debug("#####################In FileUploadHelper ##### BahrainFile errorList size is : ",errorList.size());
								}
							}
						}else if(IFileUploadConstants.FILEDOWNLOAD_HRMS.equals(systemFileUploadType)) {
							 
							ArrayList<OBHRMSData> errorList=new ArrayList();
							ProcessDataFile dataFile = new ProcessDataFile();
							resultList = dataFile.processFileUpload(file, ICMSConstant.HRMS_FILE_UPLOAD);
							DefaultLogger.debug("#####################In FileUploadHelper ##### dataFile : ErrorList : ",dataFile.getErrorList());
							if(resultList!=null)
								DefaultLogger.debug("########### resultList size is :##########In FileUploadHelper ##### line no 144 : ",resultList.size());
							HashMap eachDataMap = (HashMap)dataFile.getErrorList();
							List list = new ArrayList(eachDataMap.values());
							if(list!=null) {
								DefaultLogger.debug("#####################In FileUploadHelper ##### list size : ",list.size());
							}
							if(list!=null && list.size()>0){
								for(int i =0;i<list.size();i++){
									DefaultLogger.debug("#########Inside for loop when list.size()>0 ############In FileUploadHelper ##### list size: ",list.size());
									String[][] errList = (String[][])list.get(i);
									String str = "";
									for(int p=0;p<13;p++){
										for(int j=0;j<4;j++){
											str = str+((errList[p][j]==null)?"":errList[p][j]+";");
										}
								str=str+"~";
									}
									finalList.add(str);
								}
							}
							
							if(resultList!= null && resultList.size()>0){
								DefaultLogger.debug("#########Inside if condition resultList.size() is: ##########In FileUploadHelper ##### : ",resultList.size());
								mp = (HashMap)getFileUploadJdbc().insertHRMSfile(resultList, fileName,applicationDate,dataFromCacheView,dataFromUpdLineFacilityMV);
								totalUploadedList = (ArrayList) mp.get("totalUploadedList");
								
								errorList = (ArrayList) mp.get("errorList");
								if(totalUploadedList!= null && totalUploadedList.size()>0){
									DefaultLogger.debug("#####################In FileUploadHelper ##### totalUploadedList size : ",totalUploadedList.size());
									IFileUpload fileObj=new OBFileUpload();
									fileObj.setFileType(IFileUploadConstants.FILEDOWNLOAD_HRMS_TRANS_SUBTYPE);
									fileObj.setUploadBy("SYSTEM");
									fileObj.setUploadTime(applicationDate);
									fileObj.setFileName(fileName);
									fileObj.setTotalRecords(String.valueOf(resultList.size()));  
									for(int i=0;i<totalUploadedList.size();i++){
										OBHRMSData hrmsRecord=(OBHRMSData)totalUploadedList.get(i);
										if(hrmsRecord.getStatus().equals("PASS")){
											countPass++;
										}else if(hrmsRecord.getStatus().equals("FAIL")){
											countFail++;
										}
									}
									fileObj.setApproveRecords(String.valueOf(countPass));
									trxValueOut = getFileUploadProxy().makerCreateFile(ctx,fileObj);
									if(trxValueOut!=null){
										for(int i=0;i<totalUploadedList.size();i++){
											OBHRMSData hrmsRecord=(OBHRMSData)totalUploadedList.get(i);
											hrmsRecord.setFileId(Long.parseLong(trxValueOut.getStagingReferenceID()));
											consolidateList.add(hrmsRecord);
										}
										for(int i=0;i<errorList.size();i++){
											OBHRMSData hrmsRecord=(OBHRMSData)errorList.get(i);
											hrmsRecord.setFileId(Long.parseLong(trxValueOut.getStagingReferenceID()));
											consolidateList.add(hrmsRecord);
										}
										
										int batchSize = 200;
										for (int j = 0; j < consolidateList.size(); j += batchSize) {
											List<OBBahrainFile> batchList = consolidateList.subList(j, j + batchSize > consolidateList.size() ? consolidateList.size() : j + batchSize);
											getFileUploadJdbc().createEntireBahrainStageFile(batchList);
										}
									}
								}
								DefaultLogger.debug("#####################In FileUploadHelper ##### BahrainFile errorList size is : ",errorList.size());
							}
						}
					
						else{
							fileType="NOT_CSV";
						}
						
						trxContextMap.put(file, ctx);
						fileUploadtrxValueMap.put(file, trxValueOut);
					  
					}
					catch(FileUploadException ex){
						DefaultLogger.debug("got exception in FileUploadHelper" , ex);
						ex.printStackTrace();
					} catch (TrxParameterException e) {
						DefaultLogger.debug("got exception in FileUploadHelper" , e);
					} catch (Exception e) { 
						DefaultLogger.debug("got exception in FileUploadHelper" , e);
						e.printStackTrace();
					}
				}
			}
			resultMap.put("trxContextMap", trxContextMap);
			resultMap.put("fileUploadtrxValueMap", fileUploadtrxValueMap);
		}
		
		System.out.println("::: In FileUploadHelper :: End makerSubmitFileUpload for systemFileUploadType: "+systemFileUploadType);
	    DefaultLogger.info("::: In FileUploadHelper :: End makerSubmitFileUpload for systemFileUploadType: ",systemFileUploadType);
		
		return resultMap;
	}

	public static void checkerApproveFileUpload(Map resultMap) {
		 
		String systemFileUploadType = (String) resultMap.get("systemFileUploadType");
		System.out.println("::: In FileUploadHelper :: Start checkerApproveFileUpload for systemFileUploadType: "+systemFileUploadType);
	    DefaultLogger.info("::: In FileUploadHelper :: Start checkerApproveFileUpload for systemFileUploadType: ",systemFileUploadType);
	    
		List<File> fileInfo = (List<File>) resultMap.get("fileInfo");
		
		if(null != fileInfo && fileInfo.size()>0) {
			ConcurrentMap<String, String> dataFromUpdLineFacilityActual = new ConcurrentHashMap<String,String>();
			ConcurrentMap<File, ITrxContext> trxContextMap = (ConcurrentMap<File, ITrxContext>) resultMap.get("trxContextMap");
			ConcurrentMap<File, IFileUploadTrxValue> fileUploadtrxValueMap  = (ConcurrentMap<File, IFileUploadTrxValue>) resultMap.get("fileUploadtrxValueMap");
			
			DefaultLogger.debug(":::In FileUploadHelper :: checkerApproveFileUpload() :: systemFileUploadType is : ",systemFileUploadType);
			System.out.println("::: In FileUploadHelper :: checkerApproveFileUpload() :: systemFileUploadType is : "+systemFileUploadType);
			
			if(IFileUploadConstants.FILEUPLOAD_UBS.equals(systemFileUploadType)) {
				dataFromUpdLineFacilityActual = getFileUploadJdbc().cacheDataFromUpdLineActual(IFileUploadConstants.FILEUPLOAD_UBS_LIMITS);
			}
			else if(IFileUploadConstants.FILEUPLOAD_FINWARE.equals(systemFileUploadType)) {
				dataFromUpdLineFacilityActual = getFileUploadJdbc().cacheDataFromUpdLineActual(IFileUploadConstants.FILEUPLOAD_FINWARE);
			}
			else if(IFileUploadConstants.FILEUPLOAD_HONGKONG.equals(systemFileUploadType)) {
				dataFromUpdLineFacilityActual = getFileUploadJdbc().cacheDataFromUpdLineActual(IFileUploadConstants.FILEUPLOAD_HONGKONG);
			}
			else if(IFileUploadConstants.FILEUPLOAD_BAHRAIN.equals(systemFileUploadType)) {
				dataFromUpdLineFacilityActual = getFileUploadJdbc().cacheDataFromUpdLineActual(IFileUploadConstants.FILEUPLOAD_BAHRAIN);
			}
			
			try {
				if(fileInfo != null && fileInfo.size()>0 && !fileInfo.isEmpty()) {
					for(File file : fileInfo) {
						OBTrxContext ctx = (OBTrxContext) trxContextMap.get(file);
						ArrayList totalUploadedList=new ArrayList();
						ArrayList errorList=new ArrayList();
						ArrayList consolidateList=new ArrayList();
						// File  Trx value
						IFileUploadTrxValue trxValueIn = fileUploadtrxValueMap.get(file);
						int countPass =0;
						int countFail =0;
						long countPassFd =0;
						long countFailFd =0;
						ArrayList uploadList=null;
						try {
							//Get File List of respective transaction
							IFileUpload fileUpload;
							SearchResult fileList = new SearchResult();
							if(null != trxValueIn && null != trxValueIn.getStagingReferenceID()) {
								if(IFileUploadConstants.FILEUPLOAD_UBS_TRANS_SUBTYPE.equals(trxValueIn.getTransactionSubType())){
									fileList=getFileUploadJdbc().getAllUbsFile(trxValueIn.getStagingReferenceID());
								}else if(IFileUploadConstants.FILEUPLOAD_HONGKONG_TRANS_SUBTYPE.equals(trxValueIn.getTransactionSubType())){
									fileList=getFileUploadJdbc().getAllHongKongFile(trxValueIn.getStagingReferenceID());
								}else if(IFileUploadConstants.FILEUPLOAD_FINWARE_TRANS_SUBTYPE.equals(trxValueIn.getTransactionSubType())){
									fileList=getFileUploadJdbc().getAllFinwareFile(trxValueIn.getStagingReferenceID());
								}else if(IFileUploadConstants.FILEUPLOAD_BAHRAIN_TRANS_SUBTYPE.equals(trxValueIn.getTransactionSubType())){
									fileList=getFileUploadJdbc().getAllBahrainFile(trxValueIn.getStagingReferenceID());
								}else if(IFileUploadConstants.FILEUPLOAD_FD_TRANS_SUBTYPE.equals(trxValueIn.getTransactionSubType())){
									fileList=getFileUploadJdbc().getAllFdFile(trxValueIn.getStagingReferenceID());
								}
							}
							if(fileList!=null && null != fileList.getResultList()){
								uploadList = new ArrayList(fileList.getResultList());		
							}
						}
						catch (Exception e) {
							DefaultLogger.error("Exception caught in FileUploadHelper : could not retrive File List for trx type:" , trxValueIn.getTransactionSubType());
							System.out.println("Exception caught in FileUploadHelper : could not retrive File List for trx type: "+trxValueIn.getTransactionSubType());
							e.printStackTrace();
						}
						OBFileUpload actualClone = new OBFileUpload();
						OBFileUpload stagingClone = new OBFileUpload();
						OBFileUpload actual = (OBFileUpload)trxValueIn.getFileUpload();
						OBFileUpload staging = (OBFileUpload)trxValueIn.getStagingfileUpload();
						IFileUploadTrxValue trxValueOut=null;
						
						Date applicationDate=new Date();
						Boolean isHBConnectionException = Boolean.FALSE;
						IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
						try {
							System.out.println("::: In FileUploadHelper ::  line:-1131  checker start  generalParamDao try block");
							IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
							IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
							
							for(int i=0;i<generalParamEntries.length;i++){
								if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
									applicationDate=new Date(generalParamEntries[i].getParamValue());
									
									if (systemFileUploadType != null && IFileUploadConstants.FILEUPLOAD_FD.equalsIgnoreCase(systemFileUploadType)) {
										Date date = new Date();
										applicationDate = new Date(date.getTime());
									}
									System.out.println(" applicationDate:: "+applicationDate);

								}
							}
							System.out.println("::: In FileUploadHelper ::  line:-1147  end  generalParamDao try block ");
						}catch(Exception e) {
							isHBConnectionException = Boolean.TRUE;
							System.out.println("::: In FileUploadHelper ::  line:-1150  inside catch isHBConnectionException : "+isHBConnectionException);
							e.printStackTrace();
						}
						
						if(isHBConnectionException) {
							try {
								System.out.println("::: In FileUploadHelper ::  line:-1156  inside try isHBConnectionException : "+isHBConnectionException);
								IGeneralParamEntry generalParamEntries;
								generalParamEntries = getFileUploadJdbc().getAppDate();
								applicationDate=new Date(generalParamEntries.getParamValue());
								System.out.println("::: In FileUploadHelper ::  line:-1160  applicationDate : "+applicationDate);
								if (systemFileUploadType != null && IFileUploadConstants.FILEUPLOAD_FD.equalsIgnoreCase(systemFileUploadType)) {
									Date date = new Date();
									applicationDate = new Date(date.getTime());
								}
								
							} catch (Exception e) {
								System.out.println("::: In FileUploadHelper ::  line:-1167  inside catch() : ");
								e.printStackTrace();
							}
						}
						
/*						IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
						IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
						IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
						Date applicationDate=new Date();
						for(int i=0;i<generalParamEntries.length;i++){
							if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
								applicationDate=new Date(generalParamEntries[i].getParamValue());
								
								if (systemFileUploadType != null && IFileUploadConstants.FILEUPLOAD_FD.equalsIgnoreCase(systemFileUploadType)) {
									Date date = new Date();
									applicationDate = new Date(date.getTime());
								}
								System.out.println("applicationDate:: "+applicationDate);
							}
						}*/
						
						DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
						String appDate=df.format(applicationDate);
						
						Date d = DateUtil.getDate();
						applicationDate.setHours(d.getHours());
						applicationDate.setMinutes(d.getMinutes());
						applicationDate.setSeconds(d.getSeconds());
						IFileUploadDao dao=(IFileUploadDao)BeanHouse.get("fileUploadDao");
						if(IFileUploadConstants.FILEUPLOAD_UBS_TRANS_SUBTYPE.equals(trxValueIn.getTransactionSubType())){
							if(uploadList!=null && uploadList.size()>0){
								HashMap mp = (HashMap)getFileUploadJdbc().insertUbsfileActual(uploadList,null ,
										trxValueIn.getStagingfileUpload().getFileName(),null,applicationDate,trxValueIn,dataFromUpdLineFacilityActual);
								totalUploadedList = (ArrayList) mp.get("totalUploadedList");
								
								if(totalUploadedList.size()>0){
									for(int i=0;i<totalUploadedList.size();i++){
										OBUbsFile obj=(OBUbsFile)totalUploadedList.get(i);
										if(obj.getStatus().equals("PASS")){
											countPass++;
										}else if(obj.getStatus().equals("FAIL")){
											countFail++;
										}
									}
								}
								IFileUpload stgFile= trxValueIn.getStagingfileUpload();
								stgFile.setApproveBy("SYSTEM");
								stgFile.setApproveRecords(String.valueOf(countPass));
								errorList = (ArrayList) mp.get("errorList");
								trxValueIn.setStagingfileUpload(stgFile);
								trxValueOut = getFileUploadProxy().checkerApproveFileUpload(ctx, trxValueIn);
								long fileId=Long.parseLong(trxValueOut.getReferenceID());
								if(totalUploadedList.size()>0){
									for(int i=0;i<totalUploadedList.size();i++){
										OBUbsFile obj=(OBUbsFile)totalUploadedList.get(i);
										obj.setFileId(fileId);
										consolidateList.add(obj);
									}
								}
								for(int i=0;i<errorList.size();i++){
									OBUbsFile obj=(OBUbsFile)errorList.get(i);
									obj.setFileId(fileId);
									consolidateList.add(obj);
								}
								
								int batchSize = 200;
								for (int j = 0; j < consolidateList.size(); j += batchSize) {
									List<OBUbsFile> batchList = consolidateList.subList(j, j + batchSize > consolidateList.size() ? consolidateList.size() : j + batchSize);
									getFileUploadJdbc().createEntireUbsActualFile(batchList);
								}
							}
						}else if(IFileUploadConstants.FILEUPLOAD_HONGKONG_TRANS_SUBTYPE.equals(trxValueIn.getTransactionSubType())){
							if(uploadList!=null && uploadList.size()>0){
								HashMap mp = (HashMap)getFileUploadJdbc().insertHongKongfileActual(uploadList, null,
										trxValueIn.getStagingfileUpload().getFileName(),null,applicationDate,trxValueIn,dataFromUpdLineFacilityActual);
								
								totalUploadedList = (ArrayList) mp.get("totalUploadedList");
								
								if(totalUploadedList.size()>0){
									for(int i=0;i<totalUploadedList.size();i++){
										OBHongKongFile obj=(OBHongKongFile)totalUploadedList.get(i);
										if(obj.getStatus().equals("PASS")){
											countPass++;
										}else if(obj.getStatus().equals("FAIL")){
											countFail++;
										}
									}
								}
								IFileUpload stgFile= trxValueIn.getStagingfileUpload();
								stgFile.setApproveBy("SYSTEM");
								stgFile.setApproveRecords(String.valueOf(countPass));
								trxValueIn.setStagingfileUpload(stgFile);
								errorList = (ArrayList) mp.get("errorList");
								trxValueOut = getFileUploadProxy().checkerApproveFileUpload(ctx, trxValueIn);
								long fileId=Long.parseLong(trxValueOut.getReferenceID());
								if(totalUploadedList.size()>0){
									for(int i=0;i<totalUploadedList.size();i++){
										OBHongKongFile obj=(OBHongKongFile)totalUploadedList.get(i);
										obj.setFileId(fileId);
										consolidateList.add(obj);
									}
								}
								for(int i=0;i<errorList.size();i++){
									OBHongKongFile obj=(OBHongKongFile)errorList.get(i);
									obj.setFileId(fileId);
									consolidateList.add(obj);					
								}
								
								int batchSize = 200;
								for (int j = 0; j < consolidateList.size(); j += batchSize) {
									List<OBHongKongFile> batchList = consolidateList.subList(j, j + batchSize > consolidateList.size() ? consolidateList.size() : j + batchSize);
									getFileUploadJdbc().createEntireHongkongActualFile(batchList);
								}
							}
							
						}else if(IFileUploadConstants.FILEUPLOAD_FINWARE_TRANS_SUBTYPE.equals(trxValueIn.getTransactionSubType())){
							if(uploadList!=null && uploadList.size()>0){
								HashMap mp = (HashMap)getFileUploadJdbc().insertFinwarefileActual(uploadList, null,
										trxValueIn.getStagingfileUpload().getFileName(),null,applicationDate,trxValueIn,dataFromUpdLineFacilityActual);
								totalUploadedList = (ArrayList) mp.get("totalUploadedList");
								
								if(totalUploadedList.size()>0){
									for(int i=0;i<totalUploadedList.size();i++){
										OBFinwareFile obj=(OBFinwareFile)totalUploadedList.get(i);
										if(obj.getStatus().equals("PASS")){
											countPass++;
										}else if(obj.getStatus().equals("FAIL")){
											countFail++;
										}
									}
								}
								IFileUpload stgFile= trxValueIn.getStagingfileUpload();
								stgFile.setApproveBy("SYSTEM");
								stgFile.setApproveRecords(String.valueOf(countPass));
								trxValueIn.setStagingfileUpload(stgFile);
								errorList = (ArrayList) mp.get("errorList");
								trxValueOut = getFileUploadProxy().checkerApproveFileUpload(ctx, trxValueIn);
								long fileId=Long.parseLong(trxValueOut.getReferenceID());
								if(totalUploadedList.size()>0){
									for(int i=0;i<totalUploadedList.size();i++){
										OBFinwareFile obj=(OBFinwareFile)totalUploadedList.get(i);
										obj.setFileId(fileId);
										consolidateList.add(obj);
									}
								}
								for(int i=0;i<errorList.size();i++){
									OBFinwareFile obj=(OBFinwareFile)errorList.get(i);
									obj.setFileId(fileId);
									consolidateList.add(obj);
								}
								int batchSize = 200;
								for (int j = 0; j < consolidateList.size(); j += batchSize) {
									List<OBFinwareFile> batchList = consolidateList.subList(j, j + batchSize > consolidateList.size() ? consolidateList.size() : j + batchSize);
									getFileUploadJdbc().createEntireFinwareActualFile(batchList);
								}
							}
						}
						else if(IFileUploadConstants.FILEUPLOAD_BAHRAIN_TRANS_SUBTYPE.equals(trxValueIn.getTransactionSubType())){
							if(uploadList!=null && uploadList.size()>0){
								HashMap mp = (HashMap)getFileUploadJdbc().insertBahrainfileActual(uploadList, null,
										trxValueIn.getStagingfileUpload().getFileName(),null,applicationDate,trxValueIn,dataFromUpdLineFacilityActual);
								totalUploadedList = (ArrayList) mp.get("totalUploadedList");
								
								if(totalUploadedList.size()>0){
									for(int i=0;i<totalUploadedList.size();i++){
										OBBahrainFile obj=(OBBahrainFile)totalUploadedList.get(i);
										if(obj.getStatus().equals("PASS")){
											countPass++;
										}else if(obj.getStatus().equals("FAIL")){
											countFail++;
										}
									}
								}
								IFileUpload stgFile= trxValueIn.getStagingfileUpload();
								stgFile.setApproveBy("SYSTEM");
								stgFile.setApproveRecords(String.valueOf(countPass));
								trxValueIn.setStagingfileUpload(stgFile);
								errorList = (ArrayList) mp.get("errorList");
								trxValueOut = getFileUploadProxy().checkerApproveFileUpload(ctx, trxValueIn);
								long fileId=Long.parseLong(trxValueOut.getReferenceID());
								if(totalUploadedList.size()>0){
									for(int i=0;i<totalUploadedList.size();i++){
										OBBahrainFile obj=(OBBahrainFile)totalUploadedList.get(i);
										obj.setFileId(fileId);
										consolidateList.add(obj);
									}
								}
								for(int i=0;i<errorList.size();i++){
									OBBahrainFile obj=(OBBahrainFile)errorList.get(i);
									obj.setFileId(fileId);
									consolidateList.add(obj);
								}
								int batchSize = 200;
								for (int j = 0; j < consolidateList.size(); j += batchSize) {
									List<OBBahrainFile> batchList = consolidateList.subList(j, j + batchSize > consolidateList.size() ? consolidateList.size() : j + batchSize);
									getFileUploadJdbc().createEntireBahrainActualFile(batchList);
								}
							}
						}
					
						else if (IFileUploadConstants.FILEUPLOAD_FD_TRANS_SUBTYPE.equals(trxValueIn.getTransactionSubType())) {
							ArrayList totalUploadedFileList=new ArrayList();
								IFileUpload stgFile = trxValueIn.getStagingfileUpload();
								stgFile.setApproveBy("SYSTEM");
								trxValueIn.setStagingfileUpload(stgFile);
								trxValueOut = getFileUploadProxy().checkerApproveFileUpload(ctx, trxValueIn);
								long fileId = Long.parseLong(trxValueOut.getReferenceID());
								 
								getFileUploadJdbc().updateActualFdUploadStatus();
								getFileUploadJdbc().insertFileDataToTempActualTable(fileId,trxValueIn.getStagingReferenceID());
								getFileUploadJdbc().updateActualFdDetailsWithStatusActive(fileId, appDate);
								getFileUploadJdbc().updateActualFdDetails(fileId);
								getFileUploadJdbc().updateTempActualFdDetails(fileId);
								getFileUploadJdbc().insertTempActualFdDetails(fileId);
								countPassFd =getFileUploadJdbc().getCount(fileId,"CMS_FD_FILE_UPLOAD", "PASS");
								countFailFd=getFileUploadJdbc().getCount(fileId,"CMS_FD_FILE_UPLOAD", "FAIL");
								totalUploadedList.clear();
								totalUploadedList.addAll(getFileUploadJdbc().getTotalUploadedList(fileId,"CMS_FD_FILE_UPLOAD"));
								totalUploadedFileList.addAll(getFileUploadJdbc().getTotalUploadedFileList(fileId,"CMS_FD_FILE_UPLOAD"));
							}
					}
				}
			}
			catch (Exception e) {
				DefaultLogger.error(" Exception caught in FileUploadHelper : checkerApproveFileUpload :" , e.getMessage());
				System.out.println("Exception caught in FileUploadHelper : checkerApproveFileUpload : "+e.getMessage());
				e.printStackTrace();
			}
		}
		
		System.out.println("::: In FileUploadHelper :: End checkerApproveFileUpload for systemFileUploadType: "+systemFileUploadType);
	    DefaultLogger.info("::: In FileUploadHelper :: End checkerApproveFileUpload for systemFileUploadType: ",systemFileUploadType);
	}
	
	private static void moveFile(ResourceBundle bundle, File sourceFile, String fileName) throws IOException {

		FileInputStream inputStream = null;
		FileOutputStream outstream = null;
		try {
			inputStream = new FileInputStream(sourceFile);
			String destPath = bundle.getString("ftp.hrms.backup.local.dir");
			File destFolderPath = new File(destPath);
			if (!destFolderPath.exists()) {
				destFolderPath.mkdirs();
			}

			outstream = new FileOutputStream(destPath + fileName);
			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = inputStream.read(buffer)) > 0) {

				outstream.write(buffer, 0, length);

			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (inputStream != null)
					inputStream.close();

				if (outstream != null)
					outstream.close();

				sourceFile.delete();
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
	}
	
	private static void readingFile(String filePath) throws DBConnectionException, SQLException, Exception {
		BufferedReader bufferReader = null;
		FileReader fileReader = null;
		String[] spoolArray;
		String currentsLine = "";
		try 
		{
			fileReader = new FileReader(filePath);
			bufferReader = new BufferedReader(fileReader);
			String sCurrentLine;
			String employeeNumber = "";
		
			String splitBy = "~";

			int counter =0;
			while ((sCurrentLine = bufferReader.readLine()) != null) {
//				System.out.println("sCurrentLine=>"+sCurrentLine);
				currentsLine = sCurrentLine;
				counter++;
				String [] data = sCurrentLine.split(splitBy);
				try {
				if(counter >= 2)
				{
					sCurrentLine = sCurrentLine.trim();
					employeeNumber = data[0];
					
					
//					RelationshipMgrDAOImpl relationshipMgrDAOImpl = new RelationshipMgrDAOImpl();	
					IRelationshipMgrDAO relationshipMgrDAOImpl = (IRelationshipMgrDAO)BeanHouse.get("relationshipMgrDAO");
					IHRMSData  ihrmsData = relationshipMgrDAOImpl.getHRMSEmpDetails(employeeNumber); 
//					System.out.println("FileUploadHelper.java => readingFile(String filePath) =>employeeNumber =>"+employeeNumber+" and ihrmsData=>"+ihrmsData);
					if(null == ihrmsData || null == ihrmsData.getEmployeeCode()) {
						System.out.println("Going for insertHRMSData(data).");
						relationshipMgrDAOImpl.insertHRMSData(data);
					}else {
						System.out.println("Going for updateHRMSData(ihrmsData,data).");
						relationshipMgrDAOImpl.updateHRMSData(ihrmsData,data);
					}
				
					IRelationshipMgr iRelationshipMgr = relationshipMgrDAOImpl.getRelationshipMgrByCode(employeeNumber);
					if(null != iRelationshipMgr) {
						System.out.println("Going for updateRMData(iRelationshipMgr,data).");
						relationshipMgrDAOImpl.updateRMData(iRelationshipMgr,data);
					}
					IRegion region = relationshipMgrDAOImpl.getRegionByRegionName(data[10]);
					ICustomerDAO iCustomerDAO = CustomerDAOFactory.getDAO();
					System.out.println("iCustomerDAO "+iCustomerDAO);
					getFileUploadJdbc().updatePartyRMDetails(data[0], region);
					
				}	
				}
				catch(Exception e) {
					System.out.println("Exception while readingFile HRMS File. e=>"+e);
					e.printStackTrace();
				}
				
			}
			
			
		} catch (IOException e) {
			System.out.println("Exception in readingFile HRMS.sCurrentLine=>"+currentsLine);
			e.printStackTrace();

		} finally {
			try {
				if (bufferReader != null)
					bufferReader.close();
				if (fileReader != null)
					fileReader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	private static long getSequence(String seqName){
		long seqId=0l;
		try
		{
			seqId = Long.parseLong((new SequenceManager()).getSeqNum(seqName, true));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return seqId;
	}
}
