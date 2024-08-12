package com.integrosys.cms.ui.stockdetailsupload;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.bus.OBStockDetailsFile;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.ui.stockdetailsupload.proxy.IStockdetailsUploadProxyManager;

public class ReadStockdetailsUploadCommand extends AbstractCommand implements ICommonEventConstant, IStockDetailsUploadConstants{

private IStockdetailsUploadProxyManager stockdetailsuploadProxy;
	
	public IStockdetailsUploadProxyManager getStockdetailsuploadProxy() {
		return stockdetailsuploadProxy;
	}

	public void setStockdetailsuploadProxy(IStockdetailsUploadProxyManager stockdetailsuploadProxy) {
		this.stockdetailsuploadProxy = stockdetailsuploadProxy;
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
			{ "TrxId", String.class.getName(), REQUEST_SCOPE },
			{ "event", String.class.getName(), REQUEST_SCOPE }
		};
	}
	
	public String[][] getResultDescriptor() {
		return new String[][] { 
			{ "event", String.class.getName(), REQUEST_SCOPE },
			{ SESSION_TRX_VALUE_OUT, IFileUploadTrxValue.class.getName(),SERVICE_SCOPE },
			{ SESSION_TOTAL_UPLOADED_LIST, List.class.getName(),SERVICE_SCOPE },
			{ SESSION_TOTAL_FAILED_LIST, List.class.getName(),SERVICE_SCOPE },
			{ TOTAL, String.class.getName(), REQUEST_SCOPE },
			{ PASS, String.class.getName(), REQUEST_SCOPE },
			{ FAIL, String.class.getName(), REQUEST_SCOPE }
		};
	}
	
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		
		String fileId = (String) map.get("TrxId");
		
		try {
				DefaultLogger.info(this, "Fetching file upload transaction for transaction id: " + fileId);
				IFileUploadTrxValue trxValue = getStockdetailsuploadProxy().getFileUploadByTrxID(fileId);
				
				IFileUploadJdbc jdbc=(IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
				List<OBStockDetailsFile> totalFileList = jdbc.getAllStockDetailsFile(Long.valueOf(trxValue.getStagingReferenceID()));
				
				long countPass = 0;
				long countFail = 0;
				for(OBStockDetailsFile file : totalFileList) {
					if (ICMSConstant.PASS.equals(file.getStatus()) && ICMSConstant.YES.equals(file.getUploadStatus())) {
						countPass++;
					}
				}
				countFail = totalFileList.size() - countPass;
				
				DefaultLogger.info(this, "Total record successfully uploaded : " + countPass + ", total record failed: " + countFail);
				
				resultMap.put(TOTAL, String.valueOf(totalFileList.size()));
				resultMap.put(PASS, String.valueOf(countPass));
				resultMap.put(FAIL, String.valueOf(countFail));
				resultMap.put(SESSION_TRX_VALUE_OUT, trxValue);
				resultMap.put(SESSION_TOTAL_UPLOADED_LIST, totalFileList);
			
		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Exception occurred while fetching record for file upload of type " + ICMSConstant.STOCK_DETAILS_UPLOAD, e);
			throw new CommandProcessingException("Exception occurred while fetching record for file upload of type " + ICMSConstant.STOCK_DETAILS_UPLOAD, e);
		}
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		
		return returnMap;
	}
	
}
