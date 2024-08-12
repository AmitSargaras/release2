/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import java.util.Collection;

import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Local home interface for EBDealValuationBean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBDealValuationLocalHome extends EJBLocalHome {

	/**
	 * Find the local ejb object by primary key, the deal valuation ID.
	 * 
	 * @param dealIDPK deal valuation ID
	 * @return local deal valuation ejb object
	 * @throws FinderException on error while finding the ejb
	 */
	public EBDealValuationLocal findByPrimaryKey(Long dealIDPK) throws FinderException;

	/**
	 * Find all Deal Valuation.
	 * 
	 * @return a collection of <code>EBDealValuation</code>s
	 * @throws FinderException on error finding the ejb
	 */
	public Collection findAll() throws FinderException;
}