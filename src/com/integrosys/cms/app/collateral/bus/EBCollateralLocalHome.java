/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateralLocalHome.java,v 1.3 2003/08/11 13:21:15 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;

/**
 * Defines collateral create and finder methods for clients.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/11 13:21:15 $ Tag: $Name: $
 */
public interface EBCollateralLocalHome extends EJBLocalHome {
	/**
	 * Create collateral record.
	 * 
	 * @param collateral of type ICollateral
	 * @return collateral ejb object
	 * @throws CreateException on error creating the ejb
	 */
	public EBCollateralLocal create(ICollateral collateral) throws CreateException;

	/**
	 * Find the ejb by primary key, the collateral id.
	 * 
	 * @param collateralID collateral id
	 * @return collateral ejb object
	 * @throws FinderException on error finding the ejb
	 */
	public EBCollateralLocal findByPrimaryKey(Long collateralID) throws FinderException;

	/**
	 * Find collateral by SCI security id.
	 * 
	 * @param sciSecurityID SCI security id
	 * @return collateral
	 * @throws FinderException on error finding the collateral
	 */
	public EBCollateralLocal findBySCISecurityID(String sciSecurityID) throws FinderException;

	/**
	 * Search collateral given the criteria.
	 * 
	 * @param criteria collateral search criteria
	 * @return search result
	 * @throws SearchDAOException on error searching the collateral
	 */
	public SearchResult searchCollateral(CollateralSearchCriteria criteria) throws SearchDAOException;
}
