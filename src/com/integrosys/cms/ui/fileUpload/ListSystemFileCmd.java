package com.integrosys.cms.ui.fileUpload;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.proxy.IFileUploadProxyManager;
import com.integrosys.cms.app.ftp.FileUploadFtpService;
import com.integrosys.cms.app.ftp.IFileUploadConstants;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;
public class ListSystemFileCmd extends AbstractCommand implements ICommonEventConstant,IFileUploadConstants{

	private IFileUploadProxyManager fileUploadProxy;

	public IFileUploadProxyManager getFileUploadProxy() {
		return fileUploadProxy;
	}

	public void setFileUploadProxy(IFileUploadProxyManager fileUploadProxy) {
		this.fileUploadProxy = fileUploadProxy;
	}

	public ListSystemFileCmd(){		
	}

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "totalCount", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "fileUploadSystem", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "locale", "java.util.Locale", REQUEST_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE },

		};
	}
	public String[][] getResultDescriptor() {
		return (new String[][]{

				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "preUpload", "java.lang.String", REQUEST_SCOPE },
				{ "fileUploadSystem", "java.lang.String", REQUEST_SCOPE },
				{ "fileInfo", "java.uti.List", REQUEST_SCOPE },
				{com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
				{ "dataFromCacheView", "java.util.concurrent.ConcurrentMap", SERVICE_SCOPE },
				{ "dataFromUpdLineFacilityMV", "java.util.concurrent.ConcurrentMap", SERVICE_SCOPE },
			//	{ "dataFromFdCacheView", "java.util.Set", SERVICE_SCOPE }
		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
			
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String event=(String)map.get("event");
		String fileUploadSystem=(String)map.get("fileUploadSystem");
		IFileUploadJdbc fileUploadJdbcObj = (IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
		IFileUploadJdbc jdbc=(IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
		// Added By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION 
		ConcurrentMap<String, String> dataFromCacheView = new ConcurrentHashMap<String,String>();
		ConcurrentMap<String, String> dataFromUpdLineFacilityMV = new ConcurrentHashMap<String,String>();
		String refreshStatus = fileUploadJdbcObj.spUploadTransaction(fileUploadSystem);
	//	Set<String> dataFromFdCacheView=new HashSet<String>();  //For FD Upload
		boolean preUpload=false;
		List fileInfo=new ArrayList();
		Date date=new Date();
		IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
		IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
		IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
		Date applicationDate=new Date();
		for(int i=0;i<generalParamEntries.length;i++){
			if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
				applicationDate=new Date(generalParamEntries[i].getParamValue());
			}
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String checkDate=df.format(applicationDate);
		//below code create master transaction
		try{
			if(fileUploadSystem!=null && fileUploadSystem.equalsIgnoreCase("UBS")){
				
				// Added By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION | starts
				if(null!= refreshStatus && refreshStatus.equalsIgnoreCase("1")){
					dataFromCacheView = jdbc.cacheDataFromMaterializedView("UBS_UPLOAD_STATUS_MV");
					dataFromUpdLineFacilityMV = jdbc.cacheDataFromUpdLineFacilityMV("UPD_LINE_FAC_MV","UBS-LIMITS");
					DefaultLogger.info(this,"In ListSystemFileCmd [dataFromUpdLineFacilityMV] values is: "+dataFromUpdLineFacilityMV);
				}
				//Added By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION | Ends
				
				IFileUploadJdbc fileUploadJdbc = (IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
				if(fileUploadJdbc.getUplodCount(FILEUPLOAD_UBS_TRANS_SUBTYPE)>0){
					preUpload=true;
				}
				if(!preUpload){
					ResourceBundle bundle = ResourceBundle.getBundle("ofa");
					FileUploadFtpService fileUploadFtpService = new FileUploadFtpService();
					
					fileUploadFtpService.downlodUploadedFiles("UBS");

					File path=new File(bundle.getString(FTP_FILEUPLOAD_DOWNLOAD_UBS_LOCAL_DIR));
					File list[]=path.listFiles();

					if(null!=list) { // 																	RIYA
					for(int i=list.length-1;i>=0;i--){ 
						File sortFile=list[i];
						if(//(sortFile.getName().contains(FTP_UBS_FILE_NAME+checkDate) 
							//	&& (sortFile.getName().endsWith(".csv") ||  sortFile.getName().endsWith(".CSV")))
								
						//Uma:For UBS NCB Migration CR		
						//||
						(sortFile.getName().contains(FCCNCB_FILE_NAME+checkDate) 
								&& (sortFile.getName().endsWith(".csv") ||  sortFile.getName().endsWith(".CSV")))){
							fileInfo.add(list[i]);
						}
					}}
				}
			}
			else if (fileUploadSystem != null && fileUploadSystem.equalsIgnoreCase("FINWARE")) {

				// Added By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION |Starts
				if (null != refreshStatus && refreshStatus.equalsIgnoreCase("1")) {
					dataFromCacheView = jdbc.cacheDataFromMaterializedView("FINWARE_UPLOAD_STATUS_MV");
					dataFromUpdLineFacilityMV = jdbc.cacheDataFromUpdLineFacilityMV("UPD_LINE_FAC_MV", "FINWARE");
					DefaultLogger.info(this,
							"In ListSystemFileCmd [dataFromUpdLineFacilityMV] values is: " + dataFromUpdLineFacilityMV);
				}
				// Added By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION | Ends
				IFileUploadJdbc fileUploadJdbc = (IFileUploadJdbc) BeanHouse.get("fileUploadJdbc");
				if (fileUploadJdbc.getUplodCount(FILEUPLOAD_FINWARE_TRANS_SUBTYPE) > 0) {
					preUpload = true;
				}
				if (!preUpload) {
					ResourceBundle bundle = ResourceBundle.getBundle("ofa");
					FileUploadFtpService fileUploadFtpService = new FileUploadFtpService();
					 fileUploadFtpService.downlodUploadedFiles("FINWARE");

					File path = new File(bundle.getString(FTP_FILEUPLOAD_DOWNLOAD_FINWARE_LOCAL_DIR));
					File list[] = path.listFiles();

					if (null != list) {
						for (int i = list.length - 1; i >= 0; i--) {
							File sortFile = list[i];
							if (sortFile.getName().contains(FTP_FINWARE_FILE_NAME + checkDate)
									// Added by : Dayananda for FC-UPLOAD changes || CSV format || Starts
									// && (sortFile.getName().endsWith(".xls") ||
									// sortFile.getName().endsWith(".XLS")) )
									&& (sortFile.getName().endsWith(".csv") || sortFile.getName().endsWith(".CSV")))
							// Added by : Dayananda for FC-UPLOAD changes || CSV format || Ends
							{
								fileInfo.add(list[i]);
							}
						}
					}
				}
			}
			else if (fileUploadSystem != null && fileUploadSystem.equalsIgnoreCase("HONGKONG")) {

				// Added By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION |Starts
				if (null != refreshStatus && refreshStatus.equalsIgnoreCase("1")) {
					dataFromCacheView = jdbc.cacheDataFromMaterializedView("HONGKONG_UPLOAD_STATUS_MV");
					dataFromUpdLineFacilityMV = jdbc.cacheDataFromUpdLineFacilityMV("UPD_LINE_FAC_MV", "HONGKONG");
					DefaultLogger.info(this,
							"In ListSystemFileCmd [dataFromUpdLineFacilityMV] valuse is: " + dataFromUpdLineFacilityMV);
				}
				// Added By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION | Ends
				IFileUploadJdbc fileUploadJdbc = (IFileUploadJdbc) BeanHouse.get("fileUploadJdbc");
				if (fileUploadJdbc.getUplodCount(FILEUPLOAD_HONGKONG_TRANS_SUBTYPE) > 0) {
					preUpload = true;
				}
				if (!preUpload) {
					ResourceBundle bundle = ResourceBundle.getBundle("ofa");
					FileUploadFtpService fileUploadFtpService = new FileUploadFtpService();
					 fileUploadFtpService.downlodUploadedFiles("HONGKONG");

					File path = new File(bundle.getString(FTP_FILEUPLOAD_DOWNLOAD_HONGKONG_LOCAL_DIR));
					File list[] = path.listFiles();

					if (null != list) {
						for (int i = list.length - 1; i >= 0; i--) {
							File sortFile = list[i];
							if (sortFile.getName().contains(FTP_HONGKONG_FILE_NAME + checkDate)
									&& (sortFile.getName().endsWith(".xls") || sortFile.getName().endsWith(".XLS")
											|| sortFile.getName().endsWith(".xlsx")
											|| sortFile.getName().endsWith(".XLSX"))) {
								fileInfo.add(list[i]);
							}
						}
					}
				}
			}
			else if(fileUploadSystem!=null && fileUploadSystem.equalsIgnoreCase("BAHRAIN")){
				
				// Added By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION |Starts
				if(null!= refreshStatus && refreshStatus.equalsIgnoreCase("1")){
					dataFromCacheView = jdbc.cacheDataFromMaterializedView("BAHRAIN_UPLOAD_STATUS_MV");
					dataFromUpdLineFacilityMV = jdbc.cacheDataFromUpdLineFacilityMV("UPD_LINE_FAC_MV","BAHRAIN");
					DefaultLogger.info(this,"In ListSystemFileCmd [dataFromUpdLineFacilityMV] valuse is: "+dataFromUpdLineFacilityMV);
				}
				//Added By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION | Ends
				
				IFileUploadJdbc fileUploadJdbc = (IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
				if(fileUploadJdbc.getUplodCount(FILEUPLOAD_BAHRAIN_TRANS_SUBTYPE)>0){
					preUpload=true;
				}
				if(!preUpload){
					ResourceBundle bundle = ResourceBundle.getBundle("ofa");
					FileUploadFtpService fileUploadFtpService = new FileUploadFtpService();
					fileUploadFtpService.downlodUploadedFiles("BAHRAIN");

					File path=new File(bundle.getString(FTP_FILEUPLOAD_DOWNLOAD_BAHRAIN_LOCAL_DIR));
					File list[]=path.listFiles();

					if(null!=list) {
					for(int i=list.length-1;i>=0;i--){ 
						File sortFile=list[i];
						if(sortFile.getName().contains(FTP_BAHRAIN_FILE_NAME+checkDate) 
								&& (sortFile.getName().endsWith(".xls") ||  sortFile.getName().endsWith(".XLS"))
								|| sortFile.getName().endsWith(".xlsx") || sortFile.getName().endsWith(".XLSX") ){
							fileInfo.add(list[i]);
						}
					}}
				}
			}
			
			//Added by Uma to support FD Upload
			else if (fileUploadSystem != null
					&& "FD".equalsIgnoreCase(fileUploadSystem)) {

				// UBS_UPLOAD_OPTIMIZATION |Starts
//				if (null != refreshStatus
//						&& "1".equalsIgnoreCase(refreshStatus)) {
//					dataFromFdCacheView = jdbc
//							.cacheDataFromFdMaterializedView("FD_UPLOAD_STATUS_MV");
//					// dataFromUpdLineFacilityMV =
//					// jdbc.cacheDataFromUpdLineFacilityMV("UPD_LINE_FAC_MV","BAHRAIN");
//					// DefaultLogger.info(this,"In ListSystemFileCmd [dataFromUpdLineFacilityMV] valuse is: "+dataFromUpdLineFacilityMV);
//				}
				// UBS_UPLOAD_OPTIMIZATION | Ends

				IFileUploadJdbc fileUploadJdbc = (IFileUploadJdbc) BeanHouse
						.get("fileUploadJdbc");
				if (fileUploadJdbc.getUplodCount(FILEUPLOAD_FD_TRANS_SUBTYPE) > 0) {
					preUpload = true;
				}
				if (!preUpload) {
					ResourceBundle bundle = ResourceBundle.getBundle("ofa");
					// Commented as don't need to download file from FTP server
					FileUploadFtpService fileUploadFtpService = new FileUploadFtpService();
					fileUploadFtpService.downlodUploadedFiles("FD");

					File path = new File(bundle
							.getString(FTP_FILEUPLOAD_DOWNLOAD_FD_LOCAL_DIR));
					if(null!=path){
					File list[] = path.listFiles();

					if(null!=list) {
					for (int i = list.length - 1; i >= 0; i--) {
						File sortFile = list[i];
				/*		if (sortFile.getName().contains(
								FTP_FD_FILE_NAME + checkDate)
								&& (sortFile.getName().endsWith(".csv") || sortFile
										.getName().endsWith(".CSV")))*/ 
						if (sortFile.getName().contains(bundle.getString(FTP_FD_FILE_NAME ))
							&& (sortFile.getName().endsWith(".csv") || sortFile.getName().endsWith(".CSV"))){
							fileInfo.add(list[i]);
						}
					  }}
					}
				}
			}
		}
		catch(FileUploadException ex){
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}  catch (Exception e) { 
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		//end of master transaction
		resultMap.put("event", event);
		resultMap.put("fileInfo", fileInfo);
		resultMap.put("fileUploadSystem", fileUploadSystem);
		resultMap.put("preUpload",String.valueOf(preUpload));
		// Added By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION |Starts
		resultMap.put("dataFromCacheView",dataFromCacheView);
		resultMap.put("dataFromUpdLineFacilityMV",dataFromUpdLineFacilityMV);
		//Added By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION | Ends
		
		//Added for FD Upload
//		resultMap.put("dataFromFdCacheView",dataFromFdCacheView);
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
