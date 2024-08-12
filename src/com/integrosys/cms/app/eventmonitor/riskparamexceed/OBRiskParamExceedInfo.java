/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/riskparamexceed/OBRiskParamExceedInfo.java,v 1.1 2003/08/23 12:22:44 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor.riskparamexceed;

import com.integrosys.cms.app.eventmonitor.OBEventInfo;

/**
 * Bean to hold the information that are used for sending notifications
 */
public class OBRiskParamExceedInfo extends OBEventInfo {
	private String country;

	private double maxThreshold;

	private double countryThresHold;

	private String subType;

	private String type;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public double getMaxThreshold() {
		return maxThreshold;
	}

	public void setMaxThreshold(double maxThreshold) {
		this.maxThreshold = maxThreshold;
	}

	public double getCountryThresHold() {
		return countryThresHold;
	}

	public void setCountryThresHold(double countryThresHold) {
		this.countryThresHold = countryThresHold;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
