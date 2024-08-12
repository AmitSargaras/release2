package com.integrosys.cms.app.limit.bus;

import java.util.Date;

/**
 * @author Chong Jun Yong
 * 
 */
public class OBFacilityInterest implements IFacilityInterest {
	private long facilityMasterId;

	private Double interestRate;

	private String interestRateTypeCategoryCode;

	private String interestRateTypeEntryCode;

	private Double spread;

	private Character spreadSign;

	private Double primeRateFloor;

	private Double primeRateCeiling;

	private Date primeReviewDate;

	private Integer primeReviewTerm;

	private String primeReviewTermCodeCategoryCode;

	private String primeReviewTermCodeEntryCode;

    //Andy Wong: 5 Feb 2009: interest fields for CLOS
    private Integer interestBase;

    private Character interestMode;

    private Integer interestYearBase;
	/**
	 * @return the facilityMasterId
	 */
	public long getFacilityMasterId() {
		return facilityMasterId;
	}

	/**
	 * @return the interestRate
	 */
	public Double getInterestRate() {
		return interestRate;
	}

	/**
	 * @return the interestRateTypeCategoryCode
	 */
	public String getInterestRateTypeCategoryCode() {
		return interestRateTypeCategoryCode;
	}

	/**
	 * @return the interestRateTypeEntryCode
	 */
	public String getInterestRateTypeEntryCode() {
		return interestRateTypeEntryCode;
	}

	/**
	 * @return the spread
	 */
	public Double getSpread() {
		return spread;
	}

	/**
	 * @return the spreadSign
	 */
	public Character getSpreadSign() {
		return spreadSign;
	}

	/**
	 * @return the primeRateFloor
	 */
	public Double getPrimeRateFloor() {
		return primeRateFloor;
	}

	/**
	 * @return the primeRateCeiling
	 */
	public Double getPrimeRateCeiling() {
		return primeRateCeiling;
	}

	/**
	 * @return the primeReviewDate
	 */
	public Date getPrimeReviewDate() {
		return primeReviewDate;
	}

	/**
	 * @return the primeReviewTerm
	 */
	public Integer getPrimeReviewTerm() {
		return primeReviewTerm;
	}

	/**
	 * @return the primeReviewTermCodeCategoryCode
	 */
	public String getPrimeReviewTermCodeCategoryCode() {
		return primeReviewTermCodeCategoryCode;
	}

	/**
	 * @return the primeReviewTermCodeEntryCode
	 */
	public String getPrimeReviewTermCodeEntryCode() {
		return primeReviewTermCodeEntryCode;
	}

	/**
	 * @param facilityMasterId the facilityMasterId to set
	 */
	public void setFacilityMasterId(long facilityMasterId) {
		this.facilityMasterId = facilityMasterId;
	}

	/**
	 * @param interestRate the interestRate to set
	 */
	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	/**
	 * @param interestRateTypeCategoryCode the interestRateTypeCategoryCode to
	 *        set
	 */
	public void setInterestRateTypeCategoryCode(String interestRateTypeCategoryCode) {
		this.interestRateTypeCategoryCode = interestRateTypeCategoryCode;
	}

	/**
	 * @param interestRateTypeEntryCode the interestRateTypeEntryCode to set
	 */
	public void setInterestRateTypeEntryCode(String interestRateTypeEntryCode) {
		this.interestRateTypeEntryCode = interestRateTypeEntryCode;
	}

	/**
	 * @param spread the spread to set
	 */
	public void setSpread(Double spread) {
		this.spread = spread;
	}

	/**
	 * @param spreadSign the spreadSign to set
	 */
	public void setSpreadSign(Character spreadSign) {
		this.spreadSign = spreadSign;
	}

	/**
	 * @param primeRateFloor the primeRateFloor to set
	 */
	public void setPrimeRateFloor(Double primeRateFloor) {
		this.primeRateFloor = primeRateFloor;
	}

	/**
	 * @param primeRateCeiling the primeRateCeiling to set
	 */
	public void setPrimeRateCeiling(Double primeRateCeiling) {
		this.primeRateCeiling = primeRateCeiling;
	}

	/**
	 * @param primeReviewDate the primeReviewDate to set
	 */
	public void setPrimeReviewDate(Date primeReviewDate) {
		this.primeReviewDate = primeReviewDate;
	}

	/**
	 * @param primeReviewTerm the primeReviewTerm to set
	 */
	public void setPrimeReviewTerm(Integer primeReviewTerm) {
		this.primeReviewTerm = primeReviewTerm;
	}

	/**
	 * @param primeReviewTermCodeCategoryCode the
	 *        primeReviewTermCodeCategoryCode to set
	 */
	public void setPrimeReviewTermCodeCategoryCode(String primeReviewTermCodeCategoryCode) {
		this.primeReviewTermCodeCategoryCode = primeReviewTermCodeCategoryCode;
	}

	/**
	 * @param primeReviewTermCodeEntryCode the primeReviewTermCodeEntryCode to
	 *        set
	 */
	public void setPrimeReviewTermCodeEntryCode(String primeReviewTermCodeEntryCode) {
		this.primeReviewTermCodeEntryCode = primeReviewTermCodeEntryCode;
	}

    public Integer getInterestBase() {
        return interestBase;
    }

    public void setInterestBase(Integer interestBase) {
        this.interestBase = interestBase;
    }

    public Character getInterestMode() {
        return interestMode;
    }

    public void setInterestMode(Character interestMode) {
        this.interestMode = interestMode;
    }

    public Integer getInterestYearBase() {
        return interestYearBase;
    }

    public void setInterestYearBase(Integer interestYearBase) {
        this.interestYearBase = interestYearBase;
    }
}
