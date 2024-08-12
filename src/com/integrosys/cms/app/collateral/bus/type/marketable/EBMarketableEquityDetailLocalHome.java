/**
 * 
 */
package com.integrosys.cms.app.collateral.bus.type.marketable;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Entity bean local home interface for marketable equity detail
 * 
 * @author $Author: Siew Kheat$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBMarketableEquityDetailLocalHome extends EJBLocalHome {

	/**
	 * Create a new marketable equity detail.
	 * 
	 * @param equity of type IMarketableEquity
	 * @return local marketable equity ejb object
	 * @throws CreateException on error creating the ejb object
	 */
	public EBMarketableEquityDetailLocal create(IMarketableEquityDetail equity) throws CreateException;

	/**
	 * Find the marketable equity entity detail bean by its primary key.
	 * 
	 * @param pk marketable equity detail id
	 * @return local marketable equity detail ejb object
	 * @throws FinderException on error finding the equity detail
	 */
	public EBMarketableEquityDetailLocal findByPrimaryKey(Long pk) throws FinderException;
}
