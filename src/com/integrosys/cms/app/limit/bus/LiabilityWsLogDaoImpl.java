package com.integrosys.cms.app.limit.bus;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.manualinput.limit.LiabilityWsLog;
import com.integrosys.cms.ui.manualinput.limit.ILiabilityWsLog;

/**
 * @author uma.khot $<br>
 * 
 *         Dao Implication declares the methods used by Bus manager Implication
 */

public class LiabilityWsLogDaoImpl extends HibernateDaoSupport implements
ILiabilityWsLogDao {

	public String getEntityName() {
		return ILiabilityWsLogDao.LIABILITY_INTERFACE_LOG_NAME;
	}
	

	public ILiabilityWsLog createLiabilityWsLog(ILiabilityWsLog iLiabilityWsLog) {
		
		DefaultLogger.debug(this, "start createLiabilityWsLog");
		ILiabilityWsLog returnObj= new LiabilityWsLog();
		DefaultLogger.debug(this, "returnObj:"+returnObj);
		try {
			getHibernateTemplate().save(getEntityName(), iLiabilityWsLog);
			DefaultLogger.debug(this, "save completed.");
			returnObj = (ILiabilityWsLog)getHibernateTemplate().load(getEntityName(), new Long(iLiabilityWsLog.getId()));
			DefaultLogger.debug(this, "completed createLiabilityWsLog");
		}catch(Exception obe) {
			DefaultLogger.debug(this,"Exception in createLiabilityWsLog:"+obe.getMessage());
			obe.printStackTrace();
		}
		DefaultLogger.debug(this, "returning returnObj.getId():"+returnObj.getId());
		return returnObj;
	}

	
}
