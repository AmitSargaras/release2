/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.annexure;

import java.util.Arrays;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.recurrent.bus.ConvenantComparator;
import com.integrosys.cms.app.recurrent.bus.IConvenant;
import com.integrosys.cms.app.recurrent.bus.IConvenantSubItem;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListSubItem;
import com.integrosys.cms.app.recurrent.bus.OBRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.RecurrentException;
import com.integrosys.cms.app.recurrent.proxy.IRecurrentProxyManager;
import com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2006/09/21 12:27:54 $ Tag: $Name: $
 */
public class ReadStagingAnnexureCommand extends AbstractCommand {
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
	public ReadStagingAnnexureCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "custTypeTrxID", "java.lang.String", REQUEST_SCOPE },
				{ EVENT, "java.lang.String", REQUEST_SCOPE } });
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
				{ "recChkLst", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList", SERVICE_SCOPE },
				{ "checkListTrxVal", "com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue", SERVICE_SCOPE },
				{ "flag", "java.lang.String", REQUEST_SCOPE }, { "frame", "java.lang.String", SERVICE_SCOPE },
				{ "limitProfile", "com.integrosys.cms.app.limit.bus.ILimitProfile", FORM_SCOPE },
				{ "conList", "java.util.List", SERVICE_SCOPE },
				{ "custTypeTrxID", "java.lang.String", REQUEST_SCOPE }
			});
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
		ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		String custTypeTrxID = (String) map.get("custTypeTrxID");
		String event = (String) map.get(EVENT);
		DefaultLogger.debug(this, "TrxiD before backend call" + custTypeTrxID);

		IRecurrentCheckListTrxValue checkListTrxVal;
		try {
			checkListTrxVal = getRecurrentProxyManager().getRecurrentCheckListByTrxID(custTypeTrxID);
		}
		catch (RecurrentException ex) {
			throw new CommandProcessingException("failed to retrieve recurrent workflow for transaction id ["
					+ custTypeTrxID + "]", ex);
		}
		// testing
		IRecurrentCheckList recChkLst = (OBRecurrentCheckList) checkListTrxVal.getStagingCheckList();
		if (recChkLst != null) {
			IConvenant[] recurrentItemList = recChkLst.getConvenantList();
			if (recurrentItemList != null) {
				for (int i = 0; i < recurrentItemList.length; i++) {
					IConvenantSubItem[] subItemList = recurrentItemList[i].getConvenantSubItemList();
					if (subItemList != null) {
						for (int j = 0; j < subItemList.length; j++) {
						}
					}
				}
			}
		}

		// tag actual receive or wavied to subitemlist
		if (!"process".equals(event)) {
			IRecurrentCheckList stagingChkList = checkListTrxVal.getStagingCheckList();
			IRecurrentCheckList chkList = checkListTrxVal.getCheckList();
			if ((stagingChkList != null) && (chkList != null)) {
				IRecurrentCheckListItem[] chkItemList = chkList.getCheckListItemList();
				IRecurrentCheckListItem[] stagingItemList = stagingChkList.getCheckListItemList();
				if ((stagingItemList != null) && (chkItemList != null)) {
					for (int i = 0; i < stagingItemList.length; i++) {
						IRecurrentCheckListSubItem[] stagingSubItemList = stagingItemList[i]
								.getRecurrentCheckListSubItemList();
						IRecurrentCheckListSubItem[] chkSubItemList = chkItemList[i].getRecurrentCheckListSubItemList();
						if (stagingSubItemList != null) {
							for (int j = 0; j < stagingSubItemList.length; j++) {
								if (j < chkSubItemList.length) {
									if (ICMSConstant.RECURRENT_ITEM_STATE_RECEIVED
											.equals(chkSubItemList[j].getStatus())
											|| ICMSConstant.RECURRENT_ITEM_STATE_ITEM_WAIVED.equals(chkSubItemList[j]
													.getStatus())) {
										stagingSubItemList[j].setStatus(chkSubItemList[j].getStatus());
									}
								}
							}
						}
					}
				}
				// cr 26
				IConvenant[] convenantList = chkList.getConvenantList();
				IConvenant[] stagingConvenantList = stagingChkList.getConvenantList();
				if ((stagingConvenantList != null) && (convenantList != null)) {
					for (int i = 0; i < stagingConvenantList.length; i++) {
						IConvenantSubItem[] stagingSubItemList = stagingConvenantList[i].getConvenantSubItemList();
						IConvenantSubItem[] chkSubItemList = convenantList[i].getConvenantSubItemList();
						if (stagingSubItemList != null) {
							for (int j = 0; j < stagingSubItemList.length; j++) {
								if (j < chkSubItemList.length) {
									if (ICMSConstant.CONVENANT_STATE_CHECKED.equals(chkSubItemList[j].getStatus())
											|| ICMSConstant.RECURRENT_ITEM_STATE_ITEM_WAIVED.equals(chkSubItemList[j]
													.getStatus())) {
										stagingSubItemList[j].setStatus(chkSubItemList[j].getStatus());
									}
								}
							}
						}
					}
				}

			}
		}

		IRecurrentCheckList recChkList = checkListTrxVal.getStagingCheckList();
		IConvenant conList[] = recChkLst.getConvenantList();

		if ((conList != null) && (conList.length > 0)) {
			Arrays.sort(conList, new ConvenantComparator());
		}
		recChkLst.setConvenantList(conList);
		checkListTrxVal.setStagingCheckList(recChkLst);
		
		resultMap.put("custTypeTrxID", custTypeTrxID);
		resultMap.put("recChkLst", recChkList);
		resultMap.put("checkListTrxVal", checkListTrxVal);
		resultMap.put("limitProfile", limit);
		if ("close_checklist_item".equals(event)) {
			resultMap.put("flag", "Close");
		}
		resultMap.put("frame", "false");// used to hide frames when user
		// comes from to do list

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
