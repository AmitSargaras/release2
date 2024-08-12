package com.integrosys.cms.app.valuationAgency.bus;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.holiday.bus.AbstractHolidayBusManager;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.holiday.bus.IHoliday;
import com.integrosys.cms.app.holiday.bus.IHolidayDao;
import com.integrosys.cms.app.valuationAgency.bus.AbstractValuationAgencyBusManager;
import com.integrosys.cms.app.valuationAgency.bus.IValuationAgency;
import com.integrosys.cms.app.valuationAgency.bus.IValuationAgencyDao;
import com.integrosys.cms.app.valuationAgency.bus.ValuationAgencyException;


public class FileMapperIdBusManagerStagingImpl extends AbstractValuationAgencyBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging holiday table  
     * 
     */
	
	public String getValuationAgencyName() {
        return IValuationAgencyDao.STAGE_FILE_MAPPER_ID;
    }


    
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