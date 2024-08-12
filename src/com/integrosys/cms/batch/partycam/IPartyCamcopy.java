package com.integrosys.cms.batch.partycam;

public interface IPartyCamcopy {
	public String getCustomerId();
	public void setCustomerId(String customerId) ;
	public String getLineNo() ;
	public void setLineNo(String lineNo) ;
	public long getSrNo() ;
	public void setSrNo(long srNo) ;
	public String getCurrencyCode() ;
	public void setCurrencyCode(String currencyCode) ;
	public double getLimitAmount();
	public void setLimitAmount(double limitAmount) ;
	public double getUtilizationAmount() ;
	public void setUtilizationAmount(double utilizationAmount) ;
	
}
