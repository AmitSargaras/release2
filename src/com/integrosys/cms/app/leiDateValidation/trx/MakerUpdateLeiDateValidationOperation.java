package com.integrosys.cms.app.leiDateValidation.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.leiDateValidation.bus.ILeiDateValidation;
import com.integrosys.cms.app.leiDateValidation.bus.LeiDateValidationReplicationUtils;
import com.integrosys.cms.app.leiDateValidation.trx.AbstractLeiDateValidationTrxOperation;
import com.integrosys.cms.app.leiDateValidation.trx.ILeiDateValidationTrxValue;

public class MakerUpdateLeiDateValidationOperation extends AbstractLeiDateValidationTrxOperation{
	 /**
     * Defaulc Constructor
     */
    public MakerUpdateLeiDateValidationOperation() {
        super();
    }
    
    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_UPDATE_LEI_DATE_VALIDATION;
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
        ILeiDateValidationTrxValue trxValue = getLeiDateValidationTrxValue(anITrxValue);
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
        ILeiDateValidationTrxValue idxTrxValue = getLeiDateValidationTrxValue(anITrxValue);
        ILeiDateValidation stage = idxTrxValue.getStagingLeiDateValidation();
        ILeiDateValidation replicatedFCCBranch = LeiDateValidationReplicationUtils.replicateLeiDateValidationForCreateStagingCopy(stage);
        idxTrxValue.setStagingLeiDateValidation(replicatedFCCBranch);
        ILeiDateValidationTrxValue trxValue = createStagingLeiDateValidation(idxTrxValue);
        trxValue = updateLeiDateValidationTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
