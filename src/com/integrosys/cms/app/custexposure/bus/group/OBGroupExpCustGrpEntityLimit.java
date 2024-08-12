/**
 * 
 */
package com.integrosys.cms.app.custexposure.bus.group;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.creditriskparam.bus.entitylimit.IEntityLimit;

/**
 * Entity Limit Wrapper class that contains a instance of entityLimit for the 
 * respective Entity Limit and the aggreated outstanding amount for the entity limit
 * @author skchai
 *
 */
public class OBGroupExpCustGrpEntityLimit implements IGroupExpCustGrpEntityLimit {

	private static final long serialVersionUID = 1L;
	private IEntityLimit entityLimit;
	private Amount aggregatedOutstanding;
	
	/**
	 * Default Constructor
	 */
	public OBGroupExpCustGrpEntityLimit() {
		
	}
	
	/**
	 * Constructor that take in entity limit
	 * @param entityLimit
	 */
	public OBGroupExpCustGrpEntityLimit(IEntityLimit entityLimit) {
		this.entityLimit = entityLimit;
	}
	
	/**
	 * @return the entityLimit
	 */
	public IEntityLimit getEntityLimit() {
		return entityLimit;
	}
	/**
	 * @param entityLimit the entityLimit to set
	 */
	public void setEntityLimit(IEntityLimit entityLimit) {
		this.entityLimit = entityLimit;
	}
	/**
	 * @return the aggregatedOutstanding
	 */
	public Amount getAggregatedOutstanding() {
		return aggregatedOutstanding;
	}
	/**
	 * @param aggregatedOutstanding the aggregatedOutstanding to set
	 */
	public void setAggregatedOutstanding(Amount aggregatedOutstanding) {
		this.aggregatedOutstanding = aggregatedOutstanding;
	}
	
	/**
     * Return a String representation of this object.
     *
     * @return String
     */
    public String toString() {
        return AccessorUtil.printMethodValue (this);
    }
    
	/* (non-Javadoc)
	 * @see com.integrosys.cms.app.custexposure.bus.IGroupExpEntityLimit#getCustIDSource()
	 */
	public String getCustIDSource() {
		return this.entityLimit.getCustIDSource();
	}
	
	/* (non-Javadoc)
	 * @see com.integrosys.cms.app.custexposure.bus.IGroupExpEntityLimit#getCustomerName()
	 */
	public String getCustomerName() {
		return this.entityLimit.getCustomerName();
	}
	
	/* (non-Javadoc)
	 * @see com.integrosys.cms.app.custexposure.bus.IGroupExpEntityLimit#getLEReference()
	 */
	public String getLEReference() {
		return this.entityLimit.getLEReference();
	}
	
	/* (non-Javadoc)
	 * @see com.integrosys.cms.app.custexposure.bus.IGroupExpEntityLimit#getLimitAmount()
	 */
	public Amount getLimitAmount() {
		return this.entityLimit.getLimitAmount();
	}
	
	/* (non-Javadoc)
	 * @see com.integrosys.cms.app.custexposure.bus.IGroupExpEntityLimit#getLimitLastReviewDate()
	 */
	public Date getLimitLastReviewDate() {
		return this.entityLimit.getLimitLastReviewDate();
	}	
	

}
