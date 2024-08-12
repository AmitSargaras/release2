package com.integrosys.cms.ui.fileUpload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.bus.OBBahrainFile;
import com.integrosys.cms.app.fileUpload.bus.OBFdFile;
import com.integrosys.cms.app.fileUpload.bus.OBFinwareFile;
import com.integrosys.cms.app.fileUpload.bus.OBHongKongFile;
import com.integrosys.cms.app.fileUpload.bus.OBUbsFile;
import com.integrosys.cms.app.fileUpload.proxy.IFileUploadProxyManager;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;

public class CheckerReadFileUploadCmd extends AbstractCommand implements ICommonEventConstant{
	public CheckerReadFileUploadCmd(){
	}

	private IFileUploadProxyManager fileUploadProxy;	


	public IFileUploadProxyManager getFileUploadProxy() {
		return fileUploadProxy;
	}

	public void setFileUploadProxy(IFileUploadProxyManager fileUploadProxy) {
		this.fileUploadProxy = fileUploadProxy;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
		});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ "componentObj", "com.integrosys.cms.app.component.bus.OBComponent", FORM_SCOPE },
				{"IFileUploadTrxValue", "com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "total", "java.lang.String", REQUEST_SCOPE },
				{ "correct", "java.lang.String", REQUEST_SCOPE },
				{ "fail", "java.lang.String", REQUEST_SCOPE },
				{"totalUploadedList", "java.util.List", SERVICE_SCOPE},
				{"ubsList", "java.util.List", SERVICE_SCOPE},
				{ "dataFromUpdLineFacilityActual", "java.util.concurrent.ConcurrentMap", SERVICE_SCOPE },
				{ "stagingReferenceId", "java.lang.String", SERVICE_SCOPE},
		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,ComponentException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		ConcurrentMap<String, String> dataFromUpdLineFacilityActual = new ConcurrentHashMap<String,String>();
		
		try {
			IFileUpload fileUpload;
			SearchResult fileList = new SearchResult();
			ArrayList totalUploadedList=new ArrayList();
			ArrayList errorList=new ArrayList();
			IFileUploadTrxValue trxValue=null;
			String fileId=(String) (map.get("TrxId"));
			String event = (String) map.get("event");
			trxValue = (OBFileUploadTrxValue) getFileUploadProxy().getFielUploadByTrxID(fileId);
			IFileUploadJdbc jdbc=(IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
			if(trxValue.getTransactionSubType().equals("UBS_UPLOAD")){
				fileList=jdbc.getAllUbsFile(trxValue.getStagingReferenceID());
			}else if(trxValue.getTransactionSubType().equals("HONGKONG_UPLOAD")){
				fileList=jdbc.getAllHongKongFile(trxValue.getStagingReferenceID());
			}else if(trxValue.getTransactionSubType().equals("FINWARE_UPLOAD")){
				fileList=jdbc.getAllFinwareFile(trxValue.getStagingReferenceID());
			}else if(trxValue.getTransactionSubType().equals("BAHRAIN_UPLOAD")){
				fileList=jdbc.getAllBahrainFile(trxValue.getStagingReferenceID());
			}
			// For FD Upload
			else if("FD_UPLOAD".equals(trxValue.getTransactionSubType())){
				fileList=jdbc.getAllFdFile(trxValue.getStagingReferenceID());
			}
			List uploadList=null;
			if(fileList!=null){
				uploadList = new ArrayList(fileList.getResultList());		
			}
			int countPass = 0;
			int countFail = 0;
			if(trxValue.getTransactionSubType().equals("UBS_UPLOAD")){
				for(int i=0;i<uploadList.size();i++){
					OBUbsFile ubsRecord=(OBUbsFile)uploadList.get(i);

					if(ubsRecord.getStatus().equals("FAIL") && ubsRecord.getUploadStatus().equals("Y")){
						countFail++;
					}
					else if(ubsRecord.getStatus().equals("PASS") && ubsRecord.getUploadStatus().equals("Y")){
						countPass++;
					}
				}

				for(int i=0;i<uploadList.size();i++){
					OBUbsFile ubsRecord=(OBUbsFile)uploadList.get(i);
					if(ubsRecord.getUploadStatus().equals("Y")){
						totalUploadedList.add(ubsRecord);
					}
				}
				
				dataFromUpdLineFacilityActual = jdbc.cacheDataFromUpdLineActual("UBS-LIMITS");
			}
			else if (trxValue.getTransactionSubType().equals("HONGKONG_UPLOAD")){
				for(int i=0;i<uploadList.size();i++){
					OBHongKongFile hongkongRecord=(OBHongKongFile)uploadList.get(i);

					if(hongkongRecord.getStatus().equals("FAIL") && hongkongRecord.getUploadStatus().equals("Y")){
						countFail++;
					}
					else if(hongkongRecord.getStatus().equals("PASS") && hongkongRecord.getUploadStatus().equals("Y")){
						countPass++;
					}
				}

				for(int i=0;i<uploadList.size();i++){
					OBHongKongFile hongkongRecord=(OBHongKongFile)uploadList.get(i);
					if(hongkongRecord.getUploadStatus().equals("Y")){
						totalUploadedList.add(hongkongRecord);
					}
				}
				
				dataFromUpdLineFacilityActual = jdbc.cacheDataFromUpdLineActual("HONGKONG");
			}else if (trxValue.getTransactionSubType().equals("FINWARE_UPLOAD")){
				for(int i=0;i<uploadList.size();i++){
					OBFinwareFile finwareRecord=(OBFinwareFile)uploadList.get(i);

					if(finwareRecord.getStatus().equals("FAIL") && finwareRecord.getUploadStatus().equals("Y")){
						countFail++;
					}
					else if(finwareRecord.getStatus().equals("PASS") && finwareRecord.getUploadStatus().equals("Y")){
						countPass++;
					}
				}

				for(int i=0;i<uploadList.size();i++){
					OBFinwareFile finwareRecord=(OBFinwareFile)uploadList.get(i);
					if(finwareRecord.getUploadStatus().equals("Y")){
						totalUploadedList.add(finwareRecord);
					}
				}
				
				dataFromUpdLineFacilityActual = jdbc.cacheDataFromUpdLineActual("FINWARE");
			}	else if (trxValue.getTransactionSubType().equals("BAHRAIN_UPLOAD")){
				for(int i=0;i<uploadList.size();i++){
					OBBahrainFile bahrainRecord=(OBBahrainFile)uploadList.get(i);

					if(bahrainRecord.getStatus().equals("FAIL") && bahrainRecord.getUploadStatus().equals("Y")){
						countFail++;
					}
					else if(bahrainRecord.getStatus().equals("PASS") && bahrainRecord.getUploadStatus().equals("Y")){
						countPass++;
					}
				}

				for(int i=0;i<uploadList.size();i++){
					OBBahrainFile bahrainRecord=(OBBahrainFile)uploadList.get(i);
					if(bahrainRecord.getUploadStatus().equals("Y")){
						totalUploadedList.add(bahrainRecord);
					}
				}
				dataFromUpdLineFacilityActual = jdbc.cacheDataFromUpdLineActual("BAHRAIN");
			}
			
			//Added For FD Upload
			else if ("FD_UPLOAD".equals(trxValue.getTransactionSubType())) {
				for (int i = 0; i < uploadList.size(); i++) {
					OBFdFile fdRecord = (OBFdFile) uploadList.get(i);

					if ("FAIL".equals(fdRecord.getStatus())
							&& "Y".equals(fdRecord.getUploadStatus())) {
						countFail++;
					} else if ("PASS".equals(fdRecord.getStatus())
							&& "Y".equals(fdRecord.getUploadStatus())) {
						countPass++;
					}
				}

				for (int i = 0; i < uploadList.size(); i++) {
					OBFdFile fdRecord = (OBFdFile) uploadList.get(i);
					if ("Y".equals(fdRecord.getUploadStatus())) {
						totalUploadedList.add(fdRecord);
					}
				}

			}
			
			resultMap.put("total", String.valueOf(totalUploadedList.size()));
			resultMap.put("correct", String.valueOf(countPass));
			resultMap.put("fail", String.valueOf(countFail));

			resultMap.put("IFileUploadTrxValue", trxValue);
			if(null != trxValue) {
				resultMap.put("stagingReferenceId", trxValue.getStagingReferenceID());
			}
			resultMap.put("totalUploadedList", totalUploadedList);
			resultMap.put("ubsList", uploadList);
			resultMap.put("event", event);
			resultMap.put("dataFromUpdLineFacilityActual",dataFromUpdLineFacilityActual);
		} catch (ComponentException e) {

			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
