package com.integrosys.cms.app.limit.bus;

import com.integrosys.cms.ui.manualinput.limit.ICoBorrower_WsLog;

public interface ICoBorrower_FCUBS_WsLogDao {

	static final String COBORROWER_INTERFACE_LOG_NAME = "CoBorrower_WsLog";

	ICoBorrower_WsLog createCoBorrowerWsLog(ICoBorrower_WsLog iLiabilityWsLog);
	

}
