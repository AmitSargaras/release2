package com.integrosys.cms.app.riskType.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.riskType.bus.IRiskType;
import com.integrosys.cms.app.riskType.bus.RiskTypeReplicationUtils;

public class MakerUpdateDraftCreateRiskTypeOperation extends AbstractRiskTypeTrxOperation  {

	/**
	 * Defaulc Constructor
	 */
	public MakerUpdateDraftCreateRiskTypeOperation() {
		super();
	}
	
	/**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_CREATE_RISK_TYPE;
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
        IRiskTypeTrxValue trxValue = getRiskTypeTrxValue(anITrxValue);
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
        IRiskTypeTrxValue idxTrxValue = getRiskTypeTrxValue(anITrxValue);
        IRiskType stage = idxTrxValue.getStagingRiskType();
        IRiskType replicatedRiskType = RiskTypeReplicationUtils.replicateRiskTypeForCreateStagingCopy(stage);
        idxTrxValue.setStagingRiskType(replicatedRiskType);
        IRiskTypeTrxValue trxValue = createStagingRiskType(idxTrxValue);
        trxValue = updateRiskTypeTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
