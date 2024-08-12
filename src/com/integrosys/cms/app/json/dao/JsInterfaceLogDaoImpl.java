package com.integrosys.cms.app.json.dao;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.json.dto.IJsInterfaceLog;

public class JsInterfaceLogDaoImpl extends HibernateDaoSupport implements IJsInterfaceLogDao {

public String getEntityName() {
return IJsInterfaceLogDao.ACTUAL_INTERFACE_LOG_NAME;
}

@Override
public IJsInterfaceLog createOrUpdateInterfaceLog(IJsInterfaceLog log) throws Exception {
	DefaultLogger.debug(this,"############# Inside createOrUpdateInterfaceLog method "+ log);
	DefaultLogger.debug(this,"############# Inside createOrUpdateInterfaceLog method logID "+ log.getId());
	IJsInterfaceLog returnObj = null;
	try {
		if(log.getId() > 0) {
			DefaultLogger.debug(this,"############# Inside if condition as its an update"+ log.getId());
			getHibernateTemplate().update(getEntityName(), log);
		}else {
			DefaultLogger.debug(this,"############# Inside else condition as its an insert"+log);
			getHibernateTemplate().save(getEntityName(), log);
		}
		returnObj = (IJsInterfaceLog) getHibernateTemplate().load(
				getEntityName(), new Long(log.getId()));
	} catch (Exception obe) {
		DefaultLogger.error(this,"############# error in createOrUpdateInterfaceLog ", obe);
		obe.printStackTrace();
		throw obe;
	}
	return returnObj;

}

@Override
public IJsInterfaceLog getInterfaceLog(long id) throws Exception {
	return (IJsInterfaceLog) getHibernateTemplate().get(getEntityName(), id);
}

}

