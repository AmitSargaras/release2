/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/bus/CCTaskSearchCriteria.java,v 1.1 2003/08/31 13:56:24 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.bus;

//ofa
import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This is a helper class that will contains all the possible search criteria
 * required by the CC task
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/31 13:56:24 $ Tag: $Name: $
 */
public class CCTaskSearchCriteria extends SearchCriteria {
	private long limitProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String customerCategory = null;

	private long customerID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String domicileCountry = null;

	private String orgCode = null;

	private String[] trxStatusList = null;

	/**
	 * Default Constructor
	 */
	public CCTaskSearchCriteria() {
	}

	public long getLimitProfileID() {
		return this.limitProfileID;
	}

	public String getCustomerCategory() {
		return this.customerCategory;
	}

	public long getCustomerID() {
		return this.customerID;
	}

	public String getDomicileCountry() {
		return this.domicileCountry;
	}

	public String getOrgCode() {
		return this.orgCode;
	}

	public String[] getTrxStatusList() {
		return this.trxStatusList;
	}

	public void setLimitProfileID(long aLimitProfileID) {
		this.limitProfileID = aLimitProfileID;
	}

	public void setCustomerCategory(String aCustomerCategory) {
		this.customerCategory = aCustomerCategory;
	}

	public void setCustomerID(long aCustomerID) {
		this.customerID = aCustomerID;
	}

	public void setDomicileCountry(String aDomicileCountry) {
		this.domicileCountry = aDomicileCountry;
	}

	public void setOrgCode(String anOrgCode) {
		this.orgCode = anOrgCode;
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
