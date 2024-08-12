package com.integrosys.cms.batch.erosion.schedular;

import java.util.Date;
import java.util.List;

import com.integrosys.cms.app.erosion.bus.IErosionStatus;
import com.integrosys.cms.app.generalparam.bus.OBGeneralParamEntry;

public interface IErosionHelper {
	
	public boolean performActivity(IErosionStatus erosionStatus,boolean isCreateData,String applicationDate);
	public void finalizeErosion(boolean erosionProcessExecuted,boolean isCreate);
	public List findErosionActivities(boolean isCreate);
	public void generateErosionReportFile(String reportName,String fileName);
	public OBGeneralParamEntry getAppDate();
	public void executeErosionDataBackup();
	public OBGeneralParamEntry getErosionDate();
	public void updateGeneralParamErosionDate(String newExecutionDate, Date erosionDate);
	
}
