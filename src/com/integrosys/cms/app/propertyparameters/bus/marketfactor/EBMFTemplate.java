/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.bus.marketfactor;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.propertyparameters.bus.PropertyParametersException;

/**
 * Remote interface to EBMFTemplateBean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBMFTemplate extends EJBObject {
	/**
	 * Get the MF Template ID
	 * 
	 * @return long
	 */
	public long getMFTemplateID() throws RemoteException;

	/**
	 * Get the version time
	 * 
	 * @return long
	 */
	public long getVersionTime() throws RemoteException;

	/**
	 * Method to create child dependants via CMR
	 * 
	 * @param value is of type IMFTemplate
	 * @param verTime is the long value of the version time to be compared
	 *        against.
	 * @param mFTemplateID MF Template ID
	 * @throws ConcurrentUpdateException, PropertyParametersException,
	 *         RemoteException on error
	 * 
	 */
	public void createDependants(IMFTemplate value, long verTime, long mFTemplateID) throws ConcurrentUpdateException,
			PropertyParametersException, RemoteException;

	/**
	 * Get the MF Template business object.
	 * 
	 * @return MF Template object
	 * @throws RemoteException on error during remote method call
	 */
	public IMFTemplate getValue() throws RemoteException;

	/**
	 * Set the MF Template to this entity.
	 * 
	 * @param mFTemplate is of type IMFTemplate
	 * @throws PropertyParametersException if there is any error during updating
	 *         of MF Template
	 * @throws VersionMismatchException if the MF Template is invalid
	 * @throws RemoteException on error during remote method call
	 */
	public void setValue(IMFTemplate mFTemplate) throws PropertyParametersException, VersionMismatchException,
			RemoteException;

	/**
	 * Set the status to deleted for MF Template.
	 * 
	 * @param mFTemplate of type IMFTemplate
	 * @throws PropertyParametersException if there is any error during deleting
	 *         of MF Template
	 * @throws VersionMismatchException if the MF Template is invalid
	 * @throws RemoteException on error during remote method call
	 */
	public void delete(IMFTemplate mFTemplate) throws PropertyParametersException, VersionMismatchException,
			RemoteException;

}