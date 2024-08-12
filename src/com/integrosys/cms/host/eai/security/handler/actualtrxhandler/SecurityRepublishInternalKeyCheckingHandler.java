package com.integrosys.cms.host.eai.security.handler.actualtrxhandler;

import com.integrosys.cms.host.eai.Message;

public class SecurityRepublishInternalKeyCheckingHandler extends SecurityInternalKeyCheckingHandler {

	public Message preprocess(Message msg) {
		return checkValidCMSId(msg);
	}
}
