/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/OBAssetBasedCollateral.java,v 1.5 2003/07/24 11:26:07 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateral;

/**
 * This class represents a Collateral of type Asset entity.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/07/24 11:26:07 $ Tag: $Name: $
 */
public class OBAssetBasedCollateral extends OBCollateral implements IAssetBasedCollateral {

	/**
	 * Default Constructor.
	 */
	public OBAssetBasedCollateral() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IAssetBasedCollateral
	 */
	public OBAssetBasedCollateral(IAssetBasedCollateral obj) {
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
		else if (!(obj instanceof OBAssetBasedCollateral)) {
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