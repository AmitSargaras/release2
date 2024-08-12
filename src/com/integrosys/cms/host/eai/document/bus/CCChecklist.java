package com.integrosys.cms.host.eai.document.bus;

import com.integrosys.cms.host.eai.StandardCode;

/**
 * CheckList
 * 
 * @author $Author: shphoon $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class CCChecklist implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	private String customerType;

	private String cIFNo;
	
	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCIFNo() {
		return cIFNo;
	}

	public void setCIFNo(String no) {
		cIFNo = no;
	}

}
