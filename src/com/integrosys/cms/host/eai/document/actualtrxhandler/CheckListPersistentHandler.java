package com.integrosys.cms.host.eai.document.actualtrxhandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang.Validate;

import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.ICheckListOwner;
import com.integrosys.cms.app.checklist.bus.OBCCCheckListOwner;
import com.integrosys.cms.app.checklist.bus.OBCheckList;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.checklist.trx.OBCheckListTrxValue;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.FieldValueNotAllowedException;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.document.ChecklistSystemException;
import com.integrosys.cms.host.eai.document.DocumentConstants;
import com.integrosys.cms.host.eai.document.DocumentConstantsCla;
import com.integrosys.cms.host.eai.document.DocumentInputMessageBody;
import com.integrosys.cms.host.eai.document.bus.CheckList;
import com.integrosys.cms.host.eai.document.bus.CheckListItem;

/**
 * <p>
 * Last stage to persist the checklist into persistent storage through the
 * workflow engine.
 * <p>
 * Either update or insert done is based on the status indicator in the
 * checklist object, which is done in first stage.
 * 
 * @author Chong Jun Yong
 * 
 */
public class CheckListPersistentHandler extends AbstractCommonActualTrxHandler {

	private ICheckListProxyManager checklistProxyManager;

	public void setChecklistProxyManager(ICheckListProxyManager checklistProxyManager) {
		this.checklistProxyManager = checklistProxyManager;
	}

	public ICheckListProxyManager getChecklistProxyManager() {
		return checklistProxyManager;
	}

	public Message persistActualTrx(Message msg) {
		EAIMessage eaiMessage = (EAIMessage) msg;

		DocumentInputMessageBody msgBody = (DocumentInputMessageBody) eaiMessage.getMsgBody();
		CheckList checkList = msgBody.getCheckList();

		if (checkList.isUpdateChecklist()) {
			try {
				ICheckListTrxValue checkListTrxValue = getChecklistProxyManager().getCheckList(
						checkList.getCheckListID());
				ICheckList actualCheckList = checkListTrxValue.getCheckList();
				ICheckList stagingCheckList = checkListTrxValue.getStagingCheckList();

				actualCheckList = mergeToCmsCheckListItems(actualCheckList, checkList);
				stagingCheckList = mergeToCmsCheckListItems(stagingCheckList, checkList);

				checkListTrxValue.setCheckList(actualCheckList);
				checkListTrxValue.setStagingCheckList(stagingCheckList);

				getChecklistProxyManager().systemUpdateCheckList(checkListTrxValue, actualCheckList);
			}
			catch (CheckListException ex) {
				throw new ChecklistSystemException("failed to retrieve workflow of the checklist, id provided ["
						+ checkList.getCheckListID() + "]", ex);
			}
		}
		else if (checkList.isInsertChecklist()) {
			ICheckListTrxValue checkListTrxValue = new OBCheckListTrxValue();

			ICheckList actualCheckList = mergeToCmsCheckList(null, checkList);
			ICheckList stagingCheckList = mergeToCmsCheckList(null, checkList);

			actualCheckList = mergeToCmsCheckListItems(actualCheckList, checkList);
			stagingCheckList = mergeToCmsCheckListItems(stagingCheckList, checkList);

			checkListTrxValue.setCheckList(actualCheckList);
			checkListTrxValue.setStagingCheckList(stagingCheckList);

			checkListTrxValue.setLimitProfileID(checkList.getLimitProfileId());
			checkListTrxValue.setToState(ICMSConstant.STATE_ACTIVE);

			try {
				getChecklistProxyManager().systemCreateDocumentCheckList(checkListTrxValue, actualCheckList);
			}
			catch (CheckListException ex) {
				throw new ChecklistSystemException("failed to create workflow of the checklist, AA number ["
						+ checkList.getAANumber() + "], checklist type [" + checkList.getChecklistType() + "]", ex);
			}
		}

		return eaiMessage;
	}

	/**
	 * Merge the checklist in the message object into application cms checklist
	 * 
	 * @param cmsCheckList application cms checklist, can be null, which a new
	 *        instance will be created.
	 * @param checkList the checklist found in the message object.
	 * @return a completed application cms checklist ready to be thrown into
	 *         workflow
	 */
	protected ICheckList mergeToCmsCheckList(ICheckList cmsCheckList, CheckList checkList) {
		if (cmsCheckList == null) {
			cmsCheckList = new OBCheckList();
		}

		cmsCheckList.setCheckListID(checkList.getCheckListID());

		if (DocumentConstants.CHECKLIST_TYPE_COLLATERAL.equals(checkList.getChecklistType())) {
			cmsCheckList.setCheckListType(ICMSConstant.DOC_TYPE_SECURITY);
		}
		else {
			cmsCheckList.setCheckListType(checkList.getChecklistType());
		}
		cmsCheckList.setApplicationType(checkList.getApplicationType());
		cmsCheckList.setTemplateID(checkList.getTemplateID());
		cmsCheckList.setCheckListOwner(createCheckListOwner(checkList));
		cmsCheckList.setCheckListLocation(new OBBookingLocation(checkList.getOrigCountry(), checkList
				.getOrigOrganisation()));

		return cmsCheckList;
	}

	/**
	 * Merge all the checklist items of the checklist in the message object into
	 * application cms checklist items
	 * 
	 * @param cmsCheckList application cms checklist, <b>must not be null</b>,
	 *        can be the one created from
	 *        {@link #mergeToCmsCheckList(ICheckList, CheckList)}
	 * @param checkList the checklist found in the message object.
	 * @return a completed application cms checklist (with checklist items
	 *         created as well) ready to be thrown into workflow
	 */
	protected ICheckList mergeToCmsCheckListItems(ICheckList cmsCheckList, CheckList checkList) {
		Validate.notNull(cmsCheckList, "cms checklist object must not be null.");

		Collection finalCmsCheckListItemList = new ArrayList();
		for (Iterator itr = checkList.getCheckListItem().iterator(); itr.hasNext();) {
			CheckListItem checkListItem = (CheckListItem) itr.next();

			boolean isFound = false;
			ICheckListItem cmsCheckListItem = null;
			if (cmsCheckList.getCheckListItemList() != null) {
				for (int i = 0; !isFound && i < cmsCheckList.getCheckListItemList().length; i++) {
					cmsCheckListItem = cmsCheckList.getCheckListItemList()[i];
					if (checkListItem.getDocNo() == cmsCheckListItem.getCheckListItemRef()) {
						isFound = true;
					}
				}
			}

			if (!isFound) {
				cmsCheckListItem = new OBCheckListItem();
			}

			cmsCheckListItem.setCheckListItemID(checkListItem.getCmsDocItemID());
			cmsCheckListItem.setItemCode(checkListItem.getDocCode());
			cmsCheckListItem.setCheckListItemRef(checkListItem.getDocNo());
			cmsCheckListItem.setIsMandatoryInd(Character.toUpperCase(checkListItem.getMandatoryInd()) == 'Y');
			cmsCheckListItem.setItemDesc(checkListItem.getDescription());
			cmsCheckListItem.setExpiryDate(checkListItem.getJDOExpiryDate());
			cmsCheckListItem.setReceivedDate(checkListItem.getJDOReceivedDate());
			cmsCheckListItem.setWaivedDate(checkListItem.getJDOWaivedDate());
			cmsCheckListItem.setItemStatus(checkListItem.getStatus());
			cmsCheckListItem.setIsPreApprove(checkListItem.isPreApprove());
			cmsCheckListItem.getItem().setIsPreApprove(checkListItem.isPreApprove());
			cmsCheckListItem.setIsInherited(true);
			cmsCheckListItem.setIsDeletedInd(Character.toUpperCase(checkListItem.getIsDeleted()) == 'Y');

			finalCmsCheckListItemList.add(cmsCheckListItem);
		}

		cmsCheckList.setCheckListItemList((ICheckListItem[]) finalCmsCheckListItemList.toArray(new ICheckListItem[0]));

		return cmsCheckList;
	}

	/**
	 * Checklist owner creation step required by the application checklist
	 * module
	 * 
	 * @param checkList the checklist found in the message object
	 * @return checklist owner ready to be used by the application checklist
	 *         module
	 */
	protected ICheckListOwner createCheckListOwner(CheckList checkList) {
		long limitProfileId = checkList.getLimitProfileId();

		ICheckListOwner owner = null;

		if (DocumentConstants.CHECKLIST_TYPE_BORROWER_PLEDGOR.equals(checkList.getChecklistType())) {
			String subOwnerType = checkList.getCcChecklist().getCustomerType();

			long ownerId = 0;
			if (checkList.getCustomerId() != null) {
				ownerId = checkList.getCustomerId().longValue();
			}
			else if (checkList.getPledgorId() != null) {
				ownerId = checkList.getPledgorId().longValue();
			}

			owner = new OBCCCheckListOwner(limitProfileId, ownerId, subOwnerType);
		}
		else if (DocumentConstants.CHECKLIST_TYPE_COLLATERAL.equals(checkList.getChecklistType())) {
			long collateralId = checkList.getScChecklist().getCmsCollateralID();
			owner = new OBCollateralCheckListOwner(limitProfileId, collateralId, ICMSConstant.CHECKLIST_MAIN_BORROWER);
		}
		else {
			throw new FieldValueNotAllowedException("Checklist Type", checkList.getChecklistType(),
					DocumentConstantsCla.getChecklistTypes());
		}

		return owner;
	}
}
