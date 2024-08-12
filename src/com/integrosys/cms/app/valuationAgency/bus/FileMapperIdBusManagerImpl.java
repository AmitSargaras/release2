package com.integrosys.cms.app.valuationAgency.bus;

import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.holiday.bus.AbstractHolidayBusManager;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.holiday.bus.IHoliday;
import com.integrosys.cms.app.holiday.bus.IHolidayDao;

public class FileMapperIdBusManagerImpl extends AbstractValuationAgencyBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging holiday table  
     * 
     */
	
	public String getValuationAgencyName() {
        return IValuationAgencyDao.ACTUAL_STAGE_FILE_MAPPER_ID;
    }

	/**
	 * This method returns exception as staging
	 *  holiday can never be working copy
	 */
    
    public IValuationAgency updateToWorkingCopy(IValuationAgency workingCopy, IValuationAgency imageCopy)
            throws ValuationAgencyException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }


	public List getAllValuationAgency() {
		// TODO Auto-generated method stub
		return null;
	}

	public List getFilteredValuationAgency(String code, String name) {
		// TODO Auto-generated method stub
		return null;
	}


}