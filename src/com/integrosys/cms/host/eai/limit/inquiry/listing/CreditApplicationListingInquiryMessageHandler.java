package com.integrosys.cms.host.eai.limit.inquiry.listing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.integrosys.cms.host.eai.EAIHeader;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.core.AbstractInquiryResponseMessageHandler;
import com.integrosys.cms.host.eai.customer.NoSuchCustomerException;
import com.integrosys.cms.host.eai.customer.bus.CustomerIdInfo;
import com.integrosys.cms.host.eai.customer.bus.ICustomerDao;
import com.integrosys.cms.host.eai.customer.bus.MainProfile;
import com.integrosys.cms.host.eai.customer.bus.SubProfile;
import com.integrosys.cms.host.eai.limit.bus.ILimitDao;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.eai.support.IEAIHeaderConstant;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * Inquiry message handler for inquiry on a list of credit application match the
 * criteria passed in
 * 
 * @author Chong Jun Yong
 * 
 */
public class CreditApplicationListingInquiryMessageHandler extends AbstractInquiryResponseMessageHandler {

	private static final String RESPONSE_MESSAGE_ID = "CA002R";

	private ILimitDao limitDao;

	private ICustomerDao customerDao;

	public ILimitDao getLimitDao() {
		return limitDao;
	}

	public ICustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setLimitDao(ILimitDao limitDao) {
		this.limitDao = limitDao;
	}

	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	protected EAIMessage doProcessInquiryMessage(EAIMessage eaiMessage) {
		CreditApplicationListingInquiryMessageBody msgBody = (CreditApplicationListingInquiryMessageBody) eaiMessage
				.getMsgBody();
		SearchCriteria searchCriteria = msgBody.getCreditApplicationSearchCritiria();

		CustomerIdInfo customerIdInfo = searchCriteria.getCustomerIdInfo();
		OtherCriteria otherCriteria = searchCriteria.getOtherCriteria();

		List customerList = Collections.EMPTY_LIST;
		if (otherCriteria == null) {
			customerList = getCustomerDao().searchCustomerByCustomerIdInfo(customerIdInfo);
		}
		else {
			if (StringUtils.isNotBlank(otherCriteria.getShortCustomerName())
					|| StringUtils.isNotBlank(otherCriteria.getCifSource())
					|| StringUtils.isNotBlank(otherCriteria.getCifId())) {
				customerList = getCustomerDao().searchCustomerByCustomerIdInfoAndShortNameAndCifSource(customerIdInfo,
						otherCriteria.getShortCustomerName(), otherCriteria.getCifSource(), otherCriteria.getCifId());
			}
			else {
				customerList = getCustomerDao().searchCustomerByCustomerIdInfo(customerIdInfo);
			}
		}

		if (customerList.isEmpty()) {
			throw new NoSuchCustomerException(customerIdInfo);
		}

		// use to search limit profile
		List cmsSubProfileIdList = new ArrayList();
		// use for the response, ie for the aa, which main profile is it tied to
		Map cmsSubProfileIdMainProfileMap = new HashMap();

		for (Iterator itr = customerList.iterator(); itr.hasNext();) {
			MainProfile mainProfile = (MainProfile) itr.next();
			Map parameters = new HashMap();
			parameters.put("cmsMainProfileId", new Long(mainProfile.getCmsId()));

			List subProfileList = getCustomerDao().retrieveObjectsListByParameters(parameters, SubProfile.class);
			for (Iterator itrSubProfile = subProfileList.iterator(); itrSubProfile.hasNext();) {
				SubProfile customer = (SubProfile) itrSubProfile.next();
				cmsSubProfileIdList.add(new Long(customer.getCmsId()));
				cmsSubProfileIdMainProfileMap.put(new Long(customer.getCmsId()), mainProfile);
			}
		}

		// to retrieve limit profile information based on previous result
		String hostAANumber = null;
		String aaType = null;
		String aaLawType = null;
		if (otherCriteria != null) {
			hostAANumber = otherCriteria.getHostAANumber();
			aaType = otherCriteria.getApplicationType();
			aaLawType = otherCriteria.getApplicationLawType();
		}

		List responseCreditApplications = new ArrayList();

		List applications = getLimitDao()
				.searchLimitProfileByHostApplicationNumberAndApplicationTypeAndApplicationLawType(hostAANumber, aaType,
						aaLawType, cmsSubProfileIdList);
		for (Iterator itr = applications.iterator(); itr.hasNext();) {
			LimitProfile limitProfile = (LimitProfile) itr.next();
			CreditApplication creditApplication = new CreditApplication(limitProfile);
			creditApplication.setMainProfile((MainProfile) cmsSubProfileIdMainProfileMap.get(new Long(limitProfile
					.getCmsSubProfileId())));

			responseCreditApplications.add(creditApplication);
		}

		return prepareResponseMessage(responseCreditApplications, eaiMessage.getMsgHeader());
	}

	/**
	 * Prepare the response message based on the list of credit application
	 * queried from the system and the header in the inquiry message
	 * 
	 * @param responseCreditApplications list of credit application queried from
	 *        the system
	 * @param msgHeader the message header in the inquiry message
	 * @return response message ready to sent back to the caller
	 */
	private EAIMessage prepareResponseMessage(List responseCreditApplications, EAIHeader msgHeader) {
		EAIHeader responseHeader = new EAIHeader();
		responseHeader.setMessageId(RESPONSE_MESSAGE_ID);
		responseHeader.setMessageRefNum(msgHeader.getMessageRefNum());
		responseHeader.setMessageType(msgHeader.getMessageType());
		responseHeader.setPublishType(IEAIHeaderConstant.PUB_TYPE_NORMAL);
		responseHeader.setSource(IEAIHeaderConstant.MESSAGE_SOURCE_CMS);
		responseHeader.setPublishDate(MessageDate.getInstance().getString(new Date()));

		CreditApplicationListingResponseMessageBody msgBody = new CreditApplicationListingResponseMessageBody();
		msgBody.setCreditApplications(responseCreditApplications);

		EAIMessage responseMessage = new EAIMessage();
		responseMessage.setMsgHeader(responseHeader);
		responseMessage.setMsgBody(msgBody);

		return responseMessage;
	}
}
