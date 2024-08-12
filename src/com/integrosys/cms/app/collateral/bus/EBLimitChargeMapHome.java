/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBLimitChargeMapHome.java,v 1.2 2006/08/30 11:38:43 hmbao Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * Home interface to EBLimitChargeMapBean.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2006/08/30 11:38:43 $ Tag: $Name: $
 */
public interface EBLimitChargeMapHome extends EJBHome {
	/**
	 * Create a mapping for limit and charge.
	 * 
	 * @param chargeMap of type ILimitChargeMap
	 * @return limit charge map ejb object
	 * @throws CreateException on error creating the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBLimitChargeMap create(ILimitChargeMap chargeMap) throws CreateException, RemoteException;

	/**
	 * Find the ejb by primary key, the limit charge map id.
	 * 
	 * @param pk limit charge map id
	 * @return limit charge map ejb object
	 * @throws FinderException on error finding the limit charge map
	 * @throws RemoteException on error during remote method call
	 */
	public EBLimitChargeMap findByPrimaryKey(Long pk) throws FinderException, RemoteException;

	/**
	 * Find the limit charge map given the collateral id and limit id.
	 * 
	 * @param collateralID collateral id
	 * @param limitID limit id
	 * @return a collection of limit charge map
	 * @throws FinderException on error finding the limit charge map
	 * @throws RemoteException on error during remote method call
	 */
	// public Collection findByColIDAndLimitID (long collateralID, long limitID)
	// throws FinderException, RemoteException;
}