package com.integrosys.cms.ui.checklist.recurrentDocreceipt;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.common.ActionPartyList;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;

import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Date;

/**
 * @author $Author: wltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2006/07/20 01:44:56 $ Tag: $Name: $
 */
public class ExpressCompleteCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ExpressCompleteCommand() {
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
                { "expressCompletionIndex", "java.lang.String", REQUEST_SCOPE },
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE }
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
                { "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
                { "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE }
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
        HashMap exceptionMap = new HashMap();
        DefaultLogger.debug(this, "Inside doExecute()");
        try {
            String expressCompletionIndex = (String) map.get("expressCompletionIndex");
            DefaultLogger.debug(this, "Selected items for express completion" + expressCompletionIndex);
            HashMap expressCompletionMap = getMapFromString(expressCompletionIndex);

            ICheckList checkList = (ICheckList) map.get("checkList");
            processChecklistItem(checkList.getCheckListItemList(), expressCompletionMap);
            resultMap.put("checkList", checkList);
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


    private void processChecklistItem(ICheckListItem itemList[], HashMap temp) {        
        if ((itemList == null) || (itemList.length < 0)) {
            return;
        }
        Date today = new Date();
        for (int i = 0; i < itemList.length; i++) {
            ICheckListItem item = itemList[i];
            if (temp.containsKey(String.valueOf(i))) {
                item.setReceivedDate((item.getReceivedDate()==null) ? today : item.getReceivedDate());
                item.setCompletedDate((item.getCompletedDate()==null) ? today : item.getCompletedDate());
                item.setItemStatus(ICMSConstant.STATE_ITEM_PENDING_COMPLETE);
            }
        }
    }


    private HashMap getMapFromString(String commaSepInput) {
        HashMap hm = new HashMap();
        StringTokenizer st = new StringTokenizer(commaSepInput, ",");
        while (st.hasMoreTokens()) {
            String key = st.nextToken();
            hm.put(key, key);
        }
        return hm;
    }






}
