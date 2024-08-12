package com.integrosys.cms.ui.creditriskparam.sectorlimit.subsectorlimit;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

public class SubSectorLimitForm extends TrxContextForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private String subSectorCode = "";
    private String currencyCode = "";
    private String limitAmt = "";
    private String reviewDate = "";
    private String secCode ="";
    private String limitPercentage = "";
    private String conventionalBankPercentage = "";
    private String islamicBankPercentage = "";
    private String investmentBankPercentage = "";
    
    private String[] deleteEcoItems;
    
    public String[] getDeleteEcoItems() {
		return deleteEcoItems;
	}

	public void setDeleteEcoItems(String[] deleteEcoItems) {
		this.deleteEcoItems = deleteEcoItems;
	}
	
    
	public String getSubSectorCode() {
		return subSectorCode;
	}
	public void setSubSectorCode(String subSectorCode) {
		this.subSectorCode = subSectorCode;
	}
	
	public String getSecCode() {
		return secCode;
	}
	public void setSecCode(String secCode) {
		this.secCode = secCode;
	}
	
	public String getLimitPercentage() {
        return limitPercentage;
    }

    public void setLimitPercentage(String limitPercentage) {
        this.limitPercentage = limitPercentage;
    }
    
    public String getConventionalBankPercentage() {
        return conventionalBankPercentage;
    }

    public void setConventionalBankPercentage(String conventionalBankPercentage) {
        this.conventionalBankPercentage = conventionalBankPercentage;
    }
    
    public String getIslamicBankPercentage() {
        return islamicBankPercentage;
    }

    public void setIslamicBankPercentage(String islamicBankPercentage) {
        this.islamicBankPercentage = islamicBankPercentage;
    }
    
    public String getInvestmentBankPercentage() {
        return investmentBankPercentage;
    }

    public void setInvestmentBankPercentage(String investmentBankPercentage) {
        this.investmentBankPercentage = investmentBankPercentage;
    }
	
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getLimitAmt() {
		return limitAmt;
	}
	public void setLimitAmt(String limitAmt) {
		this.limitAmt = limitAmt;
	}
	public String getReviewDate() {
		return reviewDate;
	}
	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}
	
    public String[][] getMapper() {
        String[][] input =
        {
            {"subSectorLmtForm", "com.integrosys.cms.ui.creditriskparam.sectorlimit.subsectorlimit.SubSectorLimitMapper"},
        };
        return input;
    }	
}
