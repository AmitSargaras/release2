package com.integrosys.cms.host.eai.customer;

import com.integrosys.cms.host.eai.support.MessageDate;

public final class SearchDetailResult implements java.io.Serializable {
	/*
	 * <field name="CIFId" type="string"/> <field name="CustomerNameShort"
	 * type="string"/> <field name="CustomerNameLong" type="string"/> <field
	 * name="IDNumber" type="string"/> <field name="IdType"
	 * type="com.integrosys.cms.host.message.castor.eai.StandardCode"/> <field
	 * name="IDNumber2" type="string"/> <field name="Gender" type="string"/>
	 * <field name="BirthDate" type="string"/> <field name="Address"
	 * type="string"/>
	 */

	// Key
	private long searchCustId;

	// =ICMSConstant.LONG_INVALID_VALUE;

	private String SID;

	private String CIFId;

	private String CustomerNameShort;

	private String CustomerNameLong;

	private String IDNumber;

	private com.integrosys.cms.host.eai.StandardCode IdType;

	private String IDNumber2;

	private String Gender;

	// private String BirthDate;
	private java.util.Date BirthDate;

	private String Address;

	public final String getAddress() {
		return Address;
	}

	public final void setAddress(String address) {
		Address = address;
	}

	public final String getBirthDate() {
		return MessageDate.getInstance().getString(BirthDate);
	}

	public final void setBirthDate(String birthDate) {
		BirthDate = MessageDate.getInstance().getDate(birthDate);
	}

	public final java.util.Date getJDOBirthDate() {
		return this.BirthDate;
	}

	public final void setJDOBirthDate(java.util.Date birthDate) {
		this.BirthDate = birthDate;
	}

	public final String getCIFId() {
		return CIFId;
	}

	public final void setCIFId(String id) {
		CIFId = id;
	}

	public final String getCustomerNameLong() {
		return CustomerNameLong;
	}

	public final void setCustomerNameLong(String customerNameLong) {
		CustomerNameLong = customerNameLong;
	}

	public final String getCustomerNameShort() {
		return CustomerNameShort;
	}

	public final void setCustomerNameShort(String customerNameShort) {
		CustomerNameShort = customerNameShort;
	}

	public final String getGender() {
		return Gender;
	}

	public final void setGender(String gender) {
		Gender = gender;
	}

	public final String getIDNumber() {
		return IDNumber;
	}

	public final void setIDNumber(String number) {
		IDNumber = number;
	}

	public final String getIDNumber2() {
		return IDNumber2;
	}

	public final void setIDNumber2(String number2) {
		IDNumber2 = number2;
	}

	public final com.integrosys.cms.host.eai.StandardCode getIdType() {
		return IdType;
	}

	public final void setIdType(com.integrosys.cms.host.eai.StandardCode idType) {
		IdType = idType;
	}

	public final String getSID() {
		return SID;
	}

	public final void setSID(String sid) {
		SID = sid;
	}

	public final long getSearchCustId() {
		return searchCustId;
	}

	public final void setSearchCustId(long searchCustId) {
		this.searchCustId = searchCustId;
	}

}
