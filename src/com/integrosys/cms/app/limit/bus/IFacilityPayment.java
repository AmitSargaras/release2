package com.integrosys.cms.app.limit.bus;

import java.io.Serializable;

public interface IFacilityPayment extends Serializable {
	public long getFacilityMasterId();

	public String getGracePeriodCategoryCode();

	public String getGracePeriodCodeCategoryCode();

	public String getGracePeriodCodeEntryCode();

	public String getGracePeriodEntryCode();

	public String getInterestPaymentFrequencyCategoryCode();

	public String getInterestPaymentFrequencyEntryCode();

	public String getPaymentCodeCategoryCode();

	public String getPaymentCodeEntryCode();

	public String getPaymentFrequencyCategoryCode();

	public String getPaymentFrequencyCodeCategoryCode();

	public String getPaymentFrequencyCodeEntryCode();

	public String getPaymentFrequencyEntryCode();

	public void setFacilityMasterId(long facilityMasterId);

	public void setGracePeriodCategoryCode(String gracePeriodCategoryCode);

	public void setGracePeriodCodeCategoryCode(String gracePeriodCodeCategoryCode);

	public void setGracePeriodCodeEntryCode(String gracePeriodCodeEntryCode);

	public void setGracePeriodEntryCode(String gracePeriodEntryCode);

	public void setInterestPaymentFrequencyCategoryCode(String interestPaymentFrequencyCategoryCode);

	public void setInterestPaymentFrequencyEntryCode(String interestPaymentFrequencyEntryCode);

	public void setPaymentCodeCategoryCode(String paymentCodeCategoryCode);

	public void setPaymentCodeEntryCode(String paymentCodeEntryCode);

	public void setPaymentFrequencyCategoryCode(String paymentFrequencyCategoryCode);

	public void setPaymentFrequencyCodeCategoryCode(String paymentFrequencyCodeCategoryCode);

	public void setPaymentFrequencyCodeEntryCode(String paymentFrequencyCodeEntryCode);

	public void setPaymentFrequencyEntryCode(String paymentFrequencyEntryCode);

	public String getInterestPaymentFrequencyCodeCategoryCode();

	public void setInterestPaymentFrequencyCodeCategoryCode(String interestPaymentFrequencyCodeCategoryCode);

	public String getInterestPaymentFrequencyCodeEntryCode();

	public void setInterestPaymentFrequencyCodeEntryCode(String interestPaymentFrequencyCodeEntryCode);
}
