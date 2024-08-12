package com.integrosys.cms.app.limit.bus;

import java.io.Serializable;
import java.util.Date;

public interface IFacilityInterest extends Serializable {
	public long getFacilityMasterId();

	public Double getInterestRate();

	public String getInterestRateTypeCategoryCode();

	public String getInterestRateTypeEntryCode();

	public Double getSpread();

	public Character getSpreadSign();

	public Double getPrimeRateFloor();

	public Double getPrimeRateCeiling();

	public Date getPrimeReviewDate();

	public Integer getPrimeReviewTerm();

	public String getPrimeReviewTermCodeCategoryCode();

	public String getPrimeReviewTermCodeEntryCode();

	public void setFacilityMasterId(long facilityMasterId);

	public void setInterestRate(Double interestRate);

	public void setInterestRateTypeCategoryCode(String interestRateTypeCategoryCode);

	public void setInterestRateTypeEntryCode(String interestRateTypeEntryCode);

	public void setSpread(Double spread);

	public void setSpreadSign(Character spreadSign);

	public void setPrimeRateFloor(Double primeRateFloor);

	public void setPrimeRateCeiling(Double primeRateCeiling);

	public void setPrimeReviewDate(Date primeReviewDate);

	public void setPrimeReviewTerm(Integer primeReviewTerm);

	public void setPrimeReviewTermCodeCategoryCode(String primeReviewTermCodeCategoryCode);

	public void setPrimeReviewTermCodeEntryCode(String primeReviewTermCodeEntryCode);

    public Integer getInterestBase();

    public void setInterestBase(Integer interestBase);

    public Character getInterestMode();

    public void setInterestMode(Character interestMode);

    public Integer getInterestYearBase();

    public void setInterestYearBase(Integer interestYearBase);
}
