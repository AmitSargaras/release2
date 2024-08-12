/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/bus/DDNCollateralInfo.java,v 1.1 2004/06/28 04:37:41 hltan Exp $
 */
package com.integrosys.cms.app.ddn.bus;

//java
import java.io.Serializable;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.bus.ICollateralType;

/**
 * This class is the info holder for ddn collateral info
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/06/28 04:37:41 $ Tag: $Name: $
 */
public class DDNCollateralInfo implements Serializable {
	private long collateralID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String collateralRef = null;

	private ICollateralType collateralType = null;

	private ICollateralSubType collateralSubType = null;

	public long getCollateralID() {
		return this.collateralID;
	}

	public String getCollateralRef() {
		return this.collateralRef;
	}

	public ICollateralType getCollateralType() {
		return this.collateralType;
	}

	public ICollateralSubType getCollateralSubType() {
		return this.collateralSubType;
	}

	public void setCollateralID(long aCollateralID) {
		this.collateralID = aCollateralID;
	}

	public void setCollateralRef(String aCollateralRef) {
		this.collateralRef = aCollateralRef;
	}

	public void setCollateralType(ICollateralType aCollateralType) {
		this.collateralType = aCollateralType;
	}

	public void setCollateralSubType(ICollateralSubType aCollateralSubType) {
		this.collateralSubType = aCollateralSubType;
	}

	/**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
