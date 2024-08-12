/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/OBRequestHeader.java,v 1.1 2003/09/11 05:48:55 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class provide the implementation for the IRequestHeader
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:48:55 $ Tag: $Name: $
 */
public class OBRequestHeader implements IRequestHeader {
	private String requestTo = null;

	private String requestFrom = null;

	private String signNo = null;

	private Date requestDate = null;

	public String getRequestTo() {
		return this.requestTo;
	}

	public String getRequestFrom() {
		return this.requestFrom;
	}

	public String getSignNo() {
		return this.signNo;
	}

	public Date getRequestDate() {
		return this.requestDate;
	}

	public void setRequestTo(String aRequestTo) {
		this.requestTo = aRequestTo;
	}

	public void setRequestFrom(String aRequestFrom) {
		this.requestFrom = aRequestFrom;
	}

	public void setSignNo(String aSignNo) {
		this.signNo = aSignNo;
	}

	public void setRequestDate(Date aRequestDate) {
		this.requestDate = aRequestDate;
	}

	/**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
