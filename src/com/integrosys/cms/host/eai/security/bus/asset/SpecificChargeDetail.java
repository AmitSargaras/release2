package com.integrosys.cms.host.eai.security.bus.asset;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.support.MessageDate;

public class SpecificChargeDetail implements Serializable {

	private static final long serialVersionUID = 172643186670316438L;

	private StandardCode assetType;

	private Double purchasePrice;

	private Date purchaseDate;

	private Date registrationDate;

	private Long manufactureYear;

	private StandardCode make;

	private StandardCode model;

	private Double salesProceed;

	private String registrationNo;

	private Double registrationFee;

	private StandardCode goodStatus;

	private Date repossessionDate;

	private String publicTransport;

	private Date dateChattelSold;

	private String rLSerialNo;

	private Double residualScrapValue;

	private String yard;

	private StandardCode insurers;

	private Double downPaymentTradeIn;

	private Double downPaymentCash;

	public StandardCode getAssetType() {
		return assetType;
	}

	public String getDateChattelSold() {
		return MessageDate.getInstance().getString(this.dateChattelSold);
	}

	public Double getDownPaymentCash() {
		return downPaymentCash;
	}

	public Double getDownPaymentTradeIn() {
		return downPaymentTradeIn;
	}

	public StandardCode getGoodStatus() {
		return goodStatus;
	}

	public StandardCode getInsurers() {
		return insurers;
	}

	public Date getJDODateChattelSold() {
		return dateChattelSold;
	}

	public Date getJDOPurchaseDate() {
		return purchaseDate;
	}

	public Date getJDORegistrationDate() {
		return registrationDate;
	}

	public Date getJDORepossessionDate() {
		return repossessionDate;
	}

	public StandardCode getMake() {
		return make;
	}

	public Long getManufactureYear() {
		return manufactureYear;
	}

	public StandardCode getModel() {
		return model;
	}

	public String getPublicTransport() {
		return publicTransport;
	}

	public String getPurchaseDate() {
		return MessageDate.getInstance().getString(this.purchaseDate);
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}

	public String getRegistrationDate() {
		return MessageDate.getInstance().getString(this.registrationDate);
	}

	public Double getRegistrationFee() {
		return registrationFee;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public String getRepossessionDate() {
		return MessageDate.getInstance().getString(this.repossessionDate);
	}

	public Double getResidualScrapValue() {
		return residualScrapValue;
	}

	public String getRLSerialNo() {
		return rLSerialNo;
	}

	public Double getSalesProceed() {
		return salesProceed;
	}

	public String getYard() {
		return yard;
	}

	public void setAssetType(StandardCode assetType) {
		this.assetType = assetType;
	}

	public void setDateChattelSold(String dateChattelSold) {
		this.dateChattelSold = MessageDate.getInstance().getDate(dateChattelSold);
	}

	public void setDownPaymentCash(Double downPaymentCash) {
		this.downPaymentCash = downPaymentCash;
	}

	public void setDownPaymentTradeIn(Double downPaymentTradeIn) {
		this.downPaymentTradeIn = downPaymentTradeIn;
	}

	public void setGoodStatus(StandardCode goodStatus) {
		this.goodStatus = goodStatus;
	}

	public void setInsurers(StandardCode insurers) {
		this.insurers = insurers;
	}

	public void setJDODateChattelSold(Date dateChattelSold) {
		this.dateChattelSold = dateChattelSold;
	}

	public void setJDOPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public void setJDORegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public void setJDORepossessionDate(Date repossessionDate) {
		this.repossessionDate = repossessionDate;
	}

	public void setMake(StandardCode make) {
		this.make = make;
	}

	public void setManufactureYear(Long manufactureYear) {
		this.manufactureYear = manufactureYear;
	}

	public void setModel(StandardCode model) {
		this.model = model;
	}

	public void setPublicTransport(String publicTransport) {
		this.publicTransport = publicTransport;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = MessageDate.getInstance().getDate(purchaseDate);
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = MessageDate.getInstance().getDate(registrationDate);
	}

	public void setRegistrationFee(Double registrationFee) {
		this.registrationFee = registrationFee;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public void setRepossessionDate(String repossessionDate) {
		this.repossessionDate = MessageDate.getInstance().getDate(repossessionDate);
	}

	public void setResidualScrapValue(Double residualScrapValue) {
		this.residualScrapValue = residualScrapValue;
	}

	public void setRLSerialNo(String serialNo) {
		rLSerialNo = serialNo;
	}

	public void setSalesProceed(Double salesProceed) {
		this.salesProceed = salesProceed;
	}

	public void setYard(String yard) {
		this.yard = yard;
	}

}
