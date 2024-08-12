package com.integrosys.cms.host.eai.limit.inquiry.listing;

/**
 * Other criteria pertaining to search of credit application stored in the
 * system.
 * 
 * @author Chong Jun Yong
 * 
 */
public class OtherCriteria {
	private String hostAANumber;

	private String applicationType;

	private String applicationLawType;

	private String shortCustomerName;

	private String cifSource;
	
	private String cifId;

	public String getApplicationLawType() {
		return applicationLawType;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public String getCifId() {
		return cifId;
	}

	public String getCifSource() {
		return cifSource;
	}

	public String getHostAANumber() {
		return hostAANumber;
	}

	public String getShortCustomerName() {
		return shortCustomerName;
	}

	public void setApplicationLawType(String applicationLawType) {
		this.applicationLawType = applicationLawType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public void setCifId(String cifId) {
		this.cifId = cifId;
	}

	public void setCifSource(String cifSource) {
		this.cifSource = cifSource;
	}

	public void setHostAANumber(String hostAANumber) {
		this.hostAANumber = hostAANumber;
	}

	public void setShortCustomerName(String shortCustomerName) {
		this.shortCustomerName = shortCustomerName;
	}
}
