/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateralLimitMapLocalHome.java,v 1.6 2006/08/30 11:38:43 hmbao Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Local home interface to EBCollateralLimitMapBean.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/08/30 11:38:43 $ Tag: $Name: $
 */
public interface EBCollateralLimitMapLocalHome extends EJBLocalHome {
	/**
	 * Create a new collateral limit map.
	 * 
	 * @param limitMap the collateral limit map
	 * @return local limit map ejb object
	 * @throws CreateException on error creating the ejb
	 */
	public EBCollateralLimitMapLocal create(ICollateralLimitMap limitMap) throws CreateException;

	/**
	 * Find the ejb by primary key, the charge id.
	 * 
	 * @param pk charge id of the collateral limit map.
	 * @return local limit map ejb object
	 * @throws FinderException on error finding the collateral limit map
	 */
	public EBCollateralLimitMapLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Find the ejb by collateral id and limit id.
	 * 
	 * @param collateralID collateral id
	 * @param limitID limit id
	 * @return local limit map ejb object
	 * @throws FinderException on error finding the collateral limit map
	 */
	public Collection findByColIDAndLimitID(Long collateralID, Long limitID) throws FinderException;

	public Collection findByColIDAndCBLimitID(Long collateralID, Long cbLimitID) throws FinderException;

	/**
	 * Find the collateral limits given the collateral id.
	 * 
	 * @param collateralID of type long
	 * @return a list of collateral limits
	 * @throws FinderException on error finding the collateral limit map
	 */
	public Collection findByCollateralID(long collateralID) throws FinderException;
}