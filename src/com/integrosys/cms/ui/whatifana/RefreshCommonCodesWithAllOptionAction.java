package com.integrosys.cms.ui.whatifana;

import com.integrosys.cms.ui.commoncode.RefreshCommonCodesAction;

/**
 * Action to generate common codes options tag, this will append an 'ALL' option
 * at the end of the list.
 * 
 * @author Chong Jun Yong
 * 
 */
public class RefreshCommonCodesWithAllOptionAction extends RefreshCommonCodesAction {

	protected void afterProcessOptions(StringBuffer optionsBuf) {
		optionsBuf.append("<option label=\"ALL\" value=\"ALL\" />");
	}

}
