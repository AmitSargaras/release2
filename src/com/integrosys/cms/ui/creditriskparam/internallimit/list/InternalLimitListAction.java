package com.integrosys.cms.ui.creditriskparam.internallimit.list;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.creditriskparam.internallimit.InternalLimitAction;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.trx.internallimit.IInternalLimitParameterTrxValue;

public class InternalLimitListAction extends InternalLimitAction {

	public static final String PREPARE_EDIT = "prepare_edit";

	public static final String PAGINATE_EDIT = "paginate_edit";

	public static final String PREPARE_VIEW = "prepare_view";

	public static final String PAGINATE_VIEW = "paginate_view";

	public static final String PREPARE_PROCESS = "prepare_process";

	public static final String PAGINATE_PROCESS = "paginate_process";

	public static final String EVENT_SAVE = "save";

	public static final String EVENT_CLOSE = "close";

	public static final String ADD_ITEM = "add_item";

	public static final String ADD_ITEM_RETURN = "add_return";

	public ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, "-event : " + event);
		ICommand cmdArray[] = null;
		if (PREPARE_EDIT.equals(event)) {
			cmdArray = new ICommand[2];
			cmdArray[0] = (ICommand) getNameCommandMap().get("ReadInternalLimitListCommand");
			cmdArray[1] = (ICommand) getNameCommandMap().get("PrepareEditInternalLimitListCommand");
		} else if (ADD_ITEM.equals(event)) {
			cmdArray = new ICommand[1];
			cmdArray[0] = (ICommand) getNameCommandMap().get("SavePage2SessionCommand");
		} else if (ADD_ITEM_RETURN.equals(event)) {
			cmdArray = new ICommand[1];
			cmdArray[0] = (ICommand) getNameCommandMap().get("PrepareEditInternalLimitListCommand");
		} else if (EVENT_DELETE.equals(event)) {
			cmdArray = new ICommand[3];
			cmdArray[0] = (ICommand) getNameCommandMap().get("SavePage2SessionCommand");
			cmdArray[1] = (ICommand) getNameCommandMap().get("DeleteInternalLimitListCommand");
			cmdArray[2] = (ICommand) getNameCommandMap().get("PrepareEditInternalLimitListCommand");
		} else if (EVENT_SAVE.equals(event)) {
			cmdArray = new ICommand[2];
			cmdArray[0] = (ICommand) getNameCommandMap().get("SavePage2SessionCommand");
			cmdArray[1] = (ICommand) getNameCommandMap().get("SaveInternalLimitListCommand");
		} else if (EVENT_SUBMIT.equals(event)) {
			cmdArray = new ICommand[2];
			cmdArray[0] = (ICommand) getNameCommandMap().get("SavePage2SessionCommand");
			cmdArray[1] = (ICommand) getNameCommandMap().get("SubmitInternalLimitListCommand");
		} else if (EVENT_APPROVE.equals(event)) {
			cmdArray = new ICommand[1];
			cmdArray[1] = (ICommand) getNameCommandMap().get("ApproveInternalLimitListCommand");
		} else if (EVENT_REJECT.equals(event)) {
			cmdArray = new ICommand[1];
			cmdArray[1] = (ICommand) getNameCommandMap().get("RejectInternalLimitListCommand");
		} else if (EVENT_CLOSE.equals(event)) {
			cmdArray = new ICommand[1];
			cmdArray[1] = (ICommand) getNameCommandMap().get("CloseInternalLimitListCommand");
		} else if (PAGINATE_EDIT.equals(event) || PAGINATE_VIEW.equals(event)
				|| PAGINATE_PROCESS.equals(event)) {
			cmdArray = new ICommand[1];
			cmdArray[1] = (ICommand) getNameCommandMap().get("PaginateInternalLimitListCommand");
		}
		return cmdArray;
	}

	protected boolean isValidationRequired(String event) {
		if (EVENT_SAVE.equals(event)) {
			return true;
		}
		if (EVENT_SUBMIT.equals(event)) {
			return true;
		}
		if (ADD_ITEM.equals(event)) {
			return true;
		}
		return false;
	}

	/*protected ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return InternalLimitListFormValidator.validate(
				(InternalLimitListForm) aForm, locale);
	}*/

	protected String getErrorEvent(String event) {
		return FAIL_VALIDATE;
	}

	private boolean isWorkInProgress(String event, HashMap resultMap){		
		if(!PREPARE_EDIT.equals(event)){
			return false;
		}		
		IInternalLimitParameterTrxValue trxValue = (IInternalLimitParameterTrxValue) resultMap.get("theILParamTrxValue");
		if(trxValue==null||trxValue.getStatus()==null){
			return false;
		}		
		String status = trxValue.getStatus();
        return (status.equals(ICMSConstant.STATE_DRAFT)||
                 status.equals(ICMSConstant.STATE_PENDING_UPDATE)||
                 status.equals(ICMSConstant.STATE_REJECTED));
	}
	
	protected IPage getNextPage(String event, HashMap resultMap,
			HashMap exceptionMap) {
		
		Page page = new Page();
		if(isWorkInProgress(event,resultMap)){
			page.setPageReference("work_in_progress");
		}else if (event.endsWith("_view")) {
			// view,paginate_view
			page.setPageReference(PREPARE_VIEW);
		} else if (event.endsWith("_process")) {
			// process,paginate_view
			page.setPageReference(PREPARE_PROCESS);
		} else if (event.endsWith("_edit") || ADD_ITEM_RETURN.equals(event)
				|| EVENT_DELETE.equals(event) || FAIL_VALIDATE.equals(event)) {
			page.setPageReference(PREPARE_EDIT);
		} else if(event.equals(ADD_ITEM)){
			page.setPageReference(ADD_ITEM);
		}else{
			page.setPageReference("ack_" + event);
		}
		return page;
	}
}
