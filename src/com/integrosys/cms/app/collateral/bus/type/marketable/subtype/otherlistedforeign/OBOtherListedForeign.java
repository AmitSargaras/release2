/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/marketable/subtype/otherlistedforeign/OBOtherListedForeign.java,v 1.5 2003/08/19 10:59:12 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.marketable.subtype.otherlistedforeign;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.OBEquityCommon;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Marketable Equity of type Other Listed - Foreign.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/08/19 10:59:12 $ Tag: $Name: $
 */
public class OBOtherListedForeign extends OBEquityCommon implements IOtherListedForeign {
	/**
	 * Default Constructor.
	 */
	public OBOtherListedForeign() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_MAR_OTHERLISTED_FOREIGN));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IOtherListedForeign
	 */
	public OBOtherListedForeign(IOtherListedForeign obj) {
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
		else if (!(obj instanceof OBOtherListedForeign)) {
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