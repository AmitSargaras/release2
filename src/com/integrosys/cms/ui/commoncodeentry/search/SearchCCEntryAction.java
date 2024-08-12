package com.integrosys.cms.ui.commoncodeentry.search;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.commoncodeentry.CommonCodeEntryCommonAction;

public class SearchCCEntryAction extends CommonCodeEntryCommonAction {
	public static final String PREPARE_SEARCH = "prepare_search";

	public static final String CONFIRM_SEARCH = "confirm_search";

	public static final String CANCEL_SEARCH = "cancel_search";

	protected ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, "-event : " + event);
		ICommand cmdArray[] = null;
		if (CONFIRM_SEARCH.equals(event)) {
			cmdArray = new ICommand[1];
			cmdArray[0] = new ConfirmSearchCommand();
		}
		return cmdArray;
	}

	protected boolean isValidationRequired(String event) {
		return false;
	}

	protected IPage getNextPage(String event, HashMap resultMap,
			HashMap hashMap0) {
		Page page = new Page();
		page.setPageReference(event);
		return page;
	}

}
