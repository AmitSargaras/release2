package com.integrosys.cms.ui.partycamupload.bus;

import java.util.ArrayList;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.cms.batch.partycam.IPartyCamErrDetLog;
import com.integrosys.cms.batch.partycam.IPartyCamErrorLog;
import com.integrosys.cms.ui.partycamupload.PartyCamUploadException;
import com.integrosys.cms.ui.partycamupload.dao.IPartyCamUploadDAO;

/**
*@author $Author: Abhijit R$
*Bus Manager Implication to communicate with DAO and JDBC
 */
public class PartyCamUploadBusManagerImpl implements IPartyCamUploadBusManager {
	private IPartyCamUploadDAO partyCamUploadDao;
	public IPartyCamErrorLog insertPartyCamUpload(ArrayList resultList,String fileName,String uploadId,IPartyCamErrDetLog[] obPartyCamErrDetLog) {
		try {
			return getPartyCamUploadDao().insertPartyCamUpload( resultList,fileName,uploadId,obPartyCamErrDetLog);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new PartyCamUploadException("File is not in proper format");
		}
	}
	public IPartyCamUploadDAO getPartyCamUploadDao() {
		return partyCamUploadDao;
	}
	public void setPartyCamUploadDao(IPartyCamUploadDAO partyCamUploadDao) {
		this.partyCamUploadDao = partyCamUploadDao;
	}
}
