package com.integrosys.cms.app.feed.bus.gold;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

public class GoldDaoImpl extends HibernateDaoSupport implements IGoldDao {

	public IGoldFeedGroup createGoldFeedGroup(String entityName, IGoldFeedGroup goldFeedGroup) {
		Long key = (Long) getHibernateTemplate().save(entityName, goldFeedGroup);

		return (IGoldFeedGroup) getHibernateTemplate().get(entityName, key);
	}

	public IGoldFeedGroup getGoldFeedGroupByPrimaryKey(String entityName, Serializable key) {
		return (IGoldFeedGroup) getHibernateTemplate().get(entityName, key);
	}

	public IGoldFeedGroup updateGoldFeedGroup(String entityName, IGoldFeedGroup goldFeedGroup) {
		getHibernateTemplate().update(entityName, goldFeedGroup);

		return (IGoldFeedGroup) getHibernateTemplate().get(entityName, new Long(goldFeedGroup.getGoldFeedGroupID()));
	}

	public void deleteGoldFeedGroup(String entityName, IGoldFeedGroup group) {
		// TODO
	}

	public IGoldFeedGroup getGoldFeedGroupByType(String entityName, String groupType) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.eq("type", groupType));

		List resultList = getHibernateTemplate().findByCriteria(criteria);

		if (resultList.isEmpty()) {
			return null;
		}

		return (IGoldFeedGroup) resultList.get(0);
	}
}
