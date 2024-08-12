package com.integrosys.cms.app.otherbank.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.holiday.trx.AbstractHolidayTrxOperation;
import com.integrosys.cms.app.holiday.trx.IHolidayTrxValue;

public class CheckerRejectInsertOtherBankMasterOperation extends AbstractOtherBankTrxOperation{

    public CheckerRejectInsertOtherBankMasterOperation()
    {
        super();
    }

    /**
    * Get the operation name of the current operation
    *
    * @return String - the operation name of the current operation
    */
    public String getOperationName()
    {
        return ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER;
    }

    /**
    * Process the transaction
    * 1.    Update the transaction record
    * @param anITrxValue - ITrxValue
    * @return ITrxResult - the transaction result
    * @throws com.integrosys.base.businfra.transaction.TrxOperationException if encounters any error during the processing of the transaction
    */
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
    {
        IOtherBankTrxValue trxValue = super.getOtherBankTrxValue(anITrxValue);
        trxValue = super.updateMasterInsertTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
