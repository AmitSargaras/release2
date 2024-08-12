package com.integrosys.cms.app.holiday.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * @author abhijit.rudrakshawar
 * Bus Manager Implication for staging Holiday
 */
public class HolidayBusManagerStagingImpl extends AbstractHolidayBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging Holiday table  
     * 
     */
	
	public String getHolidayName() {
        return IHolidayDao.STAGE_HOLIDAY_NAME;
    }

	/**
	 * This method returns exception as staging
	 *  holiday can never be working copy
	 */
    
    public IHoliday updateToWorkingCopy(IHoliday workingCopy, IHoliday imageCopy)
            throws HolidayException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	
	
	
	

	
	

}