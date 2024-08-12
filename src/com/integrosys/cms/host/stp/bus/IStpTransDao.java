package com.integrosys.cms.host.stp.bus;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Aug 27, 2008
 * Time: 6:11:57 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IStpTransDao {
    IStpMasterTrans getMasterTransByPrimaryKey(Serializable key);
    /**
     * Create Stp master trx
     * @param masterTrans - instance of OBStpMasterTrans
     * @return
     */
    IStpMasterTrans createMasterTrans(IStpMasterTrans masterTrans);

    /**
     * Update Stp master trx
     * @param masterTrans - instance of OBStpMasterTrans to be updated
     * @return
     */
    IStpMasterTrans updateMasterTrans(IStpMasterTrans masterTrans);

    /**
     * Get Stp master trx by transaction ID
     * @param trxId - PK in TRANSACTION table
     * @return
     */
    IStpMasterTrans getMasterTransByTransactionId(String trxId);

    /**
     * Get a list of Stp master trx filter by statuses
     * @param statuses - list of STP_MASTER_TRANS.STATUS to be filtered
     * @return
     */
    List getMasterTransByStatus(List statuses);

    /**
     * Create Stp trx history
     * @param transHistory - OBStpTrans subclass with value replicated from parent
     * @return
     */
    IStpTrans createTransHistory(IStpTrans transHistory);

    /**
     * Update Stp trx history
     * @param transHistory - OBStpTrans subclass with value replicated from parent
     * @return
     */
    IStpTrans updateTransHistory(IStpTrans transHistory);

    /**
     * Get Stp trx history based on trx UID provided
     * @param trxUID - transaction unique ID sent in message header
     * @return
     */
    IStpTrans getTransHistoryByTrxUID(Long trxUID);

    /**
     * Create Stp transaction error - OBSOLETE
     * error is created via OBStpMasterTrans relationship
     * @param transError
     * @return
     */
    IStpTransError createTransError(IStpTransError transError);

    /**
     * Get Stp trx error by trx UID provided
     * @param trxUID - transaction unique ID sent in message header
     * @return
     */
    List getTransErrorByTrxUID(Long trxUID);

    /**
     * Update Stp trx error
     * @param transError - OBStpTrans to be updated
     * @return
     */
    IStpTransError updateTransError(IStpTransError transError);

    /**
     * Get a list of Stp trx yet to receive response from host
     * @return list of Stp trx with status SENDING
     */
    List getNoResponseStpTrans();

    /**
     * Delete obsolete Stp trx error based on trx UID provided
     * used before inserting new error riding on existing Stp trx
     * @param trxUID - transaction unique ID sent in message header
     */
    void deleteObsoleteTransError(Long trxUID);
}
