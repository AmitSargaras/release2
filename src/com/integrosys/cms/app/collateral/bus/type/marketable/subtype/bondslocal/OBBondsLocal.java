/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/marketable/subtype/bondslocal/OBBondsLocal.java,v 1.4 2003/08/19 11:00:35 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.marketable.subtype.bondslocal;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.OBBondsCommon;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Marketable of type Bonds - Local.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/08/19 11:00:35 $ Tag: $Name: $
 */
public class OBBondsLocal extends OBBondsCommon implements IBondsLocal {
	/**
	 * Default Constructor.
	 */
	public OBBondsLocal() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_MAR_BONDS_LOCAL));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IBondsLocal
	 */
	public OBBondsLocal(IBondsLocal obj) {
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
		else if (!(obj instanceof OBBondsLocal)) {
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