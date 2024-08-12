package com.integrosys.cms.app.limit.bus;

import com.integrosys.cms.ui.manualinput.limit.ILiabilityWsLog;

public interface ILiabilityWsLogDao {

	static final String LIABILITY_INTERFACE_LOG_NAME = "liabilityWsLog";

	ILiabilityWsLog createLiabilityWsLog(ILiabilityWsLog iLiabilityWsLog);
	

}
