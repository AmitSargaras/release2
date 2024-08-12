package com.integrosys.cms.app.geography.region.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;

/**
 * @author dattatray.thorat
 * Bus Manager Implication for staging Holiday
 */
public class FileMapperIdBusManagerImpl extends AbstractRegionBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging holiday table  
     * 
     */
	
	public String getRegionName() {
        return IRegionDAO.ACTUAL_STAGE_FILE_MAPPER_ID;
    }

	/**
	 * This method returns exception as staging
	 *  holiday can never be working copy
	 */
    
    public IRegion updateToWorkingCopy(IRegion workingCopy, IRegion imageCopy)
            throws NoSuchGeographyException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }
    
    public IRegion deleteRegion(
			IRegion Region) throws NoSuchGeographyException,
			TrxParameterException, TransactionException {
		throw new NoSuchGeographyException("IRegion object is null");
	}
}