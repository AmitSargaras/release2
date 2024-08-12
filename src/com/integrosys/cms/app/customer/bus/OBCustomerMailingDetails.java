/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/OBCustomerMailingDetails.java,v 1.3 2005/05/04 11:25:51 wltan Exp $
 */
package com.integrosys.cms.app.customer.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * DAO for customer
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2005/05/04 11:25:51 $ Tag: $Name: $
 */
public class OBCustomerMailingDetails implements java.io.Serializable {

	private String customerName;

	private String customerLEID;

	private String customerSubProfileID;

	private String addressLine1;

	private String addressLine2;

	private String city;

	private String state;

	private String postalCode;

	private String countryCode;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerLEID() {
		return customerLEID;
	}

	public void setCustomerLEID(String leID) {
		this.customerLEID = leID;
	}

	public String getCustomerSubProfileID() {
		return customerSubProfileID;
	}

	public void setCustomerSubProfileID(String lspID) {
		this.customerSubProfileID = lspID;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String ctryCode) {
		this.countryCode = ctryCode;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

}