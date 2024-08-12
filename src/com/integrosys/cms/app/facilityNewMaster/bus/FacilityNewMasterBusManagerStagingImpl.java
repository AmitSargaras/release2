package com.integrosys.cms.app.facilityNewMaster.bus;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * @author abhijit.rudrakshawar
 * Bus Manager Implication for staging FacilityNewMaster
 */
public class FacilityNewMasterBusManagerStagingImpl extends AbstractFacilityNewMasterBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging FacilityNewMaster table  
     * 
     */
	
	public String getFacilityNewMasterName() {
        return IFacilityNewMasterDao.STAGE_FACILITY_NEW_MASTER_NAME;
    }

	/**
	 * This method returns exception as staging
	 *  system bank branch can never be working copy
	 */
    
    public IFacilityNewMaster updateToWorkingCopy(IFacilityNewMaster workingCopy, IFacilityNewMaster imageCopy)
            throws FacilityNewMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	public SearchResult getFilteredActualFacilityNewMaster(String code,
			String name, String category, String type, String system,
			String line) throws FacilityNewMasterException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException {
		// TODO Auto-generated method stub
		return null;
	}

}