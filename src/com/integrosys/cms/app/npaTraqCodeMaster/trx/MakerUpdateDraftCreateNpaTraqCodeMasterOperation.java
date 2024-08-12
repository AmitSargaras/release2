package com.integrosys.cms.app.npaTraqCodeMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.INpaTraqCodeMaster;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.NpaTraqCodeMasterReplicationUtils;

public class MakerUpdateDraftCreateNpaTraqCodeMasterOperation extends AbstractNpaTraqCodeMasterTrxOperation  {

	/**
	 * Defaulc Constructor
	 */
	public MakerUpdateDraftCreateNpaTraqCodeMasterOperation() {
		super();
	}
	
	/**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_CREATE_NPA_TRAQ_CODE_MASTER;
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
        INpaTraqCodeMasterTrxValue trxValue = getNpaTraqCodeMasterTrxValue(anITrxValue);
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
        INpaTraqCodeMasterTrxValue idxTrxValue = getNpaTraqCodeMasterTrxValue(anITrxValue);
        INpaTraqCodeMaster stage = idxTrxValue.getStagingNpaTraqCodeMaster();
        INpaTraqCodeMaster replicatedNpaTraqCodeMaster = NpaTraqCodeMasterReplicationUtils.replicateNpaTraqCodeMasterForCreateStagingCopy(stage);
        idxTrxValue.setStagingNpaTraqCodeMaster(replicatedNpaTraqCodeMaster);
        INpaTraqCodeMasterTrxValue trxValue = createStagingNpaTraqCodeMaster(idxTrxValue);
        trxValue = updateNpaTraqCodeMasterTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
