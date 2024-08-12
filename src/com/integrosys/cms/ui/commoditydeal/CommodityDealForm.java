/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/CommodityDealForm.java,v 1.7 2004/08/24 04:04:42 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2004/08/24 04:04:42 $ Tag: $Name: $
 */

public class CommodityDealForm extends TrxContextForm implements Serializable {

	private String securityID = "";

	private String securitySubType = "";

	private String productType = "";

	private String productSubType = "";

	private String marketPrice = "";

	private String commodityDifferential = "";

	private String uomValue = "";

	private String uomUnit = "";

	private String limitID = "";

	private String activatedLimit = "";

	private String proposedFaceValueAmt = "";

	private String proposedFaceValueCcy = "";

	private String percentageFinancing = "";

	private String proposedDealAmt = "";

	private String balanceProposedFaceVal = "";

	private String outstandingOpLimit = "";

	private String collateralPosition = "";

	public String getSecurityID() {
		return securityID;
	}

	public void setSecurityID(String securityID) {
		this.securityID = securityID;
	}

	public String getSecuritySubType() {
		return securitySubType;
	}

	public void setSecuritySubType(String securitySubType) {
		this.securitySubType = securitySubType;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductSubType() {
		return productSubType;
	}

	public void setProductSubType(String productSubType) {
		this.productSubType = productSubType;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getCommodityDifferential() {
		return commodityDifferential;
	}

	public void setCommodityDifferential(String commodityDifferential) {
		this.commodityDifferential = commodityDifferential;
	}

	public String getUomValue() {
		return uomValue;
	}

	public void setUomValue(String uomValue) {
		this.uomValue = uomValue;
	}

	public String getUomUnit() {
		return uomUnit;
	}

	public void setUomUnit(String uomUnit) {
		this.uomUnit = uomUnit;
	}

	public String getLimitID() {
		return limitID;
	}

	public void setLimitID(String limitID) {
		this.limitID = limitID;
	}

	public String getActivatedLimit() {
		return activatedLimit;
	}

	public void setActivatedLimit(String activatedLimit) {
		this.activatedLimit = activatedLimit;
	}

	public String getProposedFaceValueAmt() {
		return proposedFaceValueAmt;
	}

	public void setProposedFaceValueAmt(String proposedFaceValueAmt) {
		this.proposedFaceValueAmt = proposedFaceValueAmt;
	}

	public String getProposedFaceValueCcy() {
		return proposedFaceValueCcy;
	}

	public void setProposedFaceValueCcy(String proposedFaceValueCcy) {
		this.proposedFaceValueCcy = proposedFaceValueCcy;
	}

	public String getPercentageFinancing() {
		return percentageFinancing;
	}

	public void setPercentageFinancing(String percentageFinancing) {
		this.percentageFinancing = percentageFinancing;
	}

	public String getProposedDealAmt() {
		return proposedDealAmt;
	}

	public void setProposedDealAmt(String proposedDealAmt) {
		this.proposedDealAmt = proposedDealAmt;
	}

	public String getBalanceProposedFaceVal() {
		return balanceProposedFaceVal;
	}

	public void setBalanceProposedFaceVal(String balanceProposedFaceVal) {
		this.balanceProposedFaceVal = balanceProposedFaceVal;
	}

	public String getOutstandingOpLimit() {
		return outstandingOpLimit;
	}

	public void setOutstandingOpLimit(String outstandingOpLimit) {
		this.outstandingOpLimit = outstandingOpLimit;
	}

	public String getCollateralPosition() {
		return collateralPosition;
	}

	public void setCollateralPosition(String collateralPosition) {
		this.collateralPosition = collateralPosition;
	}

	public String[][] getMapper() {
		String[][] input = { { "commodityDealObj", "com.integrosys.cms.ui.commoditydeal.CommodityDealMapper" },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" }, };
		return input;
	}
}
