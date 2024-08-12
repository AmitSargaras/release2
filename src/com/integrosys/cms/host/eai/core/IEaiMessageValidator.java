package com.integrosys.cms.host.eai.core;

import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;

/**
 * Message validator to validate the Message object parsed from the XML file.
 * 
 * @author marvin
 * @author Chong Jun Yong
 * @since 1.1
 */
public interface IEaiMessageValidator {

	/**
	 * Validate the message parsed from the XML file. Mainly validate the
	 * mandatory field and common code checking.
	 * 
	 * @param scimsg the message object parsed from the XML file
	 * @throws EAIMessageValidationException if there is any validation failed
	 */
	public void validate(EAIMessage scimsg) throws EAIMessageValidationException;
}
