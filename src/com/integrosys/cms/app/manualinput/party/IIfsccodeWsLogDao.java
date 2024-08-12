package com.integrosys.cms.app.manualinput.party;

import com.integrosys.cms.ui.manualinput.customer.IIfsccodeWsLog;

public interface IIfsccodeWsLogDao {

	static final String IFSCCODE_INTERFACE_LOG_NAME = "ifsccodeWsLog";

	IIfsccodeWsLog createIfsccodeWsLog(IIfsccodeWsLog iIfsccodeWsLog);
	

}
