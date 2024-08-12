package com.integrosys.cms.host.eai.document.actualtrxhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.FieldValueNotAllowedException;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.document.DocumentConstants;
import com.integrosys.cms.host.eai.document.DocumentConstantsCla;
import com.integrosys.cms.host.eai.document.DocumentInputMessageBody;
import com.integrosys.cms.host.eai.document.bus.CCChecklist;
import com.integrosys.cms.host.eai.document.bus.CheckList;
import com.integrosys.cms.host.eai.document.bus.CheckListItem;
import com.integrosys.cms.host.eai.document.bus.IDocumentDao;
import com.integrosys.cms.host.eai.document.bus.SCChecklist;

/**
 * <p>
 * Third handler to retrieve the possible cms checklist stored before (without
 * cms checklist id provided in the message).
 * <p>
 * This stage also include all the possible checklist items synchronization,
 * between those in persistent storage, and those in the message provided.
 * 
 * @author Chong Jun Yong
 * 
 */
public class CheckListObjectsMergerHandler extends AbstractCommonActualTrxHandler {

	private IDocumentDao documentDao;

	public IDocumentDao getDocumentDao() {
		return documentDao;
	}

	public void setDocumentDao(IDocumentDao documentDao) {
		this.documentDao = documentDao;
	}

	public Message persistActualTrx(Message msg) {
		EAIMessage eaiMessage = (EAIMessage) msg;

		DocumentInputMessageBody msgBody = (DocumentInputMessageBody) eaiMessage.getMsgBody();
		CheckList checkList = msgBody.getCheckList();

		String checklistType = checkList.getChecklistType();
		long cmsLimitProfileId = checkList.getLimitProfileId();
		long cmsCheckListId = checkList.getCheckListID();

		CheckList storedCheckList = null;
		if (DocumentConstants.CHECKLIST_TYPE_COLLATERAL.equalsIgnoreCase(checklistType)) {
			SCChecklist sc = checkList.getScChecklist();

			storedCheckList = getDocumentDao().retrieveCollateralCheckListByCmsLimitProfileIdAndCmsCollateralId(
					cmsLimitProfileId, sc.getCmsCollateralID());

		}
		else if (DocumentConstants.CHECKLIST_TYPE_BORROWER_PLEDGOR.equalsIgnoreCase(checklistType)) {
			CCChecklist cc = checkList.getCcChecklist();
			String customerType = cc.getCustomerType();

			if (ArrayUtils.contains(DocumentConstantsCla.getChecklistCategoryBorrower(), customerType)) {
				storedCheckList = getDocumentDao().retrieveBorrowerCheckListByCmsLimitProfileIdAndCmsCustomerId(
						cmsLimitProfileId, checkList.getCustomerId().longValue(), customerType);

			}
			else if (ArrayUtils.contains(DocumentConstantsCla.getChecklistCategoryPledgor(), customerType)) {
				storedCheckList = getDocumentDao().retrievePledgorCheckListByCmsLimitProfileIdAndCmsPledgorId(
						cmsLimitProfileId, checkList.getPledgorId().longValue());
			}
		}
		else {
			throw new FieldValueNotAllowedException("CheckList Type", checklistType, DocumentConstantsCla.getChecklistTypes());
		}

		if (storedCheckList != null && cmsCheckListId <= 0) {
			checkList.setCheckListID(storedCheckList.getCheckListID());
			checkList.setUpdateStatusIndicator(UPDATEINDICATOR);
			checkList.setChangeIndicator(CHANGEINDICATOR);

			CheckListItem[] checkListItems = getDocumentDao().retrieveCheckListItemsByCmsCheckListId(
					storedCheckList.getCheckListID());
			if (checkListItems != null) {
				Collection finalCheckListItems = new ArrayList();
				Collection existingItems = EntityAssociationUtils.synchronizeCollectionsByProperties(Arrays
						.asList(checkListItems), checkList.getCheckListItem(), new String[] { "cmsDocItemID" },
						new String[] { "checklistID" });

				Collection notFoundItems = EntityAssociationUtils.retrieveRemovedObjectsCollection(Arrays
						.asList(checkListItems), checkList.getCheckListItem(), new String[] { "cmsDocItemID" });

				finalCheckListItems.addAll(existingItems);
				finalCheckListItems.addAll(notFoundItems);

				checkList.setCheckListItem(new Vector(finalCheckListItems));
			}
		}

		// due to variation, only add the existing non-pre-approval docs to the
		// msg checklist
		if (storedCheckList != null && cmsCheckListId > 0) {
			checkList.setUpdateStatusIndicator(UPDATEINDICATOR);
			checkList.setChangeIndicator(CHANGEINDICATOR);

			Collection finalCheckListItems = new ArrayList();

			// first add the checklist items from the message
			finalCheckListItems.addAll(checkList.getCheckListItem());

			CheckListItem[] checkListItems = getDocumentDao().retrieveCheckListItemsByCmsCheckListId(
					storedCheckList.getCheckListID());
			if (checkListItems != null) {
				Collection notFoundItems = EntityAssociationUtils.retrieveRemovedObjectsCollection(Arrays
						.asList(checkListItems), checkList.getCheckListItem(), new String[] { "cmsDocItemID" });

				// add those checklist items not found in the msg checklist
				finalCheckListItems.addAll(notFoundItems);
			}

			checkList.setCheckListItem(new Vector(finalCheckListItems));
		}

		return eaiMessage;
	}
}