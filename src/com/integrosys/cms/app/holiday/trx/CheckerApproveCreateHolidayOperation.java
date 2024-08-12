package com.integrosys.cms.app.holiday.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.holiday.bus.HolidayReplicationUtils;
import com.integrosys.cms.app.holiday.bus.IHoliday;

/**
 * @author abhijit.rudrakshawar
 */

public class CheckerApproveCreateHolidayOperation extends AbstractHolidayTrxOperation {
    public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_HOLIDAY;
    }

    /**
     * Process the transaction
     * 1.	Create the actual data
     * 2.	Update the transaction record
     *
     * @param anITrxValue of ITrxValue type
     * @return ITrxResult - the transaction result
     * @throws com.integrosys.base.businfra.transaction.TrxOperationException
     *          if encounters any error during the processing of the transaction
     */
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
        IHolidayTrxValue trxValue = getHolidayTrxValue(anITrxValue);
      try{
        trxValue = createActualHoliday(trxValue);
        trxValue = updateHolidayTrx(trxValue);
      }catch (TrxOperationException e) {
  		throw new TrxOperationException(e.getMessage());
  	}
      catch (Exception e) {
    	  throw new TrxOperationException(e.getMessage());
	}
       
        return super.prepareResult(trxValue);
    }


    /**
     * Create the actual property index
     *
     * @param anITrxValue of ITrxValue type
     * @return ICCDocumentLocationTrxValue - the document item trx value
     * @throws ConcurrentUpdateException 
     * @throws TransactionException 
     * @throws TrxParameterException 
     * @throws HolidayException 
     */
    private IHolidayTrxValue createActualHoliday(IHolidayTrxValue idxTrxValue) throws HolidayException, TrxParameterException, TransactionException, ConcurrentUpdateException {
        try {
            IHoliday staging = idxTrxValue.getStagingHoliday();
            // Replicating is necessary or else stale object error will arise
            IHoliday replicatedHoliday = HolidayReplicationUtils.replicateHolidayForCreateStagingCopy(staging);
            IHoliday actual = getHolidayBusManager().createHoliday(replicatedHoliday);
            idxTrxValue.setHoliday(actual);
            idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
            getHolidayBusManager().updateHoliday(actual);
            return idxTrxValue;
        }
        catch (HolidayException ex) {
            throw new TrxOperationException(ex);
        }
    }
}
