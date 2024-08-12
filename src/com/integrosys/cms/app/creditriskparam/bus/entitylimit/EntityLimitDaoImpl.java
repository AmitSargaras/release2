package com.integrosys.cms.app.creditriskparam.bus.entitylimit;

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
public class EntityLimitDaoImpl extends HibernateDaoSupport implements IEntityLimitDao {

    public List findAll(String entityName) {
        DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.ne("status", "DELETED"));

        List results = getHibernateTemplate().findByCriteria(criteria);

        if (results.isEmpty()) {
            return null;
        }

        return results;
    }

    public IEntityLimit findBySubProfileID(String entityName, long subProfileId) {
        DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.eq("customerID", new Long(subProfileId))).add(Restrictions.ne("status", "DELETED"));

        List results = getHibernateTemplate().findByCriteria(criteria);

        if (results.isEmpty()) {
            return null;
        }

        return (IEntityLimit) results.get(0);
    }

    public List findByGroupID(String entityName, long groupId) {
        DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.eq("groupID", new Long(groupId))).add(Restrictions.ne("status", "DELETED")).addOrder(Order.asc("entityLimitID"));

        List results = getHibernateTemplate().findByCriteria(criteria);

        if (results.isEmpty()) {
            return null;
        }

        return results;
    }

    public IEntityLimit findByPrimaryKey(String entityName, long key) {
        return (IEntityLimit) getHibernateTemplate().get(entityName, new Long(key));
    }

    public IEntityLimit createEntityLimit(String entityName, IEntityLimit iEL) {
        Long key = (Long) getHibernateTemplate().save(entityName, iEL);
        iEL.setEntityLimitID(key.longValue());
        return iEL;
    }

    public IEntityLimit updateEntityLimit(String entityName, IEntityLimit iEl) {
        getHibernateTemplate().update(entityName, iEl);
        return (IEntityLimit) getHibernateTemplate().load(entityName, new Long(iEl.getEntityLimitID()));
    }

}