/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/doc/EBWarehouseReceiptLocalHome.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.doc;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Defines warehouse details create and finder methods for local clients.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public interface EBWarehouseReceiptLocalHome extends EJBLocalHome {
	/**
	 * Create warehouse details record.
	 * 
	 * @param receipt of type IWarehouseReceipt
	 * @return warehouse receipt ejb object
	 * @throws CreateException on error creating the ejb
	 */
	public EBWarehouseReceiptLocal create(IWarehouseReceipt receipt) throws CreateException;

	/**
	 * Find warehouse details by its primary key, the receipt id.
	 * 
	 * @param receiptID warehouse receipt id
	 * @return warehouse receipt ejb object
	 * @throws FinderException on error finding the deal
	 */
	public EBWarehouseReceiptLocal findByPrimaryKey(Long receiptID) throws FinderException;
}
