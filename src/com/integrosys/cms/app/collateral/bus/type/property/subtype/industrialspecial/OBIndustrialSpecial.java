/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * OBIndustrialSpecial
 *
 * Created on 6:29:44 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.collateral.bus.type.property.subtype.industrialspecial;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.property.OBPropertyCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 27, 2007 Time: 6:29:44 PM
 */
public class OBIndustrialSpecial extends OBPropertyCollateral implements IIndustrialSpecial {

	/**
	 * Default Constructor.
	 */
	public OBIndustrialSpecial() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_PROP_SPEC_INDUSTRIAL));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ICommercialGeneral
	 */
	public OBIndustrialSpecial(IIndustrialSpecial obj) {
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
		else if (!(obj instanceof OBIndustrialSpecial)) {
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
