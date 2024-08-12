/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/OBRequestSubject.java,v 1.1 2003/09/11 05:48:55 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//ofa
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class provides the implementation for the IRequestSubject
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:48:55 $ Tag: $Name: $
 */
public class OBRequestSubject implements IRequestSubject {
	private long customerID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String customerName = null;

	private String subject = null;

	public long getCustomerID() {
		return this.customerID;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setCustomerID(long aCustomerID) {
		this.customerID = aCustomerID;
	}

	public void setCustomerName(String aCustomerName) {
		this.customerName = aCustomerName;
	}

	public void setSubject(String aSubject) {
		this.subject = aSubject;
	}

	/**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
