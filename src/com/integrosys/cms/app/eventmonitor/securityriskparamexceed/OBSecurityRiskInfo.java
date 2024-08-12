/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/securityriskparamexceed/OBSecurityRiskInfo.java,v 1.2 2006/03/06 12:38:50 hshii Exp $
 */

package com.integrosys.cms.app.eventmonitor.securityriskparamexceed;

import com.integrosys.cms.app.customer.bus.OBCustomerSearchResult;
import com.integrosys.cms.app.eventmonitor.OBEventInfo;

/**
 * Bean to hold the information that are used for sending notifications
 */
public class OBSecurityRiskInfo extends OBEventInfo {

	private String securityLocation;

	private String securityType;

	private String securitySubType;

	// private long securityID;
	private OBCustomerSearchResult[] customerList;

	private double countryMargin;

	private Double[] securityMargin;

	public String getSecurityLocation() {
		return this.securityLocation;
	}

	public void setSecurityLocation(String securityLocation) {
		this.securityLocation = securityLocation;
	}

	public String getSecurityType() {
		return this.securityType;
	}

	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}

	public String getSecuritySubType() {
		return this.securitySubType;
	}

	public void setSecuritySubType(String securitySubType) {
		this.securitySubType = securitySubType;
	}

	/*
	 * public long getSecurityID () { return this.securityID; }
	 * 
	 * public void setSecurityID (long securityID) { this.securityID =
	 * securityID; }
	 */
	public OBCustomerSearchResult[] getCustomerList() {
		return customerList;
	}

	public void setCustomerList(OBCustomerSearchResult[] customerList) {
		this.customerList = customerList;
	}

	public double getCountryMargin() {
		return this.countryMargin;
	}

	public void setCountryMargin(double countryMargin) {
		this.countryMargin = countryMargin;
	}

	public Double[] getSecurityMargin() {
		return this.securityMargin;
	}

	public void setSecurityMargin(Double[] securityMarginList) {
		this.securityMargin = securityMarginList;
	}
}