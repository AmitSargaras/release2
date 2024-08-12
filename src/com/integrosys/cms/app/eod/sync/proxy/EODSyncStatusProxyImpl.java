package com.integrosys.cms.app.eod.sync.proxy;

import java.util.List;

import com.integrosys.cms.app.eod.sync.bus.EODSyncStatusException;
import com.integrosys.cms.app.eod.sync.bus.IEODSyncStatus;
import com.integrosys.cms.app.eod.sync.bus.IEODSyncStatusManager;
import com.integrosys.cms.app.eod.sync.bus.OBEODSyncStatus;

public class EODSyncStatusProxyImpl implements IEODSyncStatusProxy {

	IEODSyncStatusManager eodSyncStatusManager;
	
	
	/**
	 * @return the eodSyncStatusManager
	 */
	public IEODSyncStatusManager getEodSyncStatusManager() {
		return eodSyncStatusManager;
	}

	/**
	 * @param eodSyncStatusManager the eodSyncStatusManager to set
	 */
	public void setEodSyncStatusManager(IEODSyncStatusManager eodSyncStatusManager) {
		this.eodSyncStatusManager = eodSyncStatusManager;
	}

	public List<IEODSyncStatus> findEODSyncActivities()
			throws EODSyncStatusException {
		return getEodSyncStatusManager().findEODSyncActivities();
	}

	public List<OBEODSyncStatus> findEODSyncActivitiesBySyncDirection(
			String syncDirection) throws EODSyncStatusException {
		return getEodSyncStatusManager().findEODSyncActivitiesBySyncDirection(syncDirection);
	}

	public void logEODSyncStatus(String syncDirection) throws EODSyncStatusException {
		getEodSyncStatusManager().logEODSyncStatus(syncDirection);
		
	}

	public void resetEODSyncStatus(String syncDirection) throws EODSyncStatusException {
		getEodSyncStatusManager().resetEODSyncStatus(syncDirection);		
	}

	public List<IEODSyncStatus> updateAllEODSyncActivites(List eodSyncStatusList)
			throws EODSyncStatusException {
		return getEodSyncStatusManager().updateAllEODSyncActivites(eodSyncStatusList);
	}

	public IEODSyncStatus updateEODSyncActivity(IEODSyncStatus eodSyncStatus)
			throws EODSyncStatusException {
		return getEodSyncStatusManager().updateEODSyncActivity(eodSyncStatus);
	}
	public IEODSyncStatus findEODSyncActivityByProcessingKey(String processKey) throws EODSyncStatusException {
		return getEodSyncStatusManager().findEODSyncActivityByProcessingKey(processKey);
	}
	/*public List findEODActivities() throws EODStatusException {
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
*/
	}
