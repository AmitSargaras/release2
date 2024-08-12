package com.integrosys.cms.app.lei.json.dto;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class ValidationError {

	@JsonProperty("objectName")
	private String objectName;
	@JsonProperty("attributeName")
	private String attributeName;
	@JsonProperty("attributeValue") 
	private String attributeValue;
	@JsonProperty("errorCode")
	private String errorCode;
	@JsonProperty("errorMessage")
	private String errorMessage;
	@JsonProperty("methodName")
	private String methodName;
	@JsonProperty("applicableAttributes") 
	private String applicableAttributes;
	@JsonProperty("errorMessageParams")
	private List<String> errorMessageParams;
	@JsonProperty("associatedSeverity")
	private BigDecimal associatedSeverity;
	
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getApplicableAttributes() {
		return applicableAttributes;
	}
	public void setApplicableAttributes(String applicableAttributes) {
		this.applicableAttributes = applicableAttributes;
	}
	public List<String> getErrorMessageParams() {
		return errorMessageParams;
	}
	public void setErrorMessageParams(List<String> errorMessageParams) {
		this.errorMessageParams = errorMessageParams;
	}
	public BigDecimal getAssociatedSeverity() {
		return associatedSeverity;
	}
	public void setAssociatedSeverity(BigDecimal associatedSeverity) {
		this.associatedSeverity = associatedSeverity;
	}
	
	
}
