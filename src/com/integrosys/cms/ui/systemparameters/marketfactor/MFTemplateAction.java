/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.ui.systemparameters.marketfactor;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.IPin;
import com.integrosys.base.uiinfra.common.Page;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MFTemplateAction extends CommonAction implements IPin {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.CommonAction#getNextPage(java.lang
	 * .String, java.util.HashMap, java.util.HashMap)
	 */
	protected IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();

		if ((resultMap.get("wip") != null) && ((String) resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("wip");
			return aPage;
		}

		String page = getReference(event);

		aPage.setPageReference(page);
		return aPage;
	}

	private String getReference(String event) {
		if (EventConstant.EVENT_LIST.equals(event)) {
			return "list";
		}
		else if (EventConstant.EVENT_READ.equals(event) || EventConstant.EVENT_READ_RETURN.equals(event)) {
			return "read_page";
		}
		else if (EventConstant.EVENT_PREPARE_CREATE.equals(event) || EventConstant.EVENT_PREPARE_UPDATE.equals(event)
				|| EventConstant.EVENT_PROCESS_UPDATE.equals(event) || EventConstant.EVENT_UPDATE_RETURN.equals(event)
				|| EventConstant.EVENT_ERROR_RETURN.equals(event) || EventConstant.EVENT_DELETE_ITEM.equals(event)) {
			return "update_page";
		}
		else if (EventConstant.EVENT_PROCESS.equals(event) || EventConstant.EVENT_PROCESS_RETURN.equals(event)) {
			return "process_page";
		}
		else if (EventConstant.EVENT_PREPARE_CLOSE.equals(event) || EventConstant.EVENT_CLOSE_RETURN.equals(event)
				|| EventConstant.EVENT_TRACK.equals(event) || EventConstant.EVENT_TRACK_RETURN.equals(event)) {
			return "close_page";
		}
		else if (EventConstant.EVENT_PREPARE_DELETE.equals(event) || EventConstant.EVENT_PROCESS_DELETE.equals(event)) {
			return "delete_page";
		}
		else if (EventConstant.EVENT_CREATE_ITEM.equals(event)) {
			return "create_item_detail";
		}
		else if (EventConstant.EVENT_UPDATE_ITEM.equals(event)) {
			return "update_item_detail";
		}
		else if (EventConstant.EVENT_CREATE.equals(event) || EventConstant.EVENT_SUBMIT.equals(event)
				|| EventConstant.EVENT_DELETE.equals(event)) {
			return "ack_submit";
		}
		else if (EventConstant.EVENT_APPROVE.equals(event)) {
			return "ack_approve";
		}
		else if (EventConstant.EVENT_REJECT.equals(event)) {
			return "ack_reject";
		}
		else if (EventConstant.EVENT_CLOSE.equals(event)) {
			return "ack_close";
		}
		else {
			return event;
		}
	}

	protected String getErrorEvent(String event) {

		String errorEvent = event;
		if (EventConstant.EVENT_SUBMIT.equals(event) || EventConstant.EVENT_CREATE.equals(event)) {
			return EventConstant.EVENT_ERROR_RETURN;
		}
		return errorEvent;
	}

	protected boolean isValidationRequired(String event) {

		if (EventConstant.EVENT_SUBMIT.equals(event) || EventConstant.EVENT_CREATE.equals(event)) {
			return true;
		}
		return false;
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return MFTemplateValidator.validateMFTemplate(aForm, locale);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.CommonAction#getCommandChain(java.
	 * lang.String)
	 */
	protected ICommand[] getCommandChain(String event) {
		ICommand[] objArray = null;
		// TODO Auto-generated method stub

		DefaultLogger.debug(this, "In action, event=" + event);

		if (EventConstant.EVENT_LIST.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListMFTemplateCmd();
		}
		else if (EventConstant.EVENT_PREPARE_CREATE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareCreateMFTemplateCmd();
			objArray[1] = new PrepareMFTemplateCmd();
		}
		else if (EventConstant.EVENT_READ.equals(event) || EventConstant.EVENT_PROCESS.equals(event)
				|| EventConstant.EVENT_TRACK.equals(event) || EventConstant.EVENT_PREPARE_CLOSE.equals(event)
				|| EventConstant.EVENT_PREPARE_DELETE.equals(event) || EventConstant.EVENT_PROCESS_DELETE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadMFTemplateCmd();
		}
		else if (EventConstant.EVENT_PREPARE_UPDATE.equals(event) || EventConstant.EVENT_PROCESS_UPDATE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadMFTemplateCmd();
			objArray[1] = new PrepareMFTemplateCmd();
		}
		else if (EventConstant.EVENT_PROCESS_RETURN.equals(event) || EventConstant.EVENT_READ_RETURN.equals(event)
				|| EventConstant.EVENT_CLOSE_RETURN.equals(event) || EventConstant.EVENT_TRACK_RETURN.equals(event)) {
			objArray = new ICommand[0];
			// objArray[0] = new ReturnMFTemplateCmd();
		}
		else if (EventConstant.EVENT_UPDATE_RETURN.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReturnMFTemplateCmd();
			objArray[1] = new PrepareMFTemplateCmd();
		}
		else if (EventConstant.EVENT_CREATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerCreateMFTemplateCmd();
		}
		else if (EventConstant.EVENT_SUBMIT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerUpdateMFTemplateCmd();
		}
		else if (EventConstant.EVENT_APPROVE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerApproveMFTemplateCmd();
		}
		else if (EventConstant.EVENT_REJECT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerRejectMFTemplateCmd();
		}
		else if (EventConstant.EVENT_CLOSE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerCloseMFTemplateCmd();
		}
		else if (EventConstant.EVENT_CREATE_ITEM.equals(event) || EventConstant.EVENT_UPDATE_ITEM.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SaveCurWorkingMFTemplateCmd();
		}
		else if (EventConstant.EVENT_DELETE_ITEM.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeleteItemCmd();
			objArray[1] = new PrepareMFTemplateCmd();
		}
		else if (EventConstant.EVENT_ERROR_RETURN.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new SaveCurWorkingMFTemplateCmd();
			objArray[1] = new PrepareMFTemplateCmd();
		}
		else if (EventConstant.EVENT_DELETE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerDeleteMFTemplateCmd();
		}
		return objArray;
	}

}
