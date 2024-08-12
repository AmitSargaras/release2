package com.integrosys.cms.app.geography.region.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;

/**
 * @author abhijit.rudrakshawar
 * Bus Manager Implication for staging Holiday
 */
public class FileMapperIdBusManagerStagingImpl extends AbstractRegionBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging holiday table  
     * 
     */
	
	public String getRegionName() {
        return IRegionDAO.STAGE_FILE_MAPPER_ID;
    }


    
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