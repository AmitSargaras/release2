/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limit.bus;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBThresholdRating Entity Bean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBThresholdRatingLocalHome extends EJBLocalHome {

	/**
	 * Create a local Threshold Rating Entity Bean
	 * 
	 * @param agreementID is the Long value of the agreement ID
	 * @param value is the IThresholdRating object
	 * @return EBThresholdRatingLocal
	 * @throws CreateException on error
	 */
	public EBThresholdRatingLocal create(long agreementID, IThresholdRating value) throws CreateException;

	/**
	 * Find by Primary Key.
	 * 
	 * @param pk is Long value of the primary key
	 * @return EBThresholdRatingLocal
	 * @throws FinderException on error
	 */
	public EBThresholdRatingLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Find by agreement ID
	 * 
	 * @param agreementID is the Long value of the agreement ID
	 * @param status the status to be excluded in this find
	 * @return Collection of EBThresholdRatingLocal
	 * @throws FinderException on error
	 */
	public Collection findByAgreement(Long agreementID, String status) throws FinderException;
}