/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBCMSLegalEntityLocal.java,v 1.3 2003/07/03 07:51:47 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * This is the local interface to the EBCMSLegalEntity entity bean.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/07/03 07:51:47 $ Tag: $Name: $
 */
public interface EBCMSLegalEntityLocal extends EJBLocalObject {
	/**
	 * Get the version time
	 * 
	 * @return long
	 */
	public long getVersionTime();

	/**
	 * Return an object representation of the Legal Entity information.
	 * 
	 * @return ICMSLegalEntity
	 * @throws CustomerException on error
	 */
	public ICMSLegalEntity getValue() throws CustomerException;

	/**
	 * Persist a Legal Entity information
	 * 
	 * @param value is of type ICMSLegalEntity
	 * @throws ConcurrentUpdateException if version mismatch occurs
	 * @throws CustomerException on error
	 */
	public void setValue(ICMSLegalEntity value) throws CustomerException, ConcurrentUpdateException;

	/**
	 * Method to create child dependants via CMR
	 * 
	 * @param value is of type ICMSLegalEntity
	 * @param verTime is the long value of the version time to be compared
	 *        against.
	 * @throws CustomerException, ConcurrentUpdateException on error
	 */
	public void createDependants(ICMSLegalEntity value, long verTime) throws CustomerException,
			ConcurrentUpdateException;
}