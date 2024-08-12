/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/subtype/nonferrousmetal/OBNonFerrousMetal.java,v 1.2 2004/06/04 03:39:53 hltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity.subtype.nonferrousmetal;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBCommodityCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Commodity of type Non Ferrous Metal.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:39:53 $ Tag: $Name: $
 */
public class OBNonFerrousMetal extends OBCommodityCollateral implements INonFerrousMetal {
	/**
	 * Default Constructor.
	 */
	public OBNonFerrousMetal() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_COMMODITY_NON_FERROUS_METAL));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type INonFerrousMetal
	 */
	public OBNonFerrousMetal(INonFerrousMetal obj) {
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
		else if (!(obj instanceof OBNonFerrousMetal)) {
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