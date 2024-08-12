/**

 * Copyright Integro Technologies Pte Ltd

 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/customer/CustomerSearchForm.java,v 1.9 2003/11/11 03:35:49 pooja Exp $

 */

package com.integrosys.cms.ui.customer;

import com.integrosys.cms.ui.common.TrxContextForm;

public class CustomerSearchForm extends TrxContextForm implements java.io.Serializable {
	private String userID = "";

	private String subProfileID = "";

	private String legalName = "";

	private String legalIdSub = "";

	// private String idCountry = "";

	private String customerName = "";

	private String legalID = "";

	private String leIDType = "";

	private String idNO = "";

	private String gobutton = "";

	private String all = "";

	private String aaNumber = "";
	
	private String customerNameShort;
	
	private String facilitySystem;
	
	private String facilitySystemID;
	
	public String getFacilitySystem() {
		return facilitySystem;
	}

	public void setFacilitySystem(String facilitySystem) {
		this.facilitySystem = facilitySystem;
	}

	public String getFacilitySystemID() {
		return facilitySystemID;
	}

	public void setFacilitySystemID(String facilitySystemID) {
		this.facilitySystemID = facilitySystemID;
	}
	
	public String getCustomerNameShort() {
		return customerNameShort;
	}

	public void setCustomerNameShort(String customerNameShort) {
		this.customerNameShort = customerNameShort;
	}

	public String getAaNumber() {
		return aaNumber;
	}

	public String getAll() {
		return all;
	}

	public String getCustomerName() {

		return customerName;

	}

	public String getGobutton() {
		return gobutton;
	}

	public String getIdNO() {
		return idNO;
	}

	public String getLegalID() {

		return legalID;

	}

	public String getLegalIdSub() {
		return legalIdSub;
	}

	public String getLegalName() {
		return legalName;
	}

	public String getLeIDType() {
		return leIDType;
	}

	public String getSubProfileID() {
		return subProfileID;
	}

	// public String getIdCountry() {
	// return idCountry;
	// }
	//
	// public void setIdCountry(String idCountry) {
	// this.idCountry = idCountry;
	// }

	public String getUserID() {
		return userID;
	}

	public void setAaNumber(String aaNumber) {
		this.aaNumber = aaNumber;
	}

	public void setAll(String all) {
		this.all = all;
	}

	public void setCustomerName(String customerName) {

		this.customerName = customerName;

	}

	public void setGobutton(String gobutton) {
		this.gobutton = gobutton.trim();
	}

	public void setIdNO(String idNO) {
		this.idNO = idNO;
	}

	public void setLegalID(String legalID) {

		this.legalID = legalID.trim();

	}

	public void setLegalIdSub(String legalIdSub) {
		this.legalIdSub = legalIdSub;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public void setLeIDType(String leIDType) {
		this.leIDType = leIDType;
	}

	public void setSubProfileID(String subProfileID) {
		this.subProfileID = subProfileID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String[][] getMapper() {

		String[][] input = { { "aCustomerSearchCriteria", "com.integrosys.cms.ui.customer.CustomerSearchMapper" },
				{ "customerSearchCriteria", "com.integrosys.cms.ui.customer.CustomerListMapper" },

				{ "customerList", "com.integrosys.cms.ui.customer.CustomerListMapper" },

				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" }

		};

		return input;
	}

	public void reset() {
		setCustomerName("");
		setLegalID("");
		setLeIDType("");
		setIdNO("");
		setAaNumber("");
	}
}
