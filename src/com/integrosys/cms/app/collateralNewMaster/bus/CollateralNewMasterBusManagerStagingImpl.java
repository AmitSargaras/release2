package com.integrosys.cms.app.collateralNewMaster.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * @author abhijit.rudrakshawar
 * Bus Manager Implication for staging CollateralNewMaster
 */
public class CollateralNewMasterBusManagerStagingImpl extends AbstractCollateralNewMasterBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging CollateralNewMaster table  
     * 
     */
	
	public String getCollateralNewMasterName() {
        return ICollateralNewMasterDao.STAGE_COLLATERAL_NEW_MASTER_NAME;
    }

	/**
	 * This method returns exception as staging
	 *  system bank branch can never be working copy
	 */
    
    public ICollateralNewMaster updateToWorkingCopy(ICollateralNewMaster workingCopy, ICollateralNewMaster imageCopy)
            throws CollateralNewMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	
	
	
	

	
	

}