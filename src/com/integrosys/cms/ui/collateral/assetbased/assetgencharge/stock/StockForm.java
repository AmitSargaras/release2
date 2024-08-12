/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/stock/StockForm.java,v 1.4 2005/04/06 06:51:08 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.stock;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/04/06 06:51:08 $ Tag: $Name: $
 */

public class StockForm extends CommonForm implements Serializable {
	private String[] stockItemDelete;

	private String[] insuranceItemDelete;

	private String totalGrossValCMS = "";

	private String totalGrossCreditor = "";

	private String totalNetValue = "";

	private String totalStockInsuredAmt = "";

	private String totalStockEffectiveAmt = "";

	private String totalInsuranceCover = "";

	private String totalInsuredAmt = "";

	private String totalValidCoverInsAmt = "";

	private String insCoverNum = "";

	private String insCoverUnit = "";

	private String cmsSecCurreny = "";

	private String valuationCMVStock = "";

	private String margin = "";

	private String valuationFSVStock = "";

	private String hasInsurance = "";

	public String[] getStockItemDelete() {
		return this.stockItemDelete;
	}

	public void setStockItemDelete(String[] stockItemDelete) {
		this.stockItemDelete = stockItemDelete;
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

	public String getValuationCMVStock() {
		return this.valuationCMVStock;
	}

	public void setValuationCMVStock(String valuationCMVStock) {
		this.valuationCMVStock = valuationCMVStock;
	}

	public String getMargin() {
		return this.margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}

	public String getValuationFSVStock() {
		return this.valuationFSVStock;
	}

	public void setValuationFSVStock(String valuationFSVStock) {
		this.valuationFSVStock = valuationFSVStock;
	}

	public String getTotalGrossValCMS() {
		return this.totalGrossValCMS;
	}

	public void setTotalGrossValCMS(String totalGrossValCMS) {
		this.totalGrossValCMS = totalGrossValCMS;
	}

	public String getTotalGrossCreditor() {
		return this.totalGrossCreditor;
	}

	public void setTotalGrossCreditor(String totalGrossCreditor) {
		this.totalGrossCreditor = totalGrossCreditor;
	}

	public String getTotalNetValue() {
		return this.totalNetValue;
	}

	public void setTotalNetValue(String totalNetValue) {
		this.totalNetValue = totalNetValue;
	}

	public String getTotalStockInsuredAmt() {
		return this.totalStockInsuredAmt;
	}

	public void setTotalStockInsuredAmt(String totalStockInsuredAmt) {
		this.totalStockInsuredAmt = totalStockInsuredAmt;
	}

	public String getTotalStockEffectiveAmt() {
		return this.totalStockEffectiveAmt;
	}

	public void setTotalStockEffectiveAmt(String totalStockEffectiveAmt) {
		this.totalStockEffectiveAmt = totalStockEffectiveAmt;
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
				"com.integrosys.cms.ui.collateral.assetbased.assetgencharge.stock.StockMapper" }, };
		return input;
	}
}
