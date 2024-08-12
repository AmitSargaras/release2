package com.integrosys.cms.app.creditriskparam.bus.countrylimit;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Jul 23, 2010
 * Time: 1:54:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class CountryLimitDaoImpl extends HibernateDaoSupport implements ICountryLimitDao {

    public List findAll(String entityName) {
        DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.ne("status", "DELETED"));

        List results = getHibernateTemplate().findByCriteria(criteria);

        if (results.isEmpty()) {
            return null;
        }

        return results;
    }

    public ICountryLimit findByCountryCode(String entityName, String countryCode) {
        DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.eq("countryCode", countryCode)).add(Restrictions.ne("status", "DELETED"));

        List results = getHibernateTemplate().findByCriteria(criteria);

        if (!results.isEmpty()) {
            return (ICountryLimit) results.get(0);
        }

        return null;
    }

    public List findByGroupId(String entityName, long groupId) {
        DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.eq("groupID", new Long(groupId))).add(Restrictions.ne("status", "DELETED")).addOrder(Order.asc("countryLimitID"));

        List results = getHibernateTemplate().findByCriteria(criteria);

        if (results.isEmpty()) {
            return null;
        }
        
        return results;
    }

    public ICountryLimit findByPrimaryKey(String entityName, long key) {
        return (ICountryLimit) getHibernateTemplate().get(entityName, new Long(key));
    }

    public ICountryLimit createCountryLimit(String entityName, ICountryLimit iCL) {
        Long key = (Long) getHibernateTemplate().save(entityName, iCL);
        iCL.setCountryLimitID(key.longValue());
        return iCL;
    }

    public ICountryLimit updateCountryLimit(String entityName, ICountryLimit iCL) {
        getHibernateTemplate().update(entityName, iCL);
        return (ICountryLimit) getHibernateTemplate().load(entityName, new Long(iCL.getCountryLimitID()));
    }

}