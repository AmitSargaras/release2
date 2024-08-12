package com.integrosys.cms.host.eai.limit.inquiry.limitprofile;

import com.integrosys.cms.host.eai.EAIBody;

public class CreditApplicationInquiryMessageBody extends EAIBody {
	private CreditApplicationCriteria creditApplicationCriteria;

	public CreditApplicationCriteria getCreditApplicationCriteria() {
		return creditApplicationCriteria;
	}

	public void setCreditApplicationCriteria(CreditApplicationCriteria creditApplicationCriteria) {
		this.creditApplicationCriteria = creditApplicationCriteria;
	}
}
