package com.integrosys.cms.app.insurancecoverage.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author dattatray.thorat
 * Maker Update operation to  update Relationship Manager 
 */
public class MakerUpdateSaveInsuranceCoverageOperation extends AbstractInsuranceCoverageTrxOperation {
    /**
     * Defaulc Constructor
     */
    public MakerUpdateSaveInsuranceCoverageOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
     public String getOperationName()
     {
         return ICMSConstant.ACTION_MAKER_UPDATE_SAVE_INSURANCE_COVERAGE;
     }

     /**
     * Process the transaction
     * 1.    Create Staging record
     * 2.    Update the transaction record
     * @param anITrxValue - ITrxValue
     * @return ITrxResult - the transaction result
     * @throws com.integrosys.base.businfra.transaction.TrxOperationException if encounters any error during the processing of the transaction
     */
     public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
     {
         IInsuranceCoverageTrxValue idxTrxValue = getInsuranceCoverageTrxValue(anITrxValue);
         IInsuranceCoverageTrxValue trxValue = createStagingInsuranceCoverage(idxTrxValue);
         trxValue = updateInsuranceCoverageTrx(trxValue);
         return super.prepareResult(trxValue);
     }
}
