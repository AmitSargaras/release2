package com.integrosys.cms.host.eai.document.inquiry.template;

import java.util.Date;
import java.util.Vector;

import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.ITemplateItem;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.EAIHeader;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.FieldValueNotAllowedException;
import com.integrosys.cms.host.eai.core.AbstractInquiryResponseMessageHandler;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.document.CheckListTemplateSystemException;
import com.integrosys.cms.host.eai.document.DocumentConstants;
import com.integrosys.cms.host.eai.document.DocumentConstantsCla;
import com.integrosys.cms.host.eai.document.bus.CCTemplate;
import com.integrosys.cms.host.eai.document.bus.SCTemplate;
import com.integrosys.cms.host.eai.support.IEAIHeaderConstant;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * @author Iwan Satria
 */
public class CheckListTemplateInquiryMsgHandlerImpl extends AbstractInquiryResponseMessageHandler {

	private ICheckListTemplateProxyManager templateProxy;

	public void setTemplateProxy(ICheckListTemplateProxyManager templateProxy) {
		this.templateProxy = templateProxy;
	}

	public ICheckListTemplateProxyManager getTemplateProxy() {
		return templateProxy;
	}

	protected EAIMessage doProcessInquiryMessage(EAIMessage eaiMessage) {
		logger.debug("\n\n< --- Response Message Generation --- >");

		EAIMessage response = new EAIMessage();

		CheckListTemplateInquiryMsgBody msgBody = (CheckListTemplateInquiryMsgBody) eaiMessage.getMsgBody();
		EAIHeader msgHeader = eaiMessage.getMsgHeader();
		CheckListTemplateResponseMsgBody msgResponse = new CheckListTemplateResponseMsgBody();

		ChecklistTemplateCriteria ctc = msgBody.getChecklistTemplateCriteria();
		String colCountry = ctc.getCountry();
		String checklistType = ctc.getChecklistType();

		// Generate the Checklist Template and its items and then put them all
		// into the message body.
		try {

			ITemplate rsTemplate = null;
			ITemplateItem[] rsTemplateItem = null;

			if ("SC".equalsIgnoreCase(checklistType)) {
				// Preparing the fields required for querying the CheckList
				// Template
				SCTemplate sc = msgBody.getChecklistTemplateCriteria().getSCTemplate();
				String colType = sc.getSecurityType().getStandardCodeValue();
				String colSubType = sc.getSecuritySubType().getStandardCodeValue();
				String appType = sc.getApplicationType().getStandardCodeValue();
				String goodsStatus = "";
				String pbrInd = "";

				if (ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH.equals(colSubType)) {
					goodsStatus = sc.getGoodsStatus().getStandardCodeValue();
					pbrInd = sc.getPBTPBRInd().getStandardCodeValue();
				}
				rsTemplate = getTemplateProxy().getCollateralTemplate(colType, colSubType, colCountry, appType,
						goodsStatus, pbrInd, "P");
			}
			else if ("CC".equalsIgnoreCase(checklistType)) {
				// Preparing the fields required for querying the CheckList
				// Template
				CCTemplate cc = msgBody.getChecklistTemplateCriteria().getCCTemplate();
				String aLaw = cc.getApplicableLaw();
				String aLegalConstitution = cc.getCustomerClass().getStandardCodeValue();
				String borrowerType = ("B".equals(cc.getCustomerType())) ? ICMSConstant.CHECKLIST_MAIN_BORROWER
						: ICMSConstant.CHECKLIST_PLEDGER;

				// Querying the CheckList Template for CC
				rsTemplate = getTemplateProxy().getCCTemplate(aLaw, aLegalConstitution, colCountry,
						cc.getApplicationType().getStandardCodeValue(), borrowerType, "P");

			}
			else {
				throw new FieldValueNotAllowedException("Checklist Type", checklistType,
						DocumentConstantsCla.getChecklistTypes());
			}

			CheckListTemplate ct = new CheckListTemplate();
			if (rsTemplate != null) {
				ct.setTemplateID(rsTemplate.getTemplateID());
			}
			msgResponse.setCheckListTemplate(ct);

			// Querying and Generating the CheckList Template Items
			Vector items = new Vector();
			if (rsTemplate != null) {
				rsTemplateItem = rsTemplate.getTemplateItemList();
				if (rsTemplateItem != null) {
					for (int i = 0; i < rsTemplateItem.length; i++) {
						ITemplateItem ob = rsTemplateItem[i];
						ChecklistTemplateItem cti = new ChecklistTemplateItem();

						cti.setDocCode(ob.getItemCode());
						cti.setDescription(ob.getItemDesc());
						if (ob.getIsMandatoryInd()) {
							cti.setMandatoryInd(ICMSConstant.TRUE_VALUE);
						}
						else {
							cti.setMandatoryInd(ICMSConstant.FALSE_VALUE);
						}
						cti.setExpiryDate(MessageDate.getInstance().getString(ob.getItem().getExpiryDate()));

						items.add(cti);
					}
				}
				;
			}
			msgResponse.setChecklistTemplateItem(items);

		}
		catch (CheckListTemplateException cte) {
			throw new CheckListTemplateSystemException("failed to retrieve checklist template", cte);
		}

		EAIHeader mh = new EAIHeader();
		mh.setMessageId(IEAIHeaderConstant.DOCUMENT_DC001R);
		mh.setMessageType(IEaiConstant.CHECKLIST_TEMPLATE_INQUIRY);
		mh.setPublishType(IEaiConstant.NORMAL_INDICATOR);
		mh.setPublishDate(MessageDate.getInstance().getString(new Date()));
		mh.setSource(IEAIHeaderConstant.MESSAGE_SOURCE_CMS);
		mh.setMessageRefNum(msgHeader.getMessageRefNum());

		response.setMsgHeader(mh);
		response.setMsgBody(msgResponse);

		return response;
	}

}
