package com.integrosys.cms.app.holiday.bus;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * @author abhijit.rudrakshawar
 * Bus Manager Implication for staging Holiday
 */
public class FileMapperIdBusManagerStagingImpl extends AbstractHolidayBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging holiday table  
     * 
     */
	
	public String getHolidayName() {
        return IHolidayDao.STAGE_FILE_MAPPER_ID;
    }


    
    public IHoliday updateToWorkingCopy(IHoliday workingCopy, IHoliday imageCopy)
            throws HolidayException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	


}