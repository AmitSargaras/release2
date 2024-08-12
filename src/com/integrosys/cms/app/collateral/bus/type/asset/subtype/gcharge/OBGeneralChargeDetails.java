package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.vo.ILeadBankStock;

public class OBGeneralChargeDetails implements IGeneralChargeDetails {
	
	private long generalChargeDetailsID = ICMSConstant.LONG_MIN_VALUE;
	private long cmsCollatralId = ICMSConstant.LONG_MIN_VALUE;
	
	private Date dueDate;
	private String docCode;
	
	private IGeneralChargeStockDetails[] generalChargeStockDetails;
	private String status;
	
	private String fundedShare;
	private String calculatedDP;
	private BigDecimal totalLoanableAmount;
	
	
	private String lastUpdatedBy;
	private Date lastUpdatedOn;
	private String lastApprovedBy;
	private Date lastApprovedOn;

	//Uma Khot:Cam upload and Dp field calculation CR
	private String dpShare;
	
	//Added only for display,this will be saved at child level
	private String location;
	private String locationDetail;
	
	//Start Santosh
	private String dpCalculateManually;
	
	private BigDecimal dpForCashFlowOrBudget;
	
	private BigDecimal actualDp;
	
	private List<ILeadBankStock> leadNodaBankStock;
	
	public String getDpCalculateManually() {
		return dpCalculateManually;
	}

	public void setDpCalculateManually(String dpCalculateManually) {
		this.dpCalculateManually = dpCalculateManually;
	}
	//End Santosh
	
	//Start Prachit 
	
	private String stockdocMonth;
	private String stockdocYear;
	
	private BigDecimal coverageAmount;
	private BigDecimal adHocCoverageAmount;
	private Double coveragePercentage;
		
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

	public long getGeneralChargeDetailsID() {
		return generalChargeDetailsID;
	}
	public long getCmsCollatralId() {
		return cmsCollatralId;
	}
	public void setCmsCollatralId(long cmsCollatralId) {
		this.cmsCollatralId = cmsCollatralId;
	}
	public void setGeneralChargeDetailsID(long generalChargeDetailsID) {
		this.generalChargeDetailsID = generalChargeDetailsID;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public IGeneralChargeStockDetails[] getGeneralChargeStockDetails() {
		return generalChargeStockDetails;
	}
	public void setGeneralChargeStockDetails(
			IGeneralChargeStockDetails[] generalChargeStockDetails) {
		this.generalChargeStockDetails = generalChargeStockDetails;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLocationDetail() {
		return locationDetail;
	}
	public void setLocationDetail(String locationDetail) {
		this.locationDetail = locationDetail;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDocCode() {
		return docCode;
	}
	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}
	public String getFundedShare() {
		return fundedShare;
	}
	public void setFundedShare(String fundedShare) {
		this.fundedShare = fundedShare;
	}
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
	public String getDpShare() {
		return dpShare;
	}
	public void setDpShare(String dpShare) {
		this.dpShare = dpShare;
	}
	public BigDecimal getDpForCashFlowOrBudget() {
		return dpForCashFlowOrBudget;
	}
	public void setDpForCashFlowOrBudget(BigDecimal dpForCashFlowOrBudget) {
		this.dpForCashFlowOrBudget = dpForCashFlowOrBudget;
	}
	public BigDecimal getActualDp() {
		return actualDp;
	}
	public void setActualDp(BigDecimal actualDp) {
		this.actualDp = actualDp;
	}
	public List<ILeadBankStock> getLeadBankStock() {
		return leadNodaBankStock;
	}
	public void setLeadBankStock(List<ILeadBankStock> leadNodaBankStock) {
		this.leadNodaBankStock = leadNodaBankStock;
	}
	public BigDecimal getTotalLoanableAmount() {
		return totalLoanableAmount;
	}
	public void setTotalLoanableAmount(BigDecimal totalLoanableAmount) {
		this.totalLoanableAmount = totalLoanableAmount;
	}

	public BigDecimal getCoverageAmount() {
		return coverageAmount;
	}

	public void setCoverageAmount(BigDecimal coverageAmount) {
		this.coverageAmount = coverageAmount;
	}

	public Double getCoveragePercentage() {
		return coveragePercentage;
	}

	public void setCoveragePercentage(Double coveragePercentage) {
		this.coveragePercentage = coveragePercentage;
	}

	public BigDecimal getAdHocCoverageAmount() {
		return adHocCoverageAmount;
	}

	public void setAdHocCoverageAmount(BigDecimal adHocCoverageAmount) {
		this.adHocCoverageAmount = adHocCoverageAmount;
	}

	
	private String remarkByMaker;
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
