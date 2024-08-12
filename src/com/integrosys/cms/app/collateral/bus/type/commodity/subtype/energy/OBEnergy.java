/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/subtype/energy/OBEnergy.java,v 1.2 2004/06/04 03:39:53 hltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity.subtype.energy;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBCommodityCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Commodity of type Energy.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:39:53 $ Tag: $Name: $
 */
public class OBEnergy extends OBCommodityCollateral implements IEnergy {
	/**
	 * Default Constructor.
	 */
	public OBEnergy() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_COMMODITY_ENERGY));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IEnergy
	 */
	public OBEnergy(IEnergy obj) {
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
		else if (!(obj instanceof OBEnergy)) {
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