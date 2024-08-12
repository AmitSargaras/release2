package com.integrosys.cms.ui.cersaiMapping;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

public class CersaiMappingForm  extends TrxContextForm implements Serializable{

	private String climsValue;
	private String cersaiValue;
	
	private String versionTime;
	private String lastUpdateDate;
	private String disableForSelection;
	private String creationDate;
	private String createBy;
	private String lastUpdateBy;
	private String masterName;
	
	private String status;
	private String deprecated;
	private String id;
	private String operationName;
	private String[] updatedCersaiValue;
	private String[] climsValues;
	
	private String[] updatedClimsValue;
	
	private String[] masterValueList;
	
	
	

	public String[] getMasterValueList() {
		return masterValueList;
	}


	public void setMasterValueList(String[] masterValueList) {
		this.masterValueList = masterValueList;
	}


	public String[] getUpdatedClimsValue() {
		return updatedClimsValue;
	}


	public void setUpdatedClimsValue(String[] updatedClimsValue) {
		this.updatedClimsValue = updatedClimsValue;
	}


	public String[] getUpdatedCersaiValue() {
		return updatedCersaiValue;
	}


	public void setUpdatedCersaiValue(String[] updatedCersaiValue) {
		this.updatedCersaiValue = updatedCersaiValue;
	}


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


	public String getOperationName() {
		return operationName;
	}


	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

//	public static final String PRODUCT_MASTER_MAPPER = "com.integrosys.cms.ui.cersaiMapping.CersaiMappingMapper";
	public static final String CERSAI_MAPPING_MAPPER = "com.integrosys.cms.ui.cersaiMapping.CersaiMappingMapper";
	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
	
	
	public String[][] getMapper() {
		String[][] input = {  { "cersaiMappingObj", CERSAI_MAPPING_MAPPER },
				{ "theOBTrxContext", TRX_MAPPER }
		};
		return input;

	}


	public String getClimsValue() {
		return climsValue;
	}


	public void setClimsValue(String climsValue) {
		this.climsValue = climsValue;
	}


	public String getCersaiValue() {
		return cersaiValue;
	}


	public void setCersaiValue(String cersaiValue) {
		this.cersaiValue = cersaiValue;
	}


	public String getMasterName() {
		return masterName;
	}


	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}


	public String[] getClimsValues() {
		return climsValues;
	}


	public void setClimsValues(String[] climsValues) {
		this.climsValues = climsValues;
	}

	
	
	
}
