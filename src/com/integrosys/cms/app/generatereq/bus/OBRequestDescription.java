/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/OBRequestDescription.java,v 1.2 2003/09/17 07:26:59 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//ofa
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class provide the implementation for the IRequestDescription
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/17 07:26:59 $ Tag: $Name: $
 */
public class OBRequestDescription implements IRequestDescription {
	private IRequestLimitInfo[] requestLimitInfoList = null;

	private Amount totalApprovedLimitAmt = null;

	private Amount totalActivatedLimitAmt = null;

	private IRequestCollateralInfo[] requestCollateralInfoList = null;

	private Amount totalCollateralCMVAmt = null;

	public IRequestLimitInfo[] getRequestLimitInfoList() {
		return this.requestLimitInfoList;
	}

	public Amount getTotalApprovedLimitAmt() {
		return this.totalApprovedLimitAmt;
	}

	public Amount getTotalActivatedLimitAmt() {
		return this.totalActivatedLimitAmt;
	}

	public IRequestCollateralInfo[] getRequestCollateralInfoList() {
		return this.requestCollateralInfoList;
	}

	public Amount getTotalCollateralCMVAmt() {
		return this.totalCollateralCMVAmt;
	}

	public void setRequestLimitInfoList(IRequestLimitInfo[] anIRequestLimitInfoList) {
		this.requestLimitInfoList = anIRequestLimitInfoList;
	}

	public void setTotalApprovedLimitAmt(Amount aTotalApprovedLimitAmt) {
		this.totalApprovedLimitAmt = aTotalApprovedLimitAmt;
	}

	public void setTotalActivatedLimitAmt(Amount aTotalActivatedLimitAmt) {
		this.totalActivatedLimitAmt = aTotalActivatedLimitAmt;
	}

	public void setRequestCollateralInfoList(IRequestCollateralInfo[] anIRequestCollateralInfoList) {
		this.requestCollateralInfoList = anIRequestCollateralInfoList;
	}

	public void setTotalCollateralCMVAmt(Amount aTotalCollateralCMVAmt) {
		this.totalCollateralCMVAmt = aTotalCollateralCMVAmt;
	}

	/**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
