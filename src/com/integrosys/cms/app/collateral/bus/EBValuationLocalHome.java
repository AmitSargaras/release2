/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBValuationLocalHome.java,v 1.3 2003/08/06 06:34:01 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Entity bean local home interface for valuation. It defines methods for local
 * clients to create/find the valuation.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/06 06:34:01 $ Tag: $Name: $
 */
public interface EBValuationLocalHome extends EJBLocalHome {
	/**
	 * Create a new valuation.
	 * 
	 * @param valuation of type IValuation
	 * @return local valuation ejb object
	 * @throws CreateException on error creating the ejb object
	 */
	public EBValuationLocal create(IValuation valuation) throws CreateException;

	/**
	 * Find the valuation entity bean by its primary key.
	 * 
	 * @param valuationID valuation id
	 * @return local valuation ejb object
	 * @throws FinderException on error finding the ejb object
	 */
	public EBValuationLocal findByPrimaryKey(Long valuationID) throws FinderException;
}