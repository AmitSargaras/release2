/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import java.util.Collection;

import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Local home interface for EBGMRADealBean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBGMRADealLocalHome extends EJBLocalHome {

	/**
	 * Find the local ejb object by primary key, the CMS Deal ID.
	 * 
	 * @param dealIDPK CMS Deal ID
	 * @return local GMRA deal ejb object
	 * @throws FinderException on error while finding the ejb
	 */
	public EBGMRADealLocal findByPrimaryKey(Long dealIDPK) throws FinderException;

	/**
	 * Find all GMRA Deal.
	 * 
	 * @param agreementType GMRA agreement type
	 * @return a collection of <code>EBGMRADeal</code>s
	 * @throws FinderException on error finding the ejb
	 */
	public Collection findAll(String agreementType) throws FinderException;
}