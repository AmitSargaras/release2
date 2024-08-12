package com.integrosys.cms.app.securityenvelope.bus;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * Title: CLIMS
 * Description: Interface for Security Envelope
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Erene Wong
 * Date: Jan 25, 2010
 */
public interface ISecEnvelope extends Serializable {

    public long getSecEnvelopeId();

    public void setSecEnvelopeId(long secEnvelopeId);

    public long getSecLspLmtProfileId();

    public void setSecLspLmtProfileId(long secLspLmtProfileId);

    public long getVersionTime();

    public void setVersionTime(long versionTime);

    public Set getSecEnvelopeItemList();

    public void setSecEnvelopeItemList(Set secEnvelopeItemList);
    
    public String getStatus();
    
    public void setStatus(String status) ;

    public String[] getDeletedItemList();

	public void setDeletedItemList(String[] deletedItemList);

}