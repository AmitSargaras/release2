package com.integrosys.cms.app.creditriskparam.trx.productlimit;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;

/**
 * Author: Priya
 * Date: Oct 9, 2009
 */
public class CheckerApproveProductLimitParameterOperation extends AbstractProductLimitParameterTrxOperation {
	
	private static final long serialVersionUID = 1L;

    public CheckerApproveProductLimitParameterOperation() {
    }

    public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_PRODUCT_LIMIT_PARAMETER;
    }

    public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
        IProductLimitParameterTrxValue trxValue = super.getTrxValue(value);

        if (trxValue.getActualProductProgramLimitParameter() == null )
            trxValue = createActual(trxValue);
        else
            trxValue = updateActual(trxValue);
        
        trxValue = updateTransaction(trxValue);
        return prepareResult(trxValue);
    }

}
