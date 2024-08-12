/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBCoBorrowerLimitLocal.java,v 1.6 2006/08/01 12:50:25 czhou Exp $
 */
package com.integrosys.cms.app.limit.bus;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * This is the remote interface to the EBCoBorrowerLimitLocal entity bean
 * 
 * @author $Author: czhou $
 * @version $Revision: 1.6 $
 * @since $Date: 2006/08/01 12:50:25 $ Tag: $Name: $
 */
public interface EBCoBorrowerLimitLocal extends EJBLocalObject {
	/**
	 * Get the version time
	 * 
	 * @return long
	 */
	public long getVersionTime();

	/**
	 * Get the limit ID
	 * 
	 * @return long
	 */
	public long getLimitID();

	/**
	 * Get an object representation from persistance
	 * 
	 * @return ICoBorrowerLimit
	 */
	public ICoBorrowerLimit getValue() throws LimitException;

	/**
	 * Set an object representation into persistance
	 * 
	 * @param value is of type ICoBorrowerLimit
	 * @throws ConcurrentUpdateException if version mismatch occurs
	 */
	public void setValue(ICoBorrowerLimit value) throws ConcurrentUpdateException;
}