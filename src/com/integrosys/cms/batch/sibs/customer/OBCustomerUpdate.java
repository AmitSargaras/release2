
package com.integrosys.cms.batch.sibs.customer;

import com.integrosys.cms.batch.sibs.customer.ICustomer;
import java.util.Date;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * 
 * who: gek phuang
 * Date: 08th Sep 2008
 * Time: 2100hr
 * 
 */

public class OBCustomerUpdate implements ICustomerInfo {

	//private long customerID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	private String customerID = null;

	private String customerName = null;
	private String customerShortName = null;
	private String legalConst = null;
	private String country = null;
	private String customerType = null;
	private String idNo = null;
	private String idType = null;
	private String secondaryIdNo = null;
	private String secondaryIdType = null;
	private String isicCode = null;

	private String addrType = null;
	private String addr1 = null;
	private String addr2 = null;
	private String addr3 = null;
	private String addr4 = null;
	private String postCode = null;
	private String resdntCountry = null;
	private String language = null;

	private String secAddrType = null;
	private String secAddr1 = null;
	private String secAddr2 = null;
	private String secAddr3 = null;
	private String secAddr4 = null;
	private String secPostcode = null;

	private Date relStartDate = null;
	private Date incorporatedDate = null;
	private String recordType = null;
	private String endLineInd = null;

	/**
	 * Default Constructor
	 */
	public OBCustomerUpdate() {
	}

	/**
	 * Construct OB from interface
	 *
	 * @param value is of type ICustomer
	 */
	public OBCustomerUpdate(ICustomer value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	// Getters

	/**
	 * Get the record type ("H" - Header ,"D" -Detail ,"T" -trailer)
	 *
	 * @param value is of type String
	 */
	public String getRecordType() {
		return recordType;
	}

	/**
	 * Get the customer ID which is the primary key of this entity
	 *
	 * @return long
	 */
	public String getCustomerID() {
		return customerID;
	}

	/**
	 * Get the customer name.
	 *
	 * @return String
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * Get the legal constitution
	 *
	 * @return String
	 */
	public String getLegalConstitution() {
		return legalConst;
	}

	/**
	 * Get the legal country
	 *
	 * @return String
	 */
	public String getLegalRegCountry() {
		return country;
	}

	/**
	 * Get the customer short name.
	 *
	 * @return String
	 */
	public String getCustomerShortName() {
		return customerShortName;
	}


	/**
	 * Get the customer type.
	 *
	 * @return String
	 */
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * Get the ISIC Code.
	 *
	 * @return String
	 */
	public String getIsicCode() {
		return isicCode;
	}

	/**
	 * Get the ID No.
	 *
	 * @return String
	 */
	public String getIdNo() {
		return idNo;
	}

	/**
	 * Get the Id type.
	 *
	 * @return String
	 */
	public String getIdType() {
		return idType;
	}

	/**
	 * Get the customer relationship start date
	 *
	 * @return Date
	 */
	public Date getCustomerRelationshipStartDate() {
		return relStartDate;
	}

	/**
	 * Get the incorporated date
	 *
	 * @return Date
	 */
	public Date getIncorporatedDate() {
		return incorporatedDate;
	}

	/**
	 * Get the Secondary ID No
	 *
	 * @return String
	 */
	public String getSecIdNo() {
		return secondaryIdNo;
	}

	/**
	 * Get the Secondary ID Type
	 *
	 * @return String
	 */
	public String getSecIdType() {
		return secondaryIdType;
	}

	/**
	 * Get the customer address type
	 *
	 * @return String
	 */
	public String getAddrType() {
		return addrType;
	}

	/**
	 * Get the residential address/town/city
	 *
	 * @return String
	 */
	public String getAddr1() {
		return addr1;
	}

	/**
	 * Get the residential address/town/city
	 *
	 * @return String
	 */
	public String getAddr2() {
		return addr2;
	}

	/**
	 * Get the residential address/town/city
	 *
	 * @return String
	 */
	public String getAddr3() {
		return addr3;
	}

	/**
	 * Get the residential address/town/city
	 *
	 * @return String
	 */
	public String getAddr4() {
		return addr4;
	}

	/**
	 * Get the postal code
	 *
	 * @return String
	 */
	public String getPostCode() {
		return postCode;
	}

	/**
	 * Get the residential country
	 *
	 * @return String
	 */
	public String getResCountry() {
		return resdntCountry;
	}

	/**
	 * Get the language
	 *
	 * @return String
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Get the secondary address type
	 *
	 * @return String
	 */
	public String getSecAddrType() {
		return secAddrType;
	}

	/**
	 * Get the secondary residential address/town/city
	 *
	 * @return String
	 */
	public String getSecAddr1() {
		return secAddr1;
	}

	/**
	 * Get the secondary residential address/town/city
	 *
	 * @return String
	 */
	public String getSecAddr2() {
		return secAddr2;
	}

	/**
	 * Get the secondary residential address/town/city
	 *
	 * @return String
	 */
	public String getSecAddr3() {
		return secAddr3;
	}

	/**
	 * Get the secondary residential address/town/city
	 *
	 * @return String
	 */
	public String getSecAddr4() {
		return secAddr4;
	}

	/**
	 * Get the secondary residential postal code
	 *
	 * @return String
	 */
	public String getSecPostcode() {
		return secPostcode;
	}

	// Setters
	/**
	 * Set the record type ("H" - Header ,"D" -Detail ,"T" -trailer)
	 *
	 * @param value is of type String
	 */
	public void setRecordType(String valueType) {
		recordType = valueType;
	}

	/**
	 * Set the customer ID which is the primary key of this entity
	 *
	 * @param value is of type long
	 */
	public void setCustomerID(String value) {
		customerID = value;
	}

	/**
	 * Set the customer name.
	 *
	 * @param value is of type String
	 */
	public void setCustomerName(String value) {
		customerName = value;
	}

	/**
	 * Set the customer name.
	 *
	 * @param value is of type String
	 */
	public void setCustomerShortName(String value) {
		customerShortName = value;
	}

	public void setLegalConstitution( String temp ) {
		legalConst = temp;
	}

	public void setLegalRegCountry( String tempCtry ) {
		country = tempCtry;
	}

	/**
	 * Set the ID No.
	 *
	 * @param value is of type String
	 */
	public void setIdNo(String idNumber) {
		idNo = idNumber;
	}

	/**
	 * Set the ID Type.
	 *
	 * @param value is of type String
	 */
	public void setIdType(String idNoType) {
		idType = idNoType;
	}

	/**
	 * Set the customer relationship start date
	 *
	 * @param value is of type Date
	 */
	public void setCustomerRelationshipStartDate(Date value) {
		relStartDate = value;
	}

	/**
	 * Set the customer incorporated date
	 *
	 * @param value is of type Date
	 */
	public void setIncorporatedDate(Date value) {
		incorporatedDate = value;
	}

	/**
	 * Set the customer type
	 *
	 * @param value is of type String
	 */

	public void setCustomerType(String value) {
		customerType = value;
	}

	/**
	 * Set the ISIC code
	 *
	 * @param value is of type String
	 */

	public void setIsicCode(String iSicCode) {
		isicCode = iSicCode;
	}

	/**
	 * set the Secondary ID No
	 *
	 * @param value is of type String
	 */
	public void setSecIdNo(String secIdNumber) {
		secondaryIdNo = secIdNumber;
	}

	/**
	 * Set the Secondary ID Type
	 *
	 * @param value is of type  String
	 */
	public void setSecIdType(String secIdType) {
		secondaryIdType = secIdType;
	}

	/**
	 * Set the primary address Type
	 *
	 * @param value is of type  String
	 */
	public  void setAddrType(String priAddrType) {
		addrType = priAddrType;
	}

	/**
	 * Set the residential address/town/city
	 *
	 * @param value is of type String
	 */
	public void setAddr1(String priAddr1) {
		addr1 = priAddr1;
	}

	/**
	 * Set the residential address/town/city
	 *
	 * @param value is of type String
	 */
	public void setAddr2(String priAddr2) {
		addr2 = priAddr2;
	}

	/**
	 * Set the residential address/town/city
	 *
	 * @param value is of type String
	 */
	public void setAddr3(String priAddr3) {
		addr3 = priAddr3;
	}

	/**
	 * Set the residential address/town/city
	 *
	 * @param value is of type String
	 */
	public void setAddr4(String priAddr4) {
		addr4 = priAddr4;
	}

	/**
	 * Set the residential postal code
	 *
	 * @param value is of type String
	 */
	public void setPostCode(String priPostcode) {
		postCode = priPostcode;
	}

	/**
	 * Set the residential country
	 *
	 * @param value is of type String
	 */
	public void setResCountry(String resCountry) {
		resdntCountry = resCountry;
	}

	/**
	 * Set the langauge
	 *
	 * @param value is of type String
	 */
	public void setLanguage(String lang) {
		language = lang;
	}

	/**
	 * Set the secondary residential address type
	 *
	 * @param value is of type String
	 */
	public void setSecAddrType(String sAddrType) {
		secAddrType = sAddrType;
	}

	/**
	 * Set the secondary residential address/town/city
	 *
	 * @param value is of type String
	 */
	public void setSecAddr1(String sAddr1) {
		secAddr1 = sAddr1;
	}

	/**
	 * Set the secondary residential address/town/city
	 *
	 * @param value is of type String
	 */
	public void setSecAddr2(String sAddr2) {
		secAddr2 = sAddr2;
	}

	/**
	 * Set the secondary residential address/town/city
	 *
	 * @param value is of type String
	 */
	public void setSecAddr3(String sAddr3) {
		secAddr3 = sAddr3;
	}

	/**
	 * Set the secondary residential address/town/city
	 *
	 * @param value is of type String
	 */
	public void setSecAddr4(String sAddr4) {
		secAddr4 = sAddr4;
	}

	/**
	 * Set the secondary residential postal code
	 *
	 * @param value if of type String
	 */
	public void setSecPostcode(String sPostcode) {
		secPostcode = sPostcode;
	}

	public void setEndLineIndicator(String endT) {
		this.endLineInd = endT;
	}
	public String getEndLineIndicator() {
		return endLineInd;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

}