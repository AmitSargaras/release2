/*
 * Created on Mar 8, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class LmtSummaryAction extends CommonAction {

	protected IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();

		if ((resultMap.get("wip") != null) && ((String) resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("wip");
			return aPage;
		}

		aPage.setPageReference(getReference(event));
		return aPage;
	}

	private String getReference(String event) {
		if (EventConstant.EVENT_LIST_LIMIT.equals(event)) {
			return "display_limits";
		}
		else {
			return event;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.CommonAction#getCommandChain(java.
	 * lang.String)
	 */
	protected ICommand[] getCommandChain(String event) {
		ICommand[] objArray = null;
		if (EventConstant.EVENT_LIST_LIMIT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListLimitCmd();
		}
		return objArray;
	}

}
