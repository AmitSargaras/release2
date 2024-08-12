/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/EBCustodianDoc.java,v 1.8 2005/02/22 10:19:24 wltan Exp $
 */
package com.integrosys.cms.app.custodian.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Rempte interface for the custodian doc entity bean
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2005/02/22 10:19:24 $ Tag: $Name: $
 */
public interface EBCustodianDoc extends EJBObject {
	/**
	 * Retrieve an instance of a custodian document
	 * @return ICustodianDoc - the object encapsulating the custodian doc info
	 * @throws CustodianException
	 * @throws RemoteException
	 */
	public ICustodianDoc getValue() throws CustodianException, RemoteException;

	/**
	 * Set the custodian doc object
	 * @param anICustodianDoc - ICustodianDoc
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 * @throws RemoteException
	 */
	public void setValue(ICustodianDoc anICustodianDoc) throws ConcurrentUpdateException, RemoteException;
}