/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBValuationHome.java,v 1.4 2003/08/13 02:33:43 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * Entity bean home interface for valuation.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/08/13 02:33:43 $ Tag: $Name: $
 */
public interface EBValuationHome extends EJBHome {
	/**
	 * Create a new valuation.
	 * 
	 * @param valuation of type IValuation
	 * @return valuation ejb object
	 * @throws CreateException on error creating the ejb object
	 * @throws RemoteException on error during remote method call
	 */
	public EBValuation create(IValuation valuation) throws CreateException, RemoteException;

	/**
	 * Find the valuation by its primary key.
	 * 
	 * @param valuationID valuation id
	 * @return valuation ejb object
	 * @throws FinderException on error finding the ejb object
	 * @throws RemoteException on error during remote method call
	 */
	public EBValuation findByPrimaryKey(Long valuationID) throws FinderException, RemoteException;
}