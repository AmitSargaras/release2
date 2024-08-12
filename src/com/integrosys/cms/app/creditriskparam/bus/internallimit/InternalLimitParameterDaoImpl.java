package com.integrosys.cms.app.creditriskparam.bus.internallimit;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Aug 10, 2010
 * Time: 1:53:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class InternalLimitParameterDaoImpl extends HibernateDaoSupport implements IInternalLimitParameterDao {

    public List findAll(String entityName) {
        DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName)
                                                    .add(Restrictions.ne("status", "DELETED"));

        List results = getHibernateTemplate().findByCriteria(criteria);

        if (results.isEmpty()) {
            return null;
        }
        return results;
    }

    public List findByGroupId(String entityName, Long groupId) {
        DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName)
                                                    .add(Restrictions.ne("status", "DELETED"))
                                                    .add(Restrictions.eq("groupID", groupId));

        List results = getHibernateTemplate().findByCriteria(criteria);

        if (results.isEmpty()) {
            return null;
        }
        return results;
    }

    public IInternalLimitParameter findByEntityType(String entityName, String entityType) {
        DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName)
                                                    .add(Restrictions.ne("status", "DELETED"))
                                                    .add(Restrictions.eq("descriptionCode", entityType));

        List results = getHibernateTemplate().findByCriteria(criteria);

        if (results.isEmpty()) {
            return null;
        }

        // No duplicates should exist
        return (IInternalLimitParameter)results.get(0);
    }

    public IInternalLimitParameter findByPrimaryKey(String entityName, Long key) {
         return (IInternalLimitParameter) getHibernateTemplate().get(entityName, key);
    }

    public IInternalLimitParameter createInternalLimitParameter(String entityName, IInternalLimitParameter ilp) {
        Long key = (Long)getHibernateTemplate().save(entityName, ilp);
        ilp.setId(key.longValue());
        return ilp;
    }

    public IInternalLimitParameter updateInternalLimitParameter(String entityName, IInternalLimitParameter ilp) {
        getHibernateTemplate().update(entityName, ilp);
        return (IInternalLimitParameter) getHibernateTemplate().load(entityName, new Long(ilp.getId()));
    }
}
