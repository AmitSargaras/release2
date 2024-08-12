package com.integrosys.cms.host.stp.trade.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.host.stp.bus.IStpMasterTrans;

import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Aug 22, 2008
 * Time: 10:31:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class OBTradeMasterTrans implements ITradeMasterTrans {
    private Long masterTrxId;
    private String transactionId;
    private String status;
    private Long referenceId;
    private String transactionType;
    private Set trxEntriesSet;
    private Timestamp lastSubmissionDate;

    public Set getTrxEntriesSet() {
        return trxEntriesSet;
    }

    public void setTrxEntriesSet(Set trxEntriesSet) {
        this.trxEntriesSet = trxEntriesSet;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Long getMasterTrxId() {
        return masterTrxId;
    }

    public void setMasterTrxId(Long masterTrxId) {
        this.masterTrxId = masterTrxId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Timestamp getLastSubmissionDate() {
        return lastSubmissionDate;
    }

    public void setLastSubmissionDate(Timestamp lastSubmissionDate) {
        this.lastSubmissionDate = lastSubmissionDate;
    }

    public String toString() {
        return AccessorUtil.printMethodValue(this);
    }
}