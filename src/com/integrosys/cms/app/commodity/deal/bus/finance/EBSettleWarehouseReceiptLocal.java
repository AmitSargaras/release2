/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/finance/EBSettleWarehouseReceiptLocal.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.finance;

import javax.ejb.EJBLocalObject;

/**
 * Entity bean local interface to EBSettleWarehouseReceiptBean.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public interface EBSettleWarehouseReceiptLocal extends EJBLocalObject {
	/**
	 * Get settlement warehouse receipt.
	 * 
	 * @return settlement warehouse receipt
	 */
	public ISettleWarehouseReceipt getValue();

	/**
	 * Set settlement warehouse receipt.
	 * 
	 * @param settleWarehseRcpt is of type ISettleWarehouseReceipt
	 */
	public void setValue(ISettleWarehouseReceipt settleWarehseRcpt);

	/**
	 * Set cms business status.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
}