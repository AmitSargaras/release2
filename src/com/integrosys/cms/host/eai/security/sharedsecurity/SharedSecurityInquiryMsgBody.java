/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.host.eai.security.sharedsecurity;

import com.integrosys.cms.host.eai.EAIBody;
import com.integrosys.cms.host.eai.security.sharedsecurity.bus.SharedSecuritySearch;

;
/**
 * Message for Security.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/10/23 02:49:03 $ Tag: $Name: $
 */
public class SharedSecurityInquiryMsgBody extends EAIBody implements java.io.Serializable {

	private static final long serialVersionUID = -9012059167151469818L;

	private SharedSecuritySearch sharedSecuritySearch;

	public SharedSecuritySearch getSharedSecuritySearch() {
		return sharedSecuritySearch;
	}

	public void setSharedSecuritySearch(SharedSecuritySearch sharedSecuritySearch) {
		this.sharedSecuritySearch = sharedSecuritySearch;
	}

}
