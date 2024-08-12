package com.integrosys.cms.app.collateralNewMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.collateralNewMaster.bus.CollateralNewMasterReplicationUtils;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMaster;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author abhijit.rudrakshawar
 * Maker Update operation to  update system 
 */
public class MakerUpdateCollateralNewMasterOperation extends AbstractCollateralNewMasterTrxOperation {
    /**
     * Defaulc Constructor
     */
    public MakerUpdateCollateralNewMasterOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_UPDATE_COLLATERAL_NEW_MASTER;
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
        ICollateralNewMasterTrxValue trxValue = getCollateralNewMasterTrxValue(anITrxValue);
        ICollateralNewMaster staging = trxValue.getStagingCollateralNewMaster();
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
        ICollateralNewMasterTrxValue idxTrxValue = getCollateralNewMasterTrxValue(anITrxValue);
        ICollateralNewMaster stage = idxTrxValue.getStagingCollateralNewMaster();
        ICollateralNewMaster replicatedCollateralNewMaster = CollateralNewMasterReplicationUtils.replicateCollateralNewMasterForCreateStagingCopy(stage);
     //   replicatedCollateralNewMaster.getSystemBankCode().setId(stage.getSystemBankCode().getId());
        idxTrxValue.setStagingCollateralNewMaster(replicatedCollateralNewMaster);

        ICollateralNewMasterTrxValue trxValue = createStagingCollateralNewMaster(idxTrxValue);
        trxValue = updateCollateralNewMasterTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
