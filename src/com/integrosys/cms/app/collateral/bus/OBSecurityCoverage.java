package com.integrosys.cms.app.collateral.bus;

import java.math.BigDecimal;

import com.integrosys.cms.app.common.constant.ICMSConstant;

public class OBSecurityCoverage implements ISecurityCoverage {

	private static final long serialVersionUID = 1L;
	
	private long id = ICMSConstant.LONG_MIN_VALUE;;
	private long collateralId = ICMSConstant.LONG_MIN_VALUE;;
	private BigDecimal coverageAmount;
	private BigDecimal adHocCoverageAmount;
	private Double coveragePercentage;
		
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public long getCollateralId() {
		return collateralId;
	}
	public void setCollateralId(long collateralId) {
		this.collateralId = collateralId;
	}

	public BigDecimal getCoverageAmount() {
		return coverageAmount;
	}
	public void setCoverageAmount(BigDecimal coverageAmount) {
		this.coverageAmount = coverageAmount;
	}
	
	public BigDecimal getAdHocCoverageAmount() {
		return adHocCoverageAmount;
	}
	public void setAdHocCoverageAmount(BigDecimal adHocCoverageAmount) {
		this.adHocCoverageAmount = adHocCoverageAmount;
	}
	
	public Double getCoveragePercentage() {
		return coveragePercentage;
	}
	public void setCoveragePercentage(Double coveragePercentage) {
		this.coveragePercentage = coveragePercentage;
	}
	
}
