/**

 * Copyright Integro Technologies Pte Ltd

 * $Header:

 */

package com.integrosys.cms.ui.systemBank;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 *$Author: Abhijit R $ Form Bean for System Bank Master
 */

public class MaintainSystemBankForm extends TrxContextForm implements
		Serializable {

	private String systemBankCode;
	private String systemBankName;
	private String address;
	private String cityTown;
	private String state;
	private String country;
	private String region;
	private String contactNumber;
	private String contactMail;
	private String systemBankId;
	private String faxNumber;
	

	private String versionTime;

	private String lastUpdateDate;
	private String disableForSelection;

	private String creationDate;

	private String createBy;
	// private String lastUpdateDate;
	private String lastUpdateBy;

	private String status;
	private String deprecated;
	private String masterId;
	private String id;

	public String[][] getMapper() {
		String[][] input = { { "systemBankObj", SYSTEM_BANK_MAPPER },
				{ "theOBTrxContext", TRX_MAPPER } };
		return input;

	}

	public static final String SYSTEM_BANK_MAPPER = "com.integrosys.cms.ui.systemBank.SystemBankMapper";

	public static final String SYSTEM_BANK_LIST_MAPPER = "com.integrosys.cms.ui.systemBank.SystemBankListMapper";

	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";

	public String getSystemBankCode() {
		return systemBankCode;
	}

	public void setSystemBankCode(String systemBankCode) {
		this.systemBankCode = systemBankCode;
	}

	public String getSystemBankName() {
		return systemBankName;
	}

	public void setSystemBankName(String systemBankName) {
		this.systemBankName = systemBankName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCityTown() {
		return cityTown;
	}

	public void setCityTown(String cityTown) {
		this.cityTown = cityTown;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getContactMail() {
		return contactMail;
	}

	public void setContactMail(String contactMail) {
		this.contactMail = contactMail;
	}

	public String getSystemBankId() {
		return systemBankId;
	}

	public void setSystemBankId(String systemBankId) {
		this.systemBankId = systemBankId;
	}

	public String getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(String versionTime) {
		this.versionTime = versionTime;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getDisableForSelection() {
		return disableForSelection;
	}

	public void setDisableForSelection(String disableForSelection) {
		this.disableForSelection = disableForSelection;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeprecated() {
		return deprecated;
	}

	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}

	public String getMasterId() {
		return masterId;
	}

	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

}
