/*
 * Created on Jun 20, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.collateral.bus;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class OBSecApportionLmtDtl implements Serializable {
	private long leId;

	private String leName;

	private long subProfileId;

	private long cmsLspApprLmtId;

	private long limitId;

	private long chargeId;

	private int chargeRank;

	private double chargeAmount;

	private String productDesc;

	private String approvedLimitCcy;

	private String approvedLimitAmt;

	private String activatedLimitCcy;

	private String activatedLimitAmt;

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
	 * @return Returns the cmsLspApprLmtId.
	 */
	public long getCmsLspApprLmtId() {
		return cmsLspApprLmtId;
	}

	/**
	 * @param cmsLspApprLmtId The cmsLspApprLmtId to set.
	 */
	public void setCmsLspApprLmtId(long cmsLspApprLmtId) {
		this.cmsLspApprLmtId = cmsLspApprLmtId;
	}

	/**
	 * @return Returns the leId.
	 */
	public long getLeId() {
		return leId;
	}

	/**
	 * @param leId The leId to set.
	 */
	public void setLeId(long leId) {
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
	public long getLimitId() {
		return limitId;
	}

	/**
	 * @param limitId The limitId to set.
	 */
	public void setLimitId(long limitId) {
		this.limitId = limitId;
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
	public long getSubProfileId() {
		return subProfileId;
	}

	/**
	 * @param subProfileId The subProfileId to set.
	 */
	public void setSubProfileId(long subProfileId) {
		this.subProfileId = subProfileId;
	}

	/**
	 * @return Returns the chargeAmount.
	 */
	public double getChargeAmount() {
		return chargeAmount;
	}

	/**
	 * @param chargeAmount The chargeAmount to set.
	 */
	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	/**
	 * @return Returns the chargeId.
	 */
	public long getChargeId() {
		return chargeId;
	}

	/**
	 * @param chargeId The chargeId to set.
	 */
	public void setChargeId(long chargeId) {
		this.chargeId = chargeId;
	}

	/**
	 * @return Returns the chargeRank.
	 */
	public int getChargeRank() {
		return chargeRank;
	}

	/**
	 * @param chargeRank The chargeRank to set.
	 */
	public void setChargeRank(int chargeRank) {
		this.chargeRank = chargeRank;
	}
}
