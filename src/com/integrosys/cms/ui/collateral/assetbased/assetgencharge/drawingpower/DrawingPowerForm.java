/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/drawingpower/DrawingPowerForm.java,v 1.11 2005/08/27 03:53:12 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.drawingpower;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2005/08/27 03:53:12 $ Tag: $Name: $
 */

public class DrawingPowerForm extends CommonForm implements Serializable {

	// stock summary
	private String grossStockVal = "";

	private String totalCreditors = "";

	private String grossStockValLessCreditors = "";

	private String netValStocksMargin = "";

	private String totalValidInsrStocks = "";

	private String totalRecoverInsrAmt = "";

	private String insrShortfallVsStocks = "";

	// debtor summary
	private String grossDebtors = "";

	private String overdue = "";

	private String grossDebtorsLessOverdue = "";

	private String negativeStockValue = "";

	private String applicableDebtors = "";

	private String netDebtors = "";

	// fao summary
	private String grossFAO = "";

	private String netFAOMargin = "";

	// FSV, Drawing Power W and W/O netting insurance common
	private String bankParticipatingShare = "";

	private String crpMargin = "";

	private String evalDateFSV = "";

	private String otherRemarks = "";

	private String valuationCurrency = "";

	// FSV Calculation
	private String totalStockDebtorsFAO = "";

	private String bankTotalStockDebtorsFAO = "";

	private String totalNetFSV = "";

	// Drawing power W and W/o netting insurance common
	private String totalMaxApplApprLimit = "";

	private String totalExistReleasedLimit = "";

	// drawing power calculation
	private String grossAmtDrawingPower = "";

	private String bankGrossAmtDrawingPower = "";

	private String netAmtDrawingPower = "";

	private String amtToReleased = "";

	private String deficit = "";

	// Drawing Power (Without netting insurance) calculation
	private String grossAmtDrawingPowerWOIns = "";

	private String bankGrossAmtDrawingPowerWOIns = "";

	private String netAmtDrawingPowerWOIns = "";

	private String amtToReleasedWOIns = "";

	private String deficitWOIns = "";

	// limit details
	private String[] approvedLimit;

	private String[] appliedActivatedLimitBase;

	private String[] appliedActivatedLimitCMS;

	private String[] releasedLimitBase;

	private String[] releasedLimitCMS;

	private String[] limitID;

	private String totalApprovedLimit = "";

	private String totalAppliedActLimit = "";

	private String totalReleasedLimit = "";

	public String getAmtToReleased() {
		return amtToReleased;
	}

	public void setAmtToReleased(String amtToReleased) {
		this.amtToReleased = amtToReleased;
	}

	public String getBankParticipatingShare() {
		return bankParticipatingShare;
	}

	public void setBankParticipatingShare(String bankParticipatingShare) {
		this.bankParticipatingShare = bankParticipatingShare;
	}

	public String getBankTotalStockDebtorsFAO() {
		return bankTotalStockDebtorsFAO;
	}

	public void setBankTotalStockDebtorsFAO(String bankTotalStockDebtorsFAO) {
		this.bankTotalStockDebtorsFAO = bankTotalStockDebtorsFAO;
	}

	public String getCrpMargin() {
		return crpMargin;
	}

	public void setCrpMargin(String crpMargin) {
		this.crpMargin = crpMargin;
	}

	public String getGrossDebtors() {
		return grossDebtors;
	}

	public void setGrossDebtors(String grossDebtors) {
		this.grossDebtors = grossDebtors;
	}

	public String getGrossFAO() {
		return grossFAO;
	}

	public void setGrossFAO(String grossFAO) {
		this.grossFAO = grossFAO;
	}

	public String getGrossStockVal() {
		return grossStockVal;
	}

	public void setGrossStockVal(String grossStockVal) {
		this.grossStockVal = grossStockVal;
	}

	public String getGrossStockValLessCreditors() {
		return grossStockValLessCreditors;
	}

	public void setGrossStockValLessCreditors(String grossStockValLessCreditors) {
		this.grossStockValLessCreditors = grossStockValLessCreditors;
	}

	public String getInsrShortfallVsStocks() {
		return insrShortfallVsStocks;
	}

	public void setInsrShortfallVsStocks(String insrShortfallVsStocks) {
		this.insrShortfallVsStocks = insrShortfallVsStocks;
	}

	public String getNetDebtors() {
		return netDebtors;
	}

	public void setNetDebtors(String netDebtors) {
		this.netDebtors = netDebtors;
	}

	public String getNetFAOMargin() {
		return netFAOMargin;
	}

	public void setNetFAOMargin(String netFAOMargin) {
		this.netFAOMargin = netFAOMargin;
	}

	public String getNetValStocksMargin() {
		return netValStocksMargin;
	}

	public void setNetValStocksMargin(String netValStocksMargin) {
		this.netValStocksMargin = netValStocksMargin;
	}

	public String getDeficit() {
		return this.deficit;
	}

	public void setDeficit(String deficit) {
		this.deficit = deficit;
	}

	public String getEvalDateFSV() {
		return this.evalDateFSV;
	}

	public void setEvalDateFSV(String evalDateFSV) {
		this.evalDateFSV = evalDateFSV;
	}

	public String getOtherRemarks() {
		return otherRemarks;
	}

	public void setOtherRemarks(String otherRemarks) {
		this.otherRemarks = otherRemarks;
	}

	public String getOverdue() {
		return overdue;
	}

	public void setOverdue(String overdue) {
		this.overdue = overdue;
	}

	public String getTotalCreditors() {
		return totalCreditors;
	}

	public void setTotalCreditors(String totalCreditors) {
		this.totalCreditors = totalCreditors;
	}

	public String getTotalExistReleasedLimit() {
		return totalExistReleasedLimit;
	}

	public void setTotalExistReleasedLimit(String totalExistReleasedLimit) {
		this.totalExistReleasedLimit = totalExistReleasedLimit;
	}

	public String getTotalMaxApplApprLimit() {
		return totalMaxApplApprLimit;
	}

	public void setTotalMaxApplApprLimit(String totalMaxApplApprLimit) {
		this.totalMaxApplApprLimit = totalMaxApplApprLimit;
	}

	public String getTotalRecoverInsrAmt() {
		return totalRecoverInsrAmt;
	}

	public void setTotalRecoverInsrAmt(String totalRecoverInsrAmt) {
		this.totalRecoverInsrAmt = totalRecoverInsrAmt;
	}

	public String getTotalStockDebtorsFAO() {
		return totalStockDebtorsFAO;
	}

	public void setTotalStockDebtorsFAO(String totalStockDebtorsFAO) {
		this.totalStockDebtorsFAO = totalStockDebtorsFAO;
	}

	public String getTotalValidInsrStocks() {
		return totalValidInsrStocks;
	}

	public void setTotalValidInsrStocks(String totalValidInsrStocks) {
		this.totalValidInsrStocks = totalValidInsrStocks;
	}

	public String getGrossAmtDrawingPower() {
		return this.grossAmtDrawingPower;
	}

	public void setGrossAmtDrawingPower(String grossAmtDrawingPower) {
		this.grossAmtDrawingPower = grossAmtDrawingPower;
	}

	public String getNetAmtDrawingPower() {
		return this.netAmtDrawingPower;
	}

	public void setNetAmtDrawingPower(String netAmtDrawingPower) {
		this.netAmtDrawingPower = netAmtDrawingPower;
	}

	public String[] getAppliedActivatedLimitBase() {
		return this.appliedActivatedLimitBase;
	}

	public String[] getApprovedLimit() {
		return this.approvedLimit;
	}

	public void setApprovedLimit(String[] approvedLimit) {
		this.approvedLimit = approvedLimit;
	}

	public void setAppliedActivatedLimitBase(String[] appliedActivatedLimitBase) {
		this.appliedActivatedLimitBase = appliedActivatedLimitBase;
	}

	public String[] getAppliedActivatedLimitCMS() {
		return this.appliedActivatedLimitCMS;
	}

	public void setAppliedActivatedLimitCMS(String[] appliedActivatedLimitCMS) {
		this.appliedActivatedLimitCMS = appliedActivatedLimitCMS;
	}

	public String[] getReleasedLimitBase() {
		return releasedLimitBase;
	}

	public void setReleasedLimitBase(String[] releasedLimitBase) {
		this.releasedLimitBase = releasedLimitBase;
	}

	public String[] getReleasedLimitCMS() {
		return releasedLimitCMS;
	}

	public void setReleasedLimitCMS(String[] releasedLimitCMS) {
		this.releasedLimitCMS = releasedLimitCMS;
	}

	public String[] getLimitID() {
		return this.limitID;
	}

	public void setLimitID(String[] limitID) {
		this.limitID = limitID;
	}

	public String getTotalAppliedActLimit() {
		return totalAppliedActLimit;
	}

	public void setTotalAppliedActLimit(String totalAppliedActLimit) {
		this.totalAppliedActLimit = totalAppliedActLimit;
	}

	public String getTotalReleasedLimit() {
		return totalReleasedLimit;
	}

	public void setTotalReleasedLimit(String totalReleasedLimit) {
		this.totalReleasedLimit = totalReleasedLimit;
	}

	public String getGrossDebtorsLessOverdue() {
		return this.grossDebtorsLessOverdue;
	}

	public void setGrossDebtorsLessOverdue(String grossDebtorsLessOverdue) {
		this.grossDebtorsLessOverdue = grossDebtorsLessOverdue;
	}

	public String getNegativeStockValue() {
		return this.negativeStockValue;
	}

	public void setNegativeStockValue(String negativeStockValue) {
		this.negativeStockValue = negativeStockValue;
	}

	public String getApplicableDebtors() {
		return this.applicableDebtors;
	}

	public void setApplicableDebtors(String applicableDebtors) {
		this.applicableDebtors = applicableDebtors;
	}

	public String getGrossAmtDrawingPowerWOIns() {
		return this.grossAmtDrawingPowerWOIns;
	}

	public void setGrossAmtDrawingPowerWOIns(String grossAmtDrawingPowerWOIns) {
		this.grossAmtDrawingPowerWOIns = grossAmtDrawingPowerWOIns;
	}

	public String getBankGrossAmtDrawingPower() {
		return this.bankGrossAmtDrawingPower;
	}

	public void setBankGrossAmtDrawingPower(String bankGrossAmtDrawingPower) {
		this.bankGrossAmtDrawingPower = bankGrossAmtDrawingPower;
	}

	public String getBankGrossAmtDrawingPowerWOIns() {
		return this.bankGrossAmtDrawingPowerWOIns;
	}

	public void setBankGrossAmtDrawingPowerWOIns(String bankGrossAmtDrawingPowerWOIns) {
		this.bankGrossAmtDrawingPowerWOIns = bankGrossAmtDrawingPowerWOIns;
	}

	public String getNetAmtDrawingPowerWOIns() {
		return this.netAmtDrawingPowerWOIns;
	}

	public void setNetAmtDrawingPowerWOIns(String netAmtDrawingPowerWOIns) {
		this.netAmtDrawingPowerWOIns = netAmtDrawingPowerWOIns;
	}

	public String getAmtToReleasedWOIns() {
		return this.amtToReleasedWOIns;
	}

	public void setAmtToReleasedWOIns(String amtToReleasedWOIns) {
		this.amtToReleasedWOIns = amtToReleasedWOIns;
	}

	public String getDeficitWOIns() {
		return this.deficitWOIns;
	}

	public void setDeficitWOIns(String deficitWOIns) {
		this.deficitWOIns = deficitWOIns;
	}

	public String getTotalNetFSV() {
		return this.totalNetFSV;
	}

	public void setTotalNetFSV(String totalNetFSV) {
		this.totalNetFSV = totalNetFSV;
	}

	public String getTotalApprovedLimit() {
		return this.totalApprovedLimit;
	}

	public void setTotalApprovedLimit(String totalApprovedLimit) {
		this.totalApprovedLimit = totalApprovedLimit;
	}

	public String getValuationCurrency() {
		return this.valuationCurrency;
	}

	public void setValuationCurrency(String valuationCurrency) {
		this.valuationCurrency = valuationCurrency;
	}

	public String[][] getMapper() {
		String[][] input = { { "form.collateralObject",
				"com.integrosys.cms.ui.collateral.assetbased.assetgencharge.drawingpower.DrawingPowerMapper" }, };
		return input;
	}
}
