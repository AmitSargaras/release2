package com.integrosys.cms.batch.finwarefd;

public class OBFinwarefdcopy implements IFinwarefdcopy{
	private long id;
	private String fdNo;
	private double fdAmount;
	private double fdAmountLien;
	private double fdInterestRate;
	public String getFdNo() {
		return fdNo;
	}
	public void setFdNo(String fdNo) {
		this.fdNo = fdNo;
	}
	public double getFdAmount() {
		return fdAmount;
	}
	public void setFdAmount(double fdAmount) {
		this.fdAmount = fdAmount;
	}

	public double getFdAmountLien() {
		return fdAmountLien;
	}
	public void setFdAmountLien(double fdAmountLien) {
		this.fdAmountLien = fdAmountLien;
	}
	public double getFdInterestRate() {
		return fdInterestRate;
	}
	public void setFdInterestRate(double fdInterestRate) {
		this.fdInterestRate = fdInterestRate;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

}
