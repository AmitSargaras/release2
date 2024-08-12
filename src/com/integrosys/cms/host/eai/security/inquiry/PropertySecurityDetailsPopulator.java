package com.integrosys.cms.host.eai.security.inquiry;

import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.property.PropertySecurity;

public class PropertySecurityDetailsPopulator extends AbstractSecuritySubtypeDetailsPopulator {

	protected void doPopulateMessageBody(SecurityMessageBody securityMsgBody, Object securitySubtypeObject) {
		PropertySecurity property = (PropertySecurity) securitySubtypeObject;
		property.setLOSSecurityId(securityMsgBody.getSecurityDetail().getLOSSecurityId());
		securityMsgBody.setPropertyDetail(property);

		populateValuationDetails(securityMsgBody);

		populateInsurancePolicyDetails(securityMsgBody);
	}

	protected Class getSecuritySubtypeClass() {
		return PropertySecurity.class;
	}

}
