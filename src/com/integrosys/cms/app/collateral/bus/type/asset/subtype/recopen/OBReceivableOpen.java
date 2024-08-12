/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/recopen/OBReceivableOpen.java,v 1.5 2006/01/18 05:29:34 priya Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.recopen;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.OBReceivableCommon;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents asset of type receivables assigned - open.
 * 
 * @author $Author: priya $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/01/18 05:29:34 $ Tag: $Name: $
 */
public class OBReceivableOpen extends OBReceivableCommon implements IReceivableOpen {
	private String approvedBuyer;

	private String approvedBuyerLocation;

	/**
	 * Default Constructor.
	 */
	public OBReceivableOpen() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_ASSET_RECV_OPEN));

	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IReceivableOpen
	 */
	public OBReceivableOpen(IReceivableOpen obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get approved buyer.
	 * 
	 * @return String
	 */
	public String getApprovedBuyer() {
		return approvedBuyer;
	}

	/**
	 * Set approved buyer.
	 * 
	 * @param approvedBuyer is of type String
	 */
	public void setApprovedBuyer(String approvedBuyer) {
		this.approvedBuyer = approvedBuyer;
	}

	/**
	 * Get approved buyer location.
	 * 
	 * @return String
	 */
	public String getApprovedBuyerLocation() {
		return approvedBuyerLocation;
	}

	/**
	 * Set approved buyer location.
	 * 
	 * @param approvedBuyerLocation is of type String
	 */
	public void setApprovedBuyerLocation(String approvedBuyerLocation) {
		this.approvedBuyerLocation = approvedBuyerLocation;
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
		else if (!(obj instanceof OBReceivableOpen)) {
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
