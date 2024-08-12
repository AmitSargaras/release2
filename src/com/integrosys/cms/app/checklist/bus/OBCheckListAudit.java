/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/OBCheckListAudit.java,v 1.2 2003/10/29 11:29:11 hltan Exp $
 */
package com.integrosys.cms.app.checklist.bus;

//ofa
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.bus.ICollateralType;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;

/**
 * This interface defines the list of attributes that is required for audit
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/10/29 11:29:11 $ Tag: $Name: $
 */
public class OBCheckListAudit implements ICheckListAudit {
	private String customerCategory = null;

	private long customerID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String customerName = null;

	private long checkListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long collateralID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private ICollateral collateral = null;

	private IAuditItem[] auditItemList = null;

	OBCheckListAudit() {
	}

	public String getCustomerCategory() {
		return this.customerCategory;
	}

	public long getCustomerID() {
		return this.customerID;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public long getCheckListID() {
		return this.checkListID;
	}

	public long getCollateralID() {
		return this.collateralID;
	}

	public ICollateral getCollateral() {
		return this.collateral;
	}

	public String getCollateralRef() {
		if (getCollateral() != null) {
			return getCollateral().getSCISecurityID();
		}
		return null;
	}

	public ICollateralType getCollateralType() {
		if (getCollateral() != null) {
			return getCollateral().getCollateralType();
		}
		return null;
	}

	public ICollateralSubType getCollateralSubType() {
		if (getCollateral() != null) {
			return getCollateral().getCollateralSubType();
		}
		return null;
	}

	public ILimitCharge[] getLimitChargeList() {
		if (getCollateral() != null) {
			return getCollateral().getLimitCharges();
		}
		return null;
	}

	public IAuditItem[] getAuditItemList() {
		return this.auditItemList;
	}

	public void setCustomerCategory(String aCustomerCategory) {
		this.customerCategory = aCustomerCategory;
	}

	public void setCustomerID(long aCustomerID) {
		this.customerID = aCustomerID;
	}

	public void setCustomerName(String aCustomerName) {
		this.customerName = aCustomerName;
	}

	public void setCheckListID(long aCheckListID) {
		this.checkListID = aCheckListID;
	}

	public void setCollateralID(long aCollateralID) {
		this.collateralID = aCollateralID;
	}

	public void setCollateral(ICollateral anICollateral) {
		this.collateral = anICollateral;
	}

	public void setAuditItemList(IAuditItem[] anIAuditItemList) {
		this.auditItemList = anIAuditItemList;
	}

	/**
	 * Prints a String representation of this object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
