package com.integrosys.cms.app.caseCreationUpdate.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * @author abhijit.rudrakshawar
 * Bus Manager Implication for staging CaseCreation
 */
public class CaseCreationBusManagerStagingImpl extends AbstractCaseCreationBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging CaseCreation table  
     * 
     */
	
	public String getCaseCreationName() {
        return ICaseCreationDao.STAGE_CASECREATION_NAME;
    }

	/**
	 * This method returns exception as staging
	 *  caseCreationUpdate can never be working copy
	 */
    
    public ICaseCreation updateToWorkingCopy(ICaseCreation workingCopy, ICaseCreation imageCopy)
            throws CaseCreationException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	
	
	
	

	
	

}