package com.integrosys.cms.app.npaTraqCodeMaster.bus;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.ws.dto.InterfaceLogException;

/**
 * @author $Author: Bharat Waghela $<br>
 * 
 *         Dao Implication declares the methods used by Bus manager Implication
 */

public class NpaDataLogDaoImpl extends HibernateDaoSupport implements
	INpaDataLogDao {

	
	
    private INpaDataLogDao npaDataLogDao;
	
	public INpaDataLogDao getNpaDataLogDao() {
		return npaDataLogDao;
	}

	public void setNpaDataLogDao(INpaDataLogDao npaDataLogDao) {
		this.npaDataLogDao = npaDataLogDao;
	}

	public String getEntityName() {
		return npaDataLogDao.ACTUAL_INTERFACE_LOG_NAME;
	}

	

	
	public INpaDataLog createNpaDataLog(INpaDataLog npaDataLog)
			throws InterfaceLogException {
		INpaDataLog returnObj = new OBNpaDataLog();
		try {
			getHibernateTemplate().save(getEntityName(), npaDataLog);
			
			returnObj = (INpaDataLog) getHibernateTemplate().load(
					getEntityName(), new Long(npaDataLog.getId()));
		} catch (Exception obe) {
			DefaultLogger.error(this,
					"############# error in createInterfaceLog ", obe);
			obe.printStackTrace();
			throw new NpaDataLogException("Unable to generate InterfaceLog ");
		}
		return returnObj;
	}

}
