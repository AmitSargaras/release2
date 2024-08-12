package com.integrosys.cms.app.holiday.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class MakerSaveHolidayOperation extends AbstractHolidayTrxOperation {

   /**
    * Defaulc Constructor
    */
    public MakerSaveHolidayOperation()
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
		return ICMSConstant.ACTION_MAKER_SAVE_HOLIDAY;
	}


	/**
	* Process the transaction
	* 1.	Create the staging data
	* 2.	Create the transaction record
	* @param anITrxValue of ITrxValue type
	* @return ITrxResult - the transaction result
	* @throws TrxOperationException if encounters any error during the processing of the transaction
	*/
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
	{
	    IHolidayTrxValue trxValue = super.getHolidayTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStagingHoliday()==null));

	    trxValue = createStagingHoliday(trxValue);
		trxValue = createHolidayTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	* Create a property index transaction
	* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
	* @return ICCPropertyIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private IHolidayTrxValue createHolidayTransaction(IHolidayTrxValue anICCHolidayTrxValue) throws TrxOperationException,HolidayException
	{
		try
		{
            anICCHolidayTrxValue = prepareTrxValue(anICCHolidayTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCHolidayTrxValue);
            OBHolidayTrxValue holidayTrxValue = new OBHolidayTrxValue (trxValue);
            holidayTrxValue.setStagingHoliday (anICCHolidayTrxValue.getStagingHoliday());
            holidayTrxValue.setHoliday(anICCHolidayTrxValue.getHoliday());
	        return holidayTrxValue;
		}
		catch(HolidayException se)
		{
			throw new HolidayException("Error in Create Holiday Operation ");
		}
		catch(TransactionException ex)
		{
			throw new TrxOperationException(ex);
		}
		catch(Exception ex)
		{
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

}
