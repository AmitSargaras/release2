package com.integrosys.cms.app.systemBankBranch.bus;

import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * @author abhijit.rudrakshawar
 * Bus Manager Implication for staging System Bank Branch
 */
public class SystemBankBranchBusManagerStagingImpl extends AbstractSystemBankBranchBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging system bank branch table  
     * 
     */
	
	public String getSystemBankBranchName() {
        return ISystemBankBranchDao.STAGE_SYSTEM_BANK_BRANCH_NAME;
    }

	/**
	 * This method returns exception as staging
	 *  system bank branch can never be working copy
	 */
    
    public ISystemBankBranch updateToWorkingCopy(ISystemBankBranch workingCopy, ISystemBankBranch imageCopy)
            throws SystemBankBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	
	

	
	

}