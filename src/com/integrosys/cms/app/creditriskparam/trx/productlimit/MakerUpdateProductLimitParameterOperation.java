package com.integrosys.cms.app.creditriskparam.trx.productlimit;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;

/**
 * Author: Priya
 * Date: Oct 9, 2009
 */
public class MakerUpdateProductLimitParameterOperation extends AbstractProductLimitParameterTrxOperation {
	
	private static final long serialVersionUID = 1L;

    public MakerUpdateProductLimitParameterOperation() {
    }

    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_UPDATE_SECTOR_LIMIT_PARAMETER;
    }

    public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
        IProductLimitParameterTrxValue trxValue = super.getTrxValue(value);
        trxValue = createStaging(trxValue);
        trxValue = updateTransaction(trxValue);
        return prepareResult(trxValue);
    }
}
