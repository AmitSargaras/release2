/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateralDetailLocalHome.java,v 1.1 2003/07/30 02:35:22 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Home interface to the collateral's details bean.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/30 02:35:22 $ Tag: $Name: $
 */
public interface EBCollateralDetailLocalHome extends EJBLocalHome {
	/**
	 * Create collateral record.
	 * 
	 * @param collateral of type ICollateral
	 * @return collateral detail ejb object
	 * @throws CreateException on error creating the ejb
	 */
	public EBCollateralDetailLocal create(ICollateral collateral) throws CreateException;

	/**
	 * Find the ejb by primary key, the collateral id.
	 * 
	 * @param collateralID collateral id
	 * @return collateral detail ejb object
	 * @throws FinderException on error finding the ejb
	 */
	public EBCollateralDetailLocal findByPrimaryKey(Long collateralID) throws FinderException;
}
