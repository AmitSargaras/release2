package com.integrosys.cms.app.ecbf.limit;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.logger.DefaultLogger;

public class ECBFLimitInterfaceLogDAOImpl extends HibernateDaoSupport implements IECBFLimitInterfaceLogDAO {

	public String getEntityName() {
		return IECBFLimitInterfaceLogDAO.INTERFACE_LOG_NAME;
	}
	
	@Override
	public IECBFLimitInterfaceLog createOrUpdateInterfaceLog(IECBFLimitInterfaceLog log) throws Exception {

		IECBFLimitInterfaceLog returnObj = null;
		try {
			if(log.getId() > 0) {
				getHibernateTemplate().update(getEntityName(), log);
			}else {
				getHibernateTemplate().save(getEntityName(), log);
			}
			returnObj = (IECBFLimitInterfaceLog) getHibernateTemplate().load(
					getEntityName(), new Long(log.getId()));
		} catch (Exception obe) {
			DefaultLogger.error(this,"############# error in createOrUpdateInterfaceLog ", obe);
			obe.printStackTrace();
			throw obe;
		}
		return returnObj;
	
	}

}