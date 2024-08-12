package com.integrosys.cms.ui.bulkudfupdateupload;

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
import com.integrosys.cms.app.fileUpload.bus.OBPartyCamFile;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.ui.bulkudfupdateupload.proxy.IBulkUDFUploadProxyManager;

public class MakerReadBulkUDFRejectCmd extends AbstractCommand implements ICommonEventConstant{
	
private IBulkUDFUploadProxyManager bulkudfuploadProxy;
	

	
	
	/*IBulkUDFUploadProxyManager bulkudfuploadProxy = (IBulkUDFUploadProxyManager) BeanHouse.get("bulkudfuploadProxy");*/

	public MakerReadBulkUDFRejectCmd(){
			}
	
	public IBulkUDFUploadProxyManager getBulkudfuploadProxy() {
		return bulkudfuploadProxy;
	}

	public void setBulkudfuploadProxy(IBulkUDFUploadProxyManager bulkudfuploadProxy) {
		this.bulkudfuploadProxy = bulkudfuploadProxy;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{"IFileUploadTrxValue", "com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "total", "java.lang.String", REQUEST_SCOPE },
				{ "correct", "java.lang.String", REQUEST_SCOPE },
				{ "fail", "java.lang.String", REQUEST_SCOPE },
				{"totalUploadedList", "java.util.List", SERVICE_SCOPE},
				{"bulkudflist", "java.util.List", SERVICE_SCOPE},
				
		});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,FileUploadException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			IFileUpload fileUpload;
			SearchResult bulkudflist = new SearchResult();
			IFileUploadTrxValue trxValue=null;
			String fileId=(String) (map.get("TrxId"));
			String event = (String) map.get("event");
			
			ArrayList errorList=new ArrayList();
			// function to get Component Trx value
			trxValue = (OBFileUploadTrxValue) getBulkudfuploadProxy().getFielUploadByTrxID(fileId);
			IFileUploadJdbc jdbc=(IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
			bulkudflist=jdbc.getAllBulkUDFFile(trxValue.getStagingReferenceID());
			
			List uploadList=new ArrayList();
			if(bulkudflist!=null){
				uploadList = new ArrayList(bulkudflist.getResultList());		
			}
			long countPass = 0;
			long countFail = 0;
			
					for (int i = 0; i < uploadList.size(); i++) {
						OBTempBulkUDFFileUpload obj = (OBTempBulkUDFFileUpload) uploadList.get(i);
	
						if ("FAIL".equals(obj.getStatus()) && "N".equals(obj.getUploadStatus())) {
							countFail++;
						} else if ("PASS".equals(obj.getStatus()) && "Y".equals(obj.getUploadStatus())) {
							countPass++;
						}
					}
			
			//Added For FD Upload End
			resultMap.put("total", String.valueOf(uploadList.size()));
			resultMap.put("correct", String.valueOf(countPass));
			resultMap.put("fail", String.valueOf(countFail));

			resultMap.put("IFileUploadTrxValue", trxValue);
			resultMap.put("totalUploadedList", uploadList);
			resultMap.put("bulkudflist", uploadList);
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
