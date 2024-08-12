/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/bus/EBCCTaskLocal.java,v 1.1 2003/08/31 13:56:24 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.bus;

//javax
import javax.ejb.EJBLocalObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Local interface for the CC collaboration task entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/31 13:56:24 $ Tag: $Name: $
 */
public interface EBCCTaskLocal extends EJBLocalObject {
	/**
	 * Retrieve an instance of a CC task
	 * @return ICCTask - the object encapsulating the cc certificate info
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTask getValue() throws CollaborationTaskException;

	/**
	 * Set the cc certificate object
	 * @param anICCCertificate - ICCCertificate
	 * @throws CollaborationTaskException on errors
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(ICCTask anICCTask) throws CollaborationTaskException, ConcurrentUpdateException;
}