package com.integrosys.cms.ui.ubsupload.bus;

import java.util.ArrayList;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.cms.batch.ubs.IUbsErrDetLog;
import com.integrosys.cms.batch.ubs.IUbsErrorLog;
import com.integrosys.cms.ui.ubsupload.UBSUploadException;
import com.integrosys.cms.ui.ubsupload.dao.IUbsUploadDAO;
/**
*@author $Author: Abhijit R$
*Bus Manager Implication to communicate with DAO and JDBC
 */
public class UbsUploadBusManagerImpl implements IUbsUploadBusManager {
	private IUbsUploadDAO ubsUploadDao;
	public IUbsErrorLog insertUbsUpload(ArrayList resultList,String fileName,String uploadId,IUbsErrDetLog[] obUbsErrDetLog) {
		try {
			return getUbsUploadDao().insertUbsUpload( resultList,fileName,uploadId,obUbsErrDetLog);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new UBSUploadException("File is not in proper format");
		}
	}
	public IUbsUploadDAO getUbsUploadDao() {
		return ubsUploadDao;
	}
	public void setUbsUploadDao(IUbsUploadDAO ubsUploadDao) {
		this.ubsUploadDao = ubsUploadDao;
	}
}
