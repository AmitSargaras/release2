package com.integrosys.cms.app.creditriskparam.bus.sectorlimit;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.ui.creditriskparam.sectorlimit.SectorLimitComparator;

import java.util.*;

/**
 * Author: Syukri
 * Date: Jun 2, 2008
 */
public class OBMainSectorLimitParameter implements IMainSectorLimitParameter{

    private static final long serialVersionUID = 1L;

    private Long id;
    private String loanPurposeCode;
    private Double limitPercentage = null;
    private Double conventionalBankPercentage = null;
    private Double islamicBankPercentage = null;
    private Double investmentBankPercentage = null;
    private String sectorCode;
    private String status;
    private long versionTime = ICMSConstant.LONG_INVALID_VALUE;
    private long cmsRefId = ICMSConstant.LONG_INVALID_VALUE;
    private Collection subSectorList;
    private Set subSectorSet;

    public OBMainSectorLimitParameter() {
        super();
    }

    public OBMainSectorLimitParameter(IMainSectorLimitParameter mainSectorLimitParameter) {
        this();
        AccessorUtil.copyValue(mainSectorLimitParameter, this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public long getVersionTime() {
        return versionTime;
    }

    public void setVersionTime(long versionTime) {
        this.versionTime = versionTime;
    }
    
    public long getCmsRefId() {
        return cmsRefId;
    }

    public void setCmsRefId(long cmsRefId) {
        this.cmsRefId = cmsRefId;
    }

    public Set getSubSectorSet() {
        return subSectorSet;
    }

    public void setSubSectorSet(Set subSectorSet) {
        this.subSectorSet = subSectorSet;
    }

    public Collection getSubSectorList() {
        return subSectorList;
    }

    public void setSubSectorList (Collection subSectorList) {
		this.subSectorList = subSectorList;
        if(subSectorList != null && subSectorList.size() > 0){
            Set subSectorSet = new HashSet(subSectorList);
            this.subSectorSet = subSectorSet;
        }else
            this.subSectorSet = null;
	}

    public boolean equals (Object obj)
    {
        if (obj == null)
            return false;
        else if (!(obj instanceof OBMainSectorLimitParameter))
            return false;
        else {
            if (obj.hashCode() == this.hashCode())
                return true;
            else
                return false;
        }
    }
}
