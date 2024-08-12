/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.app.collateral.bus.type.document.subtype.comfort;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.document.OBDocumentCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents a document collateral of type letter of comfort.
 * 
 * @author $Author: kienleong $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2007/02/22 $ Tag: $Name: $
 */
public class OBComfort extends OBDocumentCollateral implements IComfort {
	/**
	 * Default Constructor.
	 */
	public OBComfort() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_DOC_COMFORT));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IComfort
	 */
	public OBComfort(IComfort obj) {
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
		else if (!(obj instanceof OBComfort)) {
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