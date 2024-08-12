/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/cash/EBDealCashDepositLocal.java,v 1.3 2004/07/15 16:05:19 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.cash;

import javax.ejb.EJBLocalObject;

/**
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/07/15 16:05:19 $ Tag: $Name: $
 */
public interface EBDealCashDepositLocal extends EJBLocalObject {

	public IDealCashDeposit getValue();

	public void setValue(IDealCashDeposit deposit);

	public void setStatus(String status);
}
