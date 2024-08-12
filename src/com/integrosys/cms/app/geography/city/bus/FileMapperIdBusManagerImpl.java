package com.integrosys.cms.app.geography.city.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;

/**
 * @author dattatray.thorat
 * Bus Manager Implication for staging Holiday
 */
public class FileMapperIdBusManagerImpl extends AbstractCityBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging holiday table  
     * 
     */
	
	public String getCityName() {
        return ICityDAO.ACTUAL_STAGE_FILE_MAPPER_ID;
    }

	/**
	 * This method returns exception as staging
	 *  holiday can never be working copy
	 */
    
    public ICity updateToWorkingCopy(ICity workingCopy, ICity imageCopy)
            throws NoSuchGeographyException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }
    
    public ICity deleteCity(
			ICity City) throws NoSuchGeographyException,
			TrxParameterException, TransactionException {
		throw new NoSuchGeographyException("ICity object is null");
	}
}