package com.integrosys.cms.app.npaTraqCodeMaster.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public class NpaTraqCodeMasterBusManagerStagingImpl extends AbstractNpaTraqCodeMasterBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging NpaTraqCodeMaster table  
     * 
     */
	
	public String getNpaTraqCodeMasterName() {
        return INpaTraqCodeMasterDao.STAGE_NPA_TRAQ_CODE_MASTER_NAME;
    }
	
	/**
	 * This method returns exception as staging
	 *  system bank branch can never be working copy
	 */
    
    public INpaTraqCodeMaster updateToWorkingCopy(INpaTraqCodeMaster workingCopy, INpaTraqCodeMaster imageCopy)
            throws NpaTraqCodeMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	/*public SearchResult getFilteredActualProductMaster(String code,
			String name, String category, String type, String system,
			String line) throws ProductMasterException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException {
		return null;
	}*/
	
}
