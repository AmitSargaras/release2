package com.integrosys.cms.host.eai.document.bus;

import com.integrosys.cms.host.eai.StandardCode;

/**
 * Template inquiry criteria for CC Checklist.
 * 
 * @author Phoon Sai Heng
 * @author Chong Jun Yong
 */
public class CCTemplate implements java.io.Serializable {

	private static final long serialVersionUID = 7621306220085350725L;

	private String customerType;

	private StandardCode customerClass;

	private String applicableLaw;

	private StandardCode applicationType;

	public String getApplicableLaw() {
		return applicableLaw;
	}

	public StandardCode getApplicationType() {
		return applicationType;
	}

	public StandardCode getCustomerClass() {
		return customerClass;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setApplicableLaw(String applicableLaw) {
		this.applicableLaw = applicableLaw;
	}

	public void setApplicationType(StandardCode applicationType) {
		this.applicationType = applicationType;
	}

	public void setCustomerClass(StandardCode customerClass) {
		this.customerClass = customerClass;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("CCTemplate [");
		buf.append("customerClass=");
		buf.append(customerClass);
		buf.append(", customerType=");
		buf.append(customerType);
		buf.append(", applicableLaw=");
		buf.append(applicableLaw);
		buf.append(", applicationType=");
		buf.append(applicationType);
		buf.append("]");
		return buf.toString();
	}

}
