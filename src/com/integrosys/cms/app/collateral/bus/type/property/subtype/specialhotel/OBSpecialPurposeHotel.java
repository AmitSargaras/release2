/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/property/subtype/specialhotel/OBSpecialPurposeHotel.java,v 1.7 2006/01/18 05:40:33 priya Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.property.subtype.specialhotel;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.property.OBPropertyCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Property of type Special Purpose - Hotel.
 * 
 * @author $Author: priya $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/01/18 05:40:33 $ Tag: $Name: $
 */
public class OBSpecialPurposeHotel extends OBPropertyCollateral implements ISpecialPurposeHotel {

	/**
	 * Default Constructor.
	 */
	public OBSpecialPurposeHotel() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_PROP_SPEC_HOTEL));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ISpecialPurposeHotel
	 */
	public OBSpecialPurposeHotel(ISpecialPurposeHotel obj) {
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
		else if (!(obj instanceof OBSpecialPurposeHotel)) {
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