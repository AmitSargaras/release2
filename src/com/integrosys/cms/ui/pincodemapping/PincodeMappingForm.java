package com.integrosys.cms.ui.pincodemapping;

import java.io.Serializable;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.common.TrxContextForm;

public class PincodeMappingForm extends TrxContextForm implements Serializable{

	static final long serialVersionUID = 0L;
	
	private String id;

	private String pincode;
	/*private String stateCode;*/
	private String status;
	private String stateId;
	private String stateName;
	
	private String versionTime;
	private String lastUpdateDate;
	private String disableForSelection;
	private String creationDate;
	private String createdBy;
	private String lastUpdateBy;
	private String deprecated;
	private String cpsId;
	private String operationName;
	
	public String[][] getMapper() {
		DefaultLogger.debug(this,"Getting Mapper");
		String[][] input = {
		{ "theOBTrxContext", TRX_MAPPER },
		{ "pincodeMappingObj", PINCODE_MAPPING_MAPPER } 
		};
		return input;
		}

		public static final String PINCODE_MAPPING_MAPPER = "com.integrosys.cms.ui.pincodemapping.PincodeMapper";

		public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	/*public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}*/
	
	public String getStateId() {
		return stateId;
	}
	public void setStateId(String stateId) {
		this.stateId = stateId;
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
}
