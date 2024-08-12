/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/marketable/subtype/govtlocal/OBGovtLocal.java,v 1.6 2003/08/23 08:26:34 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.marketable.subtype.govtlocal;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.OBBondsCommon;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Marketable Equity of type Government Local/ Treasury
 * Bills.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2003/08/23 08:26:34 $ Tag: $Name: $
 */
public class OBGovtLocal extends OBBondsCommon implements IGovtLocal {
	/**
	 * Default Constructor.
	 */
	public OBGovtLocal() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_MAR_GOVT_LOCAL));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IGovtLocal
	 */
	public OBGovtLocal(IGovtLocal obj) {
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
		else if (!(obj instanceof OBGovtLocal)) {
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