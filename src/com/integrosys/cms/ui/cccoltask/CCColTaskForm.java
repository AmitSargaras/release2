/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.cccoltask;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/16 02:38:00 $ Tag: $Name: $
 */

public class CCColTaskForm extends TrxContextForm implements Serializable {

	private String customerCategory = "";

	private String subProfileID = "";

	private String legalRef = "";

	private String legalName = "";

	private String domicileCountry = "";

	private String customerType = "";

	private String orgCode = "";

	private String colRemarks = "";

	public String getCustomerCategory() {
		return customerCategory;
	}

	public void setCustomerCategory(String customerCategory) {
		this.customerCategory = customerCategory;
	}

	public String getSubProfileID() {
		return subProfileID;
	}

	public void setSubProfileID(String subProfileID) {
		this.subProfileID = subProfileID;
	}

	public String getLegalRef() {
		return this.legalRef;
	}

	public void setLegalRef(String aLegalRef) {
		this.legalRef = aLegalRef;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getDomicileCountry() {
		return domicileCountry;
	}

	public void setDomicileCountry(String domicileCountry) {
		this.domicileCountry = domicileCountry;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getColRemarks() {
		return colRemarks;
	}

	public void setColRemarks(String colRemarks) {
		this.colRemarks = colRemarks;
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
				{ "limitProfile", "com.integrosys.cms.ui.cccoltask.LimitProfileMapper" },
				{ "colTask", "com.integrosys.cms.ui.cccoltask.CCColTaskMapper" } };
		return input;
	}

}
