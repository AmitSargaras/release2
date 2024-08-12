package com.integrosys.cms.host.eai.security.sharedsecurity;

import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.security.sharedsecurity.bus.SharedSecuritySearch;
import com.integrosys.cms.host.eai.security.sharedsecurity.validator.SharedSecurityValidator;

/**
 * EAIDocumentInquiryHelper.
 * 
 * @author $Author: Iwan Satria $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class EAISharedSecurityHelper {
	private static EAISharedSecurityHelper instance = null;

	private EAISharedSecurityHelper() {
	}

	public static EAISharedSecurityHelper getInstance() {
		if (instance == null) {
			synchronized (SharedSecurityValidator.class) {
				if (instance == null) {
					instance = new EAISharedSecurityHelper();
				}
			}
		}
		return instance;
	}

	// Check List
	public SharedSecuritySearch retrieveSharedSecuritySearch(EAIMessage msg) {
		return ((SharedSecurityInquiryMsgBody) msg.getMsgBody()).getSharedSecuritySearch();
	}

}
