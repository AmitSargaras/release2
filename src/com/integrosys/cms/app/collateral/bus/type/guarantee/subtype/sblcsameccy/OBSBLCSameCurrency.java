/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/guarantee/subtype/sblcsameccy/OBSBLCSameCurrency.java,v 1.2 2003/06/25 08:00:33 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.sblcsameccy;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.OBSBLCCommon;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents guarantee of type Standby LC - Same Currency
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/25 08:00:33 $ Tag: $Name: $
 */
public class OBSBLCSameCurrency extends OBSBLCCommon implements ISBLCSameCurrency {
	/**
	 * Default Constructor.
	 */
	public OBSBLCSameCurrency() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_GUARANTEE_SBLC_SAMECCY));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ISBLCSameCurrency
	 */
	public OBSBLCSameCurrency(ISBLCSameCurrency obj) {
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
		else if (!(obj instanceof OBSBLCSameCurrency)) {
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