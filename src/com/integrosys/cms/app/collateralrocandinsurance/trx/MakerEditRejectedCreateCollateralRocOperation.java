package com.integrosys.cms.app.collateralrocandinsurance.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.collateralrocandinsurance.bus.CollateralRocReplicationUtils;
import com.integrosys.cms.app.collateralrocandinsurance.bus.ICollateralRoc;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class MakerEditRejectedCreateCollateralRocOperation extends AbstractCollateralRocTrxOperation {

	/**
	 * Defaulc Constructor
	 */
	public MakerEditRejectedCreateCollateralRocOperation() {
		super();
	}
	/**
	 * Get the operation name of the current operation
	 *
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_COLLATERAL_ROC;
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
	        ICollateralRocTrxValue idxTrxValue = getCollateralRocTrxValue(anITrxValue);
	        ICollateralRoc stage = idxTrxValue.getStagingCollateralRoc();
	        ICollateralRoc replicatedCollateralRoc = CollateralRocReplicationUtils.replicateCollateralRocForCreateStagingCopy(stage);
	        idxTrxValue.setStagingCollateralRoc(replicatedCollateralRoc);

	        ICollateralRocTrxValue trxValue = createStagingCollateralRoc(idxTrxValue);
	        trxValue = updateCollateralRocTrx(trxValue);
	        return super.prepareResult(trxValue);
	    }
}
