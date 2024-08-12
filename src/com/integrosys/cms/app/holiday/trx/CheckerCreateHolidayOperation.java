package com.integrosys.cms.app.holiday.trx;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdx;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class CheckerCreateHolidayOperation extends AbstractHolidayTrxOperation {

   /**
    * Defaulc Constructor
    */
    public CheckerCreateHolidayOperation()
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
		return ICMSConstant.ACTION_CHECKER_FILE_MASTER;
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

        
	    trxValue = insertActualHoliday(trxValue);
		trxValue = createHoliday(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	* Create a property index transaction
	* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
	* @return ICCPropertyIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private IHolidayTrxValue createHoliday(IHolidayTrxValue anICCHolidayTrxValue) throws TrxOperationException, HolidayException
	{	
		try
		{
			String refTemp= anICCHolidayTrxValue.getStagingReferenceID();
			IHolidayTrxValue inHolidayTrxValue = prepareTrxValue(anICCHolidayTrxValue);
			
			inHolidayTrxValue.setFromState("PENDING_CREATE");
			inHolidayTrxValue.setTransactionType("HOLIDAY");
			inHolidayTrxValue.setToState("ACTIVE");
			inHolidayTrxValue.setStatus("ACTIVE");
			inHolidayTrxValue.setStagingReferenceID(refTemp);
			
			ICMSTrxValue trxValue = createTransaction(inHolidayTrxValue);
            OBHolidayTrxValue holidayTrxValue = new OBHolidayTrxValue (trxValue);
            holidayTrxValue.setHoliday (anICCHolidayTrxValue.getHoliday());
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
