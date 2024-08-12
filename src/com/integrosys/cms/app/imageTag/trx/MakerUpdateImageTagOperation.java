package com.integrosys.cms.app.imageTag.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.imageTag.bus.ImageTagReplicationUtils;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.ui.imageTag.ImageTagException;

/**
 * @author Anil Pandey
 * 
 * Will be used in untagging 
 *  
 */
public class MakerUpdateImageTagOperation extends AbstractImageTagTrxOperation {
    /**
     * Default Constructor
     */
    public MakerUpdateImageTagOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_UPDATE_IMAGE_TAG;
    }

    /**
     * Pre process.
     * Prepares the transaction object for persistance
     * Get the parent  transaction ID to be appended as trx parent ref
     *
     * @param anITrxValue is of type ITrxValue
     * @return ITrxValue
     * @throws com.integrosys.base.businfra.transaction.TrxOperationException
     *          on error
     */
    public ITrxValue preProcess(ITrxValue anITrxValue) throws TrxOperationException {
        anITrxValue = super.preProcess(anITrxValue);
        IImageTagTrxValue trxValue = getImageTagTrxValue(anITrxValue);
        try {
			IImageTagDetails imageTagById = getImageTagBusManager().getImageTagById(Long.parseLong(trxValue.getReferenceID()));
			trxValue.setImageTagDetails(imageTagById);
		} catch (ImageTagException e) {
			e.printStackTrace();
		} catch (TrxParameterException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (TransactionException e) {
			e.printStackTrace();
		}
        return trxValue;
    }

    /**
     * Process the transaction
     * 1.	Create the staging data
     * 2.	Update the transaction record
     *
     * @param anITrxValue of ITrxValue type
     * @return ITrxResult - the transaction result
     * @throws TrxOperationException if encounters any error during the processing of the transaction
     */
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
        IImageTagTrxValue idxTrxValue = getImageTagTrxValue(anITrxValue);
        IImageTagDetails stage = idxTrxValue.getStagingImageTagDetails();
        IImageTagDetails replicatedImageTagDetails = ImageTagReplicationUtils.replicateImageTagForCreateStagingCopy(stage);
        idxTrxValue.setStagingImageTagDetails(replicatedImageTagDetails);

        IImageTagTrxValue trxValue = createStagingImageTagDetails(idxTrxValue);
        trxValue = updateImageTagTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
