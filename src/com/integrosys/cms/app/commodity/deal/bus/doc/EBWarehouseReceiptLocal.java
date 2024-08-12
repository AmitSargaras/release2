/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/doc/EBWarehouseReceiptLocal.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.doc;

import javax.ejb.EJBLocalObject;

/**
 * Entity bean local interface to EBWarehouseReceiptBean.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public interface EBWarehouseReceiptLocal extends EJBLocalObject {
	/**
	 * Get warehouse receipt details.
	 * 
	 * @return warehouse receipt
	 */
	public IWarehouseReceipt getValue();

	/**
	 * Set warehouse receipt details.
	 * 
	 * @param receipt is of type IWarehouseReceipt
	 */
	public void setValue(IWarehouseReceipt receipt);

	/**
	 * Set warehouse CMS business status.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
}