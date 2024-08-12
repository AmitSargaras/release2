package com.integrosys.cms.app.insurancecoverage.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;

/**
 * @author dattatray.thorat
 *Maker Edit Rejected operation to   create rejected record by checker
 */
public class MakerEditRejectedCreateInsuranceCoverageOperation extends AbstractInsuranceCoverageTrxOperation{
    /**
        * Defaulc Constructor
        */
        public MakerEditRejectedCreateInsuranceCoverageOperation()
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
            return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_INSURANCE_COVERAGE;
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
            IInsuranceCoverage stage = idxTrxValue.getStagingInsuranceCoverage();
            
            idxTrxValue.setStagingInsuranceCoverage(stage);

            IInsuranceCoverageTrxValue trxValue = createStagingInsuranceCoverage(idxTrxValue);
            trxValue = updateInsuranceCoverageTrx(trxValue);
            return super.prepareResult(trxValue);
        }

}
