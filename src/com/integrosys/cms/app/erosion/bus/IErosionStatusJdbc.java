package com.integrosys.cms.app.erosion.bus;

import java.util.Date;
import java.util.List;

import com.integrosys.cms.app.generalparam.bus.OBGeneralParamEntry;

public interface IErosionStatusJdbc {

	public void spCreateDataForFacilityReport(String applicationDate);
	public void spCreateDataForSecurityReport();
	public void spCreateDataForFacilityWiseReport(String applicationDate);
	public void spCreateDataForPartyWiseReport();
	public void spUpdateErosionForNpaFile();	
	public List findErosionActivities(boolean isCreate) throws ErosionStatusException;
	public void updateErosionActivity(IErosionStatus erosionStatus) throws ErosionStatusException;
	public OBGeneralParamEntry getAppDate();
	public void spErosionDataBackup();
}
