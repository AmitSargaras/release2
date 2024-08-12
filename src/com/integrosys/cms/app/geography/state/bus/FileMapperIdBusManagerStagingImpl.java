package com.integrosys.cms.app.geography.state.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;

/**
 * @author abhijit.rudrakshawar
 * Bus Manager Implication for staging Holiday
 */
public class FileMapperIdBusManagerStagingImpl extends AbstractStateBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging holiday table  
     * 
     */
	
	public String getStateName() {
        return IStateDAO.STAGE_FILE_MAPPER_ID;
    }


    
    public IState updateToWorkingCopy(IState workingCopy, IState imageCopy)
            throws NoSuchGeographyException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }



	public IState deleteState(
			IState State) throws NoSuchGeographyException,
			TrxParameterException, TransactionException {
		throw new NoSuchGeographyException("IState object is null");
	}

	


}