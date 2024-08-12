package com.integrosys.cms.batch.bhavcopy;

public interface IBhavcopy {
	public long getScCode() ;
	public void setScCode(long scCode);
	public String getScName() ;
	public void setScName(String scName) ;
	public String getScGroup() ;
	public void setScGroup(String scGroup) ;
	public double getOpenValue() ;
	public void setOpenValue(double openValue);
	public double getCloseValue() ;
	public void setCloseValue(double closeValue) ;
	public double getLastValue() ;
	public void setLastValue(double lastValue) ;
	
}
