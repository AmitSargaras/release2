/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/bus/CCDocumentLocationSearchResult.java,v 1.1 2004/02/17 02:12:02 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.bus;

//java
import java.io.Serializable;

import com.integrosys.base.techinfra.util.AccessorUtil;

//app

/**
 * This interface defines the list of attributes that is required for CC
 * Documentation Location
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/02/17 02:12:02 $ Tag: $Name: $
 */
public class CCDocumentLocationSearchResult implements Serializable {
	private long docLocationID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String docLocationCategory = null;

	private long customerID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String docLocationCtry = null;

	private String docLocationOrgCode = null;

	private String trxID = null;

	private String trxStatus = null;

	/**
	 * Get the doc location ID
	 * @return long - the documentation location ID
	 */
	public long getDocLocationID() {
		return this.docLocationID;
	}

	public String getDocLocationCategory() {
		return this.docLocationCategory;
	}

	/**
	 * Get the customer ID
	 * @return long - the customer ID
	 */
	public long getCustomerID() {
		return this.customerID;
	}

	/**
	 * Get the documentation location country
	 * @return String - the documentation location country
	 */
	public String getDocLocationCountry() {
		return this.docLocationCtry;
	}

	/**
	 * Get the documentation organisation code
	 * @return String - the documentation location org code
	 */
	public String getDocLocationOrgCode() {
		return this.docLocationOrgCode;
	}

	/**
	 * Get the trx ID
	 * @return String - the trx ID
	 */
	public String getTrxID() {
		return this.trxID;
	}

	/**
	 * Get the trx status
	 * @return String - the trx status
	 */
	public String getTrxStatus() {
		return this.trxStatus;
	}

	/**
	 * Set the documentation location ID
	 * @param aDocLocationID of long type
	 */
	public void setDocLocationID(long aDocLocationID) {
		this.docLocationID = aDocLocationID;
	}

	/**
	 * Set the documentation location category
	 * @param aDocLocationCategory of String type
	 */
	public void setDocLocationCategory(String aDocLocationCategory) {
		this.docLocationCategory = aDocLocationCategory;
	}

	/**
	 * Set the customer ID
	 * @param aCustomerID of long type
	 */
	public void setCustomerID(long aCustomerID) {
		this.customerID = aCustomerID;
	}

	/**
	 * Set the documentation location country
	 * @param aDocLocationCountry of String type
	 */
	public void setDocLocationCountry(String aDocLocationCountry) {
		this.docLocationCtry = aDocLocationCountry;
	}

	/**
	 * Set the collateral location
	 * @param aCollateralLocation of String type
	 */
	public void setDocLocationOrgCode(String aDocLocationOrgCode) {
		this.docLocationOrgCode = aDocLocationOrgCode;
	}

	/**
	 * Set the trx ID
	 * @param aTrxID of String type
	 */
	public void setTrxID(String aTrxID) {
		this.trxID = aTrxID;
	}

	/**
	 * Set the trx status
	 * @param aTrxStatus of String type
	 */
	public void setTrxStatus(String aTrxStatus) {
		this.trxStatus = aTrxStatus;
	}

	/**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
