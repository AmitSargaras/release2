package com.integrosys.cms.ui.stockdetailsupload;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.bus.OBStockDetailsFile;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.stockdetailsupload.proxy.IStockdetailsUploadProxyManager;

public class CheckerRejectStockdetailsUploadCommand extends AbstractCommand implements ICommonEventConstant, IStockDetailsUploadConstants{

private IStockdetailsUploadProxyManager stockdetailsuploadProxy;
	
	public IStockdetailsUploadProxyManager getStockdetailsuploadProxy() {
		return stockdetailsuploadProxy;
	}

	public void setStockdetailsuploadProxy(IStockdetailsUploadProxyManager stockdetailsuploadProxy) {
		this.stockdetailsuploadProxy = stockdetailsuploadProxy;
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
			{ SESSION_TRX_VALUE_OUT, IFileUploadTrxValue.class.getName(),SERVICE_SCOPE },
			{ SESSION_TOTAL_UPLOADED_LIST, List.class.getName(),SERVICE_SCOPE },
			{ SESSION_TRX_OBJ, OBTrxContext.class.getName(), FORM_SCOPE },
			{ "remarks",  String.class.getName(), REQUEST_SCOPE },
			{ TOTAL, String.class.getName(), REQUEST_SCOPE },
			{ PASS, String.class.getName(), REQUEST_SCOPE },
			{ FAIL, String.class.getName(), REQUEST_SCOPE }
		};
	}
	
	public String[][] getResultDescriptor() {
		return new String[][] { 
			{ SESSION_TRX_VALUE_OUT, IFileUploadTrxValue.class.getName(),SERVICE_SCOPE },
			{ SESSION_TOTAL_UPLOADED_LIST, List.class.getName(),SERVICE_SCOPE },
			{ "remarks",  String.class.getName(), REQUEST_SCOPE },
			{ TOTAL, String.class.getName(), REQUEST_SCOPE },
			{ PASS, String.class.getName(), REQUEST_SCOPE },
			{ FAIL, String.class.getName(), REQUEST_SCOPE }
		};
	}
	
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();	
		IFileUploadTrxValue trxValueIn = (OBFileUploadTrxValue) map.get(SESSION_TRX_VALUE_OUT);
		try {
			IFileUploadJdbc jdbc=(IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
			OBTrxContext ctx = (OBTrxContext) map.get(SESSION_TRX_OBJ);
			List<OBStockDetailsFile> totalFileList =  (List)map.get(SESSION_TOTAL_UPLOADED_LIST);
			
			String remarks = (String) map.get("remarks");
			 
			if (StringUtils.isEmpty(remarks)) {
				exceptionMap.put("remarksError", new ActionMessage("error.string.mandatory"));
				
				resultMap.put(SESSION_TRX_VALUE_OUT, trxValueIn);
				resultMap.put(SESSION_TOTAL_UPLOADED_LIST, totalFileList);
				resultMap.put("remarks", remarks);
				resultMap.put(TOTAL, map.get(TOTAL));
				resultMap.put(PASS, map.get(PASS));
				resultMap.put(FAIL, map.get(FAIL));

				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,exceptionMap);
				return returnMap;
			}else{
				ctx.setRemarks(remarks);
			}
			
			IFileUploadTrxValue trxValueOut = getStockdetailsuploadProxy().checkerRejectFileUpload(ctx, trxValueIn);
			DefaultLogger.info(this, "User rejected file upload record ["+ trxValueOut.getStagingReferenceID() +"] with remarks: " + remarks);
			
			int batchSize = 200;
			for (int j = 0; j < totalFileList.size(); j += batchSize) {
					List<OBStockDetailsFile> batchList = totalFileList.subList(j,j + batchSize > totalFileList.size() ? totalFileList.size(): j + batchSize);
					jdbc.createEntireStockDetailsFile(batchList, true);
			}
			
			resultMap.put(SESSION_TRX_VALUE_OUT, trxValueOut);
		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Error occured while rejecting the transaction",e);
			throw new CommandProcessingException("Error occured while rejecting the transaction", e);
		}
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
	
}
