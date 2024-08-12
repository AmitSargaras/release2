package com.integrosys.cms.app.systemBank.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * @author abhijit.rudrakshawar
 * Bus Manager Implication for staging System Bank 
 */
public class SystemBankBusManagerStagingImpl extends AbstractSystemBankBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging system bank  table  
     * 
     */
	
    public String getSystemBankName() {
        return ISystemBankDao.STAGE_SYSTEM_BANK_NAME;
    }
    /**
	 * This method returns exception as staging
	 *  system bank can never be working copy
	 */

    public ISystemBank updateToWorkingCopy(ISystemBank workingCopy, ISystemBank imageCopy)
            throws SystemBankException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

		

}