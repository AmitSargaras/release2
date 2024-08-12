/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/bus/EBCollateralTask.java,v 1.1 2003/08/14 13:25:11 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Remote interface for the collateral collaboration task entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/14 13:25:11 $ Tag: $Name: $
 */
public interface EBCollateralTask extends EJBObject {
	/**
	 * Retrieve an instance of a collateral task
	 * @return ICollateralTask - the object encapsulating the cc certificate
	 *         info
	 * @throws CollaborationTaskException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICollateralTask getValue() throws CollaborationTaskException, RemoteException;

	/**
	 * Set the cc certificate object
	 * @param anICCCertificate - ICCCertificate
	 * @throws CollaborationTaskException on errors
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 * @throws RemoteException
	 */
	public void setValue(ICollateralTask anICollateralTask) throws CollaborationTaskException,
			ConcurrentUpdateException, RemoteException;
}