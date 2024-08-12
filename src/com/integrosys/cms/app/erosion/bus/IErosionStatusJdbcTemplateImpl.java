package com.integrosys.cms.app.erosion.bus;

import java.util.Date;
import java.util.List;

import com.integrosys.cms.app.generalparam.bus.OBGeneralParamEntry;

public interface IErosionStatusJdbcTemplateImpl {

	public List findErosionActivities(boolean isCreate) throws ErosionStatusException;
	public void updateErosionActivity(IErosionStatus erosionStatus) throws ErosionStatusException;
	public OBGeneralParamEntry getAppDate() throws ErosionStatusException;
	public OBGeneralParamEntry getErosionDate() throws ErosionStatusException;
	public void updateGeneralParamErosionDate(String newExecutionDate, Date erosionDate);
}
