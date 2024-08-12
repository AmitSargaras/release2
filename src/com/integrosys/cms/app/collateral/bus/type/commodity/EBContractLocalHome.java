/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBContractLocalHome.java,v 1.3 2004/06/09 03:01:41 dayanand Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Defines Contract home methods for clients.
 * 
 * @author $Author: dayanand $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/06/09 03:01:41 $ Tag: $Name: $
 */
public interface EBContractLocalHome extends EJBLocalHome {
	/**
	 * Create collateral record.
	 * 
	 * @param collateral of type ICollateral
	 * @return Contract - ejb object
	 * @throws CreateException on error creating the ejb
	 */
	public EBContractLocal create(IContract collateral) throws CreateException;

	/**
	 * Find collateral by primary key, the collateral id.
	 * 
	 * @param theID ID of the entity
	 * @return Contract - ejb object
	 * @throws FinderException on error finding the collateral
	 */
	public EBContractLocal findByPrimaryKey(Long theID) throws FinderException;

	/**
	 * finds all contracts
	 * @return
	 * @throws FinderException
	 */
	public Collection findAll() throws FinderException;

	public Collection findAllNotDeleted() throws FinderException;

}
