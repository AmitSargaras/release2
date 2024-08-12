package com.integrosys.cms.app.limit.bus;

public class OBCurrencyCodeCategory implements ICurrencyCodeStage{
	
	private long id;
	private String currencyCodeId;
	private long versionTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCurrencyCodeId() {
		return currencyCodeId;
	}
	public void setCurrencyCodeId(String currencyCodeId) {
		this.currencyCodeId = currencyCodeId;
	}
	public long getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}
}
