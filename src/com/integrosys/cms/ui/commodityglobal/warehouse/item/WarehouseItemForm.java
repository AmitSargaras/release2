/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/warehouse/item/WarehouseItemForm.java,v 1.3 2004/08/30 12:38:48 hshii Exp $
 */
package com.integrosys.cms.ui.commodityglobal.warehouse.item;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/08/30 12:38:48 $ Tag: $Name: $
 */

public class WarehouseItemForm extends CommonForm implements Serializable {
	private String warehouseName = "";

	private String address1 = "";

	private String address2 = "";

	private String city = "";

	private String state = "";

	private String postalCode = "";

	private String countryCode = "";

	private String contactName = "";

	private String fax = "";

	private String email = "";

	private String telephone = "";

	private String extensionNumber = "";

	private String warehouseRemarks = "";

	public String getWarehouseName() {
		return this.warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
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

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getExtensionNumber() {
		return extensionNumber;
	}

	public void setExtensionNumber(String extensionNumber) {
		this.extensionNumber = extensionNumber;
	}

	public String getWarehouseRemarks() {
		return warehouseRemarks;
	}

	public void setWarehouseRemarks(String warehouseRemarks) {
		this.warehouseRemarks = warehouseRemarks;
	}

	public String[][] getMapper() {
		String[][] input = { { "warehouseItemObj",
				"com.integrosys.cms.ui.commodityglobal.warehouse.item.WarehouseItemMapper" }, };
		return input;
	}

}
