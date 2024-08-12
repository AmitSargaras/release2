package com.integrosys.cms.host.stp.support;

import com.integrosys.cms.host.eai.EAIBody;
import com.integrosys.cms.host.stp.STPBody;

import java.util.Map;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public interface IMessageBodyBuilder {

	void prepareSTPBody(String messageType, String messageId, Map map);

	STPBody getSTPBody();
}