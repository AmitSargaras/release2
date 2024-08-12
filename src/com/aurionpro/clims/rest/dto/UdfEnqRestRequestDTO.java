package com.aurionpro.clims.rest.dto;

import org.apache.struts.action.ActionErrors;

public class UdfEnqRestRequestDTO {

	private String moduleId;
	private String sequence;	
	private String fieldName;
	private String mandatory;
	private String status;
	private ActionErrors errors;
	
	
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getMandatory() {
		return mandatory;
	}
	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ActionErrors getErrors() {
		return errors;
	}
	public void setErrors(ActionErrors errors) {
		this.errors = errors;
	}
	
}