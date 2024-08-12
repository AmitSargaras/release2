package com.integrosys.cms.ui.tatdoc;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.tatdoc.trx.ITatDocTrxValue;

public class TatDocAction extends CommonAction {

	/**
	 * the states of transaction/workflow state that next page should show WIP
	 * if current event is <code>read_edit</code>
	 */
	private static final String[] WIP_TRX_STATES = new String[] { ICMSConstant.STATE_DRAFT,
			ICMSConstant.STATE_PENDING_UPDATE, ICMSConstant.STATE_REJECTED, ICMSConstant.STATE_PENDING_CREATE };

	private Map nameCommandMap;

	public static final String AFTER_ERROR_EVENT = "after_error_event";

	public static final String AFTER_ERROR_EVENT_TODO = "after_error_event_todo";

	public static final String EVENT_READ_EDIT = "read_edit";

	public static final String EVENT_READ_EDIT_TODO = "read_edit_todo";

	public static final String EVENT_READ_CLOSE = "read_close";

	public static final String EVENT_SUBMIT = "submit";

	public static final String EVENT_SAVE = "save";

	public static final String EVENT_SUBMIT_TODO = "submit_todo";

	public static final String EVENT_SAVE_TODO = "save_todo";

	public static final String EVENT_APPROVE = "approve";

	public static final String EVENT_REJECT = "reject";

	public static final String EVENT_CLOSE = "close";

	public static final String EVENT_VIEW = "view";

	public static final String EVENT_VIEW_CHECKER = "view_checker";

	public static final String WORK_IN_PROGRESS = "work_in_progress";

	public static final String EVENT_READ_EDIT_NO_WIP = "event_read_edit_no_wip";

	public static final String EVENT_READ_EDIT_NO_WIP_NO_LEFT_FRAME = "event_read_edit_no_wip_no_left_frame";

	public static final String EVENT_CHECKER_READ = "checker_read";

	public static final String EVENT_VIEW_FRAME = "view_frame";
	
	public static final String EVENT_COMMIT = "commit";
	
	public static final String EVENT_UPDATE_DATE = "update_duration_date";

	protected ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, "*******" + event + "================");

		if (EVENT_READ_EDIT.equals(event) || EVENT_READ_EDIT_TODO.equals(event) || EVENT_READ_CLOSE.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadTatDocCommand") };
		}
		else if (EVENT_SUBMIT.equals(event) || EVENT_SUBMIT_TODO.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("TatDocValidationCommand"),
					(ICommand) getNameCommandMap().get("SubmitTatDocCommand") };
		}
		else if (EVENT_SAVE.equals(event) || EVENT_SAVE_TODO.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("TatDocValidationCommand"),
					(ICommand) getNameCommandMap().get("SaveTatDocCommand") };
		}
		else if (EVENT_APPROVE.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ApproveTatDocCommand") };
		}
		else if (EVENT_REJECT.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("RejectTatDocCommand") };
		}
		else if (EVENT_CLOSE.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("CloseTatDocCommand") };
		}
		else if (EVENT_VIEW.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadTatDocCommand") };
		}
		else if (EVENT_VIEW_CHECKER.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadTatDocCommand") };
		}
		else if (EVENT_CHECKER_READ.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadTatDocCommand") };
		}
		
		else if (EVENT_COMMIT.equals(event)) 
		{
			return new ICommand[] { 
					(ICommand) getNameCommandMap().get("CommitTatDocCommand"),
					(ICommand) getNameCommandMap().get("ReadTatDocCommand")
					};
		}
		
		else if (EVENT_UPDATE_DATE.equals(event)) 
		{
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadTatDocCommand")	};
		}

		// Unrecognized event.
		return null;
	}

	protected String getErrorEvent(String event) {
		if (EVENT_SUBMIT.equals(event)) {
			return AFTER_ERROR_EVENT;
		}
		else if (EVENT_SAVE.equals(event)) {
			return AFTER_ERROR_EVENT;
		}
		else if (EVENT_SUBMIT_TODO.equals(event)) {
			return AFTER_ERROR_EVENT_TODO;
		}
		else if (EVENT_SAVE_TODO.equals(event)) {
			return AFTER_ERROR_EVENT_TODO;
		}
        else if (EVENT_COMMIT.equals(event) || EVENT_UPDATE_DATE.equals(event)) {
            return EVENT_READ_EDIT;
        }
		return null;
	}

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	protected IPage getNextPage(String event, HashMap map, HashMap exceptionMap) {
		String forward = "";

		ITatDocTrxValue trxValue = null;
		if (map != null) {
			Map resultMap = (HashMap) map.get("tatFormMapper");
			trxValue = (ITatDocTrxValue) (map.get("tatDocTrxValue") != null ? map.get("tatDocTrxValue")
					: resultMap != null ? resultMap.get("tatDocTrxValue") : null);
		}
		boolean isWip = false;

		if (trxValue != null) {
			String status = trxValue.getStatus();
			isWip = ArrayUtils.contains(WIP_TRX_STATES, status);
		}

		if (EVENT_READ_EDIT.equals(event)) {
			if (isWip) {
				forward = WORK_IN_PROGRESS;
			}
			else {
				forward = EVENT_READ_EDIT;
			}
		}
		else if (EVENT_READ_EDIT_TODO.equals(event)) {
			forward = EVENT_READ_EDIT_TODO;
		}
		else if (EVENT_READ_EDIT_NO_WIP_NO_LEFT_FRAME.equals(event) || AFTER_ERROR_EVENT_TODO.equals(event)) {
			forward = EVENT_READ_EDIT_TODO;
		}
		else if (EVENT_READ_CLOSE.equals(event)) {
			forward = EVENT_VIEW;
		}
		else if (EVENT_SUBMIT.equals(event)) {
			forward = EVENT_SUBMIT;
		}
		else if (EVENT_SAVE.equals(event)) {
			forward = EVENT_SAVE;
		}
		else if (EVENT_SUBMIT_TODO.equals(event)) {
			forward = EVENT_SUBMIT_TODO;
		}
		else if (EVENT_SAVE_TODO.equals(event)) {
			forward = EVENT_SAVE_TODO;
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
		else if (EVENT_VIEW.equals(event)) {
			forward = EVENT_VIEW;
		}
		else if (EVENT_VIEW_CHECKER.equals(event)) {
			forward = EVENT_VIEW_FRAME;
		}
		else if (EVENT_CHECKER_READ.equals(event)) {
			forward = EVENT_CHECKER_READ;
		}
		else if (EVENT_READ_EDIT_NO_WIP.equals(event) || AFTER_ERROR_EVENT.equals(event)) {
			forward = EVENT_READ_EDIT;
		}

		else if(EVENT_COMMIT.equals(event))
			forward = EVENT_READ_EDIT;
		
		else if(EVENT_UPDATE_DATE.equals(event)) 
			forward = EVENT_READ_EDIT;
		
		DefaultLogger.debug(this, "The name of struts forward is " + forward);

		Page page = new Page();
		page.setPageReference(forward);

		return page;
	}

	protected boolean isValidationRequired(String event) {
		return (EVENT_SUBMIT.equals(event) || EVENT_SAVE.equals(event) || EVENT_SUBMIT_TODO.equals(event) || EVENT_SAVE_TODO
				.equals(event) || EVENT_COMMIT.equals(event));
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.debug(this, "Inside validate Input child class");
		return TatDocFormValidator.validateInput((TatDocForm) aForm, locale);
	}
}
