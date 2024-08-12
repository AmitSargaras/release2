/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/finance/OBSettleWarehouseReceipt.java,v 1.4 2004/08/30 02:22:52 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.finance;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.deal.bus.doc.IWarehouseReceipt;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Settlement Warehouse Receipt.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/30 02:22:52 $ Tag: $Name: $
 */
public class OBSettleWarehouseReceipt implements ISettleWarehouseReceipt {
	private long settleWarehseRcptID;

	private Quantity releasedQty;

	private IWarehouseReceipt warehouseReceipt;

	private long refID = ICMSConstant.LONG_INVALID_VALUE;

	private String status = ICMSConstant.STATE_ACTIVE;

	/**
	 * Default Constructor.
	 */
	public OBSettleWarehouseReceipt() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ISettleWarehouseReceipt
	 */
	public OBSettleWarehouseReceipt(ISettleWarehouseReceipt obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get settlement warehouse receipt id.
	 * 
	 * @return long
	 */
	public long getSettleWarehseRcptID() {
		return settleWarehseRcptID;
	}

	/**
	 * Set settlement warehouse receipt id.
	 * 
	 * @param settleWarehseRcptID of type long
	 */
	public void setSettleWarehseRcptID(long settleWarehseRcptID) {
		this.settleWarehseRcptID = settleWarehseRcptID;
	}

	/**
	 * Get quantity partially released.
	 * 
	 * @return Quantity
	 */
	public Quantity getReleasedQty() {
		return releasedQty;
	}

	/**
	 * Set quantity partially released.
	 * 
	 * @param releasedQty of type Quantity
	 */
	public void setReleasedQty(Quantity releasedQty) {
		this.releasedQty = releasedQty;
	}

	/**
	 * Get warehouse receipt.
	 * 
	 * @return IWarehouseReceipt
	 */
	public IWarehouseReceipt getWarehouseReceipt() {
		return warehouseReceipt;
	}

	/**
	 * Set warehouse receipt.
	 * 
	 * @param warehouseReceipt of type IWarehouseReceipt
	 */
	public void setWarehouseReceipt(IWarehouseReceipt warehouseReceipt) {
		this.warehouseReceipt = warehouseReceipt;
	}

	/**
	 * Get common reference id for actual and staging.
	 * 
	 * @return long
	 */
	public long getRefID() {
		return refID;
	}

	/**
	 * Set common reference id for actual and staging.
	 * 
	 * @param refID of type long
	 */
	public void setRefID(long refID) {
		this.refID = refID;
	}

	/**
	 * Get cms business status.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set cms business status.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * Return the hash code
	 * 
	 * @return int
	 */
	public int hashCode() {
		String hash = String.valueOf(settleWarehseRcptID);
		return hash.hashCode();
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
		else if (!(obj instanceof ISettleWarehouseReceipt)) {
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