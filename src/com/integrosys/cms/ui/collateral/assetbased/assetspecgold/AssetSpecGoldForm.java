//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION

package com.integrosys.cms.ui.collateral.assetbased.assetspecgold;

import java.io.Serializable;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.ui.collateral.assetbased.AssetBasedForm;

/**
 * AssetSpecGoldForm
 * 
 * Describe this class. Purpose: AssetSpecGoldForm Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class AssetSpecGoldForm extends AssetBasedForm implements Serializable {

	public static final String ASSETSPECGOLDMAPPER = "com.integrosys.cms.ui.collateral.assetbased.assetspecgold.AssetSpecGoldMapper";

	public String goldGrade = "";

	public String purchaseReceiptNo = "";

	//public String goldUnitPrice = "";
	
	public String goldUnitPrice;

	public String certExpiryDate = "";

	public String goldWeight = "";

	public String goldUOM = "";

	public String auctionDate = "";

	public String auctionPrice = "";

	public String auctioneer = "";

	public String salesProceed = "";

	// public String depreciateRate = "";
	public String goldUnitPriceUOM = "";

	private String purchasePrice = "";

	private String descriptionOfAsset = "";
	
	private String goldUnitPriceCurrency;
	
	public String feedGoldUnitPrice;

	public String getFeedGoldUnitPrice() {
		return feedGoldUnitPrice;
	}

	public void setFeedGoldUnitPrice(String feedGoldUnitPrice) {
		this.feedGoldUnitPrice = feedGoldUnitPrice;
	}

	public String getGoldUnitPriceCurrency() {
		return goldUnitPriceCurrency;
	}

	public void setGoldUnitPriceCurrency(String goldUnitPriceCurrency) {
		this.goldUnitPriceCurrency = goldUnitPriceCurrency;
	}

	public String getPurchasePrice() {

		return this.purchasePrice;

	}

	public void setPurchasePrice(String purchasePrice) {

		this.purchasePrice = purchasePrice;

	}

	private String datePurchase = "";

	public String getDatePurchase() {

		return this.datePurchase;

	}

	public void setDatePurchase(String datePurchase) {

		this.datePurchase = datePurchase;

	}

	public String getGoldGrade() {
		return goldGrade;
	}

	public void setGoldGrade(String goldGrade) {
		this.goldGrade = goldGrade;
	}

	public String getPurchaseReceiptNo() {
		return purchaseReceiptNo;
	}

	public void setPurchaseReceiptNo(String purchaseReceiptNo) {
		this.purchaseReceiptNo = purchaseReceiptNo;
	}

	public String getGoldUnitPrice() {
		return goldUnitPrice;
	}

	public void setGoldUnitPrice(String goldUnitPrice) {
		this.goldUnitPrice = goldUnitPrice;
	}

	public String getCertExpiryDate() {
		return certExpiryDate;
	}

	public void setCertExpiryDate(String certExpiryDate) {
		this.certExpiryDate = certExpiryDate;
	}

	public String getGoldWeight() {
		return goldWeight;
	}

	public void setGoldWeight(String goldWeight) {
		this.goldWeight = goldWeight;
	}

	public String getGoldUOM() {
		return goldUOM;
	}

	public void setGoldUOM(String goldUOM) {
		this.goldUOM = goldUOM;
	}

	public String getAuctionDate() {
		return auctionDate;
	}

	public void setAuctionDate(String auctionDate) {
		this.auctionDate = auctionDate;
	}

	public String getAuctionPrice() {
		return auctionPrice;
	}

	public void setAuctionPrice(String auctionPrice) {
		this.auctionPrice = auctionPrice;
	}

	public String getAuctioneer() {
		return auctioneer;
	}

	public void setAuctioneer(String auctioneer) {
		this.auctioneer = auctioneer;
	}

	public String getSalesProceed() {
		return salesProceed;
	}

	public void setSalesProceed(String salesProceed) {
		this.salesProceed = salesProceed;
	}

	/*
	 * public String getDepreciateRate() { return depreciateRate; }
	 * 
	 * public void setDepreciateRate(String depreciateRate) {
	 * this.depreciateRate = depreciateRate; }
	 */

	public String getGoldUnitPriceUOM() {
		return goldUnitPriceUOM;
	}

	public void setGoldUnitPriceUOM(String goldUnitPriceUOM) {
		this.goldUnitPriceUOM = goldUnitPriceUOM;
	}

	public String getDescriptionOfAsset() {
		return descriptionOfAsset;
	}

	public void setDescriptionOfAsset(String descriptionOfAsset) {
		this.descriptionOfAsset = descriptionOfAsset;
	}

	public void reset() {

		super.reset();

	}

	public String[][] getMapper() {

		String[][] input = {

		{ "form.collateralObject", ASSETSPECGOLDMAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "pledgorRemove", "com.integrosys.cms.ui.collateral.pledgor.PledgorRemoveMapper" },
				{ "pledgeRemove", "com.integrosys.cms.ui.collateral.pledge.PledgeRemoveMapper" }, };

		return input;

	}

}
