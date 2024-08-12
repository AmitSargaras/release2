/**

 * Copyright Integro Technologies Pte Ltd

 * $Header:

 */

package com.integrosys.cms.ui.collateralNewMaster;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 *@author $Author: Abhijit R$
 *Form Bean for Collateral Master
 *@since 02-05-2011 9:00 AM
 */

public class MaintainCollateralNewMasterForm extends TrxContextForm implements Serializable {

	private String newCollateralCode;
	private String newCollateralDescription;
	private String newCollateralSubType;
	private String newCollateralMainType;
	private String newCollateralCodeSearch;
	private String newCollateralDescriptionSearch;
	private String newCollateralSubTypeSearch;
	private String newCollateralMainTypeSearch;
	private String insurance;
	private String revaluationFrequencyDays;
	private String revaluationFrequencyCount;
    private String go="";
 
    private String newCollateralCategory;
    private String isApplicableForCersaiInd;
    
	public String getRevaluationFrequencyDays() {
		return revaluationFrequencyDays;
	}
	public void setRevaluationFrequencyDays(String revaluationFrequencyDays) {
		this.revaluationFrequencyDays = revaluationFrequencyDays;
	}
	public String getRevaluationFrequencyCount() {
		return revaluationFrequencyCount;
	}
	public void setRevaluationFrequencyCount(String revaluationFrequencyCount) {
		this.revaluationFrequencyCount = revaluationFrequencyCount;
	}


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
	private String operationName;
	private String cpsId;
	
	public String[][] getMapper() {
		String[][] input = {  { "collateralNewMasterObj", COLLATERAL_NEW_MASTER_MAPPER },
				{ "theOBTrxContext", TRX_MAPPER }
		};
		return input;

	}
	
	
	public static final String COLLATERAL_NEW_MASTER_MAPPER = "com.integrosys.cms.ui.collateralNewMaster.CollateralNewMasterMapper";

	public static final String COLLATERAL_NEW_MASTER_LIST_MAPPER = "com.integrosys.cms.ui.collateralNewMaster.CollateralNewMasterListMapper";

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
	public String getNewCollateralCodeSearch() {
		return newCollateralCodeSearch;
	}
	public void setNewCollateralCodeSearch(String newCollateralCodeSearch) {
		this.newCollateralCodeSearch = newCollateralCodeSearch;
	}
	public String getNewCollateralDescriptionSearch() {
		return newCollateralDescriptionSearch;
	}
	public void setNewCollateralDescriptionSearch(
			String newCollateralDescriptionSearch) {
		this.newCollateralDescriptionSearch = newCollateralDescriptionSearch;
	}
	public String getNewCollateralSubTypeSearch() {
		return newCollateralSubTypeSearch;
	}
	public void setNewCollateralSubTypeSearch(String newCollateralSubTypeSearch) {
		this.newCollateralSubTypeSearch = newCollateralSubTypeSearch;
	}
	public String getNewCollateralMainTypeSearch() {
		return newCollateralMainTypeSearch;
	}
	public void setNewCollateralMainTypeSearch(String newCollateralMainTypeSearch) {
		this.newCollateralMainTypeSearch = newCollateralMainTypeSearch;
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
