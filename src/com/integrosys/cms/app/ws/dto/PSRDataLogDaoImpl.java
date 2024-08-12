package com.integrosys.cms.app.ws.dto;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.logger.DefaultLogger;

public class PSRDataLogDaoImpl extends HibernateDaoSupport implements IPSRDataLogDao {
	
    private IPSRDataLogDao PSRDataLogDao;
	
	public IPSRDataLogDao getPSRDataLogDao() {
		return PSRDataLogDao;
	}
	public void setPSRDataLogDao(IPSRDataLogDao pSRDataLogDao) {
		PSRDataLogDao = pSRDataLogDao;
	}

	public String getEntityName() {
		return PSRDataLogDao.ACTUAL_INTERFACE_LOG_NAME;
	}
	
	public IPSRDataLog createPSRDataLog(IPSRDataLog psrDataLog) throws InterfaceLogException {
		IPSRDataLog returnObj = new OBPSRDataLog();
		try {
			getHibernateTemplate().save(getEntityName(), psrDataLog);
			returnObj = (IPSRDataLog) getHibernateTemplate().load(getEntityName(), new Long(psrDataLog.getId()));
		} catch (Exception obe) {
			DefaultLogger.error(this,"############# error in createInterfaceLog ", obe);
			obe.printStackTrace();
			throw new PSRDataLogException("Unable to generate InterfaceLog ");
		}
		return returnObj;
	}

}
