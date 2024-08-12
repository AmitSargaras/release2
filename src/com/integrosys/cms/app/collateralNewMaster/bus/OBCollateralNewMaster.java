
package com.integrosys.cms.app.collateralNewMaster.bus;


import java.util.Date;

/**
 * @author abhijit.rudrakshawar 
 */
public class OBCollateralNewMaster implements ICollateralNewMaster {
	
	/**
	 * constructor
	 */
	public OBCollateralNewMaster() {
		
	}
	private String newCollateralCode;
	private String newCollateralDescription;
	private String newCollateralSubType;
	private String newCollateralMainType;
	private String insurance;
	private String revaluationFrequencyDays;
	private long revaluationFrequencyCount;

 
	
	
	private long id;
	
	private long versionTime;
	
	
	private String status;
	private String deprecated;
	private Date creationDate;
	private String createBy;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	private String operationName;
	private String cpsId;
	
	private String newCollateralCategory;
	private String isApplicableForCersaiInd;

	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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
	
	public long getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNewCollateralCode() {
		return newCollateralCode;
	}
	public void setNewCollateralCode(String newCollateralCode) {
		this.newCollateralCode = newCollateralCode;
	}
	public String getNewCollateralDescription() {
		return newCollateralDescription;
	}
	public void setNewCollateralDescription(String newCollateralDescription) {
		this.newCollateralDescription = newCollateralDescription;
	}
	public String getNewCollateralSubType() {
		return newCollateralSubType;
	}
	public void setNewCollateralSubType(String newCollateralSubType) {
		this.newCollateralSubType = newCollateralSubType;
	}
	public String getNewCollateralMainType() {
		return newCollateralMainType;
	}
	public void setNewCollateralMainType(String newCollateralMainType) {
		this.newCollateralMainType = newCollateralMainType;
	}
	public String getInsurance() {
		return insurance;
	}
	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}
	public String getRevaluationFrequencyDays() {
		return revaluationFrequencyDays;
	}
	public void setRevaluationFrequencyDays(String revaluationFrequencyDays) {
		this.revaluationFrequencyDays = revaluationFrequencyDays;
	}
	public long getRevaluationFrequencyCount() {
		return revaluationFrequencyCount;
	}
	public void setRevaluationFrequencyCount(long revaluationFrequencyCount) {
		this.revaluationFrequencyCount = revaluationFrequencyCount;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getCpsId() {
		return cpsId;
	}
	public void setCpsId(String cpsId) {
		this.cpsId = cpsId;
	}
	public String getNewCollateralCategory() {
		return newCollateralCategory;
	}
	public void setNewCollateralCategory(String newCollateralCategory) {
		this.newCollateralCategory = newCollateralCategory;
	}
	public String getIsApplicableForCersaiInd() {
		return isApplicableForCersaiInd;
	}
	public void setIsApplicableForCersaiInd(String isApplicableForCersaiInd) {
		this.isApplicableForCersaiInd = isApplicableForCersaiInd;
	}
	
	
}