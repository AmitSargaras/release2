package com.integrosys.cms.app.geography.country.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * @author abhijit.rudrakshawar
 * Bus Manager Implication for staging Holiday
 */
public class FileMapperIdBusManagerStagingImpl extends AbstractCountryBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging holiday table  
     * 
     */
	
	public String getCountryName() {
        return ICountryDAO.STAGE_FILE_MAPPER_ID;
    }


    
    public ICountry updateToWorkingCopy(ICountry workingCopy, ICountry imageCopy)
            throws CountryException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }



	public ICountry deleteCountry(
			ICountry Country) throws CountryException,
			TrxParameterException, TransactionException {
		throw new CountryException("ICountry object is null");
	}

	


}