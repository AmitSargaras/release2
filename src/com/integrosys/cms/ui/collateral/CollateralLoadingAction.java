package com.integrosys.cms.ui.collateral;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

public class CollateralLoadingAction extends CommonAction {

	protected ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (EVENT_LIST.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadListCollateralMapCommand();
		}
		return objArray;
	}

	protected IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		String forward = "";

		if (EVENT_LIST.equals(event)) {
			forward = EVENT_LIST;
		}

		DefaultLogger.debug(this, "The name of struts forward is " + forward);

		Page page = new Page();
		page.setPageReference(forward);

		return page;
	}

}
