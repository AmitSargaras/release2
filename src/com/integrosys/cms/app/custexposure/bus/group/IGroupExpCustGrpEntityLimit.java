/**
 * 
 */
package com.integrosys.cms.app.custexposure.bus.group;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.creditriskparam.bus.entitylimit.IEntityLimit;

/**
 * Interface for Group exposure entity limit
 * @author skchai
 *
 */
public interface IGroupExpCustGrpEntityLimit extends Serializable {

	/**
	 * Setting Entity Limit
	 * @param entityLimit
	 */
	public void setEntityLimit(IEntityLimit entityLimit);
	
	/**
	 * Set the aggregated outstanding amount
	 * @param amount
	 */
	public void setAggregatedOutstanding(Amount amount);
	
	/**
	 * Get the aggregated outstanding amount
	 * @return
	 */
	public Amount getAggregatedOutstanding();
	
	/**
	 * Get customer name
	 * @return
	 */
	public String getCustomerName();
	
	/**
	 * Get customer le reference id
	 * @return
	 */
	public String getLEReference();
	
	/**
	 * Get customer originated source id
	 * @return
	 */
	public String getCustIDSource();
	
	/**
	 * Get Limit Amount by the entity limit
	 * @return
	 */
	public Amount getLimitAmount();
	
	/**
	 * Get last review date for the limit set.
	 * @return
	 */
	public Date getLimitLastReviewDate();
}
