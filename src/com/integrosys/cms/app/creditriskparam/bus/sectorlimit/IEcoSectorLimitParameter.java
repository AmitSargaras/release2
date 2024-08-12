package com.integrosys.cms.app.creditriskparam.bus.sectorlimit;

import java.io.Serializable;

public interface IEcoSectorLimitParameter extends Serializable {

    public Long getId();
    public void setId(Long id);
    
    public Long getSubSectorLimitId();
    public void setSubSectorLimitId(Long subSectorLimitId);

    public String getLoanPurposeCode();
    public void setLoanPurposeCode(String loanPurposeCode);

    public Double getLimitPercentage();
    public void setLimitPercentage(Double limitPercentage);
     
    public Double getConventionalBankPercentage();
    public void setConventionalBankPercentage(Double conventionalBankPercentage);
    
    public Double getIslamicBankPercentage();
    public void setIslamicBankPercentage(Double islamicBankPercentage);
    
    public Double getInvestmentBankPercentage();
    public void setInvestmentBankPercentage(Double investmentBankPercentage);

    public String getSectorCode();
    public void setSectorCode(String sectorCode);
    
    public String getStatus();
    public void setStatus(String status);
    
    public long getCmsRefId();
    public void setCmsRefId(long cmsRefId);

}

