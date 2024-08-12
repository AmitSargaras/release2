package com.integrosys.cms.batch.finware;

public interface IFinwarecopy {
	public String getCustomerId();
	public void setCustomerId(String customerId) ;
	public long getSrNo() ;
	public void setSrNo(long srNo) ;
	public double getLimitAmount();
	public void setLimitAmount(double limitAmount) ;
	public double getUtilizationAmount() ;
	public void setUtilizationAmount(double utilizationAmount) ;
	
}
