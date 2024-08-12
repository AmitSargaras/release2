/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/WaiverRequestSearchCriteria.java,v 1.2 2003/09/22 02:23:23 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//ofa
import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This is a helper class that will contains all the possible search criteria
 * required by the SC certificate
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/22 02:23:23 $ Tag: $Name: $
 */
public class WaiverRequestSearchCriteria extends SearchCriteria {
	private long limitProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long customerID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String[] trxStatusList = null;

	/**
	 * Default Constructor
	 */
	public WaiverRequestSearchCriteria() {
	}

	public long getLimitProfileID() {
		return this.limitProfileID;
	}

	public long getCustomerID() {
		return this.customerID;
	}

	public String[] getTrxStatusList() {
		return this.trxStatusList;
	}

	public void setLimitProfileID(long aLimitProfileID) {
		this.limitProfileID = aLimitProfileID;
	}

	public void setCustomerID(long aCustomerID) {
		this.customerID = aCustomerID;
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
