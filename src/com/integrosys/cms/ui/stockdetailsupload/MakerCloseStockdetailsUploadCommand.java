package com.integrosys.cms.ui.stockdetailsupload;

import java.util.HashMap;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.stockdetailsupload.proxy.IStockdetailsUploadProxyManager;

public class MakerCloseStockdetailsUploadCommand extends AbstractCommand implements ICommonEventConstant, IStockDetailsUploadConstants{

private IStockdetailsUploadProxyManager stockdetailsuploadProxy;
	
	public IStockdetailsUploadProxyManager getStockdetailsuploadProxy() {
		return stockdetailsuploadProxy;
	}

	public void setStockdetailsuploadProxy(IStockdetailsUploadProxyManager stockdetailsuploadProxy) {
		this.stockdetailsuploadProxy = stockdetailsuploadProxy;
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
			{ SESSION_TRX_OBJ, OBTrxContext.class.getName(), FORM_SCOPE },
			{ SESSION_TRX_VALUE_OUT, IFileUploadTrxValue.class.getName(),SERVICE_SCOPE }
		};
	}
	
	public String[][] getResultDescriptor() {
		return new String[][] { 
			{ SESSION_TRX_VALUE_OUT, IFileUploadTrxValue.class.getName(),SERVICE_SCOPE }
		};
	}
	
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException, AccessDeniedException {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
        IFileUploadTrxValue trxValueIn = (OBFileUploadTrxValue) map.get(SESSION_TRX_VALUE_OUT); 
        try {
        	OBTrxContext ctx = (OBTrxContext) map.get(SESSION_TRX_OBJ);
        	IFileUploadTrxValue trxValueOut = getStockdetailsuploadProxy().makerCloseRejectFileUpload(ctx, trxValueIn);
			
        	resultMap.put(SESSION_TRX_VALUE_OUT, trxValueOut);
		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Error occured while closing the transaction",e);
			throw new CommandProcessingException("Error occured while closing the transaction", e);
		}
        
        returnMap.put(COMMAND_RESULT_MAP, resultMap);
        return returnMap;
    }
}
