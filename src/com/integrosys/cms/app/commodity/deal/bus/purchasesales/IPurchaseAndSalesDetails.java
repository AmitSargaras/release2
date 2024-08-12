/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/purchasesales/IPurchaseAndSalesDetails.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.purchasesales;

import java.io.Serializable;

/**
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public interface IPurchaseAndSalesDetails extends Serializable {
	public long getDetailsID();

	public void setDetailsID(long purchaseAndSalesID);

	public IPurchaseDetails getPurchaseDetails();

	public void setPurchaseDetails(IPurchaseDetails purchaseDetails);

	public ISalesDetails getSalesDetails();

	public void setSalesDetails(ISalesDetails salesDetails);

	public String getStatus();

	public void setStatus(String status);

	public long getCommonReferenceID();

	public void setCommonReferenceID(long commonReferenceID);
}
