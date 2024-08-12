package com.integrosys.cms.app.excludedfacility.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public class ExcludedFacilityBusManagerStagingImpl extends AbstractExcludedFacilityBusManager {

	 /**
     * 
     * This method give the entity name of 
     * staging ExcludedFacility table  
     * 
     */
	
	public String getExcludedFacilityName() {
        return IExcludedFacilityDao.STAGE_EXCLUDED_FACILITY_NAME;
    }
	
	/**
	 * This method returns exception as staging
	 *  system bank branch can never be working copy
	 */
    
    public IExcludedFacility updateToWorkingCopy(IExcludedFacility workingCopy, IExcludedFacility imageCopy)
            throws ExcludedFacilityException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	public SearchResult getFilteredActualExcludedFacility(String code,
			String name, String category, String type, String system,
			String line) throws ExcludedFacilityException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException {
		return null;
	}
}
