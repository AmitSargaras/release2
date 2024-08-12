package com.integrosys.cms.app.ws.dto;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * @author $Author: Bharat Waghela $<br>
 * 
 *         Dao Implication declares the methods used by Bus manager Implication
 */

public class FCUBSReportDataLogDaoImpl extends HibernateDaoSupport implements
	IFCUBSReportDataLogDao {

	
	
    private IFCUBSReportDataLogDao FCUBSReportDataLogDao;
	
    public IFCUBSReportDataLogDao getFCUBSReportDataLogDao() {
		return FCUBSReportDataLogDao;
	}
	public void setFCUBSDataLogDao(IFCUBSReportDataLogDao FCUBSReportDataLogDao) {
		this.FCUBSReportDataLogDao = FCUBSReportDataLogDao;
	}
	
	
	public String getEntityName() {
		return FCUBSReportDataLogDao.ACTUAL_INTERFACE_LOG_NAME;
	}

	

	
	public IFCUBSReportDataLog createFCUBSReportDataLog(IFCUBSReportDataLog fcubsReportDataLog)
			throws InterfaceLogException {
		IFCUBSReportDataLog returnObj = new OBFCUBSReportDataLog();
		try {
			getHibernateTemplate().save(getEntityName(), fcubsReportDataLog);
			
			returnObj = (IFCUBSReportDataLog) getHibernateTemplate().load(
					getEntityName(), new Long(fcubsReportDataLog.getId()));
		} catch (Exception obe) {
			DefaultLogger.error(this,
					"############# error in createInterfaceLog ", obe);
			obe.printStackTrace();
			throw new FCUBSDataLogException("Unable to generate InterfaceLog ");
		}
		return returnObj;
	}


}
