package com.integrosys.cms.app.creditApproval.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalReplicationUtils;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;

/**
 * @author Govind.Sahu
 * Maker Update operation to  update system 
 */
public class MakerUpdateDraftCreateCreditApprovalOperation extends AbstractCreditApprovalTrxOperation {
    /**
     * Defaulc Constructor
     */
    public MakerUpdateDraftCreateCreditApprovalOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_CREATE_CREDIT_APPROVAL;
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
        ICreditApprovalTrxValue trxValue = getCreditApprovalTrxValue(anITrxValue);
        ICreditApproval staging = trxValue.getStagingCreditApproval();
        try {
//            if (staging != null) {
//                
//
//                if (staging.getId() != ICMSConstant.LONG_INVALID_VALUE) {
//                	ICMSTrxValue parentTrx = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(staging.getId()), ICMSConstant.INSTANCE_SYSTEM_BANK_BRANCH);
//                    trxValue.setTrxReferenceID(parentTrx.getTransactionID());
//                }
//                
//            }else{
//            	throw new TrxOperationException("Staging value is null.");
//            }
            return trxValue;
        }
        
        catch (Exception ex) {
            throw new TrxOperationException("Exception in preProcess: " + ex.getMessage());
        }
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
        ICreditApprovalTrxValue idxTrxValue = getCreditApprovalTrxValue(anITrxValue);
        ICreditApproval stage = idxTrxValue.getStagingCreditApproval();
        ICreditApproval replicatedCreditApproval = CreditApprovalReplicationUtils.replicateCreditApprovalForCreateStagingCopy(stage);
        idxTrxValue.setStagingCreditApproval(replicatedCreditApproval);

        ICreditApprovalTrxValue trxValue = createStagingCreditApproval(idxTrxValue);
        trxValue = updateCreditApprovalTransaction(trxValue);
        return super.prepareResult(trxValue);
    }
}
