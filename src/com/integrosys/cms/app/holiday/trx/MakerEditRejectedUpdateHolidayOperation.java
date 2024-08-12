package com.integrosys.cms.app.holiday.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.holiday.bus.IHoliday;
import com.integrosys.cms.app.holiday.bus.OBHoliday;

/**
 * @author abhijit.rudrakshawar
 *Maker Edit Rejected operation to   update rejected record by checker
 */
public class MakerEditRejectedUpdateHolidayOperation extends AbstractHolidayTrxOperation{
    /**
        * Defaulc Constructor
        */
        public MakerEditRejectedUpdateHolidayOperation()
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
            return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_HOLIDAY;
        }

        /**
        * Process the transaction
        * 1.    Create Staging record
        * 2.    Update the transaction record
        * @param anITrxValue - ITrxValue
        * @return ITrxResult - the transaction result
        * @throws com.integrosys.base.businfra.transaction.TrxOperationException if encounters any error during the processing of the transaction
        */
        public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
        {
            IHolidayTrxValue idxTrxValue = getHolidayTrxValue(anITrxValue);
            IHoliday stage = idxTrxValue.getStagingHoliday();
            IHoliday replicatedHoliday= new OBHoliday();
            replicatedHoliday.setId(stage.getId());
            replicatedHoliday.setDescription(stage.getDescription());
            replicatedHoliday.setStartDate(stage.getStartDate());
            replicatedHoliday.setEndDate(stage.getEndDate());
            replicatedHoliday.setCreateBy(stage.getCreateBy());
            replicatedHoliday.setCreationDate(stage.getCreationDate());
            replicatedHoliday.setDeprecated(stage.getDeprecated());
            replicatedHoliday.setLastUpdateBy(stage.getLastUpdateBy());
            replicatedHoliday.setLastUpdateDate(stage.getLastUpdateDate());
            
            replicatedHoliday.setStatus(stage.getStatus());
            replicatedHoliday.setVersionTime(stage.getVersionTime());
            
            idxTrxValue.setStagingHoliday(replicatedHoliday);

            IHolidayTrxValue trxValue = createStagingHoliday(idxTrxValue);
            trxValue = updateHolidayTrx(trxValue);
            return super.prepareResult(trxValue);
        }

}
