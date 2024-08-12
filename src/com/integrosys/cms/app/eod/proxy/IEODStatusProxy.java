package com.integrosys.cms.app.eod.proxy;

import java.util.List;

import com.integrosys.cms.app.eod.bus.EODStatusException;
import com.integrosys.cms.app.eod.bus.IAdfRbiStatus;
import com.integrosys.cms.app.eod.bus.IEODStatus;

public interface IEODStatusProxy {
	public List updateAllEODActivites(List eodStatusList) throws EODStatusException;
	public IEODStatus updateEODActivity(IEODStatus eodStatus) throws EODStatusException;
	public IAdfRbiStatus updateAdfRbiActivity(IAdfRbiStatus eodStatus) throws EODStatusException;
	public List findEODActivities() throws EODStatusException;	
	public List findAdfRbiActivities() throws EODStatusException;	
	public List findEODActivitiesByStatus(String status) throws EODStatusException;
}
