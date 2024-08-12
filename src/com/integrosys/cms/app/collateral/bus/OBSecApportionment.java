/*
 * Created on Jun 19, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.collateral.bus;

import java.io.Serializable;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class OBSecApportionment implements ISecApportionment, Serializable {

	private long secApportionmentID = ICMSConstant.LONG_INVALID_VALUE;

	private int priorityRanking = 1;

	private String currencyCode;

	private double priorityRankingAmount = 0;

	private double apportionAmount = 0;

	private double apportionAmountPrev = 0;

	private long collateralID = ICMSConstant.LONG_MIN_VALUE;

	private long limitID = ICMSConstant.LONG_INVALID_VALUE;

	private long refID = ICMSConstant.LONG_INVALID_VALUE;

	private long chargeDetailId = ICMSConstant.LONG_INVALID_VALUE;

	private String percAmtInd = ICMSConstant.SEC_APPORTION_NA;

	private double byAbsoluteAmt = 0;

	private double byPercentage = 0;

	private String minPercAmtInd = ICMSConstant.SEC_APPORTION_NA;

	private double minAbsoluteAmt = 0;

	private double minPercentage = 0;

	private String maxPercAmtInd = ICMSConstant.SEC_APPORTION_NA;

	private double maxAbsoluteAmt = 0;

	private double maxPercentage = 0;

	/**
	 * @return Returns the byAbsoluteAmt.
	 */
	public double getByAbsoluteAmt() {
		return byAbsoluteAmt;
	}

	/**
	 * @param byAbsoluteAmt The byAbsoluteAmt to set.
	 */
	public void setByAbsoluteAmt(double byAbsoluteAmt) {
		this.byAbsoluteAmt = byAbsoluteAmt;
	}

	/**
	 * @return Returns the byPercentage.
	 */
	public double getByPercentage() {
		return byPercentage;
	}

	/**
	 * @param byPercentage The byPercentage to set.
	 */
	public void setByPercentage(double byPercentage) {
		this.byPercentage = byPercentage;
	}

	/**
	 * @return Returns the collateralID.
	 */
	public long getCollateralID() {
		return collateralID;
	}

	/**
	 * @param collateralID The collateralID to set.
	 */
	public void setCollateralID(long collateralID) {
		this.collateralID = collateralID;
	}

	/**
	 * @return Returns the currencyCode.
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode The currencyCode to set.
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return Returns the limitID.
	 */
	public long getLimitID() {
		return limitID;
	}

	/**
	 * @param limitID The limitID to set.
	 */
	public void setLimitID(long limitID) {
		this.limitID = limitID;
	}

	/**
	 * @return Returns the maxAbsoluteAmt.
	 */
	public double getMaxAbsoluteAmt() {
		return maxAbsoluteAmt;
	}

	/**
	 * @param maxAbsoluteAmt The maxAbsoluteAmt to set.
	 */
	public void setMaxAbsoluteAmt(double maxAbsoluteAmt) {
		this.maxAbsoluteAmt = maxAbsoluteAmt;
	}

	/**
	 * @return Returns the maxPercAmtInd.
	 */
	public String getMaxPercAmtInd() {
		return maxPercAmtInd;
	}

	/**
	 * @param maxPercAmtInd The maxPercAmtInd to set.
	 */
	public void setMaxPercAmtInd(String maxPercAmtInd) {
		this.maxPercAmtInd = maxPercAmtInd;
	}

	/**
	 * @return Returns the maxPercentage.
	 */
	public double getMaxPercentage() {
		return maxPercentage;
	}

	/**
	 * @param maxPercentage The maxPercentage to set.
	 */
	public void setMaxPercentage(double maxPercentage) {
		this.maxPercentage = maxPercentage;
	}

	/**
	 * @return Returns the minAbsoluteAmt.
	 */
	public double getMinAbsoluteAmt() {
		return minAbsoluteAmt;
	}

	/**
	 * @param minAbsoluteAmt The minAbsoluteAmt to set.
	 */
	public void setMinAbsoluteAmt(double minAbsoluteAmt) {
		this.minAbsoluteAmt = minAbsoluteAmt;
	}

	/**
	 * @return Returns the minPercAmtInd.
	 */
	public String getMinPercAmtInd() {
		return minPercAmtInd;
	}

	/**
	 * @param minPercAmtInd The minPercAmtInd to set.
	 */
	public void setMinPercAmtInd(String minPercAmtInd) {
		this.minPercAmtInd = minPercAmtInd;
	}

	/**
	 * @return Returns the minPercentage.
	 */
	public double getMinPercentage() {
		return minPercentage;
	}

	/**
	 * @param minPercentage The minPercentage to set.
	 */
	public void setMinPercentage(double minPercentage) {
		this.minPercentage = minPercentage;
	}

	/**
	 * @return Returns the percAmtInd.
	 */
	public String getPercAmtInd() {
		return percAmtInd;
	}

	/**
	 * @param percAmtInd The percAmtInd to set.
	 */
	public void setPercAmtInd(String percAmtInd) {
		this.percAmtInd = percAmtInd;
	}

	/**
	 * @return Returns the priorityRanking.
	 */
	public int getPriorityRanking() {
		return priorityRanking;
	}

	/**
	 * @param priorityRanking The priorityRanking to set.
	 */
	public void setPriorityRanking(int priorityRanking) {
		this.priorityRanking = priorityRanking;
	}

	/**
	 * @return Returns the priorityRankingAmount.
	 */
	public double getPriorityRankingAmount() {
		return priorityRankingAmount;
	}

	/**
	 * @param priorityRankingAmount The priorityRankingAmount to set.
	 */
	public void setPriorityRankingAmount(double priorityRankingAmount) {
		this.priorityRankingAmount = priorityRankingAmount;
	}

	public double getApportionAmount() {
		return apportionAmount;
	}

	public void setApportionAmount(double amt) {
		this.apportionAmount = amt;
	}

	/**
	 * @return Returns the secApportionmentID.
	 */
	public long getSecApportionmentID() {
		return secApportionmentID;
	}

	/**
	 * @param secApportionmentID The secApportionmentID to set.
	 */
	public void setSecApportionmentID(long secApportionmentID) {
		this.secApportionmentID = secApportionmentID;
	}

	/**
	 * @return Returns the refID.
	 */
	public long getRefID() {
		return refID;
	}

	/**
	 * @param refID The refID to set.
	 */
	public void setRefID(long refID) {
		this.refID = refID;
	}

	/**
	 * @return Returns the priorityRankingAmtPrev.
	 */
	public double getApportionAmountPrev() {
		return apportionAmountPrev;
	}

	/**
	 * @param priorityRankingAmtPrev The priorityRankingAmtPrev to set.
	 */
	public void setApportionAmountPrev(double apportionAmountPrev) {
		this.apportionAmountPrev = apportionAmountPrev;
	}

	/**
	 * @return Returns the chargeDetailId.
	 */
	public long getChargeDetailId() {
		return chargeDetailId;
	}

	/**
	 * @param chargeDetailId The chargeDetailId to set.
	 */
	public void setChargeDetailId(long chargeDetailId) {
		this.chargeDetailId = chargeDetailId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof OBSecApportionment)) {
			return false;
		}
		OBSecApportionment apportionObj = (OBSecApportionment) obj;
		if (this.getPriorityRanking() != apportionObj.getPriorityRanking()) {
			return false;
		}
		if (this.getChargeDetailId() != apportionObj.getChargeDetailId()) {
			return false;
		}
		if (this.getApportionAmount() != apportionObj.getApportionAmount()) {
			return false;
		}
		if (this.getPriorityRankingAmount() != apportionObj.getPriorityRankingAmount()) {
			return false;
		}
		if (!this.getPercAmtInd().equals(apportionObj.getPercAmtInd())) {
			return false;
		}
		if (!this.getMinPercAmtInd().equals(apportionObj.getMinPercAmtInd())) {
			return false;
		}
		if (!this.getMaxPercAmtInd().equals(apportionObj.getMaxPercAmtInd())) {
			return false;
		}
		if (ICMSConstant.SEC_APPORTION_AMT.equals(this.getPercAmtInd())) {
			if (this.getByAbsoluteAmt() != apportionObj.getByAbsoluteAmt()) {
				return false;
			}
		}
		else if (ICMSConstant.SEC_APPORTION_PERC.equals(this.getPercAmtInd())) {
			if (this.getByPercentage() != apportionObj.getByPercentage()) {
				return false;
			}
		}

		if (ICMSConstant.SEC_APPORTION_AMT.equals(this.getMinPercAmtInd())) {
			if (this.getMinAbsoluteAmt() == apportionObj.getMinAbsoluteAmt()) {
				return false;
			}
		}
		else if (ICMSConstant.SEC_APPORTION_PERC.equals(this.getMinPercAmtInd())) {
			if (this.getMinPercentage() == apportionObj.getMinPercentage()) {
				return false;
			}
		}

		if (ICMSConstant.SEC_APPORTION_AMT.equals(this.getMaxPercAmtInd())) {
			if (this.getMaxAbsoluteAmt() == apportionObj.getMaxAbsoluteAmt()) {
				return false;
			}
		}
		else if (ICMSConstant.SEC_APPORTION_PERC.equals(this.getMaxPercAmtInd())) {
			if (this.getMaxPercentage() == apportionObj.getMaxPercentage()) {
				return false;
			}
		}
		return true;
	}
}
