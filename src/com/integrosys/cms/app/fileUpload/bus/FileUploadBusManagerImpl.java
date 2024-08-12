package com.integrosys.cms.app.fileUpload.bus;


public class FileUploadBusManagerImpl extends AbstractFileUploadBusManager implements IFileUploadBusManager{
	
	public String getFileUploadName() {
		return IFileUploadDao.ACTUAL_FILEUPLOAD_NAME;
	}

}
