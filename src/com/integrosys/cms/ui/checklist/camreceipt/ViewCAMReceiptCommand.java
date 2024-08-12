/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/checklist/secreceipt/ViewSecurityReceiptCommand.java,v 1.6 2006/06/22 02:03:50 czhou Exp $
 */
package com.integrosys.cms.ui.checklist.camreceipt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.imageTag.proxy.IImageTagProxyManager;
import com.integrosys.cms.ui.checklist.CheckListHelper;
import com.integrosys.cms.ui.checklist.ITagUntagImageConstant;

/**
 * Description
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/06/22 02:03:50 $ Tag: $Name: $
 */
public class ViewCAMReceiptCommand extends AbstractCommand implements ICommonEventConstant,ITagUntagImageConstant {
	/**
	 * Default Constructor
	 */
	public ViewCAMReceiptCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "itemRef", "java.lang.String", REQUEST_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },		
		});
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
				{ "checkListItem", "com.integrosys.cms.app.checklist.bus.ICheckListItem", FORM_SCOPE },
				{ "checkListItem", "com.integrosys.cms.app.checklist.bus.ICheckListItem", SERVICE_SCOPE },
				{ "monitorType", "java.lang.String", REQUEST_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "actualOb", "com.integrosys.cms.app.checklist.bus.ICheckListItem", REQUEST_SCOPE },
				{ "itemOb", "com.integrosys.cms.app.checklist.bus.ICheckListItem", REQUEST_SCOPE },
				{ TAGGED_FILE_NAMES, String.class.getName(), REQUEST_SCOPE},
				{ UN_TAGGED_FILE_NAMES, String.class.getName(), REQUEST_SCOPE}
		// {"actualShareCheckList", "java.util.List", REQUEST_SCOPE}
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
		try {
			
			ICheckListTrxValue checkListTrxVal = (ICheckListTrxValue) map.get("checkListTrxVal");
			ICheckList chklist = (ICheckList) map.get("checkList");
			ICheckListItem actual = null;
			String event=(String)map.get("event");
			long itemRef = Long.parseLong((String) map.get("itemRef"));
			ICheckListItem item = null;
			IImageTagProxyManager imageTagProxy = (IImageTagProxyManager) BeanHouse.get("imageTagProxy");
			
			if (chklist != null) {
				item = getItem(chklist.getCheckListItemList(), itemRef);
			}
			if (checkListTrxVal.getCheckList() != null) {
				actual = getItem(checkListTrxVal.getCheckList().getCheckListItemList(), itemRef);
			}
			String monitorType = item.getItem().getMonitorType();
			if ((monitorType != null)
					&& (monitorType.equals(ICMSConstant.INSURANCE_POLICY) || monitorType
							.equals(ICMSConstant.PREMIUM_RECEIPT))) {
				resultMap.put("monitorType", monitorType);

			}
			
			Map<Long, String> imageIdUnTaggedStatusMap = imageTagProxy.getImageIdTaggedStatusMap(String.valueOf(actual!= null ?actual.getCheckListItemID():""));
			
			String taggedFileNames = CheckListHelper.getTagUntaggedFileNameList(imageIdUnTaggedStatusMap, item.getCheckListItemImageDetail(), true);
			String unTaggedFileNames = CheckListHelper.getTagUntaggedFileNameList(imageIdUnTaggedStatusMap, item.getCheckListItemImageDetail(), false);
			
			resultMap.put("checkListItem", item);
			resultMap.put("actualOb", actual);
			resultMap.put("itemOb", item);
			resultMap.put("event", event);
			resultMap.put(TAGGED_FILE_NAMES, taggedFileNames);
			resultMap.put(UN_TAGGED_FILE_NAMES, unTaggedFileNames);
			// resultMap.put("actualShareCheckList", getShareCheckList(item));
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	private ICheckListItem getItem(ICheckListItem temp[], long itemRef) {
		ICheckListItem item = null;
		if (temp == null) {
			return item;
		}
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].getCheckListItemRef() == itemRef) {
				item = temp[i];
			}
			else {
				continue;
			}
		}
		return item;
	}
	/*
	 * private List getShareCheckList(ICheckListItem aCheckListItem) { List
	 * shareCheckList = new ArrayList(); if (aCheckListItem.getShareCheckList()
	 * != null) { IShareDoc[] temp = aCheckListItem.getShareCheckList(); if
	 * (temp.length > 0) { for (int i = 0; i < temp.length; i++) {
	 * ((OBShareDoc)temp[i]).setLeNameFromDB(); shareCheckList.add(temp[i]); } }
	 * } return shareCheckList; }
	 */
}
