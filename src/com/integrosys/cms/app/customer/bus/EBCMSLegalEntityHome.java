/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBCMSLegalEntityHome.java,v 1.2 2003/07/03 07:13:01 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * This is the Home interface to the EBCMSLegalEntity Entity Bean
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/03 07:13:01 $ Tag: $Name: $
 */
public interface EBCMSLegalEntityHome extends EJBHome {
	/**
	 * Create a LegalEntity
	 * 
	 * @param value is the ICMSLegalEntity object
	 * @return EBCMSLegalEntity
	 * @throws CreateException, RemoteException
	 */
	public EBCMSLegalEntity create(ICMSLegalEntity value) throws CreateException, RemoteException;

	/**
	 * Find by primary Key, the LegalEntity ID
	 * 
	 * @param pk is the Long value of the primary key
	 * @return EBSMECreditApplication
	 * @throws FinderException on error
	 * @throws RemoteException
	 */
	public EBCMSLegalEntity findByPrimaryKey(Long pk) throws FinderException, RemoteException;
}