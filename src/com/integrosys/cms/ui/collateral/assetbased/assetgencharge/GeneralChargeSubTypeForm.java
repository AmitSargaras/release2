/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/GeneralChargeSubTypeForm.java,v 1.1 2005/03/18 03:34:38 lini Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * For GeneralChargeSubTypeForm
 * 
 * @author $Author: lini $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/03/18 03:34:38 $ Tag: $Name: $
 */
public class GeneralChargeSubTypeForm extends CommonForm implements Serializable {
	private String address = "";

	private String valuer = "";

	private String valuationDate = "";

	private String stdRevalFreq = "";

	private String nonStdRevalFreqNum = "";

	private String nonStdRevalFreqUnit = "";

	private String revaluationDate = "";

	private String valCurrency = "";

	private String grossValueValCurr = "";

	private String cmsSecCurrency = "";

	private String grossValueCMSCurr = "";

	private String margin = "";

	private String netValue = "";

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getValuer() {
		return this.valuer;
	}

	public void setValuer(String valuer) {
		this.valuer = valuer;
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

	public String getValCurrency() {
		return this.valCurrency;
	}

	public void setValCurrency(String valCurrency) {
		this.valCurrency = valCurrency;
	}

	public String getGrossValueValCurr() {
		return this.grossValueValCurr;
	}

	public void setGrossValueValCurr(String grossValueValCurr) {
		this.grossValueValCurr = grossValueValCurr;
	}

	public String getCmsSecCurrency() {
		return this.cmsSecCurrency;
	}

	public void setCmsSecCurrency(String cmsSecCurrency) {
		this.cmsSecCurrency = cmsSecCurrency;
	}

	public String getGrossValueCMSCurr() {
		return this.grossValueCMSCurr;
	}

	public void setGrossValueCMSCurr(String grossValueCMSCurr) {
		this.grossValueCMSCurr = grossValueCMSCurr;
	}

	public String getMargin() {
		return this.margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}

	public String getNetValue() {
		return this.netValue;
	}

	public void setNetValue(String netValue) {
		this.netValue = netValue;
	}

}
