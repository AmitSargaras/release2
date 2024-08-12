/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/bus/EBCCCertificateLocal.java,v 1.1 2004/01/13 06:21:45 hltan Exp $
 */
package com.integrosys.cms.app.cccertificate.bus;

//javax
import javax.ejb.EJBLocalObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Remote interface for the cc certificate entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/01/13 06:21:45 $ Tag: $Name: $
 */
public interface EBCCCertificateLocal extends EJBLocalObject {
	/**
	 * Retrieve an instance of a cc certificate
	 * @return ICCCertificate - the object encapsulating the cc certificate info
	 * @throws CCCertificateException on errors
	 */
	public ICCCertificate getValue() throws CCCertificateException;

	/**
	 * Set the ccc object
	 * @param anICCCertificate - ICCCertificate
	 * @throws CCCertificateException on errors
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(ICCCertificate anICCCertificate) throws CCCertificateException, ConcurrentUpdateException;

	/**
	 * Create the child items that are under this ccc
	 * @param anICCCertificate - ICCCertificate
	 * @throws CCCertificateException on errors
	 */
	public void createCCCertificateItems(ICCCertificate anICCCertificate) throws CCCertificateException;
}