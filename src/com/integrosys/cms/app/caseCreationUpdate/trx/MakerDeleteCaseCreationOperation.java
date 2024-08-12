package com.integrosys.cms.app.caseCreationUpdate.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.caseCreationUpdate.bus.ICaseCreation;
import com.integrosys.cms.app.caseCreationUpdate.bus.CaseCreationReplicationUtils;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * @author abhijit.rudrakshawar
 * Maker Update operation to  update caseCreationUpdate 
 */
public class MakerDeleteCaseCreationOperation extends AbstractCaseCreationTrxOperation {
    /**
     * Defaulc Constructor
     */
    public MakerDeleteCaseCreationOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_DELETE_CASECREATION;
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
        ICaseCreationTrxValue trxValue = getCaseCreationTrxValue(anITrxValue);
        ICaseCreation staging = trxValue.getStagingCaseCreation();
        try {
            if (staging != null) {
                

                if (staging.getId() != ICMSConstant.LONG_INVALID_VALUE) {
                	ICMSTrxValue parentTrx = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(staging.getId()), ICMSConstant.INSTANCE_CASECREATION);
                    trxValue.setTrxReferenceID(parentTrx.getTransactionID());
                }
                
            }else{
            	throw new TrxOperationException("Staging Value is null");
            }
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
        ICaseCreationTrxValue idxTrxValue = getCaseCreationTrxValue(anITrxValue);
        ICaseCreation stage = idxTrxValue.getStagingCaseCreation();
        ICaseCreation replicatedCaseCreation = CaseCreationReplicationUtils.replicateCaseCreationForCreateStagingCopy(stage);
     //   replicatedCaseCreation.getSystemBankCode().setId(stage.getSystemBankCode().getId());
        idxTrxValue.setStagingCaseCreation(replicatedCaseCreation);

        ICaseCreationTrxValue trxValue = createStagingCaseCreation(idxTrxValue);
        trxValue = updateCaseCreationTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
