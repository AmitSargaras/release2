/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.recreceipt;

import java.util.HashMap;
import java.util.StringTokenizer;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.recurrent.bus.IConvenant;
import com.integrosys.cms.app.recurrent.bus.IConvenantSubItem;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListSubItem;
import com.integrosys.cms.app.recurrent.bus.RecurrentException;
import com.integrosys.cms.app.recurrent.proxy.IRecurrentProxyManager;
import com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: wltan $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2005/09/07 11:08:45 $ Tag: $Name: $
 */
public class SubmitRecurrentReceiptCommand extends AbstractCommand {
	private IRecurrentProxyManager recurrentProxyManager;

	public void setRecurrentProxyManager(IRecurrentProxyManager recurrentProxyManager) {
		this.recurrentProxyManager = recurrentProxyManager;
	}

	public IRecurrentProxyManager getRecurrentProxyManager() {
		return recurrentProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public SubmitRecurrentReceiptCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ "reminderIndex", "java.lang.String", REQUEST_SCOPE },
				{ "reminderIndex2", "java.lang.String", REQUEST_SCOPE },
				{ "recChkLst", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList", SERVICE_SCOPE },
				{ "checkListTrxVal", "com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE },
				{ "recChkLst", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList", SERVICE_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		String reminderIndex = (String) map.get("reminderIndex");

		StringTokenizer st = new StringTokenizer(reminderIndex, ",");
		IRecurrentCheckList recChkLst = (IRecurrentCheckList) map.get("recChkLst");
		while (st.hasMoreTokens()) {
			int i = Integer.parseInt(st.nextToken());
			// recChkLst.getCheckListItemList()[i].setIsPrintReminderInd(true
			// );
			// @todo: need to loop thru all subitems of all items in the
			// checklist n determine which sub item to update
			int subItemsCounter = 0;
			if (recChkLst != null) {
				IRecurrentCheckListItem[] itemList = recChkLst.getCheckListItemList();
				if (itemList != null) {
					for (int itemIndex = 0; itemIndex < itemList.length; itemIndex++) {
						IRecurrentCheckListItem item = itemList[itemIndex];
						if (item != null) {
							IRecurrentCheckListSubItem[] subItemList = item.getRecurrentCheckListSubItemList();
							if (subItemList != null) {
								for (int subItemIndex = 0; subItemIndex < subItemList.length; subItemIndex++) {
									subItemsCounter++;

									if (i == subItemsCounter) {
										IRecurrentCheckListSubItem subItem = subItemList[subItemIndex];
										subItem.setIsPrintReminderInd(true);
										item.updateSubItem(subItemIndex, subItem);
										recChkLst.updateItem(itemIndex, item);
									}
								}
							}
						}
					}
				}
			}
		}

		reminderIndex = (String) map.get("reminderIndex2");

		st = new StringTokenizer(reminderIndex, ",");
		recChkLst = (IRecurrentCheckList) map.get("recChkLst");
		DefaultLogger.debug(this, "at convenant portion recChkList null??" + (recChkLst == null));
		while (st.hasMoreTokens()) {
			int i = Integer.parseInt(st.nextToken());
			int subItemsCounter = 0;
			if (recChkLst != null) {
				IConvenant[] itemList = recChkLst.getConvenantList();
				if (itemList != null) {
					for (int itemIndex = 0; itemIndex < itemList.length; itemIndex++) {
						IConvenant item = itemList[itemIndex];
						if (item != null) {
							IConvenantSubItem[] subItemList = item.getConvenantSubItemList();
							if (subItemList != null) {
								for (int subItemIndex = 0; subItemIndex < subItemList.length; subItemIndex++) {
									subItemsCounter++;

									if (i == subItemsCounter) {
										IConvenantSubItem subItem = subItemList[subItemIndex];
										subItem.setIsPrintReminderInd(true);
										item.updateSubItem(subItemIndex, subItem);
										recChkLst.updateConvenant(itemIndex, item);

									}
								}
							}
						}
					}
				}
			}
		}
		resultMap.put("recChkLst", recChkLst);

		IRecurrentCheckListTrxValue checkListTrxVal = (IRecurrentCheckListTrxValue) map.get("checkListTrxVal");
		ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		ctx.setTrxCountryOrigin(limit.getOriginatingLocation().getCountryCode());
		ctx.setTrxOrganisationOrigin(limit.getOriginatingLocation().getOrganisationCode());

		if (checkListTrxVal.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
			try {
				checkListTrxVal = getRecurrentProxyManager().makerEditRejectedCheckListReceiptTrx(ctx, checkListTrxVal,
						recChkLst);
			}
			catch (RecurrentException ex) {
				throw new CommandProcessingException(
						"failed to update recurrent checklist receipt workflow (from rejected)", ex);
			}
		}
		else {
			try {
				checkListTrxVal = getRecurrentProxyManager().makerUpdateCheckListReceipt(ctx, checkListTrxVal,
						recChkLst);
			}
			catch (RecurrentException ex) {
				throw new CommandProcessingException("failed to update recurrent checklist receipt workflow", ex);
			}
		}

		resultMap.put("request.ITrxValue", checkListTrxVal);

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
