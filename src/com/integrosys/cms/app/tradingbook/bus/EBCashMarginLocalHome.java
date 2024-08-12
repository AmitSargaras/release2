/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Local home interface for EBCashMarginBean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBCashMarginLocalHome extends EJBLocalHome {

	/**
	 * Find the local ejb object by primary key, the cash margin ID.
	 * 
	 * @param cashMarginIDPK cash margin ID
	 * @return local cash margin ejb object
	 * @throws FinderException on error while finding the ejb
	 */
	public EBCashMarginLocal findByPrimaryKey(Long cashMarginIDPK) throws FinderException;

}