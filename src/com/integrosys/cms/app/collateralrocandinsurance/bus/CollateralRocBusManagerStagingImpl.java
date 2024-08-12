package com.integrosys.cms.app.collateralrocandinsurance.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public class CollateralRocBusManagerStagingImpl extends AbstractCollateralRocBusManager  {

	 /**
     * 
     * This method give the entity name of 
     * staging Collateral Roc table  
     * 
     */
	
	public String getCollateralRocName() {
        return ICollateralRocDao.STAGE_COLLATERAL_ROC_NAME;
    }
	
	/**
	 * This method returns exception as staging
	 *  system bank branch can never be working copy
	 */
    
    public ICollateralRoc updateToWorkingCopy(ICollateralRoc workingCopy, ICollateralRoc imageCopy)
            throws CollateralRocException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

    public SearchResult getFilteredActualCollateralRoc(String code,
			String name, String category, String type, String system,
			String line) throws CollateralRocException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException {
		return null;
	}

}
