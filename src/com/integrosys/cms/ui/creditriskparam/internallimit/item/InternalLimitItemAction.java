/*
 Copyright Integro Technologies Pte Ltd
 $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/exchangerate/list/AddExchangeRateListCommand.java,v 1.3 2003/08/22 13:21:41 btchng Exp $
 */
package com.integrosys.cms.ui.creditriskparam.internallimit.item;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.creditriskparam.internallimit.InternalLimitAction;

/**
 * @author $Author: btchng $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/22 13:21:41 $ Tag: $Name%
 */
public class InternalLimitItemAction extends InternalLimitAction {
	public static final String PREPARE_ADD = "prepare_add";

	public static final String PREPARE_EDIT = "prepare_edit";

	public static final String CONFIRM_ADD = "confirm_add";

	public static final String CONFIRM_EDIT = "confirm_edit";

	public static final String CANCEL_ADD = "cancel_add";

	public static final String ADD_DIMENSION = "add_dimension";

	public static final String ADD_DIMENSION_RETURN = "add_return";

	public ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, "-event : " + event);
		ICommand cmdArray[] = null;
		if (PREPARE_ADD.equals(event)) {
			cmdArray = new ICommand[1];
			cmdArray[0] = (ICommand) getNameCommandMap().get("PrepareAddCommand");
		} else if (FAIL_VALIDATE.equals(event)) {
			cmdArray = new ICommand[1];
			cmdArray[0] = (ICommand) getNameCommandMap().get("FailValidateCommand");
		} else if (CONFIRM_ADD.equals(event)) {
			cmdArray = new ICommand[1];
			cmdArray[0] = (ICommand) getNameCommandMap().get("ConfirmAddCommand");
		} else if (ADD_DIMENSION.equals(event)) {
			// cmdArray = new ICommand[1];
			// cmdArray[0] = new SavePage2SessionCommand();
		} else if (ADD_DIMENSION_RETURN.equals(event)) {
			// cmdArray = new ICommand[1];
			// cmdArray[0] = new PrepareEditInternalLimitListCommand();
		}
		return cmdArray;
	}

	protected boolean isValidationRequired(String event) {
		return CONFIRM_ADD.equals(event);
	}

	protected ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return InternalLimitItemFormValidator.validate(
				(InternalLimitItemForm) aForm, locale);
	}

	protected String getErrorEvent(String event) {
		return FAIL_VALIDATE;
	}

	protected IPage getNextPage(String event, HashMap resultMap,
			HashMap hashMap0) {
		Page page = new Page();
		if (FAIL_VALIDATE.equals(event)) {
			page.setPageReference(PREPARE_ADD);
		} else {
			page.setPageReference(event);
		}
		return page;
	}
}
