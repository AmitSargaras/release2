package com.integrosys.cms.app.limit.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Transaction operation for system update submitted stp facility master
 * reuse for delete operation
 *
 * @author Andy Wong
 */
public class SystemUpdateFacilityOperation extends AbstractFacilityTrxOperation {

    private static final String DEFAULT_OPERATION_NAME = FacilityTrxController.ACTION_SYSTEM_UPDATE;

    private String operationName;

    public SystemUpdateFacilityOperation() {
        operationName = DEFAULT_OPERATION_NAME;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
        IFacilityTrxValue trxValue = (IFacilityTrxValue) value;

        trxValue = updateTransaction(trxValue);
        OBCMSTrxResult trxResult = new OBCMSTrxResult();
        trxResult.setTrxValue(trxValue);

        return trxResult;
    }
}