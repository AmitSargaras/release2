package com.integrosys.cms.ui.collateral;

import org.apache.commons.lang.StringUtils;

public class SecurityCoverageForm {
	
	private String coverageAmount = StringUtils.EMPTY;
	private String coveragePercentage = StringUtils.EMPTY;
	private String adHocCoverageAmount = StringUtils.EMPTY;
	
	public String getCoverageAmount() {
		return coverageAmount;
	}
	public void setCoverageAmount(String coverageAmount) {
		this.coverageAmount = coverageAmount;
	}
	public String getCoveragePercentage() {
		return coveragePercentage;
	}
	public void setCoveragePercentage(String coveragePercentage) {
		this.coveragePercentage = coveragePercentage;
	}
	public String getAdHocCoverageAmount() {
		return adHocCoverageAmount;
	}
	public void setAdHocCoverageAmount(String adHocCoverageAmount) {
		this.adHocCoverageAmount = adHocCoverageAmount;
	}

}
