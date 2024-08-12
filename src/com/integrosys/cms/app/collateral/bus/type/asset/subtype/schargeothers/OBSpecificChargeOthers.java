/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/schargeothers/OBSpecificChargeOthers.java,v 1.4 2006/01/18 05:32:59 priya Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeothers;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.OBChargeCommon;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Asset of type Specific Charge - Others.
 * 
 * @author $Author: priya $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/01/18 05:32:59 $ Tag: $Name: $
 */
public class OBSpecificChargeOthers extends OBChargeCommon implements ISpecificChargeOthers {
	/**
	 * Default Constructor.
	 */
	public OBSpecificChargeOthers() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_OTHERS));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ISpecificChargeOthers
	 */
	public OBSpecificChargeOthers(ISpecificChargeOthers obj) {
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
		else if (!(obj instanceof OBSpecificChargeOthers)) {
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