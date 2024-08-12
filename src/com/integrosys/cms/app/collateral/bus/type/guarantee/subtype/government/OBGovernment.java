/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/guarantee/subtype/government/OBGovernment.java,v 1.2 2003/06/25 08:00:10 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.government;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.guarantee.OBGuaranteeCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Guarantee of type Government.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/25 08:00:10 $ Tag: $Name: $
 */
public class OBGovernment extends OBGuaranteeCollateral implements IGovernment {
	/**
	 * Default Constructor.
	 */
	public OBGovernment() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_GUARANTEE_GOVERNMENT));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IGovernment
	 */
	public OBGovernment(IGovernment obj) {
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
		else if (!(obj instanceof OBGovernment)) {
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