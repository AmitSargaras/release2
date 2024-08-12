package com.integrosys.cms.app.limit.bus;

public class OBCurrencyRestrictionCodeCategory implements ICurrencyRestrictionCodeStage {
	
	private long id;
	private String currencyRestrictionCodeId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCurrencyRestrictionCodeId() {
		return currencyRestrictionCodeId;
	}
	public void setCurrencyRestrictionCodeId(String currencyRestrictionCodeId) {
		this.currencyRestrictionCodeId = currencyRestrictionCodeId;
	}
	public long getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}
	private long versionTime;
}
