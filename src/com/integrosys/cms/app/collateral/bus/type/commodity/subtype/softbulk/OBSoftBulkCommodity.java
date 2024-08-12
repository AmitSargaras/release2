/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/subtype/softbulk/OBSoftBulkCommodity.java,v 1.2 2004/06/04 03:39:53 hltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity.subtype.softbulk;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBCommodityCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Commodity of type Soft Bulk Commodity.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:39:53 $ Tag: $Name: $
 */
public class OBSoftBulkCommodity extends OBCommodityCollateral implements ISoftBulkCommodity {
	/**
	 * Default Constructor.
	 */
	public OBSoftBulkCommodity() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_COMMODITY_SOFT_BULK));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ISoftBulkCommodity
	 */
	public OBSoftBulkCommodity(ISoftBulkCommodity obj) {
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
		else if (!(obj instanceof OBSoftBulkCommodity)) {
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