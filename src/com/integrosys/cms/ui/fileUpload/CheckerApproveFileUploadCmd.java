package com.integrosys.cms.ui.fileUpload;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadDao;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.bus.OBBahrainFile;
import com.integrosys.cms.app.fileUpload.bus.OBFileUpload;
import com.integrosys.cms.app.fileUpload.bus.OBFinwareFile;
import com.integrosys.cms.app.fileUpload.bus.OBHongKongFile;
import com.integrosys.cms.app.fileUpload.bus.OBUbsFile;
import com.integrosys.cms.app.fileUpload.proxy.IFileUploadProxyManager;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

public class CheckerApproveFileUploadCmd extends AbstractCommand implements ICommonEventConstant{
	public  ArrayList finalList = new ArrayList();
	public CheckerApproveFileUploadCmd(){		
	}

	private IFileUploadProxyManager fileUploadProxy;

	public IFileUploadProxyManager getFileUploadProxy() {
		return fileUploadProxy;
	}

	public void setFileUploadProxy(IFileUploadProxyManager fileUploadProxy) {
		this.fileUploadProxy = fileUploadProxy;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][]{
				{"IFileUploadTrxValue", "com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue", SERVICE_SCOPE},
				{ "dataFromUpdLineFacilityActual", "java.util.concurrent.ConcurrentMap", SERVICE_SCOPE },
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"ubsList", "java.util.List", SERVICE_SCOPE},
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{"remarks", "java.lang.String", REQUEST_SCOPE}
				
		}
		);
	}

	public String[][] getResultDescriptor() {
		return (new String[][]{
				{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
				{ "total", "java.lang.String", REQUEST_SCOPE },
				{ "correct", "java.lang.String", REQUEST_SCOPE },
				{ "fail", "java.lang.String", REQUEST_SCOPE },
				{ "preUpload", "java.lang.String", REQUEST_SCOPE },
				{"trxValueOut", "com.integrosys.cms.ui.fileUpload.FileUploadAction.IFileUploadTrxValue", SERVICE_SCOPE},
				{ "totalUploadedList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "errorList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "fileType", "java.lang.String", REQUEST_SCOPE },
		}
		);
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap chkMap = new HashMap();
		// Added by : Dayananda for FC-UPLOAD changes || CSV format || Starts
		ConcurrentMap<String, String> dataFromUpdLineFacilityActual = new ConcurrentHashMap<String,String>();
		dataFromUpdLineFacilityActual = (ConcurrentMap)map.get("dataFromUpdLineFacilityActual");
		//Added by : Dayananda for FC-UPLOAD changes || CSV format || Ends
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ArrayList totalUploadedList=new ArrayList();
			ArrayList errorList=new ArrayList();
			ArrayList consolidateList=new ArrayList();
			// File  Trx value
			IFileUploadTrxValue trxValueIn = (OBFileUploadTrxValue) map.get("IFileUploadTrxValue");
			int countPass =0;
			int countFail =0;
			long countPassFd =0;
			long countFailFd =0;
			ICommonUser user=(ICommonUser)map.get(IGlobalConstant.USER);
			ArrayList ubsList=(ArrayList)map.get("ubsList");
			OBFileUpload actualClone = new OBFileUpload();
			OBFileUpload stagingClone = new OBFileUpload();
			OBFileUpload actual = (OBFileUpload)trxValueIn.getFileUpload();
			OBFileUpload staging = (OBFileUpload)trxValueIn.getStagingfileUpload();
			IFileUploadTrxValue trxValueOut=null;
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
			IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
			IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
			Date applicationDate=new Date();
			for(int i=0;i<generalParamEntries.length;i++){
				if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
					applicationDate=new Date(generalParamEntries[i].getParamValue());
				}
			}
			
			DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
			String appDate=df.format(applicationDate);
			
			Date d = DateUtil.getDate();
			applicationDate.setHours(d.getHours());
			applicationDate.setMinutes(d.getMinutes());
			applicationDate.setSeconds(d.getSeconds());
			IFileUploadDao dao=(IFileUploadDao)BeanHouse.get("fileUploadDao");
			IFileUploadJdbc jdbc=(IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
			if(trxValueIn.getTransactionSubType().equals("UBS_UPLOAD")){
				if(ubsList!=null && ubsList.size()>0){
					HashMap mp = (HashMap)jdbc.insertUbsfileActual(ubsList, this,trxValueIn.getStagingfileUpload().getFileName(),user,applicationDate,trxValueIn,dataFromUpdLineFacilityActual);
					totalUploadedList = (ArrayList) mp.get("totalUploadedList");
					
					//jdbc.updateXrefActualAmount(totalUploadedList,"UBS-LIMITS");
					
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
					stgFile.setApproveBy(user.getEmployeeID());
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
						jdbc.createEntireUbsActualFile(batchList);
					}
				}
				// Added By Dayananda Laishram on 31/03/2015 || SP_FACILITY_RELEASE 
			// 03/02/2016: Uma Khot:Commented below code as not to update Released Amount from upload file.		
			//	jdbc.spUpdateReleasedAmount("UBS_UPLOAD","ACTUAL");
				
			}else if(trxValueIn.getTransactionSubType().equals("HONGKONG_UPLOAD")){
				if(ubsList!=null && ubsList.size()>0){
					HashMap mp = (HashMap)jdbc.insertHongKongfileActual(ubsList, this,trxValueIn.getStagingfileUpload().getFileName(),user,applicationDate,trxValueIn,dataFromUpdLineFacilityActual);
					
					totalUploadedList = (ArrayList) mp.get("totalUploadedList");
					
					//jdbc.updateXrefActualAmount(totalUploadedList,"HONGKONG");
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
					stgFile.setApproveBy(user.getEmployeeID());
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
						jdbc.createEntireHongkongActualFile(batchList);
					}
				}
				// Added By Dayananda Laishram on 31/03/2015 || SP_FACILITY_RELEASE 
				// 03/02/2016: Uma Khot:Commented below code as not to update Released Amount from upload file.			
			//	jdbc.spUpdateReleasedAmount("HONGKONG_UPLOAD","ACTUAL");
				
			}else if(trxValueIn.getTransactionSubType().equals("FINWARE_UPLOAD")){
				if(ubsList!=null && ubsList.size()>0){
					HashMap mp = (HashMap)jdbc.insertFinwarefileActual(ubsList, this,trxValueIn.getStagingfileUpload().getFileName(),user,applicationDate,trxValueIn,dataFromUpdLineFacilityActual);
					totalUploadedList = (ArrayList) mp.get("totalUploadedList");
					
					//jdbc.updateXrefFinwareActualAmount(totalUploadedList,"FINWARE");
					
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
					stgFile.setApproveBy(user.getEmployeeID());
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
						jdbc.createEntireFinwareActualFile(batchList);
					}
				}
				// Added By Dayananda Laishram on 31/03/2015 || SP_FACILITY_RELEASE 
			// 03/02/2016: Uma Khot:Commented below code as not to update Released Amount from upload file.
			//	jdbc.spUpdateReleasedAmount("FINWARE_UPLOAD","ACTUAL");
				
			}else if(trxValueIn.getTransactionSubType().equals("BAHRAIN_UPLOAD")){
				if(ubsList!=null && ubsList.size()>0){
					HashMap mp = (HashMap)jdbc.insertBahrainfileActual(ubsList, this,trxValueIn.getStagingfileUpload().getFileName(),user,applicationDate,trxValueIn,dataFromUpdLineFacilityActual);
					totalUploadedList = (ArrayList) mp.get("totalUploadedList");
					
					//jdbc.updateXrefActualAmount(totalUploadedList,"BAHRAIN");
					
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
					stgFile.setApproveBy(user.getEmployeeID());
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
						jdbc.createEntireBahrainActualFile(batchList);
					}
				}
				// Added By Dayananda Laishram on 31/03/2015 || SP_FACILITY_RELEASE 
				// 03/02/2016: Uma Khot:Commented below code as not to update Released Amount from upload file.
			//	jdbc.spUpdateReleasedAmount("BAHRAIN_UPLOAD","ACTUAL");
			}
		
			//Added For FD Upload
			else if ("FD_UPLOAD".equals(trxValueIn.getTransactionSubType())) {
				ArrayList totalUploadedFileList=new ArrayList();
//				IFileUploadJdbc fileUploadJdbcObj = (IFileUploadJdbc) BeanHouse
//						.get("fileUploadJdbc");
//				// IFileUploadJdbc
//				// jdbc=(IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
//				String refreshStatus = fileUploadJdbcObj
//						.spUploadTransaction(trxValueIn.getTransactionSubType()
//								+ "_STATUS_MV");
//
//				if (null != refreshStatus
//						&& "1".equalsIgnoreCase(refreshStatus))
//					dataFromFdCacheView = jdbc
//							.cacheDataFromFdMaterializedView("FD_UPLOAD_STATUS_MV");
//
//				if (ubsList != null && ubsList.size() > 0) {
//					HashMap mp = (HashMap) jdbc.insertFdfileActual(ubsList,
//							this, trxValueIn.getStagingfileUpload()
//									.getFileName(), dataFromFdCacheView);
//					totalUploadedList = (ArrayList) mp.get("totalUploadedList");
//
//					// jdbc.updateXrefActualAmount(totalUploadedList,"FD-LIMITS");
//
//					if (totalUploadedList.size() > 0) {
//						for (int i = 0; i < totalUploadedList.size(); i++) {
//							OBFdFile obj = (OBFdFile) totalUploadedList.get(i);
//							if ("PASS".equals(obj.getStatus())) {
//								countPass++;
//							} else if ("FAIL".equals(obj.getStatus())) {
//								countFail++;
//							}
//						}
//					}
					IFileUpload stgFile = trxValueIn.getStagingfileUpload();
					stgFile.setApproveBy(user.getEmployeeID());
//					stgFile.setApproveRecords(String.valueOf(countPass));
//					errorList = (ArrayList) mp.get("errorList");
					trxValueIn.setStagingfileUpload(stgFile);
					ctx.setCustomer(null); //Setting value null as we don't need cust details
					ctx.setLimitProfile(null);
					trxValueOut = getFileUploadProxy()
							.checkerApproveFileUpload(ctx, trxValueIn);
					long fileId = Long.parseLong(trxValueOut.getReferenceID());
//					if (totalUploadedList.size() > 0) {
//						for (int i = 0; i < totalUploadedList.size(); i++) {
//							OBFdFile obj = (OBFdFile) totalUploadedList.get(i);
//							obj.setFileId(fileId);
//							consolidateList.add(obj);
//						}
//					}
//					for (int i = 0; i < errorList.size(); i++) {
//						OBFdFile obj = (OBFdFile) errorList.get(i);
//						obj.setFileId(fileId);
//						consolidateList.add(obj);
//					}
//
//					int batchSize = 200;
//					for (int j = 0; j < consolidateList.size(); j += batchSize) {
//						List<OBFdFile> batchList = consolidateList
//								.subList(j, j + batchSize > consolidateList
//										.size() ? consolidateList.size() : j
//										+ batchSize);
//						jdbc.createEntireFdActualFile(batchList);
//					}
//					DefaultLogger
//							.debug(
//									this,
//									"############### Temp Actual Table is Updated for FD Upload ##################:: ");
					
					//jdbc.updateCashDepositToNull("CMS_CASH_DEPOSIT");
					
					//Start:Uma Khot:21/01/2016 Added for FD Upload NEw Logic as FD Upload was taking time
					jdbc.updateActualFdUploadStatus();
					jdbc.insertFileDataToTempActualTable(fileId,trxValueIn.getStagingReferenceID());
					
					jdbc.updateActualFdDetailsWithStatusActive(fileId, appDate);
					
					jdbc.updateActualFdDetails(fileId);
					jdbc.updateTempActualFdDetails(fileId);
					jdbc.insertTempActualFdDetails(fileId);
					countPassFd =jdbc.getCount(fileId,"CMS_FD_FILE_UPLOAD", "PASS");
					countFailFd=jdbc.getCount(fileId,"CMS_FD_FILE_UPLOAD", "FAIL");
					totalUploadedList.clear();
					totalUploadedList.addAll(jdbc.getTotalUploadedList(fileId,"CMS_FD_FILE_UPLOAD"));
					totalUploadedFileList.addAll(jdbc.getTotalUploadedFileList(fileId,"CMS_FD_FILE_UPLOAD"));
					
					resultMap.put("correct", String.valueOf(countPassFd));
					resultMap.put("fail", String.valueOf(countFailFd));
					resultMap.put("total", String.valueOf(totalUploadedFileList.size()));
					
					//End:Uma Khot:21/01/2016 Added for FD Upload NEw Logic as FD Upload was taking time
				}

				// Update Clims Actual table CMS_CASH_DEPOSIT


			resultMap.put("fileType", "CSV");
			resultMap.put("totalUploadedList", totalUploadedList);
			if(!"FD_UPLOAD".equals(trxValueIn.getTransactionSubType())){
			resultMap.put("total", String.valueOf(totalUploadedList.size()));
			resultMap.put("correct", String.valueOf(countPass));
			resultMap.put("fail", String.valueOf(countFail));
			}
			resultMap.put("errorList", errorList);
			resultMap.put("trxValueOut", trxValueOut);
		}catch (ComponentException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
