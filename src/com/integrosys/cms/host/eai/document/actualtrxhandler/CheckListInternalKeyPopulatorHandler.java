package com.integrosys.cms.host.eai.document.actualtrxhandler;

import java.util.Iterator;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.FieldValueNotAllowedException;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.customer.NoSuchCustomerException;
import com.integrosys.cms.host.eai.customer.bus.SubProfile;
import com.integrosys.cms.host.eai.document.DocumentConstants;
import com.integrosys.cms.host.eai.document.DocumentConstantsCla;
import com.integrosys.cms.host.eai.document.DocumentInputMessageBody;
import com.integrosys.cms.host.eai.document.bus.CCChecklist;
import com.integrosys.cms.host.eai.document.bus.CheckList;
import com.integrosys.cms.host.eai.document.bus.CheckListItem;
import com.integrosys.cms.host.eai.document.bus.IDocumentDao;
import com.integrosys.cms.host.eai.document.bus.IDocumentDaoJdbc;
import com.integrosys.cms.host.eai.document.bus.SCChecklist;
import com.integrosys.cms.host.eai.limit.NoSuchLimitProfileException;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.eai.security.NoSuchPledgorException;
import com.integrosys.cms.host.eai.security.NoSuchSecurityException;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.Pledgor;

/**
 * <p>
 * Second handler to populate the internal key of the AA, customer/pledgor,
 * collateral id, and possible the checklist item id based on the doc number
 * (reference number) pre approval indicator <code>(Y)</code> will be populated
 * into checklist item.
 * <p>
 * At this stage, cms checklist cannot be retrieved, which supposed to be done
 * in next step, to complete the checklist itself, and also the checklist items.
 * 
 * @author Chong Jun Yong
 * 
 */
public class CheckListInternalKeyPopulatorHandler extends AbstractCommonActualTrxHandler {

	private IDocumentDao documentDao;

	private IDocumentDaoJdbc documentJdbcDao;

	public IDocumentDao getDocumentDao() {
		return documentDao;
	}

	public IDocumentDaoJdbc getDocumentJdbcDao() {
		return documentJdbcDao;
	}

	public void setDocumentDao(IDocumentDao documentDao) {
		this.documentDao = documentDao;
	}

	public void setDocumentJdbcDao(IDocumentDaoJdbc documentJdbcDao) {
		this.documentJdbcDao = documentJdbcDao;
	}

	public Message persistActualTrx(Message msg) {
		EAIMessage eaiMessage = (EAIMessage) msg;

		DocumentInputMessageBody msgBody = (DocumentInputMessageBody) eaiMessage.getMsgBody();
		CheckList checkList = msgBody.getCheckList();

		String checklistType = checkList.getChecklistType();

		LimitProfile limitProfile = getDocumentJdbcDao().retrieveLimitByAANumber(checkList.getAANumber());

		if (limitProfile == null) {
			throw new NoSuchLimitProfileException(checkList.getAANumber());
		}

		String origCountry = getDocumentJdbcDao().getOrigCountryByAANumber(checkList.getAANumber(), checklistType);
		String origOrganisation = getDocumentJdbcDao().getOrigOrganisationByAANumber(checkList.getAANumber(),
				checklistType);

		checkList.setLimitProfileId(limitProfile.getLimitProfileId());
		checkList.setApplicationType(limitProfile.getAAType());
		checkList.setOrigCountry(origCountry);
		checkList.setOrigOrganisation(origOrganisation);

		if (DocumentConstants.CHECKLIST_TYPE_COLLATERAL.equalsIgnoreCase(checklistType)) {
			SCChecklist sc = checkList.getScChecklist();

			if (sc.getCmsCollateralID() <= 0) {
				ApprovedSecurity sec = getDocumentJdbcDao().getCollateralByLOSSecurityID(sc.getLOSSecurityId());
				if (sec == null) {
					throw new NoSuchSecurityException(sc.getLOSSecurityId(), msg.getMsgHeader().getSource());
				}

				sc.setCmsCollateralID(sec.getCMSSecurityId());
			}

		}
		else if (DocumentConstants.CHECKLIST_TYPE_BORROWER_PLEDGOR.equalsIgnoreCase(checklistType)) {
			CCChecklist cc = checkList.getCcChecklist();
			String customerType = cc.getCustomerType();
			String cifNumber = cc.getCIFNo();

			if (ArrayUtils.contains(DocumentConstantsCla.getChecklistCategoryBorrower(), customerType)) {
				SubProfile customer = getDocumentJdbcDao().retrieveSubProfileByCIFNumber(cifNumber);

				if (customer == null) {
					throw new NoSuchCustomerException(cifNumber);
				}

				checkList.setCustomerId(new Long(customer.getCmsId()));

			}
			else if (ArrayUtils.contains(DocumentConstantsCla.getChecklistCategoryPledgor(), customerType)) {
				Pledgor pledgor = getDocumentJdbcDao().retrievePledgorByAAAndCIF(checkList.getAANumber(), cifNumber);

				if (pledgor == null) {
					throw new NoSuchPledgorException(cifNumber);
				}

				checkList.setPledgorId(new Long(pledgor.getCmsId()));
			}
		}
		else {
			throw new FieldValueNotAllowedException("CheckList Type", checklistType, DocumentConstantsCla.getChecklistTypes());
		}

		for (Iterator itr = checkList.getCheckListItem().iterator(); itr.hasNext();) {
			CheckListItem item = (CheckListItem) itr.next();
			item.setPreApprove(true);

			if (item.getCmsDocItemID() <= 0) {
				CheckListItem storedCheckListItem = (CheckListItem) getDocumentJdbcDao().retrieveCheckListItemByDocNo(
						item.getDocNo());

				if (storedCheckListItem != null) {
					item.setCmsDocItemID(storedCheckListItem.getCmsDocItemID());
					item.setUpdateStatusIndicator(Character.toString(UPDATEINDICATOR));
					item.setChangeIndicator(Character.toString(CHANGEINDICATOR));
				}
			}
		}

		return eaiMessage;
	}
}
