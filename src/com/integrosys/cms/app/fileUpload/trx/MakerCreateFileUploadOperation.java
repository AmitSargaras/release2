package com.integrosys.cms.app.fileUpload.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public class MakerCreateFileUploadOperation extends AbstractFielUploadTrxOperation{
	public MakerCreateFileUploadOperation()
    {
        super();
    }

	public String getOperationName()
	{
		return ICMSConstant.ACTION_MAKER_CREATE_FILEUPLOAD;
	}
	
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
	{
		IFileUploadTrxValue trxValue = super.getFileUploadTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStagingfileUpload()==null));

	    trxValue = createStagingFileUpload(trxValue);
		trxValue = createFileUploadTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
	
	private IFileUploadTrxValue createFileUploadTransaction(IFileUploadTrxValue anFielUploadTrxValue) throws TrxOperationException,ComponentException
	{
		try
		{
			anFielUploadTrxValue = prepareTrxValue(anFielUploadTrxValue);
			ICMSTrxValue trxValue = createTransaction(anFielUploadTrxValue);
			OBFileUploadTrxValue fileUploadTrxValue = new OBFileUploadTrxValue (trxValue);
			fileUploadTrxValue.setStagingfileUpload (anFielUploadTrxValue.getStagingfileUpload());
			fileUploadTrxValue.setFileUpload(anFielUploadTrxValue.getFileUpload());
	        return fileUploadTrxValue;
		}
		catch(FileUploadException se)
		{
			throw new FileUploadException("Error in Create File Upload Operation ");
		}
		catch(TransactionException ex)
		{
			throw new TrxOperationException(ex);
		}
		catch(Exception ex)
		{
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}
}
