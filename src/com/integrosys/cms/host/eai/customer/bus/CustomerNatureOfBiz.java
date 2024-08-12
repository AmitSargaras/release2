package com.integrosys.cms.host.eai.customer.bus;

import com.integrosys.cms.host.eai.StandardCode;

public class CustomerNatureOfBiz implements java.io.Serializable {
	
	public long getCmsId() {
		return cmsId;
	}

	public void setCmsId(long cmsId) {
		this.cmsId = cmsId;
	}

	public long getCmsMainProfileId() {
		return cmsMainProfileId;
	}

	public void setCmsMainProfileId(long cmsMainProfileId) {
		this.cmsMainProfileId = cmsMainProfileId;
	}

	public StandardCode getNatureOfBiz() {
		return natureOfBiz;
	}

	public void setNatureOfBiz(StandardCode natureOfBiz) {
		this.natureOfBiz = natureOfBiz;
	}

	private long cmsId;

	private long cmsMainProfileId;
	
	private String CIFId;
	
	public String getCIFId() {
		return CIFId;
	}

	public void setCIFId(String id) {
		CIFId = id;
	}

	private StandardCode natureOfBiz;


}
