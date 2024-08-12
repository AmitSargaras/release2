/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/marketable/subtype/bondsforeign/OBBondsForeign.java,v 1.4 2003/08/19 11:00:43 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.marketable.subtype.bondsforeign;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.OBBondsCommon;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Marketable of type Bonds - Foreign.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/08/19 11:00:43 $ Tag: $Name: $
 */
public class OBBondsForeign extends OBBondsCommon implements IBondsForeign {
	/**
	 * Default Constructor.
	 */
	public OBBondsForeign() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_MAR_BONDS_FOREIGN));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IBondsForeign
	 */
	public OBBondsForeign(IBondsForeign obj) {
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
		else if (!(obj instanceof OBBondsForeign)) {
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