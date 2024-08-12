/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/bus/CCDocumentLocationSearchCriteria.java,v 1.1 2004/02/17 02:12:02 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.bus;

//ofa
import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This is a helper class that will contains all the possible search criteria
 * required by the CC documentation location
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/02/17 02:12:02 $ Tag: $Name: $
 */
public class CCDocumentLocationSearchCriteria extends SearchCriteria {
	private long limitProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String docLocationCategory = null;

	private long customerID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String docLocationCtry = null;

	private String docLocationOrgCode = null;

	private String[] trxStatusList = null;

	/**
	 * Default Constructor
	 */
	public CCDocumentLocationSearchCriteria() {
	}

	public long getLimitProfileID() {
		return this.limitProfileID;
	}

	public String getDocLocationCategory() {
		return this.docLocationCategory;
	}

	public long getCustomerID() {
		return this.customerID;
	}

	public String getDocLocationCountry() {
		return this.docLocationCtry;
	}

	public String getDocLocationOrgCode() {
		return this.docLocationOrgCode;
	}

	public String[] getTrxStatusList() {
		return this.trxStatusList;
	}

	public void setLimitProfileID(long aLimitProfileID) {
		this.limitProfileID = aLimitProfileID;
	}

	public void setDocLocationCategory(String aDocLocationCategory) {
		this.docLocationCategory = aDocLocationCategory;
	}

	public void setCustomerID(long aCustomerID) {
		this.customerID = aCustomerID;
	}

	public void setDocLocationCountry(String aDocLocationCountry) {
		this.docLocationCtry = aDocLocationCountry;
	}

	public void setDocLocationOrgCode(String aDocLocationOrgCode) {
		this.docLocationOrgCode = aDocLocationOrgCode;
	}

	public void setTrxStatusList(String[] aTrxStatusList) {
		this.trxStatusList = aTrxStatusList;
	}

	/**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
