/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.bus.marketfactor;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * Remote home interface for EBMFTemplate.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBMFTemplateHome extends EJBHome {
	/**
	 * Called by the client to create a MF Template ejb object.
	 * 
	 * @param mFTemplate of type IMFTemplate
	 * @return EBMFTemplate
	 * @throws CreateException on error while creating the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBMFTemplate create(IMFTemplate mFTemplate) throws CreateException, RemoteException;

	/**
	 * Find the MF Template ejb object by primary key, the MF Template ID.
	 * 
	 * @param mFTemplateIDPK MF Template ID of type Long
	 * @return EBMFTemplate
	 * @throws FinderException on error while finding the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBMFTemplate findByPrimaryKey(Long mFTemplateIDPK) throws FinderException, RemoteException;

	/**
	 * Find all the MF Template ejb object excluded the specified status.
	 * 
	 * @param status the status excluded
	 * @return Collection of EBMFTemplate
	 * @throws FinderException on error while finding the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public Collection findAll(String status) throws FinderException, RemoteException;

}