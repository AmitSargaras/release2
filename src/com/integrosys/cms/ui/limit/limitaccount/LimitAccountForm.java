/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/LimitsAction.java,v 1.9 2006/09/27 06:09:07 hshii Exp $
 */

package com.integrosys.cms.ui.limit.limitaccount;

import com.integrosys.cms.ui.manualinput.limit.XRefDetailForm;

public class LimitAccountForm extends XRefDetailForm {

	public String[][] getMapper() {
		String[][] input = { { "accountOB", "com.integrosys.cms.ui.limit.limitAccount.LimitAccountMapper" }, };
		return input;
	}
}