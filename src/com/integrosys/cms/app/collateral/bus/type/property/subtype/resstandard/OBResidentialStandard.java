/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/property/subtype/resstandard/OBResidentialStandard.java,v 1.6 2006/01/18 05:39:48 priya Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.property.subtype.resstandard;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.property.OBPropertyCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Property of type Residential - Standard.
 * 
 * @author $Author: priya $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/01/18 05:39:48 $ Tag: $Name: $
 */
public class OBResidentialStandard extends OBPropertyCollateral implements IResidentialStandard {
	/**
	 * Default Constructor.
	 */
	public OBResidentialStandard() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_PROP_RES_STANDARD));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IResidentialStandard
	 */
	public OBResidentialStandard(IResidentialStandard obj) {
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
		else if (!(obj instanceof OBResidentialStandard)) {
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