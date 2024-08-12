package com.integrosys.cms.host.eai.support;

import java.util.Map;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.host.eai.CMSHeader;
import com.integrosys.cms.host.eai.EAIBody;

/**
 * @author marvin
 * @author Chong Jun Yong	
 * @since 1.1
 */
public class EAIMessageBodyBuilderHelper {
	public static final String EAI_MESSAGE_HANDLER_PREFIX = "integrosys.eai.message.handler.";

	public static EAIBody prepareMessage(String messageType, String messageId, Map hashMap) throws Exception {
		String className = PropertyManager.getValue(EAI_MESSAGE_HANDLER_PREFIX + messageType.toLowerCase() + "."
				+ messageId);
		try {
			IMessageBodyBuilder bodyHandler = (IMessageBodyBuilder) Class.forName(className).newInstance();

			bodyHandler.prepareEAIBody(messageType, messageId, hashMap);

			CMSHeader cmsHeader = new CMSHeader();
			cmsHeader.setMessageId(messageId);
			cmsHeader.setMessageType(messageType);

			// Publish type to get from map
			cmsHeader.setPublishType((String) hashMap.get(IEAIHeaderConstant.PUBLISH_TYPE));
			cmsHeader.setSource(IEAIHeaderConstant.GCMSHDR_SOURCE);

			// Set CMS header after the EAI body, so as to not being overwrite
			// by the respective Message Handler
			
			/*Remove the CMS Header from message according to the template*/
			//bodyHandler.setCMSHeader(cmsHeader);

			return bodyHandler.getEAIBody();

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
