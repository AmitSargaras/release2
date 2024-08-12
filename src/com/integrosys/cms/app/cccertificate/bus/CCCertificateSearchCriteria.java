/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/bus/CCCertificateSearchCriteria.java,v 1.3 2005/05/12 03:19:23 lyng Exp $
 */
package com.integrosys.cms.app.cccertificate.bus;

//ofa
import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This is a helper class that will contains all the possible search criteria
 * required by the cc certificate
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/05/12 03:19:23 $ Tag: $Name: $
 */
public class CCCertificateSearchCriteria extends SearchCriteria {
	private long limitProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long subProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long pledgorID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long checkListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String category = null;

	private String[] trxStatusList = null;

	private boolean includeClosedCCC;

	/**
	 * Default Constructor
	 */
	public CCCertificateSearchCriteria() {
	}

	public long getLimitProfileID() {
		return this.limitProfileID;
	}

	public long getSubProfileID() {
		return this.subProfileID;
	}

	public long getPledgorID() {
		return this.pledgorID;
	}

	public long getCheckListID() {
		return this.checkListID;
	}

	public String getCategory() {
		return this.category;
	}

	public String[] getTrxStatusList() {
		return this.trxStatusList;
	}

	public void setLimitProfileID(long aLimitProfileID) {
		this.limitProfileID = aLimitProfileID;
	}

	public void setSubProfileID(long aSubProfileID) {
		this.subProfileID = aSubProfileID;
	}

	public void setPledgorID(long aPledgorID) {
		this.pledgorID = aPledgorID;
	}

	public void setCheckListID(long aCheckListID) {
		this.checkListID = aCheckListID;
	}

	public void setCategory(String aCategory) {
		this.category = aCategory;
	}

	public void setTrxStatusList(String[] aTrxStatusList) {
		this.trxStatusList = aTrxStatusList;
	}

	public boolean isIncludeClosedCCC() {
		return includeClosedCCC;
	}

	public void setIncludeClosedCCC(boolean includeClosedCCC) {
		this.includeClosedCCC = includeClosedCCC;
	}

	/**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
