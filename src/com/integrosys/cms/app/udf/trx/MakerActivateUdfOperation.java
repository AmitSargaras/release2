package com.integrosys.cms.app.udf.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.udf.bus.IUdf;
import com.integrosys.cms.app.udf.bus.UdfReplicationUtils;
import com.integrosys.cms.app.udf.trx.AbstractUdfTrxOperation;
import com.integrosys.cms.app.udf.trx.IUdfTrxValue;

/**
 * @author uma.khot Maker Update operation to update party group
 */
public class MakerActivateUdfOperation extends
		AbstractUdfTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerActivateUdfOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_ACTIVATE_UDF;
	}

	/**
	 * Pre process. Prepares the transaction object for persistance Get the
	 * parent transaction ID to be appended as trx parent ref
	 * 
	 * @param anITrxValue
	 *            is of type ITrxValue
	 * @return ITrxValue
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException
	 *             on error
	 */
	public ITrxValue preProcess(ITrxValue anITrxValue)
			throws TrxOperationException {
		anITrxValue = super.preProcess(anITrxValue);
		IUdfTrxValue trxValue = getUdfTrxValue(anITrxValue);
		IUdf staging = trxValue.getStagingUdf();
		try {
			
			return trxValue;
		}

		catch (Exception ex) {
			throw new TrxOperationException("Exception in preProcess: "
					+ ex.toString());
		}
	}

	/**
	 * Process the transaction 1. Create the staging data 2. Update the
	 * transaction record
	 * 
	 * @param anITrxValue
	 *            of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException
	 *             if encounters any error during the processing of the
	 *             transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue)
			throws TrxOperationException {
		IUdfTrxValue idxTrxValue = getUdfTrxValue(anITrxValue);
		IUdf stage = idxTrxValue.getStagingUdf();
		IUdf replicatedUdf = UdfReplicationUtils
				.replicateUdfForCreateStagingCopy(stage);
		idxTrxValue.setStagingUdf(replicatedUdf);

		IUdfTrxValue trxValue = createStagingUdf(idxTrxValue);
		trxValue = updateUdfTrx(trxValue);
		return super.prepareResult(trxValue);
	}
}
