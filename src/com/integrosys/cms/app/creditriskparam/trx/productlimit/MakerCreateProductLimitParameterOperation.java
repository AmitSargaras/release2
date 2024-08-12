package com.integrosys.cms.app.creditriskparam.trx.productlimit;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Author: Priya
 * Date: Oct 9, 2009
 */

public class MakerCreateProductLimitParameterOperation extends AbstractProductLimitParameterTrxOperation {
	
	private static final long serialVersionUID = 1L;

	public MakerCreateProductLimitParameterOperation() {
        super();
    }

    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_CREATE_PRODUCT_LIMIT_PARAMETER;
    }

    public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
    	IProductLimitParameterTrxValue trxValue = super.getTrxValue(value);
    	DefaultLogger.debug (this, "ProductLimit >>>>> create Transaction: " + trxValue.getTransactionID());
    	
        trxValue = createStaging(trxValue);

        if (ICMSConstant.STATE_ND.equals(trxValue.getStatus())){
            trxValue = createTransaction(trxValue);
            DefaultLogger.debug (this, "ProductLimit >>>>> created Transaction id: " + trxValue.getTransactionID());
        }
        else{
            trxValue = updateTransaction(trxValue);
        }
        
        return prepareResult(trxValue);
    }

}
