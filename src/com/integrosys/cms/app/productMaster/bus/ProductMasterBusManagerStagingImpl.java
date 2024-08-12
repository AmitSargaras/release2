package com.integrosys.cms.app.productMaster.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public class ProductMasterBusManagerStagingImpl extends AbstractProductMasterBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging ProductMaster table  
     * 
     */
	
	public String getProductMasterName() {
        return IProductMasterDao.STAGE_PRODUCT_MASTER_NAME;
    }
	
	/**
	 * This method returns exception as staging
	 *  system bank branch can never be working copy
	 */
    
    public IProductMaster updateToWorkingCopy(IProductMaster workingCopy, IProductMaster imageCopy)
            throws ProductMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	public SearchResult getFilteredActualProductMaster(String code,
			String name, String category, String type, String system,
			String line) throws ProductMasterException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException {
		return null;
	}
}
