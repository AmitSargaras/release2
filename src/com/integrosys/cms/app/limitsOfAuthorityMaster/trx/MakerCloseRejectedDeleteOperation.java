package com.integrosys.cms.app.limitsOfAuthorityMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.ILimitsOfAuthorityMaster;

public class MakerCloseRejectedDeleteOperation extends AbstractTrxOperation{

    private static final String DEFAULT_OPERATION_NAME = ICMSConstant.LimitsOfAuthorityMaster.ACTION_MAKER_CLOSE_REJECTED_DELETE_LIMITS_OF_AUTHORITY;

    private String operationName;

    public MakerCloseRejectedDeleteOperation()
    {
        operationName = DEFAULT_OPERATION_NAME;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
    {
        ILimitsOfAuthorityMasterTrxValue trxValue = super.getTrxValue (anITrxValue);
        ILimitsOfAuthorityMaster st = (ILimitsOfAuthorityMaster) trxValue.getStaging();
        trxValue = updateTrx(trxValue);
        return super.prepareResult(trxValue);
    }

}