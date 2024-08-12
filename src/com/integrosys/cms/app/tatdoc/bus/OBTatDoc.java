package com.integrosys.cms.app.tatdoc.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Aug 27, 2008
 * Time: 3:18:36 PM
 * To change this template use File | Settings | File Templates.
 */

public class OBTatDoc implements ITatDoc {

    private long limitProfileID = ICMSConstant.LONG_INVALID_VALUE;
    private long tatDocID = ICMSConstant.LONG_INVALID_VALUE;

    //Pre-Disbursement Fields
    private String isPAOrSolInvolvementReq;
	private Date solicitorInstructionDate;
    private Date fileReceivedFromBizCenter;
    private ITatDocDraft[] draftList;
    private Set draftListSet;               //for hibernate
    private Date docReceviceForPADate;
    private Date docPAExcuteDate;
    private String preDisbursementRemarks;

    //Disbursement fields
    private Date solicitorAdviseReleaseDate;
    private Date disbursementDocCompletionDate;
    private Date disbursementDate;
    private String disbursementRemarks;

    //Post Disbursement Fields
    private Date docCompletionDate;
    private String postDisbursementRemarks;

    //standard fields
    private String status = ICMSConstant.STATE_ACTIVE;
    private long versionTime = ICMSConstant.LONG_INVALID_VALUE;


    public long getLimitProfileID() {
        return limitProfileID;
    }

    public void setLimitProfileID(long limitProfileID) {
        this.limitProfileID = limitProfileID;
    }


    public long getTatDocID() {
        return tatDocID;
    }

    public void setTatDocID(long tatDocID) {
        this.tatDocID = tatDocID;
    }

    public String getIsPAOrSolInvolvementReq() {
		return isPAOrSolInvolvementReq;
	}

	public void setIsPAOrSolInvolvementReq(String isPAOrSolInvolvementReq) {
		this.isPAOrSolInvolvementReq = isPAOrSolInvolvementReq;
	}
    
    public Date getSolicitorInstructionDate() {
        return solicitorInstructionDate;
    }

    public void setSolicitorInstructionDate(Date solicitorInstructionDate) {
        this.solicitorInstructionDate = solicitorInstructionDate;
    }


    public Date getFileReceivedFromBizCenter() {
        return fileReceivedFromBizCenter;
    }

    public void setFileReceivedFromBizCenter(Date fileReceivedFromBizCenter) {
        this.fileReceivedFromBizCenter = fileReceivedFromBizCenter;
    }

    public ITatDocDraft[] getDraftList() {
        return draftList;
    }

    public void setDraftList(ITatDocDraft[] draftList) {
        this.draftList = draftList;
        this.draftListSet = (draftList == null) ? new HashSet(0) : new HashSet(Arrays.asList(draftList));
    }


    public Set getDraftListSet() {
        return draftListSet;
    }

    public void setDraftListSet(Set draftListSet) {
        this.draftListSet = draftListSet;
        this.draftList = (draftListSet == null) ? null : (ITatDocDraft[]) draftListSet.toArray(new ITatDocDraft[0]);
    }

    public Date getDocReceviceForPADate() {
        return docReceviceForPADate;
    }

    public void setDocReceviceForPADate(Date docReceviceForPADate) {
        this.docReceviceForPADate = docReceviceForPADate;
    }

    public Date getDocPAExcuteDate() {
        return docPAExcuteDate;
    }

    public void setDocPAExcuteDate(Date docPAExcuteDate) {
        this.docPAExcuteDate = docPAExcuteDate;
    }

    public String getPreDisbursementRemarks() {
        return preDisbursementRemarks;
    }

    public void setPreDisbursementRemarks(String preDisbursementRemarks) {
        this.preDisbursementRemarks = preDisbursementRemarks;
    }

    public Date getSolicitorAdviseReleaseDate() {
        return solicitorAdviseReleaseDate;
    }

    public void setSolicitorAdviseReleaseDate(Date solicitorAdviseReleaseDate) {
        this.solicitorAdviseReleaseDate = solicitorAdviseReleaseDate;
    }

    public Date getDisbursementDocCompletionDate() {
        return disbursementDocCompletionDate;
    }

    public void setDisbursementDocCompletionDate(Date disbursementDocCompletionDate) {
        this.disbursementDocCompletionDate = disbursementDocCompletionDate;
    }

    public Date getDisbursementDate() {
        return disbursementDate;
    }

    public void setDisbursementDate(Date disbursementDate) {
        this.disbursementDate = disbursementDate;
    }

    public String getDisbursementRemarks() {
        return disbursementRemarks;
    }

    public void setDisbursementRemarks(String disbursementRemarks) {
        this.disbursementRemarks = disbursementRemarks;
    }

    public Date getDocCompletionDate() {
        return docCompletionDate;
    }

    public void setDocCompletionDate(Date docCompletionDate) {
        this.docCompletionDate = docCompletionDate;
    }

    public String getPostDisbursementRemarks() {
        return postDisbursementRemarks;
    }

    public void setPostDisbursementRemarks(String postDisbursementRemarks) {
        this.postDisbursementRemarks = postDisbursementRemarks;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getVersionTime() {
        return versionTime;
    }

    public void setVersionTime(long versionTime) {
        this.versionTime = versionTime;
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OBTatDoc obTatDoc = (OBTatDoc) o;

        if (limitProfileID != obTatDoc.limitProfileID) return false;
        if (tatDocID != obTatDoc.tatDocID) return false;
        if (versionTime != obTatDoc.versionTime) return false;
        if (disbursementDate != null ? !disbursementDate.equals(obTatDoc.disbursementDate) : obTatDoc.disbursementDate != null)
            return false;
        if (disbursementDocCompletionDate != null ? !disbursementDocCompletionDate.equals(obTatDoc.disbursementDocCompletionDate) : obTatDoc.disbursementDocCompletionDate != null)
            return false;
        if (disbursementRemarks != null ? !disbursementRemarks.equals(obTatDoc.disbursementRemarks) : obTatDoc.disbursementRemarks != null)
            return false;
        if (docCompletionDate != null ? !docCompletionDate.equals(obTatDoc.docCompletionDate) : obTatDoc.docCompletionDate != null)
            return false;
        if (docPAExcuteDate != null ? !docPAExcuteDate.equals(obTatDoc.docPAExcuteDate) : obTatDoc.docPAExcuteDate != null)
            return false;
        if (docReceviceForPADate != null ? !docReceviceForPADate.equals(obTatDoc.docReceviceForPADate) : obTatDoc.docReceviceForPADate != null)
            return false;
        if (!Arrays.equals(draftList, obTatDoc.draftList)) return false;
        if (draftListSet != null ? !draftListSet.equals(obTatDoc.draftListSet) : obTatDoc.draftListSet != null)
            return false;
        if (fileReceivedFromBizCenter != null ? !fileReceivedFromBizCenter.equals(obTatDoc.fileReceivedFromBizCenter) : obTatDoc.fileReceivedFromBizCenter != null)
            return false;
        if (postDisbursementRemarks != null ? !postDisbursementRemarks.equals(obTatDoc.postDisbursementRemarks) : obTatDoc.postDisbursementRemarks != null)
            return false;
        if (preDisbursementRemarks != null ? !preDisbursementRemarks.equals(obTatDoc.preDisbursementRemarks) : obTatDoc.preDisbursementRemarks != null)
            return false;
        if (solicitorAdviseReleaseDate != null ? !solicitorAdviseReleaseDate.equals(obTatDoc.solicitorAdviseReleaseDate) : obTatDoc.solicitorAdviseReleaseDate != null)
            return false;
        if (solicitorInstructionDate != null ? !solicitorInstructionDate.equals(obTatDoc.solicitorInstructionDate) : obTatDoc.solicitorInstructionDate != null)
            return false;
        if (status != null ? !status.equals(obTatDoc.status) : obTatDoc.status != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (int) (limitProfileID ^ (limitProfileID >>> 32));
        result = 31 * result + (int) (tatDocID ^ (tatDocID >>> 32));
        result = 31 * result + (solicitorInstructionDate != null ? solicitorInstructionDate.hashCode() : 0);
        result = 31 * result + (fileReceivedFromBizCenter != null ? fileReceivedFromBizCenter.hashCode() : 0);
        result = 31 * result + (draftList != null ? draftList.hashCode() : 0);
        result = 31 * result + (draftListSet != null ? draftListSet.hashCode() : 0);
        result = 31 * result + (docReceviceForPADate != null ? docReceviceForPADate.hashCode() : 0);
        result = 31 * result + (docPAExcuteDate != null ? docPAExcuteDate.hashCode() : 0);
        result = 31 * result + (preDisbursementRemarks != null ? preDisbursementRemarks.hashCode() : 0);
        result = 31 * result + (solicitorAdviseReleaseDate != null ? solicitorAdviseReleaseDate.hashCode() : 0);
        result = 31 * result + (disbursementDocCompletionDate != null ? disbursementDocCompletionDate.hashCode() : 0);
        result = 31 * result + (disbursementDate != null ? disbursementDate.hashCode() : 0);
        result = 31 * result + (disbursementRemarks != null ? disbursementRemarks.hashCode() : 0);
        result = 31 * result + (docCompletionDate != null ? docCompletionDate.hashCode() : 0);
        result = 31 * result + (postDisbursementRemarks != null ? postDisbursementRemarks.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (int) (versionTime ^ (versionTime >>> 32));
        return result;
    }
}
