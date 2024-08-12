package com.integrosys.cms.host.eai.document.inquiry.checklist;

import java.util.Date;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.EAIHeader;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.FieldValueNotAllowedException;
import com.integrosys.cms.host.eai.core.AbstractInquiryResponseMessageHandler;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.customer.NoSuchCustomerException;
import com.integrosys.cms.host.eai.customer.bus.SubProfile;
import com.integrosys.cms.host.eai.document.ChecklistSystemException;
import com.integrosys.cms.host.eai.document.DocumentConstants;
import com.integrosys.cms.host.eai.document.DocumentConstantsCla;
import com.integrosys.cms.host.eai.document.NoSuchCheckListException;
import com.integrosys.cms.host.eai.document.bus.CCChecklist;
import com.integrosys.cms.host.eai.document.bus.CheckList;
import com.integrosys.cms.host.eai.document.bus.CheckListItem;
import com.integrosys.cms.host.eai.document.bus.IDocumentDao;
import com.integrosys.cms.host.eai.document.bus.IDocumentDaoJdbc;
import com.integrosys.cms.host.eai.document.bus.SCChecklist;
import com.integrosys.cms.host.eai.limit.NoSuchLimitProfileException;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.eai.security.bus.Pledgor;
import com.integrosys.cms.host.eai.support.IEAIHeaderConstant;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * Document Checklist inquiry message handler
 * 
 * @author Iwan Satria
 * @author Chong Jun Yong
 */
public class DocChecklistInquiryMsgHandlerImpl extends AbstractInquiryResponseMessageHandler {

	private IDocumentDaoJdbc documentJdbcDao;

	private IDocumentDao documentDao;

	private ICheckListProxyManager checkListProxyManager;

	public IDocumentDaoJdbc getDocumentJdbcDao() {
		return documentJdbcDao;
	}

	public IDocumentDao getDocumentDao() {
		return documentDao;
	}

	public ICheckListProxyManager getCheckListProxyManager() {
		return checkListProxyManager;
	}

	public void setDocumentJdbcDao(IDocumentDaoJdbc documentJdbcDao) {
		this.documentJdbcDao = documentJdbcDao;
	}

	public void setDocumentDao(IDocumentDao documentDao) {
		this.documentDao = documentDao;
	}

	public void setCheckListProxyManager(ICheckListProxyManager checkListProxyManager) {
		this.checkListProxyManager = checkListProxyManager;
	}

	protected EAIMessage doProcessInquiryMessage(EAIMessage msg) {
		EAIMessage response = new EAIMessage();

		DocChecklistInquiryMsgBody msgBody = (DocChecklistInquiryMsgBody) msg.getMsgBody();
		DocChecklistResponseMsgBody msgResponse = new DocChecklistResponseMsgBody();

		ChecklistCriteria criteria = msgBody.getChecklistCriteria();
		String checklistType = criteria.getChecklistType();

		// Generate the Checklist Template and its items and then put them all
		// into the message body.

		ICheckList cmsCheckList = null;
		ICheckListItem[] rsCheckListItem = null;

		if (criteria.getCMSChecklistID() > 0) {
			try {
				cmsCheckList = getCheckListProxyManager().getCheckListByID(criteria.getCMSChecklistID());
			}
			catch (CheckListException ex) {
				logger.warn("No checklist found for checklist id provided [" + criteria.getCMSChecklistID()
						+ "], skipped to do the normal flow", ex);
				cmsCheckList = null;
			}
		}

		if (cmsCheckList == null) {
			LimitProfile limitProfile = getDocumentJdbcDao().retrieveLimitByAANumber(criteria.getLOSAANumber());

			if (limitProfile == null) {
				throw new NoSuchLimitProfileException(criteria.getLOSAANumber());
			}

			if (DocumentConstants.CHECKLIST_TYPE_COLLATERAL.equalsIgnoreCase(checklistType)) {
				SCChecklist sc = criteria.getSCChecklist();

				CheckList collateralCheckList = getDocumentDao()
						.retrieveCollateralCheckListByCmsLimitProfileIdAndCmsCollateralId(
								limitProfile.getLimitProfileId(), sc.getCmsCollateralID());

				if (collateralCheckList == null) {
					throw new NoSuchCheckListException(criteria.getLOSAANumber(), sc.getCmsCollateralID());
				}

				try {
					cmsCheckList = getCheckListProxyManager().getCheckListByID(collateralCheckList.getCheckListID());
				}
				catch (CheckListException e) {
					throw new ChecklistSystemException("failed to retrieve existing checklist, checklist id ["
							+ collateralCheckList.getCheckListID() + "]", e);
				}

			}
			else if (DocumentConstants.CHECKLIST_TYPE_BORROWER_PLEDGOR.equalsIgnoreCase(checklistType)) {
				CCChecklist cc = criteria.getCCChecklist();
				String customerType = cc.getCustomerType();
				String cifNumber = cc.getCIFNo();

				if (ArrayUtils.contains(DocumentConstantsCla.getChecklistCategoryBorrower(), customerType)) {
					SubProfile customer = getDocumentJdbcDao().retrieveSubProfileByCIFNumber(cifNumber);

					if (customer == null) {
						throw new NoSuchCustomerException(cifNumber);
					}

					CheckList borrowerCheckList = getDocumentDao()
							.retrieveBorrowerCheckListByCmsLimitProfileIdAndCmsCustomerId(
									limitProfile.getLimitProfileId(), customer.getCmsId(), customerType);

					if (borrowerCheckList == null) {
						throw new NoSuchCheckListException(criteria.getLOSAANumber(), cifNumber, customerType);
					}

					try {
						cmsCheckList = getCheckListProxyManager().getCheckListByID(borrowerCheckList.getCheckListID());
					}
					catch (CheckListException e) {
						throw new ChecklistSystemException("failed to retrieve existing checklist, checklist id ["
								+ borrowerCheckList.getCheckListID() + "]", e);
					}

				}
				else if (ArrayUtils.contains(DocumentConstantsCla.getChecklistCategoryPledgor(), customerType)) {
					Pledgor pledgor = getDocumentJdbcDao().retrievePledgorByAAAndCIF(criteria.getLOSAANumber(),
							cifNumber);

					if (pledgor == null) {
						throw new NoSuchCustomerException(cifNumber);
					}

					CheckList pledgorCheckList = getDocumentDao()
							.retrievePledgorCheckListByCmsLimitProfileIdAndCmsPledgorId(
									limitProfile.getLimitProfileId(), pledgor.getCmsId());

					if (pledgorCheckList == null) {
						throw new NoSuchCheckListException(criteria.getLOSAANumber(), cifNumber, customerType);
					}

					try {
						cmsCheckList = getCheckListProxyManager().getCheckListByID(pledgorCheckList.getCheckListID());
					}
					catch (CheckListException e) {
						throw new ChecklistSystemException("failed to retrieve existing checklist, checklist id ["
								+ pledgorCheckList.getCheckListID() + "]", e);
					}
				}
			}
			else {
				throw new FieldValueNotAllowedException("CheckList Type", checklistType,
						DocumentConstantsCla.getChecklistTypes());
			}
		}

		CheckList ct = new CheckList();
		ct.setCheckListID(cmsCheckList.getCheckListID());
		ct.setTemplateID(cmsCheckList.getTemplateID());
		msgResponse.setCheckList(ct);

		rsCheckListItem = cmsCheckList.getCheckListItemList();
		Vector items = new Vector();
		if (rsCheckListItem != null) {
			for (int i = 0; i < rsCheckListItem.length; i++) {
				ICheckListItem ob = rsCheckListItem[i];
				if (!ob.getIsPreApprove()) {
					continue;
				}

				CheckListItem cti = new CheckListItem();
				cti.setCmsDocItemID(ob.getCheckListItemID());
				cti.setDocCode(ob.getItemCode());
				cti.setDocNo(ob.getCheckListItemRef());
				cti.setMandatoryInd(ob.getIsMandatoryInd() ? ICMSConstant.TRUE_VALUE.charAt(0)
						: ICMSConstant.FALSE_VALUE.charAt(0));
				cti.setDescription(ob.getItemDesc());
				cti.setExpiryDate(MessageDate.getInstance().getString(ob.getExpiryDate()));
				cti.setStatus(ob.getItemStatus());
				cti.setReceivedDate(MessageDate.getInstance().getString(ob.getReceivedDate()));
				cti.setWaivedDate(MessageDate.getInstance().getString(ob.getWaivedDate()));
				items.add(cti);
			}
		}
		msgResponse.setCheckListItem(items);

		EAIHeader mh = new EAIHeader();
		mh.setMessageId(IEAIHeaderConstant.DOCUMENT_DC002R);
		mh.setMessageType(IEaiConstant.CHECKLIST_DOCUMENT_INQUIRY);
		mh.setPublishType(msg.getMsgHeader().getPublishType());
		mh.setMessageRefNum(msg.getMsgHeader().getMessageRefNum());
		mh.setPublishDate(MessageDate.getInstance().getString(new Date()));
		mh.setSource(IEAIHeaderConstant.MESSAGE_SOURCE_CMS);

		response.setMsgHeader(mh);
		response.setMsgBody(msgResponse);

		return response;
	}
}
