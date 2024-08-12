package com.integrosys.cms.ui.todo;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxSearchCriteria;

public class PrepareToTrackCommand extends PrepareTodoCommand {

	/**
	 * Default Constructor
	 */
	public PrepareToTrackCommand() {

	}

	protected CMSTrxSearchCriteria prepareSearchCriteria(CMSTrxSearchCriteria criteria, String event) {
		criteria.setSearchIndicator(ICMSConstant.TOTRACK_ACTION);
		return criteria;
	}

}
