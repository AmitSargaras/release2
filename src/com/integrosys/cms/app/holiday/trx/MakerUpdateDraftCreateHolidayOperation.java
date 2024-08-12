package com.integrosys.cms.app.holiday.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.holiday.bus.HolidayReplicationUtils;
import com.integrosys.cms.app.holiday.bus.IHoliday;

/**
 * @author abhijit.rudrakshawar
 * Maker Update operation to  update holiday 
 */
public class MakerUpdateDraftCreateHolidayOperation extends AbstractHolidayTrxOperation {
    /**
     * Defaulc Constructor
     */
    public MakerUpdateDraftCreateHolidayOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_CREATE_HOLIDAY;
    }

    /**
     * Pre process.
     * Prepares the transaction object for persistance
     * Get the parent  transaction ID to be appended as trx parent ref
     *
     * @param anITrxValue is of type ITrxValue
     * @return ITrxValue
     * @throws com.integrosys.base.businfra.transaction.TrxOperationException
     *          on error
     */
    public ITrxValue preProcess(ITrxValue anITrxValue) throws TrxOperationException {
        anITrxValue = super.preProcess(anITrxValue);
        IHolidayTrxValue trxValue = getHolidayTrxValue(anITrxValue);
       
            return trxValue;
    }

    /**
     * Process the transaction
     * 1.	Create the staging data
     * 2.	Update the transaction record
     *
     * @param anITrxValue of ITrxValue type
     * @return ITrxResult - the transaction result
     * @throws TrxOperationException if encounters any error during the processing of the transaction
     */
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
        IHolidayTrxValue idxTrxValue = getHolidayTrxValue(anITrxValue);
        IHoliday stage = idxTrxValue.getStagingHoliday();
        IHoliday replicatedHoliday = HolidayReplicationUtils.replicateHolidayForCreateStagingCopy(stage);
     //   replicatedHoliday.getSystemBankCode().setId(stage.getSystemBankCode().getId());
        idxTrxValue.setStagingHoliday(replicatedHoliday);

        IHolidayTrxValue trxValue = createStagingHoliday(idxTrxValue);
        trxValue = updateHolidayTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
