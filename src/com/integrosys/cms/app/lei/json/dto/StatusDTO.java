package com.integrosys.cms.app.lei.json.dto;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StatusDTO {

	@JsonProperty("isOverriden") 
	private Boolean isOverriden;
	@JsonProperty("replyCode")
	private Long replyCode;
	@JsonProperty("replyText")
	private String replyText;
	@JsonProperty("memo")
	private Object memo;
	@JsonProperty("externalReferenceNo") 
	private String externalReferenceNo;
	@JsonProperty("internalReferenceNumber")
	private String internalReferenceNumber;
	@JsonProperty("postingDate")
	private PostingDate postingDate;
	@JsonProperty("errorCode")
	private String errorCode;
	@JsonProperty("extendedReply") 
	private ExtendedReply extendedReply;
	@JsonProperty("validationErrors")
	private Object validationErrors;
	@JsonProperty("userReferenceNumber")
	private Object userReferenceNumber;
	
	public Boolean getIsOverriden() {
		return isOverriden;
	}
	public void setIsOverriden(Boolean isOverriden) {
		this.isOverriden = isOverriden;
	}
	public Long getReplyCode() {
		return replyCode;
	}
	public void setReplyCode(Long replyCode) {
		this.replyCode = replyCode;
	}
	public String getReplyText() {
		return replyText;
	}
	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}
	public Object getMemo() {
		return memo;
	}
	public void setMemo(Object memo) {
		this.memo = memo;
	}
	public String getExternalReferenceNo() {
		return externalReferenceNo;
	}
	public void setExternalReferenceNo(String externalReferenceNo) {
		this.externalReferenceNo = externalReferenceNo;
	}
	public String getInternalReferenceNumber() {
		return internalReferenceNumber;
	}
	public void setInternalReferenceNumber(String internalReferenceNumber) {
		this.internalReferenceNumber = internalReferenceNumber;
	}
	public PostingDate getPostingDate() {
		return postingDate;
	}
	public void setPostingDate(PostingDate postingDate) {
		this.postingDate = postingDate;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public ExtendedReply getExtendedReply() {
		return extendedReply;
	}
	public void setExtendedReply(ExtendedReply extendedReply) {
		this.extendedReply = extendedReply;
	}
	public Object getValidationErrors() {
		return validationErrors;
	}
	public void setValidationErrors(Object validationErrors) {
		this.validationErrors = validationErrors;
	}
	public Object getUserReferenceNumber() {
		return userReferenceNumber;
	}
	public void setUserReferenceNumber(Object userReferenceNumber) {
		this.userReferenceNumber = userReferenceNumber;
	}
	

}
