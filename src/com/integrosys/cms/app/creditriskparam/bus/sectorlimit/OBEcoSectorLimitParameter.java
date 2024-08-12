package com.integrosys.cms.app.creditriskparam.bus.sectorlimit;

import com.integrosys.base.techinfra.util.AccessorUtil;

public class OBEcoSectorLimitParameter implements IEcoSectorLimitParameter{

	private static final long serialVersionUID = 1L;
	
    private Long id;
    private Long subSectorLimitId;
	private String loanPurposeCode;
    private Double limitPercentage = null;
    private Double conventionalBankPercentage = null;
    private Double islamicBankPercentage = null;
    private Double investmentBankPercentage = null;
    private String sectorCode;
    private String status;
    private long cmsRefId = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

    public OBEcoSectorLimitParameter() {
        super();
    }

    public OBEcoSectorLimitParameter(IEcoSectorLimitParameter ecoSectorLimitParameter) {
        this();
        AccessorUtil.copyValue(ecoSectorLimitParameter, this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getSubSectorLimitId() {
        return subSectorLimitId;
    }

    public void setSubSectorLimitId (Long subSectorLimitId) {
        this.subSectorLimitId = subSectorLimitId;
    }

    public String getLoanPurposeCode() {
        return loanPurposeCode;
    }

    public void setLoanPurposeCode(String loanPurposeCode) {
        this.loanPurposeCode = loanPurposeCode;
    }

    public Double getLimitPercentage() {
        return limitPercentage;
    }

    public void setLimitPercentage(Double limitPercentage) {
        this.limitPercentage = limitPercentage;
    }
    
    public Double getConventionalBankPercentage() {
        return conventionalBankPercentage;
    }

    public void setConventionalBankPercentage(Double conventionalBankPercentage) {
        this.conventionalBankPercentage = conventionalBankPercentage;
    }
    
    public Double getIslamicBankPercentage() {
        return islamicBankPercentage;
    }

    public void setIslamicBankPercentage(Double islamicBankPercentage) {
        this.islamicBankPercentage = islamicBankPercentage;
    }
    
    public Double getInvestmentBankPercentage() {
        return investmentBankPercentage;
    }

    public void setInvestmentBankPercentage(Double investmentBankPercentage) {
        this.investmentBankPercentage = investmentBankPercentage;
    }
    
    public String getSectorCode() {
		return sectorCode;
	}

	public void setSectorCode(String sectorCode) {
		this.sectorCode = sectorCode;	
	}
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public long getCmsRefId() {
        return cmsRefId;
    }

    public void setCmsRefId(long cmsRefId) {
        this.cmsRefId = cmsRefId;
    }

    public boolean equals (Object obj)
    {
        if (obj == null)
            return false;
        else if (!(obj instanceof OBEcoSectorLimitParameter))
            return false;
        else {
            if (obj.hashCode() == this.hashCode())
                return true;
            else
                return false;
        }
    }

	
}

