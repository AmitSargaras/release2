package com.integrosys.cms.host.eai.security.inquiry;

/**
 * The security inquiry object contains one field which is the cms internal key
 * only.
 * 
 * @author Chong Jun Yong
 * 
 */
public class SecurityInquiry {
	private long cmsSecurityId;

	public void setCmsSecurityId(long cmsSecurityId) {
		this.cmsSecurityId = cmsSecurityId;
	}

	public long getCmsSecurityId() {
		return cmsSecurityId;
	}
}
