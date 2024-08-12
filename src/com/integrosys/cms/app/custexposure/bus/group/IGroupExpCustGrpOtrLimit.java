/**
 * 
 */
package com.integrosys.cms.app.custexposure.bus.group;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.custgrpi.bus.IGroupOtrLimit;

/**
 * Interface wrapper class for Group Other Limit
 * @author skchai
 *
 */
public interface IGroupExpCustGrpOtrLimit extends Serializable {

	/**
	 * Set Group outer limit
	 * @param groupOuterLimit
	 */
	public void setGroupOtrLimit(IGroupOtrLimit groupOuterLimit);
	
	/**
	 * Set aggregated amount
	 * @param aggregatedAmount
	 */
	public void setAggregatedOutstanding(Amount aggregatedAmount);
	
	/**
	 * get aggregated amount
	 * @return
	 */
	public Amount getAggregatedOutstanding();
	
	/**
	 * get group Outer Limit Type code
	 * @return
	 */
    public String getOtrLimitTypeCD() ;

    /**
     * Get group outer limit amount
     * @return
     */
    public Amount getLimitAmt();

    /**
     * Get Currency code
     * @return
     */
    public String getCurrencyCD() ;

    /**
     * Get last review date
     * @return
     */
    public Date getLastReviewedDt() ;

    /**
     * Get description
     * @return
     */
    public String getDescription();
}
