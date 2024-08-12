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
 * This class represents Vehicle Detail of Asset Based.
 * 
 * @author Phoon Sai Heng
 * @author Chong Jun Yong
 */
public class VehicleDetail implements Serializable {

	private static final long serialVersionUID = 7242300478554535150L;

	private String chassisNumber;

	private String pBTPBRInd;

	private Long PBTPBRPeriod;

	private String logBookNo;

	private StandardCode dealerName;

	private Double priceList;

	private String engineNumber;

	private String transmissionType;

	private StandardCode energySource;

	private String energyCapacity;

	private String vehicleColour;

	private String eHakMilikNum;

	private Double downPaymentTradeIn;

	private Double downPaymentCash;

	private String yardWarehouseStore;

	private String roadTaxAmtType;

	private Double roadTaxAmt;

	private Date roadTaxExpiryDate;

	private Double freightCharges;

	private StandardCode vehicleType;

	private String registrationNo;
	
	private String invoiceNumber;

	public String getChassisNumber() {
		return chassisNumber;
	}

	public StandardCode getDealerName() {
		return dealerName;
	}

	public Double getDownPaymentCash() {
		return downPaymentCash;
	}

	public Double getDownPaymentTradeIn() {
		return downPaymentTradeIn;
	}

	public String getEHakMilikNum() {
		return eHakMilikNum;
	}

	public String getEnergyCapacity() {
		return energyCapacity;
	}

	public StandardCode getEnergySource() {
		return energySource;
	}

	public String getEngineNumber() {
		return engineNumber;
	}

	public Double getFreightCharges() {
		return freightCharges;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public Date getJDORoadTaxExpiryDate() {
		return roadTaxExpiryDate;
	}

	public String getLogBookNo() {
		return logBookNo;
	}

	public String getPBTPBRInd() {
		return pBTPBRInd;
	}

	public Long getPBTPBRPeriod() {
		return PBTPBRPeriod;
	}

	public Double getPriceList() {
		return priceList;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public Double getRoadTaxAmt() {
		return roadTaxAmt;
	}

	public String getRoadTaxAmtType() {
		return roadTaxAmtType;
	}

	public String getRoadTaxExpiryDate() {
		return MessageDate.getInstance().getString(this.roadTaxExpiryDate);
	}

	public String getTransmissionType() {
		return transmissionType;
	}

	public String getVehicleColour() {
		return vehicleColour;
	}

	public StandardCode getVehicleType() {
		return vehicleType;
	}

	public String getYardWarehouseStore() {
		return yardWarehouseStore;
	}

	public void setChassisNumber(String chassisNumber) {
		this.chassisNumber = chassisNumber;
	}

	public void setDealerName(StandardCode dealerName) {
		this.dealerName = dealerName;
	}

	public void setDownPaymentCash(Double downPaymentCash) {
		this.downPaymentCash = downPaymentCash;
	}

	public void setDownPaymentTradeIn(Double downPaymentTradeIn) {
		this.downPaymentTradeIn = downPaymentTradeIn;
	}

	public void setEHakMilikNum(String hakMilikNum) {
		eHakMilikNum = hakMilikNum;
	}

	public void setEnergyCapacity(String energyCapacity) {
		this.energyCapacity = energyCapacity;
	}

	public void setEnergySource(StandardCode energySource) {
		this.energySource = energySource;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	public void setFreightCharges(Double freightCharges) {
		this.freightCharges = freightCharges;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public void setJDORoadTaxExpiryDate(Date roadTaxExpiryDate) {
		this.roadTaxExpiryDate = roadTaxExpiryDate;
	}

	public void setLogBookNo(String logBookNo) {
		this.logBookNo = logBookNo;
	}

	public void setPBTPBRInd(String ind) {
		pBTPBRInd = ind;
	}

	public void setPBTPBRPeriod(Long PBTPBRPeriod) {
		this.PBTPBRPeriod = PBTPBRPeriod;
	}

	public void setPriceList(Double priceList) {
		this.priceList = priceList;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public void setRoadTaxAmt(Double roadTaxAmt) {
		this.roadTaxAmt = roadTaxAmt;
	}

	public void setRoadTaxAmtType(String roadTaxAmtType) {
		this.roadTaxAmtType = roadTaxAmtType;
	}

	public void setRoadTaxExpiryDate(String roadTaxExpiryDate) {
		this.roadTaxExpiryDate = MessageDate.getInstance().getDate(roadTaxExpiryDate);
	}

	public void setTransmissionType(String transmissionType) {
		this.transmissionType = transmissionType;
	}

	public void setVehicleColour(String vehicleColour) {
		this.vehicleColour = vehicleColour;
	}

	public void setVehicleType(StandardCode vehicleType) {
		this.vehicleType = vehicleType;
	}

	public void setYardWarehouseStore(String yardWarehouseStore) {
		this.yardWarehouseStore = yardWarehouseStore;
	}

}
