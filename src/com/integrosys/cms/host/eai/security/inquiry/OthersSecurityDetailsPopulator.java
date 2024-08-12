package com.integrosys.cms.host.eai.security.inquiry;

import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.others.OthersSecurity;

public class OthersSecurityDetailsPopulator extends AbstractSecuritySubtypeDetailsPopulator {

	protected void doPopulateMessageBody(SecurityMessageBody securityMsgBody, Object securitySubtypeObject) {
		OthersSecurity others = (OthersSecurity) securitySubtypeObject;
		others.setLOSSecurityId(securityMsgBody.getSecurityDetail().getLOSSecurityId());
		securityMsgBody.setOtherDetail(others);

		populateValuationDetails(securityMsgBody);

		populateInsurancePolicyDetails(securityMsgBody);
	}

	protected Class getSecuritySubtypeClass() {
		return OthersSecurity.class;
	}

}
