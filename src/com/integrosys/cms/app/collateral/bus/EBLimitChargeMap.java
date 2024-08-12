/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBLimitChargeMap.java,v 1.1 2003/11/06 03:16:09 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * Remote interface for EBLimitChargeMapBean.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/11/06 03:16:09 $ Tag: $Name: $
 */
public interface EBLimitChargeMap extends EJBObject {
	/**
	 * Get the mapping between limit and charge.
	 * 
	 * @return ILimitChargeMap
	 * @throws RemoteException on error during remote method call
	 */
	public ILimitChargeMap getValue() throws RemoteException;

	/**
	 * Set the mapping between limit and charge.
	 * 
	 * @param chargeMap of type ILimitChargeMap
	 * @throws RemoteException on error during remote method call
	 */
	public void setValue(ILimitChargeMap chargeMap) throws RemoteException;

	/**
	 * Set the status of the limit charge map.
	 * 
	 * @param status of type String
	 * @throws RemoteException on error during remote method call
	 */
	public void setStatus(String status) throws RemoteException;
}