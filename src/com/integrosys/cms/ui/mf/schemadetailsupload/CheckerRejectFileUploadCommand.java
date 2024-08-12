package com.integrosys.cms.ui.mf.schemadetailsupload;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.bus.OBMFSchemaDetailsFile;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.mf.schemadetailsupload.proxy.ISchemadetailsUploadProxyManager;

public class CheckerRejectFileUploadCommand extends AbstractCommand implements ISchemaDetailsUploadConstants{

	private ISchemadetailsUploadProxyManager mfSchemaDetailsuploadProxy;
	
	public ISchemadetailsUploadProxyManager getMfSchemaDetailsuploadProxy() {
		return mfSchemaDetailsuploadProxy;
	}

	public void setMfSchemaDetailsuploadProxy(ISchemadetailsUploadProxyManager mfSchemaDetailsuploadProxy) {
		this.mfSchemaDetailsuploadProxy = mfSchemaDetailsuploadProxy;
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][]{
				{ SESSION_TRX_VALUE_OUT, IFileUploadTrxValue.class.getName(),SERVICE_SCOPE },
				{ SESSION_TRX_OBJ, OBTrxContext.class.getName(), FORM_SCOPE },
				{ SESSION_TOTAL_UPLOADED_LIST, List.class.getName(), SERVICE_SCOPE },
				{ "remarks",  String.class.getName(), REQUEST_SCOPE },
				{ "total", String.class.getName(), REQUEST_SCOPE },
				{ "correct", String.class.getName(), REQUEST_SCOPE },
				{ "fail", String.class.getName(), REQUEST_SCOPE }
		};
	}
	
	public String[][] getResultDescriptor() {
		return new String[][]{
				{ SESSION_TRX_VALUE_OUT, IFileUploadTrxValue.class.getName(),SERVICE_SCOPE },
				{ SESSION_TOTAL_UPLOADED_LIST, List.class.getName(), SERVICE_SCOPE },
				{ "remarks",  String.class.getName(), REQUEST_SCOPE },
				{ "total", String.class.getName(), REQUEST_SCOPE },
				{ "correct", String.class.getName(), REQUEST_SCOPE },
				{ "fail", String.class.getName(), REQUEST_SCOPE }
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
			List<OBMFSchemaDetailsFile> totalFileList =  (List)map.get(SESSION_TOTAL_UPLOADED_LIST);
			
			String remarks = (String) map.get("remarks");
			 
			if (null == remarks || remarks.isEmpty()) {
				exceptionMap.put("remarksError", new ActionMessage("error.string.mandatory"));
				
				resultMap.put(SESSION_TRX_VALUE_OUT, trxValueIn);
				resultMap.put(SESSION_TOTAL_UPLOADED_LIST, totalFileList);
				resultMap.put("remarks", remarks);
				resultMap.put("total", map.get("total"));
				resultMap.put("correct", map.get("correct"));
				resultMap.put("fail", map.get("fail"));

				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,exceptionMap);
				return returnMap;
			}else{
				ctx.setRemarks(remarks);
			}
			
			IFileUploadTrxValue trxValueOut = getMfSchemaDetailsuploadProxy().checkerRejectFileUpload(ctx, trxValueIn);
			DefaultLogger.info(this, "User rejected file upload record ["+ trxValueOut.getStagingReferenceID() +"] with remarks: " + remarks);
			
			int batchSize = 200;
			for (int j = 0; j < totalFileList.size(); j += batchSize) {
					List<OBMFSchemaDetailsFile> batchList = totalFileList.subList(j,j + batchSize > totalFileList.size() ? totalFileList.size(): j + batchSize);
					jdbc.createEntireMFSchemaDetailsFile(batchList, true);
			}
			
			resultMap.put(SESSION_TRX_VALUE_OUT, trxValueOut);
		} catch (SQLException e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Error occured while storing file detail in db");
			throw new CommandProcessingException("Error occured while storing file detail in db", e);
		} catch (FileUploadException e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Error occured while rejecting the transaction [" + trxValueIn.getStagingReferenceID() + "]");
			throw new CommandProcessingException("Error occured while rejecting the transaction", e);
		} catch (TrxParameterException e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Error occured while rejecting the transaction [" + trxValueIn.getStagingReferenceID() + "]");
			throw new CommandProcessingException("Error occured while rejecting the transaction", e);
		} catch (TransactionException e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Error occured while rejecting the transaction [" + trxValueIn.getStagingReferenceID() + "]");
			throw new CommandProcessingException("Error occured while rejecting the transaction", e);
		}
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
	
}
