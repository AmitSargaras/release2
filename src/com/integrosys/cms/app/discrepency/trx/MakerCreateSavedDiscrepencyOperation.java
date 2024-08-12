package com.integrosys.cms.app.discrepency.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.discrepency.bus.DiscrepencyReplicationUtils;
import com.integrosys.cms.app.discrepency.bus.IDiscrepency;

public class MakerCreateSavedDiscrepencyOperation extends AbstractDiscrepencyTrxOperation{

	/**
     * Defaulc Constructor
     */
    public MakerCreateSavedDiscrepencyOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_CREATE_DISCREPENCY;
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
    	IDiscrepencyTrxValue idxTrxValue = getDiscrepencyTrxValue(anITrxValue);
    	IDiscrepency stage = idxTrxValue.getStagingDiscrepency();
    	IDiscrepency replicatedDiscrepency = DiscrepencyReplicationUtils.replicateDiscrepencyForCreateStagingCopy(stage);
        idxTrxValue.setStagingDiscrepency(replicatedDiscrepency);

        IDiscrepencyTrxValue trxValue = createStagingDiscrepency(idxTrxValue);
        trxValue = updateDiscrepencyTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
