/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBContractLocal.java,v 1.5 2004/07/21 06:12:06 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

/**
 * Defines Contract home methods for clients.
 *
 * @author  $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since   $Date: 2004/07/21 06:12:06 $
 * Tag:     $Name:  $
 */
import javax.ejb.EJBLocalObject;

public interface EBContractLocal extends EJBLocalObject {
	/**
	 * Retrieve an instance of commodity contract.
	 * 
	 * @return IContract - the object encapsulating the contract info
	 */
	public IContract getValue();

	/**
	 * Set the commodity contract info.
	 * 
	 * @param value of type IContract
	 */
	public void setValue(IContract value);

	/**
	 * To soft delete the commodity contract.
	 */
	public void softDelete();
}