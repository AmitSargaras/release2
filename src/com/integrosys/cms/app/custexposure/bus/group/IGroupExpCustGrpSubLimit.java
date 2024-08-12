/**
 * 
 */
package com.integrosys.cms.app.custexposure.bus.group;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.custgrpi.bus.IGroupSubLimit;

/**
 * Interface wrapper for Group Sub Limit
 * @author skchai
 *
 */
public interface IGroupExpCustGrpSubLimit extends Serializable {

	/**
	 * Set group Sub Limit
	 * @param groupSubLimit
	 */
	public void setGroupSubLimit(IGroupSubLimit groupSubLimit);
	
	/**
	 * Set aggregated outstanding amount
	 * @param aggregatedOutstanding
	 */
	public void setAggregatedOutstanding(Amount aggregatedOutstanding);
	
	/**
	 * Get aggregated outstanding amount filtered by customer group sub limit
	 * @return
	 */
	public Amount getAggregatedOutstanding();

	/**
	 * Get sub limit type code
	 * @return
	 */
    public String getSubLimitTypeCD() ;

    /**
     * Get the limit amount for the set group sub limit
     * @return
     */
    public Amount getLimitAmt();

    /**
     * Get the used currency code
     * @return
     */
    public String getCurrencyCD() ;

    /**
     * Get last review date 
     * @return
     */
    public Date getLastReviewedDt() ;

    /**
     * Get group sub limit description
     * @return
     */
    public String getDescription();


}
