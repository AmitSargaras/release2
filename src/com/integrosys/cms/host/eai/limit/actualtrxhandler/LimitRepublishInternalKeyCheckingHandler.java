package com.integrosys.cms.host.eai.limit.actualtrxhandler;

import com.integrosys.cms.host.eai.Message;

public class LimitRepublishInternalKeyCheckingHandler extends LimitInternalKeyCheckingHandler {

	public Message preprocess(Message msg) {
		return checkValidCMSId(msg);
	}

}
