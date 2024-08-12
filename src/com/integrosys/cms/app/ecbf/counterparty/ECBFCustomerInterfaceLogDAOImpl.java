package com.integrosys.cms.app.ecbf.counterparty;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.logger.DefaultLogger;

public class ECBFCustomerInterfaceLogDAOImpl extends HibernateDaoSupport implements IECBFCustomerInterfaceLogDAO {

	public String getEntityName() {
		return IECBFCustomerInterfaceLogDAO.INTERFACE_LOG_NAME;
	}
	
	@Override
	public IECBFCustomerInterfaceLog createOrUpdateInterfaceLog(IECBFCustomerInterfaceLog log) throws Exception {

		IECBFCustomerInterfaceLog returnObj = null;
		try {
			if(log.getId() > 0) {
				getHibernateTemplate().update(getEntityName(), log);
			}else {
				getHibernateTemplate().save(getEntityName(), log);
			}
			returnObj = (IECBFCustomerInterfaceLog) getHibernateTemplate().load(
					getEntityName(), new Long(log.getId()));
		} catch (Exception obe) {
			DefaultLogger.error(this,"############# error in createOrUpdateInterfaceLog ", obe);
			obe.printStackTrace();
			throw obe;
		}
		return returnObj;
	
	}

	@Override
	public IECBFCustomerInterfaceLog getInterfaceLog(long id) throws Exception {
		return (IECBFCustomerInterfaceLog) getHibernateTemplate().get(getEntityName(), id);
	}

}