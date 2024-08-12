package com.integrosys.cms.host.eai.limit.inquiry.listing;

import com.integrosys.cms.host.eai.EAIBody;

/**
 * Credit application listing inquiry message body.
 * 
 * @author Chong Jun Yong
 * 
 */
public class CreditApplicationListingInquiryMessageBody extends EAIBody {

	private static final long serialVersionUID = -539939945054249552L;

	private SearchCriteria creditApplicationSearchCritiria;

	public SearchCriteria getCreditApplicationSearchCritiria() {
		return creditApplicationSearchCritiria;
	}

	public void setCreditApplicationSearchCritiria(SearchCriteria creditApplicationSearchCritiria) {
		this.creditApplicationSearchCritiria = creditApplicationSearchCritiria;
	}
}
