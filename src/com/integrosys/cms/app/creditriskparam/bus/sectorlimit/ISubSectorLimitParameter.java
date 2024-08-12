package com.integrosys.cms.app.creditriskparam.bus.sectorlimit;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ISubSectorLimitParameter extends Serializable {

    public Long getId();
    public void setId(Long id);
    
    public Long getMainSectorLimitId();
    public void setMainSectorLimitId(Long mainSectorLimitId);

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
    
    public Collection getEcoSectorList ();   
    public void setEcoSectorList (Collection ecoSectorList);
    
    public Set getEcoSectorSet ();
    public void setEcoSectorSet (Set ecoSectorSet);

}
