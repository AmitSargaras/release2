package com.integrosys.cms.app.newTat.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * @author  Abhijit R. 
 */
public interface INewTat extends Serializable, IValueObject {

	public long getId();
	public void setId(long id);
	public long getVersionTime();
	public void setVersionTime(long versionTime) ;
	public String getCreateBy() ;
	public void setCreateBy(String createBy) ;
	public Date getCreationDate() ;
	public void setCreationDate(Date creationDate) ;
	public String getLastUpdateBy() ;
	public void setLastUpdateBy(String lastUpdateBy) ;
	public Date getLastUpdateDate() ;
	public void setLastUpdateDate(Date lastUpdateDate) ;
	public String getDeprecated() ;
	public void setDeprecated(String deprecated);
	public String getStatus() ;
	public void setStatus(String status) ;
	public String getLspLeId() ;
	public void setLspLeId(String lspLeId) ;
	public String getLspShortName() ;
	public void setLspShortName(String lspShortName) ;
	public String getLspLeIdListSearch() ;
	public void setLspLeIdListSearch(String lspLeIdListSearch) ;
	public String getLspShortNameListSearch() ;
	public void setLspShortNameListSearch(String lspShortNameListSearch) ;
	public String getModule() ;
	public void setModule(String module) ;
	public String getCaseInitiator() ;
	public void setCaseInitiator(String caseInitiator) ;
	public String getRelationshipManager() ;
	public void setRelationshipManager(String relationshipManager) ;
	public String getDocStatus() ;
	public void setDocStatus(String docStatus) ;
	public String getRemarks();
	public void setRemarks(String remarks) ;
	public String getFacilityCategory() ;
	public void setFacilityCategory(String facilityCategory) ;
	public String getFacilityName() ;
	public void setFacilityName(String facilityName) ;
	public String getCamType() ;
	public void setCamType(String camType);
	public String getDeferralType() ;
	public void setDeferralType(String deferralType) ;
	public String getLssCoordinatorBranch() ;
	public void setLssCoordinatorBranch(String lssCoordinatorBranch) ;
	public String getType();
	public void setType(String type) ;
	public Date getActivityTime() ;
	public void setActivityTime(Date activityTime);
	public Date getActualActivityTime();
	public void setActualActivityTime(Date actualActivityTime) ;
	public String getFacilitySystem();
	public void setFacilitySystem(String facilitySystem) ;
	public String getFacilityManual();
	public void setFacilityManual(String facilityManual) ;
	public String getAmount() ;
	public void setAmount(String amount) ;
	public String getCurrency() ;
	public void setCurrency(String currency) ;
	public String getLineNumber() ;
	public void setLineNumber(String lineNumber) ;
	public String getSerialNumber() ;
	public void setSerialNumber(String serialNumber) ;

	public long getCmsLeMainProfileId() ;
	public void setCmsLeMainProfileId(long cmsLeMainProfileId);
	public long getCaseId() ;
	public void setCaseId(long caseId) ;
	public String getRegion();
	public void setRegion(String region);
	public String getSegment();
	public void setSegment(String segment);
	public Object clone() throws CloneNotSupportedException ;
	public String getRmRegion() ;
	public void setRmRegion(String rmRegion) ;
	
	
	public String getIsTatBurst() ;
	public void setIsTatBurst(String isTatBurst);
	public String getDelayReason();
	public void setDelayReason(String delayReason) ;
	
	public String getFacilitySection();
	public void setFacilitySection(String facilitySection) ;
	public String getDelayReasonText();
	public void setDelayReasonText(String delayReasonText);
}
