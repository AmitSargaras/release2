/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/purchasesales/EBPurchaseAndSalesDetailsLocal.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.purchasesales;

/**
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public interface EBPurchaseAndSalesDetailsLocal extends javax.ejb.EJBLocalObject {
	/**
	 * Get the purchase and sales details business object.
	 * 
	 * @return purchase and sales details
	 */
	public IPurchaseAndSalesDetails getValue();

	/**
	 * Set the purchase and sales details business object.
	 * 
	 * @param details - purchase and sales details is of type
	 *        IPurchaseAndSalesDetails
	 */
	public void setValue(IPurchaseAndSalesDetails details);
}
