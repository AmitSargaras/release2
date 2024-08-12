package com.integrosys.cms.app.eod.bus;

import java.util.List;
import java.util.Map;

import com.integrosys.cms.app.eod.sync.bus.EODSyncRecordStatus;
import com.integrosys.cms.app.poi.report.OBFilter;
import com.integrosys.cms.batch.common.syncmaster.climstocps.xml.schema.SyncMasterTemplateOut;

public interface IEODSyncOutMasterDao {
	public List<Object[]> getReportQueryResult( SyncMasterTemplateOut syncMasterTemplateOut,OBFilter filter);
	public List<String[]> getReportDataList(List<Object[]> reportQueryResult,Map parameters);
	public void updateEodSyncStatus(String masterName, List<String[]> reportDataList);
	public void updateEodSyncStatus(String masterName, EODSyncRecordStatus syncStatus);
	//public void updateEodSyncStatus(String masterName, String opStatus,String id);
	public void updateRecordsEodSyncStatus(String masterName, Object obToStore);
	public long getCountryId(Long cpsId);
	public long getRegionId(Long cpsId);
	public long getStateId(Long cpsId);
}
