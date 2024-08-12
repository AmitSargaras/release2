package com.integrosys.cms.app.digitalLibrary.bus;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

/**
 * Implementation of {@link IDigitalLibraryDao} using Hibernate
 * 
 * @author Chong Jun Yong
 * @since 1.1
 */
public class DigitalLibraryDaoImpl extends HibernateDaoSupport implements IDigitalLibraryDao {

	public IDigitalLibraryEntry createDigitalLibraryEntry(String entityName, IDigitalLibraryEntry digitalLibraryEntry) {
		Long key = (Long) getHibernateTemplate().save(entityName, digitalLibraryEntry);

		return (IDigitalLibraryEntry) getHibernateTemplate().get(entityName, key);
	}

	public IDigitalLibraryGroup createDigitalLibraryGroup(String entityName, IDigitalLibraryGroup digitalLibraryGroup) {
		Long key = (Long) getHibernateTemplate().save(entityName, digitalLibraryGroup);

		return (IDigitalLibraryGroup) getHibernateTemplate().get(entityName, key);
	}

	public void deleteDigitalLibraryEntry(String entityName, IDigitalLibraryEntry digitalLibraryEntry) {
		getHibernateTemplate().delete(entityName, digitalLibraryEntry);
	}

	public void deleteDigitalLibraryGroup(String entityName, IDigitalLibraryGroup digitalLibraryGroup) {
		getHibernateTemplate().delete(entityName, digitalLibraryGroup);
	}

	public IDigitalLibraryEntry getDigitalLibraryEntryByPrimaryKey(String entityName, Serializable key) {
		return (IDigitalLibraryEntry) getHibernateTemplate().get(entityName, key);
	}

	public IDigitalLibraryGroup getDigitalLibraryGroupByGroupType(String entityName, String groupType) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.eq("type", groupType));

		List resultList = getHibernateTemplate().findByCriteria(criteria);

		if (resultList.isEmpty()) {
			return null;
		}

		return (IDigitalLibraryGroup) resultList.get(0);
	}

	public IDigitalLibraryEntry getDigitalLibraryEntryByRic(String entityName, String ric) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.eq("ric", ric));

		List resultList = getHibernateTemplate().findByCriteria(criteria);

		if (resultList.isEmpty()) {
			return null;
		}

		return (IDigitalLibraryEntry) resultList.get(0);
	}

	public IDigitalLibraryGroup getDigitalLibraryGroupByPrimaryKey(String entityName, Serializable key) {
		return (IDigitalLibraryGroup) getHibernateTemplate().get(entityName, key);
	}

	public IDigitalLibraryEntry updateDigitalLibraryEntry(String entityName, IDigitalLibraryEntry digitalLibraryEntry) {
		getHibernateTemplate().update(entityName, digitalLibraryEntry);

		return (IDigitalLibraryEntry) getHibernateTemplate().get(entityName, new Long(digitalLibraryEntry.getDigitalLibraryEntryID()));
	}

	public IDigitalLibraryGroup updateDigitalLibraryGroup(String entityName, IDigitalLibraryGroup digitalLibraryGroup) {
		getHibernateTemplate().update(entityName, digitalLibraryGroup);

		return (IDigitalLibraryGroup) getHibernateTemplate().get(entityName, new Long(digitalLibraryGroup.getDigitalLibraryGroupID()));
	}
}
