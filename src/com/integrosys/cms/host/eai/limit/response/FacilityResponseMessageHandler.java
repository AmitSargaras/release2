package com.integrosys.cms.host.eai.limit.response;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import com.integrosys.cms.app.limit.bus.IFacilityGeneral;
import com.integrosys.cms.app.limit.bus.IFacilityJdbc;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.proxy.IFacilityProxy;
import com.integrosys.cms.host.eai.EAIHeader;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.core.AbstractInquiryResponseMessageHandler;
import com.integrosys.cms.host.eai.support.IEAIHeaderConstant;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * <p>
 * Message handler to get the response of the facility snapshot of a limit
 * profile.
 * <p>
 * This implementation assume that all the facility and collateral of limit
 * profile has been sent to host system. So the client must have some checking
 * on limit profile to determine whether to call this and get the response
 * required.
 * 
 * @author Chong Jun Yong
 * 
 */
public class FacilityResponseMessageHandler extends AbstractInquiryResponseMessageHandler {

	private static final String MESSAGE_ID_ACKNOLEDGMENT = "AC002";

	private IFacilityProxy facilityProxy;

	private IFacilityJdbc facilityJdbc;

	public IFacilityProxy getFacilityProxy() {
		return facilityProxy;
	}

	public IFacilityJdbc getFacilityJdbc() {
		return facilityJdbc;
	}

	public void setFacilityProxy(IFacilityProxy facilityProxy) {
		this.facilityProxy = facilityProxy;
	}

	public void setFacilityJdbc(IFacilityJdbc facilityJdbc) {
		this.facilityJdbc = facilityJdbc;
	}

	protected EAIMessage doProcessInquiryMessage(EAIMessage eaiMessage) {
		InquiryMessageBody inquiry = (InquiryMessageBody) eaiMessage.getMsgBody();

		long cmsLimitProfileId = inquiry.getCmsLimitProfileId();

		List facilityMasterList = getFacilityProxy().retrieveListOfFacilityMasterByLimitProfileId(cmsLimitProfileId);

		EAIHeader msgHeader = formulateResponseHeader();
		Vector responseFacilityList = prepareResponseFacilityList(facilityMasterList);

		ResponseMessageBody messageBody = new ResponseMessageBody();
		messageBody.setLosAANumber(inquiry.getLosAANumber());
		messageBody.setStpDate(MessageDate.getInstance().getString(inquiry.getStpDate()));
		if (inquiry.getUser() != null) {
			messageBody.setUserId(inquiry.getUser().getLoginID());
			messageBody.setUserName(inquiry.getUser().getUserName());
		}
		messageBody.setFacilityList(responseFacilityList);

		EAIMessage responseMessage = new EAIMessage();
		responseMessage.setMsgHeader(msgHeader);
		responseMessage.setMsgBody(messageBody);

		return responseMessage;
	}

	protected Vector prepareResponseFacilityList(List facilityMasterList) {
		Vector responseFacilityList = new Vector();

		for (Iterator itr = facilityMasterList.iterator(); itr.hasNext();) {
			IFacilityMaster facilityMaster = (IFacilityMaster) itr.next();

			IFacilityGeneral facilityGeneral = getFacilityJdbc()
					.retrieveCancelAndRejectFacilityGeneralInfoByCmsFacilityMasterId(facilityMaster.getId());

			ResponseFacilityMaster facility = new ResponseFacilityMaster();
			facility.setLosLimitId(facilityMaster.getLimit().getLosLimitRef());
			facility.setFacilityStatusEntryCode(facilityGeneral.getFacilityStatusEntryCode());
			facility.setCancelRejectDate(MessageDate.getInstance().getString(facilityGeneral.getCancelOrRejectDate()));

			StandardCode cancelRejectCode = new StandardCode();
			cancelRejectCode.setStandardCodeNumber(facilityGeneral.getCancelOrRejectCategoryCode());
			cancelRejectCode.setStandardCodeValue(facilityGeneral.getCancelOrRejectEntryCode());

			facility.setCancelRejectCode(cancelRejectCode);

			responseFacilityList.add(facility);
		}

		return responseFacilityList;
	}

	protected EAIHeader formulateResponseHeader() {
		EAIHeader responseHeader = new EAIHeader();
		responseHeader.setMessageId(MESSAGE_ID_ACKNOLEDGMENT);
		responseHeader.setMessageType(IEAIHeaderConstant.MESSAGE_TYPE_ACKNOWLEDGMENT);
		responseHeader.setPublishType(IEAIHeaderConstant.PUB_TYPE_NORMAL);
		responseHeader.setMessageRefNum(String.valueOf(Math.abs(new Random().nextInt())));
		responseHeader.setPublishDate(MessageDate.getInstance().getString(new Date()));
		responseHeader.setSource(IEAIHeaderConstant.MESSAGE_SOURCE_CMS);

		return responseHeader;
	}
}
