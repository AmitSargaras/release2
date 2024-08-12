package com.integrosys.cms.ui.udf;

import com.integrosys.cms.ui.common.TrxContextForm;

public class UdfForm extends TrxContextForm implements java.io.Serializable {

	//	Fields
	private String fieldName;
	private String fieldTypeId;
	private String fieldType;
	private String moduleId;
	private String moduleName;
	private String options;
	private String sequence;
	private String mandatory;
	private String numericLength;
	private String creationDate;
	private String createBy;
	private String lastUpdateBy;
	private String lastUpdateDate;
	private String operationName;
	
	private String id;
	private String deprecated;
	
	private String status;
	
	public String getNumericLength() {
		return numericLength;
	}
	public void setNumericLength(String numericLength) {
		this.numericLength = numericLength;
	}
	
	
	public String getMandatory() {
		return mandatory;
	}
	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}
	public String getFieldName() {
		return fieldName;
	}
	public String getFieldType() {
		return fieldType;
	}
	public String getFieldTypeId() {
		return fieldTypeId;
	}
	public String getModuleId() {
		return moduleId;
	}
	public String getModuleName() {
		return moduleName;
	}
	public String getOptions() {
		return options;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public void setFieldTypeId(String fieldTypeId) {
		this.fieldTypeId = fieldTypeId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String[][] getMapper() {
		String[][] input = { 
				{ "UdfObj", UDF_MAPPER },
				{ "theOBTrxContext", TRX_MAPPER }
		};
		return input;
	}
	
	
	
	public static final String UDF_MAPPER = "com.integrosys.cms.ui.udf.UdfMapper";
	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";

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
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getOperationName() {
		return operationName;
	}


	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeprecated() {
		return deprecated;
	}
	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}

}
