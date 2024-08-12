package com.integrosys.cms.ui.function;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.function.trx.ITeamFunctionGrpTrxValue;

public class TeamFunctionGrpAction extends CommonAction {
	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	protected ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, "*******" + event + "================");

		if (EVENT_LIST_READ.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ListTeamFunctionGrpCommand") };
		}
		else if (EVENT_READ_MAKER_EDIT.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadTeamFunctionGrpCommand") };
		}
		else if (EVENT_READ_CHECKER_APPROVE_REJECT.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadTeamFunctionGrpCommand") };
		}
		else if (EVENT_SUBMIT.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("SubmitTeamFunctionGrpCommand") };
		}
		else if (EVENT_APPROVE.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ApproveTeamFunctionGrpCommand") };
		}
		else if (EVENT_REJECT.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("RejectTeamFunctionGrpCommand") };
		}
		else if (EVENT_READ_MAKER_PROCESS.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadTeamFunctionGrpCommand") };
		}
		else if (EVENT_CLOSE.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("CloseTeamFunctionGrpCommand") };
		}
		else if (EVENT_VIEW.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadTeamFunctionGrpCommand") };
		}
		else if (EVENT_READ_CHECKER_CLOSE.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadTeamFunctionGrpCommand") };
		}
		
		// Unrecognized event.
		return null;
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.debug(this, "Inside validate Input  class " + ((TeamFunctionGrpForm) aForm));
		return TeamFunctionGrpFormValidator.validateInput((TeamFunctionGrpForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		return EVENT_SUBMIT.equals(event);
	}

	protected String getErrorEvent(String event) {

		if (EVENT_APPROVE.equals(event)) {
			return EVENT_LIST_CHECKER_APPROVE_REJECT;
		}
		else if (EVENT_REJECT.equals(event)) {
			return EVENT_LIST_CHECKER_APPROVE_REJECT;
		}
		else if (EVENT_SUBMIT.equals(event)) {
			return EVENT_READ_MAKER_EDIT;
		}

		return null;
	}

	protected IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		// String must be one of the struts local forwards.
		String forward = null;

		ITeamFunctionGrpTrxValue value = (ITeamFunctionGrpTrxValue) resultMap.get("teamFunctionGrpTrxValue");

		boolean isWip = false;

		if (value != null) {
			String status = value.getStatus();
			isWip = status.equals(ICMSConstant.STATE_DRAFT) || status.equals(ICMSConstant.STATE_PENDING_UPDATE)
					|| status.equals(ICMSConstant.STATE_REJECTED) || status.equals(ICMSConstant.STATE_PENDING_CREATE);
		}
		if (EVENT_READ_MAKER_EDIT.equals(event)) {
			if (isWip) {
				forward = "workInProgress";
			}
			else {
				forward = "prepare";
			}
		}
		else if (EVENT_READ_MAKER_PROCESS.equals(event)) {
			forward = "prepare";
		}
		else if (EVENT_SUBMIT.equals(event)) {
			forward = EVENT_SUBMIT;
		}
		else if (EVENT_READ_CHECKER_APPROVE_REJECT.equals(event)) {
			forward = "list2ForChecker";
		}
		else if (EVENT_APPROVE.equals(event)) {
			forward = EVENT_APPROVE;
		}
		else if (EVENT_REJECT.equals(event)) {
			forward = EVENT_REJECT;
		}
		else if (EVENT_CLOSE.equals(event)) {
			forward = EVENT_CLOSE;
		}
		else if (EVENT_READ_CHECKER_CLOSE.equals(event)) {
			forward = EVENT_READ_CHECKER_CLOSE;
		}
		else if (EVENT_LIST_READ.equals(event)) {
			forward = "list";
		}
		else if (EVENT_VIEW.equals(event)) {
			forward = EVENT_VIEW;
		}

		DefaultLogger.debug(this, "The name of struts forward is " + forward);

		Page page = new Page();
		page.setPageReference(forward);

		return page;
	}

	protected String getDefaultEvent() {
		return EVENT_READ;
	}

	/** For reading items. */
	public static final String EVENT_READ = "read";

	public static final String EVENT_READ_MAKER_EDIT = "read_maker_edit";

	public static final String EVENT_LIST_READ = "list_read";

	/** For submitting. */
	public static final String EVENT_SUBMIT = "submit";

	public static final String EVENT_READ_CHECKER_APPROVE_REJECT = "read_checker_approve_reject";

	public static final String EVENT_READ_MAKER_CLOSE = "readMakerClose";

	public static final String EVENT_READ_MAKER_PROCESS = "read_maker_process";

	/** For checker approving. */
	public static final String EVENT_APPROVE = "approve";

	/** For checker rejecting. */
	public static final String EVENT_REJECT = "reject";

	/** For listing for checker. */
	public static final String EVENT_LIST_CHECKER_APPROVE_REJECT = "listCheckerApproveReject";

	/** For maker closing. */
	public static final String EVENT_CLOSE = "close";
	
	public static final String EVENT_READ_CHECKER_CLOSE = "read_checker_close";

	public static final String EVENT_LIST_STAGING = "listStaging";

	/** For totrack view. */
	public static final String EVENT_VIEW = "view";
}
