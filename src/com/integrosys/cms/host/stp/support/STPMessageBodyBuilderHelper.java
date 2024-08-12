package com.integrosys.cms.host.stp.support;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.host.eai.CMSHeader;
import com.integrosys.cms.host.eai.support.IEAIHeaderConstant;
import com.integrosys.cms.host.stp.STPBody;

import java.util.Map;

/**
 * @author marvin
 * @author Chong Jun Yong
 * @since 1.1
 */
public class STPMessageBodyBuilderHelper {
	public static final String STP_MESSAGE_HANDLER_PREFIX = "integrosys.stp.message.handler.";

	public static STPBody prepareMessage(String messageType, String messageId, Map hashMap) throws Exception {
		String className = PropertyManager.getValue(STP_MESSAGE_HANDLER_PREFIX + messageType.toLowerCase() + "."
				+ messageId);
		try {
			IMessageBodyBuilder bodyHandler = (IMessageBodyBuilder) Class.forName(className).newInstance();

			bodyHandler.prepareSTPBody(messageType, messageId, hashMap);

			CMSHeader cmsHeader = new CMSHeader();
			cmsHeader.setMessageId(messageId);
			cmsHeader.setMessageType(messageType);

			// Publish type to get from map
			cmsHeader.setPublishType((String) hashMap.get(ISTPHeaderConstant.PUBLISH_TYPE));
			cmsHeader.setSource(ISTPHeaderConstant.GCMSHDR_SOURCE);

			// Set CMS header after the EAI body, so as to not being overwrite
			// by the respective Message Handler

			/*Remove the CMS Header from message according to the template*/
			//bodyHandler.setCMSHeader(cmsHeader);

			return bodyHandler.getSTPBody();

		}
		catch (ClassNotFoundException cnfe) {
			throw new Exception("Creating Message Body Handler for EAI " + messageType + ":" + messageId
					+ ": , cannot Find Class [" + className + "] .");
		}
		catch (InstantiationException ie) {
			throw new Exception("Creating Message Body Handler for EAI " + messageType + ":" + messageId
					+ ": , Unable to Instantiate Class : [" + className + "] .");
		}
		catch (IllegalAccessException iae) {
			throw new Exception("Creating Message Body Handler for EAI " + messageType + ":" + messageId
					+ ": , Unable to Instantiate Class : [" + className + "] .");
		}

	}
}