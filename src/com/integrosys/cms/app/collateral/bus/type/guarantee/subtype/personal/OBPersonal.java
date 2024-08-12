/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/guarantee/subtype/personal/OBPersonal.java,v 1.2 2003/06/25 08:00:22 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.personal;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.guarantee.OBGuaranteeCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Guarantee of type Personal.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/25 08:00:22 $ Tag: $Name: $
 */
public class OBPersonal extends OBGuaranteeCollateral implements IPersonal {
	/**
	 * Default Constructor.
	 */
	public OBPersonal() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_GUARANTEE_PERSONAL));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IPersonal
	 */
	public OBPersonal(IPersonal obj) {
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
		else if (!(obj instanceof OBPersonal)) {
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