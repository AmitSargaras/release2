/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBPledgor.java,v 1.1 2003/09/01 10:31:07 hltan Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * Remote interface to EBPledgorBean.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/01 10:31:07 $ Tag: $Name: $
 */
public interface EBPledgor extends EJBObject {
	/**
	 * Get the pledgor information.
	 * 
	 * @return a pledgor
	 */
	public IPledgor getValue() throws RemoteException;

	/**
	 * Set the pledgor to this entity.
	 * 
	 * @param pledgor is of type IPledgor
	 */
	public void setValue(IPledgor pledgor) throws RemoteException;
}