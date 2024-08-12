package com.integrosys.cms.batch;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

public class SchedulerDaoImpl extends HibernateDaoSupport implements ISchedulerDao {

	@Override
	public OBSchedulerLog createSchedulerLog(OBSchedulerLog log) {
		Long key = (Long) getHibernateTemplate().save(log);

		return (OBSchedulerLog) getHibernateTemplate().load(OBSchedulerLog.class, key);
	}

	@Override
	public OBSchedulerLog updateSchedulerLog(OBSchedulerLog log) {
		getHibernateTemplate().update(log);

		return (OBSchedulerLog) getHibernateTemplate().load(OBSchedulerLog.class, log.getId());

	}

}
