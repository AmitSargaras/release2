package com.integrosys.cms.host.eai;

/**
 * @author marvin
 * @author Chong Jun Yong
 * @since 1.1
 */
public class CreditGrade extends AbstractCreditGrade implements java.io.Serializable {

	private long cmsMainProfileId;

	private String CIFId;

	public long getCmsMainProfileId() {
		return cmsMainProfileId;
	}

	public void setCmsMainProfileId(long cmsMainProfileId) {
		this.cmsMainProfileId = cmsMainProfileId;
	}

	public String getCIFId() {
		return CIFId;
	}

	public void setCIFId(String CIFId) {
		this.CIFId = CIFId;
	}

}
