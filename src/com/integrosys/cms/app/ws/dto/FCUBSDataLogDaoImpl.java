package com.integrosys.cms.app.ws.dto;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * @author $Author: Bharat Waghela $<br>
 * 
 *         Dao Implication declares the methods used by Bus manager Implication
 */

public class FCUBSDataLogDaoImpl extends HibernateDaoSupport implements
	IFCUBSDataLogDao {

	
	
    private IFCUBSDataLogDao FCUBSDataLogDao;
	
    public IFCUBSDataLogDao geFCUBSDataLogDao() {
		return FCUBSDataLogDao;
	}
	public void setFCUBSDataLogDao(IFCUBSDataLogDao FCUBSDataLogDao) {
		this.FCUBSDataLogDao = FCUBSDataLogDao;
	}
	
	
	public String getEntityName() {
		return FCUBSDataLogDao.ACTUAL_INTERFACE_LOG_NAME;
	}

	

	
	public IFCUBSDataLog createFCUBSDataLog(IFCUBSDataLog fcubsDataLog)
			throws InterfaceLogException {
		IFCUBSDataLog returnObj = new OBFCUBSDataLog();
		try {
			getHibernateTemplate().save(getEntityName(), fcubsDataLog);
			
			returnObj = (IFCUBSDataLog) getHibernateTemplate().load(
					getEntityName(), new Long(fcubsDataLog.getId()));
		} catch (Exception obe) {
			DefaultLogger.error(this,
					"############# error in createInterfaceLog ", obe);
			obe.printStackTrace();
			throw new FCUBSDataLogException("Unable to generate InterfaceLog ");
		}
		return returnObj;
	}


}
