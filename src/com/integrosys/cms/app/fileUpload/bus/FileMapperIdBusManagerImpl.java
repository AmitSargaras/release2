package com.integrosys.cms.app.fileUpload.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.IComponent;

public class FileMapperIdBusManagerImpl extends AbstractFileUploadBusManager{

	public String getFileUploadName() {
		return IFileUploadDao.ACTUAL_STAGE_FILE_MAPPER_ID;
	}
	
	public IFileUpload updateToWorkingCopy(IFileUpload workingCopy, IFileUpload imageCopy)
    throws FileUploadException,TrxParameterException,TransactionException,ConcurrentUpdateException {
throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
}

}
