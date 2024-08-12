package com.integrosys.cms.app.creditriskparam.bus.bankentitybranch;

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
public class BankEntityBranchParamDaoImpl extends HibernateDaoSupport implements IBankEntityBranchParamDao {

    public List findAll(String entityName) {
        DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.eq("status", "ACTIVE")).addOrder(Order.asc("entityType"));

        List results = getHibernateTemplate().findByCriteria(criteria);

        if (results.isEmpty()) {
            return null;
        }

        return results;
    }

    public List findByGroupId(String entityName, long groupId) {
        DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.eq("groupId", new Long(groupId))).add(Restrictions.eq("status", "ACTIVE")).addOrder(Order.asc("entityType"));

        List results = getHibernateTemplate().findByCriteria(criteria);

        if (results.isEmpty()) {
            return null;
        }

        return results;
    }

    public IBankEntityBranchParam findByPrimaryKey(String entityName, long key) {
        return (IBankEntityBranchParam) getHibernateTemplate().get(entityName, new Long(key));
    }

    public IBankEntityBranchParam createInternalCreditRating(String entityName, IBankEntityBranchParam iBEBP) {
        Long key = (Long) getHibernateTemplate().save(entityName, iBEBP);
        iBEBP.setBankEntityId(key);
        return iBEBP;
    }

    public IBankEntityBranchParam updateInternalCreditRating(String entityName, IBankEntityBranchParam iBEBP) {
        getHibernateTemplate().update(entityName, iBEBP);
        return (IBankEntityBranchParam) getHibernateTemplate().load(entityName, iBEBP.getBankEntityId());
    }

}