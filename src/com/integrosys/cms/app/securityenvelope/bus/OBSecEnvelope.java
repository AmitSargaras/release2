
package com.integrosys.cms.app.securityenvelope.bus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * Title: CLIMS 
 * Description: Data object for EBSecEnvelope 
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Erene Wong 
 * Date: Jan 25, 2010
 */

public class OBSecEnvelope implements ISecEnvelope {
	private long secEnvelopeId;
	private long secLspLmtProfileId = 0;
    private long versionTime = 0;
	private Set secEnvelopeItemList;
	private String status;
    private String[] deletedItemList;

  /* begin value object */

	/* end value object */

	public OBSecEnvelope() {
	}

	public OBSecEnvelope(long secEnvelopeId, long secLspLmtProfileId, long versionTime) {
		setSecEnvelopeId(secEnvelopeId);
		setSecLspLmtProfileId(secLspLmtProfileId);
		setVersionTime(versionTime);
	}

	public OBSecEnvelope(OBSecEnvelope otherData) {
		setSecEnvelopeId(otherData.getSecEnvelopeId());
		setSecLspLmtProfileId(otherData.getSecLspLmtProfileId());
		setVersionTime(otherData.getVersionTime());

	}

	public long getSecEnvelopeId() {
		return this.secEnvelopeId;
	}

	public void setSecEnvelopeId(long secEnvelopeId) {
		this.secEnvelopeId = secEnvelopeId;
	}

	public long getSecLspLmtProfileId() {
		return this.secLspLmtProfileId;
	}

	public void setSecLspLmtProfileId(long secLspLmtProfileId) {
		this.secLspLmtProfileId = secLspLmtProfileId;
	}

	public long getVersionTime() {
		return this.versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public String toString() {
		StringBuffer str = new StringBuffer("{");

		str.append("secEnvelopeId=" + getSecEnvelopeId() + " " + "secLspLmtProfileId="
				+ getSecLspLmtProfileId() + "versionTime=" + getVersionTime());
		str.append('}');

		return (str.toString());
	}

	public Set getSecEnvelopeItemList() {
		return secEnvelopeItemList;
	}

	public void setSecEnvelopeItemList(Set secEnvelopeItemList) {
		this.secEnvelopeItemList = secEnvelopeItemList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    public String[] getDeletedItemList() {
		return deletedItemList;
	}

	public void setDeletedItemList(String[] deletedItemList) {
		this.deletedItemList = deletedItemList;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null ||
            !(o instanceof OBSecEnvelope)) {
            return false;
        }

        OBSecEnvelope other = (OBSecEnvelope)o;

        // if the id is missing, return false
        if (secEnvelopeId > 0) return false;

        // equivalence by id
        return secEnvelopeId == other.getSecEnvelopeId();
    }

    public int hashCode() {
        if (secEnvelopeId > 0) {
            return Long.toString(secEnvelopeId).hashCode();
        } else {
            return super.hashCode();
        }
    }
}
