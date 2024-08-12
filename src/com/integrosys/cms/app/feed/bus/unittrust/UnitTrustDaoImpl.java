package com.integrosys.cms.app.feed.bus.unittrust;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

/**
 * @author Chong Jun Yong
 * @since 1.0
 * 
 */
public class UnitTrustDaoImpl extends HibernateDaoSupport implements IUnitTrustDao {

	public IUnitTrustFeedEntry createUnitTrustFeedEntry(String entityName, IUnitTrustFeedEntry unitTrustFeedEntry) {
		Long key = (Long) getHibernateTemplate().save(entityName, unitTrustFeedEntry);

		return (IUnitTrustFeedEntry) getHibernateTemplate().get(entityName, key);
	}

	public IUnitTrustFeedGroup createUnitTrustFeedGroup(String entityName, IUnitTrustFeedGroup unitTrustFeedGroup) {
		Long key = (Long) getHibernateTemplate().save(entityName, unitTrustFeedGroup);

		return (IUnitTrustFeedGroup) getHibernateTemplate().get(entityName, key);
	}

	public void deleteUnitTrustFeedEntry(String entityName, IUnitTrustFeedEntry unitTrustFeedEntry) {
		// TODO Auto-generated method stub

	}

	public void deleteUnitTrustFeedGroup(String entityName, IUnitTrustFeedGroup unitTrustFeedGroup) {
		// TODO Auto-generated method stub

	}

	public IUnitTrustFeedEntry getUnitTrustFeedEntryByPrimaryKey(String entityName, Serializable key) {
		return (IUnitTrustFeedEntry) getHibernateTemplate().get(entityName, key);
	}

	public IUnitTrustFeedGroup getUnitTrustFeedGroupByPrimaryKey(String entityName, Serializable key) {
		return (IUnitTrustFeedGroup) getHibernateTemplate().get(entityName, key);
	}

	public IUnitTrustFeedEntry updateUnitTrustFeedEntry(String entityName, IUnitTrustFeedEntry unitTrustFeedEntry) {
		getHibernateTemplate().update(entityName, unitTrustFeedEntry);

		return (IUnitTrustFeedEntry) getHibernateTemplate().get(entityName,
				new Long(unitTrustFeedEntry.getUnitTrustFeedEntryID()));
	}

	public IUnitTrustFeedGroup updateUnitTrustFeedGroup(String entityName, IUnitTrustFeedGroup unitTrustFeedGroup) {
		getHibernateTemplate().update(entityName, unitTrustFeedGroup);

		return (IUnitTrustFeedGroup) getHibernateTemplate().get(entityName,
				new Long(unitTrustFeedGroup.getUnitTrustFeedGroupID()));
	}

	public IUnitTrustFeedEntry getUnitTrustFeedEntryByRic(String entityName, String ric) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.eq("ric", ric));

		List results = getHibernateTemplate().findByCriteria(criteria);

		if (results.isEmpty()) {
			return null;
		}

		return (IUnitTrustFeedEntry) results.get(0);
	}

	public IUnitTrustFeedGroup getUnitTrustFeedGroupByTypeAndSubType(String entityName, String type, String subType) {
		Map parameters = new HashMap();
		parameters.put("type", type);
		parameters.put("subType", subType);

		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.allEq(parameters));

		List results = getHibernateTemplate().findByCriteria(criteria);

		if (results.isEmpty()) {
			return null;
		}

		return (IUnitTrustFeedGroup) results.get(0);
	}

}
