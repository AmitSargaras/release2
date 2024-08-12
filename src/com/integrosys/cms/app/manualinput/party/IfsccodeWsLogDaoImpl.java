package com.integrosys.cms.app.manualinput.party;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.manualinput.customer.IIfsccodeWsLog;
import com.integrosys.cms.ui.manualinput.customer.IfsccodeWsLog;

/**
 * @author uma.khot $<br>
 * 
 *         Dao Implication declares the methods used by Bus manager Implication
 */

public class IfsccodeWsLogDaoImpl extends HibernateDaoSupport implements
IIfsccodeWsLogDao {

	public String getEntityName() {
		return IIfsccodeWsLogDao.IFSCCODE_INTERFACE_LOG_NAME;
	}
	

	public IIfsccodeWsLog createIfsccodeWsLog(IIfsccodeWsLog iIfsccodeWsLog) {
		IIfsccodeWsLog returnObj= new IfsccodeWsLog();
		try {
			getHibernateTemplate().save(getEntityName(), iIfsccodeWsLog);
			returnObj = (IIfsccodeWsLog)getHibernateTemplate().load(getEntityName(), new Long(iIfsccodeWsLog.getId()));
		}catch(Exception obe) {
			DefaultLogger.debug(this,obe.getMessage());
			obe.printStackTrace();
		}
		return returnObj;
	}

	
}
