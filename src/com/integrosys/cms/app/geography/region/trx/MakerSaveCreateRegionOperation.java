package com.integrosys.cms.app.geography.region.trx;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class MakerSaveCreateRegionOperation extends AbstractRegionTrxOperation{

	private static final long serialVersionUID = -6138935003644406544L;

	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_SAVE_REGION;
	}

	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		Validate.notNull(value, "transaction value must not be null");

		IRegionTrxValue trxValue = (IRegionTrxValue) value;
		trxValue = super.createStagingRegion(trxValue);

		if (StringUtils.isNotBlank(trxValue.getTransactionID())) {
			trxValue = super.updateTransaction(trxValue);
		}
		else {
			trxValue = super.createTransaction(trxValue);
		}

		return super.prepareResult(trxValue);
	}
}
