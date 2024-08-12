package com.integrosys.cms.app.limit.bus;

import com.integrosys.cms.ui.manualinput.limit.IFacilityWsLog;

public interface IFacilityWsLogDao {

	static final String FACILITY_INTERFACE_LOG_NAME = "facilityWsLog";

	IFacilityWsLog createFacilityWsLog(IFacilityWsLog iFacilityWsLog);
	

}
