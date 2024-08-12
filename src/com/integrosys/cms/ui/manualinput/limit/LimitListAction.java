/*
 * Created on Mar 19, 2007
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
public class LimitListAction extends CommonAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.CommonAction#getNextPage(java.lang
	 * .String, java.util.HashMap, java.util.HashMap)
	 */
	protected IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		// TODO Auto-generated method stub
		Page aPage = new Page();
		if (EventConstant.EVENT_LIST_LIMIT.equals(event)) {
			aPage.setPageReference("limitList");
		}
		else if (EventConstant.EVENT_SHOW_LMT_DETAIL.equals(event)) {
			aPage.setPageReference("limitDetail");
		}
		else if (EventConstant.EVENT_SHOW_LMT_CUST_DETAIL.equals(event)) {
			aPage.setPageReference("custlimitDetail");
		}
		else if (EventConstant.EVENT_DEL_LIMIT.equals(event)) {
			aPage.setPageReference("limitDel");
		}
		else if (EventConstant.EVENT_SHOW_REJECTED_LMT_DETAIL.equals(event)) {
			aPage.setPageReference("rejectedLimitDetail");
		}
		else {
			aPage.setPageReference(event);
		}
		return aPage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.CommonAction#getCommandChain(java.
	 * lang.String)
	 */
	protected ICommand[] getCommandChain(String event) {
		// TODO Auto-generated method stub
		ICommand[] objArray = null;
		if (EventConstant.EVENT_LIST_LIMIT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListLimitCmd();
		}
		return objArray;
	}
}
