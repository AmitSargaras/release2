package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

public class LeadBankStockSummaryForm extends CommonForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String drawingPowerAsPerLeadBank;
	private String stockAmount;
	private String bankSharePercentage;
	private String banktStockAction;
	
	public String getDrawingPowerAsPerLeadBank() {
		return drawingPowerAsPerLeadBank;
	}
	public void setDrawingPowerAsPerLeadBank(String drawingPowerAsPerLeadBank) {
		this.drawingPowerAsPerLeadBank = drawingPowerAsPerLeadBank;
	}
	public String getStockAmount() {
		return stockAmount;
	}
	public void setStockAmount(String stockAmount) {
		this.stockAmount = stockAmount;
	}
	public String getBankSharePercentage() {
		return bankSharePercentage;
	}
	public void setBankSharePercentage(String bankSharePercentage) {
		this.bankSharePercentage = bankSharePercentage;
	}
	public String getBanktStockAction() {
		return banktStockAction;
	}
	public void setBanktStockAction(String banktStockAction) {
		this.banktStockAction = banktStockAction;
	}
	

}
