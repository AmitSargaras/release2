package com.integrosys.cms.ui.npaTraqCodeMaster;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

public class NpaTraqCodeMasterForm extends TrxContextForm implements Serializable{

	private String npaTraqCode;
	private String securityType;
	private String securitySubType;
	private String propertyTypeCodeDesc;
	
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

	public static final String NPA_TRAQ_CODE_MASTER_MAPPER = "com.integrosys.cms.ui.npaTraqCodeMaster.NpaTraqCodeMasterMapper";
	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
	
	
	public String[][] getMapper() {
		String[][] input = {  { "npaTraqCodeMasterObj", NPA_TRAQ_CODE_MASTER_MAPPER },
				{ "theOBTrxContext", TRX_MAPPER }
		};
		return input;

	}


	public String getNpaTraqCode() {
		return npaTraqCode;
	}


	public void setNpaTraqCode(String npaTraqCode) {
		this.npaTraqCode = npaTraqCode;
	}


	public String getSecurityType() {
		return securityType;
	}


	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}


	public String getSecuritySubType() {
		return securitySubType;
	}


	public void setSecuritySubType(String securitySubType) {
		this.securitySubType = securitySubType;
	}


	public String getPropertyTypeCodeDesc() {
		return propertyTypeCodeDesc;
	}


	public void setPropertyTypeCodeDesc(String propertyTypeCodeDesc) {
		this.propertyTypeCodeDesc = propertyTypeCodeDesc;
	}

}
