/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/bus/EBCCDocumentLocationLocalHome.java,v 1.1 2004/02/17 02:12:02 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.bus;

//javax
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Local Home interface for the CC documentation location entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/02/17 02:12:02 $ Tag: $Name: $
 */

public interface EBCCDocumentLocationLocalHome extends EJBLocalHome {
	/**
	 * Create a CC documentation location
	 * @param anICCDocumentLocation of ICCDocumentLocation
	 * @return EBCCDocumentLocationLocal - the local handler for the created CC
	 *         documentation location
	 * @throws CreateException if creation fails
	 */
	public EBCCDocumentLocationLocal create(ICCDocumentLocation anICCDocumentLocation) throws CreateException;

	/**
	 * Find by primary Key, the CC Documentation Location ID
	 * @param aPK - Long
	 * @return EBCCDocumentLocationLocal - the local handler for the CC
	 *         documentation location that has the PK as specified
	 * @throws FinderException
	 */
	public EBCCDocumentLocationLocal findByPrimaryKey(Long aPK) throws FinderException;

}