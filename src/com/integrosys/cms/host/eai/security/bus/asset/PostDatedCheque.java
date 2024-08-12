package com.integrosys.cms.host.eai.security.bus.asset;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.cms.host.eai.support.MessageDate;



public class PostDatedCheque implements Serializable {
	private Date chequeDate;

	private Date privateCaveatGteeExpDate;

	private String chequeRefNum;

	private Double interestRate;

	public String getChequeDate() {
		return  MessageDate.getInstance().getString(this.chequeDate);
	}

	public void setChequeDate(String chequeDate) {
		this.chequeDate = MessageDate.getInstance().getDate(chequeDate);
	}

	public String getPrivateCaveatGteeExpDate() {
		return  MessageDate.getInstance().getString(this.privateCaveatGteeExpDate);
	}

	public void setPrivateCaveatGteeExpDate(String privateCaveatGteeExpDate) {
		this.privateCaveatGteeExpDate = MessageDate.getInstance().getDate(privateCaveatGteeExpDate);
	}
	
	public Date getJDOChequeDate() {
		return chequeDate;
	}

	public void setJDOChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}

	public Date getJDOPrivateCaveatGteeExpDate() {
		return privateCaveatGteeExpDate;
	}

	public void setJDOPrivateCaveatGteeExpDate(Date privateCaveatGteeExpDate) {
		this.privateCaveatGteeExpDate = privateCaveatGteeExpDate;
	}
	

	public String getChequeRefNum() {
		return chequeRefNum;
	}

	public void setChequeRefNum(String chequeRefNum) {
		this.chequeRefNum = chequeRefNum;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}



}
