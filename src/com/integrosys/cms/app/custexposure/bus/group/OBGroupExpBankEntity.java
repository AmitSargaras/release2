/**
 * 
 */
package com.integrosys.cms.app.custexposure.bus.group;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author user
 *
 */
public class OBGroupExpBankEntity implements IGroupExpBankEntity {

	private static final long serialVersionUID = 1L;
	
	Amount availableLimit = null;
	String bankEntity;
	Amount bookedLimit = null;
	Amount contingentLiabilities = null;
	Amount gP5Limit = null;
	Amount internalLimit = null;
	Date limitReviewDate = null;
	double percentageLimitUsed = ICMSConstant.DOUBLE_INVALID_VALUE;
	Amount totalExposure = null;
	/**
	 * @return the availableLimit
	 */
	public Amount getAvailableLimit() {
		return availableLimit;
	}
	/**
	 * @param availableLimit the availableLimit to set
	 */
	public void setAvailableLimit(Amount availableLimit) {
		this.availableLimit = availableLimit;
	}
	/**
	 * @return the bankEntity
	 */
	public String getBankEntity() {
		return bankEntity;
	}
	/**
	 * @param bankEntity the bankEntity to set
	 */
	public void setBankEntity(String bankEntity) {
		this.bankEntity = bankEntity;
	}
	/**
	 * @return the bookedLimit
	 */
	public Amount getBookedLimit() {
		return bookedLimit;
	}
	/**
	 * @param bookedLimit the bookedLimit to set
	 */
	public void setBookedLimit(Amount bookedLimit) {
		this.bookedLimit = bookedLimit;
	}
	/**
	 * @return the contingentLiabilities
	 */
	public Amount getContingentLiabilities() {
		return contingentLiabilities;
	}
	/**
	 * @param contingentLiabilities the contingentLiabilities to set
	 */
	public void setContingentLiabilities(Amount contingentLiabilities) {
		this.contingentLiabilities = contingentLiabilities;
	}
	/**
	 * @return the gP5Limit
	 */
	public Amount getGP5Limit() {
		return gP5Limit;
	}
	/**
	 * @param limit the gP5Limit to set
	 */
	public void setGP5Limit(Amount limit) {
		gP5Limit = limit;
	}
	/**
	 * @return the internalLimit
	 */
	public Amount getInternalLimit() {
		return internalLimit;
	}
	/**
	 * @param internalLimit the internalLimit to set
	 */
	public void setInternalLimit(Amount internalLimit) {
		this.internalLimit = internalLimit;
	}
	/**
	 * @return the limitReviewDate
	 */
	public Date getLimitReviewDate() {
		return limitReviewDate;
	}
	/**
	 * @param limitReviewDate the limitReviewDate to set
	 */
	public void setLimitReviewDate(Date limitReviewDate) {
		this.limitReviewDate = limitReviewDate;
	}
	/**
	 * @return the percentageLimitUsed
	 */
	public double getPercentageLimitUsed() {
		return percentageLimitUsed;
	}
	/**
	 * @param percentageLimitUsed the percentageLimitUsed to set
	 */
	public void setPercentageLimitUsed(double percentageLimitUsed) {
		this.percentageLimitUsed = percentageLimitUsed;
	}
	/**
	 * @return the totalExposure
	 */
	public Amount getTotalExposure() {
		return totalExposure;
	}
	/**
	 * @param totalExposure the totalExposure to set
	 */
	public void setTotalExposure(Amount totalExposure) {
		this.totalExposure = totalExposure;
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
