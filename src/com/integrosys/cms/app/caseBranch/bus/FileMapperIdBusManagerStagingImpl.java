package com.integrosys.cms.app.caseBranch.bus;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * @author abhijit.rudrakshawar
 * Bus Manager Implication for staging CaseBranch
 */
public class FileMapperIdBusManagerStagingImpl extends AbstractCaseBranchBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging caseBranch table  
     * 
     */
	
	public String getCaseBranchName() {
        return ICaseBranchDao.STAGE_FILE_MAPPER_ID;
    }


    
    public ICaseBranch updateToWorkingCopy(ICaseBranch workingCopy, ICaseBranch imageCopy)
            throws CaseBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	


}