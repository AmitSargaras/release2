/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.seccoltask;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/08/07 11:24:38 $ Tag: $Name: $
 */

public class SecurityColTaskForm extends TrxContextForm implements Serializable {

	private String limitProfileId;

	private String collateralId;

	private String collateralRef;

	private String colLocation;

	private String limitId;

	private String colType;

	private String colSubType;

	private String colRemarks;

	private String securityOrganization;

	private long leSubProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String customerCategory;

	public String getColRemarks() {
		return colRemarks;
	}

	public void setColRemarks(String colRemarks) {
		this.colRemarks = colRemarks;
	}

	public String getColSubType() {
		return colSubType;
	}

	public void setColSubType(String colSubType) {
		this.colSubType = colSubType;
	}

	public String getColType() {
		return colType;
	}

	public void setColType(String colType) {
		this.colType = colType;
	}

	public String getLimitId() {
		return limitId;
	}

	public void setLimitId(String limitId) {
		this.limitId = limitId;
	}

	public String getCollateralId() {
		return collateralId;
	}

	public void setCollateralId(String collateralId) {
		this.collateralId = collateralId;
	}

	public String getColLocation() {
		return colLocation;
	}

	public void setColLocation(String colLocation) {
		this.colLocation = colLocation;
	}

	public String getSecurityOrganization() {
		return securityOrganization;
	}

	public void setSecurityOrganization(String securityOrganization) {
		this.securityOrganization = securityOrganization;
	}

	public String getLimitProfileId() {
		return limitProfileId;
	}

	public void setLimitProfileId(String limitProfileId) {
		this.limitProfileId = limitProfileId;
	}

	public String getCollateralRef() {
		return this.collateralRef;
	}

	public void setCollateralRef(String aCollateralRef) {
		this.collateralRef = aCollateralRef;
	}

	public long getLeSubProfileID() {
		return leSubProfileID;
	}

	public void setLeSubProfileID(long leSubProfileID) {
		this.leSubProfileID = leSubProfileID;
	}

	public String getCustomerCategory() {
		return customerCategory;
	}

	public void setCustomerCategory(String customerCategory) {
		this.customerCategory = customerCategory;
	}

	/**
	 * This method defines a String array which tells what object is to be
	 * formed from the form and using what mapper classes to form it. it has a
	 * syntax (keyMapperclassname)
	 * 
	 * @return two-dimesnional String Array
	 */
	public String[][] getMapper() {
		String[][] input = { { "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "limitProfile", "com.integrosys.cms.ui.seccoltask.LimitProfileMapper" },
				{ "colTask", "com.integrosys.cms.ui.seccoltask.SecurityColTaskMapper" } };
		return input;
	}

}
