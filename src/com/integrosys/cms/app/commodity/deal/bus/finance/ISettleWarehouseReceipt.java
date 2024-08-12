/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/finance/ISettleWarehouseReceipt.java,v 1.4 2004/08/30 02:22:52 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.finance;

import java.io.Serializable;

import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.deal.bus.doc.IWarehouseReceipt;

/**
 * This interface represents Settlement Warehouse Receipt.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/30 02:22:52 $ Tag: $Name: $
 */
public interface ISettleWarehouseReceipt extends Serializable {
	/**
	 * Get settlement warehouse receipt id.
	 * 
	 * @return long
	 */
	public long getSettleWarehseRcptID();

	/**
	 * Set settlement warehouse receipt id.
	 * 
	 * @param settleWarehseRcptID of type long
	 */
	public void setSettleWarehseRcptID(long settleWarehseRcptID);

	/**
	 * Get quantity partially released.
	 * 
	 * @return Quantity
	 */
	public Quantity getReleasedQty();

	/**
	 * Set quantity partially released.
	 * 
	 * @param releasedQty of type Quantity
	 */
	public void setReleasedQty(Quantity releasedQty);

	/**
	 * Get warehouse receipt.
	 * 
	 * @return IWarehouseReceipt
	 */
	public IWarehouseReceipt getWarehouseReceipt();

	/**
	 * Set warehouse receipt.
	 * 
	 * @param warehouseReceipt of type IWarehouseReceipt
	 */
	public void setWarehouseReceipt(IWarehouseReceipt warehouseReceipt);

	/**
	 * Get common reference id for actual and staging.
	 * 
	 * @return long
	 */
	public long getRefID();

	/**
	 * Set common reference id for actual and staging.
	 * 
	 * @param refID of type long
	 */
	public void setRefID(long refID);

	/**
	 * Get cms business status.
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set cms business status.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
}