package com.integrosys.cms.app.insurancecoverage.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.insurancecoverage.bus.InsuranceCoverageReplicationUtils;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;

/**
 * @author dattatray.thorat
 * Maker Update operation to  update Insurance Coverage
 */
public class MakerUpdateInsuranceCoverageOperation extends AbstractInsuranceCoverageTrxOperation {
    /**
     * Defaulc Constructor
     */
    public MakerUpdateInsuranceCoverageOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_UPDATE_INSURANCE_COVERAGE;
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
        IInsuranceCoverageTrxValue trxValue = getInsuranceCoverageTrxValue(anITrxValue);
        IInsuranceCoverage staging = trxValue.getStagingInsuranceCoverage();
        try {
            if (staging != null) {
                

                if (staging.getId() != ICMSConstant.LONG_INVALID_VALUE) {
                	ICMSTrxValue parentTrx = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(staging.getId()), ICMSConstant.INSTANCE_INSURANCE_COVERAGE);
                    trxValue.setTrxReferenceID(parentTrx.getTransactionID());
                }
                
            }
            return trxValue;
        }
        
        catch (Exception ex) {
            throw new TrxOperationException("Exception in preProcess: " + ex.toString());
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
        IInsuranceCoverageTrxValue idxTrxValue = getInsuranceCoverageTrxValue(anITrxValue);
        IInsuranceCoverageTrxValue trxValue = createStagingInsuranceCoverage(idxTrxValue);
        trxValue = updateInsuranceCoverageTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
