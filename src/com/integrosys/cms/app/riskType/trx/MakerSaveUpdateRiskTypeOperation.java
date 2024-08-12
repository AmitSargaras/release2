package com.integrosys.cms.app.riskType.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.riskType.bus.IRiskType;
import com.integrosys.cms.app.riskType.bus.RiskTypeReplicationUtils;

public class MakerSaveUpdateRiskTypeOperation extends AbstractRiskTypeTrxOperation  {

	/**
	 * Defaulc Constructor
	 */
	public MakerSaveUpdateRiskTypeOperation() {
		super();
	}
	
	/**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_SAVE_UPDATE_RISK_TYPE;
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
        IRiskTypeTrxValue idxTrxValue = getRiskTypeTrxValue(anITrxValue);
        IRiskType stage = idxTrxValue.getStagingRiskType();
        IRiskType replicatedRiskType = RiskTypeReplicationUtils.replicateRiskTypeForCreateStagingCopy(stage);
        idxTrxValue.setStagingRiskType(replicatedRiskType);
        IRiskTypeTrxValue trxValue = createStagingRiskType(idxTrxValue);
        trxValue = updateRiskTypeTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
