package com.integrosys.cms.app.feed.bus.stockindex;

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
public class StockIndexDaoImpl extends HibernateDaoSupport implements IStockIndexDao {

	public IStockIndexFeedEntry createStockIndexFeedEntry(String entityName, IStockIndexFeedEntry stockIndexFeedEntry) {
		Long key = (Long) getHibernateTemplate().save(entityName, stockIndexFeedEntry);

		return (IStockIndexFeedEntry) getHibernateTemplate().get(entityName, key);
	}

	public IStockIndexFeedGroup createStockIndexFeedGroup(String entityName, IStockIndexFeedGroup stockIndexFeedGroup) {
		Long key = (Long) getHibernateTemplate().save(entityName, stockIndexFeedGroup);

		return (IStockIndexFeedGroup) getHibernateTemplate().get(entityName, key);
	}

	public void deleteStockIndexFeedEntry(String entityName, IStockIndexFeedEntry stockIndexFeedEntry) {
		// TODO Auto-generated method stub

	}

	public void deleteStockIndexFeedGroup(String entityName, IStockIndexFeedGroup stockIndexFeedGroup) {
		// TODO Auto-generated method stub

	}

	public IStockIndexFeedEntry getStockIndexFeedEntryByPrimaryKey(String entityName, Serializable key) {
		return (IStockIndexFeedEntry) getHibernateTemplate().get(entityName, key);
	}

	public IStockIndexFeedGroup getStockIndexFeedGroupByPrimaryKey(String entityName, Serializable key) {
		return (IStockIndexFeedGroup) getHibernateTemplate().get(entityName, key);
	}

	public IStockIndexFeedEntry updateStockIndexFeedEntry(String entityName, IStockIndexFeedEntry stockIndexFeedEntry) {
		getHibernateTemplate().update(entityName, stockIndexFeedEntry);

		return (IStockIndexFeedEntry) getHibernateTemplate().get(entityName,
				new Long(stockIndexFeedEntry.getStockIndexFeedEntryID()));
	}

	public IStockIndexFeedGroup updateStockIndexFeedGroup(String entityName, IStockIndexFeedGroup stockIndexFeedGroup) {
		getHibernateTemplate().update(entityName, stockIndexFeedGroup);

		return (IStockIndexFeedGroup) getHibernateTemplate().get(entityName,
				new Long(stockIndexFeedGroup.getStockIndexFeedGroupID()));
	}

	public IStockIndexFeedEntry getStockIndexFeedEntryByRic(String entityName, String ric) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.eq("ric", ric));

		List results = getHibernateTemplate().findByCriteria(criteria);

		if (results.isEmpty()) {
			return null;
		}

		return (IStockIndexFeedEntry) results.get(0);
	}

	public IStockIndexFeedGroup getStockIndexFeedGroupByTypeAndSubType(String entityName, String type, String subType) {
		Map parameters = new HashMap();
		parameters.put("type", type);
		parameters.put("subType", subType);

		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.allEq(parameters));

		List results = getHibernateTemplate().findByCriteria(criteria);

		if (results.isEmpty()) {
			return null;
		}

		return (IStockIndexFeedGroup) results.get(0);
	}

}
