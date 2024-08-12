package com.integrosys.cms.batch.mimb.customer;

import java.util.Date;

public class OBCustomerInfo implements ICustomerInfo {

	private long tempID;

	private String recordType;

	private String aaNo;

	private String cifNo;

	private String legalName;

	private String customerName;

	private String idType;

	private String idNo;

	private Date relationshipStartDate;

	private String customerTypeCode;

	private String customerTypeDes;

	private String customerLegalCons;

	private String customerLegalConsDes;

	private String addressType;

	private String address1;

	private String address2;

	private String address3;

	private String address4;

	private String country;

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getAaNo() {
		return aaNo;
	}

	public void setAaNo(String aaNo) {
		this.aaNo = aaNo;
	}

	public String getCifNo() {
		return cifNo;
	}

	public void setCifNo(String cifNo) {
		this.cifNo = cifNo;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public Date getRelationshipStartDate() {
		return relationshipStartDate;
	}

	public void setRelationshipStartDate(Date relationshipStartDate) {
		this.relationshipStartDate = relationshipStartDate;
	}

	public String getCustomerTypeCode() {
		return customerTypeCode;
	}

	public void setCustomerTypeCode(String customerTypeCode) {
		this.customerTypeCode = customerTypeCode;
	}

	public String getCustomerTypeDes() {
		return customerTypeDes;
	}

	public void setCustomerTypeDes(String customerTypeDes) {
		this.customerTypeDes = customerTypeDes;
	}

	public String getCustomerLegalCons() {
		return customerLegalCons;
	}

	public void setCustomerLegalCons(String customerLegalCons) {
		this.customerLegalCons = customerLegalCons;
	}

	public String getCustomerLegalConsDes() {
		return customerLegalConsDes;
	}

	public void setCustomerLegalConsDes(String customerLegalConsDes) {
		this.customerLegalConsDes = customerLegalConsDes;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
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

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String address4) {
		this.address4 = address4;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public long getTempID() {
		return tempID;
	}

	public void setTempID(long tempID) {
		this.tempID = tempID;
	}

}
