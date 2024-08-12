/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBLimitChargeLocalHome.java,v 1.6 2003/09/16 06:06:59 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Local home interface to EBLimitChargeBean.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2003/09/16 06:06:59 $ Tag: $Name: $
 */
public interface EBLimitChargeLocalHome extends EJBLocalHome {
	/**
	 * Create a new collateral limit charge.
	 * 
	 * @param limitCharge the collateral limit charge
	 * @return local limit charge ejb object
	 * @throws CreateException on error creating the ejb
	 */
	public EBLimitChargeLocal create(ILimitCharge limitCharge) throws CreateException;

	/**
	 * Find the charge by primary key, the charge id.
	 * 
	 * @param pk limit charge id of the collateral.
	 * @return local limit charge ejb object
	 * @throws FinderException on error finding the charge
	 */
	public EBLimitChargeLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Find the charge by its reference id.
	 * 
	 * @param refID reference id
	 * @param colID collateral id
	 * @return local limit charge ejb object
	 * @throws FinderException on error find the charge
	 */
	public EBLimitChargeLocal findByRefIDAndColID(long refID, long colID) throws FinderException;
}