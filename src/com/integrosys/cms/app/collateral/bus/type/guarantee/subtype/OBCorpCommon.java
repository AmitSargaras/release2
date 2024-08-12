/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/guarantee/subtype/OBCorpCommon.java,v 1.1 2003/06/16 03:51:10 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.guarantee.subtype;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.type.guarantee.OBGuaranteeCollateral;

/**
 * This class represents a common corporate for guarantee.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/16 03:51:10 $ Tag: $Name: $
 */
public class OBCorpCommon extends OBGuaranteeCollateral implements ICorpCommon {
	/**
	 * Default Constructor.
	 */
	public OBCorpCommon() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ICorpCommon
	 */
	public OBCorpCommon(ICorpCommon obj) {
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
		else if (!(obj instanceof OBCorpCommon)) {
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