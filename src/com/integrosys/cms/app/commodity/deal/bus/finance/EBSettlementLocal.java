/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/finance/EBSettlementLocal.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.finance;

import javax.ejb.EJBLocalObject;

/**
 * Entity bean local interface to EBSettlementBean.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public interface EBSettlementLocal extends EJBLocalObject {
	/**
	 * Get the deal settlement details.
	 * 
	 * @return settlement details
	 */
	public ISettlement getValue();

	/**
	 * Set the deal settlement details.
	 * 
	 * @param settlement is of type ISettlement
	 */
	public void setValue(ISettlement settlement);

	/**
	 * Set cms business status.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
}