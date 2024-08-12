package com.integrosys.cms.app.directorMaster.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.directorMaster.bus.IDirectorMasterDao;

/**
 * Description:
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-04 15:13:16 +0800 (Tue, 03 May 2011) $
 * Bus Manager Implication for staging director master
 */
public class DirectorMasterBusManagerStagingImpl extends AbstractDirectorMasterBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging director master table  
     * 
     */
	
	public String getDirectorMasterName() {
        return IDirectorMasterDao.STAGE_DIRECTOR_MASTER_NAME;
    }

	/**
	 * This method returns exception as staging
	 *  director master can never be working copy
	 */
    
    public IDirectorMaster updateToWorkingCopy(IDirectorMaster workingCopy, IDirectorMaster imageCopy)
            throws DirectorMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }


	
	
	

}