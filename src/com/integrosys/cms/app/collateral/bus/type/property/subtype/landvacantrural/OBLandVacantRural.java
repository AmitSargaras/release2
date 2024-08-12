/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/property/subtype/landvacantrural/OBLandVacantRural.java,v 1.2 2003/06/26 06:42:07 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.property.subtype.landvacantrural;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.property.OBPropertyCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Property of type Land Vacant - Rural.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/26 06:42:07 $ Tag: $Name: $
 */
public class OBLandVacantRural extends OBPropertyCollateral implements ILandVacantRural {
	/**
	 * Default Constructor.
	 */
	public OBLandVacantRural() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_PROP_LAND_VACANT));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ILandVacantRural
	 */
	public OBLandVacantRural(ILandVacantRural obj) {
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
		else if (!(obj instanceof OBLandVacantRural)) {
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