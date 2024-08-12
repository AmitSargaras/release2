/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBHedgingContractInfoLocalHome.java,v 1.2 2004/06/04 03:39:53 hltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Defines HedgingContractInfo home methods for clients.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:39:53 $ Tag: $Name: $
 */
public interface EBHedgingContractInfoLocalHome extends EJBLocalHome {
	/**
	 * Create collateral record.
	 * 
	 * @param
	 * @return HedgingContractInfo - ejb object
	 * @throws CreateException on error creating the ejb
	 */
	public EBHedgingContractInfoLocal create(IHedgingContractInfo value) throws CreateException;

	/**
	 * Find collateral by primary key, the collateral id.
	 * 
	 * @param theID ID of the entity
	 * @return HedgingContractInfo - ejb object
	 * @throws FinderException on error finding the collateral
	 */
	public EBHedgingContractInfoLocal findByPrimaryKey(Long theID) throws FinderException;

	/**
	 * finds all contracts
	 * @return
	 * @throws FinderException
	 */
	public Collection findAll() throws FinderException;

}
