/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/hedging/HedgingForm.java,v 1.3 2004/06/16 10:42:23 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance.hedging;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/06/16 10:42:23 $ Tag: $Name: $
 */

public class HedgingForm extends CommonForm implements Serializable {
	private String tpDealRefNo = "";

	private String globalTreasuryRefNo = "";

	private String dealdate = "";

	private String dealAmt = "";

	private String hedgeAgreeRef = "";

	private String hedgeAgreeDate = "";

	private String counterPartyName = "";

	private String margin = "";

	private String hedgingPriceCcy = "";

	private String hedgingPriceAmt = "";

	private String totalQtyGoods = "";

	private String hedgeQtyUOM = "";

	private String totalQtyGoodsHedge = "";

	private String percentageGoodsHedge = "";

	private String[] period = new String[0];

	private String[] periodUnit = new String[0];

	private String[] periodStartDate = new String[0];

	private String[] periodEndDate = new String[0];

	private String[] periodID = new String[0];

	private String[] deletePeriod = new String[0];

	public String getTpDealRefNo() {
		return tpDealRefNo;
	}

	public void setTpDealRefNo(String tpDealRefNo) {
		this.tpDealRefNo = tpDealRefNo;
	}

	public String getGlobalTreasuryRefNo() {
		return globalTreasuryRefNo;
	}

	public void setGlobalTreasuryRefNo(String globalTreasuryRefNo) {
		this.globalTreasuryRefNo = globalTreasuryRefNo;
	}

	public String getDealdate() {
		return dealdate;
	}

	public void setDealdate(String dealdate) {
		this.dealdate = dealdate;
	}

	public String getDealAmt() {
		return dealAmt;
	}

	public void setDealAmt(String dealAmt) {
		this.dealAmt = dealAmt;
	}

	public String getHedgeAgreeRef() {
		return hedgeAgreeRef;
	}

	public void setHedgeAgreeRef(String hedgeAgreeRef) {
		this.hedgeAgreeRef = hedgeAgreeRef;
	}

	public String getHedgeAgreeDate() {
		return hedgeAgreeDate;
	}

	public void setHedgeAgreeDate(String hedgeAgreeDate) {
		this.hedgeAgreeDate = hedgeAgreeDate;
	}

	public String getCounterPartyName() {
		return counterPartyName;
	}

	public void setCounterPartyName(String counterPartyName) {
		this.counterPartyName = counterPartyName;
	}

	public String getMargin() {
		return margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}

	public String getHedgingPriceCcy() {
		return hedgingPriceCcy;
	}

	public void setHedgingPriceCcy(String hedgingPriceCcy) {
		this.hedgingPriceCcy = hedgingPriceCcy;
	}

	public String getHedgingPriceAmt() {
		return hedgingPriceAmt;
	}

	public void setHedgingPriceAmt(String hedgingPriceAmt) {
		this.hedgingPriceAmt = hedgingPriceAmt;
	}

	public String getTotalQtyGoods() {
		return totalQtyGoods;
	}

	public void setTotalQtyGoods(String totalQtyGoods) {
		this.totalQtyGoods = totalQtyGoods;
	}

	public String getHedgeQtyUOM() {
		return hedgeQtyUOM;
	}

	public void setHedgeQtyUOM(String hedgeQtyUOM) {
		this.hedgeQtyUOM = hedgeQtyUOM;
	}

	public String getTotalQtyGoodsHedge() {
		return totalQtyGoodsHedge;
	}

	public void setTotalQtyGoodsHedge(String totalQtyGoodsHedge) {
		this.totalQtyGoodsHedge = totalQtyGoodsHedge;
	}

	public String getPercentageGoodsHedge() {
		return percentageGoodsHedge;
	}

	public void setPercentageGoodsHedge(String percentageGoodsHedge) {
		this.percentageGoodsHedge = percentageGoodsHedge;
	}

	public String[] getPeriod() {
		return period;
	}

	public void setPeriod(String[] period) {
		this.period = period;
	}

	public String[] getPeriodUnit() {
		return periodUnit;
	}

	public void setPeriodUnit(String[] periodUnit) {
		this.periodUnit = periodUnit;
	}

	public String[] getPeriodStartDate() {
		return periodStartDate;
	}

	public void setPeriodStartDate(String[] periodStartDate) {
		this.periodStartDate = periodStartDate;
	}

	public String[] getPeriodEndDate() {
		return periodEndDate;
	}

	public void setPeriodEndDate(String[] periodEndDate) {
		this.periodEndDate = periodEndDate;
	}

	public String[] getPeriodID() {
		return periodID;
	}

	public void setPeriodID(String[] periodID) {
		this.periodID = periodID;
	}

	public String[] getDeletePeriod() {
		return deletePeriod;
	}

	public void setDeletePeriod(String[] deletePeriod) {
		this.deletePeriod = deletePeriod;
	}

	public String[][] getMapper() {
		String[][] input = { { "hedgingObj", "com.integrosys.cms.ui.commoditydeal.finance.hedging.HedgingMapper" }, };
		return input;
	}
}
