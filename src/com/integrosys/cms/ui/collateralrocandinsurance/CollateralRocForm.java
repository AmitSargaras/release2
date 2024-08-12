package com.integrosys.cms.ui.collateralrocandinsurance;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

public class CollateralRocForm extends TrxContextForm implements Serializable{

	private String collateralRocActualCode;
	private String collateralCategory;
	private String collateralRocCode;
	private String collateralRocDescription;
	private String irbCategory;
	private String insuranceApplicable;
	
	public String getCollateralRocCode() {
		return collateralRocCode;
	}


	public void setCollateralRocCode(String collateralRocCode) {
		this.collateralRocCode = collateralRocCode;
	}


	public String getCollateralRocDescription() {
		return collateralRocDescription;
	}


	public void setCollateralRocDescription(String collateralRocDescription) {
		this.collateralRocDescription = collateralRocDescription;
	}


	public String getIrbCategory() {
		return irbCategory;
	}


	public void setIrbCategory(String irbCategory) {
		this.irbCategory = irbCategory;
	}


	public String getInsuranceApplicable() {
		return insuranceApplicable;
	}


	public void setInsuranceApplicable(String insuranceApplicable) {
		this.insuranceApplicable = insuranceApplicable;
	}


	private String versionTime;
	private String lastUpdateDate;
	private String disableForSelection;
	private String creationDate;
	private String createBy;
	private String lastUpdateBy;
	
	private String status;
	private String deprecated;
	private String id;
	private String cpsId;
	private String operationName;
	
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


	public static final String COLLATERAL_ROC_MAPPER = "com.integrosys.cms.ui.collateralrocandinsurance.CollateralRocMapper";
	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
	
	
	public String[][] getMapper() {
		String[][] input = {  { "collateralRocObj", COLLATERAL_ROC_MAPPER },
				{ "theOBTrxContext", TRX_MAPPER }
		};
		return input;

	}


	public String getCollateralCategory() {
		return collateralCategory;
	}


	public void setCollateralCategory(String collateralCategory) {
		this.collateralCategory = collateralCategory;
	}


	public String getCollateralRocActualCode() {
		return collateralRocActualCode;
	}


	public void setCollateralRocActualCode(String collateralRocActualCode) {
		this.collateralRocActualCode = collateralRocActualCode;
	}
}
