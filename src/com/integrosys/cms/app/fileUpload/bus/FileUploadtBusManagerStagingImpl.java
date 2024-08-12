package com.integrosys.cms.app.fileUpload.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public class FileUploadtBusManagerStagingImpl extends AbstractFileUploadBusManager{
	
	

	/**
	 * This method returns exception as staging
	 *  component can never be working copy
	 */
    
    public IFileUpload updateToWorkingCopy(IFileUpload workingCopy, IFileUpload imageCopy)
            throws FileUploadException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	public String getFileUploadName() {
		 return IFileUploadDao.STAGE_FILEUPLOAD_NAME;
	}
	
	
	

}
