package com.integrosys.cms.app.riskType.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.riskType.bus.IRiskType;

/**
 * @author dattatray.thorat
 *Maker Edit Rejected operation to   delete rejected record by checker
 */
public class MakerEditRejectedDeleteRiskTypeOperation extends AbstractRiskTypeTrxOperation{
    /**
        * Defaulc Constructor
        */
        public MakerEditRejectedDeleteRiskTypeOperation()
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
            return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_DELETE_RISK_TYPE;
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
        	IRiskTypeTrxValue idxTrxValue = getRiskTypeTrxValue(anITrxValue);
            IRiskType stage = idxTrxValue.getStagingRiskType();
            idxTrxValue.setStagingRiskType(stage);

            IRiskTypeTrxValue trxValue = createStagingRiskType(idxTrxValue);
            trxValue = updateRiskTypeTrx(trxValue);
            return super.prepareResult(trxValue);
        }

}
