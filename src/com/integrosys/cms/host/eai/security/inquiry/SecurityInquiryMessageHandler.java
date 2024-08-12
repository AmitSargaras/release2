package com.integrosys.cms.host.eai.security.inquiry;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.integrosys.cms.host.eai.EAIHeader;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.core.AbstractInquiryResponseMessageHandler;
import com.integrosys.cms.host.eai.security.NoSuchSecurityException;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.ISecurityDao;
import com.integrosys.cms.host.eai.security.bus.SecurityPledgorMap;
import com.integrosys.cms.host.eai.support.IEAIHeaderConstant;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * Security Inquiry message handler to handle the inquiry of security stored in
 * CMS, the inquiry must assume that the key provided in the inquiry message is
 * a valid key in CMS system, as error will be raised.
 * 
 * @author Chong Jun Yong
 * 
 */
public class SecurityInquiryMessageHandler extends AbstractInquiryResponseMessageHandler {

	private static final String SECURITY_INQUIRY_MESSAGE_TYPE = "SECURITY.INQUIRY";

	private ISecurityDao securityDao;

	private Map securityTypeDetailsPopulaterMap;

	private Map securityTypeMessageIdMap;

	public ISecurityDao getSecurityDao() {
		return securityDao;
	}

	public Map getSecurityTypeDetailsPopulaterMap() {
		return securityTypeDetailsPopulaterMap;
	}

	public Map getSecurityTypeMessageIdMap() {
		return securityTypeMessageIdMap;
	}

	public void setSecurityDao(ISecurityDao securityDao) {
		this.securityDao = securityDao;
	}

	/**
	 * <p>
	 * Mapping between the security type and
	 * <code>SecuritySubtypeDetailsPopulator<code> implementation
	 * @param securityTypeDetailsPopulaterMap the map between security type code
	 *        and the implementation of
	 *        <code>SecuritySubtypeDetailsPopulator<code>
	 */
	public void setSecurityTypeDetailsPopulaterMap(Map securityTypeDetailsPopulaterMap) {
		this.securityTypeDetailsPopulaterMap = securityTypeDetailsPopulaterMap;
	}

	/**
	 * <p>
	 * Mapping between the security type and response message id.
	 * <p>
	 * Key is the security type, such AB, PT, etc. Value is the response message
	 * id, such CO001R, CO002R
	 * @param securityTypeMessageIdMap the map between security type code and
	 *        response message id
	 */
	public void setSecurityTypeMessageIdMap(Map securityTypeMessageIdMap) {
		this.securityTypeMessageIdMap = securityTypeMessageIdMap;
	}

	protected EAIMessage doProcessInquiryMessage(EAIMessage eaiMessage) {
		EAIHeader inquiryHeader = eaiMessage.getMsgHeader();
		SecurityInquiryMessageBody msgBody = (SecurityInquiryMessageBody) eaiMessage.getMsgBody();

		SecurityInquiry inquiry = msgBody.getSecurityInquiry();

		ApprovedSecurity sec = (ApprovedSecurity) getSecurityDao().retrieve(new Long(inquiry.getCmsSecurityId()),
				ApprovedSecurity.class);
		if (sec == null) {
			throw new NoSuchSecurityException(inquiry.getCmsSecurityId());
		}

		// message body
		SecurityMessageBody securityMsgBody = new SecurityMessageBody();
		securityMsgBody.setSecurityDetail(sec);

		// populate security sub type details
		prepareSecuritySubtypeDetails(sec, securityMsgBody);

		// populate pledgor details
		preparePledgorDetails(sec, securityMsgBody);

		// message header
		EAIHeader responseHeader = new EAIHeader();
		responseHeader.setMessageId((String) getSecurityTypeMessageIdMap().get(
				sec.getSecurityType().getStandardCodeValue()));
		responseHeader.setMessageRefNum(inquiryHeader.getMessageRefNum());
		responseHeader.setMessageType(SECURITY_INQUIRY_MESSAGE_TYPE);
		responseHeader.setPublishDate(MessageDate.getInstance().getString(new Date()));
		responseHeader.setPublishType(inquiryHeader.getPublishType());
		responseHeader.setSource(IEAIHeaderConstant.MESSAGE_SOURCE_CMS);

		// response message
		EAIMessage response = new EAIMessage();
		response.setMsgBody(securityMsgBody);
		response.setMsgHeader(responseHeader);

		return response;
	}

	/**
	 * <p>
	 * Prepare the security subtype details based on the common security
	 * supplied, and populate into message body.
	 * <p>
	 * This might include, valuation, insurance policy, to be popluated into the
	 * message body which is not applied to all the collateral type.
	 * @param sec common security details
	 * @param securityMsgBody security message body to be responsed back
	 */
	private void prepareSecuritySubtypeDetails(ApprovedSecurity sec, SecurityMessageBody securityMsgBody) {
		String securityType = sec.getSecurityType().getStandardCodeValue();
		SecuritySubtypeDetailsPopulator subtypeDetailsPopulator = (SecuritySubtypeDetailsPopulator) getSecurityTypeDetailsPopulaterMap()
				.get(securityType);
		subtypeDetailsPopulator.prepareSecuritySubtypeDetails(sec, securityMsgBody);
	}

	/**
	 * <p>
	 * Prepare the pledgor details based on the common security supplied and
	 * populate into message body.
	 * <p>
	 * First security pledgor linkage will be retrieved first, then pledgor
	 * details will be retrieved based on the linkages.
	 * @param sec common security details
	 * @param securityMsgBody security message body to be responsed back
	 */
	private void preparePledgorDetails(ApprovedSecurity sec, SecurityMessageBody securityMsgBody) {
		Map parameters = new HashMap();
		parameters.put("CMSSecurityId", new Long(sec.getCMSSecurityId()));

		Set pledgorIdSet = new HashSet();
		List secPledgorMapList = getSecurityDao().retrieveObjectsListByParameters(parameters, SecurityPledgorMap.class);
		if (secPledgorMapList != null && !secPledgorMapList.isEmpty()) {
			for (Iterator itr = secPledgorMapList.iterator(); itr.hasNext();) {
				SecurityPledgorMap secPledgorMap = (SecurityPledgorMap) itr.next();
				pledgorIdSet.add(secPledgorMap.getCMSPledgorId());
			}

			List pledgorList = getSecurityDao().retrievePledgorsByCmsPledgorIds(pledgorIdSet);
			Vector pledgorDetails = new Vector(pledgorList);
			securityMsgBody.setPledgor(pledgorDetails);
		}
	}
}
