package com.integrosys.cms.host.eai.limit.bus;

import java.util.Vector;

import com.integrosys.cms.host.eai.customer.bus.MainProfile;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class CoBorrower implements java.io.Serializable {

	private long limitID;
	
	private String limitRef;
	
	private long outerLimitID;
	
	private String outerLimitRef;
	
	private String CIFId;
	
	private String status;

	public long getLimitID() {
		return limitID;
	}

	public void setLimitID(long limitID) {
		this.limitID = limitID;
	}

	public String getLimitRef() {
		return limitRef;
	}

	public void setLimitRef(String limitRef) {
		this.limitRef = limitRef;
	}

	public long getOuterLimitID() {
		return outerLimitID;
	}

	public void setOuterLimitID(long outerLimitID) {
		this.outerLimitID = outerLimitID;
	}

	public String getOuterLimitRef() {
		return outerLimitRef;
	}

	public void setOuterLimitRef(String outerLimitRef) {
		this.outerLimitRef = outerLimitRef;
	}

	public String getCIFId() {
		return CIFId;
	}

	public void setCIFId(String id) {
		CIFId = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
