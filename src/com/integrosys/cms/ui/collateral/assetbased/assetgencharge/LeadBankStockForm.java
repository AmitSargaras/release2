package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.cms.ui.collateral.CollateralConstant;

public class LeadBankStockForm extends CommonForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public String[][] getMapper(){	
        String[][] input =
            {
            	{CollateralConstant.LEAD_BANK_STOCK_KEY, LeadBankStockMapper.class.getName()},
            };
        return input;
    }
	
	private String drawingPowerAsPerLeadBank;
	private String bankSharePercentage;
	private String stockAmount;
	private String creditorsAmount;
	private String debtorAmount;
	private String marginOnStock;
	private String marginOnCreditors;
	private String marginOnDebtor;
	private String isLeadBankStockBankingArr;
	
	public String getDrawingPowerAsPerLeadBank() {
		return drawingPowerAsPerLeadBank;
	}
	public void setDrawingPowerAsPerLeadBank(String drawingPowerAsPerLeadBank) {
		this.drawingPowerAsPerLeadBank = drawingPowerAsPerLeadBank;
	}
	public String getBankSharePercentage() {
		return bankSharePercentage;
	}
	public void setBankSharePercentage(String bankSharePercentage) {
		this.bankSharePercentage = bankSharePercentage;
	}
	public String getStockAmount() {
		return stockAmount;
	}
	public void setStockAmount(String stockAmount) {
		this.stockAmount = stockAmount;
	}
	public String getCreditorsAmount() {
		return creditorsAmount;
	}
	public void setCreditorsAmount(String creditorsAmount) {
		this.creditorsAmount = creditorsAmount;
	}
	public String getDebtorAmount() {
		return debtorAmount;
	}
	public void setDebtorAmount(String debtorAmount) {
		this.debtorAmount = debtorAmount;
	}
	public String getMarginOnStock() {
		return marginOnStock;
	}
	public void setMarginOnStock(String marginOnStock) {
		this.marginOnStock = marginOnStock;
	}
	public String getMarginOnCreditors() {
		return marginOnCreditors;
	}
	public void setMarginOnCreditors(String marginOnCreditors) {
		this.marginOnCreditors = marginOnCreditors;
	}
	public String getMarginOnDebtor() {
		return marginOnDebtor;
	}
	public void setMarginOnDebtor(String marginOnDebtor) {
		this.marginOnDebtor = marginOnDebtor;
	}
	public String getIsLeadBankStockBankingArr() {
		return isLeadBankStockBankingArr;
	}
	public void setIsLeadBankStockBankingArr(String isLeadBankStockBankingArr) {
		this.isLeadBankStockBankingArr = isLeadBankStockBankingArr;
	}
	
}
