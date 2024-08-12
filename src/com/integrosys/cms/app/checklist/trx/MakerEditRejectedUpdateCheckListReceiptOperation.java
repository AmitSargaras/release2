/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/MakerEditRejectedUpdateCheckListReceiptOperation.java,v 1.9 2005/02/22 10:23:37 wltan Exp $
 */
package com.integrosys.cms.app.checklist.trx;

//java
import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This operation allows a maker to edit a rejected doc item update receipt
 * transaction
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.9 $
 * @since $Date: 2005/02/22 10:23:37 $ Tag: $Name: $
 */
public class MakerEditRejectedUpdateCheckListReceiptOperation extends AbstractCheckListTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerEditRejectedUpdateCheckListReceiptOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_CHECKLIST_RECEIPT;
	}

	/**
	 * Pre process. Prepares the transaction object for persistance To spawn the
	 * custodian doc trx at checklist item level
	 * @param anITrxValue is of type ITrxValue
	 * @return ITrxValue
	 * @throws TrxOperationException on error
	 */
	public ITrxValue preProcess(ITrxValue anITrxValue) throws TrxOperationException {
		anITrxValue = super.preProcess(anITrxValue);
		ICheckListTrxValue trxValue = getCheckListTrxValue(anITrxValue);
		ICheckList orgCheckList = trxValue.getCheckList();
		ICheckList newCheckList = trxValue.getStagingCheckList();
		Long curStgCheckListID = new Long(trxValue.getStagingReferenceID());
		ICheckList curStgCheckList = getCurrentStagingCheckList(curStgCheckListID.longValue());
		ICheckListItem[] orgItemList = orgCheckList.getCheckListItemList();
		ICheckListItem[] newItemList = newCheckList.getCheckListItemList();

		undoCustodianOperation(trxValue.getTrxContext(), orgCheckList, curStgCheckList.getCheckListItemList(),
				newItemList);

		for (int ii = 0; ii < newItemList.length; ii++) {
			// if (!newItemList[ii].getIsApprovedInd())
			// {
			DefaultLogger.debug(this, "CPC Cust item Id: " + newItemList[ii].getCheckListItemRef());
			DefaultLogger.debug(this, "CPC Cust Status2: " + newItemList[ii].getCPCCustodianStatus());
			if (newItemList[ii].getIsInVaultInd()) {
				DefaultLogger.debug(this, "Custodian Doc Status: " + newItemList[ii].getCustodianDocStatus());
				if (ICMSConstant.STATE_ITEM_PENDING_TEMP_UPLIFT_AUTHZ.equals(newItemList[ii].getCustodianDocStatus())
						|| ICMSConstant.STATE_ITEM_PENDING_PERM_UPLIFT_AUTHZ.equals(newItemList[ii]
								.getCustodianDocStatus())
						|| ICMSConstant.STATE_ITEM_PENDING_RELODGE_AUTHZ
								.equals(newItemList[ii].getCustodianDocStatus())) {
					newItemList[ii].setCPCCustodianStatus(newItemList[ii].getCustodianDocStatus());
				}
				DefaultLogger.debug(this, "CPC Cust Status2: " + newItemList[ii].getCPCCustodianStatus());
				/*
				 * if(ICMSConstant.STATE_ITEM_PENDING_TEMP_UPLIFT_AUTHZ.equals(
				 * newItemList[ii].getCustodianDocStatus())) {
				 * DefaultLogger.debug(this, "IN TEMP UPLIFT AUTHZ");
				 * requestAllowTempUplift(trxValue.getTrxContext(),
				 * orgCheckList.getCheckListID(), getOrgItem(orgItemList,
				 * newItemList[ii])); continue; } if
				 * (ICMSConstant.STATE_ITEM_PENDING_PERM_UPLIFT_AUTHZ
				 * .equals(newItemList[ii].getCustodianDocStatus())) {
				 * requestAllowPermUplift(trxValue.getTrxContext(),
				 * orgCheckList.getCheckListID(), getOrgItem(orgItemList,
				 * newItemList[ii])); continue; }
				 */
			}
			// }
		}
		return anITrxValue;
	}

	private void undoCustodianOperation(ITrxContext anITrxContext, ICheckList orgCheckList,
			ICheckListItem[] orgStgCheckList, ICheckListItem[] newStgCheckList) throws TrxOperationException {
		ICheckListItem[] orgItemList = orgCheckList.getCheckListItemList();
		for (int ii = 0; ii < orgItemList.length; ii++) {
			ICheckListItem orgItem = orgItemList[ii];
			ICheckListItem newStgItem = getOrgItem(newStgCheckList, orgItem);

			newStgItem.setCPCCustodianStatus(orgItem.getCPCCustodianStatus());

			/*
			 * if
			 * ((ICMSConstant.STATE_ITEM_PENDING_TEMP_UPLIFT_AUTHZ.equals(stgItem
			 * .getCustodianDocStatus())) &&
			 * (!ICMSConstant.STATE_ITEM_PENDING_TEMP_UPLIFT_AUTHZ
			 * .equals(newStgItem.getCustodianDocStatus()))) {
			 * rejectCustodianTrx(anITrxContext, orgCheckList.getCheckListID(),
			 * getOrgItem(orgCheckList.getCheckListItemList(), stgItem)); }
			 * 
			 * if
			 * ((ICMSConstant.STATE_ITEM_PENDING_PERM_UPLIFT_AUTHZ.equals(stgItem
			 * .getCustodianDocStatus())) &&
			 * (!ICMSConstant.STATE_ITEM_PENDING_PERM_UPLIFT_AUTHZ
			 * .equals(newStgItem.getCustodianDocStatus()))) {
			 * rejectCustodianTrx(anITrxContext, orgCheckList.getCheckListID(),
			 * getOrgItem(orgCheckList.getCheckListItemList(), stgItem)); }
			 */

		}
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

	private ICheckList getCurrentStagingCheckList(long aCheckListID) throws TrxOperationException {
		try {
			return getSBStagingCheckListBusManager().getCheckListByID(aCheckListID);
		}
		catch (CheckListException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("RemoteException at getCurrentStagingCheckList: " + ex.toString());
		}
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
	 * 
	 * private void requestAllowTempUplift(ITrxContext anITrxContext, long
	 * aCheckListID, ICheckListItem anICheckListItem) throws
	 * TrxOperationException { try { ICustodianProxyManager custMgr =
	 * CustodianProxyManagerFactory.getCustodianProxyManager(); ICustodianDoc
	 * custDoc =
	 * custMgr.getCustodianDocByCheckListItem(anICheckListItem.getCheckListItemRef
	 * ()); DefaultLogger.debug(this, "CustDOCID: " +
	 * custDoc.getCustodianDocID()); ICustodianTrxValue custDocTrxValue =
	 * custMgr.getTrxCustodianDoc(custDoc.getCustodianDocID()); if
	 * (!((ICMSConstant
	 * .STATE_ITEM_PENDING_TEMP_UPLIFT_AUTHZ.equals(custDocTrxValue
	 * .getStatus())) ||
	 * (ICMSConstant.STATE_ITEM_PENDING_PERM_UPLIFT_AUTHZ.equals
	 * (custDocTrxValue.getStatus())))) { ICustodianDoc newCustDoc =
	 * custDocTrxValue.getCustodianDoc();
	 * //newCustDoc.setReason(anICheckListItem.getRemarks()); ITrxContext
	 * context = custDocTrxValue.getTrxContext();
	 * anITrxContext.setTeam(context.getTeam());
	 * anITrxContext.setUser(context.getUser());
	 * custMgr.authzTempUpliftDocMaker(anITrxContext, custDocTrxValue,
	 * newCustDoc); } } catch(CustodianException ex) { throw new
	 * TrxOperationException(ex); } }
	 * 
	 * private void requestAllowPermUplift(ITrxContext anITrxContext, long
	 * aCheckListID, ICheckListItem anICheckListItem) throws
	 * TrxOperationException { try { ICustodianProxyManager custMgr =
	 * CustodianProxyManagerFactory.getCustodianProxyManager(); ICustodianDoc
	 * custDoc =
	 * custMgr.getCustodianDocByCheckListItem(anICheckListItem.getCheckListItemRef
	 * ()); ICustodianTrxValue custDocTrxValue =
	 * custMgr.getTrxCustodianDoc(custDoc.getCustodianDocID()); if
	 * (!((ICMSConstant
	 * .STATE_ITEM_PENDING_TEMP_UPLIFT_AUTHZ.equals(custDocTrxValue
	 * .getStatus())) ||
	 * (ICMSConstant.STATE_ITEM_PENDING_PERM_UPLIFT_AUTHZ.equals
	 * (custDocTrxValue.getStatus())))) { ICustodianDoc newCustDoc =
	 * custDocTrxValue.getCustodianDoc();
	 * //newCustDoc.setReason(anICheckListItem.getRemarks()); ITrxContext
	 * context = custDocTrxValue.getTrxContext();
	 * anITrxContext.setTeam(context.getTeam());
	 * anITrxContext.setUser(context.getUser());
	 * custMgr.authzPermUpliftDocMaker(anITrxContext, custDocTrxValue,
	 * newCustDoc); } } catch(CustodianException ex) { throw new
	 * TrxOperationException(ex); } }
	 */

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICheckListTrxValue trxValue = createStagingCheckList(getCheckListTrxValue(anITrxValue));
		trxValue = updateCheckListTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}