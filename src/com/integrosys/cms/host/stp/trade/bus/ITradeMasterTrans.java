package com.integrosys.cms.host.stp.trade.bus;

import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: USER
 * Date: Aug 27, 2008
 * Time: 5:21:22 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ITradeMasterTrans {
    Set getTrxEntriesSet();

    void setTrxEntriesSet(Set trxEntriesSet);

    String getStatus();

    void setStatus(String status);

    Long getReferenceId();

    void setReferenceId(Long referenceId);

    String getTransactionType();

    void setTransactionType(String transactionType);

    Long getMasterTrxId();

    void setMasterTrxId(Long masterTrxId);

    String getTransactionId();

    void setTransactionId(String transactionId);

    Timestamp getLastSubmissionDate();

    void setLastSubmissionDate(Timestamp lastSubmissionDate);
}