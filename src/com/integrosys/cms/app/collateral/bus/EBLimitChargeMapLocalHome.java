/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBLimitChargeMapLocalHome.java,v 1.3 2006/08/30 11:38:43 hmbao Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Local home interface to EBLimitChargeMapBean.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2006/08/30 11:38:43 $ Tag: $Name: $
 */
public interface EBLimitChargeMapLocalHome extends EJBLocalHome {
	/**
	 * Create a mapping for limit and charge.
	 * 
	 * @param chargeMap of type ILimitChargeMap
	 * @return local limit charge map ejb object
	 * @throws CreateException on error creating the ejb
	 */
	public EBLimitChargeMapLocal create(ILimitChargeMap chargeMap) throws CreateException;

	/**
	 * Find the ejb by primary key, the limit charge map id.
	 * 
	 * @param pk limit charge map id
	 * @return local limit charge map ejb object
	 * @throws FinderException on error finding the limit charge map
	 */
	public EBLimitChargeMapLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Find the limit charge map given the collateral id and limit id.
	 * 
	 * @param collateralID collateral id
	 * @param limitID limit id
	 * @return a collection of limit charge map
	 * @throws FinderException on error finding the limit charge map
	 */
	// public Collection findByColIDAndLimitID (long collateralID, long limitID)
	// throws FinderException;
}