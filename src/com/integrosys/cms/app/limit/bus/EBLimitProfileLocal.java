/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBLimitProfileLocal.java,v 1.4 2003/07/09 11:40:29 kllee Exp $
 */
package com.integrosys.cms.app.limit.bus;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * This is the local interface to the EBLimitProfile entity bean.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.4 $
 * @since $Date: 2003/07/09 11:40:29 $ Tag: $Name: $
 */
public interface EBLimitProfileLocal extends EJBLocalObject {
	/**
	 * Return an object representation of the limit profile information.
	 * 
	 * @param loadDependants is a boolean value indicating of child dependants
	 *        should be loaded
	 * @return ILimitProfile
	 * @throws LimitException;
	 */
	public ILimitProfile getValue(boolean loadDependants) throws LimitException;

	/**
	 * Persist a limit profile information
	 * 
	 * @param value is of type ILimitProfile
	 * @throws ConcurrentUpdateException if version mismatch occurs
	 * @throws LimitException on errors
	 */
	public void setValue(ILimitProfile value) throws LimitException, ConcurrentUpdateException;
}