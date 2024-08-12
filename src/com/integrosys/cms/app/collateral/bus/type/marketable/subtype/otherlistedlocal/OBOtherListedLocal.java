/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/marketable/subtype/otherlistedlocal/OBOtherListedLocal.java,v 1.5 2003/08/19 10:58:49 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.marketable.subtype.otherlistedlocal;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.OBEquityCommon;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Marketable Equity of type Other Listed - Local.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/08/19 10:58:49 $ Tag: $Name: $
 */
public class OBOtherListedLocal extends OBEquityCommon implements IOtherListedLocal {
	/**
	 * Default Constructor.
	 */
	public OBOtherListedLocal() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_MAR_OTHERLISTED_LOCAL));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IOtherListedLocal
	 */
	public OBOtherListedLocal(IOtherListedLocal obj) {
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
		else if (!(obj instanceof OBOtherListedLocal)) {
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