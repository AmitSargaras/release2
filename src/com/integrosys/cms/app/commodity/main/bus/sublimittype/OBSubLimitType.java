/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/sublimittype/OBSubLimitType.java,v 1.2 2005/10/07 02:47:24 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.sublimittype;

import com.integrosys.cms.app.commodity.main.bus.OBCommodityMainInfo;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-15
 * @Tag 
 *      com.integrosys.cms.app.commodity.main.bus.sublimittype.OBSubLimitType.java
 */
public class OBSubLimitType extends OBCommodityMainInfo implements ISubLimitType {
	private long subLimitTypeID;

	private String limitType;

	private String subLimitType;

	private long groupID = ICMSConstant.LONG_INVALID_VALUE;

	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;

	/*
	 * @see
	 * com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitType#
	 * getSubLimitTypeID()
	 */
	public long getSubLimitTypeID() {
		return subLimitTypeID;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitType#
	 * setSubLimitTypeID(long)
	 */
	public void setSubLimitTypeID(long subLimitTypeID) {
		this.subLimitTypeID = subLimitTypeID;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitType#
	 * getLimitType()
	 */
	public String getLimitType() {
		return limitType;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitType#
	 * setLimitType(java.lang.String)
	 */
	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitType#
	 * getSubLimitType()
	 */
	public String getSubLimitType() {
		return subLimitType;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitType#
	 * setSubLimitType(java.lang.String)
	 */
	public void setSubLimitType(String subLimitType) {
		this.subLimitType = subLimitType;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitType#
	 * getGroupID()
	 */
	public long getGroupID() {
		return groupID;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitType#
	 * setGroupID(long)
	 */
	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitType#
	 * getCommonRef()
	 */
	public long getCommonRef() {
		return commonRef;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitType#
	 * setCommonRef(long)
	 */
	public void setCommonRef(long commonRef) {
		this.commonRef = commonRef;
	}
}
