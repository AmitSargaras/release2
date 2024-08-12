package com.integrosys.cms.host.eai.security.inquiry;

import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.document.DocumentSecurity;

public class DocumentSecurityDetailsPopulator extends AbstractSecuritySubtypeDetailsPopulator {

	protected void doPopulateMessageBody(SecurityMessageBody securityMsgBody, Object securitySubtypeObject) {
		DocumentSecurity document = (DocumentSecurity) securitySubtypeObject;
		document.setLOSSecurityId(securityMsgBody.getSecurityDetail().getLOSSecurityId());
		securityMsgBody.setDocumentationDetail(document);

		populateValuationDetails(securityMsgBody);
	}

	protected Class getSecuritySubtypeClass() {
		return DocumentSecurity.class;
	}

}
