/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/CheckerApproveUpdateCheckListReceiptOperation.java,v 1.46 2006/11/20 03:04:04 czhou Exp $
 */
package com.integrosys.cms.app.checklist.trx;

//java
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICCCheckListOwner;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.ICheckListOwner;
import com.integrosys.cms.app.checklist.bus.ICollateralCheckListOwner;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.custodian.bus.CustodianException;
import com.integrosys.cms.app.custodian.bus.ICustodianDoc;
import com.integrosys.cms.app.custodian.bus.OBCustodianDoc;
import com.integrosys.cms.app.custodian.proxy.CustodianProxyManagerFactory;
import com.integrosys.cms.app.custodian.proxy.ICustodianProxyManager;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This operation allows a checker to approve the checklist receipt updating
 * 
 * @author $Author: czhou $
 * @version $Revision: 1.46 $
 * @since $Date: 2006/11/20 03:04:04 $ Tag: $Name: $
 */
public class CheckerApproveUpdateCheckListReceiptOperation extends AbstractCheckListTrxOperation {

	// private HashMap shareCheckListIDUpdateMap = new HashMap();
	// private HashMap shareCheckListIDDeletedMap = new HashMap();
	// private static HashMap statusRankingMap = null;

	/**
	 * Defaulc Constructor
	 */
	public CheckerApproveUpdateCheckListReceiptOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_CHECKLIST_RECEIPT;
	}

	/**
	 * Pre process. Prepares the transaction object for persistance To approve
	 * custodian doc trxs spawned at the checklist item level
	 * @param anITrxValue is of type ITrxValue
	 * @return ITrxValue
	 * @throws TrxOperationException on error
	 */
	public ITrxValue preProcess(ITrxValue anITrxValue) throws TrxOperationException {
		anITrxValue = super.preProcess(anITrxValue);
		ICheckListTrxValue trxValue = getCheckListTrxValue(anITrxValue);
		ICheckList orgCheckList = trxValue.getCheckList();
		ICheckList newCheckList = trxValue.getStagingCheckList();
		ICheckListItem[] orgItemList = orgCheckList.getCheckListItemList();
		ICheckListItem[] newItemList = newCheckList.getCheckListItemList();
		ArrayList renewList = new ArrayList();
		ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
		String itemState = null;
		super.setSendCheckListItemNotificationInd(trxValue);

		for (int ii = 0; ii < newItemList.length; ii++) {
			if (orgItemList != null) {
				for (int x = 0; x < orgItemList.length; x++) {
					if (newItemList[ii].getCheckListItemRef() == orgItemList[x].getCheckListItemRef()) {
						newItemList[ii].setCPCCustodianStatusLastUpdateDate(orgItemList[x]
								.getCPCCustodianStatusUpdateDate());
						break;
					}
				}
			}
			
			//CY : To use custodian status to check whether it requires a state change since status is always completed
			
			if((newItemList[ii].getCPCCustodianStatus()!= null) && (newItemList[ii].getCPCCustodianStatus().startsWith("ALLOW")))
			{
				newItemList[ii].setItemStatus(newItemList[ii].getCPCCustodianStatus());
			}

			if (requireStateChange(newItemList[ii].getItemStatus())) {
				itemState = getNextCheckListItemState(proxy, newItemList[ii]);
				
				

                // CMSSP-619 - start
				if (newItemList[ii].getItemStatus().equals(ICMSConstant.STATE_ITEM_PENDING_UPDATE)) {
					ICheckListItem actualChecklistItem = getOrgItem(orgItemList, newItemList[ii]);
					//if ((actualChecklistItem != null) && (actualChecklistItem.getItemStatus() != null)
					//		&& actualChecklistItem.getItemStatus().equals(ICMSConstant.STATE_ITEM_EXPIRED)) {
                    if (actualChecklistItem != null && actualChecklistItem.getItemStatus() != null) {
                        if (actualChecklistItem.getItemStatus().equals(ICMSConstant.STATE_ITEM_EXPIRED)) {
                            Date actualChecklistItemExpiryDate = actualChecklistItem.getExpiryDate();
                            Date stageChecklistItemExpiryDate = newItemList[ii].getExpiryDate();
                            // item status to remain as EXPIRED if there's no change
                            // in expiry date
                            if (actualChecklistItemExpiryDate.equals(stageChecklistItemExpiryDate)) {
                                itemState = ICMSConstant.STATE_ITEM_EXPIRED;
                            }
                        } else if (actualChecklistItem.getItemStatus().equals(ICMSConstant.STATE_ITEM_AWAITING)) {
                            itemState = ICMSConstant.STATE_ITEM_AWAITING;                             
                        } else if (actualChecklistItem.getItemStatus().equals(ICMSConstant.STATE_ITEM_DEFERRED)) {
                            itemState = ICMSConstant.STATE_ITEM_DEFERRED;
                        }
					}
				}
				// CMSSP-619 - end

				if (newItemList[ii].getItemStatus().equals(ICMSConstant.STATE_ITEM_PENDING_COMPLETE)) {
					Date completionDate = DateUtil.getDate();
					newCheckList.setLastDocReceivedDate(completionDate);
					newItemList[ii].setDocCompletionDate(completionDate);
					if (newItemList[ii].getIsInVaultInd()) {
						// storeInVault(trxValue.getTrxContext(), orgCheckList,
						// getOrgItem(orgItemList, newItemList[ii]));
						newItemList[ii].setCPCCustodianStatus(ICMSConstant.STATE_RECEIVED);
					}
				}
				else if (newItemList[ii].getItemStatus().equals(ICMSConstant.STATE_ITEM_PENDING_RENEWAL)) {
					handleRenew(renewList, newItemList[ii]);
				}
				newItemList[ii].setItemStatus(itemState);
				
				//CY : TO ensure cms_checklist_item cpc_cust_status is set accordingly
				if(requireCustodianApproval(itemState))
				{
					newItemList[ii].setCPCCustodianStatus(itemState);
					newItemList[ii].setItemStatus(ICMSConstant.STATE_ITEM_COMPLETED);
				}
				// For CR CMS-382
				newItemList[ii].setLastUpdateDate(DateUtil.getDate());
				// newItemList[ii].setCPCCustodianStatusUpdateDate(DateUtil.
				// getDate());
			}
			if (newItemList[ii].getIsInVaultInd() && (newItemList[ii].getCustodianDocStatus() != null)) {

				if (newItemList[ii].getCustodianDocStatus().equals(ICMSConstant.STATE_ITEM_PENDING_TEMP_UPLIFT_AUTHZ)) {
					// approveRequestAllowTempUplift(trxValue.getTrxContext(),
					// orgCheckList.getCheckListID(), getOrgItem(orgItemList,
					// newItemList[ii]));
					newItemList[ii].setCPCCustodianStatus(ICMSConstant.STATE_AUTHZ_TEMP_UPLIFTED);
					newItemList[ii].setCPCCustodianStatusUpdateDate(DateUtil.getDate());
					continue;
				}
				if (newItemList[ii].getCustodianDocStatus().equals(ICMSConstant.STATE_ITEM_PENDING_PERM_UPLIFT_AUTHZ)) {
					// approveRequestAllowPermUplift(trxValue.getTrxContext(),
					// orgCheckList.getCheckListID(), getOrgItem(orgItemList,
					// newItemList[ii]));
					newItemList[ii].setCPCCustodianStatus(ICMSConstant.STATE_AUTHZ_PERM_UPLIFTED);
					newItemList[ii].setCPCCustodianStatusUpdateDate(DateUtil.getDate());
					continue;
				}
				if (newItemList[ii].getCustodianDocStatus().equals(ICMSConstant.STATE_ITEM_PENDING_RELODGE_AUTHZ)) {
					// approveRequestAllowRelodge(trxValue.getTrxContext(),
					// orgCheckList.getCheckListID(), getOrgItem(orgItemList,
					// newItemList[ii]));
					newItemList[ii].setCPCCustodianStatus(ICMSConstant.STATE_AUTHZ_RELODGED);
					newItemList[ii].setCPCCustodianStatusUpdateDate(DateUtil.getDate());
					continue;
				}
			}
		}
		ICheckListItem[] itemList1 = (ICheckListItem[]) renewList.toArray(new OBCheckListItem[0]);
		ICheckListItem[] resultList = new OBCheckListItem[newItemList.length + itemList1.length];
		for (int jj = 0; jj < newItemList.length; jj++) {
			resultList[jj] = newItemList[jj];
		}
		for (int jj = 0; jj < itemList1.length; jj++) {
			resultList[newItemList.length + jj] = itemList1[jj];
		}
		newItemList = resultList;
		newCheckList.setCheckListItemList(newItemList);
		return anITrxValue;
	}

	protected boolean requireStateChange(String anItemStatus) {
		if ((anItemStatus.equals(ICMSConstant.STATE_ITEM_PENDING_WAIVER_REQ))
				|| (anItemStatus.equals(ICMSConstant.STATE_ITEM_PENDING_DEFER_REQ))
				|| (anItemStatus.equals(ICMSConstant.STATE_ITEM_PENDING_COMPLETE))
				|| (anItemStatus.equals(ICMSConstant.STATE_ITEM_PENDING_RENEWAL))
				|| (anItemStatus.equals(ICMSConstant.STATE_ITEM_PENDING_WAIVER))
				|| (anItemStatus.equals(ICMSConstant.STATE_ITEM_PENDING_DEFERRAL))
				|| (anItemStatus.equals(ICMSConstant.STATE_ITEM_PENDING_DELETE))
				|| (anItemStatus.equals(ICMSConstant.ACTION_ITEM_PENDING_UPDATE))
                || (anItemStatus.equals(ICMSConstant.ACTION_ITEM_PENDING_LODGE))
                || (anItemStatus.equals(ICMSConstant.ACTION_ITEM_PENDING_RELODGE))
                || (anItemStatus.equals(ICMSConstant.ACTION_ITEM_ALLOW_PENDING_RELODGE))
                || (anItemStatus.equals(ICMSConstant.ACTION_ITEM_ALLOW_PENDING_LODGE))
                || (anItemStatus.equals(ICMSConstant.ACTION_ITEM_PENDING_TEMP_UPLIFT))
                || (anItemStatus.equals(ICMSConstant.ACTION_ITEM_PENDING_PERM_UPLIFT))
                || (anItemStatus.equals(ICMSConstant.ACTION_ITEM_ALLOW_PENDING_PERM_UPLIFT))
                || (anItemStatus.equals(ICMSConstant.ACTION_ITEM_ALLOW_PENDING_TEMP_UPLIFT))
                || (anItemStatus.equals(ICMSConstant.STATE_ITEM_PENDING_REDEEM))) {
			return true;                //can be summarize to like 'PENDING_%' to change state?
		}
		return false;
	}
	
	protected boolean requireCustodianApproval(String anItemStatus) {
		if ((anItemStatus.equals(ICMSConstant.ACTION_ITEM_PENDING_RELODGE))
				|| (anItemStatus.equals(ICMSConstant.ACTION_ITEM_PENDING_TEMP_UPLIFT))
				|| (anItemStatus.equals(ICMSConstant.ACTION_ITEM_PENDING_PERM_UPLIFT))
                || (anItemStatus.equals(ICMSConstant.ACTION_ITEM_PENDING_LODGE))) {
			return true;                //can be summarize to like 'PENDING_%' to change state?
		}
		return false;
	}

	private String getNextCheckListItemState(ICheckListProxyManager aProxy, ICheckListItem anItem)
			throws TrxOperationException {
		try {
			HashMap map = aProxy.getNextCheckListItemState(anItem.getItemStatus(), anItem.getCustodianDocStatus(),
					ICMSConstant.ACTION_ITEM_APPROVE);
			return (String) map.get(ICMSConstant.CHECKLIST_ITEM_STATE);
		}
		catch (CheckListException ex) {
			throw new TrxOperationException(ex);
		}
	}

	private ICheckListItem getOrgItem(ICheckListItem[] anOrgCheckListItemList, ICheckListItem aCheckListItem)
			throws TrxOperationException {
		ICheckListItem checkListItem = null;
		if (anOrgCheckListItemList != null) {
			for (int ii = 0; ii < anOrgCheckListItemList.length; ii++) {
				checkListItem = anOrgCheckListItemList[ii];
				if (checkListItem.getCheckListItemRef() == aCheckListItem.getCheckListItemRef()) {
					return checkListItem;
				}
			}
		}
		return null;
	}

	private void handleRenew(ArrayList anItemList, ICheckListItem aRenewedItem) throws TrxOperationException {
		try {
			ICheckListItem childItem = (ICheckListItem) CommonUtil.deepClone((OBCheckListItem) aRenewedItem);
			childItem.setCheckListItemID(com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE);
			childItem.setCheckListItemRef(com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE);
			childItem.setItemStatus(ICMSConstant.STATE_ITEM_AWAITING);
			childItem.setDocRef(null);
			childItem.setFormNo(null);
			childItem.setDocDate(null);
			childItem.setExpiryDate(null);
			childItem.setDeferExpiryDate(null);
			childItem.setActionParty(null);
			childItem.setCreditApprover(null);
			childItem.setRemarks(null);
			anItemList.add(childItem);
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception in handleRenew " + ex.toString());
		}
	}

	private void storeInVault(ITrxContext anITrxContext, ICheckList anICheckList, ICheckListItem anICheckListItem)
			throws TrxOperationException {
		try {
			ICustodianProxyManager custMgr = CustodianProxyManagerFactory.getCustodianProxyManager();
			ICustodianDoc custDoc = formulateCustodianDoc(anICheckList, anICheckListItem);
			if (anICheckList.getCheckListType().equals(ICMSConstant.DOC_TYPE_CC)) {
				custMgr.createDocByBorrower(anITrxContext, custDoc);
			}
			else {
				custMgr.createDocByCollateral(anITrxContext, custDoc);
			}
		}
		catch (CustodianException ex) {
			throw new TrxOperationException(ex);
		}
	}

	private ICustodianDoc formulateCustodianDoc(ICheckList anICheckList, ICheckListItem anICheckListItem) {
		ICustodianDoc custDoc = new OBCustodianDoc();
		custDoc.setCheckListID(anICheckList.getCheckListID());
		custDoc.setDocType(anICheckList.getCheckListType());
		ICheckListOwner owner = anICheckList.getCheckListOwner();
		custDoc.setLimitProfileID(owner.getLimitProfileID());
		if (owner instanceof ICCCheckListOwner) {
			ICCCheckListOwner ccOwner = (ICCCheckListOwner) owner;
			custDoc.setDocSubType(ccOwner.getSubOwnerType());
			if ((ccOwner.getSubOwnerType().equals(ICMSConstant.CHECKLIST_MAIN_BORROWER))
					|| (ccOwner.getSubOwnerType().equals(ICMSConstant.CHECKLIST_CO_BORROWER))
					|| (ccOwner.getSubOwnerType().equals(ICMSConstant.CHECKLIST_NON_BORROWER))) {
				custDoc.setSubProfileID(ccOwner.getSubOwnerID());
				return custDoc;
			}
			custDoc.setPledgorID(ccOwner.getSubOwnerID());
			return custDoc;
		}
		ICollateralCheckListOwner colOwner = (ICollateralCheckListOwner) owner;
		custDoc.setDocSubType(colOwner.getSubOwnerType());
		custDoc.setCollateralID(colOwner.getCollateralID());
		return custDoc;
	}

	/**
	 * update custodian transaction
	 * @param trxValue
	 */
	private void updateCustodianTransaction(ITrxValue trxValue) throws TrxOperationException {
		ICustodianProxyManager proxy = CustodianProxyManagerFactory.getCustodianProxyManager();
		try {
			proxy.updateTransaction(trxValue);
		}
		catch (CustodianException e) {
			throw new TrxOperationException(e);
		}
	}

	/**
	 * check whether update custodian
	 * @param trxValue
	 * @return
	 */
	private boolean checkUpdateCustodian(ICheckListTrxValue trxValue) throws TrxOperationException {
		try {
			ICheckList checkList = trxValue.getCheckList();
			ICheckList stagingCheckList = trxValue.getStagingCheckList();
			ICheckListItem[] checkListItem = checkList.getCheckListItemList();
			ICheckListItem[] stagingCheckListItem = stagingCheckList.getCheckListItemList();
			if ((checkListItem != null) && (stagingCheckListItem != null)) {
				for (int x = 0; x < checkListItem.length; x++) {
					long checkListItemRef = checkListItem[x].getCheckListItemRef();
					String cpcStatus = checkListItem[x].getCPCCustodianStatus();
					String itemStatus = checkListItem[x].getItemStatus();
					for (int y = 0; y < stagingCheckListItem.length; y++) {
						long stagingCheckListItemRef = stagingCheckListItem[y].getCheckListItemRef();
						String cpcStagingStatus = stagingCheckListItem[y].getCPCCustodianStatus();
						String stagingItemStatus = stagingCheckListItem[y].getItemStatus();
						if ((checkListItemRef == stagingCheckListItemRef) && stagingCheckListItem[y].getIsInVaultInd()) {
							if ((stagingItemStatus != null) && !stagingItemStatus.equals(itemStatus)
									&& stagingItemStatus.equals(ICMSConstant.STATE_ITEM_COMPLETED)) {
								return true;
							}
							else if ((cpcStagingStatus != null) && cpcStagingStatus.equals(cpcStatus)
									&& (stagingItemStatus != null) && !stagingItemStatus.equals(itemStatus)
									&& !stagingItemStatus.equals(ICMSConstant.STATE_ITEM_DELETED)) {
								return true;
							}
							else if ((cpcStagingStatus != null)
									&& !cpcStagingStatus.equals(cpcStatus)
									&& (cpcStagingStatus.equals(ICMSConstant.STATE_RECEIVED)
											|| cpcStagingStatus.equals(ICMSConstant.STATE_AUTHZ_TEMP_UPLIFTED)
											|| cpcStagingStatus.equals(ICMSConstant.STATE_AUTHZ_PERM_UPLIFTED) || cpcStagingStatus
											.equals(ICMSConstant.STATE_AUTHZ_RELODGED))) {
								return true;
							}
						}
					}
				}
			}
			return false;
		}
		catch (Exception e) {
			throw new TrxOperationException(e);
		}
	}

	/**
	 * Process the transaction 1. Update the actual data 2. Update the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICheckListTrxValue trxValue = getCheckListTrxValue(anITrxValue);
		// update custodian transaction
		boolean updateCust = this.checkUpdateCustodian(trxValue);
		DefaultLogger.debug(this, "updateCust: ------------------------------ " + updateCust);
		//if (updateCust) {
		//	updateCustodianTransaction(anITrxValue);
		//}
		
		super.checklistOldStatus = trxValue.getCheckList().getCheckListStatus();
		boolean itemNotificationInd = trxValue.getSendItemNotificationInd();
		DefaultLogger.debug(this, "<<<<<<<<<<<< checklist item notification ind: "
            + trxValue.getSendItemNotificationInd());

//        if (trxValue.getCheckList().getCheckListOwner() instanceof ICCCheckListOwner) {
//			trxValue = createStagingCheckList(trxValue);
//		}

//        trxValue = createStagingCheckList(trxValue);        //without creating this, to_track doesn't show correctly for sec receipt
		trxValue = updateActualCheckList(trxValue);
		trxValue = super.updateCheckListTransaction(trxValue);
		trxValue.setSendItemNotificationInd(itemNotificationInd);
		super.setSendNotificationIndicator(super.checklistOldStatus, trxValue);
		return super.prepareResult(trxValue);
	}


	private ICheckListTrxValue updateStagingCheckList(ICheckListTrxValue anICheckListTrxValue)
			throws TrxOperationException {
		try {
			ICheckList staging = anICheckListTrxValue.getStagingCheckList();
			staging = getSBStagingCheckListBusManager().update(staging);
			anICheckListTrxValue.setStagingCheckList(staging);
			return anICheckListTrxValue;
		}
		catch (ConcurrentUpdateException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
		catch (CheckListException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {

			throw new TrxOperationException("Exception in updateStagingCheckList(): " + ex.toString());
		}
	}
}