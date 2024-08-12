package com.integrosys.cms.app.limit.bus;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.manualinput.limit.FacilityWsLog;
import com.integrosys.cms.ui.manualinput.limit.IFacilityWsLog;

/**
 * @author uma.khot $<br>
 * 
 *         Dao Implication declares the methods used by Bus manager Implication
 */

public class FacilityWsLogDaoImpl extends HibernateDaoSupport implements
IFacilityWsLogDao {

	public String getEntityName() {
		return IFacilityWsLogDao.FACILITY_INTERFACE_LOG_NAME;
	}
	

	public IFacilityWsLog createFacilityWsLog(IFacilityWsLog iFacilityWsLog) {
		DefaultLogger.debug(this, "start createFacilityWsLog");
		IFacilityWsLog returnObj= new FacilityWsLog();
		DefaultLogger.debug(this, "returnObj:"+returnObj);
		try {
			getHibernateTemplate().save(getEntityName(), iFacilityWsLog);
			
			DefaultLogger.debug(this, "save completed.");
			returnObj = (IFacilityWsLog)getHibernateTemplate().load(getEntityName(), new Long(iFacilityWsLog.getId()));
			DefaultLogger.debug(this, "completed createFacilityWsLog");
		}catch(Exception obe) {
			DefaultLogger.debug(this,"Exception in createFacilityWsLog:"+obe.getMessage());
			obe.printStackTrace();
		}
		
		DefaultLogger.debug(this, "returning returnObj.getId():"+returnObj.getId());
		return returnObj;
	}

	
}
