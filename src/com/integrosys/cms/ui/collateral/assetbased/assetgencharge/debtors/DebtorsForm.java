/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/debtors/DebtorsForm.java,v 1.4 2005/08/12 11:16:03 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.debtors;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/08/12 11:16:03 $ Tag: $Name: $
 */

public class DebtorsForm extends CommonForm implements Serializable {
	private String[] debtorsAgeing = new String[13];

	private String[] debtorsAgeingCMS = new String[13];

	private String total = "";

	private String totalCMS = "";

	private String periodApplicable = "";

	private String totalDebtors = "";

	private String totalDebtorsCMS = "";

	private String valCurrency = "";

	private String cmsCurrency = "";

	private String negativeStockValue = "";

	private String applicableDebtors = "";

	private String margin = "";

	private String valuationFSV = "";

	private String valuationDate = "";

	private String stdRevalFreq = "";

	private String nonStdRevalFreqNum = "";

	private String nonStdRevalFreqUnit = "";

	private String revaluationDate = "";

	public String[] getDebtorsAgeing() {
		return this.debtorsAgeing;
	}

	public void setDebtorsAgeing(String[] debtorsAgeing) {
		this.debtorsAgeing = debtorsAgeing;
	}

	public String[] getDebtorsAgeingCMS() {
		return this.debtorsAgeingCMS;
	}

	public void setDebtorsAgeingCMS(String[] debtorsAgeingCMS) {
		this.debtorsAgeingCMS = debtorsAgeingCMS;
	}

	public String getTotal() {
		return this.total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getTotalCMS() {
		return this.totalCMS;
	}

	public void setTotalCMS(String totalCMS) {
		this.totalCMS = totalCMS;
	}

	public String getPeriodApplicable() {
		return this.periodApplicable;
	}

	public void setPeriodApplicable(String periodApplicable) {
		this.periodApplicable = periodApplicable;
	}

	public String getTotalDebtors() {
		return this.totalDebtors;
	}

	public void setTotalDebtors(String totalDebtors) {
		this.totalDebtors = totalDebtors;
	}

	public String getTotalDebtorsCMS() {
		return this.totalDebtorsCMS;
	}

	public void setTotalDebtorsCMS(String totalDebtorsCMS) {
		this.totalDebtorsCMS = totalDebtorsCMS;
	}

	public String getValCurrency() {
		return this.valCurrency;
	}

	public void setValCurrency(String valCurrency) {
		this.valCurrency = valCurrency;
	}

	public String getCmsCurrency() {
		return this.cmsCurrency;
	}

	public void setCmsCurrency(String cmsCurrency) {
		this.cmsCurrency = cmsCurrency;
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

	public String getMargin() {
		return this.margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}

	public String getValuationFSV() {
		return this.valuationFSV;
	}

	public void setValuationFSV(String valuationFSV) {
		this.valuationFSV = valuationFSV;
	}

	public String getValuationDate() {
		return this.valuationDate;
	}

	public void setValuationDate(String valuationDate) {
		this.valuationDate = valuationDate;
	}

	public String getStdRevalFreq() {
		return this.stdRevalFreq;
	}

	public void setStdRevalFreq(String stdRevalFreq) {
		this.stdRevalFreq = stdRevalFreq;
	}

	public String getNonStdRevalFreqNum() {
		return this.nonStdRevalFreqNum;
	}

	public void setNonStdRevalFreqNum(String nonStdRevalFreqNum) {
		this.nonStdRevalFreqNum = nonStdRevalFreqNum;
	}

	public String getNonStdRevalFreqUnit() {
		return this.nonStdRevalFreqUnit;
	}

	public void setNonStdRevalFreqUnit(String nonStdRevalFreqUnit) {
		this.nonStdRevalFreqUnit = nonStdRevalFreqUnit;
	}

	public String getRevaluationDate() {
		return this.revaluationDate;
	}

	public void setRevaluationDate(String revaluationDate) {
		this.revaluationDate = revaluationDate;
	}

	public String[][] getMapper() {
		String[][] input = { { "form.collateralObject",
				"com.integrosys.cms.ui.collateral.assetbased.assetgencharge.debtors.DebtorsMapper" }, };
		return input;
	}
}
