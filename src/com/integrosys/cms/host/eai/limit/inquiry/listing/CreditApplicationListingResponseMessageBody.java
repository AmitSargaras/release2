package com.integrosys.cms.host.eai.limit.inquiry.listing;

import java.util.List;

import com.integrosys.cms.host.eai.EAIBody;

/**
 * Credit application listing response message body
 * 
 * @author Chong Jun Yong
 * 
 */
public class CreditApplicationListingResponseMessageBody extends EAIBody {

	private static final long serialVersionUID = -1628653727461683892L;

	private List creditApplications;

	public List getCreditApplications() {
		return creditApplications;
	}

	public void setCreditApplications(List creditApplications) {
		this.creditApplications = creditApplications;
	}
}
