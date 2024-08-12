/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/marketable/subtype/mainindexlocal/OBMainIndexLocal.java,v 1.5 2003/08/19 10:59:42 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.marketable.subtype.mainindexlocal;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.OBEquityCommon;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Marketable Equity of type Main Index - Local.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/08/19 10:59:42 $ Tag: $Name: $
 */
public class OBMainIndexLocal extends OBEquityCommon implements IMainIndexLocal {
	/**
	 * Default Constructor.
	 */
	public OBMainIndexLocal() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_MAR_MAIN_IDX_LOCAL));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IMainIndexLocal
	 */
	public OBMainIndexLocal(IMainIndexLocal obj) {
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
		else if (!(obj instanceof OBMainIndexLocal)) {
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