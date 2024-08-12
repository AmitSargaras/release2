package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.vo;

import java.util.Date;
import java.math.BigDecimal;

public class OBLeadBankStock implements ILeadBankStock {

	private static final long serialVersionUID = 1L;

	private Long id;
    private Date creationDate = new Date();
    private String createBy = "CMS";
    private Date lastUpdateDate = new Date();
    private String lastUpdateBy ="CMS";
    private long generalChargeDetailId;
	private BigDecimal drawingPowerAsPerLeadBank;
	private Double bankSharePercentage;
	private BigDecimal stockAmount;
	private BigDecimal creditorsAmount;
	private BigDecimal debtorsAmount;
	private BigDecimal marginOnStock;
	private BigDecimal marginOnCreditors;
	private BigDecimal marginOnDebtors;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public BigDecimal getDrawingPowerAsPerLeadBank() {
		return drawingPowerAsPerLeadBank;
	}
	public void setDrawingPowerAsPerLeadBank(BigDecimal drawingPowerAsPerLeadBank) {
		this.drawingPowerAsPerLeadBank = drawingPowerAsPerLeadBank;
	}
	public Double getBankSharePercentage() {
		return bankSharePercentage;
	}
	public void setBankSharePercentage(Double bankSharePercentage) {
		this.bankSharePercentage = bankSharePercentage;
	}
	public BigDecimal getStockAmount() {
		return stockAmount;
	}
	public void setStockAmount(BigDecimal stockAmount) {
		this.stockAmount = stockAmount;
	}
	public BigDecimal getCreditorsAmount() {
		return creditorsAmount;
	}
	public void setCreditorsAmount(BigDecimal creditorsAmount) {
		this.creditorsAmount = creditorsAmount;
	}
	public BigDecimal getDebtorsAmount() {
		return debtorsAmount;
	}
	public void setDebtorsAmount(BigDecimal debtorsAmount) {
		this.debtorsAmount = debtorsAmount;
	}
	public BigDecimal getMarginOnStock() {
		return marginOnStock;
	}
	public void setMarginOnStock(BigDecimal marginOnStock) {
		this.marginOnStock = marginOnStock;
	}
	public BigDecimal getMarginOnCreditors() {
		return marginOnCreditors;
	}
	public void setMarginOnCreditors(BigDecimal marginOnCreditors) {
		this.marginOnCreditors = marginOnCreditors;
	}
	public BigDecimal getMarginOnDebtors() {
		return marginOnDebtors;
	}
	public void setMarginOnDebtors(BigDecimal marginOnDebtors) {
		this.marginOnDebtors = marginOnDebtors;
	}
	public long getGeneralChargeDetailId() {
		return generalChargeDetailId;
	}
	public void setGeneralChargeDetailId(long generalChargeDetailId) {
		this.generalChargeDetailId = generalChargeDetailId;
	}

}
