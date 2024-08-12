package com.integrosys.cms.host.eai.security.inquiry;

import com.integrosys.cms.host.eai.EAIBody;

/**
 * The security inquiry message body after unmarshalled from xml string.
 * 
 * @author Chong Jun Yong
 * 
 */
public class SecurityInquiryMessageBody extends EAIBody {

	private static final long serialVersionUID = 2968944060930602527L;

	private SecurityInquiry securityInquiry;

	public SecurityInquiry getSecurityInquiry() {
		return securityInquiry;
	}

	public void setSecurityInquiry(SecurityInquiry securityInquiry) {
		this.securityInquiry = securityInquiry;
	}
}
