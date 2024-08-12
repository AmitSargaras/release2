package com.integrosys.cms.ui.finwareupload.bus;

import java.util.ArrayList;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.batch.ubs.IUbsErrDetLog;
import com.integrosys.cms.batch.ubs.IUbsErrorLog;
import com.integrosys.cms.ui.finwareupload.dao.IFinwareUploadDAO;
/**
*@author $Author: Abhijit R$
*Bus Manager Implication to communicate with DAO and JDBC
 */
public class FinwareUploadBusManagerImpl implements IFinwareUploadBusManager {
	private IFinwareUploadDAO finwareUploadDao;
	public IUbsErrorLog insertFinwareUpload(ArrayList result,String fileName,String uploadId,IUbsErrDetLog obUbsErrDetLog[]) {
		try {
			return getFinwareUploadDao().insertFinwareUpload(result,fileName,uploadId,obUbsErrDetLog);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new NoSuchGeographyException("File is not in proper format");
		}
	}
	public IFinwareUploadDAO getFinwareUploadDao() {
		return finwareUploadDao;
	}
	public void setFinwareUploadDao(IFinwareUploadDAO finwareUploadDao) {
		this.finwareUploadDao = finwareUploadDao;
	}

	
}
