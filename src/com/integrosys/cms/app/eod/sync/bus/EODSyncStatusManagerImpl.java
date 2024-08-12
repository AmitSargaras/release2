package com.integrosys.cms.app.eod.sync.bus;

import java.util.List;

import com.integrosys.base.techinfra.context.BeanHouse;


public class EODSyncStatusManagerImpl implements IEODSyncStatusManager {

	IEODSyncStatusDao eodSyncStatusDao;

	
	/**
	 * @return the eodSyncStatusDao
	 */
	public IEODSyncStatusDao getEodSyncStatusDao() {
		return eodSyncStatusDao;
	}

	/**
	 * @param eodSyncStatusDao the eodSyncStatusDao to set
	 */
	public void setEodSyncStatusDao(IEODSyncStatusDao eodSyncStatusDao) {
		this.eodSyncStatusDao = eodSyncStatusDao;
		if(this.eodSyncStatusDao == null)
			this.eodSyncStatusDao = (IEODSyncStatusDao) BeanHouse.get("eodSyncStatusDao");
	}

	public List<IEODSyncStatus> findEODSyncActivities()
			throws EODSyncStatusException {
		return getEodSyncStatusDao().findEODSyncActivities();
	}

	public List<OBEODSyncStatus> findEODSyncActivitiesBySyncDirection(
			String syncDirection) throws EODSyncStatusException {
		return getEodSyncStatusDao().findEODSyncActivitiesBySyncDirection(syncDirection);
	}
	public IEODSyncStatus findEODSyncActivityByProcessingKey(String processKey) throws EODSyncStatusException {
		return getEodSyncStatusDao().findEODSyncActivityByProcessingKey(processKey);
	}

	public void logEODSyncStatus(String syncDirection) throws EODSyncStatusException {
		getEodSyncStatusDao().logEODSyncStatus(syncDirection);		
	}

	public void resetEODSyncStatus(String syncDirection) throws EODSyncStatusException {
		getEodSyncStatusDao().resetEODSyncStatus(syncDirection);		
	}

	public List<IEODSyncStatus> updateAllEODSyncActivites(List eodSyncStatusList)
			throws EODSyncStatusException {
		return getEodSyncStatusDao().updateAllEODSyncActivites(eodSyncStatusList);
	}

	public IEODSyncStatus updateEODSyncActivity(IEODSyncStatus eodSyncStatus)
			throws EODSyncStatusException {
		return getEodSyncStatusDao().updateEODSyncActivity(eodSyncStatus);
	}

}
