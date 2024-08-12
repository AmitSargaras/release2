package com.integrosys.cms.batch.mimb.limit;

public interface ILimitInformation {

	public String getRecordType();

	public void setRecordType(String recordType);

	public String getAaNo();

	public void setAaNo(String aaNo);

	public String getSourceLimitID();

	public void setSourceLimitID(String sourceLimitID);

	public String getLimitBookingLocationCode();

	public void setLimitBookingLocationCode(String limitBookingLocationCode);

	public String getLimitBookingLocationDesc();

	public void setLimitBookingLocationDesc(String limitBookingLocationDesc);

	public String getProductTypeCode();

	public void setProductTypeCode(String productTypeCode);

	public String getProductDescription();

	public void setProductDescription(String productDescription);

	public Double getApprovedLimit();

	public void setApprovedLimit(Double approvedLimit);

	public Double getDrawingLimit();

	public void setDrawingLimit(Double drawingLimit);

	public String getOutstandingBalanceSign();

	public void setOutstandingBalanceSign(String outstandingBalanceSign);

	public Double getOutstandingBalance();

	public void setOutstandingBalance(Double outstandingBalance);

	public String getLimitStatus();

	public void setLimitStatus(String limitStatus);

	public long getTempID();

	public void setTempID(long tempID);

}
