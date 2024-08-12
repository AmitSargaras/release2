/*
 * Created on Apr 3, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.security;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.manualinput.limit.LmtDetailAction;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SecDetailAction extends LmtDetailAction {

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

		aPage.setPageReference(getReference(event));
		return aPage;
	}

	private String getReference(String event) {
		/*
		 * if (EventConstant.EVENT_READ.equals(event) ||
		 * EventConstant.EVENT_READ_RETURN.equals(event)) { return "read_page";
		 * }
		 */
		if (EventConstant.EVENT_PREPARE_CREATE.equals(event) || EventConstant.EVENT_PREPARE_UPDATE.equals(event)
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
		else if (EventConstant.EVENT_CREATE_SUB_PLEDGOR.equals(event)) {
			return "create_plg_detail";
		}
		else if (EventConstant.EVENT_CREATE.equals(event) || EventConstant.EVENT_SUBMIT.equals(event)) {
			return "new_return";
			// return "ack_submit";
		}
		else if (EventConstant.EVENT_CANCEL.equals(event)) {
			return "cancel_return";
		}
		else if (EventConstant.EVENT_SAVE.equals(event)) {
			return "ack_save";
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
		else if (EventConstant.EVENT_REFRESH_ORG.equals(event)) {
			return "refresh_org";
		}
		else if (EventConstant.EVENT_REFRESH_SUBTYPE.equals(event)) {
			return "refresh_subtype";
		}
		else if (EventConstant.EVENT_REFRESH_COLLATERAL.equals(event)) {
			return "refresh_colcode";
		}
		else {
			return event;
		}
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;
		if (EventConstant.EVENT_CREATE_SUB_PLEDGOR.equals(event) || EventConstant.EVENT_SUBMIT.equals(event)
				|| EventConstant.EVENT_SAVE.equals(event) || EventConstant.EVENT_CREATE.equals(event)) {
			return EventConstant.EVENT_ERROR_RETURN;
		}
		return errorEvent;
	}

	protected boolean isValidationRequired(String event) {
		if (EventConstant.EVENT_SUBMIT.equals(event) || EventConstant.EVENT_CREATE.equals(event)
				|| EventConstant.EVENT_SAVE.equals(event)) {
			return true;
		}
		return false;
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return MISecValidator.validateMISecurity(aForm, locale);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.CommonAction#getCommandChain(java.
	 * lang.String)
	 */
	protected ICommand[] getCommandChain(String event) {
		// TODO Auto-generated method stub
		ICommand[] objArray = null;
		// TODO Auto-generated method stub
		if (EventConstant.EVENT_PREPARE_CREATE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareCreateSecCmd();
			objArray[1] = new PrepareSecDetailCmd();
		}
		else if (EventConstant.EVENT_READ.equals(event) || EventConstant.EVENT_PROCESS.equals(event)
				|| EventConstant.EVENT_TRACK.equals(event) || EventConstant.EVENT_PREPARE_CLOSE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadSecDetailCmd();
		}
		else if (EventConstant.EVENT_PREPARE_UPDATE.equals(event) || EventConstant.EVENT_PROCESS_UPDATE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadSecDetailCmd();
			objArray[1] = new PrepareSecDetailCmd();
		}
		else if (EventConstant.EVENT_PROCESS_RETURN.equals(event) || EventConstant.EVENT_READ_RETURN.equals(event)
				|| EventConstant.EVENT_CLOSE_RETURN.equals(event) || EventConstant.EVENT_TRACK_RETURN.equals(event)) {
			objArray = new ICommand[0];
			// objArray[0] = new ReturnLmtDetailCmd();
		}
		else if (EventConstant.EVENT_UPDATE_RETURN.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReturnSecDetailCmd();
			objArray[1] = new PrepareSecDetailCmd();
		}
		else if (EventConstant.EVENT_CREATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerCreateSecDetailCmd();
		}
		else if (EventConstant.EVENT_SUBMIT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerUpdateSecDetailCmd();
		}
		else if (EventConstant.EVENT_SAVE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerSaveSecDetailCmd();
		}
		else if (EventConstant.EVENT_APPROVE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerApproveSecDetailCmd();
		}
		else if (EventConstant.EVENT_REJECT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerRejectSecDetailCmd();
		}
		else if (EventConstant.EVENT_CLOSE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerCloseSecDetailCmd();
		}
		else if (EventConstant.EVENT_CREATE_SUB_PLEDGOR.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SaveCurWorkingSecCmd();
		}
		else if (EventConstant.EVENT_DELETE_ITEM.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeleteItemCmd();
			objArray[1] = new PrepareSecDetailCmd();
		}
		else if (EventConstant.EVENT_REFRESH_ORG.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshSecOrgCmd();
		}
		else if (EventConstant.EVENT_REFRESH_SUBTYPE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshSecSubtypeCmd();
		}
		else if (EventConstant.EVENT_REFRESH_COLLATERAL.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshSecCollateralCmd();
		}
		else if (EventConstant.EVENT_ERROR_RETURN.equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new SaveCurWorkingSecCmd();
			objArray[1] = new PrepareSecDetailCmd();
			objArray[2] = new MapSecListCmd();
		}
		return objArray;
	}

}
