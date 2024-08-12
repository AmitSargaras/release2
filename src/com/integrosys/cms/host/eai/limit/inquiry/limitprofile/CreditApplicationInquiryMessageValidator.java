package com.integrosys.cms.host.eai.limit.inquiry.limitprofile;

import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.MandatoryFieldMissingException;
import com.integrosys.cms.host.eai.core.IEaiMessageValidator;

public class CreditApplicationInquiryMessageValidator implements IEaiMessageValidator {

	public void validate(EAIMessage scimsg) throws EAIMessageValidationException {
		CreditApplicationInquiryMessageBody msgBody = (CreditApplicationInquiryMessageBody) scimsg.getMsgBody();

		CreditApplicationCriteria criteria = msgBody.getCreditApplicationCriteria();

		if (criteria.getLosAANumber() == null || "".equals(criteria.getLosAANumber().trim())) {
			throw new MandatoryFieldMissingException("LOSAANumber");
		}

		if (criteria.getApplicationSource() == null || "".equals(criteria.getApplicationSource().trim())) {
			throw new MandatoryFieldMissingException("AASource");
		}
	}
}
