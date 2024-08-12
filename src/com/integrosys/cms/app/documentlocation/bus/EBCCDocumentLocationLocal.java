/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/bus/EBCCDocumentLocationLocal.java,v 1.1 2004/02/17 02:12:02 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.bus;

//javax
import javax.ejb.EJBLocalObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Local interface for the CC documentation location entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/02/17 02:12:02 $ Tag: $Name: $
 */
public interface EBCCDocumentLocationLocal extends EJBLocalObject {
	/**
	 * Retrieve an instance of a CC documentation location
	 * @return ICCDocumentLocation - the object encapsulating the cc
	 *         documentation location info
	 * @throws DocumentLocationException on errors
	 */
	public ICCDocumentLocation getValue() throws DocumentLocationException;

	/**
	 * Set the cc documentation location object
	 * @param anICCDocumentLocation - ICCDocumentLocation
	 * @throws DocumentLocationException on errors
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(ICCDocumentLocation anICCDocumentLocation) throws DocumentLocationException,
			ConcurrentUpdateException;
}