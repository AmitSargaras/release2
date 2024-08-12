package com.integrosys.cms.app.collateral.bus;

import java.io.Serializable;
import java.math.BigDecimal;

public interface ISecurityCoverage extends Serializable {
	
	public long getId();
	public void setId(long secCoverageId);
	
	public long getCollateralId();
	public void setCollateralId(long collateralId);
	
	public BigDecimal getCoverageAmount();
	public void setCoverageAmount(BigDecimal coverageAmount);
	
	public BigDecimal getAdHocCoverageAmount();
	public void setAdHocCoverageAmount(BigDecimal adHocCoverageAmount);
	
	public Double getCoveragePercentage();
	public void setCoveragePercentage(Double coveragePercentage);
	
}
