package com.integrosys.cms.host.stp.bus;

import com.integrosys.cms.host.stp.common.IStpConstants;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Aug 25, 2008
 * Time: 11:32:06 PM
 * To change this template use File | Settings | File Templates.
 */

public class StpTransDaoImpl extends HibernateDaoSupport implements IStpTransDao, IStpConstants {

    public IStpMasterTrans createMasterTrans(IStpMasterTrans masterTrans) {
        Long key = (Long) getHibernateTemplate().save(STP_MASTER_TRANS_ENTITY_NAME, masterTrans);
        masterTrans.setMasterTrxId(key);
        return masterTrans;
    }

    public IStpMasterTrans updateMasterTrans(IStpMasterTrans masterTrans) {
        getHibernateTemplate().update(STP_MASTER_TRANS_ENTITY_NAME, masterTrans);
        return (IStpMasterTrans) getHibernateTemplate().load(STP_MASTER_TRANS_ENTITY_NAME, masterTrans.getMasterTrxId());
    }

    /**
     * Get Stp Master Transaction by PK, excluding STP transaction with OBSOLETE status
     *
     * @param key
     * @return
     */
    public IStpMasterTrans getMasterTransByPrimaryKey(Serializable key) {
        return (IStpMasterTrans) getHibernateTemplate().get(STP_MASTER_TRANS_ENTITY_NAME, key);
    }

    public IStpMasterTrans getMasterTransByTransactionId(String trxId) {
        Map parameters = new HashMap();
        parameters.put("transactionId", trxId);
        DetachedCriteria criteria = DetachedCriteria.forEntityName(STP_MASTER_TRANS_ENTITY_NAME).add(Restrictions.allEq(parameters));
        List results = getHibernateTemplate().findByCriteria(criteria);

        if (results.isEmpty()) {
            return null;
        }
        return (IStpMasterTrans) results.get(0);
    }

    public List getMasterTransByStatus(List statuses) {
        DetachedCriteria criteria = DetachedCriteria.forEntityName(STP_MASTER_TRANS_ENTITY_NAME)
                .add(Restrictions.in("status", statuses))
                .addOrder(Order.asc("lastSubmissionDate"));
        return getHibernateTemplate().findByCriteria(criteria);
    }

    public IStpTrans createTransHistory(IStpTrans transHistory) {
        Long key = (Long) getHibernateTemplate().save(STP_TRANS_HISTORY_ENTITY_NAME, transHistory);
        transHistory.setTrxHistoryId(key);
        return transHistory;
    }

    public IStpTrans updateTransHistory(IStpTrans transHistory) {
        getHibernateTemplate().update(STP_TRANS_HISTORY_ENTITY_NAME, transHistory);
        return (IStpTrans) getHibernateTemplate().load(STP_TRANS_HISTORY_ENTITY_NAME, transHistory.getTrxHistoryId());
    }

    public IStpTrans getTransHistoryByTrxUID(Long trxUID) {
        Map parameters = new HashMap();
        parameters.put("trxUID", trxUID);
        DetachedCriteria criteria = DetachedCriteria.forEntityName(STP_TRANS_HISTORY_ENTITY_NAME).add(Restrictions.allEq(parameters));
        List results = getHibernateTemplate().findByCriteria(criteria);

        if (results.isEmpty()) {
            return null;
        }

        return (IStpTrans) results.get(0);
    }

    public IStpTransError createTransError(IStpTransError transError) {
        Long key = (Long) getHibernateTemplate().save(STP_TRANS_ERROR_ENTITY_NAME, transError);
        transError.setErrorId(key);
        return transError;
    }

    public List getTransErrorByTrxUID(Long trxUID) {
        Map parameters = new HashMap();
        parameters.put("trxUID", trxUID);
        DetachedCriteria criteria = DetachedCriteria.forEntityName(STP_TRANS_ERROR_ENTITY_NAME).add(Restrictions.allEq(parameters));

        return getHibernateTemplate().findByCriteria(criteria);
    }

    public IStpTransError updateTransError(IStpTransError transError) {
        getHibernateTemplate().update(STP_TRANS_ERROR_ENTITY_NAME, transError);

        return (IStpTransError) getHibernateTemplate().load(STP_TRANS_ERROR_ENTITY_NAME, transError.getErrorId());
    }

    public List getNoResponseStpTrans() {
        Map parameters = new HashMap();
        parameters.put("status", TRX_SENDING);
        DetachedCriteria criteria = DetachedCriteria.forEntityName(STP_TRANS_ENTITY_NAME).add(
                Restrictions.allEq(parameters));

        return getHibernateTemplate().findByCriteria(criteria);
    }

    public void deleteObsoleteTransError(Long trxUID) {
        Map parameters = new HashMap();
        parameters.put("trxUID", trxUID);
        DetachedCriteria criteria = DetachedCriteria.forEntityName(STP_TRANS_ERROR_ENTITY_NAME).add(
                Restrictions.allEq(parameters));

        List aList = getHibernateTemplate().findByCriteria(criteria);
        for (Iterator iterator = aList.iterator(); iterator.hasNext();) {
            IStpTransError err = (IStpTransError) iterator.next();
            getHibernateTemplate().delete(STP_TRANS_ERROR_ENTITY_NAME, err);
        }
    }
}