/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/MakerCloseRejectedUpdateCheckListReceiptOperation.java,v 1.8 2005/02/22 10:23:37 wltan Exp $
 */
package com.integrosys.cms.app.checklist.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a maker to close a rejected checklist receipt
 * transaction
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.8 $
 * @since $Date: 2005/02/22 10:23:37 $ Tag: $Name: $
 */
public class MakerCloseRejectedUpdateCheckListReceiptOperation extends AbstractCheckListTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerCloseRejectedUpdateCheckListReceiptOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_UPDATE_CHECKLIST_RECEIPT;
	}

	/**
	 * Pre process. Prepares the transaction object for persistance To reject
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

		ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
		String itemState = null;
		for (int ii = 0; ii < newItemList.length; ii++) {
			/*
			 * if (newItemList[ii].getCustodianDocStatus() != null) { if
			 * ((newItemList[ii].getCustodianDocStatus().equals(ICMSConstant.
			 * STATE_ITEM_PENDING_TEMP_UPLIFT_AUTHZ)) ||
			 * (newItemList[ii].getCustodianDocStatus
			 * ().equals(ICMSConstant.STATE_ITEM_PENDING_PERM_UPLIFT_AUTHZ))) {
			 * if (newItemList[ii].getIsInVaultInd()) {
			 * rejectCustodianTrx(trxValue.getTrxContext(),
			 * orgCheckList.getCheckListID(), getOrgItem(orgItemList,
			 * newItemList[ii])); } continue; } } else
			 */
			if ((ICMSConstant.STATE_ITEM_PENDING_WAIVER.equals(newItemList[ii].getItemStatus()))
					|| (ICMSConstant.STATE_ITEM_PENDING_DEFERRAL.equals(newItemList[ii].getItemStatus()))) {
				ICheckListItem item = getOrgItem(orgItemList, newItemList[ii]);
				item.setItemStatus(ICMSConstant.STATE_ITEM_AWAITING);
			}
		}
		orgCheckList.setCheckListItemList(orgItemList);
		return anITrxValue;
	}

	private ICheckListItem getOrgItem(ICheckListItem[] anOrgCheckListItemList, ICheckListItem aCheckListItem)
			throws TrxOperationException {
		ICheckListItem checkListItem = null;
		for (int ii = 0; ii < anOrgCheckListItemList.length; ii++) {
			checkListItem = anOrgCheckListItemList[ii];
			if (checkListItem.getCheckListItemRef() == aCheckListItem.getCheckListItemRef()) {
				return checkListItem;
			}
		}
		throw new TrxOperationException("No org item found for corresponding new item with item ref "
				+ aCheckListItem.getCheckListItemRef());
	}

	/*
	 * private void rejectCustodianTrx(ITrxContext anITrxContext, long
	 * aCheckListID, ICheckListItem anICheckListItem) throws
	 * TrxOperationException { try { ICustodianProxyManager custMgr =
	 * CustodianProxyManagerFactory.getCustodianProxyManager(); ICustodianDoc
	 * custDoc =
	 * custMgr.getCustodianDocByCheckListItem(anICheckListItem.getCheckListItemRef
	 * ()); ICustodianTrxValue custDocTrxValue =
	 * custMgr.getTrxCustodianDoc(custDoc.getCustodianDocID()); ITrxContext
	 * context = custDocTrxValue.getTrxContext();
	 * anITrxContext.setTeam(context.getTeam());
	 * anITrxContext.setUser(context.getUser());
	 * custMgr.rejectCustodian(anITrxContext, custDocTrxValue); }
	 * catch(CustodianException ex) { throw new TrxOperationException(ex); } }
	 */

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICheckListTrxValue trxValue = getCheckListTrxValue(anITrxValue);
		// trxValue.setStagingCheckList(trxValue.getCheckList());
		// trxValue = createStagingCheckList(trxValue);
		ICheckList checkList = updateActualCheckList(trxValue.getCheckList());
		trxValue = updateCheckListTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

}