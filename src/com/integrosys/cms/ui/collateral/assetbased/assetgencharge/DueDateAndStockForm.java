package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.cms.ui.collateral.CollateralConstant;

public class DueDateAndStockForm extends CommonForm implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String[][] getMapper() {
		String[][] input = { { CollateralConstant.DUE_DATE_AND_STOCK_DETAILS_KEY,
			DueDateAndStockMapper.class.getName() } };
		return input;
	}
	
	private String dueDate;
	private String docCode;
	private String location;
	private String stockDocMonth;
	private String stockDocYear;
	private String statementName;
	private String dpCalcManually;
	private String dpShare;
	private String dpAsPerStockStatement;
	private String dpForCashFlowOrBudget;
	private String assetId;
	private String totalLoanableAmount;
	
	private String dueDateActual;
	private String stockDocMonthActual;
	private String stockDocYearActual;
	private String statementNameActual;
	private String dpCalcManuallyActual;
	private String dpShareActual;
	private String dpAsPerStockStatementActual;
	
	private List<AssetGenChargeStockDetailsHelperForm> stockSummaryForm;
	
	private String leadBankStockInd;
	private List<LeadBankStockSummaryForm> leadBankStockSummary;

	private String leadBankDp;
	private String avgLeadBankSharePercentage;
	private String actualDp;
	
	private String coverageAmount = StringUtils.EMPTY;
	private String adHocCoverageAmount = StringUtils.EMPTY;
	private String coveragePercentage = StringUtils.EMPTY;
	
	private String totalReleasedAmount;
	private String bankingArrangementVal;
	private String dpAsPerLeadAndNodal;
	private int leadBankStockSummaryCount;
	private String dueDateAlreadyReceived;
	private String dueDateActionPage;
	
	
	public String getDueDateActionPage() {
		return dueDateActionPage;
	}
	public void setDueDateActionPage(String dueDateActionPage) {
		this.dueDateActionPage = dueDateActionPage;
	}
	public String getDueDateAlreadyReceived() {
		return dueDateAlreadyReceived;
	}
	public void setDueDateAlreadyReceived(String dueDateAlreadyReceived) {
		this.dueDateAlreadyReceived = dueDateAlreadyReceived;
	}
	public int getLeadBankStockSummaryCount() {
		return leadBankStockSummaryCount;
	}
	public void setLeadBankStockSummaryCount(int leadBankStockSummaryCount) {
		this.leadBankStockSummaryCount = leadBankStockSummaryCount;
	}
	public String getBankingArrangementVal() {
		return bankingArrangementVal;
	}
	public void setBankingArrangementVal(String bankingArrangementVal) {
		this.bankingArrangementVal = bankingArrangementVal;
	}
	public String getDpAsPerLeadAndNodal() {
		return dpAsPerLeadAndNodal;
	}
	public void setDpAsPerLeadAndNodal(String dpAsPerLeadAndNodal) {
		this.dpAsPerLeadAndNodal = dpAsPerLeadAndNodal;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getDocCode() {
		return docCode;
	}
	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getStockDocMonth() {
		return stockDocMonth;
	}
	public void setStockDocMonth(String stockDocMonth) {
		this.stockDocMonth = stockDocMonth;
	}
	public String getStockDocYear() {
		return stockDocYear;
	}
	public void setStockDocYear(String stockDocYear) {
		this.stockDocYear = stockDocYear;
	}
	public String getStatementName() {
		return statementName;
	}
	public void setStatementName(String statementName) {
		this.statementName = statementName;
	}
	public String getDpCalcManually() {
		return dpCalcManually;
	}
	public void setDpCalcManually(String dpCalcManually) {
		this.dpCalcManually = dpCalcManually;
	}
	public String getDpShare() {
		return dpShare;
	}
	public void setDpShare(String dpShare) {
		this.dpShare = dpShare;
	}
	public String getDpAsPerStockStatement() {
		return dpAsPerStockStatement;
	}
	public void setDpAsPerStockStatement(String dpAsPerStockStatement) {
		this.dpAsPerStockStatement = dpAsPerStockStatement;
	}
	public String getDpForCashFlowOrBudget() {
		return dpForCashFlowOrBudget;
	}
	public void setDpForCashFlowOrBudget(String dpForCashFlowOrBudget) {
		this.dpForCashFlowOrBudget = dpForCashFlowOrBudget;
	}
	public String getAssetId() {
		return assetId;
	}
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}	
	public String getTotalLoanableAmount() {
		return totalLoanableAmount;
	}
	public void setTotalLoanableAmount(String totalLoanableAmount) {
		this.totalLoanableAmount = totalLoanableAmount;
	}
	public List<AssetGenChargeStockDetailsHelperForm> getStockSummaryForm() {
		return stockSummaryForm;
	}
	public void setStockSummaryForm(List<AssetGenChargeStockDetailsHelperForm> stockSummaryForm) {
		this.stockSummaryForm = stockSummaryForm;
	}
	public String getLeadBankStockInd() {
		return leadBankStockInd;
	}
	public void setLeadBankStockInd(String leadBankStockInd) {
		this.leadBankStockInd = leadBankStockInd;
	}
	public List<LeadBankStockSummaryForm> getLeadBankStockSummary() {
		return leadBankStockSummary;
	}
	public void setLeadBankStockSummary(List<LeadBankStockSummaryForm> leadBankStockSummary) {
		this.leadBankStockSummary = leadBankStockSummary;
	}
	public String getActualDp() {
		return actualDp;
	}
	public void setActualDp(String actualDp) {
		this.actualDp = actualDp;
	}
	public String getLeadBankDp() {
		return leadBankDp;
	}
	public void setLeadBankDp(String leadBankDp) {
		this.leadBankDp = leadBankDp;
	}
	public String getAvgLeadBankSharePercentage() {
		return avgLeadBankSharePercentage;
	}
	public void setAvgLeadBankSharePercentage(String avgLeadBankSharePercentage) {
		this.avgLeadBankSharePercentage = avgLeadBankSharePercentage;
	}
	public String getDueDateActual() {
		return dueDateActual;
	}
	public void setDueDateActual(String dueDateActual) {
		this.dueDateActual = dueDateActual;
	}
	public String getStockDocMonthActual() {
		return stockDocMonthActual;
	}
	public void setStockDocMonthActual(String stockDocMonthActual) {
		this.stockDocMonthActual = stockDocMonthActual;
	}
	public String getStockDocYearActual() {
		return stockDocYearActual;
	}
	public void setStockDocYearActual(String stockDocYearActual) {
		this.stockDocYearActual = stockDocYearActual;
	}
	public String getStatementNameActual() {
		return statementNameActual;
	}
	public void setStatementNameActual(String statementNameActual) {
		this.statementNameActual = statementNameActual;
	}
	public String getDpCalcManuallyActual() {
		return dpCalcManuallyActual;
	}
	public void setDpCalcManuallyActual(String dpCalcManuallyActual) {
		this.dpCalcManuallyActual = dpCalcManuallyActual;
	}
	public String getDpShareActual() {
		return dpShareActual;
	}
	public void setDpShareActual(String dpShareActual) {
		this.dpShareActual = dpShareActual;
	}
	public String getDpAsPerStockStatementActual() {
		return dpAsPerStockStatementActual;
	}
	public void setDpAsPerStockStatementActual(String dpAsPerStockStatementActual) {
		this.dpAsPerStockStatementActual = dpAsPerStockStatementActual;
	}
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
	public String getTotalReleasedAmount() {
		return totalReleasedAmount;
	}
	public void setTotalReleasedAmount(String totalReleasedAmount) {
		this.totalReleasedAmount = totalReleasedAmount;
	}
	public String getAdHocCoverageAmount() {
		return adHocCoverageAmount;
	}
	public void setAdHocCoverageAmount(String adHocCoverageAmount) {
		this.adHocCoverageAmount = adHocCoverageAmount;
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
	
	private String selectedArray;
	private String unCheckArray;

	public String getSelectedArray() {
		return selectedArray;
	}
	public void setSelectedArray(String selectedArray) {
		this.selectedArray = selectedArray;
	}
	public String getUnCheckArray() {
		return unCheckArray;
	}
	public void setUnCheckArray(String unCheckArray) {
		this.unCheckArray = unCheckArray;
	}
	
	

}
