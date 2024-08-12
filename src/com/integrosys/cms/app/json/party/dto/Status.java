package com.integrosys.cms.app.json.party.dto;

public class Status{
    public boolean isOverriden;
	public int replyCode;
    public String replyText;
    public Object memo;
    public String externalReferenceNo;
    public String internalReferenceNumber;
    public PostingDate postingDate;
    public String errorCode;
    public ExtendedReply extendedReply;
    public Object validationErrors;
    public Object userReferenceNumber;
    
    public boolean isOverriden() {
		return isOverriden;
	}
	public void setOverriden(boolean isOverriden) {
		this.isOverriden = isOverriden;
	}
	public int getReplyCode() {
		return replyCode;
	}
	public void setReplyCode(int replyCode) {
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
