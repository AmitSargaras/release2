/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.bus;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Local home interface for EBInterestRateBean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBInterestRateLocalHome extends EJBLocalHome {
	/**
	 * Called by the client to create an local EJB object.
	 * 
	 * @param intRate of type IInterestRate
	 * @return local interest rate ejb object
	 * @throws CreateException on error while creating the ejb
	 */
	public EBInterestRateLocal create(IInterestRate intRate) throws CreateException;

	/**
	 * Find the local ejb object by primary key, the interest rate ID.
	 * 
	 * @param intRateID interest rate ID
	 * @return local interest rate ejb object
	 * @throws FinderException on error while finding the ejb
	 */
	public EBInterestRateLocal findByPrimaryKey(Long intRateID) throws FinderException;

	/**
	 * Find all interest rate.
	 * 
	 * @return a collection of <code>EBInterestRate</code>s
	 * @throws FinderException on error finding interest rate
	 */
	public Collection findAll() throws FinderException;

	/**
	 * Find interest rate by its group id.
	 * 
	 * @param groupID group id
	 * @return a collection of <code>EBInterestRate</code>s
	 * @throws FinderException on error finding the interest rate
	 */
	public Collection findByGroupID(long groupID) throws FinderException;
}