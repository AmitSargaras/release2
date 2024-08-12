/**
 * MaintainCommonCodeParameterAction.java
 *
 * Created on January 29, 2007, 11:05 AM
 *
 * Purpose:
 * Description:
 *
 * @Author: BaoHongMan
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.ui.commoncodeentry.item;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.commoncodeentry.CommonCodeEntryCommonAction;

public class MaintainCCEntryAction extends CommonCodeEntryCommonAction {
	public static final String PREPARE_ADD = "prepare_add";

	public static final String CONFIRM_ADD = "confirm_add";

	public static final String CANCEL_ADD = "cancel_add";

	public static final String FAIL_VALIDATE = "fail_validate";

	protected ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, "-event : " + event);
		ICommand cmdArray[] = null;
		if (PREPARE_ADD.equals(event)) {
			cmdArray = new ICommand[1];
			cmdArray[0] = new PrepareAddCommand();
		}
		else if (FAIL_VALIDATE.equals(event)) {
			cmdArray = new ICommand[1];
			cmdArray[0] = new FailValidateCommand();
		}
		else if (CONFIRM_ADD.equals(event)) {
			cmdArray = new ICommand[1];
			cmdArray[0] = new ConfirmAddCommand();
		}
		return cmdArray;
	}

	protected boolean isValidationRequired(String event) {
		return CONFIRM_ADD.equals(event);
	}

	protected ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return CCEntryFormValidator.validate((MaintainCCEntryForm) aForm);
	}

	protected String getErrorEvent(String event) {
		return FAIL_VALIDATE;
	}

	protected IPage getNextPage(String event, HashMap resultMap, HashMap hashMap0) {
		Page page = new Page();
		if (FAIL_VALIDATE.equals(event)) {
			page.setPageReference(PREPARE_ADD);
		}
		else {
			page.setPageReference(event);
		}
		return page;
	}

}