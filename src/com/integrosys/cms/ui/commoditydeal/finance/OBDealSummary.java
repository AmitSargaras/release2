/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/OBDealSummary.java,v 1.1 2004/07/15 09:31:28 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/07/15 09:31:28 $ Tag: $Name: $
 */
public class OBDealSummary {

	public OBDealSummary() {
	}

	private String sn = "";

	private String type = "";

	private String commodityDesc = "";

	private String tpDealRef = "";

	private String dealNo = "";

	private String dealAmt = "";

	private String marketPrice = "";

	private String qty = "";

	private String dealCMV = "";

	private String dealFSV = "";

	private String dealSecureStatus = "";

	private String cashReqAmt = "";

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCommodityDesc() {
		return commodityDesc;
	}

	public void setCommodityDesc(String commodityDesc) {
		this.commodityDesc = commodityDesc;
	}

	public String getTpDealRef() {
		return tpDealRef;
	}

	public void setTpDealRef(String tpDealRef) {
		this.tpDealRef = tpDealRef;
	}

	public String getDealNo() {
		return dealNo;
	}

	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}

	public String getDealAmt() {
		return dealAmt;
	}

	public void setDealAmt(String dealAmt) {
		this.dealAmt = dealAmt;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
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

	public String getDealSecureStatus() {
		return dealSecureStatus;
	}

	public void setDealSecureStatus(String dealSecureStatus) {
		this.dealSecureStatus = dealSecureStatus;
	}

	public String getCashReqAmt() {
		return cashReqAmt;
	}

	public void setCashReqAmt(String cashReqAmt) {
		this.cashReqAmt = cashReqAmt;
	}
}
