package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public interface ILeadBankStock extends Serializable {

	public Long getId();
	public void setId(Long id);
	public Date getCreationDate();
	public void setCreationDate(Date creationDate);
	public String getCreateBy();
	public void setCreateBy(String createBy);	
	public Date getLastUpdateDate();
	public void setLastUpdateDate(Date lastUpdateDate);
	public String getLastUpdateBy();
	public void setLastUpdateBy(String lastUpdateBy);
	public long getGeneralChargeDetailId();
	public void setGeneralChargeDetailId(long generalChargeDetailId);
	public BigDecimal getDrawingPowerAsPerLeadBank();
	public void setDrawingPowerAsPerLeadBank(BigDecimal drawingPowerAsPerLeadBank);
	public Double getBankSharePercentage();
	public void setBankSharePercentage(Double bankSharePercentage);
	public BigDecimal getStockAmount();
	public void setStockAmount(BigDecimal stockAmount);
	public BigDecimal getCreditorsAmount();
	public void setCreditorsAmount(BigDecimal creditorsAmount);
	public BigDecimal getDebtorsAmount();
	public void setDebtorsAmount(BigDecimal debtorsAmount);
	public BigDecimal getMarginOnStock();
	public void setMarginOnStock(BigDecimal marginOnStock);
	public BigDecimal getMarginOnCreditors();
	public void setMarginOnCreditors(BigDecimal marginOnCreditors);
	public BigDecimal getMarginOnDebtors();
	public void setMarginOnDebtors(BigDecimal marginOnDebtors);
    
}
