package com.integrosys.cms.app.fileUpload.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.IComponent;


public interface IFileUploadBusManager {
	
	
	IFileUpload makerCreateFile(IFileUpload fileUpload)throws FileUploadException;
	public IFileUpload getFileUploadById(long id)	throws FileUploadException, TrxParameterException,	TransactionException;
	public IFileUpload createFileUpload(IFileUpload anFile)throws FileUploadException;
	public IFileUpload updateFileUpload(IFileUpload item)throws FileUploadException, TrxParameterException,TransactionException;

}
