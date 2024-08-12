/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/OBRequestCollateralInfo.java,v 1.2 2003/09/20 10:06:59 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//ofa
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.bus.ICollateralType;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class that provides the implementation for the IRequestCollateralInfo
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/20 10:06:59 $ Tag: $Name: $
 */
public class OBRequestCollateralInfo implements IRequestCollateralInfo {
	private long collateralID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private ICollateralType collateralType = null;

	private ICollateralSubType collateralSubType = null;

	private String[] natureOfChargeList = null;

	private Amount collateralCMVAmt = null;

	private String checkListStatus = ICMSConstant.STATE_CHECKLIST_IN_PROGRESS;

	public long getCollateralID() {
		return this.collateralID;
	}

	public ICollateralType getCollateralType() {
		return this.collateralType;
	}

	public ICollateralSubType getCollateralSubType() {
		return this.collateralSubType;
	}

	public String[] getNatureOfChargeList() {
		return this.natureOfChargeList = natureOfChargeList;
	}

	public Amount getCollateralCMVAmt() {
		return this.collateralCMVAmt;
	}

	public String getCheckListStatus() {
		return this.checkListStatus;
	}

	public void setCollateralID(long aCollateralID) {
		this.collateralID = aCollateralID;
	}

	public void setCollateralType(ICollateralType anICollateralType) {
		this.collateralType = anICollateralType;
	}

	public void setCollateralSubType(ICollateralSubType anICollateralSubType) {
		this.collateralSubType = anICollateralSubType;
	}

	public void setNatureOfChargeList(String[] aNatureOfChargeList) {
		this.natureOfChargeList = aNatureOfChargeList;
	}

	public void setCollateralCMVAmt(Amount aCollateralCMVAmt) {
		this.collateralCMVAmt = aCollateralCMVAmt;
	}

	public void setCheckListStatus(String aCheckListStatus) {
		this.checkListStatus = aCheckListStatus;
	}

	/**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
