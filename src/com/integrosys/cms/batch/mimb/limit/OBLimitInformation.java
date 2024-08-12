package com.integrosys.cms.batch.mimb.limit;

public class OBLimitInformation implements ILimitInformation{

	private long tempID;

	private String recordType;

	private String aaNo;

	private String sourceLimitID;

	private String limitBookingLocationCode;

	private String limitBookingLocationDesc;

	private String productTypeCode;

	private String productDescription;

	private Double approvedLimit;

	private Double drawingLimit;

	private String outstandingBalanceSign;

	private Double outstandingBalance;

	private String limitStatus;

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getAaNo() {
		return aaNo;
	}

	public void setAaNo(String aaNo) {
		this.aaNo = aaNo;
	}

	public String getSourceLimitID() {
		return sourceLimitID;
	}

	public void setSourceLimitID(String sourceLimitID) {
		this.sourceLimitID = sourceLimitID;
	}

	public String getLimitBookingLocationCode() {
		return limitBookingLocationCode;
	}

	public void setLimitBookingLocationCode(String limitBookingLocationCode) {
		this.limitBookingLocationCode = limitBookingLocationCode;
	}

	public String getLimitBookingLocationDesc() {
		return limitBookingLocationDesc;
	}

	public void setLimitBookingLocationDesc(String limitBookingLocationDesc) {
		this.limitBookingLocationDesc = limitBookingLocationDesc;
	}

	public String getProductTypeCode() {
		return productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Double getApprovedLimit() {
		return approvedLimit;
	}

	public void setApprovedLimit(Double approvedLimit) {
		this.approvedLimit = approvedLimit;
	}

	public Double getDrawingLimit() {
		return drawingLimit;
	}

	public void setDrawingLimit(Double drawingLimit) {
		this.drawingLimit = drawingLimit;
	}

	public String getOutstandingBalanceSign() {
		return outstandingBalanceSign;
	}

	public void setOutstandingBalanceSign(String outstandingBalanceSign) {
		this.outstandingBalanceSign = outstandingBalanceSign;
	}

	public Double getOutstandingBalance() {
		return outstandingBalance;
	}

	public void setOutstandingBalance(Double outstandingBalance) {
		this.outstandingBalance = outstandingBalance;
	}

	public String getLimitStatus() {
		return limitStatus;
	}

	public void setLimitStatus(String limitStatus) {
		this.limitStatus = limitStatus;
	}

	public long getTempID() {
		return tempID;
	}

	public void setTempID(long tempID) {
		this.tempID = tempID;
	}

}
