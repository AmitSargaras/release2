/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/SCCertificateSearchCriteria.java,v 1.4 2005/05/11 11:45:22 lyng Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

//ofa
import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This is a helper class that will contains all the possible search criteria
 * required by the SC certificate
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/05/11 11:45:22 $ Tag: $Name: $
 */
public class SCCertificateSearchCriteria extends SearchCriteria {
	private long limitProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String category = null;

	private String[] trxStatusList = null;

	private boolean includeClosedSCC;

	/**
	 * Default Constructor
	 */
	public SCCertificateSearchCriteria() {
	}

	public long getLimitProfileID() {
		return this.limitProfileID;
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

	public void setCategory(String aCategory) {
		this.category = aCategory;
	}

	public void setTrxStatusList(String[] aTrxStatusList) {
		this.trxStatusList = aTrxStatusList;
	}

	public boolean isIncludeClosedSCC() {
		return includeClosedSCC;
	}

	public void setIncludeClosedSCC(boolean includeClosedSCC) {
		this.includeClosedSCC = includeClosedSCC;
	}

	/**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
