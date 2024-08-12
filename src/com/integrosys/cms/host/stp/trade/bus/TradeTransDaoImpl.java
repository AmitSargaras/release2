package com.integrosys.cms.host.stp.trade.bus;

import com.integrosys.cms.host.stp.bus.IStpMasterTrans;
import com.integrosys.cms.host.stp.bus.IStpTrans;
import com.integrosys.cms.host.stp.bus.IStpTransDao;
import com.integrosys.cms.host.stp.bus.IStpTransError;
import com.integrosys.cms.host.stp.common.IStpConstants;
import com.integrosys.cms.host.stp.trade.common.ITradeConstants;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
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

public class TradeTransDaoImpl extends HibernateDaoSupport implements ITradeTransDao, ITradeConstants {

    public ITradeMasterTrans createMasterTrans(ITradeMasterTrans masterTrans) {
        Long key = (Long) getHibernateTemplate().save(TRADE_MASTER_TRANS_ENTITY_NAME, masterTrans);
        masterTrans.setMasterTrxId(key);
        return masterTrans;
    }

    public ITradeMasterTrans updateMasterTrans(ITradeMasterTrans masterTrans) {
        getHibernateTemplate().update(TRADE_MASTER_TRANS_ENTITY_NAME, masterTrans);
        return (ITradeMasterTrans) getHibernateTemplate().load(TRADE_MASTER_TRANS_ENTITY_NAME, masterTrans.getMasterTrxId());
    }

    /**
     * Get Trade Master Transaction by PK, excluding STP transaction with OBSOLETE status
     *
     * @param key
     * @return
     */
    public ITradeMasterTrans getMasterTransByPrimaryKey(Serializable key) {
        return (ITradeMasterTrans) getHibernateTemplate().get(TRADE_MASTER_TRANS_ENTITY_NAME, key);
    }

    public ITradeMasterTrans getMasterTransByTransactionId(String trxId) {
        Map parameters = new HashMap();
        parameters.put("transactionId", trxId);
        DetachedCriteria criteria = DetachedCriteria.forEntityName(TRADE_MASTER_TRANS_ENTITY_NAME).add(Restrictions.allEq(parameters));
        List results = getHibernateTemplate().findByCriteria(criteria);

        if (results.isEmpty()) {
            return null;
        }
        return (ITradeMasterTrans) results.get(0);
    }

    public List getMasterTransByStatus(List statuses) {
        DetachedCriteria criteria = DetachedCriteria.forEntityName(TRADE_MASTER_TRANS_ENTITY_NAME)
                .add(Restrictions.in("status", statuses))
                .addOrder(Order.asc("lastSubmissionDate"));
        return  getHibernateTemplate().findByCriteria(criteria);
    }

    public ITradeTrans createTransHistory(ITradeTrans transHistory) {
        Long key = (Long) getHibernateTemplate().save(TRADE_TRANS_HISTORY_ENTITY_NAME, transHistory);
        transHistory.setTrxHistoryId(key);
        return transHistory;
    }

    public ITradeTrans updateTransHistory(ITradeTrans transHistory) {
        getHibernateTemplate().update(TRADE_TRANS_HISTORY_ENTITY_NAME, transHistory);
        return (ITradeTrans) getHibernateTemplate().load(TRADE_TRANS_HISTORY_ENTITY_NAME, transHistory.getTrxHistoryId());
    }

    public ITradeTrans getTransHistoryByTrxUID(Long trxUID) {
        Map parameters = new HashMap();
        parameters.put("trxUID", trxUID);
        DetachedCriteria criteria = DetachedCriteria.forEntityName(TRADE_TRANS_HISTORY_ENTITY_NAME).add(Restrictions.allEq(parameters));
        List results = getHibernateTemplate().findByCriteria(criteria);

        if (results.isEmpty()) {
            return null;
        }

        return (ITradeTrans) results.get(0);
    }

    public List getTransErrorByTrxUID(Long trxUID) {
        return null;
        //TODO
/*        Map parameters = new HashMap();
        parameters.put("trxUID", trxUID);
        DetachedCriteria criteria = DetachedCriteria.forEntityName(TRADE_TRANS_ERROR_ENTITY_NAME).add(Restrictions.allEq(parameters));

        return getHibernateTemplate().findByCriteria(criteria);*/
    }

    public ITradeTransError createTransError(ITradeTransError transError) {
        return null;
        //TODO
        /*Long key = (Long) getHibernateTemplate().save(TRADE_TRANS_ERROR_ENTITY_NAME, transError);
        transError.setErrorId(key);
        return transError;*/
    }

    public ITradeTransError updateTransError(ITradeTransError transError) {
        //TODO
        return null;
        /*getHibernateTemplate().update(TRADE_TRANS_ERROR_ENTITY_NAME, transError);

        return (ITradeTransError) getHibernateTemplate().load(TRADE_TRANS_ERROR_ENTITY_NAME, transError.getErrorId());*/
    }

    public List getNoResponseTradeTrans() {
        //TODO
/*        Map parameters = new HashMap();
        parameters.put("status", TRX_SENDING);
        DetachedCriteria criteria = DetachedCriteria.forEntityName(TRADE_TRANS_ENTITY_NAME).add(
                Restrictions.allEq(parameters));

        return getHibernateTemplate().findByCriteria(criteria);*/
        return null;
    }

    public void deleteObsoleteTransError(Long trxUID) {
        //TODO
/*        Map parameters = new HashMap();
        parameters.put("trxUID", trxUID);
        DetachedCriteria criteria = DetachedCriteria.forEntityName(TRADE_TRANS_ERROR_ENTITY_NAME).add(
                Restrictions.allEq(parameters));

        List aList = getHibernateTemplate().findByCriteria(criteria);
        for (Iterator iterator = aList.iterator(); iterator.hasNext();) {
            ITradeTransError err = (ITradeTransError) iterator.next();
            getHibernateTemplate().delete(TRADE_TRANS_ERROR_ENTITY_NAME, err);
        }*/
    }
}