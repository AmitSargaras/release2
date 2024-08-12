package com.integrosys.cms.ui.creditriskparam.sectorlimit.econsectorlimit;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

public class EconSectorLimitForm extends TrxContextForm implements Serializable {
	
	private final static long serialVersionUID = 1L;
	
    private String econSectorCode = "";
    private String secCode ="";
    private String limitPercentage = "";
    private String conventionalBankPercentage = "";
    private String islamicBankPercentage = "";
    private String investmentBankPercentage = "";
    
	public String getEconSectorCode() {
		return econSectorCode;
	}
	public void setEconSectorCode(String econSectorCode) {
		this.econSectorCode = econSectorCode;
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
	
	
    public String[][] getMapper() {
        String[][] input =
        {
            {"econSectorLmtForm", "com.integrosys.cms.ui.creditriskparam.sectorlimit.econsectorlimit.EconSectorLimitMapper"},
        };
        return input;
    }	
}
