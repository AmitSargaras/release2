/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/finance/EBSettlementLocalHome.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.finance;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Defines settlement's create and finder methods for local clients.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public interface EBSettlementLocalHome extends EJBLocalHome {
	/**
	 * Create settlement details.
	 * 
	 * @param settlement of type ISettlement
	 * @return settlement ejb object
	 * @throws CreateException on error creating the ejb
	 */
	public EBSettlementLocal create(ISettlement settlement) throws CreateException;

	/**
	 * Find settlement details by its primary key, the settlement id.
	 * 
	 * @param settleID settlement id
	 * @return settlement ejb object
	 * @throws FinderException on error finding the settlement details
	 */
	public EBSettlementLocal findByPrimaryKey(Long settleID) throws FinderException;
}
