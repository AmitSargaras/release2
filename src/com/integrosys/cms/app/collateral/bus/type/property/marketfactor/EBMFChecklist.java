/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.bus.type.property.marketfactor;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.collateral.bus.CollateralException;

/**
 * Remote interface to EBMFChecklistBean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBMFChecklist extends EJBObject {
	/**
	 * Get the MF Checklist ID
	 * 
	 * @return long
	 */
	public long getMFChecklistID() throws RemoteException;

	/**
	 * Get the version time
	 * 
	 * @return long
	 */
	public long getVersionTime() throws RemoteException;

	/**
	 * Method to create child dependants via CMR
	 * 
	 * @param value is of type IMFChecklist
	 * @param verTime is the long value of the version time to be compared
	 *        against.
	 * @param mFChecklistID MF Checklist ID
	 * @throws ConcurrentUpdateException, CollateralException, RemoteException
	 *         on error
	 * 
	 */
	public void createDependants(IMFChecklist value, long verTime, long mFChecklistID)
			throws ConcurrentUpdateException, CollateralException, RemoteException;

	/**
	 * Get the MF Checklist business object.
	 * 
	 * @return MF Checklist object
	 * @throws RemoteException on error during remote method call
	 */
	public IMFChecklist getValue() throws RemoteException;

	/**
	 * Set the MF Checklist to this entity.
	 * 
	 * @param mFChecklist is of type IMFChecklist
	 * @throws CollateralException if there is any error during updating of MF
	 *         Checklist
	 * @throws VersionMismatchException if the MF Checklist is invalid
	 * @throws RemoteException on error during remote method call
	 */
	public void setValue(IMFChecklist mFChecklist) throws CollateralException, VersionMismatchException,
			RemoteException;

}