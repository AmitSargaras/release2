package com.integrosys.cms.app.tatdoc.bus;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Aug 27, 2008
 * Time: 3:57:01 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ITatDocDraft extends Serializable {
    
    public long getDraftID();

    public void setDraftID(long draftID);

    public String getDocDraftStage();

    public void setDocDraftStage(String docDraftStage);

    public short getDraftNumber();

    public void setDraftNumber(short draftNumber);

    public Date getDraftDate();

    public void setDraftDate(Date draftDate);

    public String getStatus();

    public void setStatus(String status);

    public long getReferenceID();

    public void setReferenceID(long referenceID);

}
