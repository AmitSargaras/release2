/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBTemplateLocalHome.java,v 1.2 2003/07/02 01:18:22 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//java
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Home interface for the template entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/02 01:18:22 $ Tag: $Name: $
 */

public interface EBTemplateLocalHome extends EJBLocalHome {
	/**
	 * Create a template
	 * @param anITemplate - ITemplate
	 * @return EBTemplate - the remote handler for the created template
	 * @throws CreateException if creation fails
	 * @throws RemoteException
	 */
	public EBTemplateLocal create(ITemplate anITemplate) throws CreateException;

	/**
	 * Find by primary Key, the template ID
	 * @param aPK - Long
	 * @return EBTemplate - the remote handler for the template that has the PK
	 *         as specified
	 * @throws FinderException
	 * @throws RemoteException
	 */
	public EBTemplateLocal findByPrimaryKey(Long aPK) throws FinderException;
}