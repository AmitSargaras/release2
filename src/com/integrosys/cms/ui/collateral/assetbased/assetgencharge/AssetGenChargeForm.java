//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION

package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.integrosys.cms.ui.collateral.SecurityCoverageForm;
import com.integrosys.cms.ui.collateral.assetbased.AssetBasedForm;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 2:56:38 PM
 * To change this template use Options | File Templates.
 */

public class AssetGenChargeForm extends AssetBasedForm implements Serializable {
	public static final String ASSETGENCHARGEMAPPER = "com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeMapper";

	private String userAccess = "false";
	
	private String dueDate;
	private String stockLocation;
	//required for validation
	private String isStockDetailsAdded;
	
//	private String fundedShare;
	private String calculatedDP;
	
	private String lastUpdatedBy;
	private Date lastUpdatedOn;
	private String lastApprovedBy;
	private Date lastApprovedOn;
	
	//Uma Khot:Cam upload and Dp field calculation CR
	private String dpShare;
	private String stockdocMonth;
	private String stockdocYear;
	
	private List<DueDateAndStockSummaryForm> dueDateAndStock;
	
	private SecurityCoverageForm securityCoverageForm;
	
	private String coverageAmount = StringUtils.EMPTY;
	private String adHocCoverageAmount = StringUtils.EMPTY;
	private String coveragePercentage = StringUtils.EMPTY;

	
	public String getStockdocMonth() {
		return stockdocMonth;
	}

	public void setStockdocMonth(String stockdocMonth) {
		this.stockdocMonth = stockdocMonth;
	}

	public String getStockdocYear() {
		return stockdocYear;
	}

	public void setStockdocYear(String stockdocYear) {
		this.stockdocYear = stockdocYear;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getStockLocation() {
		return stockLocation;
	}

	
	public String getIsStockDetailsAdded() {
		return isStockDetailsAdded;
	}

	public void setIsStockDetailsAdded(String isStockDetailsAdded) {
		this.isStockDetailsAdded = isStockDetailsAdded;
	}

	public void setStockLocation(String stockLocation) {
		this.stockLocation = stockLocation;
	}

	public String getUserAccess() {
		return this.userAccess;
	}

	public void setUserAccess(String userAccess) {
		this.userAccess = userAccess;
	}
	


//	public String getFundedShare() {
//		return fundedShare;
//	}
//
//	public void setFundedShare(String fundedShare) {
//		this.fundedShare = fundedShare;
//	}

	public String getCalculatedDP() {
		return calculatedDP;
	}

	public void setCalculatedDP(String calculatedDP) {
		this.calculatedDP = calculatedDP;
	}

	
	
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public String getLastApprovedBy() {
		return lastApprovedBy;
	}

	public void setLastApprovedBy(String lastApprovedBy) {
		this.lastApprovedBy = lastApprovedBy;
	}

	public Date getLastApprovedOn() {
		return lastApprovedOn;
	}

	public void setLastApprovedOn(Date lastApprovedOn) {
		this.lastApprovedOn = lastApprovedOn;
	}

	public void reset() {
		super.reset();
	}

	/*
	 * private String depreciateRate ;
	 * 
	 * public String getDepreciateRate() { return depreciateRate; }
	 * 
	 * public void setDepreciateRate(String depreciateRate) {
	 * this.depreciateRate = depreciateRate; }
	 */

	public String[][] getMapper() {

		String[][] input = { { "form.collateralObject", ASSETGENCHARGEMAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "pledgorRemove", "com.integrosys.cms.ui.collateral.pledgor.PledgorRemoveMapper" },
				{ "insuranceGCObj", "com.integrosys.cms.ui.collateral.InsuranceGCMapper" },
				{ "pledgeRemove", "com.integrosys.cms.ui.collateral.pledge.PledgeRemoveMapper" }, };
		return input;
	}
	
	//Uma Khot::Insurance Deferral maintainance
	private String actionType = "";

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getDpShare() {
		return dpShare;
	}
	public void setDpShare(String dpShare) {
		this.dpShare = dpShare;
	}

	public List<DueDateAndStockSummaryForm> getDueDateAndStock() {
		return dueDateAndStock;
	}

	public void setDueDateAndStock(List<DueDateAndStockSummaryForm> dueDateAndStock) {
		this.dueDateAndStock = dueDateAndStock;
	}

	public SecurityCoverageForm getSecurityCoverageForm() {
		return securityCoverageForm;
	}

	public void setSecurityCoverageForm(SecurityCoverageForm securityCoverageForm) {
		this.securityCoverageForm = securityCoverageForm;
	}

	public String getCoverageAmount() {
		return coverageAmount;
	}

	public void setCoverageAmount(String coverageAmount) {
		this.coverageAmount = coverageAmount;
	}

	public String getAdHocCoverageAmount() {
		return adHocCoverageAmount;
	}

	public void setAdHocCoverageAmount(String adHocCoverageAmount) {
		this.adHocCoverageAmount = adHocCoverageAmount;
	}

	public String getCoveragePercentage() {
		return coveragePercentage;
	}

	public void setCoveragePercentage(String coveragePercentage) {
		this.coveragePercentage = coveragePercentage;
	}
	
	private String remarkByMaker ;
	private String totalLoanable;
	private String migrationFlag_DP_CR;

	public String getRemarkByMaker() {
		return remarkByMaker;
	}

	public void setRemarkByMaker(String remarkByMaker) {
		this.remarkByMaker = remarkByMaker;
	}

	public String getTotalLoanable() {
		return totalLoanable;
	}

	public void setTotalLoanable(String totalLoanable) {
		this.totalLoanable = totalLoanable;
	}

	public String getMigrationFlag_DP_CR() {
		return migrationFlag_DP_CR;
	}

	public void setMigrationFlag_DP_CR(String migrationFlag_DP_CR) {
		this.migrationFlag_DP_CR = migrationFlag_DP_CR;
	}
	
	
	
}
