package com.integrosys.cms.app.limit.bus;

import com.integrosys.base.businfra.currency.Amount;

/**
 * @author Chong Jun Yong
 * 
 */
public class OBFacilityFeeCharge implements IFacilityFeeCharge {
	private long facilityMasterId;

	private String currencyCode;

	private Integer lateChargeType;

	private Amount commissionFeesAmount;

	private Amount handlingFeesAmount;

	private Amount subsidyAmount;

	private Amount othersFeeAmount;

	private Double commissionRate;

	private String commissionBasisCategoryCode;

	private String commissionBasisEntryCode;

	private Amount maximumCommissionAmount;

	private Amount minimumCommissionAmount;

	private Double facilityCommitmentRate;

	private String facilityCommitmentRateNumberCategoryCode;

	private String facilityCommitmentRateNumberEntryCode;

	/**
	 * @return the commissionBasisCategoryCode
	 */
	public String getCommissionBasisCategoryCode() {
		return commissionBasisCategoryCode;
	}

	/**
	 * @return the commissionBasisEntryCode
	 */
	public String getCommissionBasisEntryCode() {
		return commissionBasisEntryCode;
	}

	/**
	 * @return the commissionFeesAmount
	 */
	public Amount getCommissionFeesAmount() {
		return commissionFeesAmount;
	}

	/**
	 * @return the commissionRate
	 */
	public Double getCommissionRate() {
		return commissionRate;
	}

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @return the facilityCommitmentRate
	 */
	public Double getFacilityCommitmentRate() {
		return facilityCommitmentRate;
	}

	/**
	 * @return the facilityCommitmentRateNumberCategoryCode
	 */
	public String getFacilityCommitmentRateNumberCategoryCode() {
		return facilityCommitmentRateNumberCategoryCode;
	}

	/**
	 * @return the facilityCommitmentRateNumberEntryCode
	 */
	public String getFacilityCommitmentRateNumberEntryCode() {
		return facilityCommitmentRateNumberEntryCode;
	}

	/**
	 * @return the facilityMasterId
	 */
	public long getFacilityMasterId() {
		return facilityMasterId;
	}

	/**
	 * @return the handlingFeesAmount
	 */
	public Amount getHandlingFeesAmount() {
		return handlingFeesAmount;
	}

	/**
	 * @return the lateChargeType
	 */
	public Integer getLateChargeType() {
		return lateChargeType;
	}

	/**
	 * @return the maximumCommissionAmount
	 */
	public Amount getMaximumCommissionAmount() {
		return maximumCommissionAmount;
	}

	/**
	 * @return the minimumCommissionAmount
	 */
	public Amount getMinimumCommissionAmount() {
		return minimumCommissionAmount;
	}

	/**
	 * @return the othersFeeAmount
	 */
	public Amount getOthersFeeAmount() {
		return othersFeeAmount;
	}

	/**
	 * @return the subsidyAmount
	 */
	public Amount getSubsidyAmount() {
		return subsidyAmount;
	}

	/**
	 * @param commissionBasisCategoryCode the commissionBasisCategoryCode to set
	 */
	public void setCommissionBasisCategoryCode(String commissionBasisCategoryCode) {
		this.commissionBasisCategoryCode = commissionBasisCategoryCode;
	}

	/**
	 * @param commissionBasisEntryCode the commissionBasisEntryCode to set
	 */
	public void setCommissionBasisEntryCode(String commissionBasisEntryCode) {
		this.commissionBasisEntryCode = commissionBasisEntryCode;
	}

	/**
	 * @param commissionFeesAmount the commissionFeesAmount to set
	 */
	public void setCommissionFeesAmount(Amount commissionFeesAmount) {
		this.commissionFeesAmount = commissionFeesAmount;
	}

	/**
	 * @param commissionRate the commissionRate to set
	 */
	public void setCommissionRate(Double commissionRate) {
		this.commissionRate = commissionRate;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @param facilityCommitmentRate the facilityCommitmentRate to set
	 */
	public void setFacilityCommitmentRate(Double facilityCommitmentRate) {
		this.facilityCommitmentRate = facilityCommitmentRate;
	}

	/**
	 * @param facilityCommitmentRateNumberCategoryCode the
	 *        facilityCommitmentRateNumberCategoryCode to set
	 */
	public void setFacilityCommitmentRateNumberCategoryCode(String facilityCommitmentRateNumberCategoryCode) {
		this.facilityCommitmentRateNumberCategoryCode = facilityCommitmentRateNumberCategoryCode;
	}

	/**
	 * @param facilityCommitmentRateNumberEntryCode the
	 *        facilityCommitmentRateNumberEntryCode to set
	 */
	public void setFacilityCommitmentRateNumberEntryCode(String facilityCommitmentRateNumberEntryCode) {
		this.facilityCommitmentRateNumberEntryCode = facilityCommitmentRateNumberEntryCode;
	}

	/**
	 * @param facilityMasterId the facilityMasterId to set
	 */
	public void setFacilityMasterId(long facilityMasterId) {
		this.facilityMasterId = facilityMasterId;
	}

	/**
	 * @param handlingFeesAmount the handlingFeesAmount to set
	 */
	public void setHandlingFeesAmount(Amount handlingFeesAmount) {
		this.handlingFeesAmount = handlingFeesAmount;
	}

	/**
	 * @param lateChargeType the lateChargeType to set
	 */
	public void setLateChargeType(Integer lateChargeType) {
		this.lateChargeType = lateChargeType;
	}

	/**
	 * @param maximumCommissionAmount the maximumCommissionAmount to set
	 */
	public void setMaximumCommissionAmount(Amount maximumCommissionAmount) {
		this.maximumCommissionAmount = maximumCommissionAmount;
	}

	/**
	 * @param minimumCommissionAmount the minimumCommissionAmount to set
	 */
	public void setMinimumCommissionAmount(Amount minimumCommissionAmount) {
		this.minimumCommissionAmount = minimumCommissionAmount;
	}

	/**
	 * @param othersFeeAmount the othersFeeAmount to set
	 */
	public void setOthersFeeAmount(Amount othersFeeAmount) {
		this.othersFeeAmount = othersFeeAmount;
	}

	/**
	 * @param subsidyAmount the subsidyAmount to set
	 */
	public void setSubsidyAmount(Amount subsidyAmount) {
		this.subsidyAmount = subsidyAmount;
	}

}
