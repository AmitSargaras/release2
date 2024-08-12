package com.integrosys.cms.host.eai.covenant.bus;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.integrosys.cms.host.eai.EAIHeader;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.MandatoryFieldMissingException;
import com.integrosys.cms.host.eai.core.AbstractInquiryResponseMessageHandler;
import com.integrosys.cms.host.eai.covenant.ConvenantInquireMessageBody;
import com.integrosys.cms.host.eai.covenant.CovenantResponseMsgBody;
import com.integrosys.cms.host.eai.covenant.NoSuchCovenantException;
import com.integrosys.cms.host.eai.covenant.NoSuchRecurrentDocException;
import com.integrosys.cms.host.eai.customer.NoSuchCustomerException;
import com.integrosys.cms.host.eai.customer.bus.SubProfile;
import com.integrosys.cms.host.eai.limit.NoSuchLimitProfileException;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.eai.support.IEAIHeaderConstant;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * 
 * @author Thurein</br>
 * @since 1.1</br>
 * @version 1.0</br> Message Handler that responsible for the convenant inquire
 *          message. Retrieve the convenants data by using LOSAANumber.
 * 
 */
public class CovenantInquiryMsgHandlerImpl extends AbstractInquiryResponseMessageHandler {

	private ICovenantDao covenantDao;

	public ICovenantDao getCovenantDao() {
		return covenantDao;
	}

	public void setCovenantDao(ICovenantDao covenantDao) {
		this.covenantDao = covenantDao;
	}

	protected EAIMessage doProcessInquiryMessage(EAIMessage eaiMessage) {

		logger.debug("\n\n< --- Response Message Generation --- >");
		ConvenantInquireMessageBody inquireMsgBody = (ConvenantInquireMessageBody) eaiMessage.getMsgBody();

		String losAANumber;

		LimitProfile storedLimitProfile;

		SubProfile storedSubProfile;

		RecurrentDoc storedRecurrentDoc;

		try {
			losAANumber = inquireMsgBody.getCovenant().getLOSAANumber();
		}
		catch (NullPointerException nex) {
			throw new MandatoryFieldMissingException("Covenant - LOS AA Number");
		}

		storedLimitProfile = getCovenantDao().getLimitProfile(losAANumber);

		if (storedLimitProfile == null) {
			throw new NoSuchLimitProfileException(losAANumber);
		}

		storedSubProfile = getCovenantDao().getSubProfile(storedLimitProfile.getCIFId());
		if (storedSubProfile == null) {
			throw new NoSuchCustomerException(storedLimitProfile.getCIFId(), storedLimitProfile.getCIFSource());
		}

		storedRecurrentDoc = getCovenantDao().getRecurrentDoc(storedLimitProfile.getLimitProfileId(),
				storedSubProfile.getCmsId(), RecurrentDoc.class);
		if (storedRecurrentDoc == null) {
			throw new NoSuchRecurrentDocException(losAANumber, storedLimitProfile.getCIFId());
		}

		List convenantsListResult = getCovenantDao().getConvenantItemByRecurrentDocID(
				storedRecurrentDoc.getRecurrentDocID(), CovenantItem.class);
		if (convenantsListResult.isEmpty() || convenantsListResult.size() < 1) {
			throw new NoSuchCovenantException("Can not find the covenant with the recurrent doc id ["
					+ storedRecurrentDoc.getRecurrentDocID() + "]");
		}

		EAIMessage response = new EAIMessage();

		EAIHeader header = formulateResponseHeader(eaiMessage);

		response.setMsgHeader(header);

		CovenantResponseMsgBody msgBody = new CovenantResponseMsgBody();

		msgBody.setConvenantItem(prepareResult(convenantsListResult));

		response.setMsgBody(msgBody);

		return response;

	}

	private Vector prepareResult(List convenantsList) {
		Vector covResultVec = new Vector();
		Iterator iter = convenantsList.iterator();
		while (iter.hasNext()) {
			covResultVec.addElement(iter.next());
		}
		return covResultVec;
	}

	protected EAIHeader formulateResponseHeader(EAIMessage msg) {
		EAIHeader mh = msg.getMsgHeader();

		EAIHeader responseHeader = new EAIHeader();
		responseHeader.setMessageId(IEAIHeaderConstant.CONVENANT_RESPONSE);
		responseHeader.setMessageType(mh.getMessageType());
		responseHeader.setPublishType(mh.getPublishType());
		responseHeader.setMessageRefNum(mh.getMessageRefNum());
		responseHeader.setPublishDate(MessageDate.getInstance().getString(new Date()));
		responseHeader.setSource(IEAIHeaderConstant.MESSAGE_SOURCE_CMS);

		return responseHeader;
	}
}
