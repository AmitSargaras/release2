/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/property/subtype/comshophouse/OBCommercialShopHouse.java,v 1.6 2006/01/18 05:37:29 priya Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.property.subtype.comshophouse;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.property.OBPropertyCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Property of type Commercial - Shop House.
 * 
 * @author $Author: priya $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/01/18 05:37:29 $ Tag: $Name: $
 */
public class OBCommercialShopHouse extends OBPropertyCollateral implements ICommercialShopHouse {
	/**
	 * Default Constructor.
	 */
	public OBCommercialShopHouse() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_PROP_COM_SHOP_HOUSE));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ICommercialShopHouse
	 */
	public OBCommercialShopHouse(ICommercialShopHouse obj) {
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
		else if (!(obj instanceof OBCommercialShopHouse)) {
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