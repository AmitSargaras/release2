package com.integrosys.cms.app.systemBankBranch.bus;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * @author abhijit.rudrakshawar
 * Bus Manager Implication for staging System Bank Branch
 */
public class FileMapperIdBusManagerStagingImpl extends AbstractSystemBankBranchBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging system bank branch table  
     * 
     */
	
	public String getSystemBankBranchName() {
        return ISystemBankBranchDao.STAGE_FILE_MAPPER_ID;
    }

	/**
	 * This method returns exception as staging
	 *  system bank branch can never be working copy
	 */
    
    public ISystemBankBranch updateToWorkingCopy(ISystemBankBranch workingCopy, ISystemBankBranch imageCopy)
            throws SystemBankBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	
	public List getAllHUBBranchId() throws SystemBankBranchException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List getAllHUBBranchValue() throws SystemBankBranchException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException {
		// TODO Auto-generated method stub
		return null;
	}	

}