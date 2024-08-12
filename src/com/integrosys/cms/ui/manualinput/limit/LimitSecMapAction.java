/*
 * Created on 2007-2-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class LimitSecMapAction extends LmtDetailAction {
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
		if (EventConstant.EVENT_PREPARE_CREATE.equals(event) || EventConstant.EVENT_SEARCH_SECDETAIL.equals(event)) {
			return "update_lmtsec";
		}
		if (EventConstant.EVENT_READ_SECURITY.equals(event)) {
			return "view_lmtsec";
		}else if (EventConstant.EVENT_READ_SECURITY_REJECTED.equals(event)) {
			return "view_lmtsec_rejected";
		}
		else if (EventConstant.EVENT_CREATE.equals(event) || EventConstant.EVENT_CANCEL.equals(event)) {
			return "update_return";
		}
		else if (EventConstant.EVENT_CREATE_NEW_SEC.equals(event)) {
			return "new_sec";
		}else if (EventConstant.EVENT_SEARCH_SECPROPDETAIL.equals(event)) {
			return "update_lmtsec";
		}

		return event;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;
		if (EventConstant.EVENT_CREATE.equals(event)) {
			return EventConstant.EVENT_PREPARE_CREATE;
		}
		return errorEvent;
	}

	protected boolean isValidationRequired(String event) {
		if (EventConstant.EVENT_CREATE.equals(event)) {
			return true;
		}
		return false;
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return MILimitValidator.validateXRef(aForm, locale);
	}

	protected ICommand[] getCommandChain(String event) {
		ICommand[] objArray = null;
		// TODO Auto-generated method stub
		if (EventConstant.EVENT_PREPARE_CREATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareSecDetailCmd();
		}
		else if (EventConstant.EVENT_CREATE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new SaveSecDetailCmd();
			objArray[1] = new ReturnLmtDetailCmd();
		}
		else if (EventConstant.EVENT_SEARCH_SECDETAIL.equals(event) || EventConstant.EVENT_READ_SECURITY.equals(event)
				|| EventConstant.EVENT_SEARCH_SECPROPDETAIL.equals(event)
				||EventConstant.EVENT_READ_SECURITY_REJECTED.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new SearchSecDetailCmd();
			objArray[1] = new PrepareSecDetailCmd();
		}
		else if (EventConstant.EVENT_CANCEL.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReturnLmtDetailCmd();
		}
		else if (EventConstant.EVENT_CREATE_NEW_SEC.equals(event)) {
			objArray = new ICommand[0];
		}
		return objArray;
	}
}
