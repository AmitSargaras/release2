package com.integrosys.cms.app.udf.bus;

import java.util.Date;

public class OBUdf implements IUdf {
	
	// Fields
	private String fieldName;
	private long fieldTypeId;
	private String fieldType;
	private long id;
	private long moduleId;
	private String moduleName;
	private String options;
	private long  versionTime;
	private int  sequence;
	private String status;
	
	private String mandatory;
	private String numericLength;
	
	private Date creationDate;
	private String createBy;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	private String operationName;
	private String deprecated;
	
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
	// Getters 
	public String getFieldName() {
		return fieldName;
	}
	public long getFieldTypeId() {
		return fieldTypeId;
	}
	public long getId() {
		return id;
	}
	public long getModuleId() {
		return moduleId;
	}
	public String getOptions() {
		return options;
	}
	public long getVersionTime() {
		return versionTime;
	}
	public String getModuleName() {
		return moduleName;
	}	
	public String getFieldType() {
		return fieldType;
	}
	
	// Setters
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public void setFieldTypeId(long fieldTypeId) {
		this.fieldTypeId = fieldTypeId;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getDeprecated() {
		return deprecated;
	}
	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}
	
}
