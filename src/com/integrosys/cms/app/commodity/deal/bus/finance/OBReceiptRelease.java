/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/finance/OBReceiptRelease.java,v 1.3 2004/09/08 06:36:58 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.finance;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents a Warehouse Receipt Release details.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/09/08 06:36:58 $ Tag: $Name: $
 */
public class OBReceiptRelease implements IReceiptRelease {
	private long releaseID;

	private Date releaseDate;

	private Quantity releasedQty;

	private ISettleWarehouseReceipt[] settleWarehouseReceipts;

	private long refID = ICMSConstant.LONG_INVALID_VALUE;

	private String status = ICMSConstant.STATE_ACTIVE;

	/**
	 * Get warehouse receipt release id.
	 * 
	 * @return long
	 */
	public long getReleaseID() {
		return releaseID;
	}

	/**
	 * Set warehouse receipt release id.
	 * 
	 * @param releaseID of type long
	 */
	public void setReleaseID(long releaseID) {
		this.releaseID = releaseID;
	}

	/**
	 * Get warehouse receipt release date.
	 * 
	 * @return Date
	 */
	public Date getReleaseDate() {
		return releaseDate;
	}

	/**
	 * Set warehouse receipt release date.
	 * 
	 * @param releaseDate of type Date
	 */
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	/**
	 * Get total released quantity.
	 * 
	 * @return Quantity
	 */
	public Quantity getReleasedQty() {
		return releasedQty;
	}

	/**
	 * Set total released quantity.
	 * 
	 * @param releasedQty of type Quantity
	 */
	public void setReleasedQty(Quantity releasedQty) {
		this.releasedQty = releasedQty;
	}

	/**
	 * Get a list of released warehouse receipts.
	 * 
	 * @return ISettleWarehouseReceipt[]
	 */
	public ISettleWarehouseReceipt[] getSettleWarehouseReceipts() {
		return settleWarehouseReceipts;
	}

	/**
	 * Set a list of released warehouse receipts.
	 * 
	 * @param settleWarehouseReceipts of type ISettleWarehouseReceipt[]
	 */
	public void setSettleWarehouseReceipts(ISettleWarehouseReceipt[] settleWarehouseReceipts) {
		this.settleWarehouseReceipts = settleWarehouseReceipts;
	}

	/**
	 * Get common reference id of actual and staging release.
	 * 
	 * @return long
	 */
	public long getRefID() {
		return refID;
	}

	/**
	 * Set common reference id of actual and staging release.
	 * 
	 * @param refID of type long
	 */
	public void setRefID(long refID) {
		this.refID = refID;
	}

	/**
	 * Get cms business status of this release.
	 * 
	 * @return of type String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set cms business status to this release.
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Get total quantity released.
	 * 
	 * @return Quantity
	 */
	public Quantity getTotalReleasedQuantity() {
		try {
			if ((settleWarehouseReceipts == null) || (settleWarehouseReceipts.length == 0)) {
				return null;
			}

			Quantity totalQty = null;
			for (int i = 0; i < settleWarehouseReceipts.length; i++) {
				Quantity qty = null;
				if (settleWarehouseReceipts[i].getReleasedQty() != null) {
					qty = new Quantity(settleWarehouseReceipts[i].getReleasedQty().getQuantity(),
							settleWarehouseReceipts[i].getReleasedQty().getUnitofMeasure());
				}

				if (totalQty == null) {
					totalQty = qty;
				}
				else {
					if (qty != null) {
						totalQty = new Quantity(totalQty.getQuantity().add(qty.getQuantity()), totalQty
								.getUnitofMeasure());
					}
				}
			}
			return totalQty;
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in getting total quantity released! " + e.toString());
		}
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
		String hash = String.valueOf(releaseID);
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
		else if (!(obj instanceof IReceiptRelease)) {
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
