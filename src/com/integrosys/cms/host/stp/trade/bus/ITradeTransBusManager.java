package com.integrosys.cms.host.stp.trade.bus;

import com.integrosys.cms.host.stp.bus.IStpMasterTrans;
import com.integrosys.cms.host.stp.bus.IStpTrans;
import com.integrosys.cms.host.stp.bus.IStpTransError;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Sep 18, 2008
 * Time: 5:46:24 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ITradeTransBusManager {
    /**
     * Method to persist Stp master transaction OB in DB
     *
     * @param masterTrans
     * @return
     */
    ITradeMasterTrans createMasterTrans(ITradeMasterTrans masterTrans);

    /**
     * Method to update Stp master transaction OB in DB
     *
     * @param masterTrans
     * @return
     */
    ITradeMasterTrans updateMasterTrans(ITradeMasterTrans masterTrans);

    /**
     * Method to get Trade master transaction OB by primary key
     *
     * @param key
     * @return
     */
    ITradeMasterTrans getMasterTransByPrimaryKey(Serializable key);

    /**
     * Method to get Trade master transaction OB by transaction Id
     *
     * @param trxId
     * @return
     */
    ITradeMasterTrans getMasterTransByTransactionId(String trxId);

    /**
     * Method to get a list of Trade master transaction OB filter by status
     *
     * @param statuses
     * @return
     */
    List getMasterTransByStatus(List statuses);

    /**
     * Method to persist Trade transasction history OB in DB
     *
     * @param transHistory
     * @return
     */
    ITradeTrans createTransHistory(ITradeTrans transHistory);

    /**
     * Method to update Trade transaction history OB in DB
     *
     * @param transHistory
     * @return
     */
    ITradeTrans updateTransHistory(ITradeTrans transHistory);

    /**
     * Method to get Trade transaction history OB by transaction UID
     *
     * @param trxUID
     * @return
     */
    ITradeTrans getTransHistoryByTrxUID(Long trxUID);

    /**
     * Method to persist Trade transaction error OB in DB
     *
     * @param transError
     * @return
     */
    ITradeTransError createTransError(ITradeTransError transError);

    /**
     * Method to get Trade transaction error OB by transaction UID
     *
     * @param trxUID
     * @return
     */
    List getTransErrorByTrxUID(Long trxUID);

    /**
     * Method to update Trade transaction error OB in DB
     *
     * @param transError
     * @return
     */
    ITradeTransError updateTransError(ITradeTransError transError);

    /**
     * Method to get Trade transaction UID using seq from DB
     *
     * @return Trade transaction UID
     */
    String getSeqNum();

    /**
     * Method to get list of Trade transaction yet to receive response from SIBS
     *
     * @return
     */
    List getNoResponseTradeTrans();

    /**
     * Delete obsolete trans error before log transaction new error
     *
     * @param trxUID
     */
    void deleteObsoleteTransError(Long trxUID);

    /**
     * Method to get Trade transaction reference number to use in MBASE HDRNUM field
     *
     * @return trx ref number
     */
    public String getTrxRefNum();

    /**
     * Retrieve common code category entry description irregardless the status
     *
     * @param categoryCode
     * @param entryCode
     * @return
     */
    public String getCodeCategoryEntry(String categoryCode, String entryCode);

}