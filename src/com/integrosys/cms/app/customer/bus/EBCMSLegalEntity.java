/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBCMSLegalEntity.java,v 1.2 2003/07/04 02:18:44 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * This is the remote interface to the EBCMSLegalEntity entity bean
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/04 02:18:44 $ Tag: $Name: $
 */
public interface EBCMSLegalEntity extends EJBObject {


	/**
	 * Get an object representation from persistance
	 * 
	 * @return ICMSLegalEntity
	 * @throws CustomerException on error
	 * @throws RemoteException
	 */
	public ICMSLegalEntity getValue() throws CustomerException, RemoteException;

	/**
	 * Set an object representation into persistance
	 * 
	 * @param value is of type ICMSLegalEntity
	 * @throws ConcurrentUpdateException if version mismatch occurs
	 * @throws CustomerException on error
	 * @throws RemoteException
	 */
	public void setValue(ICMSLegalEntity value) throws ConcurrentUpdateException, CustomerException, RemoteException;

	/**
	 * Method to create child dependants via CMR
	 * 
	 * @param value is of type ICMSLegalEntity
	 * @param verTime is the version time to be compared against the beans'
	 *        version
	 * @throws CustomerException, ConcurrentUpdateException, RemoteException on
	 *         error
	 */
	public void createDependants(ICMSLegalEntity value, long verTime) throws CustomerException,
			ConcurrentUpdateException, RemoteException;
}