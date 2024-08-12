package com.integrosys.cms.ui.diaryitem;

public class ODSchedule {
	
	private String diaryNumber;        
	private String loanAmount;
	private String interestRate;
	private String toBeReducedOn;
	private String month;
	private String OpBal;
	private String emi;
	private String interest;
	private String principal;
	private String calBal;
	private String isClosed;
	
	public String getDiaryNumber() {
		return diaryNumber;
	}
	public void setDiaryNumber(String diaryNumber) {
		this.diaryNumber = diaryNumber;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}
	public String getToBeReducedOn() {
		return toBeReducedOn;
	}
	public void setToBeReducedOn(String toBeReducedOn) {
		this.toBeReducedOn = toBeReducedOn;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getOpBal() {
		return OpBal;
	}
	public void setOpBal(String opBal) {
		OpBal = opBal;
	}
	public String getEmi() {
		return emi;
	}
	public void setEmi(String emi) {
		this.emi = emi;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getCalBal() {
		return calBal;
	}
	public void setCalBal(String calBal) {
		this.calBal = calBal;
	}
	public String getIsClosed() {
		return isClosed;
	}
	public void setIsClosed(String isClosed) {
		this.isClosed = isClosed;
	}
}
