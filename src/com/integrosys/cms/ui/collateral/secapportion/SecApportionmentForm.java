/*
 * Created on Jun 27, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.collateral.secapportion;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SecApportionmentForm extends CommonForm implements Serializable {
	private String priorityRank;

	private String priorityRankingAmt;

	private String apportionAmt;

	private String availableCollateralAmt;

	private String minFsvCharge;

	private String fsvCurrency;

	private String leId;

	private String leName;

	private String subProfileId;

	private String limitId;

	private String chargeDtlId;

	private String chargeRank;

	private String productDesc;

	private String approvedLimitCcy;

	private String approvedLimitAmt;

	private String activatedLimitCcy;

	private String activatedLimitAmt;

	private String percAmtInd;

	private String byAbsoluteAmt;

	private String byAbsoluteAmtDisp;

	private String byPercentage;

	private String byPercentageDisp;

	private String minPercAmtInd;

	private String minAbsoluteAmt;

	private String minAbsoluteAmtDisp;

	private String minPercentage;

	private String minPercentageDisp;

	private String maxPercAmtInd;

	private String maxAbsoluteAmt;

	private String maxAbsoluteAmtDisp;

	private String maxPercentage;

	private String maxPercentageDisp;

	private boolean priorityRankingAmtChanged = false;

	private boolean byAmountChanged = false;

	private boolean minimumChanged = false;

	private boolean maximumChanged = false;

	private boolean apportionAmtChanged = false;

	private String priorityRankDisp;

	private String leIdDisp;

	private String subProfileIdDisp;

	private String limitIdDisp;

	/**
	 * @return Returns the activatedLimitAmt.
	 */
	public String getActivatedLimitAmt() {
		return activatedLimitAmt;
	}

	/**
	 * @param activatedLimitAmt The activatedLimitAmt to set.
	 */
	public void setActivatedLimitAmt(String activatedLimitAmt) {
		this.activatedLimitAmt = activatedLimitAmt;
	}

	/**
	 * @return Returns the activatedLimitCcy.
	 */
	public String getActivatedLimitCcy() {
		return activatedLimitCcy;
	}

	/**
	 * @param activatedLimitCcy The activatedLimitCcy to set.
	 */
	public void setActivatedLimitCcy(String activatedLimitCcy) {
		this.activatedLimitCcy = activatedLimitCcy;
	}

	/**
	 * @return Returns the approvedLimitAmt.
	 */
	public String getApprovedLimitAmt() {
		return approvedLimitAmt;
	}

	/**
	 * @param approvedLimitAmt The approvedLimitAmt to set.
	 */
	public void setApprovedLimitAmt(String approvedLimitAmt) {
		this.approvedLimitAmt = approvedLimitAmt;
	}

	/**
	 * @return Returns the approvedLimitCcy.
	 */
	public String getApprovedLimitCcy() {
		return approvedLimitCcy;
	}

	/**
	 * @param approvedLimitCcy The approvedLimitCcy to set.
	 */
	public void setApprovedLimitCcy(String approvedLimitCcy) {
		this.approvedLimitCcy = approvedLimitCcy;
	}

	/**
	 * @return Returns the availableCollateralAmt.
	 */
	public String getAvailableCollateralAmt() {
		return availableCollateralAmt;
	}

	/**
	 * @param availableCollateralAmt The availableCollateralAmt to set.
	 */
	public void setAvailableCollateralAmt(String availableCollateralAmt) {
		this.availableCollateralAmt = availableCollateralAmt;
	}

	/**
	 * @return Returns the byAbsoluteAmt.
	 */
	public String getByAbsoluteAmt() {
		return byAbsoluteAmt;
	}

	/**
	 * @param byAbsoluteAmt The byAbsoluteAmt to set.
	 */
	public void setByAbsoluteAmt(String byAbsoluteAmt) {
		this.byAbsoluteAmt = byAbsoluteAmt;
	}

	/**
	 * @return Returns the byAbsoluteAmtDisp.
	 */
	public String getByAbsoluteAmtDisp() {
		return byAbsoluteAmtDisp;
	}

	/**
	 * @param byAbsoluteAmtDisp The byAbsoluteAmtDisp to set.
	 */
	public void setByAbsoluteAmtDisp(String byAbsoluteAmtDisp) {
		this.byAbsoluteAmtDisp = byAbsoluteAmtDisp;
	}

	/**
	 * @return Returns the byPercentage.
	 */
	public String getByPercentage() {
		return byPercentage;
	}

	/**
	 * @param byPercentage The byPercentage to set.
	 */
	public void setByPercentage(String byPercentage) {
		this.byPercentage = byPercentage;
	}

	/**
	 * @return Returns the byPercentageDisp.
	 */
	public String getByPercentageDisp() {
		return byPercentageDisp;
	}

	/**
	 * @param byPercentageDisp The byPercentageDisp to set.
	 */
	public void setByPercentageDisp(String byPercentageDisp) {
		this.byPercentageDisp = byPercentageDisp;
	}

	/**
	 * @return Returns the leId.
	 */
	public String getLeId() {
		return leId;
	}

	/**
	 * @param leId The leId to set.
	 */
	public void setLeId(String leId) {
		this.leId = leId;
	}

	/**
	 * @return Returns the leName.
	 */
	public String getLeName() {
		return leName;
	}

	/**
	 * @param leName The leName to set.
	 */
	public void setLeName(String leName) {
		this.leName = leName;
	}

	/**
	 * @return Returns the limitId.
	 */
	public String getLimitId() {
		return limitId;
	}

	/**
	 * @param limitId The limitId to set.
	 */
	public void setLimitId(String limitId) {
		this.limitId = limitId;
	}

	/**
	 * @return Returns the chargeDtlId.
	 */
	public String getChargeDtlId() {
		return chargeDtlId;
	}

	/**
	 * @param chargeDtlId The chargeDtlId to set.
	 */
	public void setChargeDtlId(String chargeDtlId) {
		this.chargeDtlId = chargeDtlId;
	}

	public String getChargeRank() {
		return chargeRank;
	}

	public void setChargeRank(String chargeRank) {
		this.chargeRank = chargeRank;
	}

	/**
	 * @return Returns the maxAbsoluteAmt.
	 */
	public String getMaxAbsoluteAmt() {
		return maxAbsoluteAmt;
	}

	/**
	 * @param maxAbsoluteAmt The maxAbsoluteAmt to set.
	 */
	public void setMaxAbsoluteAmt(String maxAbsoluteAmt) {
		this.maxAbsoluteAmt = maxAbsoluteAmt;
	}

	/**
	 * @return Returns the maxAbsoluteAmtDisp.
	 */
	public String getMaxAbsoluteAmtDisp() {
		return maxAbsoluteAmtDisp;
	}

	/**
	 * @param maxAbsoluteAmtDisp The maxAbsoluteAmtDisp to set.
	 */
	public void setMaxAbsoluteAmtDisp(String maxAbsoluteAmtDisp) {
		this.maxAbsoluteAmtDisp = maxAbsoluteAmtDisp;
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
	public String getMaxPercentage() {
		return maxPercentage;
	}

	/**
	 * @param maxPercentage The maxPercentage to set.
	 */
	public void setMaxPercentage(String maxPercentage) {
		this.maxPercentage = maxPercentage;
	}

	/**
	 * @return Returns the maxPercentageDisp.
	 */
	public String getMaxPercentageDisp() {
		return maxPercentageDisp;
	}

	/**
	 * @param maxPercentageDisp The maxPercentageDisp to set.
	 */
	public void setMaxPercentageDisp(String maxPercentageDisp) {
		this.maxPercentageDisp = maxPercentageDisp;
	}

	/**
	 * @return Returns the minAbsoluteAmt.
	 */
	public String getMinAbsoluteAmt() {
		return minAbsoluteAmt;
	}

	/**
	 * @param minAbsoluteAmt The minAbsoluteAmt to set.
	 */
	public void setMinAbsoluteAmt(String minAbsoluteAmt) {
		this.minAbsoluteAmt = minAbsoluteAmt;
	}

	/**
	 * @return Returns the minAbsoluteAmtDisp.
	 */
	public String getMinAbsoluteAmtDisp() {
		return minAbsoluteAmtDisp;
	}

	/**
	 * @param minAbsoluteAmtDisp The minAbsoluteAmtDisp to set.
	 */
	public void setMinAbsoluteAmtDisp(String minAbsoluteAmtDisp) {
		this.minAbsoluteAmtDisp = minAbsoluteAmtDisp;
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
	public String getMinPercentage() {
		return minPercentage;
	}

	/**
	 * @param minPercentage The minPercentage to set.
	 */
	public void setMinPercentage(String minPercentage) {
		this.minPercentage = minPercentage;
	}

	/**
	 * @return Returns the minPercentageDisp.
	 */
	public String getMinPercentageDisp() {
		return minPercentageDisp;
	}

	/**
	 * @param minPercentageDisp The minPercentageDisp to set.
	 */
	public void setMinPercentageDisp(String minPercentageDisp) {
		this.minPercentageDisp = minPercentageDisp;
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
	 * @return Returns the priorityRank.
	 */
	public String getPriorityRank() {
		return priorityRank;
	}

	/**
	 * @param priorityRank The priorityRank to set.
	 */
	public void setPriorityRank(String priorityRank) {
		this.priorityRank = priorityRank;
	}

	/**
	 * @return Returns the productDesc.
	 */
	public String getProductDesc() {
		return productDesc;
	}

	/**
	 * @param productDesc The productDesc to set.
	 */
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	/**
	 * @return Returns the subProfileId.
	 */
	public String getSubProfileId() {
		return subProfileId;
	}

	/**
	 * @param subProfileId The subProfileId to set.
	 */
	public void setSubProfileId(String subProfileId) {
		this.subProfileId = subProfileId;
	}

	/**
	 * @return Returns the fsvCurrency.
	 */
	public String getFsvCurrency() {
		return fsvCurrency;
	}

	/**
	 * @param fsvCurrency The fsvCurrency to set.
	 */
	public void setFsvCurrency(String fsvCurrency) {
		this.fsvCurrency = fsvCurrency;
	}

	/**
	 * @return Returns the priorityRankingAmt.
	 */
	public String getPriorityRankingAmt() {
		return priorityRankingAmt;
	}

	/**
	 * @param priorityRankingAmt The priorityRankingAmt to set.
	 */
	public void setPriorityRankingAmt(String priorityRankingAmt) {
		this.priorityRankingAmt = priorityRankingAmt;
	}

	public String getApportionAmt() {
		return apportionAmt;
	}

	public void setApportionAmt(String apportionAmt) {
		this.apportionAmt = apportionAmt;
	}

	/**
	 * @return Returns the priorityRankingAmtChanged.
	 */
	public boolean isPriorityRankingAmtChanged() {
		return priorityRankingAmtChanged;
	}

	/**
	 * @param priorityRankingAmtChanged The priorityRankingAmtChanged to set.
	 */
	public void setPriorityRankingAmtChanged(boolean priorityRankingAmtChanged) {
		this.priorityRankingAmtChanged = priorityRankingAmtChanged;
	}

	/**
	 * @return Returns the byAmountChanged.
	 */
	public boolean isByAmountChanged() {
		return byAmountChanged;
	}

	/**
	 * @param byAmountChanged The byAmountChanged to set.
	 */
	public void setByAmountChanged(boolean byAmountChanged) {
		this.byAmountChanged = byAmountChanged;
	}

	/**
	 * @return Returns the maximumChanged.
	 */
	public boolean isMaximumChanged() {
		return maximumChanged;
	}

	/**
	 * @param maximumChanged The maximumChanged to set.
	 */
	public void setMaximumChanged(boolean maximumChanged) {
		this.maximumChanged = maximumChanged;
	}

	/**
	 * @return Returns the minimumChanged.
	 */
	public boolean isMinimumChanged() {
		return minimumChanged;
	}

	/**
	 * @param minimumChanged The minimumChanged to set.
	 */
	public void setMinimumChanged(boolean minimumChanged) {
		this.minimumChanged = minimumChanged;
	}

	public boolean isApportionAmtChanged() {
		return apportionAmtChanged;
	}

	public void setApportionAmtChanged(boolean apportionAmtChanged) {
		this.apportionAmtChanged = apportionAmtChanged;
	}

	/**
	 * @return Returns the leIdDisp.
	 */
	public String getLeIdDisp() {
		return leIdDisp;
	}

	/**
	 * @param leIdDisp The leIdDisp to set.
	 */
	public void setLeIdDisp(String leIdDisp) {
		this.leIdDisp = leIdDisp;
	}

	/**
	 * @return Returns the limitIdDisp.
	 */
	public String getLimitIdDisp() {
		return limitIdDisp;
	}

	/**
	 * @param limitIdDisp The limitIdDisp to set.
	 */
	public void setLimitIdDisp(String limitIdDisp) {
		this.limitIdDisp = limitIdDisp;
	}

	/**
	 * @return Returns the priorityRankDisp.
	 */
	public String getPriorityRankDisp() {
		return priorityRankDisp;
	}

	/**
	 * @param priorityRankDisp The priorityRankDisp to set.
	 */
	public void setPriorityRankDisp(String priorityRankDisp) {
		this.priorityRankDisp = priorityRankDisp;
	}

	/**
	 * @return Returns the subProfileIdDisp.
	 */
	public String getSubProfileIdDisp() {
		return subProfileIdDisp;
	}

	/**
	 * @param subProfileIdDisp The subProfileIdDisp to set.
	 */
	public void setSubProfileIdDisp(String subProfileIdDisp) {
		this.subProfileIdDisp = subProfileIdDisp;
	}

	public String getMinFsvCharge() {
		return minFsvCharge;
	}

	public void setMinFsvCharge(String minFsvCharge) {
		this.minFsvCharge = minFsvCharge;
	}

	public String[][] getMapper() {
		String[][] input = {
				{ "form.secApportionObject", "com.integrosys.cms.ui.collateral.secapportion.SecApportionmentMapper" },
				{ "form.availableColAmt", "com.integrosys.cms.ui.collateral.secapportion.AvailableColAmtMapper" },
				{ "form.apportionLmtDtl", "com.integrosys.cms.ui.collateral.secapportion.SecApportionLmtDtlMapper" } };
		return input;
	}

	public void reset() {
	}

}
