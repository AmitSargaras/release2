package com.integrosys.cms.app.limit.bus;

import com.integrosys.base.businfra.currency.Amount;

/**
 * @author siew kheat
 * 
 */
public class OBFacilityMultiTierFinancing implements IFacilityMultiTierFinancing {

	private static final long serialVersionUID = -7340986875085139961L;

	private long id;

	private long facilityMasterId;

	private Long cmsRefId;

	private Short tierSeqNo;

	private Short tierTerm;

	private Character tierTermCode;

	private Double rate;

	private String status;

	private Long rateNumber;

	private Double rateVariance;

	private String varianceCode;

	private String gracePeriod;

	private Amount paymentAmount;
	
	private String currencyCode;

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Long getRateNumber() {
		return rateNumber;
	}

	public void setRateNumber(Long rateNumber) {
		this.rateNumber = rateNumber;
	}

	public Double getRateVariance() {
		return rateVariance;
	}

	public void setRateVariance(Double rateVariance) {
		this.rateVariance = rateVariance;
	}

	public String getVarianceCode() {
		return varianceCode;
	}

	public void setVarianceCode(String varianceCode) {
		this.varianceCode = varianceCode;
	}

	public String getGracePeriod() {
		return gracePeriod;
	}

	public void setGracePeriod(String gracePeriod) {
		this.gracePeriod = gracePeriod;
	}

	public Amount getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Amount paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the facilityMasterId
	 */
	public long getFacilityMasterId() {
		return facilityMasterId;
	}

	/**
	 * @param facilityMasterId the facilityMasterId to set
	 */
	public void setFacilityMasterId(long facilityMasterId) {
		this.facilityMasterId = facilityMasterId;
	}

	/**
	 * @return the cmsRefId
	 */
	public Long getCmsRefId() {
		return cmsRefId;
	}

	/**
	 * @param cmsRefId the cmsRefId to set
	 */
	public void setCmsRefId(Long cmsRefId) {
		this.cmsRefId = cmsRefId;
	}

	/**
	 * @return the tierSeqNo
	 */
	public Short getTierSeqNo() {
		return tierSeqNo;
	}

	/**
	 * @param tierSeqNo the tierSeqNo to set
	 */
	public void setTierSeqNo(Short tierSeqNo) {
		this.tierSeqNo = tierSeqNo;
	}

	/**
	 * @return the tierTerm
	 */
	public Short getTierTerm() {
		return tierTerm;
	}

	/**
	 * @param tierTerm the tierTerm to set
	 */
	public void setTierTerm(Short tierTerm) {
		this.tierTerm = tierTerm;
	}

	/**
	 * @return the tierTermCode
	 */
	public Character getTierTermCode() {
		return tierTermCode;
	}

	/**
	 * @param tierTermCode the tierTermCode to set
	 */
	public void setTierTermCode(Character tierTermCode) {
		this.tierTermCode = tierTermCode;
	}

	/**
	 * @return the rate
	 */
	public Double getRate() {
		return rate;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(Double rate) {
		this.rate = rate;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tierSeqNo == null) ? 0 : tierSeqNo.hashCode());
		result = prime * result + ((tierTerm == null) ? 0 : tierTerm.hashCode());
		result = prime * result + ((tierTermCode == null) ? 0 : tierTermCode.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		OBFacilityMultiTierFinancing other = (OBFacilityMultiTierFinancing) obj;
		if (rate == null) {
			if (other.rate != null) {
				return false;
			}
		}
		else if (!rate.equals(other.rate)) {
			return false;
		}
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		}
		else if (!status.equals(other.status)) {
			return false;
		}
		if (tierSeqNo == null) {
			if (other.tierSeqNo != null) {
				return false;
			}
		}
		else if (!tierSeqNo.equals(other.tierSeqNo)) {
			return false;
		}
		if (tierTerm == null) {
			if (other.tierTerm != null) {
				return false;
			}
		}
		else if (!tierTerm.equals(other.tierTerm)) {
			return false;
		}
		if (tierTermCode == null) {
			if (other.tierTermCode != null) {
				return false;
			}
		}
		else if (!tierTermCode.equals(other.tierTermCode)) {
			return false;
		}
		return true;
	}

}
