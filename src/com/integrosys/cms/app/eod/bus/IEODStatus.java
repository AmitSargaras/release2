package com.integrosys.cms.app.eod.bus;

import java.util.Date;

public interface IEODStatus {
			
	public long getId();
	public void setId(long id);
//	public long getVersionTime() ;
	//public void setVersionTime(long versionTime);
	public Date getEodDate();
	public void setEodDate(Date eodDate);
	public String getActivity();
	public void setActivity(String activity);
	public String getStatus();
	public void setStatus(String status);
}
