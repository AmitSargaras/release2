package com.integrosys.cms.host.eai.covenant.validator;

import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.MandatoryFieldMissingException;
import com.integrosys.cms.host.eai.core.EaiValidationHelper;
import com.integrosys.cms.host.eai.core.IEaiMessageValidator;
import com.integrosys.cms.host.eai.covenant.ConvenantInquireMessageBody;

/**
 * 
 * @author Thurein</br>
 * @since 1.1 </br>Validator for the Covenant Inquire Message(CV002I)
 * 
 */
public class ConvenantInquireValidator implements IEaiMessageValidator {

	public void validate(EAIMessage scimsg) throws EAIMessageValidationException {

		EaiValidationHelper helper = EaiValidationHelper.getInstance();

		ConvenantInquireMessageBody msgBody = (ConvenantInquireMessageBody) scimsg.getMsgBody();

		if (msgBody.getCovenant() == null) {
			throw new MandatoryFieldMissingException("Convenant");
		}

		helper.validateString(msgBody.getCovenant().getLOSAANumber(), "LOSAANumber", true, 1, 35);

	}

}
