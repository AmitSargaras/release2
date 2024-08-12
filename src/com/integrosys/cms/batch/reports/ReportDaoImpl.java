package com.integrosys.cms.batch.reports;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

/**
 * Implementation of IReportDao using hibernate routine.
 * 
 * @author Forbys Wei
 * @author Chong Jun Yong
 */
public class ReportDaoImpl extends HibernateDaoSupport implements IReportDao {

	public IReportRequest getReportRequestByRequestID(Serializable key) {
		return (IReportRequest) getHibernateTemplate().get(OBReportRequest.class, key);
	}

	public IReportRequest createReportRequest(IReportRequest item) {
		Long key = (Long) getHibernateTemplate().save(item);

		item.setReportRequestID(key.longValue());
		return item;
	}

	public IReportRequest updateReportRequest(IReportRequest item) {
		getHibernateTemplate().update(item);

		return (IReportRequest) getHibernateTemplate().get(OBReportRequest.class, new Long(item.getReportRequestID()));
	}

	public List getReportRequestByStatus(String status) {
		DetachedCriteria criteria = DetachedCriteria.forClass(OBReportRequest.class).add(
				Restrictions.eq("status", status));

		return getHibernateTemplate().findByCriteria(criteria);
	}

}
