/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealAction
 *
 * Created on 10:33:13 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.ui.predeal;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 21, 2007 Time: 10:33:13 AM
 */
public class PreDealAction extends CommonAction {
	protected String getDefaultEvent() {
		return PreDealConstants.EVENT_MAKER_PREPARE_SEARCH;
	}

	protected ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, "Retrieving command class for event : " + event);

		// maker's events
		if (PreDealConstants.EVENT_MAKER_SEARCH.equals(event)) {
			return new ICommand[] { new PreDealSearchCommand() };
		}
		else if (PreDealConstants.EVENT_MAKER_VIEW_EAR_MARK.equals(event)) {
			return new ICommand[] { new PreDealViewEarMarkGroupCommand(), new PreDealSaveSearchParamCommand() };
		}
		else if (PreDealConstants.EVENT_MAKER_PREPARE_NEW_EAR_MARK.equals(event)) {
			return new ICommand[] { new PreDealPrepareNewEarMarkCommand(), new PreDealSaveSearchParamCommand() /*
																												 * ,
																												 * new
																												 * PreDealPrepareCommonCodeCommand
																												 * (
																												 * )
																												 */};
		}
		else if (PreDealConstants.EVENT_MAKER_SOURCE_SYSTEM.equals(event)
				|| PreDealConstants.EVENT_MAKER_SOURCE_SYSTEM_REJECT.equals(event)) {
			return new ICommand[] { new PreDealSourceSystemCommand() }; // get
																		// source
																		// system
																		// command
		}
		else if (PreDealConstants.EVENT_MAKER_CALCULATE.equals(event)
				|| PreDealConstants.EVENT_MAKER_CALCULATE_REJECT.equals(event)) {
			return new ICommand[] { new PreDealCalculateCommand() /*
																 * , new
																 * PreDealPrepareCommonCodeCommand
																 * ()
																 */};
		}
		else if (PreDealConstants.EVENT_MAKER_PRESUBMIT_NEW_EAR_MARK.equals(event)) {
			return new ICommand[] { new PreDealPreSubmitNewEarMarkCommand(), new PreDealSaveSearchParamCommand() };
		}
		else if (PreDealConstants.EVENT_MAKER_SUBMIT_NEW_EAR_MARK.equals(event)) {
			return new ICommand[] { new PreDealCreateNewEarMarkCommand() };
		}
		else if (PreDealConstants.EVENT_MAKER_PREPARE_TRANSFER.equals(event)
				|| PreDealConstants.EVENT_MAKER_PREPARE_DELETE.equals(event)
				|| PreDealConstants.EVENT_MAKER_PREPARE_RELEASE.equals(event)) {
			return new ICommand[] { new PreDealPrepareUpdateEarMarkCommand() };
		}
		else if (PreDealConstants.EVENT_MAKER_SUBMIT_DELETE.equals(event)) {
			return new ICommand[] { new PreDealDeleteEarMarkCommand() };
		}
		else if (PreDealConstants.EVENT_MAKER_SUBMIT_TRANSFER.equals(event)
				|| PreDealConstants.EVENT_MAKER_SUBMIT_RELEASE.equals(event)) {
			return new ICommand[] { new PreDealUpdateEarMarkCommand() };
		}
		else if (PreDealConstants.EVENT_MAKER_PREPARE_CLOSE_DELETE.equals(event)
				|| PreDealConstants.EVENT_MAKER_PREPARE_CLOSE_RELEASE.equals(event)
				|| PreDealConstants.EVENT_MAKER_PREPARE_CLOSE_TRANSFER.equals(event)
				|| PreDealConstants.EVENT_MAKER_PREPARE_CLOSE_UPDATE.equals(event)
				|| PreDealConstants.EVENT_MAKER_PREPARE_CLOSE_CREATE.equals(event)
				|| PreDealConstants.EVENT_MAKER_PREPARE_UPDATE_REJECT.equals(event)) {
			return new ICommand[] { new PreDealCheckerPrepareProcessCommand() /*
																			 * ,
																			 * new
																			 * PreDealPrepareCommonCodeCommand
																			 * (
																			 * )
																			 */};
		}
		else if (PreDealConstants.EVENT_MAKER_SUBMIT_CLOSE.equals(event)) {
			return new ICommand[] { new PreDealCloseEarMarkCommand() };
		}
		else if (PreDealConstants.EVENT_MAKER_PRESUBMIT_UPDATE_REJECT.equals(event)) {
			return new ICommand[] { new PreDealPreUpdateRejectedCommand() };
		}
		else if (PreDealConstants.EVENT_MAKER_SUBMIT_UPDATE_REJECT.equals(event)) {
			return new ICommand[] { new PreDealUpdateRejectNewEarMarkCommand() };
		}
		else if (PreDealConstants.EVENT_MAKER_VIEW_CONCENTRATION.equals(event)) {
			return new ICommand[] { new PreDealViewConcentrationCommand(), new PreDealSaveSearchParamCommand() };
		}

		// checker's events
		else if (PreDealConstants.EVENT_CHECKER_PROCESS.equals(event)
				|| PreDealConstants.EVENT_CHECKER_PROCESS_DELETE.equals(event)) {
			return new ICommand[] { new PreDealCheckerPrepareProcessCommand() };
		}
		else if (PreDealConstants.EVENT_CHECKER_PROCESS_NEW.equals(event)) {
			return new ICommand[] { new PreDealCheckerPrepareProcessCommand() };
		}
		else if (PreDealConstants.EVENT_CHECKER_APPROVE.equals(event)) {
			return new ICommand[] { new PreDealConfirmApproveCommand() };
		}
		else if (PreDealConstants.EVENT_CHECKER_APPROVE_NEW.equals(event)) {
			return new ICommand[] { new PreDealConfirmApproveCommand() };
		}
		else if (PreDealConstants.EVENT_CHECKER_REJECT.equals(event)
				|| PreDealConstants.EVENT_CHECKER_REJECT_NEW.equals(event)) {
			return new ICommand[] { new PreDealConfirmRejectCommand() };
		}

		else if (PreDealConstants.EVENT_TO_TRACK.equals(event)) {
			return new ICommand[] { new PreDealToTrackCommand() };
		}

		return new ICommand[0];
	}

	protected boolean isValidationRequired(String event) {
		boolean flag = PreDealConstants.EVENT_MAKER_SEARCH.equals(event)
				|| PreDealConstants.EVENT_MAKER_CALCULATE.equals(event)
				|| PreDealConstants.EVENT_MAKER_PRESUBMIT_NEW_EAR_MARK.equals(event)
				|| PreDealConstants.EVENT_MAKER_SUBMIT_RELEASE.equals(event);// ||
		PreDealConstants.EVENT_MAKER_CALCULATE_REJECT.equals(event);

		DefaultLogger.debug(this, "Validation required for event \"" + event + "\" is : " + flag);

		return flag;
	}

	protected ActionErrors validateInput(ActionForm form, Locale locale) {
		if (form instanceof PreDealForm) {
			PreDealForm preDealForm = (PreDealForm) form;
			String event = preDealForm.getEvent();

			if (PreDealConstants.EVENT_MAKER_SEARCH.equals(event)) {
				return PreDealValidator.validateSearchParameters((PreDealForm) form, locale);
			}
			else if (PreDealConstants.EVENT_MAKER_CALCULATE.equals(event)
					|| PreDealConstants.EVENT_MAKER_PRESUBMIT_NEW_EAR_MARK.equals(event)) {
				return PreDealValidator.validateNewEarMarking((PreDealForm) form, locale);
			}
			else if (PreDealConstants.EVENT_MAKER_SUBMIT_RELEASE.equals(event)) {
				return PreDealValidator.validateEarMarkRelease((PreDealForm) form, locale);
			}
		}

		return super.validateInput(form, locale);
	}

	protected String getErrorEvent(String event) {
		String returnStr = event;

		if (PreDealConstants.EVENT_MAKER_SEARCH.equals(event)) {
			returnStr = PreDealConstants.EVENT_MAKER_PREPARE_SEARCH;
		}
		else if (PreDealConstants.EVENT_MAKER_CALCULATE.equals(event)
				|| PreDealConstants.EVENT_MAKER_SOURCE_SYSTEM.equals(event)
				|| PreDealConstants.EVENT_MAKER_PRESUBMIT_NEW_EAR_MARK.equals(event)) {
			returnStr = PreDealConstants.EVENT_MAKER_PREPARE_NEW_EAR_MARK;
		}
		else if (PreDealConstants.EVENT_MAKER_CALCULATE_REJECT.equals(event)
				|| PreDealConstants.EVENT_MAKER_SOURCE_SYSTEM_REJECT.equals(event)
				|| PreDealConstants.EVENT_MAKER_PRESUBMIT_UPDATE_REJECT.equals(event)) {
			returnStr = PreDealConstants.EVENT_MAKER_PREPARE_UPDATE_REJECT;
		}
		else if (PreDealConstants.EVENT_MAKER_SUBMIT_RELEASE.equals(event)) {
			returnStr = PreDealConstants.EVENT_MAKER_PREPARE_RELEASE;
		}

		DefaultLogger.debug(this, "Error event for event \"" + event + "\" is : " + returnStr);

		return returnStr;
	}

	protected IPage getNextPage(String event, HashMap resultMap, HashMap execptionMap) {
		Page page = new Page();

		DefaultLogger.debug(this, "Retreiving page for event : " + event);

		if (PreDealConstants.EVENT_MAKER_CALCULATE.equals(event)) {
			page.setPageReference(PreDealConstants.EVENT_MAKER_PREPARE_NEW_EAR_MARK);
		}
		else if (PreDealConstants.EVENT_MAKER_CALCULATE_REJECT.equals(event)) {
			page.setPageReference(PreDealConstants.EVENT_MAKER_PREPARE_UPDATE_REJECT);
		}
		else if (PreDealConstants.EVENT_MAKER_SOURCE_SYSTEM.equals(event)) {
			page.setPageReference(PreDealConstants.EVENT_MAKER_PREPARE_NEW_EAR_MARK);
		}
		if (PreDealConstants.EVENT_MAKER_SOURCE_SYSTEM_REJECT.equals(event)) {
			page.setPageReference(PreDealConstants.EVENT_MAKER_PREPARE_UPDATE_REJECT);
		}
		else if (PreDealConstants.EVENT_CHECKER_APPROVE_NEW.equals(event)) {
			page.setPageReference(PreDealConstants.EVENT_CHECKER_APPROVE);
		}
		else if (PreDealConstants.EVENT_CHECKER_REJECT_NEW.equals(event)) {
			page.setPageReference(PreDealConstants.EVENT_CHECKER_REJECT);
		}
		else if (PreDealConstants.EVENT_MAKER_WIP.equals(resultMap.get(PreDealConstants.EVENT_MAKER_WIP))) {
			page.setPageReference(PreDealConstants.EVENT_MAKER_WIP);
		}
		else if (PreDealConstants.EVENT_ERROR_PAGE.equals(resultMap.get(PreDealConstants.EVENT_ERROR_PAGE))) {
			page.setPageReference(PreDealConstants.EVENT_ERROR_PAGE);
		}
		else if (PreDealConstants.MAX_CAP_BREACH.equals(resultMap.get(PreDealConstants.LIMIT_LEVEL_BREACHED))) {
			page.setPageReference(PreDealConstants.MAX_CAP_BREACH);
		}
		// else if ( PreDealConstants.QUOTA_CAP_BREACH.equals ( resultMap.get (
		// PreDealConstants.LIMIT_LEVEL_BREACHED ) ) )
		// {
		// page.setPageReference ( PreDealConstants.QUOTA_CAP_BREACH ) ;
		// }
		else if ((event != null) && event.startsWith("checker")) {
			if (PreDealConstants.EARMARK_STATUS_DELETED.equals(resultMap.get(PreDealConstants.UPDATE_TYPE))) {
				page.setPageReference(PreDealConstants.EVENT_CHECKER_PROCESS_DELETE);
			}
			else if (PreDealConstants.EARMARK_STATUS_RELEASED.equals(resultMap.get(PreDealConstants.UPDATE_TYPE))) {
				page.setPageReference(PreDealConstants.EVENT_CHECKER_PROCESS_RELEASE);
			}
			else if (PreDealConstants.EARMARK_STATUS_HOLDING.equals(resultMap.get(PreDealConstants.UPDATE_TYPE))) {
				page.setPageReference(PreDealConstants.EVENT_CHECKER_PROCESS_TRANSFER);
			}
		}
		else if (PreDealConstants.EVENT_MAKER_PREPARE_CLOSE_UPDATE.equals(event)) {
			if (PreDealConstants.EARMARK_STATUS_RELEASED.equals(resultMap.get(PreDealConstants.UPDATE_TYPE))) {
				page.setPageReference(PreDealConstants.EVENT_MAKER_PREPARE_CLOSE_RELEASE);
			}
			else if (PreDealConstants.EARMARK_STATUS_HOLDING.equals(resultMap.get(PreDealConstants.UPDATE_TYPE))) {
				page.setPageReference(PreDealConstants.EVENT_MAKER_PREPARE_CLOSE_TRANSFER);
			}
		}
		else if (PreDealConstants.EVENT_TO_TRACK.equals(event)) {
			page.setPageReference((String) resultMap.get(PreDealConstants.NEXT_EVENT));
		}

		if (page.getPageReference() == null) {
			page.setPageReference(event);
		}

		DefaultLogger.debug(this, "Page reference found is : " + page.getPageReference());

		return page;
	}

}
