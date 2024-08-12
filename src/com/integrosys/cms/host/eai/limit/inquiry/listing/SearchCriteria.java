package com.integrosys.cms.host.eai.limit.inquiry.listing;

import com.integrosys.cms.host.eai.customer.bus.CustomerIdInfo;

/**
 * Search criteria shell for the credit application inquiry, cover customer
 * search and other criteria
 * 
 * @author Chong Jun Yong
 * 
 */
public class SearchCriteria {
	private CustomerIdInfo customerIdInfo;

	private OtherCriteria otherCriteria;

	public CustomerIdInfo getCustomerIdInfo() {
		return customerIdInfo;
	}

	public OtherCriteria getOtherCriteria() {
		return otherCriteria;
	}

	public void setCustomerIdInfo(CustomerIdInfo customerIdInfo) {
		this.customerIdInfo = customerIdInfo;
	}

	public void setOtherCriteria(OtherCriteria otherCriteria) {
		this.otherCriteria = otherCriteria;
	}
}
