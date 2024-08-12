package com.integrosys.cms.app.newtatmaster.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public class TatMasterBusManagerStagingImpl extends	AbstractTatMasterBusManager{
	


	
	public String getTatMasterName() {
        return ITatMasterDao.STAGE_TAT_MASTER_NAME;
    }

	/**
	 * This method returns exception as staging
	 *  component can never be working copy
	 */
    
    public ITatMasterDao updateToWorkingCopy(ITatMasterDao workingCopy, ITatMasterDao imageCopy)
            throws TatMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	
	public INewTatMaster updateToWorkingCopy(INewTatMaster workingCopy,
			INewTatMaster imageCopy) throws TatMasterException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException {
		
		return null;
	}
}
