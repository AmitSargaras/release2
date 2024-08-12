package com.integrosys.cms.app.facilityNewMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author abhijit.rudrakshawar
 * Checker reject Operation to reject update made by maker
 */

public class CheckerEditRejectFacilityNewMasterOperation extends AbstractFacilityNewMasterTrxOperation{

    public CheckerEditRejectFacilityNewMasterOperation()
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
        return ICMSConstant.ACTION_CHECKER_REJECT_EDIT_FACILITY_NEW_MASTER;
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
        IFacilityNewMasterTrxValue trxValue = super.getFacilityNewMasterTrxValue(anITrxValue);
        trxValue = super.updateFacilityNewMasterTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
