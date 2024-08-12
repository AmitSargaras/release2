package com.integrosys.cms.host.stp.support;

import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.stp.STPMessage;
import com.integrosys.cms.host.stp.common.StpCommonException;

/**
 * Message validator to validate the Message object parsed from the XML file.
 *
 * @author marvin
 * @author Chong Jun Yong
 * @since 1.1
 */
public interface IStpMessageValidator {

	/**
	 * Validate the message parsed from the XML file. Mainly validate the
	 * mandatory field and common code checking.
	 *
	 * @param scimsg the message object parsed from the XML file
	 * @throws com.integrosys.cms.host.eai.EAIMessageValidationException if there is any validation failed
	 */
	public void validate(STPMessage scimsg) throws StpCommonException;
}