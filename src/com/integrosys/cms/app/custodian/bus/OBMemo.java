/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/OBMemo.java,v 1.5 2005/01/28 03:44:43 wltan Exp $
 */
package com.integrosys.cms.app.custodian.bus;

import java.util.Date;

import com.integrosys.cms.app.customer.bus.ICMSCustomer;

/**
 * Implementation class for the IMemo interface
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/01/28 03:44:43 $ Tag: $Name: $
 */
public class OBMemo implements IMemo {
	private String memoReference;

	private Date memoDate;

	private String memoFrom;

	private String memoTo;

	private String memoType;

	private String memoSubject;

	private String famCode;

	private ICustodianDoc[] custodianDocList;

	private ICMSCustomer customer;

	/**
	 * Default Constructor
	 */
	public OBMemo() {
	}

	/**
	 * Getter methods
	 */
	public String getMemoReference() {
		return this.memoReference;
	}

	public Date getMemoDate() {
		return this.memoDate;
	}

	public String getMemoFrom() {
		return this.memoFrom;
	}

	public String getMemoTo() {
		return this.memoTo;
	}

	public String getMemoType() {
		return this.memoType;
	}

	public String getFAMCode() {
		return this.famCode;
	}

	public ICustodianDoc[] getCustodianDocList() {
		return this.custodianDocList;
	}

	/**
	 * Setter methods
	 */
	public void setMemoReference(String aMemoReference) {
		this.memoReference = aMemoReference;
	}

	public void setMemoDate(Date aMemoDate) {
		this.memoDate = aMemoDate;
	}

	public void setMemoFrom(String aMemoFrom) {
		this.memoFrom = aMemoFrom;
	}

	public void setMemoTo(String aMemoTo) {
		this.memoTo = aMemoTo;
	}

	public void setMemoType(String aMemoType) {
		this.memoType = aMemoType;
	}

	public String getMemoSubject() {
		return memoSubject;
	}

	public void setMemoSubject(String memoSubject) {
		this.memoSubject = memoSubject;
	}

	public void setFAMCode(String famCode) {
		this.famCode = famCode;
	}

	public void setCustodianDocList(ICustodianDoc[] anICustodianDocList) {
		this.custodianDocList = anICustodianDocList;
	}

	public ICMSCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(ICMSCustomer customer) {
		this.customer = customer;
	}
}