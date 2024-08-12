/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/cash/EBCashDepositLocal.java,v 1.2 2003/10/23 06:20:47 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.cash;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.customer.bus.CustomerException;

/**
 * Entity bean local interface for cash collateral.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/10/23 06:20:47 $ Tag: $Name: $
 */
public interface EBCashDepositLocal extends EJBLocalObject {
	/**
	 * Get cash deposit.
	 * 
	 * @return cash deposit
	 */
	public ICashDeposit getValue() throws CollateralException;

	/**
	 * Set cash deposit.
	 * 
	 * @param deposit is of type ICashDeposit
	 */
	public void setValue(ICashDeposit deposit);//throws CollateralException, ConcurrentUpdateException;

	/**
	 * Set the status of the cash deposit.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
}