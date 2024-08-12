package com.aurionpro.clims.rest.dto;

public class UdfEnqRestResponseDTO {

	private String sequence;	
	private String fieldName;
	private String fieldValue;
	private String mandatory;
	private String fieldLength;	
	private String status;
	
	
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
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public String getMandatory() {
		return mandatory;
	}
	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}
	public String getFieldLength() {
		return fieldLength;
	}
	public void setFieldLength(String fieldLength) {
		this.fieldLength = fieldLength;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
