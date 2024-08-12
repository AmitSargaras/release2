package com.integrosys.cms.app.baselmaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.baselmaster.bus.BaselReplicationUtils;
import com.integrosys.cms.app.baselmaster.bus.IBaselMaster;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.component.bus.ComponentReplicationUtils;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;

public class MakerUpdateBaselOperation extends AbstractBaselTrxOperation{
	
	public MakerUpdateBaselOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_UPDATE_BASEL;
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
        IBaselMasterTrxValue trxValue = getBaselTrxValue(anITrxValue);
       
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
    	IBaselMasterTrxValue idxTrxValue = getBaselTrxValue(anITrxValue);
    	IBaselMaster stage = idxTrxValue.getStagingBaselMaster();
    	IBaselMaster replicatedBasel = BaselReplicationUtils.replicateBaselForCreateStagingCopy(stage);
     //   replicatedComponent.getSystemBankCode().setId(stage.getSystemBankCode().getId());
        idxTrxValue.setStagingBaselMaster(replicatedBasel);

        IBaselMasterTrxValue trxValue = createStagingBasel(idxTrxValue);
        trxValue = updateBaselTrx(trxValue);
        return super.prepareResult(trxValue);
    }

}
