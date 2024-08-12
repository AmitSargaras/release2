/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/guarantee/subtype/OBSBLCCommon.java,v 1.3 2003/07/21 10:01:12 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.guarantee.subtype;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.type.guarantee.OBGuaranteeCollateral;

/**
 * This class represents a common SBLC for guarantee.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/07/21 10:01:12 $ Tag: $Name: $
 */
public class OBSBLCCommon extends OBGuaranteeCollateral implements ISBLCCommon {
	/**
	 * Default Constructor.
	 */
	public OBSBLCCommon() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ISBLCCommon
	 */
	public OBSBLCCommon(ISBLCCommon obj) {
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
		else if (!(obj instanceof OBSBLCCommon)) {
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