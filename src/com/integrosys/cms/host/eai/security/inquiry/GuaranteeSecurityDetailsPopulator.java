package com.integrosys.cms.host.eai.security.inquiry;

import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.guarantee.GuaranteeSecurity;

public class GuaranteeSecurityDetailsPopulator extends AbstractSecuritySubtypeDetailsPopulator {

	protected void doPopulateMessageBody(SecurityMessageBody securityMsgBody, Object securitySubtypeObject) {
		GuaranteeSecurity guarantee = (GuaranteeSecurity) securitySubtypeObject;
		guarantee.setLOSSecurityId(securityMsgBody.getSecurityDetail().getLOSSecurityId());
		securityMsgBody.setGuaranteeDetail(guarantee);

		populateValuationDetails(securityMsgBody);
	}

	protected Class getSecuritySubtypeClass() {
		return GuaranteeSecurity.class;
	}

}
