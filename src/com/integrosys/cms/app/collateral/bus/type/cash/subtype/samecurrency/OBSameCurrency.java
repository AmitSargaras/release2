/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/cash/subtype/samecurrency/OBSameCurrency.java,v 1.2 2003/06/26 06:27:16 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.cash.subtype.samecurrency;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.cash.OBCashCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Cash of same currency type.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/26 06:27:16 $ Tag: $Name: $
 */
public class OBSameCurrency extends OBCashCollateral implements ISameCurrency {
	/**
	 * Default Constructor.
	 */
	public OBSameCurrency() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_CASH_SAMECCY));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IFXISDA
	 */
	public OBSameCurrency(ISameCurrency obj) {
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
		else if (!(obj instanceof OBSameCurrency)) {
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