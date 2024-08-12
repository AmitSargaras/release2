package com.integrosys.cms.app.creditriskparam.trx.internallimit;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class MakerCancelUpdateInternalLimitParameterOperation extends AbstractInternalLimitParameterOperation {
	
	private static final long serialVersionUID = 1L;

	public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_CANCEL_INTERNAL_LIMIT;
    }

    public ITrxResult performProcess (ITrxValue value) throws TrxOperationException
    {
        try {
        	
        	IInternalLimitParameterTrxValue trxValue = super.getILParamTrxValue (value);

            trxValue.setStagingILPList(trxValue.getActualILPList());
            trxValue = super.createStagingILP (trxValue);
            trxValue = super.updateTransaction(trxValue);
        	
            return prepareResult(trxValue);
        }
        catch(TrxOperationException e) {
            throw e;
        }
        catch(Exception e) {
            throw new TrxOperationException("Caught Exception!", e);
        }
    }
}

