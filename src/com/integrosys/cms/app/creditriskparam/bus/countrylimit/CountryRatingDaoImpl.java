package com.integrosys.cms.app.creditriskparam.bus.countrylimit;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Jul 23, 2010
 * Time: 1:54:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class CountryRatingDaoImpl extends HibernateDaoSupport implements ICountryRatingDao {

    public List findAll(String entityName) {
        List results = getHibernateTemplate().findByCriteria(DetachedCriteria.forEntityName(entityName));

        if (results.isEmpty()) {
            return null;
        }

        return results;
    }

    public List findByGroupId(String entityName, long groupId) {
        DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.eq("groupID", new Long(groupId))).addOrder(Order.asc("countryRatingID"));

        List results = getHibernateTemplate().findByCriteria(criteria);

        if (results.isEmpty()) {
            return null;
        }

        return results;
    }

    public ICountryRating findByPrimaryKey(String entityName, long key) {
        return (ICountryRating) getHibernateTemplate().get(entityName, new Long(key));
    }

    public ICountryRating createCountryRating(String entityName, ICountryRating iCR) {
        Long key = (Long) getHibernateTemplate().save(entityName, iCR);
        iCR.setCountryRatingID(key.longValue());
        return iCR;
    }

    public ICountryRating updateCountryRating(String entityName, ICountryRating iCR) {
        getHibernateTemplate().update(entityName, iCR);
        return (ICountryRating) getHibernateTemplate().load(entityName, new Long(iCR.getCountryRatingID()));
    }

}