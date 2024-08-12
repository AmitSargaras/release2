/**
 * 
 */
package com.integrosys.cms.app.custexposure.bus.group;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * @author Siew Kheat
 *
 */
public interface IGroupExpBankEntity extends Serializable {

	public String getBankEntity();
	public void setBankEntity(String bankEntity);
	
	public Amount getGP5Limit();
	public void setGP5Limit(Amount gP5Amount);
	
	public Date getLimitReviewDate();
	public void setLimitReviewDate(Date limitReviewDate);
	
	public Amount getInternalLimit();
	public void setInternalLimit(Amount internalLimitAmount);
	
	public Amount getTotalExposure();
	public void setTotalExposure(Amount totalExposure);
	
	public Amount getBookedLimit();
	public void setBookedLimit(Amount bookedLimit);
	
	public Amount getAvailableLimit();
	public void setAvailableLimit(Amount availableLimit);
	
	public double getPercentageLimitUsed();
	public void setPercentageLimitUsed(double percentageLimitUsed);
	
	public Amount getContingentLiabilities();
	public void setContingentLiabilities(Amount contingentLiabilities);
	
	
}
