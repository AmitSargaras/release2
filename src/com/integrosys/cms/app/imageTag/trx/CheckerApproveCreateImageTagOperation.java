package com.integrosys.cms.app.imageTag.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.imageTag.bus.ImageTagReplicationUtils;
import com.integrosys.cms.ui.imageTag.ImageTagException;

/**
 * @author abhijit.rudrakshawar
 */

public class CheckerApproveCreateImageTagOperation extends AbstractImageTagTrxOperation {
    public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_IMAGE_TAG;
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
        IImageTagTrxValue trxValue = getImageTagTrxValue(anITrxValue);
      try{
        trxValue = createActualImageTag(trxValue);
      }catch (Exception e) {
		e.printStackTrace();
	}
        trxValue = updateImageTagTrx(trxValue);
        return super.prepareResult(trxValue);
    }


    /**
     * Create the actual Image Tag
     *
     * @param anITrxValue of ITrxValue type
     * @return ICCDocumentLocationTrxValue - the document item trx value
     * @throws ConcurrentUpdateException 
     * @throws TransactionException 
     * @throws TrxParameterException 
     * @throws ImageTagException 
     */
    private IImageTagTrxValue createActualImageTag(IImageTagTrxValue idxTrxValue) throws ImageTagException, TrxParameterException, TransactionException, ConcurrentUpdateException {
        try {
            IImageTagDetails staging = idxTrxValue.getStagingImageTagDetails();
            IImageTagDetails replicatedImageTag = ImageTagReplicationUtils.replicateImageTagForCreateStagingCopy(staging);
            IImageTagDetails actual = getImageTagBusManager().createImageTagDetail((replicatedImageTag));
            idxTrxValue.setImageTagDetails((actual));
            idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
            getImageTagBusManager().updateImageTag(actual);
            return idxTrxValue;
        }
        catch (ImageTagException ex) {
            throw new TrxOperationException(ex);
        }
    }
}
