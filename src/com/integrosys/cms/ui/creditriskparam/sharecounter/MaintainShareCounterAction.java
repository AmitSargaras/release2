/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * MaintainShareCounterAction
 *
 * Created on 9:30:08 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.ui.creditriskparam.sharecounter;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.cms.ui.role.Page;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 16, 2007 Time: 9:30:08 AM
 */
public class MaintainShareCounterAction extends CommonAction {

	protected String getDefaultEvent() {
		return ShareCounterConstants.SHARE_COUNTER_MAKER_START;
	}

	protected ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, "Now retrieving command class for event : " + event);

		// maker's methods
		// the drop down list
/*		
		if (ShareCounterConstants.SHARE_COUNTER_MAKER_START.equals(event)) {
			return new ICommand[] { new MaintainShareCounterListStockExchangeCommand() };
		}
		else if (ShareCounterConstants.SHARE_COUNTER_MAKER_STOCK_EXCHANGE_SELECTED.equals(event)) {
			return new ICommand[] { new MaintainShareCounterListTypeCommand(),
					new MaintainShareCounterListStockExchangeCommand() };
		}

		// the actual listing
		else 
*/		
		if (ShareCounterConstants.SHARE_COUNTER_MAKER_TYPE_SELECTED.equals(event)) {
			return new ICommand[] { new MaintainShareCounterListEntriesCommand(),
					new MaintainShareCounterPrepareCommonCodeCommand() };
		}
		else if (ShareCounterConstants.SHARE_COUNTER_MAKER_PAGINATE.equals(event)
				|| ShareCounterConstants.SHARE_COUNTER_MAKER_REFRESH.equals(event)) {
			return new ICommand[] { new MaintainShareCounterListEntriesCommand(),
					new MaintainShareCounterPrepareCommonCodeCommand() };
		}
		else if (ShareCounterConstants.SHARE_COUNTER_MAKER_UPDATE.equals(event)) {
			return new ICommand[] { new MaintainShareCounterUpdateCommand() };
		}
		else if (ShareCounterConstants.SHARE_COUNTER_MAKER_UPDATE_ERORR.equals(event)) {
			return new ICommand[] { new MaintainShareCounterListEntriesCommand(),
					new MaintainShareCounterPrepareCommonCodeCommand() };
		}

		// editing the rejected items
		else if (ShareCounterConstants.SHARE_COUNTER_MAKER_PREPARE_UPDATE_REJECTED.equals(event)) {
			return new ICommand[] { new MaintainShareCounterPrepareUpdateRejectedCommand(),
					new MaintainShareCounterPrepareCommonCodeCommand() };
		}
		else if (ShareCounterConstants.SHARE_COUNTER_MAKER_PAGINATE_REJECTED.equals(event)
				|| ShareCounterConstants.SHARE_COUNTER_MAKER_REFRESH_REJECTED.equals(event)) {
			return new ICommand[] { new MaintainShareCounterPrepareUpdateRejectedCommand(),
					new MaintainShareCounterPrepareCommonCodeCommand() };
		}
		else if (ShareCounterConstants.SHARE_COUNTER_MAKER_UPDATE_REJECTED.equals(event)) {
			return new ICommand[] { new MaintainShareCounterUpdateRejectedCommand() };
		}
		else if (ShareCounterConstants.SHARE_COUNTER_MAKER_UPDATE_REJECTED_ERROR.equals(event)) {
			return new ICommand[] { new MaintainShareCounterPrepareUpdateRejectedCommand(),
					new MaintainShareCounterPrepareCommonCodeCommand() };
		}

		// close rejected items
		else if (ShareCounterConstants.SHARE_COUNTER_MAKER_PREPARE_CLOSE.equals(event)) {
			return new ICommand[] { new MaintainShareCounterReadTrxCommand() };
		}
		else if (ShareCounterConstants.SHARE_COUNTER_MAKER_CLOSE.equals(event)) {
			return new ICommand[] { new MaintainShareCounterCloseCommand() };
		}

		// checker's methods
		else if (ShareCounterConstants.SHARE_COUNTER_CHECKER_VIEW.equals(event)) {
			return new ICommand[] { new MaintainShareCounterReadTrxCommand() };
		}
		else if (ShareCounterConstants.SHARE_COUNTER_CHECKER_APPROVE.equals(event)) {
			return new ICommand[] { new MaintainShareCounterApproveCommand() };
		}
		else if (ShareCounterConstants.SHARE_COUNTER_CHECKER_REJECT.equals(event)) {
			return new ICommand[] { new MaintainShareCounterRejectCommand() };
		}

		else if (ShareCounterConstants.SHARE_COUNTER_VIEW.equals(event)
				||ShareCounterConstants.SHARE_COUNTER_VIEW_PAGINATE.equals(event)) {
			return new ICommand[] { new MaintainShareCounterListEntriesCommand() };
		}
		else if (ShareCounterConstants.SHARE_COUNTER_TOTRACK.equals(event)) {
			return new ICommand[] { new MaintainShareCounterReadTrxCommand() };
		}

		return new ICommand[0];
	}

	protected boolean isValidationRequired(String event) {
		boolean flag = ShareCounterConstants.SHARE_COUNTER_MAKER_UPDATE_REJECTED.equals(event)
				|| ShareCounterConstants.SHARE_COUNTER_MAKER_UPDATE.equals(event)
				|| ShareCounterConstants.SHARE_COUNTER_MAKER_TYPE_SELECTED.equals(event)
				|| ShareCounterConstants.SHARE_COUNTER_VIEW.equals(event)
				|| ShareCounterConstants.SHARE_COUNTER_MAKER_PAGINATE.equals(event)
				|| ShareCounterConstants.SHARE_COUNTER_MAKER_PAGINATE_REJECTED.equals(event);

		DefaultLogger.debug(this, "Validation for event \"" + event + "\" is : " + flag);

		return flag;
	}

	protected ActionErrors validateInput(ActionForm actionForm, Locale locale) {
		return ShareCounterValidator.validate(actionForm, locale);
	}

	protected String getErrorEvent(String event) {
		String returnEvent = event;

		if (ShareCounterConstants.SHARE_COUNTER_MAKER_UPDATE.equals(event)
				|| ShareCounterConstants.SHARE_COUNTER_MAKER_PAGINATE.equals(event)) {
			returnEvent = ShareCounterConstants.SHARE_COUNTER_MAKER_UPDATE_ERORR;
		}
		else if (ShareCounterConstants.SHARE_COUNTER_MAKER_UPDATE_REJECTED.equals(event)
				|| ShareCounterConstants.SHARE_COUNTER_MAKER_PAGINATE_REJECTED.equals(event)) {
			returnEvent = ShareCounterConstants.SHARE_COUNTER_MAKER_UPDATE_REJECTED_ERROR;
		} else if (ShareCounterConstants.SHARE_COUNTER_MAKER_TYPE_SELECTED.equals(event)
				|| ShareCounterConstants.SHARE_COUNTER_VIEW.equals(event)) {
			returnEvent = ShareCounterConstants.SHARE_COUNTER_MAKER_STOCK_EXCHANGE_SELECTED;
		}

		DefaultLogger.debug(this, "Error event : " + returnEvent);

		return returnEvent;
	}

	protected IPage getNextPage(String string, HashMap hashMap, HashMap hashMap1) {
		Page page = new Page();

		if (ShareCounterConstants.SHARE_COUNTER_POLICY_NOT_SET.equals(hashMap
				.get(ShareCounterConstants.SHARE_COUNTER_WIP))) {
			page.setPageReference(ShareCounterConstants.SHARE_COUNTER_POLICY_NOT_SET);
		}
		else if (ShareCounterConstants.SHARE_COUNTER_WIP.equals(hashMap.get(ShareCounterConstants.SHARE_COUNTER_WIP))) {
			page.setPageReference(ShareCounterConstants.SHARE_COUNTER_WIP);
		}
		else if (ShareCounterConstants.SHARE_COUNTER_MAKER_PAGINATE.equals(string)
				|| ShareCounterConstants.SHARE_COUNTER_MAKER_REFRESH.equals(string)) {
			page.setPageReference(ShareCounterConstants.SHARE_COUNTER_MAKER_TYPE_SELECTED);
		}
		else if (ShareCounterConstants.SHARE_COUNTER_MAKER_UPDATE_ERORR.equals(string)) {
			page.setPageReference(ShareCounterConstants.SHARE_COUNTER_MAKER_TYPE_SELECTED);
		}
		else if (ShareCounterConstants.SHARE_COUNTER_MAKER_PAGINATE_REJECTED.equals(string)
				|| ShareCounterConstants.SHARE_COUNTER_MAKER_REFRESH_REJECTED.equals(string)) {
			page.setPageReference(ShareCounterConstants.SHARE_COUNTER_MAKER_PREPARE_UPDATE_REJECTED);
		}
		else if (ShareCounterConstants.SHARE_COUNTER_MAKER_UPDATE_REJECTED_ERROR.equals(string)) {
			page.setPageReference(ShareCounterConstants.SHARE_COUNTER_MAKER_PREPARE_UPDATE_REJECTED);
		}else if (ShareCounterConstants.SHARE_COUNTER_VIEW_PAGINATE.equals(string)) {
			page.setPageReference(ShareCounterConstants.SHARE_COUNTER_VIEW);
		}
		else {
			page.setPageReference(string);
		}

		DefaultLogger.debug(this, "Next page is : " + page.getPageReference());

		return page;
	}

}
