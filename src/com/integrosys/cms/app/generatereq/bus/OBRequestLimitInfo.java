/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/OBRequestLimitInfo.java,v 1.2 2003/09/17 07:26:59 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//ofa
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class provides the implementation for the interface IRequestLimitInfo
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/17 07:26:59 $ Tag: $Name: $
 */
public class OBRequestLimitInfo implements IRequestLimitInfo {
	private long limitID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String limitType = null;

	private Amount approvedLimitAmt = null;

	private Amount activatedLimitAmt = null;

	public long getLimitID() {
		return this.limitID;
	}

	public String getLimitType() {
		return this.limitType;
	}

	public Amount getApprovedLimitAmt() {
		return this.approvedLimitAmt;
	}

	public Amount getActivatedLimitAmt() {
		return this.activatedLimitAmt;
	}

	public void setLimitID(long aLimitID) {
		this.limitID = aLimitID;
	}

	public void setLimitType(String aLimitType) {
		this.limitType = aLimitType;
	}

	public void setApprovedLimitAmt(Amount anApprovedLimitAmt) {
		this.approvedLimitAmt = anApprovedLimitAmt;
	}

	public void setActivatedLimitAmt(Amount anActivatedLimitAmt) {
		this.activatedLimitAmt = anActivatedLimitAmt;
	}

	/**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
