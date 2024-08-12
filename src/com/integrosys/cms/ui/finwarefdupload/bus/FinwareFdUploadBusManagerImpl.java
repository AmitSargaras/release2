package com.integrosys.cms.ui.finwarefdupload.bus;

import java.util.ArrayList;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.batch.ubs.IUbsErrDetLog;
import com.integrosys.cms.batch.ubs.IUbsErrorLog;
import com.integrosys.cms.ui.finwarefdupload.dao.IFinwareFdUploadDAO;
/**
*@author $Author: Abhijit R$
*Bus Manager Implication to communicate with DAO and JDBC
 */
public class FinwareFdUploadBusManagerImpl implements IFinwareFdUploadBusManager {
	private IFinwareFdUploadDAO finwarefdUploadDao;
	public IUbsErrorLog compareFinwareFdfile(ArrayList result,String fileName,String uploadId,IUbsErrDetLog obUbsErrDetLog[]) {
		try {
			return getFinwarefdUploadDao().compareFinwareFdfile(result,fileName,uploadId,obUbsErrDetLog);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new NoSuchGeographyException("File is not in proper format");
		}
	}
	public IFinwareFdUploadDAO getFinwarefdUploadDao() {
		return finwarefdUploadDao;
	}
	public void setFinwarefdUploadDao(IFinwareFdUploadDAO finwarefdUploadDao) {
		this.finwarefdUploadDao = finwarefdUploadDao;
	}


	
}
