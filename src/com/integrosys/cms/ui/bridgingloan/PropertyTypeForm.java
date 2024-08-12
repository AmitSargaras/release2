/*
Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.bridgingloan;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * ActionForm for PropertyTypeAction.
 * @author $Author: KLYong $<br>
 * @version $Revision: $
 * @since $String: $ Tag: $Name: $
 */
public class PropertyTypeForm extends TrxContextForm implements Serializable {
	private String propertyTypeID = "";

	private String projectID = "";

	private String propertyType = "";

	private String propertyTypeOthers = "";

	private String noOfUnits = "";

	private String remarks = "";

	public PropertyTypeForm() {
	}

	public String getPropertyTypeID() {
		return propertyTypeID;
	}

	public void setPropertyTypeID(String propertyTypeID) {
		this.propertyTypeID = propertyTypeID;
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getPropertyTypeOthers() {
		return propertyTypeOthers;
	}

	public void setPropertyTypeOthers(String propertyTypeOthers) {
		this.propertyTypeOthers = propertyTypeOthers;
	}

	public String getNoOfUnits() {
		return noOfUnits;
	}

	public void setNoOfUnits(String noOfUnits) {
		this.noOfUnits = noOfUnits;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String[][] getMapper() {
		String[][] input = {
		// {"bridgingLoanTrxValue",
		// "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue"},
		{ "objBridgingLoan", "com.integrosys.cms.ui.bridgingloan.PropertyTypeMapper" }, };
		return input;
	}
}