/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/SystemApproveGenerateDeferralRequestCheckListOperation.java,v 1.2 2003/09/23 03:02:14 hltan Exp $
 */
package com.integrosys.cms.app.checklist.trx;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a system to approve deferral generation on a checklist
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/23 03:02:14 $ Tag: $Name: $
 */
public class SystemApproveGenerateDeferralRequestCheckListOperation extends AbstractCheckListTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public SystemApproveGenerateDeferralRequestCheckListOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_SYSTEM_APPROVE_GENERATE_CHECKLIST_DEFERRAL;
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
		List renewedItemList = Arrays.asList(newItemList);

		ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
		String itemState = null;

		for (int ii = 0; ii < newItemList.length; ii++) {
			if (requireStateChange(newItemList[ii].getItemStatus())) {
				itemState = getNextCheckListItemState(proxy, newItemList[ii]);
				newItemList[ii].setItemStatus(itemState);
			}
			newCheckList.setCheckListItemList((ICheckListItem[]) renewedItemList.toArray(new OBCheckListItem[0]));
		}
		return anITrxValue;
	}

	private boolean requireStateChange(String anItemStatus) {
		if (anItemStatus.equals(ICMSConstant.STATE_ITEM_DEFER_REQ)) {
			return true;
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

	/*
	 * private ICheckListItem getOrgItem(ICheckListItem[]
	 * anOrgCheckListItemList, ICheckListItem aCheckListItem) throws
	 * TrxOperationException { ICheckListItem checkListItem = null; for (int
	 * ii=0; ii<anOrgCheckListItemList.length; ii++) { checkListItem =
	 * anOrgCheckListItemList[ii]; if (checkListItem.getCheckListItemRef() ==
	 * aCheckListItem.getCheckListItemRef()) { return checkListItem; } } throw
	 * newTrxOperationException(
	 * "No org item found for corresponding new item with item ref " +
	 * aCheckListItem.getCheckListItemRef()); }
	 */

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
		trxValue = updateStagingCheckList(trxValue);
		trxValue = updateActualCheckList(trxValue);
		trxValue = super.updateCheckListTransaction(trxValue);
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

	/**
	 * Update the actual checklist from the staging checklist
	 * @param anICheckListTrxValue - ICheckListTrxValue
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws TrxOperationException on errors
	 */
	/*
	 * private ICheckListTrxValue updateActualCheckList(ICheckListTrxValue
	 * anICheckListTrxValue) throws TrxOperationException { try { ICheckList
	 * staging = anICheckListTrxValue.getStagingCheckList(); ICheckList actual =
	 * anICheckListTrxValue.getCheckList(); ICheckList updActual =
	 * (ICheckList)CommonUtil.deepClone(staging); updActual =
	 * mergeCheckList(actual, updActual); ICheckList actualCheckList =
	 * getSBCheckListTemplateBusManager().update(updActual);
	 * anICheckListTrxValue.setCheckList(updActual); return
	 * anICheckListTrxValue; } catch(ConcurrentUpdateException ex) {
	 * ex.printStackTrace(); throw new TrxOperationException(ex); }
	 * catch(CheckListException ex) { ex.printStackTrace(); throw new
	 * TrxOperationException(ex); } catch(Exception ex) { ex.printStackTrace();
	 * throw new TrxOperationException("Exception in updateActualCheckList(): "
	 * + ex.toString()); } }
	 */
}