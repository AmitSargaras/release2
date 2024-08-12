/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/PartialSCCertificateSearchCriteria.java,v 1.1 2005/05/12 02:42:46 lyng Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

//ofa
import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This is a helper class that will contains all the possible search criteria
 * required by the PSC certificate
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/05/12 02:42:46 $ Tag: $Name: $
 */
public class PartialSCCertificateSearchCriteria extends SearchCriteria {
	private long limitProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String category = null;

	private String[] trxStatusList = null;

	private boolean includeClosedPSCC;

	/**
	 * Default Constructor
	 */
	public PartialSCCertificateSearchCriteria() {
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

	public boolean isIncludeClosedPSCC() {
		return includeClosedPSCC;
	}

	public void setIncludeClosedPSCC(boolean includeClosedPSCC) {
		this.includeClosedPSCC = includeClosedPSCC;
	}

	/**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
