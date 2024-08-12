/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/finance/EBHedgePriceExtensionLocal.java,v 1.3 2004/07/15 16:05:39 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.finance;

/**
 * @author $Author: lyng $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/07/15 16:05:39 $ Tag: $Name: $
 */
public interface EBHedgePriceExtensionLocal extends javax.ejb.EJBLocalObject {
	public IHedgePriceExtension getValue();

	public void setValue(IHedgePriceExtension extension);

	public void setStatus(String status);
}
