package com.integrosys.cms.app.limit.bus;

import java.io.Serializable;

import com.integrosys.base.businfra.currency.Amount;

public interface IFacilityFeeCharge extends Serializable {
	
	public String getCommissionBasisCategoryCode();

	public String getCommissionBasisEntryCode();
	
	public Amount getCommissionFeesAmount();

	public Double getCommissionRate();

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode();

	public Double getFacilityCommitmentRate();

	public String getFacilityCommitmentRateNumberCategoryCode();

	public String getFacilityCommitmentRateNumberEntryCode();

	public long getFacilityMasterId();

	public Amount getHandlingFeesAmount();

	public Integer getLateChargeType();

	public Amount getMaximumCommissionAmount();

	public Amount getMinimumCommissionAmount();

	public Amount getOthersFeeAmount();

	public Amount getSubsidyAmount();

	public void setCommissionBasisCategoryCode(String commissionBasisCategoryCode);

	public void setCommissionBasisEntryCode(String commissionBasisEntryCode);

	public void setCommissionFeesAmount(Amount commissionFeesAmount);

	public void setCommissionRate(Double commissionRate);

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode);

	public void setFacilityCommitmentRate(Double facilityCommitmentRate);

	public void setFacilityCommitmentRateNumberCategoryCode(String facilityCommitmentRateNumberCategoryCode);

	public void setFacilityCommitmentRateNumberEntryCode(String facilityCommitmentRateNumberEntryCode);

	public void setFacilityMasterId(long facilityMasterId);

	public void setHandlingFeesAmount(Amount handlingFeesAmount);

	public void setLateChargeType(Integer lateChargeType);

	public void setMaximumCommissionAmount(Amount maximumCommissionAmount);

	public void setMinimumCommissionAmount(Amount minimumCommissionAmount);

	public void setOthersFeeAmount(Amount othersFeeAmount);

	public void setSubsidyAmount(Amount subsidyAmount);
}
