package com.integrosys.cms.app.fileUpload.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.fileUpload.bus.FileUploadReplicationUtils;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;

public class CheckerApproveCreateFileUploadOperation extends AbstractFielUploadTrxOperation{
	
	  public String getOperationName() {
	        return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_FILEUPLOAD;
	    }

	    /**
	     * Process the transaction
	     * 1.	Create the actual data
	     * 2.	Update the transaction record
	     *
	     * @param anITrxValue of ITrxValue type
	     * @return ITrxResult - the transaction result
	     * @throws com.integrosys.base.businfra.transaction.TrxOperationException
	     *          if encounters any error during the processing of the transaction
	     */
	    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
	    	IFileUploadTrxValue trxValue = getFileUploadTrxValue(anITrxValue);
	      try{
	        trxValue = createActualFileUpload(trxValue);
	        trxValue = updateFileUploadTrx(trxValue);
	      }catch (TrxOperationException e) {
	  		throw new TrxOperationException(e.getMessage());
	  	}
	      catch (Exception e) {
	    	  throw new TrxOperationException(e.getMessage());
		}
	       
	        return super.prepareResult(trxValue);
	    }


	    /**
	     * Create the actual property index
	     *
	     * @param anITrxValue of ITrxValue type
	     * @return ICCDocumentLocationTrxValue - the document item trx value
	     * @throws ConcurrentUpdateException 
	     * @throws TransactionException 
	     * @throws TrxParameterException 
	     * @throws ComponentException 
	     */
	    private IFileUploadTrxValue createActualFileUpload(IFileUploadTrxValue idxTrxValue) throws ComponentException, TrxParameterException, TransactionException, ConcurrentUpdateException {
	        try {
	        	IFileUpload staging = idxTrxValue.getStagingfileUpload();
	            // Replicating is necessary or else stale object error will arise
	        	IFileUpload replicatedFile = FileUploadReplicationUtils.replicateFileForCreateStagingCopy(staging);
	        	IFileUpload actual = getFileUploadBusManager().createFileUpload(replicatedFile);
	            idxTrxValue.setFileUpload(actual);
	            idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
	            getFileUploadBusManager().updateFileUpload(actual);
	            return idxTrxValue;
	        }
	        catch (ComponentException ex) {
	            throw new TrxOperationException(ex);
	        }
	    }

}
