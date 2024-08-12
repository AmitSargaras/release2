package com.integrosys.cms.app.custexposure.bus.group;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.custgrpi.bus.IGroupOtrLimit;

/**
 * OB Class that wrap around Customer Group Outer Limit
 * @author skchai
 *
 */
public class OBGroupExpCustGrpOtrLimit implements IGroupExpCustGrpOtrLimit {

	private IGroupOtrLimit groupOtrLimit;
	private Amount aggregatedOutstanding;

	/**
	 * Default constructor
	 */
	public OBGroupExpCustGrpOtrLimit() {
		
	}
	
	/**
	 * @param groupOtrLimit
	 */
	public OBGroupExpCustGrpOtrLimit(IGroupOtrLimit groupOtrLimit) {
		this.groupOtrLimit = groupOtrLimit;
	}
	
	/**
	 * @return the groupOtrLimit
	 */
	public IGroupOtrLimit getGroupOtrLimit() {
		return groupOtrLimit;
	}
	
	/**
	 * @param groupOtrLimit the groupOtrLimit to set
	 */
	public void setGroupOtrLimit(IGroupOtrLimit groupOtrLimit) {
		this.groupOtrLimit = groupOtrLimit;
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
	
	/* (non-Javadoc)
	 * @see com.integrosys.cms.app.custexposure.bus.IGroupExpCustGrpOtrLimit#getCurrencyCD()
	 */
	public String getCurrencyCD() {
		return this.groupOtrLimit.getCurrencyCD();
	}
	
	/* (non-Javadoc)
	 * @see com.integrosys.cms.app.custexposure.bus.IGroupExpCustGrpOtrLimit#getDescription()
	 */
	public String getDescription() {
		return this.groupOtrLimit.getDescription();
	}
	
	/* (non-Javadoc)
	 * @see com.integrosys.cms.app.custexposure.bus.IGroupExpCustGrpOtrLimit#getLastReviewedDt()
	 */
	public Date getLastReviewedDt() {
		return this.groupOtrLimit.getLastReviewedDt();
	}
	
	/* (non-Javadoc)
	 * @see com.integrosys.cms.app.custexposure.bus.IGroupExpCustGrpOtrLimit#getLimitAmt()
	 */
	public Amount getLimitAmt() {
		return this.groupOtrLimit.getLimitAmt();
	}
	
	/* (non-Javadoc)
	 * @see com.integrosys.cms.app.custexposure.bus.IGroupExpCustGrpOtrLimit#getOtrLimitTypeCD()
	 */
	public String getOtrLimitTypeCD() {
		return this.groupOtrLimit.getOtrLimitTypeCD();
	}
	
	/**
     * Return a String representation of this object.
     *
     * @return String
     */
    public String toString() {
        return AccessorUtil.printMethodValue (this);
    }

}
