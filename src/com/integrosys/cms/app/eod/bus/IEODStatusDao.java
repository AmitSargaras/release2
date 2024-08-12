package com.integrosys.cms.app.eod.bus;

import java.util.List;

public interface IEODStatusDao {
	public List updateAllEODActivites(List eodStatusList) throws EODStatusException;
	public IEODStatus updateEODActivity(IEODStatus udf) throws EODStatusException;
	public IAdfRbiStatus updateAdfRbiActivity(IAdfRbiStatus udf) throws EODStatusException;
	public List findEODActivities() throws EODStatusException;	
	public List findAdfRbiActivities() throws EODStatusException;	
	public List findEODActivitiesByStatus(String status) throws EODStatusException;
	
	
}
