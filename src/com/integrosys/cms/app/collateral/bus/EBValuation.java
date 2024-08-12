/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBValuation.java,v 1.2 2003/08/06 06:33:18 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * Entity bean remote interface for valuation.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/06 06:33:18 $ Tag: $Name: $
 */
public interface EBValuation extends EJBObject {
	/**
	 * Get the valuation business object.
	 * 
	 * @return valuation
	 * @throws RemoteException on error during remote method call
	 */
	public IValuation getValue() throws RemoteException;

	/**
	 * Set the valuation to this entity.
	 * 
	 * @param valuation is of type IValuation
	 * @throws RemoteException on error during remote method call
	 */
	public void setValue(IValuation valuation) throws RemoteException;
}