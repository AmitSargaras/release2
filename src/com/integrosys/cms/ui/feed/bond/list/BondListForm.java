package com.integrosys.cms.ui.feed.bond.list;

import java.util.Date;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * This class implements FormBean
 */
public class BondListForm extends TrxContextForm implements java.io.Serializable {

	public static final String MAPPER = "com.integrosys.cms.ui.feed.bond.list.BondListMapper";

	private String[] updatedUnitPrices;

	private String[] ratingArr;

	private String[] chkDeletes;

	private String[] bondNames;
	
	private String[] bondCodes;
	
	private String[] isinCode;
	
	private String[] couponRate;
	
	private String[] issueDateArr;
	
	private String[] maturityDateArr;

	private String[] lastUpdateDate;
	
	private String targetOffset = "-1";

	/**
	 * @return the bondCodes
	 */
	public String[] getBondCodes() {
		return bondCodes;
	}

	/**
	 * @param bondCodes the bondCodes to set
	 */
	public void setBondCodes(String[] bondCodes) {
		this.bondCodes = bondCodes;
	}

	public String[] getBondNames() {
		return bondNames;
	}

	public String[] getChkDeletes() {
		return chkDeletes;
	}

	/**
	 * This method defines a String array which tells what object is to be
	 * formed from the form and using what mapper classes to form it. it has a
	 * syntax [(key, MapperClassname)]
	 * 
	 * @return 2-dimensional String Array
	 */
	public String[][] getMapper() {
		return new String[][] { { MAPPER, MAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
	}

	public String[] getRatingArr() {
		return ratingArr;
	}

	public String getRatingArr(int i) {
		return ratingArr[i];
	}

	public String[] getIssueDateArr() {
		return issueDateArr;
	}

	public void setIssueDateArr(String[] issueDateArr) {
		this.issueDateArr = issueDateArr;
	}

	public String[] getMaturityDateArr() {
		return maturityDateArr;
	}

	public void setMaturityDateArr(String[] maturityDateArr) {
		this.maturityDateArr = maturityDateArr;
	}

	public String[] getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String[] lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String[] getCouponRate() {
		return couponRate;
	}

	public void setCouponRate(String[] couponRate) {
		this.couponRate = couponRate;
	}

	public String[] getIsinCode() {
		return isinCode;
	}

	public void setIsinCode(String[] isinCode) {
		this.isinCode = isinCode;
	}

	public String getTargetOffset() {
		return targetOffset;
	}

	public String[] getUpdatedUnitPrices() {
		return updatedUnitPrices;
	}

	public String getUpdatedUnitPrices(int i) {
		return updatedUnitPrices[i];
	}

	public void setBondNames(String[] bondNames) {
		this.bondNames = bondNames;
	}

	public void setChkDeletes(String[] chkDeletes) {
		this.chkDeletes = chkDeletes;
	}

	public void setRatingArr(String[] rating) {
		this.ratingArr = rating;
	}

	public void setTargetOffset(String targetOffset) {
		this.targetOffset = targetOffset;
	}

	public void setUpdatedUnitPrices(String[] updatedUnitPrices) {
		this.updatedUnitPrices = updatedUnitPrices;
	}

}
