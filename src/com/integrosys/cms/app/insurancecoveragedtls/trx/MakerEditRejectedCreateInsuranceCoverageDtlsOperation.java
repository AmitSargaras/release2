package com.integrosys.cms.app.insurancecoveragedtls.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.insurancecoveragedtls.IInsuranceCoverageDtls;

/**
 * @author dattatray.thorat
 *Maker Edit Rejected operation to   create rejected record by checker
 */
public class MakerEditRejectedCreateInsuranceCoverageDtlsOperation extends AbstractInsuranceCoverageDtlsTrxOperation{
    /**
        * Defaulc Constructor
        */
        public MakerEditRejectedCreateInsuranceCoverageDtlsOperation()
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
            return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_INSURANCE_COVERAGE_DTLS;
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
            IInsuranceCoverageDtlsTrxValue idxTrxValue = getInsuranceCoverageDtlsTrxValue(anITrxValue);
            IInsuranceCoverageDtls stage = idxTrxValue.getStagingInsuranceCoverageDtls();
            idxTrxValue.setStagingInsuranceCoverageDtls(stage);

            IInsuranceCoverageDtlsTrxValue trxValue = createStagingInsuranceCoverageDtls(idxTrxValue);
            trxValue = updateInsuranceCoverageDtlsTrx(trxValue);
            return super.prepareResult(trxValue);
        }

}
