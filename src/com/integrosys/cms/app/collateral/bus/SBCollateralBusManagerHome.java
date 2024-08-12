/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/SBCollateralBusManagerHome.java,v 1.1 2003/06/11 10:18:36 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Home interface to SBCollateralManagerBean session bean.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/11 10:18:36 $ Tag: $Name: $
 */
public interface SBCollateralBusManagerHome extends EJBHome {
	/**
	 * Creates collateral manager ejb object.
	 * 
	 * @return colateral manager session bean
	 * @throws RemoteException on errors during remote method call
	 */
	public SBCollateralBusManager create() throws CreateException, RemoteException;
}