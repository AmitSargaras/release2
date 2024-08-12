package com.integrosys.cms.app.creditriskparam.trx.sectorlimit;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;

/**
 * Author: Syukri
 * Date: Jun 17, 2008
 */
public class CheckerApproveSectorLimitParameterOperation extends AbstractSectorLimitParameterTrxOperation {
	
	private static final long serialVersionUID = 1L;

    public CheckerApproveSectorLimitParameterOperation() {
    }

    public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_SECTOR_LIMIT_PARAMETER;
    }

    public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
        ISectorLimitParameterTrxValue trxValue = super.getTrxValue(value);

        if (trxValue.getActualMainSectorLimitParameter() == null)
            trxValue = createActual(trxValue);
        else
            trxValue = updateActual(trxValue);
        
        trxValue = updateTransaction(trxValue);
        return prepareResult(trxValue);
    }

}
