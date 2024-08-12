package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import com.integrosys.base.uiinfra.common.CommonForm;

public class DueDateAndStockSummaryForm extends CommonForm {

	private static final long serialVersionUID = 1L;
	
	private String dueDate;
	private String statementName;
	private String dpCalcManually;
	private String dpShare;
	private String dpAsPerStockStatement;
	private String dpForCashFlowOrBudget;
	private String gcDetailId;
	
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
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
	public String getGcDetailId() {
		return gcDetailId;
	}
	public void setGcDetailId(String gcDetailId) {
		this.gcDetailId = gcDetailId;
	}
	
}