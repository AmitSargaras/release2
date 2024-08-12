/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.host.eai.security.bus.asset;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * This class represents Gold Detail of Asset Based.
 * 
 * @author $Author: shphoon $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class GoldDetail implements Serializable {
	/**
	 * Default constructor.
	 */
	public GoldDetail() {
		super();
	}

	// Gold Detail
	private StandardCode goldGrade;

	private String purchaseReceiptNo;

	private Double goldUnitPrice;

	private StandardCode goldUnitPriceUOM;

	private Date certificateExpiryDate;

	private Double goldWeight;

	private StandardCode goldWeightUnit;

	private String remarks;

	private Date auctionDate;

	private Double auctionPrice;

	private String auctioneer;

	public StandardCode getGoldGrade() {
		return goldGrade;
	}

	public void setGoldGrade(StandardCode goldGrade) {
		this.goldGrade = goldGrade;
	}

	public String getPurchaseReceiptNo() {
		return purchaseReceiptNo;
	}

	public void setPurchaseReceiptNo(String purchaseReceiptNo) {
		this.purchaseReceiptNo = purchaseReceiptNo;
	}

	public Double getGoldUnitPrice() {
		return goldUnitPrice;
	}

	public void setGoldUnitPrice(Double goldUnitPrice) {
		this.goldUnitPrice = goldUnitPrice;
	}

	public StandardCode getGoldUnitPriceUOM() {
		return goldUnitPriceUOM;
	}

	public void setGoldUnitPriceUOM(StandardCode goldUnitPriceUOM) {
		this.goldUnitPriceUOM = goldUnitPriceUOM;
	}

	public String getCertificateExpiryDate() {
		return MessageDate.getInstance().getString(certificateExpiryDate);
	}

	public void setCertificateExpiryDate(String certificateExpiryDate) {
		this.certificateExpiryDate = MessageDate.getInstance().getDate(certificateExpiryDate);
	}

	public Date getJDOCertificateExpiryDate() {
		return certificateExpiryDate;
	}

	public void setJDOCertificateExpiryDate(Date certificateExpiryDate) {
		this.certificateExpiryDate = certificateExpiryDate;
	}

	public Double getGoldWeight() {
		return goldWeight;
	}

	public void setGoldWeight(Double goldWeight) {
		this.goldWeight = goldWeight;
	}

	public StandardCode getGoldWeightUnit() {
		return goldWeightUnit;
	}

	public void setGoldWeightUnit(StandardCode goldWeightUnit) {
		this.goldWeightUnit = goldWeightUnit;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAuctionDate() {
		return MessageDate.getInstance().getString(auctionDate);
	}

	public void setAuctionDate(String auctionDate) {
		this.auctionDate = MessageDate.getInstance().getDate(auctionDate);
	}

	public Date getJDOAuctionDate() {
		return auctionDate;
	}

	public void setJDOAuctionDate(Date auctionDate) {
		this.auctionDate = auctionDate;
	}

	public Double getAuctionPrice() {
		return auctionPrice;
	}

	public void setAuctionPrice(Double auctionPrice) {
		this.auctionPrice = auctionPrice;
	}

	public String getAuctioneer() {
		return auctioneer;
	}

	public void setAuctioneer(String auctioneer) {
		this.auctioneer = auctioneer;
	}

}
