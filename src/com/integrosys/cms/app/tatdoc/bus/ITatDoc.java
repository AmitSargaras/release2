package com.integrosys.cms.app.tatdoc.bus;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

import java.util.Date;
import java.util.Set;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Aug 27, 2008
 * Time: 3:43:02 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ITatDoc extends IValueObject, Serializable {

    public long getLimitProfileID();
    public void setLimitProfileID(long limitProfileID);
    public long getTatDocID();
    public void setTatDocID(long tatID);

    public String getIsPAOrSolInvolvementReq();
	public void setIsPAOrSolInvolvementReq(String isPAOrSolInvolvementReq);
    public Date getSolicitorInstructionDate();
    public void setSolicitorInstructionDate(Date solicitorInstructionDate);
    public Date getFileReceivedFromBizCenter();
    public void setFileReceivedFromBizCenter(Date fileReceivedFromBizCenter);
    public ITatDocDraft[] getDraftList();
    public void setDraftList(ITatDocDraft[] draftList);
    public Set getDraftListSet();
    public void setDraftListSet(Set draftListSet);
    public Date getDocReceviceForPADate();
    public void setDocReceviceForPADate(Date docReceviceForPADate);
    public Date getDocPAExcuteDate();
    public void setDocPAExcuteDate(Date docPAExcuteDate);
    public String getPreDisbursementRemarks();
    public void setPreDisbursementRemarks(String preDisbursementRemarks);

    public Date getSolicitorAdviseReleaseDate();
    public void setSolicitorAdviseReleaseDate(Date solicitorAdviseReleaseDate);
    public Date getDisbursementDocCompletionDate();
    public void setDisbursementDocCompletionDate(Date disbursementDocCompletionDate);
    public Date getDisbursementDate();
    public void setDisbursementDate(Date disbursementDate);
    public String getDisbursementRemarks();
    public void setDisbursementRemarks(String disbursementRemarks);

    public Date getDocCompletionDate();
    public void setDocCompletionDate(Date docCompletionDate);
    public String getPostDisbursementRemarks();
    public void setPostDisbursementRemarks(String postDisbursementRemarks);

    public String getStatus();
    public void setStatus(String status);

}
