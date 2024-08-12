package com.integrosys.cms.app.goodsMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.goodsMaster.bus.IGoodsMaster;
import com.integrosys.cms.app.goodsMaster.bus.GoodsMasterReplicationUtils;

/**
 * @author Santosh.Sonmankar
 * Maker Update operation to  update FCCBranch 
 */
public class MakerUpdateGoodsMasterOperation extends AbstractGoodsMasterTrxOperation{
	 /**
     * Defaulc Constructor
     */
    public MakerUpdateGoodsMasterOperation() {
        super();
    }
    
    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_UPDATE_GOODS_MASTER;
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
        IGoodsMasterTrxValue trxValue = getGoodsMasterTrxValue(anITrxValue);
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
        IGoodsMasterTrxValue idxTrxValue = getGoodsMasterTrxValue(anITrxValue);
        IGoodsMaster stage = idxTrxValue.getStagingGoodsMaster();
        IGoodsMaster replicatedFCCBranch = GoodsMasterReplicationUtils.replicateGoodsMasterForCreateStagingCopy(stage);
        idxTrxValue.setStagingGoodsMaster(replicatedFCCBranch);
        IGoodsMasterTrxValue trxValue = createStagingGoodsMaster(idxTrxValue);
        trxValue = updateGoodsMasterTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
