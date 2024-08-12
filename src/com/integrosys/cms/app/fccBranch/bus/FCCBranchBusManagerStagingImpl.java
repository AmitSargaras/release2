package com.integrosys.cms.app.fccBranch.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * @author abhijit.rudrakshawar
 * Bus Manager Implication for staging CaseBranch
 */
public class FCCBranchBusManagerStagingImpl extends AbstractFCCBranchBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging CaseBranch table  
     * 
     */
	
	public String getBranchName() {
        return IFCCBranchDao.STAGE_FCCBRANCH_NAME;
    }

	/**
	 * This method returns exception as staging
	 *  caseBranch can never be working copy
	 */
    
    public IFCCBranch updateToWorkingCopy(IFCCBranch workingCopy, IFCCBranch imageCopy)
            throws FCCBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	
	
	
	

	
	

}