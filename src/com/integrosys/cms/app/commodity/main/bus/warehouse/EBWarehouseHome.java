/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/warehouse/EBWarehouseHome.java,v 1.2 2004/06/04 04:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.warehouse;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * Defines TitleDOcument home methods for clients.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 04:53:33 $ Tag: $Name: $
 */
public interface EBWarehouseHome extends EJBHome {
	/**
	 * Create collateral record.
	 * 
	 * @param collateral of type ICollateral
	 * @return Warehouse - ejb object
	 * @throws CreateException on error creating the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBWarehouse create(IWarehouse collateral) throws CreateException, RemoteException;

	/**
	 * Find collateral by primary key, the collateral id.
	 * 
	 * @param theID ID of the entity
	 * @return Warehouse - ejb object
	 * @throws FinderException on error finding the collateral
	 * @throws RemoteException on error during remote method call
	 */
	public EBWarehouse findByPrimaryKey(Long theID) throws FinderException, RemoteException;

	/**
	 * finds all Warehouses
	 * @return
	 * @throws FinderException
	 * @throws RemoteException
	 */
	public Collection findAll() throws FinderException, RemoteException;

	public Collection findAllNotDeleted() throws FinderException, RemoteException;

	public Collection findByCountryCode(String countryCode) throws FinderException, RemoteException;

	public Collection findByGroupID(Long groupID) throws FinderException, RemoteException;

	public EBWarehouse findByCommonRef(Long commonRef) throws FinderException, RemoteException;

	public EBWarehouse findByGroupIDCommonRef(Long groupID, Long commonRef) throws FinderException, RemoteException;

}
