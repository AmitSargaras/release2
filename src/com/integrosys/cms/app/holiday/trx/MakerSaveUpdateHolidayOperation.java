package com.integrosys.cms.app.holiday.trx;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdx;
import com.integrosys.cms.app.holiday.bus.IHoliday;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.holiday.bus.HolidayReplicationUtils;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class MakerSaveUpdateHolidayOperation extends AbstractHolidayTrxOperation {

   /**
    * Defaulc Constructor
    */
    public MakerSaveUpdateHolidayOperation()
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
		return ICMSConstant.ACTION_MAKER_SAVE_UPDATE_HOLIDAY;
	}


	/**
	* Process the transaction
	* 1.	Create the staging data
	* 2.	Create the transaction record
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
