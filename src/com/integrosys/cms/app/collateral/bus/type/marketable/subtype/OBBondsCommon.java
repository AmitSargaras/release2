/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/marketable/subtype/OBBondsCommon.java,v 1.1 2003/08/19 10:51:50 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.marketable.subtype;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.type.marketable.OBMarketableCollateral;

/**
 * This class represents common attributes for marketable of type bonds.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/19 10:51:50 $ Tag: $Name: $
 */
public class OBBondsCommon extends OBMarketableCollateral implements IBondsCommon {
	/**
	 * Default Constructor.
	 */
	public OBBondsCommon() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IBondsCommon
	 */
	public OBBondsCommon(IBondsCommon obj) {
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
		else if (!(obj instanceof OBBondsCommon)) {
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