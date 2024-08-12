/*
 * Created on Mar 19, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class LmtListForm extends CommonForm {
	private String limitProfileID;

	private String customerID;

	private String sessionCriteria;
	
	public String getSessionCriteria() {
		return sessionCriteria;
	}

	public void setSessionCriteria(String sessionCriteria) {
		this.sessionCriteria = sessionCriteria;
	}

	/**
	 * @return Returns the limitProfileId.
	 */
	public String getLimitProfileID() {
		return limitProfileID;
	}

	/**
	 * @param limitProfileId The limitProfileId to set.
	 */
	public void setLimitProfileID(String limitProfileID) {
		this.limitProfileID = limitProfileID;
	}

	/**
	 * @return Returns the customerID.
	 */
	public String getCustomerID() {
		return customerID;
	}

	/**
	 * @param customerID The customerID to set.
	 */
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
}
