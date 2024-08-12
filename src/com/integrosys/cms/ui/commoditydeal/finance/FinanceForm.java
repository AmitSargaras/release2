/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/FinanceForm.java,v 1.8 2004/09/08 01:50:47 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2004/09/08 01:50:47 $ Tag: $Name: $
 */

public class FinanceForm extends TrxContextForm implements Serializable {

	private String goodsDesc = "";

	private String supplierName = "";

	private String buyerName = "";

	private String purchaseValue = "";

	private String salesValue = "";

	private String hedgingPrice = "";

	private String totalQtyGoodsHedge = "";

	private String margin = "";

	private String hedgeMarketPrice = "";

	private String hedgeProfitLoss = "";

	private String totalSettlePayment = "";

	private String settleBalanceOutstanding = "";

	private String totalSettleQtyRel = "";

	private String actualQty = "";

	private String settleQtyBalanceOutstanding = "";

	private String faceValue = "";

	private String valPostAdvRate = "";

	private String originalFaceValue = "";

	private String percentageFinancing = "";

	private String dealFinanceAmt = "";

	private String actualDealCMV = "";

	private String actualDealFSV = "";

	private String dealCMV = "";

	private String dealFSV = "";

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getPurchaseValue() {
		return purchaseValue;
	}

	public void setPurchaseValue(String purchaseValue) {
		this.purchaseValue = purchaseValue;
	}

	public String getSalesValue() {
		return salesValue;
	}

	public void setSalesValue(String salesValue) {
		this.salesValue = salesValue;
	}

	public String getHedgingPrice() {
		return hedgingPrice;
	}

	public void setHedgingPrice(String hedgingPrice) {
		this.hedgingPrice = hedgingPrice;
	}

	public String getMargin() {
		return margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}

	public String getTotalQtyGoodsHedge() {
		return totalQtyGoodsHedge;
	}

	public void setTotalQtyGoodsHedge(String totalQtyGoodsHedge) {
		this.totalQtyGoodsHedge = totalQtyGoodsHedge;
	}

	public String getHedgeMarketPrice() {
		return hedgeMarketPrice;
	}

	public void setHedgeMarketPrice(String hedgeMarketPrice) {
		this.hedgeMarketPrice = hedgeMarketPrice;
	}

	public String getHedgeProfitLoss() {
		return hedgeProfitLoss;
	}

	public void setHedgeProfitLoss(String hedgeProfitLoss) {
		this.hedgeProfitLoss = hedgeProfitLoss;
	}

	public String getSettleQtyBalanceOutstanding() {
		return settleQtyBalanceOutstanding;
	}

	public void setSettleQtyBalanceOutstanding(String settleQtyBalanceOutstanding) {
		this.settleQtyBalanceOutstanding = settleQtyBalanceOutstanding;
	}

	public String getTotalSettlePayment() {
		return totalSettlePayment;
	}

	public void setTotalSettlePayment(String totalSettlePayment) {
		this.totalSettlePayment = totalSettlePayment;
	}

	public String getSettleBalanceOutstanding() {
		return settleBalanceOutstanding;
	}

	public void setSettleBalanceOutstanding(String settleBalanceOutstanding) {
		this.settleBalanceOutstanding = settleBalanceOutstanding;
	}

	public String getTotalSettleQtyRel() {
		return totalSettleQtyRel;
	}

	public void setTotalSettleQtyRel(String totalSettleQtyRel) {
		this.totalSettleQtyRel = totalSettleQtyRel;
	}

	public String getActualQty() {
		return actualQty;
	}

	public void setActualQty(String actualQty) {
		this.actualQty = actualQty;
	}

	public String getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(String faceValue) {
		this.faceValue = faceValue;
	}

	public String getValPostAdvRate() {
		return valPostAdvRate;
	}

	public void setValPostAdvRate(String valPostAdvRate) {
		this.valPostAdvRate = valPostAdvRate;
	}

	public String getOriginalFaceValue() {
		return originalFaceValue;
	}

	public void setOriginalFaceValue(String originalFaceValue) {
		this.originalFaceValue = originalFaceValue;
	}

	public String getPercentageFinancing() {
		return percentageFinancing;
	}

	public void setPercentageFinancing(String percentageFinancing) {
		this.percentageFinancing = percentageFinancing;
	}

	public String getDealFinanceAmt() {
		return dealFinanceAmt;
	}

	public void setDealFinanceAmt(String dealFinanceAmt) {
		this.dealFinanceAmt = dealFinanceAmt;
	}

	public String getActualDealCMV() {
		return actualDealCMV;
	}

	public void setActualDealCMV(String actualDealCMV) {
		this.actualDealCMV = actualDealCMV;
	}

	public String getActualDealFSV() {
		return actualDealFSV;
	}

	public void setActualDealFSV(String actualDealFSV) {
		this.actualDealFSV = actualDealFSV;
	}

	public String getDealCMV() {
		return dealCMV;
	}

	public void setDealCMV(String dealCMV) {
		this.dealCMV = dealCMV;
	}

	public String getDealFSV() {
		return dealFSV;
	}

	public void setDealFSV(String dealFSV) {
		this.dealFSV = dealFSV;
	}

	public String[][] getMapper() {
		String[][] input = { { "financeObj", "com.integrosys.cms.ui.commoditydeal.finance.FinanceMapper" }, };
		return input;
	}
}
