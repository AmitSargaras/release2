package com.integrosys.cms.app.fccBranch.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/****
 * 
 * @author komal.agicha
 *
 */
public class FileMapperIdBusManagerStagingImpl extends AbstractFCCBranchBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging caseBranch table  
     * 
     */
	
	public String getBranchName() {
        return IFCCBranchDao.STAGE_FILE_MAPPER_ID;
    }


    
    public IFCCBranch updateToWorkingCopy(IFCCBranch workingCopy, IFCCBranch imageCopy)
            throws FCCBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	


}