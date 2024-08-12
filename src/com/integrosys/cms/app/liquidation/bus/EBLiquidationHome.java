/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * Remote home interface for EBLiquidation.
 * 
 * @author $Author: Siew Kheat $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBLiquidationHome extends EJBHome {

	/**
	 * Called by the client to create an local EJB object.
	 * 
	 * @param liquidation of type ILiquidation
	 * @return Liquidation ejb object
	 * @throws javax.ejb.CreateException on error while creating the ejb
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public EBLiquidation create(ILiquidation liquidation) throws CreateException, RemoteException;

	/**
	 * Find the local ejb object by primary key, the Liquidation ID.
	 * 
	 * @param liquidation LiquidationID
	 * @return local Liquidation ejb object
	 * @throws javax.ejb.FinderException on error while finding the ejb
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public EBLiquidation findByPrimaryKey(Long liquidation) throws FinderException, RemoteException;
}
