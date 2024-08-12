package com.integrosys.cms.app.erosion.bus;

import java.util.Date;

public interface IErosionStatus {
	
	public long getId();
	public void setId(long id);
	public Date getReportingDate();
	public void setReportingDate(Date eodDate);
	public String getActivity();
	public void setActivity(String activity);
	public String getStatus();
	public void setStatus(String status);


}
