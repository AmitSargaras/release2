/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/guarantee/subtype/interbrindem/OBInterBranchIndemnity.java,v 1.4 2003/07/21 10:02:01 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gteinwardlc;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.guarantee.OBGuaranteeCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Guarantee of type Interbranch Indemnity.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/07/21 10:02:01 $ Tag: $Name: $
 */
public class OBGteinwardLC extends OBGuaranteeCollateral implements IGteinwardLC {
	/**
	 * Default Constructor.
	 */
	public OBGteinwardLC() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_GUARANTEE_BANK_INWARDLC));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IInterBranchIndemnity
	 */
	public OBGteinwardLC(IGteinwardLC obj) {
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
		else if (!(obj instanceof OBGteinwardLC)) {
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