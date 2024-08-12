/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/purchasesales/EBPurchaseAndSalesDetailsLocalHome.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.purchasesales;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

/**
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public interface EBPurchaseAndSalesDetailsLocalHome extends javax.ejb.EJBLocalHome {
	public EBPurchaseAndSalesDetailsLocal create(IPurchaseAndSalesDetails details) throws CreateException;

	public EBPurchaseAndSalesDetailsLocal findByPrimaryKey(Long key) throws FinderException;
}
