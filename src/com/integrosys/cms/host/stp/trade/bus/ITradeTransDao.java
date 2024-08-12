package com.integrosys.cms.host.stp.trade.bus;

import com.integrosys.cms.host.stp.bus.IStpMasterTrans;
import com.integrosys.cms.host.stp.bus.IStpTrans;
import com.integrosys.cms.host.stp.bus.IStpTransError;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Aug 27, 2008
 * Time: 6:11:57 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ITradeTransDao {
    ITradeMasterTrans getMasterTransByPrimaryKey(Serializable key);
    /**
     * Create Trade master trx
     * @param masterTrans - instance of OBTradeMasterTrans
     * @return
     */
    ITradeMasterTrans createMasterTrans(ITradeMasterTrans masterTrans);

    /**
     * Update Trade master trx
     * @param masterTrans - instance of OBTradeMasterTrans to be updated
     * @return
     */
    ITradeMasterTrans updateMasterTrans(ITradeMasterTrans masterTrans);

    /**
     * Get Trade master trx by transaction ID
     * @param trxId - PK in TRANSACTION table
     * @return
     */
    ITradeMasterTrans getMasterTransByTransactionId(String trxId);

    /**
     * Get a list of Trade master trx filter by statuses
     * @param statuses - list of Trade_MASTER_TRANS.STATUS to be filtered
     * @return
     */
    List getMasterTransByStatus(List statuses);

    /**
     * Create Trade trx history
     * @param transHistory - OBTradeTrans subclass with value replicated from parent
     * @return
     */
    ITradeTrans createTransHistory(ITradeTrans transHistory);

    /**
     * Update Trade trx history
     * @param transHistory - OBTradeTrans subclass with value replicated from parent
     * @return
     */
    ITradeTrans updateTransHistory(ITradeTrans transHistory);

    /**
     * Get Trade trx history based on trx UID provided
     * @param trxUID - transaction unique ID sent in message header
     * @return
     */
    ITradeTrans getTransHistoryByTrxUID(Long trxUID);

    /**
     * Create Trade transaction error - OBSOLETE
     * error is created via OBTradeMasterTrans relationship
     * @param transError
     * @return
     */
    ITradeTransError createTransError(ITradeTransError transError);

    /**
     * Get Trade trx error by trx UID provided
     * @param trxUID - transaction unique ID sent in message header
     * @return
     */
    List getTransErrorByTrxUID(Long trxUID);

    /**
     * Update Trade trx error
     * @param transError - OBTradeTrans to be updated
     * @return
     */
    ITradeTransError updateTransError(ITradeTransError transError);

    /**
     * Get a list of Trade trx yet to receive response from host
     * @return list of Trade trx with status SENDING
     */
    List getNoResponseTradeTrans();

    /**
     * Delete obsolete Trade trx error based on trx UID provided
     * used before inserting new error riding on existing Trade trx
     * @param trxUID - transaction unique ID sent in message header
     */
    void deleteObsoleteTransError(Long trxUID);
}