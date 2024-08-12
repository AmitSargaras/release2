package com.integrosys.cms.ui.fileUpload;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadDao;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.bus.OBFileUpload;
import com.integrosys.cms.app.fileUpload.bus.OBFacilitydetailsFile;
import com.integrosys.cms.app.fileUpload.proxy.IFileUploadProxyManager;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.batch.common.filereader.ProcessDataFile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

public class UploadFacilitydetailsFileCmd extends AbstractCommand implements ICommonEventConstant{
	
	private IFileUploadProxyManager fileUploadProxy;
	public static final String UBS_UPLOAD = "UbsUpload";

	public IFileUploadProxyManager getFileUploadProxy() {
		return fileUploadProxy;
	}
	public void setFileUploadProxy(IFileUploadProxyManager fileUploadProxy) {
		this.fileUploadProxy = fileUploadProxy;
	}
	public UploadFacilitydetailsFileCmd(){		
	}

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "totalCount", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "path", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "locale", "java.util.Locale", REQUEST_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE },
				{ "dataFromCacheView", "java.util.concurrent.ConcurrentMap", SERVICE_SCOPE },
				{ "dataFromUpdLineFacilityMV", "java.util.concurrent.ConcurrentMap", SERVICE_SCOPE },
		};
	}
	public String[][] getResultDescriptor() {
		return (new String[][]{              
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "total", "java.lang.String", REQUEST_SCOPE },
				{ "correct", "java.lang.String", REQUEST_SCOPE },
				{ "fail", "java.lang.String", REQUEST_SCOPE },
				{ "preUpload", "java.lang.String", REQUEST_SCOPE },
				{ "fileType", "java.lang.String", REQUEST_SCOPE },
				{"session.searchcomponentName", "java.lang.String", SERVICE_SCOPE},
				{"trxValueOut", "com.integrosys.cms.ui.fileUpload.FileUploadAction.IFileUploadTrxValue", SERVICE_SCOPE},
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "totalUploadedList", "java.util.List", SERVICE_SCOPE },
				{ "errorList", "java.util.List", SERVICE_SCOPE },
				{ "finalList", "java.util.List", SERVICE_SCOPE },
				{com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE}
		});
	}
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String event=(String)map.get("event");
		String path=(String)map.get("path");
		// Added By Mukesh Mohapatra || UBS_UPLOAD_OPTIMIZATION | Start
		ConcurrentMap<String, String> dataFromUpdLineFacilityMV = new ConcurrentHashMap<String,String>();
		ConcurrentMap<String, String> dataFromCacheView = new ConcurrentHashMap<String,String>();
		dataFromCacheView = (ConcurrentMap)map.get("dataFromCacheView");
		dataFromUpdLineFacilityMV = (ConcurrentMap)map.get("dataFromUpdLineFacilityMV");
		DefaultLogger.debug(this, "#####################In UploadUbsFileCommand ##### size of dataFromCacheView map is : "+dataFromCacheView.size());
		DefaultLogger.debug(this, "#####################In UploadUbsFileCommand ##### size of dataFromUpdLineFacilityMV map is : "+dataFromUpdLineFacilityMV.size());
		// Added By Mukesh Mohapatra || UBS_UPLOAD_OPTIMIZATION |  Ends
		DefaultLogger.debug(this, "#####################In UploadUbsFileCommand ##### line no 86#######:: "+path);
		ICommonUser user=(ICommonUser)map.get(IGlobalConstant.USER);
		
		int countPass =0;
		int countFail =0;
		List finalList = new ArrayList();
		String preUpload="false";
		ArrayList totalUploadedList=new ArrayList();
		ArrayList consolidateList=new ArrayList();
		ArrayList<OBFacilitydetailsFile> errorList=new ArrayList();
		HashMap mp = new HashMap();
		ArrayList resultList=new ArrayList();
		//IFacilitydetailsErrorLog objFacilitydetailsErrorLog = new OBFacilitydetailsErrorLog();
		String fileType = "EXCEL";
		IFileUploadTrxValue trxValueOut = new OBFileUploadTrxValue();
		trxValueOut.setTransactionSubType("RLD_UPLOAD");
		//below code create master transaction
		try{
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			File file=new File(path);
			FileInputStream readFile=new FileInputStream(path);    	 
			String fileName=file.getName();
			DefaultLogger.debug(this, "#####################In UploadFacilitydetailsFileCommand ##### line no 106#######:: "+fileName);
			IFileUploadDao dao=(IFileUploadDao)BeanHouse.get("fileUploadDao");
			IFileUploadJdbc jdbc=(IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
			if(fileName.endsWith(".XLS") || fileName.endsWith(".xls") || fileName.endsWith(".xlsx") || fileName.endsWith(".XLSX")){  
				ProcessDataFile dataFile = new ProcessDataFile();
				resultList = dataFile.processFileUpload(file, UBS_UPLOAD);
				HashMap eachDataMap = (HashMap)dataFile.getErrorList();
				
				IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
				IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
				IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
				Date applicationDate=new Date();
				for(int i=0;i<generalParamEntries.length;i++){
					if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
						applicationDate=new Date(generalParamEntries[i].getParamValue());
					}
				}
				Date d = DateUtil.getDate();
				applicationDate.setHours(d.getHours());
				applicationDate.setMinutes(d.getMinutes());
				applicationDate.setSeconds(d.getSeconds());
				
				
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
					DefaultLogger.debug(this, "#####################In UploadUbsFileCommand ##### line no 142#######:: "+resultList.size());
				
					mp = (HashMap)jdbc.insertFacilitydetailsfile(resultList, this,fileName,user,applicationDate,dataFromCacheView,dataFromUpdLineFacilityMV);
					totalUploadedList = (ArrayList) mp.get("totalUploadedList");
					
					errorList = (ArrayList) mp.get("errorList");
					if(totalUploadedList.size()>0){
						IFileUpload fileObj=new OBFileUpload();
						fileObj.setFileType("UBS_UPLOAD");
						fileObj.setUploadBy(ctx.getUser().getLoginID());
						fileObj.setUploadTime(applicationDate);
						fileObj.setFileName(fileName);
						fileObj.setTotalRecords(String.valueOf(resultList.size()));     
						for(int i=0;i<totalUploadedList.size();i++){
							OBFacilitydetailsFile ubsRecord=(OBFacilitydetailsFile)totalUploadedList.get(i);
							if(ubsRecord.getStatus().equals("PASS")){
								countPass++;
							}else if(ubsRecord.getStatus().equals("FAIL")){
								countFail++;
							}
						}
						fileObj.setApproveRecords(String.valueOf(countPass));
						DefaultLogger.debug(this, "##########4###########before create ##### line no 162#######:: ");
						trxValueOut = getFileUploadProxy().makerCreateFile(ctx,fileObj);
						DefaultLogger.debug(this, "##########5###########after create ###### line no 164######:: ");
						if(trxValueOut!=null){
							for(int i=0;i<totalUploadedList.size();i++){
								OBFacilitydetailsFile ubsRecord=(OBFacilitydetailsFile)totalUploadedList.get(i);
								ubsRecord.setFileId(Long.parseLong(trxValueOut.getStagingReferenceID()));
								consolidateList.add(ubsRecord);
							}
							for(int i=0;i<errorList.size();i++){
								OBFacilitydetailsFile ubsRecord=(OBFacilitydetailsFile)errorList.get(i);
								ubsRecord.setFileId(Long.parseLong(trxValueOut.getStagingReferenceID()));
								consolidateList.add(ubsRecord);
							}
							DefaultLogger.debug(this, "##########6###########consolidateList ##### line no 181#######:: "+consolidateList.size());
							int batchSize = 200;
							for (int j = 0; j < consolidateList.size(); j += batchSize) {
								List<OBFacilitydetailsFile> batchList = consolidateList.subList(j, j + batchSize > consolidateList.size() ? consolidateList.size() : j + batchSize);
								jdbc.createEntireFacilitydetailsStageFile(batchList);
							}
							DefaultLogger.debug(this, "##########6###########done errorList ##### line no 184#######:: ");

						}
					}
				}
			}
			else{
				fileType="NOT_EXCEL";
			}
		}
		catch(FileUploadException ex){
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (TrxParameterException e) {
			new CommandProcessingException(e.getMessage());
		} catch (Exception e) { 
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		//end of master transaction
		resultMap.put("event", event);
		resultMap.put("fileType", fileType);
		resultMap.put("preUpload", preUpload);
		resultMap.put("trxValueOut", trxValueOut);
		resultMap.put("totalUploadedList", totalUploadedList);
		resultMap.put("errorList", errorList);
		resultMap.put("finalList", finalList);
		resultMap.put("total", String.valueOf(resultList.size()+finalList.size()));
		resultMap.put("correct", String.valueOf(countPass));
		resultMap.put("fail", String.valueOf(countFail+finalList.size()));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
