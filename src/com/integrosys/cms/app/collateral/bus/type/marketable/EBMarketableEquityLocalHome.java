/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/marketable/EBMarketableEquityLocalHome.java,v 1.5 2003/08/20 06:50:10 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.marketable;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Entity bean local home interface for marketable equity.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/08/20 06:50:10 $ Tag: $Name: $
 */
public interface EBMarketableEquityLocalHome extends EJBLocalHome {
	/**
	 * Create a new marketable equity.
	 * 
	 * @param equity of type IMarketableEquity
	 * @return local marketable equity ejb object
	 * @throws CreateException on error creating the ejb object
	 */
	public EBMarketableEquityLocal create(IMarketableEquity equity) throws CreateException;

	/**
	 * Find the marketable equity entity bean by its primary key.
	 * 
	 * @param pk marketable equity id
	 * @return local marketable equity ejb object
	 * @throws FinderException on error finding the equity
	 */
	public EBMarketableEquityLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Find the marketable equity by its reference id.
	 * 
	 * @param refID reference id for staging and actual data
	 * @return local marketable equity ejb object
	 * @throws FinderException on error finding the marketale equity
	 */
	public EBMarketableEquityLocal findByRefID(long refID) throws FinderException;
}