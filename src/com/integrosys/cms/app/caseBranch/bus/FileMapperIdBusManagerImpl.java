package com.integrosys.cms.app.caseBranch.bus;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.caseBranch.trx.ICaseBranchTrxValue;

/**
 * @author abhijit.rudrakshawar
 * Bus Manager Implication for staging CaseBranch
 */
public class FileMapperIdBusManagerImpl extends AbstractCaseBranchBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging caseBranch table  
     * 
     */
	
	public String getCaseBranchName() {
        return ICaseBranchDao.ACTUAL_STAGE_FILE_MAPPER_ID;
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