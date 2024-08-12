package com.integrosys.cms.ui.mf.schemadetailsupload;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.mf.schemadetailsupload.proxy.ISchemadetailsUploadProxyManager;

public class MakerCloseFileUploadCommand extends AbstractCommand implements ISchemaDetailsUploadConstants{

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
        		{ SESSION_TRX_OBJ, OBTrxContext.class.getName(), FORM_SCOPE }
        };
    }
	
    public String[][] getResultDescriptor() {
        return new String[][]{
        	{ SESSION_TRX_VALUE_OUT, IFileUploadTrxValue.class.getName(),SERVICE_SCOPE }
        };
    }
    
    @Override
    public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException, AccessDeniedException {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
        IFileUploadTrxValue trxValueIn = (OBFileUploadTrxValue) map.get(SESSION_TRX_VALUE_OUT); 
        try {
        	OBTrxContext ctx = (OBTrxContext) map.get(SESSION_TRX_OBJ);
        	IFileUploadTrxValue trxValueOut = getMfSchemaDetailsuploadProxy().makerCloseRejectFileUpload(ctx, trxValueIn);
        	DefaultLogger.info(this, "User closed file upload record ["+ trxValueOut.getStagingReferenceID() +"]");
			
        	resultMap.put(SESSION_TRX_VALUE_OUT, trxValueOut);
		} catch (FileUploadException e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Error occured while closing the transaction ["+ trxValueIn.getStagingReferenceID() +"]");
			throw new CommandProcessingException("Error occured while closing the transaction", e);
		} catch (TrxParameterException e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Error occured while closing the transaction ["+ trxValueIn.getStagingReferenceID() +"]");
			throw new CommandProcessingException("Error occured while closing the transaction", e);
		} catch (TransactionException e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Error occured while closing the transaction ["+ trxValueIn.getStagingReferenceID() +"]");
			throw new CommandProcessingException("Error occured while closing the transaction", e);
		}
        
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        return returnMap;
    }

}