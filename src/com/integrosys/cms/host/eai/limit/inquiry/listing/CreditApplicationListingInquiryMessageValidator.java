package com.integrosys.cms.host.eai.limit.inquiry.listing;

import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.MandatoryFieldMissingException;
import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.core.EaiValidationHelper;
import com.integrosys.cms.host.eai.core.IEaiMessageValidator;
import com.integrosys.cms.host.eai.customer.bus.CustomerIdInfo;

/**
 * Message Validator for credit application listing inquiry
 * 
 * @author Chong Jun Yong
 * 
 */
public class CreditApplicationListingInquiryMessageValidator implements IEaiMessageValidator {

	private final EaiValidationHelper validator = EaiValidationHelper.getInstance();

	public void validate(EAIMessage scimsg) throws EAIMessageValidationException {
		CreditApplicationListingInquiryMessageBody msgBody = (CreditApplicationListingInquiryMessageBody) scimsg
				.getMsgBody();
		SearchCriteria criteria = msgBody.getCreditApplicationSearchCritiria();

		CustomerIdInfo idInfo = criteria.getCustomerIdInfo();
		if (idInfo == null) {
			throw new MandatoryFieldMissingException("PrimaryIDInfo");
		}

		String idNumber = idInfo.getIdNumber();
		validator.validateString(idNumber, "IdNumber", true, 1, 100);

		StandardCode idType = idInfo.getIdType();
		validator.validateStdCode(idType, scimsg.getMsgHeader().getSource(), "ID_TYPE");

		String countryIssued = idInfo.getCountryIssued();
		validator.validateString(countryIssued, "CountryIssued", false, 2, 2);
	}
}
