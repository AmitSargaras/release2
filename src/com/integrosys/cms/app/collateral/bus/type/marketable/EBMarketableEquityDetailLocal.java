/**
 * 
 */
package com.integrosys.cms.app.collateral.bus.type.marketable;

import javax.ejb.EJBLocalObject;

/**
 * Entity bean local interface for marketable equity detail
 * 
 * @author $Author: Siew Kheat$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBMarketableEquityDetailLocal extends EJBLocalObject {

	/**
	 * Get the marketable equity detail business object.
	 * 
	 * @return marketable equity detail
	 */
	public void setValue(IMarketableEquityDetail equityDetail);

	/**
	 * Get the marketable equity detail business object.
	 * 
	 * @return marketable equity
	 */
	public IMarketableEquityDetail getValue();

}
