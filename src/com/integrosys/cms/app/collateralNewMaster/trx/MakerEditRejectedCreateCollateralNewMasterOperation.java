package com.integrosys.cms.app.collateralNewMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.collateralNewMaster.bus.CollateralNewMasterReplicationUtils;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMaster;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class MakerEditRejectedCreateCollateralNewMasterOperation extends AbstractCollateralNewMasterTrxOperation{
  /**
    * Defaulc Constructor
    */
    public MakerEditRejectedCreateCollateralNewMasterOperation()
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
        return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_COLLATERAL_NEW_MASTER;
    }

    /**
    * Process the transaction
    * 1. Create Staging record
    * 2. Update the transaction record
    * @param anITrxValue - ITrxValue
    * @return ITrxResult - the transaction result
    * @throws com.integrosys.base.businfra.transaction.TrxOperationException if encounters any error during the processing of the transaction
    */
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
    {
        ICollateralNewMasterTrxValue idxTrxValue = getCollateralNewMasterTrxValue(anITrxValue);
        ICollateralNewMaster stage = idxTrxValue.getStagingCollateralNewMaster();
        ICollateralNewMaster replicatedCollateralNewMaster = CollateralNewMasterReplicationUtils.replicateCollateralNewMasterForCreateStagingCopy(stage);
        idxTrxValue.setStagingCollateralNewMaster(replicatedCollateralNewMaster);

        ICollateralNewMasterTrxValue trxValue = createStagingCollateralNewMaster(idxTrxValue);
        trxValue = updateCollateralNewMasterTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
