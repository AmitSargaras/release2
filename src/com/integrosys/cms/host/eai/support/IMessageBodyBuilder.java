package com.integrosys.cms.host.eai.support;

import java.util.Map;

import com.integrosys.cms.host.eai.EAIBody;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public interface IMessageBodyBuilder {

	void prepareEAIBody(String messageType, String messageId, Map map);

	EAIBody getEAIBody();
}
