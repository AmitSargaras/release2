package com.integrosys.cms.app.goodsMaster.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public class GoodsMasterBusManagerStagingImpl extends AbstractGoodsMasterBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging GoodsMaster table  
     * 
     */
	
	public String getGoodsMasterName() {
        return IGoodsMasterDao.STAGE_GOODS_MASTER_NAME;
    }
	
	/**
	 * This method returns exception as staging
	 *  system bank branch can never be working copy
	 */
    
    public IGoodsMaster updateToWorkingCopy(IGoodsMaster workingCopy, IGoodsMaster imageCopy)
            throws GoodsMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	public SearchResult getFilteredActualGoodsMaster(String code,
			String name, String category, String type, String system,
			String line) throws GoodsMasterException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException {
		return null;
	}
}
