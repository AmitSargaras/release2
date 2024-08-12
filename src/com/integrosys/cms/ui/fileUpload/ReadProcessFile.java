package com.integrosys.cms.ui.fileUpload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.proxy.IFileUploadProxyManager;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;

public class ReadProcessFile extends AbstractCommand implements ICommonEventConstant{
	public ReadProcessFile(){
		
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
			
				{"IFileUploadTrxValue", "com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{"ubsList", "java.util.List", SERVICE_SCOPE},
				
		});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,FileUploadException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			IFileUpload fileUpload;
			SearchResult ubsFileList = new SearchResult();
			IFileUploadTrxValue trxValue=null;
			String fileId=(String) (map.get("TrxId"));
			String event = (String) map.get("event");
			// function to get Component Trx value
			trxValue = (OBFileUploadTrxValue) getFileUploadProxy().getFielUploadByTrxID(fileId);
			String trxStatus=(String)trxValue.getStatus();
			IFileUploadJdbc jdbc=(IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
			if(trxStatus.equals("ACTIVE")&& !trxValue.getFromState().equals("REJECTED")){
			if("UBS_UPLOAD".equals(trxValue.getTransactionSubType())){
			ubsFileList=jdbc.getAllActualUbsFile(trxValue.getReferenceID());
			}else if("HONGKONG_UPLOAD".equals(trxValue.getTransactionSubType())){
				ubsFileList=jdbc.getAllActualHongKongFile(trxValue.getReferenceID());
			}else if("FINWARE_UPLOAD".equals(trxValue.getTransactionSubType())){
				ubsFileList=jdbc.getAllActualFinwareFile(trxValue.getReferenceID());
			}else if("BAHRAIN_UPLOAD".equals(trxValue.getTransactionSubType())){
				ubsFileList=jdbc.getAllActualBahrainFile(trxValue.getReferenceID());
			}
			//Added For FD Upload
			else if("FD_UPLOAD".equals(trxValue.getTransactionSubType())){
				ubsFileList=jdbc.getAllActualFdFile(trxValue.getReferenceID());
			}
			}else {
				if("UBS_UPLOAD".equals(trxValue.getTransactionSubType())){
					ubsFileList=jdbc.getAllUbsFile(trxValue.getStagingReferenceID());
					}else if("HONGKONG_UPLOAD".equals(trxValue.getTransactionSubType())){
						ubsFileList=jdbc.getAllHongKongFile(trxValue.getStagingReferenceID());
					}else if("FINWARE_UPLOAD".equals(trxValue.getTransactionSubType())){
						ubsFileList=jdbc.getAllFinwareFile(trxValue.getStagingReferenceID());
					}else if("BAHRAIN_UPLOAD".equals(trxValue.getTransactionSubType())){
						ubsFileList=jdbc.getAllBahrainFile(trxValue.getStagingReferenceID());
					}
				//Added For FD Upload
					else if("FD_UPLOAD".equals(trxValue.getTransactionSubType())){
						ubsFileList=jdbc.getAllActualFdFile(trxValue.getStagingReferenceID());
					}
			}
			
			List ubsList=null;
			if(ubsFileList!=null){
				ubsList = new ArrayList(ubsFileList.getResultList());		
			}
			
			
			resultMap.put("IFileUploadTrxValue", trxValue);
			resultMap.put("ubsList", ubsList);
			//resultMap.put("componentObj", component);
			resultMap.put("event", event);
		} catch (FileUploadException e) {
		
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
