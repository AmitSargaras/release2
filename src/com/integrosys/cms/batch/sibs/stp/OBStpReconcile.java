package com.integrosys.cms.batch.sibs.stp;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Oct 2, 2008
 * Time: 11:27:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class OBStpReconcile {
    private String recordType;
    private String trxUID;
    private String filler;

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getTrxUID() {
        return trxUID;
    }

    public void setTrxUID(String trxUID) {
        this.trxUID = trxUID;
    }

    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
    }

    public String toString() {
        return trxUID;
    }
}
