/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityprofile/item/CommProfileItemForm.java,v 1.3 2006/03/03 02:11:02 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityprofile.item;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * Description
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2006/03/03 02:11:02 $ Tag: $Name: $
 */

public class CommProfileItemForm extends CommonForm implements Serializable {
	private String commodityCategory = "";

	private String productType = "";

	private String productSubType = "";

	private String marketUOM = "";

	private String plusmn = "";

	private String commPriceDiff = "";

	private String priceType = "";

	// Option Futures
	private String marketName = "";

	private String ricFuturesChoice = "";

	private String ricFutures = "";

	private String ricFuturesOptions = "";

	// option Cash
	private String countryArea = "";

	private String outrights = "";

	private String chains = "";

	private String ricCash = "";

	// Non-RIC
	private String nonRICCode = "";

	private String nonRICDesc = "";

	private String country = "";

	private String supplier = "";

	private String buyer = "";

	private String[] supplierList;

	private String[] buyerList;

	public String getCommodityCategory() {
		return this.commodityCategory;
	}

	public void setCommodityCategory(String commodityCategory) {
		this.commodityCategory = commodityCategory;
	}

	public String getProductType() {
		return this.productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductSubType() {
		return this.productSubType;
	}

	public void setProductSubType(String productSubType) {
		this.productSubType = productSubType;
	}

	public String getMarketUOM() {
		return this.marketUOM;
	}

	public void setMarketUOM(String marketUOM) {
		this.marketUOM = marketUOM;
	}

	public String getPlusmn() {
		return this.plusmn;
	}

	public void setPlusmn(String plusmn) {
		this.plusmn = plusmn;
	}

	public String getCommPriceDiff() {
		return this.commPriceDiff;
	}

	public void setCommPriceDiff(String commPriceDiff) {
		this.commPriceDiff = commPriceDiff;
	}

	public String getMarketName() {
		return this.marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getRicFuturesChoice() {
		return ricFuturesChoice;
	}

	public void setRicFuturesChoice(String ricFuturesChoice) {
		this.ricFuturesChoice = ricFuturesChoice;
	}

	public String getRicFutures() {
		return ricFutures;
	}

	public void setRicFutures(String ricFutures) {
		this.ricFutures = ricFutures;
	}

	public String getRicFuturesOptions() {
		return ricFuturesOptions;
	}

	public void setRicFuturesOptions(String ricFuturesOptions) {
		this.ricFuturesOptions = ricFuturesOptions;
	}

	public String getCountryArea() {
		return countryArea;
	}

	public void setCountryArea(String countryArea) {
		this.countryArea = countryArea;
	}

	public String getOutrights() {
		return outrights;
	}

	public void setOutrights(String outrights) {
		this.outrights = outrights;
	}

	public String getChains() {
		return chains;
	}

	public void setChains(String chains) {
		this.chains = chains;
	}

	public String getRicCash() {
		return ricCash;
	}

	public void setRicCash(String ricCash) {
		this.ricCash = ricCash;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String[] getSupplierList() {
		return this.supplierList;
	}

	public void setSupplierList(String[] supplierList) {
		this.supplierList = supplierList;
	}

	public String[] getBuyerList() {
		return buyerList;
	}

	public void setBuyerList(String[] buyerList) {
		this.buyerList = buyerList;
	}

	public String getNonRICCode() {
		return nonRICCode;
	}

	public void setNonRICCode(String nonRICCode) {
		this.nonRICCode = nonRICCode;
	}

	public String getNonRICDesc() {
		return nonRICDesc;
	}

	public void setNonRICDesc(String nonRICDesc) {
		this.nonRICDesc = nonRICDesc;
	}

	public String[][] getMapper() {
		String[][] input = { { "commProfileItemObj",
				"com.integrosys.cms.ui.commodityglobal.commodityprofile.item.CommProfileItemMapper" }, };
		return input;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
