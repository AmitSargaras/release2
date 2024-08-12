
package com.integrosys.cms.app.facilityNewMaster.bus;


import java.util.Date;

/**
 * @author abhijit.rudrakshawar 
 */
public class OBFacilityNewMaster implements IFacilityNewMaster {
	
	/**
	 * constructor
	 */
	public OBFacilityNewMaster() {
		
	}
    
	private String newFacilityCode;
	private String newFacilityName;
	private String newFacilityCategory;
	private String newFacilityType;
	private String newFacilitySystem;
	private String lineNumber;
	private String purpose;
	private double weightage;
	private String operationName;
	private String cpsCode;
	
	
	private long id;
	
	private long versionTime;
	private String cpsId;
	
	private String status;
	private String deprecated;
	private Date creationDate;
	private String createBy;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	private String ruleId;
	
	private String productAllowed;
	private String currencyRestriction;
	private String revolvingLine;
	private String lineCurrency;
	private String intradayLimit;
	private String stlFlag;
	private String lineDescription;
	private String scmFlag;
	private String lineExcludeFromLoa;
	private String idlApplicableFlag;
	

	public String getIdlApplicableFlag() {
		return idlApplicableFlag;
	}
	public void setIdlApplicableFlag(String idlApplicableFlag) {
		this.idlApplicableFlag = idlApplicableFlag;
	}
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
	public String getNewFacilityCode() {
		return newFacilityCode;
	}
	public void setNewFacilityCode(String newFacilityCode) {
		this.newFacilityCode = newFacilityCode;
	}
	public String getNewFacilityName() {
		return newFacilityName;
	}
	public void setNewFacilityName(String newFacilityName) {
		this.newFacilityName = newFacilityName;
	}
	public String getNewFacilityCategory() {
		return newFacilityCategory;
	}
	public void setNewFacilityCategory(String newFacilityCategory) {
		this.newFacilityCategory = newFacilityCategory;
	}
	public String getNewFacilityType() {
		return newFacilityType;
	}
	public void setNewFacilityType(String newFacilityType) {
		this.newFacilityType = newFacilityType;
	}
	
	public String getNewFacilitySystem() {
		return newFacilitySystem;
	}
	public void setNewFacilitySystem(String newFacilitySystem) {
		this.newFacilitySystem = newFacilitySystem;
	}
	public String getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public double getWeightage() {
		return weightage;
	}
	public void setWeightage(double weightage) {
		this.weightage = weightage;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getCpsCode() {
		return cpsCode;
	}
	public void setCpsCode(String cpsCode) {
		this.cpsCode = cpsCode;
	}
	public String getCpsId() {
		return cpsId;
	}
	public void setCpsId(String cpsId) {
		this.cpsId = cpsId;
	}
	/**
	 * @return the ruleId
	 */
	public String getRuleId() {
		return ruleId;
	}
	/**
	 * @param ruleId the ruleId to set
	 */
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	
	
	public String getProductAllowed() {
		return productAllowed;
	}
	public void setProductAllowed(String productAllowed) {
		this.productAllowed = productAllowed;
	}
	
	public String getCurrencyRestriction() {
		return currencyRestriction;
	}
	public void setCurrencyRestriction(String currencyRestriction) {
		this.currencyRestriction = currencyRestriction;
	}
	
	public String getRevolvingLine() {
		return revolvingLine;
	}
	public void setRevolvingLine(String revolvingLine) {
		this.revolvingLine = revolvingLine;
	}
	
	public String getLineCurrency() {
		return lineCurrency;
	}
	public void setLineCurrency(String lineCurrency) {
		this.lineCurrency = lineCurrency;
	}
	
	public String getIntradayLimit() {
		return intradayLimit;
	}
	public void setIntradayLimit(String intradayLimit) {
		this.intradayLimit = intradayLimit;
	}
	
	public String getStlFlag() {
		return stlFlag;
	}
	public void setStlFlag(String stlFlag) {
		this.stlFlag = stlFlag;
	}
	
	public String getLineDescription() {
		return lineDescription;
	}
	public void setLineDescription(String lineDescription) {
		this.lineDescription = lineDescription;
	}
	
	public String getScmFlag() {
		return scmFlag;
	}
	public void setScmFlag(String scmFlag) {
		this.scmFlag = scmFlag;
	}
	
	private String selectedRiskTypes;
	private String availAndOptionApplicable;

	public String getSelectedRiskTypes() {
		return selectedRiskTypes;
	}
	public void setSelectedRiskTypes(String selectedRiskTypes) {
		this.selectedRiskTypes = selectedRiskTypes;
	}
	public String getAvailAndOptionApplicable() {
		return availAndOptionApplicable;
	}
	public void setAvailAndOptionApplicable(String availAndOptionApplicable) {
		this.availAndOptionApplicable = availAndOptionApplicable;
	}
	
	public String getLineExcludeFromLoa() {
		return lineExcludeFromLoa;
	}
	public void setLineExcludeFromLoa(String lineExcludeFromLoa) {
		this.lineExcludeFromLoa = lineExcludeFromLoa;
	}
	
	
}