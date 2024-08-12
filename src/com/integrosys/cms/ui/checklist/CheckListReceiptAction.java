/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/checklist/CheckListReceiptAction.java,v 1.2 2003/08/23 07:54:59 elango Exp $
 */
package com.integrosys.cms.ui.checklist;

import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/23 07:54:59 $ Tag: $Name: $
 */
public class CheckListReceiptAction extends CommonAction {
	
	private Map nameCommandMap;

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if ("process".equals(event) || "edit_staging_checklist_item".equals(event)
				|| "close_checklist_item".equals(event) || "edit".equals(event) || "to_track".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ReadCheckListReceiptTrxCommand");
		}
		return (objArray);
	}

	/**
	 * This method is used to determine which the page to be displayed next
	 * using the event Result hashmap and exception hashmap.It returns the page
	 * object .
	 * 
	 * @param event is of type String
	 * @param resultMap is of type HashMap
	 * @param exceptionMap is of type HashMap
	 * @return IPage
	 */
	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		IRecurrentCheckListTrxValue checkListTrxVal = (IRecurrentCheckListTrxValue) resultMap.get("checkListTrxVal");
		aPage.setPageReference(getRedirectPath(checkListTrxVal));
		return aPage;
	}

	private String getRedirectPath(IRecurrentCheckListTrxValue checkListTrxVal) {
		String redirectPath = "";
		if (checkListTrxVal.getOpDesc().endsWith("ANNEXURE")) {
			redirectPath = "update_annexure";
		}
		else if (checkListTrxVal.getOpDesc().endsWith("RECEIPT")) {
			redirectPath = "update_receipt";
		}
		else {
			redirectPath = "maintain_receipt";
		}
		DefaultLogger.debug(this, "Redirect path for Recurrent Checklist >>>>>>>>>>>>>>>>>>>>>>> " + redirectPath);
		return redirectPath;
	}
}