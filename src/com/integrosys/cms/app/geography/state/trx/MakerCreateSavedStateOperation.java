package com.integrosys.cms.app.geography.state.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.bus.StateReplicationUtils;

public class MakerCreateSavedStateOperation extends AbstractStateTrxOperation{

	/**
     * Defaulc Constructor
     */
    public MakerCreateSavedStateOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_CREATE_STATE;
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
    	IStateTrxValue idxTrxValue = getStateTrxValue(anITrxValue);
    	IState stage = idxTrxValue.getStagingState();
    	IState replicatedState = StateReplicationUtils.replicateStateForCreateStagingCopy(stage);
        idxTrxValue.setStagingState(replicatedState);

        IStateTrxValue trxValue = createStagingState(idxTrxValue);
        trxValue = updateStateTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
