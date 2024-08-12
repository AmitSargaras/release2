package com.integrosys.cms.batch.ecbf;

import java.util.Date;

import com.integrosys.cms.ui.common.UIUtil;

public class OBEcbfDeferralReport {
	
	private String category;
	private String partyId;
	private String partyName;
	private String dueDate;
	private String remarks;
	private String rmName;
	private String pan;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRmName() {
		return rmName;
	}
	public void setRmName(String rmName) {
		this.rmName = rmName;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	
	@Override
	public String toString() {
		return ((getCategory()==null ? "" : getCategory()) + "|" 
				+ (getPartyId()==null ? "" : getPartyId().toString()) + "|" 
				+ (getPartyName()==null ? "" : getPartyName()) + "|" 
				+ (getDueDate()==null ? "" : getDueDate()) + "|" 
				+ (getRemarks()==null ? "" : getRemarks()) + "|" 
				+ (getRmName()==null ? "" : getRmName()) + "|" 
				+ (getPan()==null ? "" : getPan()));
	}
}
