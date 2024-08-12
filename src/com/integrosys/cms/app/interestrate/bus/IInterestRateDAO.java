/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.bus;

import java.util.Date;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Interface for interest rate DAO.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IInterestRateDAO {
	/**
	 * Get all interest rate avaiable in CMS based on the criteria.
	 * @param intRateType the type of interest rate
	 * @param monthYear the date for the interest rate
	 * 
	 * @return a list of interest rate
	 * @throws SearchDAOException on error searching the interest rate
	 */
	public IInterestRate[] getInterestRateByMonth(String intRateType, Date monthYear) throws SearchDAOException;

}
