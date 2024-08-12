/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/EBCustodianDocLocal.java,v 1.1 2005/02/22 10:19:24 wltan Exp $
 */
package com.integrosys.cms.app.custodian.bus;

//javax
import java.rmi.RemoteException;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Local interface for the custodian doc entity bean
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/02/22 10:19:24 $ Tag: $Name: $
 */
public interface EBCustodianDocLocal extends EJBLocalObject {
	/**
	 * Retrieve an instance of a custodian document
	 * @return ICustodianDoc - the object encapsulating the custodian doc info
	 * @throws CustodianException
	 * @throws RemoteException
	 */
	public ICustodianDoc getValue() throws CustodianException;

	/**
	 * Set the custodian doc object
	 * @param anICustodianDoc - ICustodianDoc
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 * @throws RemoteException
	 */
	public void setValue(ICustodianDoc anICustodianDoc) throws ConcurrentUpdateException;
}