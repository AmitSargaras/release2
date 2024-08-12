/**
 * 
 */
package com.integrosys.cms.app.custexposure.bus.group;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.custgrpi.bus.IGroupSubLimit;

/**
 * @author skchai
 *
 */
public class OBGroupExpCustGrpSubLimit implements IGroupExpCustGrpSubLimit {

	private static final long serialVersionUID = 1L;
	private IGroupSubLimit groupSubLimit;
	private Amount aggregatedOutstanding;
	
	/**
	 * Default constructor
	 */
	public OBGroupExpCustGrpSubLimit() {
		
	}
	
	/**
	 * @param groupSubLimit
	 */
	public OBGroupExpCustGrpSubLimit(IGroupSubLimit groupSubLimit) {
		this.groupSubLimit = groupSubLimit;
	}
	
	/**
	 * @return the groupSubLimit
	 */
	public IGroupSubLimit getGroupSubLimit() {
		return groupSubLimit;
	}
	/**
	 * @param groupSubLimit the groupSubLimit to set
	 */
	public void setGroupSubLimit(IGroupSubLimit groupSubLimit) {
		this.groupSubLimit = groupSubLimit;
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
	 * @see com.integrosys.cms.app.custexposure.bus.IGroupExpCustGrpSubLimit#getCurrencyCD()
	 */
	public String getCurrencyCD() {
		return this.groupSubLimit.getCurrencyCD();
	}
	/* (non-Javadoc)
	 * @see com.integrosys.cms.app.custexposure.bus.IGroupExpCustGrpSubLimit#getDescription()
	 */
	public String getDescription() {
		return this.groupSubLimit.getDescription();
	}
	/* (non-Javadoc)
	 * @see com.integrosys.cms.app.custexposure.bus.IGroupExpCustGrpSubLimit#getLastReviewedDt()
	 */
	public Date getLastReviewedDt() {
		return this.groupSubLimit.getLastReviewedDt();
	}
	/* (non-Javadoc)
	 * @see com.integrosys.cms.app.custexposure.bus.IGroupExpCustGrpSubLimit#getLimitAmt()
	 */
	public Amount getLimitAmt() {
		return this.groupSubLimit.getLimitAmt();
	}
	/* (non-Javadoc)
	 * @see com.integrosys.cms.app.custexposure.bus.IGroupExpCustGrpSubLimit#getSubLimitTypeCD()
	 */
	public String getSubLimitTypeCD() {
		return this.groupSubLimit.getSubLimitTypeCD();
	}
	/**
     * Return a String representation of the object
     *
     * @return String
     */
    public String toString() {
        return AccessorUtil.printMethodValue(this);
    }
}
