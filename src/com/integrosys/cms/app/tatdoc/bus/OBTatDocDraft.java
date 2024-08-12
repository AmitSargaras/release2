package com.integrosys.cms.app.tatdoc.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Aug 27, 2008
 * Time: 3:46:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class OBTatDocDraft implements ITatDocDraft {

    public long draftID = ICMSConstant.LONG_INVALID_VALUE;
    public String docDraftStage = null;
    public short draftNumber = 1;
    public Date draftDate = null;
    public String status = ICMSConstant.STATE_ACTIVE;
    public long referenceID = ICMSConstant.LONG_INVALID_VALUE;


    public long getDraftID() {
        return draftID;
    }

    public void setDraftID(long draftID) {
        this.draftID = draftID;
    }

    public String getDocDraftStage() {
        return docDraftStage;
    }

    public void setDocDraftStage(String docDraftStage) {
        this.docDraftStage = docDraftStage;
    }

    public short getDraftNumber() {
        return draftNumber;
    }

    public void setDraftNumber(short draftNumber) {
        this.draftNumber = draftNumber;
    }

    public Date getDraftDate() {
        return draftDate;
    }

    public void setDraftDate(Date draftDate) {
        this.draftDate = draftDate;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(long referenceID) {
        this.referenceID = referenceID;
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OBTatDocDraft that = (OBTatDocDraft) o;

        if (draftID != that.draftID) return false;
        if (draftNumber != that.draftNumber) return false;
        if (referenceID != that.referenceID) return false;
        if (docDraftStage != null ? !docDraftStage.equals(that.docDraftStage) : that.docDraftStage != null)
            return false;
        if (draftDate != null ? !draftDate.equals(that.draftDate) : that.draftDate != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (int) (draftID ^ (draftID >>> 32));
        result = 31 * result + (docDraftStage != null ? docDraftStage.hashCode() : 0);
        result = 31 * result + (int) draftNumber;
        result = 31 * result + (draftDate != null ? draftDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (int) (referenceID ^ (referenceID >>> 32));
        return result;
    }
}
