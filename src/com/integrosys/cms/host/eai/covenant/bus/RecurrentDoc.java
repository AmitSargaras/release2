package com.integrosys.cms.host.eai.covenant.bus;

import java.io.Serializable;
import java.util.Vector;

public class RecurrentDoc implements Serializable {

	private static final long serialVersionUID = -7281072068846038628L;

	private long recurrentDocID;

	private long cmsSubProfileID;

	private long cmsLimitProfileID;

	private String status;

	private Vector convenantItems;

	private String lOSAANumber;

	public long getCmsLimitProfileID() {
		return cmsLimitProfileID;
	}

	public long getCmsSubProfileID() {
		return cmsSubProfileID;
	}

	public Vector getConvenantItems() {
		return convenantItems;
	}

	public String getLOSAANumber() {
		return lOSAANumber;
	}

	public long getRecurrentDocID() {
		return recurrentDocID;
	}

	public String getStatus() {
		return status;
	}

	public void setCmsLimitProfileID(long cmsLimitProfileID) {
		this.cmsLimitProfileID = cmsLimitProfileID;
	}

	public void setCmsSubProfileID(long cmsSubProfileID) {
		this.cmsSubProfileID = cmsSubProfileID;
	}

	public void setConvenantItems(Vector convenantItems) {
		this.convenantItems = convenantItems;
	}

	public void setLOSAANumber(String number) {
		lOSAANumber = number;
	}

	public void setRecurrentDocID(long recurrentDocID) {
		this.recurrentDocID = recurrentDocID;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("RecurrentDoc [");
		buf.append("cmsLimitProfileID=");
		buf.append(cmsLimitProfileID);
		buf.append(", cmsSubProfileID=");
		buf.append(cmsSubProfileID);
		buf.append(", lOSAANumber=");
		buf.append(lOSAANumber);
		buf.append(", convenantItems=");
		buf.append(convenantItems);
		buf.append(", status=");
		buf.append(status);
		buf.append("]");
		return buf.toString();
	}

}
