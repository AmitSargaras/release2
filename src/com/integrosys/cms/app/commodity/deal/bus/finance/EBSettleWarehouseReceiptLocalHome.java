/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/finance/EBSettleWarehouseReceiptLocalHome.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.finance;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Defines settlement warehouse receipt's create and finder methods for local
 * clients.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public interface EBSettleWarehouseReceiptLocalHome extends EJBLocalHome {
	/**
	 * Create settlement warehouse receipt.
	 * 
	 * @param settleWarehseRcpt of type ISettleWarehouseReceipt
	 * @return settlement warehouse receipt ejb object
	 * @throws CreateException on error creating the ejb
	 */
	public EBSettleWarehouseReceiptLocal create(ISettleWarehouseReceipt settleWarehseRcpt) throws CreateException;

	/**
	 * Find settlement warehouse receipt by its primary key.
	 * 
	 * @param pk settlement warehouse receipt id
	 * @return settlement warehouse receipt ejb object
	 * @throws FinderException on error finding the settlement warehouse receipt
	 */
	public EBSettleWarehouseReceiptLocal findByPrimaryKey(Long pk) throws FinderException;
}
