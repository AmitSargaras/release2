/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/sublimit/OBSubLimit.java,v 1.1 2005/10/06 05:49:52 hmbao Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity.sublimit;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-26
 * @Tag com.integrosys.cms.ui.collateral.commodity.sublimit.SubLimitItem.java
 */
public class OBSubLimit implements ISubLimit {
	private long subLimitID;

	private long chargeID;

	private String subLimitType;

	private String subLimitCCY;

	private String subLimitAmount;

	private String activeAmount;

	private long groupID;

	private long versionTime;

	private long commonRef;

	private String status;

	private boolean innerFlag;

	public OBSubLimit() {
		subLimitID = ICMSConstant.LONG_INVALID_VALUE;
		groupID = ICMSConstant.LONG_INVALID_VALUE;
		commonRef = ICMSConstant.LONG_INVALID_VALUE;
		innerFlag = false;
	}

	public boolean isEmpty() {
		if (isEmpty(getSubLimitType()) && isEmpty(getSubLimitCCY()) && isEmpty(getActiveAmount())
				&& isEmpty(getSubLimitAmount()) && (subLimitID == ICMSConstant.LONG_INVALID_VALUE)
				&& (innerFlag == false)) {
			return true;
		}
		return false;
	}

	private boolean isEmpty(String str) {
		if ((str == null) || "".equals(str.trim())) {
			return true;
		}
		return false;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit
	 * #getActiveAmount()
	 */
	public String getActiveAmount() {
		return activeAmount;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit
	 * #setActiveAmount(String)
	 */
	public void setActiveAmount(String activeAmount) {
		this.activeAmount = activeAmount;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit
	 * #getSubLimitAmount()
	 */
	public String getSubLimitAmount() {
		return subLimitAmount;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit
	 * #setSubLimitAmount(String)
	 */
	public void setSubLimitAmount(String subLimitAmount) {
		this.subLimitAmount = subLimitAmount;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit
	 * #getSubLimitCCY()
	 */
	public String getSubLimitCCY() {
		return subLimitCCY;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit
	 * #setSubLimitCCY(String)
	 */
	public void setSubLimitCCY(String subLimitCCY) {
		this.subLimitCCY = subLimitCCY;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit
	 * #getSubLimitType()
	 */
	public String getSubLimitType() {
		return subLimitType;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit
	 * #setSubLimitType(String)
	 */
	public void setSubLimitType(String subLimitType) {
		this.subLimitType = subLimitType;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit
	 * #getSubLimitID()
	 */
	public long getSubLimitID() {
		return subLimitID;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit
	 * #setSubLimitID(long)
	 */
	public void setSubLimitID(long subLimitID) {
		this.subLimitID = subLimitID;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit
	 * #getGroupID()
	 */
	public long getGroupID() {
		return groupID;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit
	 * #setGroupID(long)
	 */
	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit
	 * #getCommonRef()
	 */
	public long getCommonRef() {
		return commonRef;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit
	 * #setCommonRef(long)
	 */
	public void setCommonRef(long commonRef) {
		this.commonRef = commonRef;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit
	 * #getVersionTime()
	 */
	public long getVersionTime() {
		return versionTime;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit
	 * #setVersionTime(long)
	 */
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit
	 * #getStatus()
	 */
	public String getStatus() {
		return status;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit
	 * #setStatus(java.lang.String)
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit
	 * #getChargeID()
	 */
	public long getChargeID() {
		return chargeID;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit
	 * #setChargeID(long)
	 */
	public void setChargeID(long chargeID) {
		this.chargeID = chargeID;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit
	 * #isInnerLimit()
	 */
	public boolean isInnerLimit() {
		return innerFlag;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit
	 * #setInnerLimit(boolean)
	 */
	public void setInnerLimit(boolean innerFlag) {
		this.innerFlag = innerFlag;
	}
}
