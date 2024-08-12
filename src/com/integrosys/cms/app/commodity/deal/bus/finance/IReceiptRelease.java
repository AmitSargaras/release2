/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/finance/IReceiptRelease.java,v 1.3 2004/09/08 06:36:58 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.finance;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.cms.app.commodity.common.Quantity;

/**
 * This interface represents Warehouse Receipt Release details.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/09/08 06:36:58 $ Tag: $Name: $
 */
public interface IReceiptRelease extends Serializable {
	/**
	 * Get warehouse receipt release id.
	 * 
	 * @return long
	 */
	public long getReleaseID();

	/**
	 * Set warehouse receipt release id.
	 * 
	 * @param releaseID of type long
	 */
	public void setReleaseID(long releaseID);

	/**
	 * Get warehouse receipt release date.
	 * 
	 * @return Date
	 */
	public Date getReleaseDate();

	/**
	 * Set warehouse receipt release date.
	 * 
	 * @param releaseDate of type Date
	 */
	public void setReleaseDate(Date releaseDate);

	/**
	 * Get total released quantity.
	 * 
	 * @return Quantity
	 */
	public Quantity getReleasedQty();

	/**
	 * Set total released quantity.
	 * 
	 * @param releasedQty of type Quantity
	 */
	public void setReleasedQty(Quantity releasedQty);

	/**
	 * Get a list of released warehouse receipts.
	 * 
	 * @return ISettleWarehouseReceipt[]
	 */
	public ISettleWarehouseReceipt[] getSettleWarehouseReceipts();

	/**
	 * Set a list of released warehouse receipts.
	 * 
	 * @param settleWarehouseReceipts of type ISettleWarehouseReceipt[]
	 */
	public void setSettleWarehouseReceipts(ISettleWarehouseReceipt[] settleWarehouseReceipts);

	/**
	 * Get common reference id of actual and staging release.
	 * 
	 * @return long
	 */
	public long getRefID();

	/**
	 * Set common reference id of actual and staging release.
	 * 
	 * @param refID of type long
	 */
	public void setRefID(long refID);

	/**
	 * Get cms business status of this release.
	 * 
	 * @return of type String
	 */
	public String getStatus();

	/**
	 * Set cms business status to this release.
	 * 
	 * @param status
	 */
	public void setStatus(String status);

	/**
	 * Get total quantity released.
	 * 
	 * @return Quantity
	 */
	public Quantity getTotalReleasedQuantity();
}
