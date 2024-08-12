/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateralDetailHome.java,v 1.1 2003/06/19 06:51:03 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;

/**
 * Home interface to the collateral's details bean.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/19 06:51:03 $ Tag: $Name: $
 */
public interface EBGeneralChargeDetailHome extends EJBHome {
	/**
	 * Create collateral record.
	 * 
	 * @param collateral of type ICollateral
	 * @return collateral detail ejb object
	 * @throws CreateException on error creating the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBGeneralChargeDetail create(IGeneralChargeDetails generalChargeDetails) throws CreateException, RemoteException;

	/**
	 * Find the ejb by primary key, the collateral id.
	 * 
	 * @param collateralID collateral id
	 * @return collateral detail ejb object
	 * @throws FinderException on error finding the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBGeneralChargeDetail findByPrimaryKey(Long collateralID) throws FinderException, RemoteException;
}
