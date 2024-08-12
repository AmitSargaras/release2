/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBLimitLocal.java,v 1.5 2003/07/08 10:14:32 kllee Exp $
 */
package com.integrosys.cms.app.limit.bus;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * This is the remote interface to the EBLimitLocal entity bean
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.5 $
 * @since $Date: 2003/07/08 10:14:32 $ Tag: $Name: $
 */
public interface EBLimitLocal extends EJBLocalObject {
	/**
	 * Get the version time
	 * 
	 * @return long
	 */
	public long getVersionTime();

	/**
	 * Get an object representation from persistance
	 * 
	 * @return ILimit
	 * @throws LimitException on error
	 */
	public ILimit getValue() throws LimitException;

	/**
	 * Set an object representation into persistance
	 * 
	 * @param value is of type ILimit
	 * @throws ConcurrentUpdateException if version mismatch occurs
	 * @throws LimitException on error
	 */
	public ILimit setValue(ILimit value) throws LimitException, ConcurrentUpdateException;

	/**
	 * Method to create child dependants via CMR
	 * 
	 * @param value is of type ILimit
	 * @param verTime is the version time to be compared against the beans'
	 *        version
	 * @throws LimitException, ConcurrentUpdateException on error
	 */
	public void createDependants(ILimit value, long verTime) throws LimitException, ConcurrentUpdateException;
}