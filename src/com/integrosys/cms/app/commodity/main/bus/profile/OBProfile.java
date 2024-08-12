/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/profile/OBProfile.java,v 1.7 2006/03/03 05:01:04 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.profile;

import java.math.BigDecimal;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.main.bus.OBCommodityMainInfo;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: dayanand Date: Mar 24, 2004 Time: 10:23:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class OBProfile extends OBCommodityMainInfo implements IProfile {
	private long profileID = ICMSConstant.LONG_INVALID_VALUE;

	private String category;

	private String productType;

	private String productSubType;

	private String differentialSign;

	private BigDecimal priceDifferential;

	private String marketName;

	public String priceType;

	public String RICType;

	private String reuterSymbol;

	public String countryArea;

	public String chains;

	public String outrights;

	private OBSupplier[] suppliers;

	private OBBuyer[] buyers;

	private long groupID = ICMSConstant.LONG_INVALID_VALUE;

	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;

	private Amount unitPrice;

	private Date unitPriceDate;

	private String nonRICDesc = "";

	public OBProfile() {
	}

	public OBProfile(IProfile iValue) {
		if (iValue != null) {
			AccessorUtil.copyValue(iValue, this);
		}
	}

	public long getProfileID() {
		return profileID;
	}

	public void setProfileID(long profileID) {
		this.profileID = profileID;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public String getCountryArea() {
		return countryArea;
	}

	public void setCountryArea(String countryArea) {
		this.countryArea = countryArea;
	}

	public String getReuterSymbol() {
		return reuterSymbol;
	}

	public void setReuterSymbol(String reuterSymbol) {
		this.reuterSymbol = reuterSymbol;
	}

	public String getDifferentialSign() {
		return differentialSign;
	}

	public void setDifferentialSign(String differentialSign) {
		this.differentialSign = differentialSign;
	}

	public BigDecimal getPriceDifferential() {
		return priceDifferential;
	}

	public void setPriceDifferential(BigDecimal priceDifferential) {
		this.priceDifferential = priceDifferential;
	}

	public String getMarketName() {
		return marketName;
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

	public String getRICType() {
		return RICType;
	}

	public void setRICType(String RICType) {
		this.RICType = RICType;
	}

	public String getChains() {
		return chains;
	}

	public void setChains(String chains) {
		this.chains = chains;
	}

	public String getOutrights() {
		return outrights;
	}

	public void setOutrights(String outrights) {
		this.outrights = outrights;
	}

	public ISupplier[] getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(OBSupplier[] suppliers) {
		this.suppliers = suppliers;
	}

	public void setSuppliers(ISupplier[] suppliers) {
		this.suppliers = (OBSupplier[]) suppliers;
	}

	public IBuyer[] getBuyers() {
		return buyers;
	}

	public void setBuyers(OBBuyer[] buyers) {
		this.buyers = buyers;
	}

	public void setBuyers(IBuyer[] buyers) {
		this.buyers = (OBBuyer[]) buyers;
	}

	public long getGroupID() {
		return groupID;
	}

	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}

	public long getCommonRef() {
		return commonRef;
	}

	public void setCommonRef(long commonRef) {
		this.commonRef = commonRef;
	}

	/**
	 * Get unit price.
	 * 
	 * @return Amount
	 */
	public Amount getUnitPrice() {
		return unitPrice;
	}

	/**
	 * Set unit price.
	 * 
	 * @param unitPrice of type Amount
	 */
	public void setUnitPrice(Amount unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * Get unit price date.
	 * 
	 * @return Date
	 */
	public Date getUnitPriceDate() {
		return unitPriceDate;
	}

	/**
	 * Set unit price date.
	 * 
	 * @param unitPriceDate of type Date
	 */
	public void setUnitPriceDate(Date unitPriceDate) {
		this.unitPriceDate = unitPriceDate;
	}

	public String getNonRICDesc() {
		return nonRICDesc;
	}

	public void setNonRICDesc(String nonRICDesc) {
		this.nonRICDesc = nonRICDesc;
	}
}
