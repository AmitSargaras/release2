package com.integrosys.cms.host.eai.customer.validator;

import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.core.EaiValidationHelper;
import com.integrosys.cms.host.eai.core.IEaiMessageValidator;
import com.integrosys.cms.host.eai.customer.CustomerEnquiryMessageBody;
import com.integrosys.cms.host.eai.customer.SearchDetailResult;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class CustomerSearchMultipleValidator implements IEaiMessageValidator {

	public CustomerSearchMultipleValidator() {

	}

	public void validate(EAIMessage scimessage) throws EAIMessageValidationException {
		CustomerEnquiryMessageBody msgBody = ((CustomerEnquiryMessageBody) scimessage.getMsgBody());

		String source = scimessage.getMsgHeader().getSource();

		EaiValidationHelper vH = EaiValidationHelper.getInstance();

		// Mandatory Total records .
		vH.validateString(msgBody.getSearchHeader().getTotalRecord(), "TotalRecord", true, 1, 7);

		// Validate Details
		// Result Details

		java.util.Vector v = msgBody.getSearchDetail().getResult();

		for (int i = 0; i < v.size(); i++) {

			SearchDetailResult sdr = (SearchDetailResult) v.get(i);

			// CIF No
			vH.validateString(sdr.getCIFId(), "CIFId", true, 1, 10);

			// Customer ShortName
			vH.validateString(sdr.getCustomerNameShort(), "CustomerNameShort", true, 1, 50);

			// Validate ID Type
			vH.validateStdCode(sdr.getIdType(), source, "ID_TYPE");

			// Customer Address
			vH.validateString(sdr.getAddress(), "Address", false, 1, 200);

			// Customer Birthdate
			vH.validateDate(sdr.getBirthDate(), "BirthDate", false);

		}

	}

}
