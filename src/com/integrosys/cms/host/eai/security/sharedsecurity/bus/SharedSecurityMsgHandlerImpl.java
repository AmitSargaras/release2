package com.integrosys.cms.host.eai.security.sharedsecurity.bus;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

import com.integrosys.cms.host.eai.EAIHeader;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageException;
import com.integrosys.cms.host.eai.core.AbstractInquiryResponseMessageHandler;
import com.integrosys.cms.host.eai.security.sharedsecurity.SharedSecurityInquiryMsgBody;
import com.integrosys.cms.host.eai.security.sharedsecurity.SharedSecurityResponseMsgBody;
import com.integrosys.cms.host.eai.support.IEAIHeaderConstant;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * Message Handler to do response for the shared security inquiry
 * 
 * @author Iwan
 * @author Chong Jun Yong
 */
public class SharedSecurityMsgHandlerImpl extends AbstractInquiryResponseMessageHandler {

	private Map securitySubTypeCodeSearchHandlerMap;

	private SharedSecuritySearchHandler defaultSharedSecuritySearchHandler;

	/**
	 * The key is the security sub type code, value is the search handler
	 * instance
	 * 
	 * @param securitySubTypeCodeSearchHandlerMap
	 */
	public void setSecuritySubTypeCodeSearchHandlerMap(Map securitySubTypeCodeSearchHandlerMap) {
		this.securitySubTypeCodeSearchHandlerMap = securitySubTypeCodeSearchHandlerMap;
	}

	public void setDefaultSharedSecuritySearchHandler(SharedSecuritySearchHandler defaultSharedSecuritySearchHandler) {
		this.defaultSharedSecuritySearchHandler = defaultSharedSecuritySearchHandler;
	}

	protected EAIMessage doProcessInquiryMessage(EAIMessage msg) throws EAIMessageException {
		logger.debug("\n\n< --- Response Message Generation --- >");

		SharedSecurityInquiryMsgBody inquiryMsgBody = (SharedSecurityInquiryMsgBody) msg.getMsgBody();
		SharedSecuritySearch shareSecuritySearch = inquiryMsgBody.getSharedSecuritySearch();

		SharedSecuritySearchHandler searchHandler = (SharedSecuritySearchHandler) this.securitySubTypeCodeSearchHandlerMap
				.get(shareSecuritySearch.getSecuritySubType().getStandardCodeValue());
		if (searchHandler == null) {
			searchHandler = this.defaultSharedSecuritySearchHandler;
		}

		SharedSecurityResultItem[] result = searchHandler.searchAndGenerateSharedSecurity(shareSecuritySearch);

		EAIMessage response = new EAIMessage();
		EAIHeader header = formulateResponseHeader(msg);
		response.setMsgHeader(header);

		SharedSecurityResponseMsgBody msgBody = new SharedSecurityResponseMsgBody();
		msgBody.setSharedSecurityResultItem(new Vector(Arrays.asList(result)));

		response.setMsgBody(msgBody);

		return response;
	}

	protected EAIMessage responseEmptyBodyMessage(EAIMessage msg) {
		EAIMessage response = new EAIMessage();

		EAIHeader header = formulateResponseHeader(msg);
		response.setMsgHeader(header);

		SharedSecurityResponseMsgBody msgBody = new SharedSecurityResponseMsgBody();

		SharedSecurityResultItem emptyResult = new SharedSecurityResultItem();
		Vector resultVector = new Vector();
		resultVector.add(emptyResult);

		msgBody.setSharedSecurityResultItem(resultVector);

		response.setMsgBody(msgBody);

		return response;
	}

	protected EAIHeader formulateResponseHeader(EAIMessage msg) {
		EAIHeader mh = msg.getMsgHeader();

		EAIHeader responseHeader = new EAIHeader();
		responseHeader.setMessageId(IEAIHeaderConstant.SHARED_SECURITY_RESPONSE);
		responseHeader.setMessageType(mh.getMessageType());
		responseHeader.setPublishType(mh.getPublishType());
		responseHeader.setMessageRefNum(mh.getMessageRefNum());
		responseHeader.setPublishDate(MessageDate.getInstance().getString(new Date()));
		responseHeader.setSource(IEAIHeaderConstant.MESSAGE_SOURCE_CMS);

		return responseHeader;
	}
}
