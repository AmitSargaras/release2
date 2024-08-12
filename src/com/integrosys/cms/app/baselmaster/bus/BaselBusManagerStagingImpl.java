package com.integrosys.cms.app.baselmaster.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.bus.IComponentDao;

public class BaselBusManagerStagingImpl extends AbstractBaselBusManager{
	
	public String getBaselName() {
        return IBaselDao.STAGE_BASEL_NAME;
    }

	/**
	 * This method returns exception as staging
	 *  component can never be working copy
	 */
    
    public IBaselMaster updateToWorkingCopy(IBaselMaster workingCopy, IBaselMaster imageCopy)
            throws BaselMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

}
