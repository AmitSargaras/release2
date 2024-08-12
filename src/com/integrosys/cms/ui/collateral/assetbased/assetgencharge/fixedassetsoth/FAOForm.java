/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/fixedassetsoth/FAOForm.java,v 1.3 2005/04/06 06:50:59 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.fixedassetsoth;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/04/06 06:50:59 $ Tag: $Name: $
 */

public class FAOForm extends CommonForm implements Serializable {
	private String[] faoItemDelete;

	private String[] insuranceItemDelete;

	private String totalInsuredAmt = "";

	private String totalValidCoverInsAmt = "";

	private String insCoverNum = "";

	private String insCoverUnit = "";

	private String cmsSecCurreny = "";

	private String valuationCMVFAO = "";

	private String margin = "";

	private String valuationFSVFAO = "";

	private String totalGrossValCMS = "";

	private String totalNetValue = "";

	private String totalFAOInsuredAmt = "";

	private String totalFAOEffectiveAmt = "";

	private String totalInsuranceCover = "";

	private String hasInsurance = "";

	public String[] getFaoItemDelete() {
		return this.faoItemDelete;
	}

	public void setFaoItemDelete(String[] faoItemDelete) {
		this.faoItemDelete = faoItemDelete;
	}

	public String[] getInsuranceItemDelete() {
		return this.insuranceItemDelete;
	}

	public void setInsuranceItemDelete(String[] insuranceItemDelete) {
		this.insuranceItemDelete = insuranceItemDelete;
	}

	public String getTotalInsuredAmt() {
		return this.totalInsuredAmt;
	}

	public void setTotalInsuredAmt(String totalInsuredAmt) {
		this.totalInsuredAmt = totalInsuredAmt;
	}

	public String getTotalValidCoverInsAmt() {
		return this.totalValidCoverInsAmt;
	}

	public void setTotalValidCoverInsAmt(String totalValidCoverInsAmt) {
		this.totalValidCoverInsAmt = totalValidCoverInsAmt;
	}

	public String getInsCoverNum() {
		return this.insCoverNum;
	}

	public void setInsCoverNum(String insCoverNum) {
		this.insCoverNum = insCoverNum;
	}

	public String getInsCoverUnit() {
		return this.insCoverUnit;
	}

	public void setInsCoverUnit(String insCoverUnit) {
		this.insCoverUnit = insCoverUnit;
	}

	public String getCmsSecCurreny() {
		return this.cmsSecCurreny;
	}

	public void setCmsSecCurreny(String cmsSecCurreny) {
		this.cmsSecCurreny = cmsSecCurreny;
	}

	public String getValuationCMVFAO() {
		return this.valuationCMVFAO;
	}

	public void setValuationCMVFAO(String valuationCMVFAO) {
		this.valuationCMVFAO = valuationCMVFAO;
	}

	public String getMargin() {
		return this.margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}

	public String getValuationFSVFAO() {
		return this.valuationFSVFAO;
	}

	public void setValuationFSVFAO(String valuationFSVFAO) {
		this.valuationFSVFAO = valuationFSVFAO;
	}

	public String getTotalGrossValCMS() {
		return this.totalGrossValCMS;
	}

	public void setTotalGrossValCMS(String totalGrossValCMS) {
		this.totalGrossValCMS = totalGrossValCMS;
	}

	public String getTotalNetValue() {
		return this.totalNetValue;
	}

	public void setTotalNetValue(String totalNetValue) {
		this.totalNetValue = totalNetValue;
	}

	public String getTotalFAOInsuredAmt() {
		return this.totalFAOInsuredAmt;
	}

	public void setTotalFAOInsuredAmt(String totalFAOInsuredAmt) {
		this.totalFAOInsuredAmt = totalFAOInsuredAmt;
	}

	public String getTotalFAOEffectiveAmt() {
		return this.totalFAOEffectiveAmt;
	}

	public void setTotalFAOEffectiveAmt(String totalFAOEffectiveAmt) {
		this.totalFAOEffectiveAmt = totalFAOEffectiveAmt;
	}

	public String getTotalInsuranceCover() {
		return this.totalInsuranceCover;
	}

	public void setTotalInsuranceCover(String totalInsuranceCover) {
		this.totalInsuranceCover = totalInsuranceCover;
	}

	public String getHasInsurance() {
		return this.hasInsurance;
	}

	public void setHasInsurance(String hasInsurance) {
		this.hasInsurance = hasInsurance;
	}

	public String[][] getMapper() {
		String[][] input = { { "form.collateralObject",
				"com.integrosys.cms.ui.collateral.assetbased.assetgencharge.fixedassetsoth.FAOMapper" }, };
		return input;
	}
}
