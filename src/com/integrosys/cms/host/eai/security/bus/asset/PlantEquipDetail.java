package com.integrosys.cms.host.eai.security.bus.asset;

import java.io.Serializable;
import com.integrosys.cms.host.eai.StandardCode;

public class PlantEquipDetail implements Serializable {
	private String serialNo;

	private String invoiceNo;

	private StandardCode plantEquipType;

	private Long manufactureYear;

	private StandardCode model;
	
	private Double downPaymentTradeIn;
	
	private Double downPaymentCash;

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public StandardCode getPlantEquipType() {
		return plantEquipType;
	}

	public void setPlantEquipType(StandardCode plantEquipType) {
		this.plantEquipType = plantEquipType;
	}

	public Long getManufactureYear() {
		return manufactureYear;
	}

	public void setManufactureYear(Long manufactureYear) {
		this.manufactureYear = manufactureYear;
	}

	public StandardCode getModel() {
		return model;
	}

	public void setModel(StandardCode model) {
		this.model = model;
	}

	public Double getDownPaymentTradeIn() {
		return downPaymentTradeIn;
	}

	public void setDownPaymentTradeIn(Double downPaymentTradeIn) {
		this.downPaymentTradeIn = downPaymentTradeIn;
	}

	public Double getDownPaymentCash() {
		return downPaymentCash;
	}

	public void setDownPaymentCash(Double downPaymentCash) {
		this.downPaymentCash = downPaymentCash;
	}

}
