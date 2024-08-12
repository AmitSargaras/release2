package com.integrosys.cms.app.caseBranch.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * @author abhijit.rudrakshawar
 * Bus Manager Implication for staging CaseBranch
 */
public class CaseBranchBusManagerStagingImpl extends AbstractCaseBranchBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging CaseBranch table  
     * 
     */
	
	public String getCaseBranchName() {
        return ICaseBranchDao.STAGE_CASEBRANCH_NAME;
    }

	/**
	 * This method returns exception as staging
	 *  caseBranch can never be working copy
	 */
    
    public ICaseBranch updateToWorkingCopy(ICaseBranch workingCopy, ICaseBranch imageCopy)
            throws CaseBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	
	
	
	

	
	

}