package com.integrosys.cms.app.collateralrocandinsurance.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.collateralrocandinsurance.bus.CollateralRocReplicationUtils;
import com.integrosys.cms.app.collateralrocandinsurance.bus.ICollateralRoc;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class MakerSaveUpdateCollateralRocOperation extends AbstractCollateralRocTrxOperation {

	/**
	 * Defaulc Constructor
	 */
	public MakerSaveUpdateCollateralRocOperation() {
		super();
	}
	/**
	* Get the operation name of the current operation
	*
	* @return String - the operation name of the current operation
	*/
	public String getOperationName()
	{
		return ICMSConstant.ACTION_MAKER_SAVE_UPDATE_COLLATERAL_ROC;
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
        ICollateralRocTrxValue idxTrxValue = getCollateralRocTrxValue(anITrxValue);
        ICollateralRoc stage = idxTrxValue.getStagingCollateralRoc();
        ICollateralRoc replicatedCollateralRoc = CollateralRocReplicationUtils.replicateCollateralRocForCreateStagingCopy(stage);
        idxTrxValue.setStagingCollateralRoc(replicatedCollateralRoc);

        ICollateralRocTrxValue trxValue = createStagingCollateralRoc(idxTrxValue);
        trxValue = updateCollateralRocTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
