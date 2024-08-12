package com.integrosys.cms.app.fileUpload.bus;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.IComponent;

public abstract class AbstractFileUploadBusManager implements IFileUploadBusManager{
	
	private IFileUploadDao fileUploadDao;
	
	

	public IFileUploadDao getFileUploadDao() {
		return fileUploadDao;
	}



	public void setFileUploadDao(IFileUploadDao fileUploadDao) {
		this.fileUploadDao = fileUploadDao;
	}

	public abstract String getFileUploadName();

	public IFileUpload makerCreateFile(IFileUpload fileUpload)
			throws FileUploadException {
		if (!(fileUpload == null)) {
			return getFileUploadDao().makerCreateFile(getFileUploadName(), fileUpload);
		} else {
			throw new FileUploadException(
					"ERROR- File object   is null. ");
		}
	}
	
	public IFileUpload getFileUploadById(long id)	throws FileUploadException, TrxParameterException,	TransactionException {
		if (id != 0) {
			return getFileUploadDao().getFileUpload(getFileUploadName(), new Long(id));
		} else {
				throw new FileUploadException("ERROR-- Key for Object Retrival is null.");
				}
	}
	
	public IFileUpload createFileUpload(IFileUpload anFile)throws FileUploadException {
		if (!(anFile == null)) {
			return getFileUploadDao().createFileUpload(getFileUploadName(), anFile);
		} else {
			throw new ComponentException(
					"ERROR- Component object   is null. ");
		}
	}
	
	public IFileUpload updateFileUpload(IFileUpload item)throws FileUploadException, TrxParameterException,TransactionException {
		try {
			return getFileUploadDao().updateFileUpload(getFileUploadName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new ComponentException("current Component");
		}
	}
	
	

}
