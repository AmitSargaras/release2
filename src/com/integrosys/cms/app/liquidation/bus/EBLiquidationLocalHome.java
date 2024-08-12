/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Local home interface for EBLiquidation.
 * 
 * @author $Author: Siew Kheat $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBLiquidationLocalHome extends EJBLocalHome {

	/**
	 * Called by the client to create an local EJB object.
	 * 
	 * @param liquidation of type ILiquidation
	 * @return Liquidation ejb object
	 * @throws javax.ejb.CreateException on error while creating the ejb
	 */
	public EBLiquidationLocal create(ILiquidation liquidation) throws CreateException;

	/**
	 * Find the local ejb object by primary key, the Liquidation ID.
	 * 
	 * @param liquidation LiquidationID
	 * @return local Liquidation ejb object
	 * @throws javax.ejb.FinderException on error while finding the ejb
	 */
	public EBLiquidationLocal findByPrimaryKey(Long liquidation) throws FinderException;
}
