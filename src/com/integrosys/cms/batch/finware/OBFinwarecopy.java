package com.integrosys.cms.batch.finware;

public class OBFinwarecopy implements IFinwarecopy{
	private long id;
	private String customerId;
	private long srNo;
	private double limitAmount;
	private double utilizationAmount;

	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public long getSrNo() {
		return srNo;
	}
	public void setSrNo(long srNo) {
		this.srNo = srNo;
	}
	public double getLimitAmount() {
		return limitAmount;
	}
	public void setLimitAmount(double limitAmount) {
		this.limitAmount = limitAmount;
	}
	public double getUtilizationAmount() {
		return utilizationAmount;
	}
	public void setUtilizationAmount(double utilizationAmount) {
		this.utilizationAmount = utilizationAmount;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
}
