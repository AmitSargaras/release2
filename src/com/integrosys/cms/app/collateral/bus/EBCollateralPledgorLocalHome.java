/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateralPledgorLocalHome.java,v 1.2 2003/06/19 10:48:04 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Entity bean local home interface for EBCollateralPledgorBean.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/19 10:48:04 $ Tag: $Name: $
 */
public interface EBCollateralPledgorLocalHome extends EJBLocalHome {
	/**
	 * Called by the client to create a local EJB object.
	 * 
	 * @param pledgor of type ICollateralPledgor
	 * @return collateral pledgor local ejb object
	 * @throws CreateException on error while creating the ejb
	 */
	public EBCollateralPledgorLocal create(ICollateralPledgor pledgor) throws CreateException;

	/**
	 * Find the local ejb object by primary key.
	 * 
	 * @param pk the collateral pledgor primary key
	 * @return collateral pledgor local ejb object
	 * @throws FinderException on error while finding the ejb
	 */
	public EBCollateralPledgorLocal findByPrimaryKey(Long pk) throws FinderException;
}