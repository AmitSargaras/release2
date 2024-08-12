package com.integrosys.cms.app.component.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;


public class ComponentBusManagerStagingImpl extends AbstractComponentBusManager {

	
	public String getComponentName() {
        return IComponentDao.STAGE_COMPONENT_NAME;
    }

	/**
	 * This method returns exception as staging
	 *  component can never be working copy
	 */
    
    public IComponent updateToWorkingCopy(IComponent workingCopy, IComponent imageCopy)
            throws ComponentException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }
	
	
	
	


}
