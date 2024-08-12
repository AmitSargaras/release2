/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/warehouse/EBWarehouseLocalHome.java,v 1.2 2004/06/04 04:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.warehouse;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Defines TitleDOcument home methods for clients.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 04:53:33 $ Tag: $Name: $
 */
public interface EBWarehouseLocalHome extends EJBLocalHome {
	/**
	 * Create collateral record.
	 * 
	 * @param collateral of type ICollateral
	 * @return Warehouse - ejb object
	 * @throws CreateException on error creating the ejb
	 */
	public EBWarehouseLocal create(IWarehouse collateral) throws CreateException;

	/**
	 * Find collateral by primary key, the collateral id.
	 * 
	 * @param theID ID of the entity
	 * @return Warehouse - ejb object
	 * @throws FinderException on error finding the collateral
	 */
	public EBWarehouseLocal findByPrimaryKey(Long theID) throws FinderException;

	/**
	 * finds all Warehouses
	 * @return
	 * @throws FinderException
	 */
	public Collection findAll() throws FinderException;

	public Collection findAllNotDeleted() throws FinderException;

	public Collection findByCountryCode(String countryCode) throws FinderException;

	public Collection findByGroupID(Long groupID) throws FinderException;

	public EBWarehouseLocal findByCommonRef(Long commonRef) throws FinderException;

	public EBWarehouseLocal findByGroupIDCommonRef(Long groupID, Long commonRef) throws FinderException;

}
