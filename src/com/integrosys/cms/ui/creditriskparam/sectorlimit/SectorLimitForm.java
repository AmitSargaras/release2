package com.integrosys.cms.ui.creditriskparam.sectorlimit;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Author: Syukri
 * Date: Jun 5, 2008
 */
public class SectorLimitForm extends TrxContextForm implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private String sectorCode = "";
    private String currencyCode = "";
    private String limitAmt = "";
    private String reviewDate = "";
    private String limitPercentage = "";
    private String conventionalBankPercentage = "";
    private String islamicBankPercentage = "";
    private String investmentBankPercentage = "";
    private String secCode = "";
    
    private String[] deleteItems;
    
    public String[] getDeleteItems() {
		return deleteItems;
	}

	public void setDeleteItems(String[] deleteItems) {
		this.deleteItems = deleteItems;
	}

	public String getSectorCode() {
        return sectorCode;
    }

    public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }

    public String getLimitAmt() {
        return limitAmt;
    }

    public void setLimitAmt(String limitAmt) {
        this.limitAmt = limitAmt;
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
    
    public String getSecCode() {
        return secCode;
    }

    public void setSecCode(String secCode) {
        this.secCode = secCode;
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
            {"SectorLimitForm", "com.integrosys.cms.ui.creditriskparam.sectorlimit.SectorLimitMapper"},
            {"theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper"}
        };
        return input;
    }

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
}
