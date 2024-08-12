/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/dealinfo/DealInfoForm.java,v 1.6 2006/03/23 08:39:08 hmbao Exp $
 */
package com.integrosys.cms.ui.commoditydeal.dealinfo;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Description
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/03/23 08:39:08 $ Tag: $Name: $
 */

public class DealInfoForm extends TrxContextForm implements Serializable {
	// Contracted Deal
	private String conCommCategory = "";

	private String conCommProductType = "";

	private String conCommProductSubType = "";

	private String conCommPriceTypeChange = "";

	private String conCommPriceType = "";

	private String conRIC = "";

	private String conQtyVolume = "";

	private String conQtyUOM = "";

	private String conQtyMarketUnit = "";

	private String conQtyMetricUnit = "";

	private String conQtyDiffPlusmn = "";

	private String conQtyDiffUOM = "";

	private String conQtyDiffValue = "";

	private String conPriceDiffPlusmn = "";

	private String conPriceDiffCcy = "";

	private String conBuyerSellerAgreeDiff = "";

	private String conContractedPriceCcy = "";

	private String conContractedPriceAmt = "";

	// Actual Deal
	private String actQtyVolume = "";

	private String actQtyUOM = "";

	private String actQtyMarketUnit = "";

	private String actQtyMetricUnit = "";

	// price type - EOD
	private String actEODDate = "";

	private String actEODMarketPrice = "";

	private String actEODMarketPriceCcy = "";

	private String actEODMarketPriceAmt = "";

	private String actEODCustDiff = "";

	private String actEODCustDiffSign = "";

	private String actEODCommDiff = "";

	private String actEODCommDiffSign = "";

	private String actEODAdjustPrice = "";

	// price Type - Floating Futures Contract
	private String actFloatDate = "";

	private String actFloatMarketPrice = "";

	private String actFloatMarketPriceCcy = "";

	private String actFloatMarketPriceAmt = "";

	private String actFloatBuySellAgreeDiff = "";

	private String actFloatAdjustPrice = "";

	// price type - Fixed Futures Contract
	private String actFixBuySellFixPriceCcy = "";

	private String actFixBuySellFixPriceAmt = "";

	// price type - Non-RIC
	private String actNonRICDate = "";

	private String actNonRICMarketPrice = "";

	private String actNonRICMarketPriceCcy = "";

	private String actNonRICMarketPriceAmt = "";

	private String actNonRICCmdtDiff = "";

	private String actNonRICCmdtDiffSign = "";

	private String actNonRICAdjustPrice = "";

	public String getActNonRICAdjustPrice() {
		return actNonRICAdjustPrice;
	}

	public void setActNonRICAdjustPrice(String actNonRICAdjustPrice) {
		this.actNonRICAdjustPrice = actNonRICAdjustPrice;
	}

	public String getActNonRICDate() {
		return actNonRICDate;
	}

	public void setActNonRICDate(String actNonRICDate) {
		this.actNonRICDate = actNonRICDate;
	}

	public String getActNonRICMarketPrice() {
		return actNonRICMarketPrice;
	}

	public void setActNonRICMarketPrice(String actNonRICMarketPrice) {
		this.actNonRICMarketPrice = actNonRICMarketPrice;
	}

	public String getActNonRICMarketPriceAmt() {
		return actNonRICMarketPriceAmt;
	}

	public void setActNonRICMarketPriceAmt(String actNonRICMarketPriceAmt) {
		this.actNonRICMarketPriceAmt = actNonRICMarketPriceAmt;
	}

	public String getActNonRICMarketPriceCcy() {
		return actNonRICMarketPriceCcy;
	}

	public void setActNonRICMarketPriceCcy(String actNonRICMarketPriceCcy) {
		this.actNonRICMarketPriceCcy = actNonRICMarketPriceCcy;
	}

	public String getConCommCategory() {
		return conCommCategory;
	}

	public void setConCommCategory(String conCommCategory) {
		this.conCommCategory = conCommCategory;
	}

	public String getConCommProductType() {
		return conCommProductType;
	}

	public void setConCommProductType(String conCommProductType) {
		this.conCommProductType = conCommProductType;
	}

	public String getConCommProductSubType() {
		return conCommProductSubType;
	}

	public void setConCommProductSubType(String conCommProductSubType) {
		this.conCommProductSubType = conCommProductSubType;
	}

	public String getConCommPriceType() {
		return conCommPriceType;
	}

	public void setConCommPriceType(String conCommPriceType) {
		this.conCommPriceType = conCommPriceType;
	}

	public String getConCommPriceTypeChange() {
		return conCommPriceTypeChange;
	}

	public void setConCommPriceTypeChange(String conCommPriceTypeChange) {
		this.conCommPriceTypeChange = conCommPriceTypeChange;
	}

	public String getConRIC() {
		return conRIC;
	}

	public void setConRIC(String conRIC) {
		this.conRIC = conRIC;
	}

	public String getConQtyVolume() {
		return conQtyVolume;
	}

	public void setConQtyVolume(String conQtyVolume) {
		this.conQtyVolume = conQtyVolume;
	}

	public String getConQtyUOM() {
		return conQtyUOM;
	}

	public void setConQtyUOM(String conQtyUOM) {
		this.conQtyUOM = conQtyUOM;
	}

	public String getConQtyMarketUnit() {
		return conQtyMarketUnit;
	}

	public void setConQtyMarketUnit(String conQtyMarketUnit) {
		this.conQtyMarketUnit = conQtyMarketUnit;
	}

	public String getConQtyMetricUnit() {
		return conQtyMetricUnit;
	}

	public void setConQtyMetricUnit(String conQtyMetricUnit) {
		this.conQtyMetricUnit = conQtyMetricUnit;
	}

	public String getConQtyDiffPlusmn() {
		return conQtyDiffPlusmn;
	}

	public void setConQtyDiffPlusmn(String conQtyDiffPlusmn) {
		this.conQtyDiffPlusmn = conQtyDiffPlusmn;
	}

	public String getConQtyDiffUOM() {
		return conQtyDiffUOM;
	}

	public void setConQtyDiffUOM(String conQtyDiffUOM) {
		this.conQtyDiffUOM = conQtyDiffUOM;
	}

	public String getConQtyDiffValue() {
		return conQtyDiffValue;
	}

	public void setConQtyDiffValue(String conQtyDiffValue) {
		this.conQtyDiffValue = conQtyDiffValue;
	}

	public String getConPriceDiffPlusmn() {
		return conPriceDiffPlusmn;
	}

	public void setConPriceDiffPlusmn(String conPriceDiffPlusmn) {
		this.conPriceDiffPlusmn = conPriceDiffPlusmn;
	}

	public String getConPriceDiffCcy() {
		return conPriceDiffCcy;
	}

	public void setConPriceDiffCcy(String conPriceDiffCcy) {
		this.conPriceDiffCcy = conPriceDiffCcy;
	}

	public String getConBuyerSellerAgreeDiff() {
		return conBuyerSellerAgreeDiff;
	}

	public void setConBuyerSellerAgreeDiff(String conBuyerSellerAgreeDiff) {
		this.conBuyerSellerAgreeDiff = conBuyerSellerAgreeDiff;
	}

	public String getConContractedPriceCcy() {
		return conContractedPriceCcy;
	}

	public void setConContractedPriceCcy(String conContractedPriceCcy) {
		this.conContractedPriceCcy = conContractedPriceCcy;
	}

	public String getConContractedPriceAmt() {
		return conContractedPriceAmt;
	}

	public void setConContractedPriceAmt(String conContractedPriceAmt) {
		this.conContractedPriceAmt = conContractedPriceAmt;
	}

	public String getActQtyVolume() {
		return actQtyVolume;
	}

	public void setActQtyVolume(String actQtyVolume) {
		this.actQtyVolume = actQtyVolume;
	}

	public String getActQtyUOM() {
		return actQtyUOM;
	}

	public void setActQtyUOM(String actQtyUOM) {
		this.actQtyUOM = actQtyUOM;
	}

	public String getActQtyMarketUnit() {
		return actQtyMarketUnit;
	}

	public void setActQtyMarketUnit(String actQtyMarketUnit) {
		this.actQtyMarketUnit = actQtyMarketUnit;
	}

	public String getActQtyMetricUnit() {
		return actQtyMetricUnit;
	}

	public void setActQtyMetricUnit(String actQtyMetricUnit) {
		this.actQtyMetricUnit = actQtyMetricUnit;
	}

	public String getActEODDate() {
		return actEODDate;
	}

	public void setActEODDate(String actEODDate) {
		this.actEODDate = actEODDate;
	}

	public String getActEODMarketPrice() {
		return actEODMarketPrice;
	}

	public void setActEODMarketPrice(String actEODMarketPrice) {
		this.actEODMarketPrice = actEODMarketPrice;
	}

	public String getActEODMarketPriceCcy() {
		return actEODMarketPriceCcy;
	}

	public void setActEODMarketPriceCcy(String actEODMarketPriceCcy) {
		this.actEODMarketPriceCcy = actEODMarketPriceCcy;
	}

	public String getActEODMarketPriceAmt() {
		return actEODMarketPriceAmt;
	}

	public void setActEODMarketPriceAmt(String actEODMarketPriceAmt) {
		this.actEODMarketPriceAmt = actEODMarketPriceAmt;
	}

	public String getActEODCustDiff() {
		return actEODCustDiff;
	}

	public void setActEODCustDiff(String actEODCustDiff) {
		this.actEODCustDiff = actEODCustDiff;
	}

	public String getActEODCustDiffSign() {
		return actEODCustDiffSign;
	}

	public void setActEODCustDiffSign(String actEODCustDiffSign) {
		this.actEODCustDiffSign = actEODCustDiffSign;
	}

	public String getActEODCommDiffSign() {
		return actEODCommDiffSign;
	}

	public void setActEODCommDiffSign(String actEODCommDiffSign) {
		this.actEODCommDiffSign = actEODCommDiffSign;
	}

	public String getActEODCommDiff() {
		return actEODCommDiff;
	}

	public void setActEODCommDiff(String actEODCommDiff) {
		this.actEODCommDiff = actEODCommDiff;
	}

	public String getActEODAdjustPrice() {
		return actEODAdjustPrice;
	}

	public void setActEODAdjustPrice(String actEODAdjustPrice) {
		this.actEODAdjustPrice = actEODAdjustPrice;
	}

	public String getActFloatDate() {
		return actFloatDate;
	}

	public void setActFloatDate(String actFloatDate) {
		this.actFloatDate = actFloatDate;
	}

	public String getActFloatMarketPrice() {
		return actFloatMarketPrice;
	}

	public void setActFloatMarketPrice(String actFloatMarketPrice) {
		this.actFloatMarketPrice = actFloatMarketPrice;
	}

	public String getActFloatMarketPriceCcy() {
		return actFloatMarketPriceCcy;
	}

	public void setActFloatMarketPriceCcy(String actFloatMarketPriceCcy) {
		this.actFloatMarketPriceCcy = actFloatMarketPriceCcy;
	}

	public String getActFloatMarketPriceAmt() {
		return actFloatMarketPriceAmt;
	}

	public void setActFloatMarketPriceAmt(String actFloatMarketPriceAmt) {
		this.actFloatMarketPriceAmt = actFloatMarketPriceAmt;
	}

	public String getActFloatBuySellAgreeDiff() {
		return actFloatBuySellAgreeDiff;
	}

	public void setActFloatBuySellAgreeDiff(String actFloatBuySellAgreeDiff) {
		this.actFloatBuySellAgreeDiff = actFloatBuySellAgreeDiff;
	}

	public String getActFloatAdjustPrice() {
		return actFloatAdjustPrice;
	}

	public void setActFloatAdjustPrice(String actFloatAdjustPrice) {
		this.actFloatAdjustPrice = actFloatAdjustPrice;
	}

	public String getActFixBuySellFixPriceCcy() {
		return actFixBuySellFixPriceCcy;
	}

	public void setActFixBuySellFixPriceCcy(String actFixBuySellFixPriceCcy) {
		this.actFixBuySellFixPriceCcy = actFixBuySellFixPriceCcy;
	}

	public String getActFixBuySellFixPriceAmt() {
		return actFixBuySellFixPriceAmt;
	}

	public void setActFixBuySellFixPriceAmt(String actFixBuySellFixPriceAmt) {
		this.actFixBuySellFixPriceAmt = actFixBuySellFixPriceAmt;
	}

	public String[][] getMapper() {
		String[][] input = { { "dealInfoObj", "com.integrosys.cms.ui.commoditydeal.dealinfo.DealInfoMapper" }, };
		return input;
	}

	public String getActNonRICCmdtDiff() {
		return actNonRICCmdtDiff;
	}

	public void setActNonRICCmdtDiff(String actNonRICCmdtDiff) {
		this.actNonRICCmdtDiff = actNonRICCmdtDiff;
	}

	public String getActNonRICCmdtDiffSign() {
		return actNonRICCmdtDiffSign;
	}

	public void setActNonRICCmdtDiffSign(String actNonRICCmdtDiffSign) {
		this.actNonRICCmdtDiffSign = actNonRICCmdtDiffSign;
	}
}
