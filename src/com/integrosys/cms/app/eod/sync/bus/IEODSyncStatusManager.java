package com.integrosys.cms.app.eod.sync.bus;

import java.util.List;


public interface IEODSyncStatusManager {
	public List<OBEODSyncStatus> findEODSyncActivitiesBySyncDirection(String syncDirection) throws EODSyncStatusException;	
	public List<IEODSyncStatus> updateAllEODSyncActivites(List eodSyncStatusList) throws EODSyncStatusException ;
	public IEODSyncStatus updateEODSyncActivity(IEODSyncStatus eodSyncStatus) throws EODSyncStatusException ;
	public List<IEODSyncStatus> findEODSyncActivities() throws EODSyncStatusException ;
	public void logEODSyncStatus(String syncDirection) throws EODSyncStatusException ;
	public void resetEODSyncStatus(String syncDirection) throws EODSyncStatusException;
	public IEODSyncStatus findEODSyncActivityByProcessingKey(String processKey) throws EODSyncStatusException ;
}
