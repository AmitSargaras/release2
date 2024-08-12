/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limit.bus;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * This is the local remote interface to the EBThresholdRating entity bean
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBThresholdRatingLocal extends EJBLocalObject {

	/**
	 * Get an object representation from persistance
	 * 
	 * @return IThresholdRating
	 */
	public IThresholdRating getValue();

	/**
	 * Set an object representation into persistance
	 * 
	 * @param value is of type IThresholdRating
	 * @throws LimitException on errors
	 */
	public void setValue(IThresholdRating value) throws LimitException;

	/**
	 * Set the status to deleted for threshold rating.
	 * 
	 * @param value of type IThresholdRating
	 * @throws ConcurrentUpdateException if another threshold rating is updating
	 *         at the same time
	 */
	public void setStatusDeleted(IThresholdRating value) throws ConcurrentUpdateException;

}