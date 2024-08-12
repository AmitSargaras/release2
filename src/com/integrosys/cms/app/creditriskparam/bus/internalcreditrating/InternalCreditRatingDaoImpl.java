package com.integrosys.cms.app.creditriskparam.bus.internalcreditrating;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Jul 23, 2010
 * Time: 1:54:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class InternalCreditRatingDaoImpl extends HibernateDaoSupport implements IInternalCreditRatingDao {

    public List findAll(String entityName) {
        DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.ne("status", "DELETED"));

        List results = getHibernateTemplate().findByCriteria(criteria);

        if (results.isEmpty()) {
            return null;
        }

        return results;
    }

    public List findByGroupId(String entityName, long groupId) {
        DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.eq("groupId", new Long(groupId))).add(Restrictions.ne("status", "DELETED")).addOrder(Order.asc("intCredRatId"));

        List results = getHibernateTemplate().findByCriteria(criteria);

        if (results.isEmpty()) {
            return null;
        }
        
        return results;
    }

    public IInternalCreditRating findByPrimaryKey(String entityName, long key) {
        return (IInternalCreditRating) getHibernateTemplate().get(entityName, new Long(key));
    }

    public IInternalCreditRating createInternalCreditRating(String entityName, IInternalCreditRating iCR) {
        Long key = (Long) getHibernateTemplate().save(entityName, iCR);
        iCR.setIntCredRatId(key);
        return iCR;
    }

    public IInternalCreditRating updateInternalCreditRating(String entityName, IInternalCreditRating iCR) {
        getHibernateTemplate().update(entityName, iCR);
        return (IInternalCreditRating) getHibernateTemplate().load(entityName, iCR.getIntCredRatId());
    }

}