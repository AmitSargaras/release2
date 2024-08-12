package com.integrosys.cms.app.geography.city.trx;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public class MakerCreateCityOperation extends AbstractCityTrxOperation{
	
	private static final long serialVersionUID = -6138935003644406544L;

	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CREATE_CITY;
	}

	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		Validate.notNull(value, "transaction value must not be null");

		ICityTrxValue trxValue = (ICityTrxValue) value;
		trxValue = super.createStagingCity(trxValue);

		if (StringUtils.isNotBlank(trxValue.getTransactionID())) {
			trxValue = super.updateTransaction(trxValue);
		}
		else {
			trxValue = super.createTransaction(trxValue);
		}

		return super.prepareResult(trxValue);
	}

}
