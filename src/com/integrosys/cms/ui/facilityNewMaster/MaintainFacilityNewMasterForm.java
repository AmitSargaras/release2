/**

 * Copyright Integro Technologies Pte Ltd

 * $Header:

 */

package com.integrosys.cms.ui.facilityNewMaster;

import java.io.Serializable;
import java.text.DecimalFormat;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 *@author $Author: Abhijit R$
 *Form Bean for Facility Master
 *@since 02-05-2011 9:00 AM
 */

public class MaintainFacilityNewMasterForm extends TrxContextForm implements Serializable {

	private String newFacilityCode;
	private String newFacilityName;
	private String newFacilityCategory;
	private String newFacilityType;
	private String newFacilitySystem;
	private String lineNumber;
	
	private String newFacilityCodeSearch;
	private String newFacilityNameSearch;
	private String newFacilityCategorySearch;
	private String newFacilityTypeSearch;
	private String newFacilitySystemSearch;
	private String lineNumberSearch;
	
	private String purpose;
	private String weightage;
	private String go="";

	private String versionTime;

	private String lastUpdateDate;
	private String disableForSelection;
	
	
	private String creationDate;
	
	
	private String createBy;
	//private String lastUpdateDate;
	private String lastUpdateBy;
	
	
	
	private String status;
	private String deprecated;
	private String id;
	private String cpsId;
	private String operationName;
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
	private String availAndOptionApplicable;
	private String allListRiskType;
	private String riskTypeNames;
	private String selectedRiskTypes;
	private String idlApplicableFlag;
	
	
	public String getIdlApplicableFlag() {
		return idlApplicableFlag;
	}
	public void setIdlApplicableFlag(String idlApplicableFlag) {
		this.idlApplicableFlag = idlApplicableFlag;
	}
	public String getAvailAndOptionApplicable() {
		return availAndOptionApplicable;
	}
	public void setAvailAndOptionApplicable(String availAndOptionApplicable) {
		this.availAndOptionApplicable = availAndOptionApplicable;
	}
	public String getAllListRiskType() {
		return allListRiskType;
	}
	public void setAllListRiskType(String allListRiskType) {
		this.allListRiskType = allListRiskType;
	}
	
	public String getRiskTypeNames() {
		return riskTypeNames;
	}
	public void setRiskTypeNames(String riskTypeNames) {
		this.riskTypeNames = riskTypeNames;
	}
	
	public String getSelectedRiskTypes() {
		return selectedRiskTypes;
	}
	public void setSelectedRiskTypes(String selectedRiskTypes) {
		this.selectedRiskTypes = selectedRiskTypes;
	}
	
	public String[][] getMapper() {
		String[][] input = {  { "facilityNewMasterObj", FACILITY_NEW_MASTER_MAPPER },
				{ "theOBTrxContext", TRX_MAPPER }
		};
		return input;

	}
	
	
	public static final String FACILITY_NEW_MASTER_MAPPER = "com.integrosys.cms.ui.facilityNewMaster.FacilityNewMasterMapper";

	public static final String FACILITY_NEW_MASTER_LIST_MAPPER = "com.integrosys.cms.ui.facilityNewMaster.FacilityNewMasterListMapper";

	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
	
	
	
	
	
	public String getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(String versionTime) {
		this.versionTime = versionTime;
	}
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getDisableForSelection() {
		return disableForSelection;
	}
	public void setDisableForSelection(String disableForSelection) {
		this.disableForSelection = disableForSelection;
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
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getGo() {
		return go;
	}
	public void setGo(String go) {
		this.go = go;
	}
	
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
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
	public String getWeightage() {
		
			return weightage;
	}
	public void setWeightage(String weightage) {
		this.weightage = weightage;
	}
	public String getNewFacilityCodeSearch() {
		return newFacilityCodeSearch;
	}
	public void setNewFacilityCodeSearch(String newFacilityCodeSearch) {
		this.newFacilityCodeSearch = newFacilityCodeSearch;
	}
	public String getNewFacilityNameSearch() {
		return newFacilityNameSearch;
	}
	public void setNewFacilityNameSearch(String newFacilityNameSearch) {
		this.newFacilityNameSearch = newFacilityNameSearch;
	}
	public String getNewFacilityCategorySearch() {
		return newFacilityCategorySearch;
	}
	public void setNewFacilityCategorySearch(String newFacilityCategorySearch) {
		this.newFacilityCategorySearch = newFacilityCategorySearch;
	}
	public String getNewFacilityTypeSearch() {
		return newFacilityTypeSearch;
	}
	public void setNewFacilityTypeSearch(String newFacilityTypeSearch) {
		this.newFacilityTypeSearch = newFacilityTypeSearch;
	}
	public String getNewFacilitySystemSearch() {
		return newFacilitySystemSearch;
	}
	public void setNewFacilitySystemSearch(String newFacilitySystemSearch) {
		this.newFacilitySystemSearch = newFacilitySystemSearch;
	}
	public String getLineNumberSearch() {
		return lineNumberSearch;
	}
	public void setLineNumberSearch(String lineNumberSearch) {
		this.lineNumberSearch = lineNumberSearch;
	}
	public String getCpsId() {
		return cpsId;
	}
	public void setCpsId(String cpsId) {
		this.cpsId = cpsId;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
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
	public String getLineExcludeFromLoa() {
		return lineExcludeFromLoa;
	}
	public void setLineExcludeFromLoa(String lineExcludeFromLoa) {
		this.lineExcludeFromLoa = lineExcludeFromLoa;
	}
}
