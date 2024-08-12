package com.integrosys.cms.host.eai.security.inquiry;

import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.MandatoryFieldMissingException;
import com.integrosys.cms.host.eai.core.IEaiMessageValidator;

public class SecurityInquiryValidator implements IEaiMessageValidator {

	public void validate(EAIMessage eaiMessage) throws EAIMessageValidationException {
		SecurityInquiryMessageBody msgBody = (SecurityInquiryMessageBody) eaiMessage.getMsgBody();

		SecurityInquiry inquiry = msgBody.getSecurityInquiry();

		if (inquiry == null) {
			throw new MandatoryFieldMissingException("SecurityInquiry");
		}

		if (inquiry.getCmsSecurityId() <= 0) {
			throw new MandatoryFieldMissingException("CMSSecurityId");
		}

	}

}
