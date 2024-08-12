package com.integrosys.cms.app.baselmaster.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.bus.IComponentDao;

public class FileMapperIdBusManagerImpl extends AbstractBaselBusManager{

	public String getBaselName() {
		 return IBaselDao.ACTUAL_STAGE_FILE_MAPPER_ID;
	}
	
	public IBaselMaster updateToWorkingCopy(IBaselMaster workingCopy, IBaselMaster imageCopy)
   throws ComponentException,TrxParameterException,TransactionException,ConcurrentUpdateException {
throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
}
}
