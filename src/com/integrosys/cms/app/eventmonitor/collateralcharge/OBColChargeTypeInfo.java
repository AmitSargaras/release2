/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/collateralcharge/OBColChargeTypeInfo.java,v 1.1 2006/10/06 05:50:23 jychong Exp $
 */

package com.integrosys.cms.app.eventmonitor.collateralcharge;

import com.integrosys.cms.app.eventmonitor.OBEventInfo;

/**
 * Bean to hold the information that are used for sending notifications
 */
public class OBColChargeTypeInfo extends OBEventInfo {

	private String limitId;

	private String securityType;

	private String securitySubType;

	private String chargeType;

	private String securityLocation;

	public String getLimitID() {
		return this.limitId;
	}

	public void setLimitID(String limitId) {
		this.limitId = limitId;
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

	public String getSecurityLocation() {
		return this.securityLocation;
	}

	public void setSecurityLocation(String securityLocation) {
		this.securityLocation = securityLocation;
	}

	public String getSecurityChargeType() {
		return this.chargeType;
	}

	public void setSecurityChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
}