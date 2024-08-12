package com.integrosys.cms.app.eod.proxy;

import java.util.List;

import com.integrosys.cms.app.eod.bus.EODStatusException;
import com.integrosys.cms.app.eod.bus.IAdfRbiStatus;
import com.integrosys.cms.app.eod.bus.IEODStatus;
import com.integrosys.cms.app.eod.bus.IEODStatusManager;

public class EODStatusProxyImpl implements IEODStatusProxy {

	IEODStatusManager eodStatusManager;
	
	public IEODStatusManager getEodStatusManager() {
		return eodStatusManager;
	}

	public void setEodStatusManager(IEODStatusManager eodStatusManager) {
		this.eodStatusManager = eodStatusManager;
	}

	public List findEODActivities() throws EODStatusException {
		return eodStatusManager.findEODActivities();
	}

	public List findEODActivitiesByStatus(String status) throws EODStatusException {
		return eodStatusManager.findEODActivitiesByStatus(status);
	}

	public List updateAllEODActivites(List eodStatusList) throws EODStatusException {
		return eodStatusManager.updateAllEODActivites(eodStatusList);
	}

	public IEODStatus updateEODActivity(IEODStatus eodStatus) throws EODStatusException {
		return eodStatusManager.updateEODActivity(eodStatus);
	}

	public List findAdfRbiActivities() throws EODStatusException {
		return eodStatusManager.findAdfRbiActivities();
	}
	public IAdfRbiStatus updateAdfRbiActivity(IAdfRbiStatus eodStatus) throws EODStatusException {
		return eodStatusManager.updateAdfRbiActivity(eodStatus);
	}
}
