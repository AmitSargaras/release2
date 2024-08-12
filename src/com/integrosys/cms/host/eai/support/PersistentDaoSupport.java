package com.integrosys.cms.host.eai.support;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.Validate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.cms.host.eai.core.IPersistentDao;

/**
 * <p>
 * ORM based DAO using hibernate
 * 
 * <p>
 * This is a abstract class, prevent to be used directly in the code
 * 
 * @author Chong Jun Yong
 * @since 18.08.2008
 */
public abstract class PersistentDaoSupport extends HibernateDaoSupport implements IPersistentDao {

	public void remove(Object object, Class classRequired) {
		Validate.notNull(object, "'object' passed in for persistence action must not be null.");
		Validate.notNull(classRequired, "'classRequired' must not be null.");

		checkObjectMatchClassRequired(object, classRequired);

		getHibernateTemplate().delete(object);
	}

	public Object retrieve(Serializable id, Class classRequired) {
		Validate.notNull(id, "'id' passed in for persistence action must not be null.");
		Validate.notNull(classRequired, "'classRequired' must not be null.");
		
		return getHibernateTemplate().get(classRequired, id);
	}

	public Object retrieve(Serializable id, String entityName) {
		Validate.notNull(id, "'id' passed in for persistence action must not be null.");
		Validate.notNull(entityName, "'entityName' must not be null.");
		return getHibernateTemplate().get(entityName, id);
	}

	public Object retrieveObjectByParameters(Map parameters, String statusIndicatorFieldName, Class classRequired) {
		Validate.notNull(parameters, "'parameters' passed in for restrictions usage must not be null");
		Validate.notNull(statusIndicatorFieldName,
				"'statusIndicatorFieldName' passed in for restrictions usage must not be null");
		Validate.notNull(classRequired, "'classRequired' must not be null.");

		DetachedCriteria criteria = DetachedCriteria.forClass(classRequired).add(Restrictions.allEq(parameters)).add(
				Restrictions.ne(statusIndicatorFieldName, "D"));

		List objects = getHibernateTemplate().findByCriteria(criteria, 0, 1);
		return (objects.isEmpty()) ? null : objects.get(0);
	}

	public Object retrieveNonDeletedObjectByParameters(Map parameters, String statusIndicatorFieldName,
			Class classRequired, String entityName) {
		Validate.notNull(parameters, "'parameters' passed in for restrictions usage must not be null");
		Validate.notNull(statusIndicatorFieldName,
				"'statusIndicatorFieldName' passed in for restrictions usage must not be null");
		Validate.notNull(classRequired, "'classRequired' must not be null.");

		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.allEq(parameters)).add(
				Restrictions.ne(statusIndicatorFieldName, "D"));

		List objects = getHibernateTemplate().findByCriteria(criteria, 0, 1);
		return (objects.isEmpty()) ? null : objects.get(0);
	}

	public Object retrieveNonDeletedObjectByParameters(Map parameters, String statusIndicatorFieldName,
			Class classRequired) {
		Validate.notNull(parameters, "'parameters' passed in for restrictions usage must not be null");
		Validate.notNull(statusIndicatorFieldName,
				"'statusIndicatorFieldName' passed in for restrictions usage must not be null");
		Validate.notNull(classRequired, "'classRequired' must not be null.");

		DetachedCriteria criteria = DetachedCriteria.forClass(classRequired).add(Restrictions.allEq(parameters)).add(
				Restrictions.ne(statusIndicatorFieldName, "D"));
		List objects = getHibernateTemplate().findByCriteria(criteria, 0, 1);
		return (objects.isEmpty()) ? null : objects.get(0);
	}

	public List retrieveNonDeletedObjectsListByParameters(Map parameters, String statusIndicatorFieldName,
			Class classRequired) {
		Validate.notNull(parameters, "'parameters' passed in for restrictions usage must not be null");
		Validate.notNull(statusIndicatorFieldName,
				"'statusIndicatorFieldName' passed in for restrictions usage must not be null");
		Validate.notNull(classRequired, "'classRequired' must not be null.");

		DetachedCriteria criteria = DetachedCriteria.forClass(classRequired).add(Restrictions.allEq(parameters)).add(
				Restrictions.ne(statusIndicatorFieldName, "D"));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	public List retrieveNonDeletedObjectsListByParameters(Map parameters, String statusIndicatorFieldName,
			String entityName) {
		Validate.notNull(parameters, "'parameters' passed in for restrictions usage must not be null");
		Validate.notNull(statusIndicatorFieldName,
				"'statusIndicatorFieldName' passed in for restrictions usage must not be null");
		Validate.notNull(entityName, "'entity name' must not be null.");
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName ).add(Restrictions.allEq(parameters)).add(
				Restrictions.ne(statusIndicatorFieldName, "D"));

		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	public Object retrieveObjectByParameters(Map parameters, Class classRequired, String entityName) {
		Validate.notNull(parameters, "'parameters' passed in for restrictions usage must not be null");
		Validate.notNull(classRequired, "'classRequired' must not be null.");
		Validate.notNull(entityName, "'entityName' must not be null.");

		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.allEq(parameters));

		List objects = getHibernateTemplate().findByCriteria(criteria, 0, 1);
		return (objects.isEmpty()) ? null : objects.get(0);
	}

	public Object retrieveObjectByParameters(Map parameters, Class classRequired) {
		Validate.notNull(parameters, "'parameters' passed in for restrictions usage must not be null");
		Validate.notNull(classRequired, "'classRequired' must not be null.");
		DetachedCriteria criteria = DetachedCriteria.forClass(classRequired).add(Restrictions.allEq(parameters));
		List objects = getHibernateTemplate().findByCriteria(criteria, 0, 1);
		return (objects.isEmpty()) ? null : objects.get(0);
	}

	public List retrieveObjectListByParameters(Map parameters, Class classRequired) {
		Validate.notNull(parameters, "'parameters' passed in for restrictions usage must not be null");
		Validate.notNull(classRequired, "'classRequired' must not be null.");

		DetachedCriteria criteria = DetachedCriteria.forClass(classRequired).add(Restrictions.allEq(parameters));

		/* changed to get the list or records */
		return getHibernateTemplate().findByCriteria(criteria, 0, 1);
		// return getHibernateTemplate().findByCriteria(criteria);
	}

	public Serializable store(Object object, Class classRequired) {
		Validate.notNull(object, "'object' passed in for persistence action must not be null.");
		Validate.notNull(classRequired, "'classRequired' must not be null.");

		checkObjectMatchClassRequired(object, classRequired);
		return getHibernateTemplate().save(object);

	}

	public Serializable store(Object object, Class classRequired, String entityName) {
		Validate.notNull(object, "'object' passed in for persistence action must not be null.");
		Validate.notNull(classRequired, "'classRequired' must not be null.");
		checkObjectMatchClassRequired(object, classRequired);
		return getHibernateTemplate().save(entityName, object);

	}

	public void update(Object object, Class classRequired) {
		Validate.notNull(object, "'object' passed in for persistence action must not be null.");
		Validate.notNull(classRequired, "'classRequired' must not be null.");
		checkObjectMatchClassRequired(object, classRequired);
		getHibernateTemplate().update(object);
	}

	public void update(Object object, Class classRequired, String entityName) {
		Validate.notNull(object, "'object' passed in for persistence action must not be null.");
		Validate.notNull(classRequired, "'classRequired' must not be null.");
		Validate.notNull(entityName, "'entityName' must not be null.");
		checkObjectMatchClassRequired(object, classRequired);
		getHibernateTemplate().update(entityName, object);
	}

	protected void checkObjectMatchClassRequired(Object object, Class classRequired) {
		if (object.getClass() != classRequired) {
			throw new IllegalStateException("object [" + object + "] of type [" + object.getClass()
					+ "] is not type of class required [" + classRequired + "]");
		} 
	}
	
	/* to get the list or records */
	public List retrieveObjectsListByParameters(Map parameters, Class classRequired) {
		Validate.notNull(parameters, "'parameters' passed in for restrictions usage must not be null");
		Validate.notNull(classRequired, "'classRequired' must not be null.");

		DetachedCriteria criteria = DetachedCriteria.forClass(classRequired).add(Restrictions.allEq(parameters));

		
		 return getHibernateTemplate().findByCriteria(criteria);
	}

}
