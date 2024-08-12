package com.integrosys.cms.app.collateral.trx;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Transaction Operation to be invoked to create collateral, create transaction.
 * 
 * @author Chong Jun Yong
 * 
 */
public class MakerCreateCollateralOperation extends AbstractCollateralTrxOperation {

	private static final long serialVersionUID = -6138935003644406544L;

	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CREATE_COL;
	}

	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		Validate.notNull(value, "transaction value must not be null");

		ICollateralTrxValue trxValue = (ICollateralTrxValue) value;
		trxValue = super.createStagingCollateral(trxValue);

		if (StringUtils.isNotBlank(trxValue.getTransactionID())) {
			trxValue = super.updateTransaction(trxValue);
		}
		else {
			trxValue = super.createTransaction(trxValue);
		}

		return super.prepareResult(trxValue);
	}
}
