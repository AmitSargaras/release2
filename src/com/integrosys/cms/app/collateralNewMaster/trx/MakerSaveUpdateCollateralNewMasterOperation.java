package com.integrosys.cms.app.collateralNewMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.collateralNewMaster.bus.CollateralNewMasterReplicationUtils;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMaster;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class MakerSaveUpdateCollateralNewMasterOperation extends AbstractCollateralNewMasterTrxOperation {

   /**
    * Defaulc Constructor
    */
    public MakerSaveUpdateCollateralNewMasterOperation()
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
		return ICMSConstant.ACTION_MAKER_SAVE_UPDATE_COLLATERAL_NEW_MASTER;
	}


	/**
	* Process the transaction
	* 1.	Create the staging data
	* 2.	Create the transaction record
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
