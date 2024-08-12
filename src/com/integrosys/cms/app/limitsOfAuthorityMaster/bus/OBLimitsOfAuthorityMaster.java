package com.integrosys.cms.app.limitsOfAuthorityMaster.bus;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;

import org.springframework.util.comparator.NullSafeComparator;

public class OBLimitsOfAuthorityMaster implements ILimitsOfAuthorityMaster, Cloneable {

	private long id;
	private long versionTime;
	
	private String employeeGrade;
	
	private Integer rankingOfSequence;
	private String segment;
	private BigDecimal limitReleaseAmt;
	private BigDecimal totalSanctionedLimit;
	private BigDecimal propertyValuation;
	private BigDecimal fdAmount;
	private BigDecimal drawingPower;
	private BigDecimal sblcSecurityOmv;
	private String facilityCamCovenant;
	
	private String status;
	private String deprecated;
	private Date creationDate;
	private String createdBy;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeprecated() {
		return deprecated;
	}

	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getEmployeeGrade() {
		return employeeGrade;
	}

	public void setEmployeeGrade(String employeeGrade) {
		this.employeeGrade = employeeGrade;
	}

	public Integer getRankingOfSequence() {
		return rankingOfSequence;
	}

	public void setRankingOfSequence(Integer rankingOfSequence) {
		this.rankingOfSequence = rankingOfSequence;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public BigDecimal getLimitReleaseAmt() {
		return limitReleaseAmt;
	}

	public void setLimitReleaseAmt(BigDecimal limitReleaseAmt) {
		this.limitReleaseAmt = limitReleaseAmt;
	}

	public BigDecimal getTotalSanctionedLimit() {
		return totalSanctionedLimit;
	}

	public void setTotalSanctionedLimit(BigDecimal totalSanctionedLimit) {
		this.totalSanctionedLimit = totalSanctionedLimit;
	}

	public BigDecimal getPropertyValuation() {
		return propertyValuation;
	}

	public void setPropertyValuation(BigDecimal propertyValuation) {
		this.propertyValuation = propertyValuation;
	}

	public BigDecimal getFdAmount() {
		return fdAmount;
	}

	public void setFdAmount(BigDecimal fdAmount) {
		this.fdAmount = fdAmount;
	}

	public BigDecimal getDrawingPower() {
		return drawingPower;
	}

	public void setDrawingPower(BigDecimal drawingPower) {
		this.drawingPower = drawingPower;
	}

	public BigDecimal getSblcSecurityOmv() {
		return sblcSecurityOmv;
	}

	public void setSblcSecurityOmv(BigDecimal sblcSecurityOmv) {
		this.sblcSecurityOmv = sblcSecurityOmv;
	}

	public String getFacilityCamCovenant() {
		return facilityCamCovenant;
	}

	public void setFacilityCamCovenant(String facilityCamCovenant) {
		this.facilityCamCovenant = facilityCamCovenant;
	}
	
	public static class Comparators {
        public static Comparator<ILimitsOfAuthorityMaster> LOA_MASTER_FIELD_TOTAL_SANCTIONED_LMT = new Comparator<ILimitsOfAuthorityMaster>() {
            @Override
            public int compare(ILimitsOfAuthorityMaster l1, ILimitsOfAuthorityMaster l2) {
            	return NullSafeComparator.NULLS_HIGH.compare(l1.getTotalSanctionedLimit(), l2.getTotalSanctionedLimit());
            }
        };
        
        public static Comparator<ILimitsOfAuthorityMaster> LOA_MASTER_FIELD_LIMIT_RELEASE_AMT = new Comparator<ILimitsOfAuthorityMaster>() {
            @Override
            public int compare(ILimitsOfAuthorityMaster l1, ILimitsOfAuthorityMaster l2) {
            	return NullSafeComparator.NULLS_HIGH.compare(l1.getLimitReleaseAmt(), l2.getLimitReleaseAmt());
            }
        };
        
        public static Comparator<ILimitsOfAuthorityMaster> LOA_MASTER_FIELD_PROPERTY_AMT = new Comparator<ILimitsOfAuthorityMaster>() {
            @Override
            public int compare(ILimitsOfAuthorityMaster l1, ILimitsOfAuthorityMaster l2) {
            	return NullSafeComparator.NULLS_HIGH.compare(l1.getPropertyValuation(), l2.getPropertyValuation());
            }
        };
        
        public static Comparator<ILimitsOfAuthorityMaster> LOA_MASTER_FIELD_FD_AMT = new Comparator<ILimitsOfAuthorityMaster>() {
            @Override
            public int compare(ILimitsOfAuthorityMaster l1, ILimitsOfAuthorityMaster l2) {
            	return NullSafeComparator.NULLS_HIGH.compare(l1.getFdAmount(), l2.getFdAmount());
            }
        };
        
        public static Comparator<ILimitsOfAuthorityMaster> LOA_MASTER_FIELD_DRAWING_POWER = new Comparator<ILimitsOfAuthorityMaster>() {
            @Override
            public int compare(ILimitsOfAuthorityMaster l1, ILimitsOfAuthorityMaster l2) {
            	return NullSafeComparator.NULLS_HIGH.compare(l1.getDrawingPower(), l2.getDrawingPower());
            }
        };
        
        public static Comparator<ILimitsOfAuthorityMaster> LOA_MASTER_FIELD_SBLC_SECURITY_OMV = new Comparator<ILimitsOfAuthorityMaster>() {
            @Override
            public int compare(ILimitsOfAuthorityMaster l1, ILimitsOfAuthorityMaster l2) {
            	return NullSafeComparator.NULLS_HIGH.compare(l1.getSblcSecurityOmv(), l2.getSblcSecurityOmv());
            }
        };
        
    }
	
}