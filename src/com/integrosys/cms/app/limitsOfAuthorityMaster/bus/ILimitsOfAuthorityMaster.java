package com.integrosys.cms.app.limitsOfAuthorityMaster.bus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface ILimitsOfAuthorityMaster extends Serializable, IValueObject{

	public long getId();

	public void setId(long id);

	public long getVersionTime();

	public void setVersionTime(long versionTime);
	

	public String getStatus();

	public void setStatus(String status);

	public String getDeprecated();

	public void setDeprecated(String deprecated);

	public Date getCreationDate() ;

	public void setCreationDate(Date creationDate);

	public Date getLastUpdateDate();

	public void setLastUpdateDate(Date lastUpdateDate) ;

	public String getLastUpdateBy() ;

	public void setLastUpdateBy(String lastUpdateBy) ;

	public String getCreatedBy();

	public void setCreatedBy(String createdBy);

	public String getEmployeeGrade() ;

	public void setEmployeeGrade(String employeeGrade);

	public Integer getRankingOfSequence();

	public void setRankingOfSequence(Integer rankingOfSequence);

	public String getSegment();

	public void setSegment(String segment);

	public BigDecimal getLimitReleaseAmt();

	public void setLimitReleaseAmt(BigDecimal limitReleaseAmt);

	public BigDecimal getTotalSanctionedLimit() ;

	public void setTotalSanctionedLimit(BigDecimal totalSanctionedLimit);

	public BigDecimal getPropertyValuation();

	public void setPropertyValuation(BigDecimal propertyValuation) ;

	public BigDecimal getFdAmount();

	public void setFdAmount(BigDecimal fdAmount) ;
	public BigDecimal getDrawingPower() ;

	public void setDrawingPower(BigDecimal drawingPower);
	public BigDecimal getSblcSecurityOmv();

	public void setSblcSecurityOmv(BigDecimal sblcSecurityOmv);

	public String getFacilityCamCovenant();

	public void setFacilityCamCovenant(String facilityCamCovenant) ;

}