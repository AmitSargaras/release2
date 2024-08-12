package com.integrosys.cms.app.limit.bus;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.manualinput.limit.LiabilityWsLog;
import com.integrosys.cms.ui.manualinput.limit.CoBorrower_WsLog;
import com.integrosys.cms.ui.manualinput.limit.ICoBorrower_WsLog;
import com.integrosys.cms.ui.manualinput.limit.ILiabilityWsLog;

/**
 * @author uma.khot $<br>
 * 
 *         Dao Implication declares the methods used by Bus manager Implication
 */

public class CoBorrower_WsLogDaoImpl extends HibernateDaoSupport implements
ICoBorrower_FCUBS_WsLogDao {

	public String getEntityName() {
		return ICoBorrower_FCUBS_WsLogDao.COBORROWER_INTERFACE_LOG_NAME;
	}
	

	public ICoBorrower_WsLog createCoBorrowerWsLog(ICoBorrower_WsLog iCoBorrowerWsLog) {
		
		DefaultLogger.debug(this, "start createCoBorrowerWsLog");
		ICoBorrower_WsLog returnObj= new CoBorrower_WsLog();
		DefaultLogger.debug(this, "returnObj:"+returnObj);
		try {
			getHibernateTemplate().save(getEntityName(), iCoBorrowerWsLog);
			DefaultLogger.debug(this, "save completed.");
			returnObj = (ICoBorrower_WsLog)getHibernateTemplate().load(getEntityName(), new Long(iCoBorrowerWsLog.getId()));
			DefaultLogger.debug(this, "completed createCoBorrowerWsLog");
		}catch(Exception obe) {
			DefaultLogger.debug(this,"Exception in createCoBorrowerWsLog:"+obe.getMessage());
			obe.printStackTrace();
		}
		DefaultLogger.debug(this, "returning returnObj.getId():"+returnObj.getId());
		return returnObj;
	}

	
}
