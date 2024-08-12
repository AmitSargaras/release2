/*
 * Created on Jun 19, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.collateral.bus;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class OBSecApportionmentDtl extends OBSecApportionment {
	private String leId;

	private String subProfileId;

	private String limitIdDisp;

	private String leName;

	private String productDesc;

	private String approvedLimitCcy;

	private String approvedLimitAmt;

	private String activatedLimitCcy;

	private String activatedLimitAmt;

	private String chargeRank;

	private double chargeAmount;

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
	 * @return Returns the chargeRank.
	 */
	public String getChargeRank() {
		return chargeRank;
	}

	/**
	 * @param chargeRank The chargeRank to set.
	 */
	public void setChargeRank(String chargeRank) {
		this.chargeRank = chargeRank;
	}
}
