package com.integrosys.cms.app.eod.sync.proxy;

import java.util.List;

import com.integrosys.cms.app.eod.sync.bus.EODSyncStatusException;
import com.integrosys.cms.app.eod.sync.bus.IEODSyncStatus;
import com.integrosys.cms.app.eod.sync.bus.OBEODSyncStatus;


public interface IEODSyncStatusProxy {
	public List<OBEODSyncStatus> findEODSyncActivitiesBySyncDirection(String syncDirection) throws EODSyncStatusException;	
	public List<IEODSyncStatus> updateAllEODSyncActivites(List eodSyncStatusList) throws EODSyncStatusException ;
	public IEODSyncStatus updateEODSyncActivity(IEODSyncStatus eodSyncStatus) throws EODSyncStatusException ;
	public List<IEODSyncStatus> findEODSyncActivities() throws EODSyncStatusException ;
	public void logEODSyncStatus(String syncDirection) throws EODSyncStatusException ;
	public void resetEODSyncStatus(String syncDirection) throws EODSyncStatusException;
	public IEODSyncStatus findEODSyncActivityByProcessingKey(String processKey) throws EODSyncStatusException ;
}
