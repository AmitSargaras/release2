package com.integrosys.cms.app.fileUpload.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class CheckerRejectFileUploadOperation extends AbstractFielUploadTrxOperation{
	
	 public CheckerRejectFileUploadOperation()
	    {
	        super();
	    }

	    /**
	    * Get the operation name of the current operation
	    *
	    * @return String - the operation name of the current operation
	    */
	    public String getOperationName()
	    {
	        return ICMSConstant.ACTION_CHECKER_REJECT_FILEUPLOAD;
	    }

	    /**
	    * Process the transaction
	    * 1.    Update the transaction record
	    * @param anITrxValue - ITrxValue
	    * @return ITrxResult - the transaction result
	    * @throws com.integrosys.base.businfra.transaction.TrxOperationException if encounters any error during the processing of the transaction
	    */
	    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
	    {
	    	IFileUploadTrxValue trxValue = super.getFileUploadTrxValue(anITrxValue);
	        trxValue = super.updateFileUploadTrx(trxValue);
	        return super.prepareResult(trxValue);
	    }

}
