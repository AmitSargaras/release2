package com.integrosys.cms.batch.bhavcopy;

public class OBBhavcopy implements IBhavcopy{
	private long scCode;
	private String scName;
	private String scGroup;
	private double openValue;
	private double closeValue;
	private double lastValue;
	
	public long getScCode() {
		return scCode;
	}
	public void setScCode(long scCode) {
		this.scCode = scCode;
	}
	public String getScName() {
		return scName;
	}
	public void setScName(String scName) {
		this.scName = scName;
	}
	public String getScGroup() {
		return scGroup;
	}
	public void setScGroup(String scGroup) {
		this.scGroup = scGroup;
	}
	public double getOpenValue() {
		return openValue;
	}
	public void setOpenValue(double openValue) {
		this.openValue = openValue;
	}
	public double getCloseValue() {
		return closeValue;
	}
	public void setCloseValue(double closeValue) {
		this.closeValue = closeValue;
	}
	public double getLastValue() {
		return lastValue;
	}
	public void setLastValue(double lastValue) {
		this.lastValue = lastValue;
	}
	
	
}
