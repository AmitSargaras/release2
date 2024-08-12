/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/cash/EBCashDepositLocalHome.java,v 1.2 2003/08/20 05:54:22 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.cash;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Entity bean local home interface for cash collateral.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/20 05:54:22 $ Tag: $Name: $
 */
public interface EBCashDepositLocalHome extends EJBLocalHome {
	/**
	 * Create cash deposit.
	 * 
	 * @param deposit of type ICashDeposit
	 * @return local cash deposit ejb object
	 * @throws CreateException on error creating the cash deposit
	 */
	public EBCashDepositLocal create(ICashDeposit deposit) throws CreateException;

	/**
	 * Find the cash deposit by its primary key.
	 * 
	 * @param pk cash deposit id
	 * @return local cash deposit ejb object
	 * @throws FinderException on error finding the cash deposit
	 */
	public EBCashDepositLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Find the cash deposit by its reference id.
	 * 
	 * @param refID reference id for staging and actual data
	 * @return local cash deposit ejb object
	 * @throws FinderException on error finding the cash deposit
	 */
	public EBCashDepositLocal findByRefID(long refID) throws FinderException;
}