package com.integrosys.cms.ui.bulkudfupdateupload;

import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.bulkudfupdateupload.proxy.IBulkUDFUploadProxyManager;

public class CheckerRejectBulkUDFFileCmd extends AbstractCommand implements ICommonEventConstant{

private IBulkUDFUploadProxyManager bulkudfuploadProxy;
	
	
	/*IBulkUDFUploadProxyManager bulkudfuploadProxy = (IBulkUDFUploadProxyManager) BeanHouse.get("bulkudfuploadProxy");*/

	public CheckerRejectBulkUDFFileCmd(){
		
	}
	
	public IBulkUDFUploadProxyManager getBulkudfuploadProxy() {
		return bulkudfuploadProxy;
	}

	public void setBulkudfuploadProxy(IBulkUDFUploadProxyManager bulkudfuploadProxy) {
		this.bulkudfuploadProxy = bulkudfuploadProxy;
	}
		
	public String[][] getParameterDescriptor() {
		return (new String[][]{
				{"IFileUploadTrxValue", "com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"bulkudflist", "java.util.List", SERVICE_SCOPE},
				{"remarks", "java.lang.String", REQUEST_SCOPE}
		}
		);
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][]{
				{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
				{"trxValueOut", "com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue", SERVICE_SCOPE},
				{"bulkudflist","java.util.List",SERVICE_SCOPE}
		}
		);
	}
	
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,ComponentException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();	
		
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			// File  Trx value
			IFileUploadTrxValue trxValueIn = (OBFileUploadTrxValue) map.get("IFileUploadTrxValue");
			List bulkudflist=(List)map.get("bulkudflist");
			
			String remarks = (String) map.get("remarks");
			
			if (null == remarks || remarks.isEmpty()) {
				exceptionMap.put("limitRemarksError", new ActionMessage("error.string.mandatory"));
				resultMap.put("trxValueOut", trxValueIn);
				resultMap.put("bulkudflist", bulkudflist);

				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,exceptionMap);
				return returnMap;
			}
			
			else{
			ctx.setRemarks(remarks);
			}
			
			IFileUploadTrxValue trxValueOut = getBulkudfuploadProxy().checkerRejectFileUpload(ctx, trxValueIn);
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
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

	
}
