package com.integrosys.cms.app.udf.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.udf.bus.IUdf;
import com.integrosys.cms.app.udf.bus.UdfReplicationUtils;

public class MakerSaveUpdateUdfOperation extends AbstractUdfTrxOperation  {

	/**
	 * Defaulc Constructor
	 */
	public MakerSaveUpdateUdfOperation() {
		super();
	}
	
	/**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_SAVE_UPDATE_UDF;
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
        IUdfTrxValue idxTrxValue = getUdfTrxValue(anITrxValue);
        IUdf stage = idxTrxValue.getStagingUdf();
        IUdf replicatedUdf = UdfReplicationUtils.replicateUdfForCreateStagingCopy(stage);
        idxTrxValue.setStagingUdf(replicatedUdf);
        IUdfTrxValue trxValue = createStagingUdf(idxTrxValue);
        trxValue = updateUdfTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
