package com.integrosys.cms.app.newtatmaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.newtatmaster.bus.INewTatMaster;
import com.integrosys.cms.app.newtatmaster.bus.TatMasterReplicationUtils;

public class MakerUpdateTatMasterOperation extends	AbstractTatMasterTrxOperation{
	

	
	public MakerUpdateTatMasterOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_UPDATE_TAT_MASTER;
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
        ITatMasterTrxValue trxValue = getTatMasterTrxValue(anITrxValue);
       
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
    	ITatMasterTrxValue idxTrxValue = getTatMasterTrxValue(anITrxValue);
    	INewTatMaster stage = idxTrxValue.getStagingtatMaster();
    	INewTatMaster replicatedtatMaster = TatMasterReplicationUtils.replicateTatMasterForCreateStagingCopy(stage);
        idxTrxValue.setStagingtatMaster(replicatedtatMaster);

        ITatMasterTrxValue trxValue = createStagingTatMaster(idxTrxValue);
        trxValue = updateTatMasterTrx(trxValue);
        return super.prepareResult(trxValue);
    }


	
	

}
