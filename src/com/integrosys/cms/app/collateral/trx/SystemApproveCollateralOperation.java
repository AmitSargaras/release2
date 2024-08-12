package com.integrosys.cms.app.collateral.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Transaction operation for system approve submitted stp collateral master
 *
 * @author Andy Wong
 */
public class SystemApproveCollateralOperation extends AbstractCollateralTrxOperation {

    public String getOperationName() {
        return ICMSConstant.ACTION_SYSTEM_APPROVE_COL;
    }

    public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
        ICollateralTrxValue trxValue = (ICollateralTrxValue) value;

        trxValue = updateTransaction(trxValue);
        OBCMSTrxResult trxResult = new OBCMSTrxResult();
        trxResult.setTrxValue(trxValue);

        return trxResult;
    }
}