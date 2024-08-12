package com.integrosys.cms.app.component.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;


public class FileMapperIdBusManagerImpl extends AbstractComponentBusManager {

	
	public String getComponentName() {
		 return IComponentDao.ACTUAL_STAGE_FILE_MAPPER_ID;
	}
	
	public IComponent updateToWorkingCopy(IComponent workingCopy, IComponent imageCopy)
    throws ComponentException,TrxParameterException,TransactionException,ConcurrentUpdateException {
throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
}

}
