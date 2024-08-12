/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/CheckerApproveUpdateRecurrentCheckListReceiptOperation.java,v 1.6 2005/08/25 07:58:32 wltan Exp $
 */
package com.integrosys.cms.app.recurrent.annexure.trx;

import java.util.Date;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.recurrent.bus.IConvenant;
import com.integrosys.cms.app.recurrent.bus.IConvenantSubItem;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListSubItem;
import com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue;

/**
 * This operation allows a checker to approve the checklist updating
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.6 $
 * @since $Date: 2005/08/25 07:58:32 $ Tag: $Name: $
 */
public class CheckerApproveUpdateAnnexureReceiptOperation extends
		CheckerApproveUpdateAnnexureOperation {
	/**
	 * Defaulc Constructor
	 */
	public CheckerApproveUpdateAnnexureReceiptOperation() {
		super();
	}

	/**
	 * Pre process. Prepares the transaction object for persistance Get the
	 * parent checklist transaction ID to be appended as trx parent ref
	 * @param anITrxValue is of type ITrxValue
	 * @return ITrxValue
	 * @throws TrxOperationException on error
	 */
	public ITrxValue preProcess(ITrxValue anITrxValue) throws TrxOperationException {
		anITrxValue = super.preProcess(anITrxValue);
		IRecurrentCheckListTrxValue trxValue = getCheckListTrxValue(anITrxValue);
		IRecurrentCheckList staging = trxValue.getStagingCheckList();
		IRecurrentCheckListItem[] itemList = staging.getCheckListItemList();
		IRecurrentCheckList actual = trxValue.getCheckList(); // get the actual
																// checklist
		IRecurrentCheckListItem[] actualItems = actual.getCheckListItemList(); // get
																				// the
																				// actual
																				// checklist
																				// items
		if ((itemList != null) && (itemList.length > 0)) {
			for (int ii = 0; ii < itemList.length; ii++) {
				IRecurrentCheckListSubItem[] subItemList = itemList[ii].getRecurrentCheckListSubItemList();
				if ((subItemList != null) && (subItemList.length > 0)) {
					for (int jj = 0; jj < subItemList.length; jj++) {
						// DefaultLogger.debug(this, "SUBITEM Status1: " +
						// subItemList[jj].getStatus());
						if (ICMSConstant.STATE_PENDING_RECEIVED.equals(subItemList[jj].getStatus())) {
							// DefaultLogger.debug(this, "SUBITEM Status2: " +
							// subItemList[jj].getStatus());
							subItemList[jj].setStatus(ICMSConstant.RECURRENT_ITEM_STATE_RECEIVED);
						}
						if (ICMSConstant.STATE_ITEM_PENDING_DEFERRAL.equals(subItemList[jj].getStatus())) {
							// DefaultLogger.debug(this, "SUBITEM Status2: " +
							// subItemList[jj].getStatus());
							subItemList[jj].setStatus(ICMSConstant.STATE_CHECKLIST_DEFERRED);
						}
						// bernard - the following block is for CMS-1430
						// added handling for WAIVED sub-items
						else if (ICMSConstant.RECURRENT_ITEM_STATE_PENDING_WAIVER.equals(subItemList[jj].getStatus())) {
							// DefaultLogger.debug(this, "SUBITEM Status2: " +
							// subItemList[jj].getStatus());
							subItemList[jj].setStatus(ICMSConstant.RECURRENT_ITEM_STATE_ITEM_WAIVED);
						}
						// added handling to increment the Deferred Count
						if (ICMSConstant.RECURRENT_ITEM_STATE_PENDING.equals(subItemList[jj].getStatus())
								&& (subItemList[jj].getDeferredDate() != null) && (actualItems != null)) {
							for (int a = 0; a < actualItems.length; a++) // loop
																			// through
																			// actual
																			// items
							{
								IRecurrentCheckListSubItem[] actualSubItems = actualItems[a]
										.getRecurrentCheckListSubItemList();
								if (actualSubItems != null) {
									for (int b = 0; b < actualSubItems.length; b++) // loop
																					// through
																					// actual
																					// sub
																					// -
																					// items
									{
										long actualSubItemRef = actualSubItems[b].getSubItemRef();
										Date actualDeferredDate = actualSubItems[b].getDeferredDate();
										Date stageDeferredDate = subItemList[jj].getDeferredDate();
										// if matching subItemRef found and
										// actual deferred date is different
										// from staging deferred date
										if ((actualSubItemRef == subItemList[jj].getSubItemRef())
												&& (stageDeferredDate != null)
												&& ((actualDeferredDate == null) || ((actualDeferredDate != null) && !actualDeferredDate
														.equals(stageDeferredDate)))) {
											subItemList[jj].setDeferredCount(subItemList[jj].getDeferredCount() + 1);
										}
									}
								}
							}
						}
						// end of CMS-1430
					}
					itemList[ii].setRecurrentCheckListSubItemList(subItemList);
				}
			}
			staging.setCheckListItemList(itemList);
		}

		// cr26
		// DefaultLogger.debug(this, "~~~~~~~before checking convenant");
		IConvenant[] citemList = staging.getConvenantList();
		// IRecurrentCheckList actual = trxValue.getCheckList(); //get the
		// actual checklist
		IConvenant[] cactualItems = actual.getConvenantList(); // get the actual
																// checklist
																// items
		if ((citemList != null) && (citemList.length > 0)) {
			for (int ii = 0; ii < citemList.length; ii++) {
				IConvenantSubItem[] subItemList = citemList[ii].getConvenantSubItemList();
				// DefaultLogger.debug(this, "~~~~~~~subitemlist null?: " +
				// (subItemList==null));

				if ((subItemList != null) && (subItemList.length > 0)) {
					for (int jj = 0; jj < subItemList.length; jj++) {
						// DefaultLogger.debug(this, "SUBITEM Status1: " +
						// subItemList[jj].getStatus());
						if (ICMSConstant.CONVENANT_STATE_PENDING_CHECKED.equals(subItemList[jj].getStatus())) {
							// DefaultLogger.debug(this, "SUBITEM Status2: " +
							// subItemList[jj].getStatus());
							subItemList[jj].setStatus(ICMSConstant.CONVENANT_STATE_CHECKED);
						}
						// bernard - the following block is for CMS-1430
						// added handling for WAIVED sub-items
						else if (ICMSConstant.RECURRENT_ITEM_STATE_PENDING_WAIVER.equals(subItemList[jj].getStatus())) {
							// DefaultLogger.debug(this, "SUBITEM Status2: " +
							// subItemList[jj].getStatus());
							subItemList[jj].setStatus(ICMSConstant.RECURRENT_ITEM_STATE_ITEM_WAIVED);
						}
						// added handling to increment the Deferred Count
						if (ICMSConstant.RECURRENT_ITEM_STATE_PENDING.equals(subItemList[jj].getStatus())
								&& (subItemList[jj].getDeferredDate() != null) && (actualItems != null)) {
							for (int a = 0; a < cactualItems.length; a++) // loop
																			// through
																			// actual
																			// items
							{
								IConvenantSubItem[] actualSubItems = cactualItems[a].getConvenantSubItemList();
								if (actualSubItems != null) {
									for (int b = 0; b < actualSubItems.length; b++) // loop
																					// through
																					// actual
																					// sub
																					// -
																					// items
									{
										long actualSubItemRef = actualSubItems[b].getSubItemRef();
										Date actualDeferredDate = actualSubItems[b].getDeferredDate();
										Date stageDeferredDate = subItemList[jj].getDeferredDate();
										// if matching subItemRef found and
										// actual deferred date is different
										// from staging deferred date
										if ((actualSubItemRef == subItemList[jj].getSubItemRef())
												&& (stageDeferredDate != null)
												&& ((actualDeferredDate == null) || ((actualDeferredDate != null) && !actualDeferredDate
														.equals(stageDeferredDate)))) {
											subItemList[jj].setDeferredCount(subItemList[jj].getDeferredCount() + 1);
										}
									}
								}
							}
						}
						// end of CMS-1430
					}
					citemList[ii].setConvenantSubItemList(subItemList);
				}
			}
			staging.setConvenantList(citemList);
		}

		trxValue.setStagingCheckList(staging);
		return trxValue;
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_ANNEXURE_CHECKLIST;
	}
}