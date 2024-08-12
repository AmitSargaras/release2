package com.integrosys.cms.batch.mimb.customer;

import java.util.Date;

public interface ICustomerInfo {

	public String getRecordType();

	public void setRecordType(String recordType);

	public String getAaNo();

	public void setAaNo(String aaNo);

	public String getCifNo();

	public void setCifNo(String cifNo);

	public String getLegalName();

	public void setLegalName(String legalName);

	public String getCustomerName();

	public void setCustomerName(String customerName);

	public String getIdType();

	public void setIdType(String idType);

	public String getIdNo();

	public void setIdNo(String idNo);

	public Date getRelationshipStartDate();

	public void setRelationshipStartDate(Date relationshipStartDate);

	public String getCustomerTypeCode();

	public void setCustomerTypeCode(String customerTypeCode);

	public String getCustomerTypeDes();

	public void setCustomerTypeDes(String customerTypeDes);

	public String getCustomerLegalCons();

	public void setCustomerLegalCons(String customerLegalCons);

	public String getCustomerLegalConsDes();

	public void setCustomerLegalConsDes(String customerLegalConsDes);

	public String getAddressType();

	public void setAddressType(String addressType);

	public String getAddress1();

	public void setAddress1(String address1);

	public String getAddress2();

	public void setAddress2(String address2);

	public String getAddress3();

	public void setAddress3(String address3);

	public String getAddress4();

	public void setAddress4(String address4);

	public String getCountry();

	public void setCountry(String country);

	public long getTempID();

	public void setTempID(long tempID);

}
