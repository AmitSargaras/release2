package com.integrosys.cms.app.limit.bus;

import java.io.Serializable;

import com.integrosys.base.businfra.currency.Amount;

/**
 * @author siew kheat
 *
 */
public interface IFacilityMultiTierFinancing extends Serializable {

	/**
	 * @return the id
	 */
	public long getId();
	
	/**
	 * @param id the id to set
	 */
	public void setId(long id);
	
	/**
	 * @return the facilityMasterId
	 */
	public long getFacilityMasterId();
	/**
	 * @param facilityMasterId the facilityMasterId to set
	 */
	public void setFacilityMasterId(long facilityMasterId);
	/**
	 * @return the cmsRefId
	 */
	public Long getCmsRefId();
	/**
	 * @param cmsRefId the cmsRefId to set
	 */
	public void setCmsRefId(Long cmsRefId);
	/**
	 * @return the tierSeqNo
	 */
	public Short getTierSeqNo();
	/**
	 * @param tierSeqNo the tierSeqNo to set
	 */
	public void setTierSeqNo(Short tierSeqNo);
	/**
	 * @return the tierTerm
	 */
	public Short getTierTerm();
	/**
	 * @param tierTerm the tierTerm to set
	 */
	public void setTierTerm(Short tierTerm);
	/**
	 * @return the tierTermCode
	 */
	public Character getTierTermCode();
	/**
	 * @param tierTermCode the tierTermCode to set
	 */
	public void setTierTermCode(Character tierTermCode);
	/**
	 * @return the rate
	 */
	public Double getRate();
	/**
	 * @param rate the rate to set
	 */
	public void setRate(Double rate);
	/**
	 * @return the status
	 */
	public String getStatus();
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status);
	
	public Long getRateNumber();

	public void setRateNumber(Long rateNumber);

	public Double getRateVariance();

	public void setRateVariance(Double rateVariance);

	public String getVarianceCode();

	public void setVarianceCode(String varianceCode);

	public String getGracePeriod();

	public void setGracePeriod(String gracePeriod);

	public Amount getPaymentAmount();

	public void setPaymentAmount(Amount paymentAmount);
	
	public String getCurrencyCode();

	public void setCurrencyCode(String currencyCode);
}
