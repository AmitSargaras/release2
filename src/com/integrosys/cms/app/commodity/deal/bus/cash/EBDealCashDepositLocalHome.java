/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/cash/EBDealCashDepositLocalHome.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.cash;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public interface EBDealCashDepositLocalHome extends EJBLocalHome {
	public EBDealCashDepositLocal create(IDealCashDeposit deposit) throws CreateException;

	public EBDealCashDepositLocal findByPrimaryKey(Long key) throws FinderException;
}
