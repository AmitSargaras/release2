/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/cash/subtype/hkdusd/OBHKDUSD.java,v 1.3 2003/07/09 07:04:05 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.cash.subtype.hkdusd;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.cash.OBCashCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Cash of type HKD/USD.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/07/09 07:04:05 $ Tag: $Name: $
 */
public class OBHKDUSD extends OBCashCollateral implements IHKDUSD {
	/**
	 * Default Constructor.
	 */
	public OBHKDUSD() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_CASH_HKDUSD));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IHKDUSD
	 */
	public OBHKDUSD(IHKDUSD obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBHKDUSD)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}