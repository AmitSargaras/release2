/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/finance/EBHedgePriceExtensionLocalHome.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.finance;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

/**
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public interface EBHedgePriceExtensionLocalHome extends javax.ejb.EJBLocalHome {
	public EBHedgePriceExtensionLocal create(IHedgePriceExtension extensions) throws CreateException;

	public EBHedgePriceExtensionLocal findByPrimaryKey(Long key) throws FinderException;
}
