package com.integrosys.cms.app.holiday.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.holiday.bus.HolidayReplicationUtils;
import com.integrosys.cms.app.holiday.bus.IHoliday;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class MakerEditRejectedCreateHolidayOperation extends AbstractHolidayTrxOperation{
  /**
    * Defaulc Constructor
    */
    public MakerEditRejectedCreateHolidayOperation()
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
        return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_HOLIDAY;
    }

    /**
    * Process the transaction
    * 1. Create Staging record
    * 2. Update the transaction record
    * @param anITrxValue - ITrxValue
    * @return ITrxResult - the transaction result
    * @throws com.integrosys.base.businfra.transaction.TrxOperationException if encounters any error during the processing of the transaction
    */
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
    {
        IHolidayTrxValue idxTrxValue = getHolidayTrxValue(anITrxValue);
     /*   IHoliday stage = idxTrxValue.getStagingHoliday();
        IHoliday replicatedHoliday = HolidayReplicationUtils.replicateHolidayForCreateStagingCopy(stage);
        idxTrxValue.setStagingHoliday(replicatedHoliday);*/

        IHolidayTrxValue trxValue = createStagingHoliday(idxTrxValue);
        trxValue = updateHolidayTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
