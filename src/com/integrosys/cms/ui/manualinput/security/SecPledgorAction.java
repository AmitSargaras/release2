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

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SecPledgorAction extends SecDetailAction {
	protected IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();

		if ((resultMap.get("wip") != null) && ((String) resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("wip");
			return aPage;
		}

		aPage.setPageReference(getReference(event, resultMap, exceptionMap));
		return aPage;
	}

	private String getReference(String event, HashMap resultMap, HashMap exceptionMap) {
		if (EventConstant.EVENT_PREPARE_CREATE.equals(event) || EventConstant.EVENT_SEARCH_PLGDETAIL.equals(event)) {
			return "update_pledgor";
		}
		else if (EventConstant.EVENT_CREATE.equals(event) || EventConstant.EVENT_CANCEL.equals(event)) {
			return "update_return";
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
		return MISecValidator.validatePledgorDtl(aForm, locale);
	}

	protected ICommand[] getCommandChain(String event) {
		ICommand[] objArray = null;
		// TODO Auto-generated method stub
		if (EventConstant.EVENT_PREPARE_CREATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PreparePledgorDtlCmd();
		}
		else if (EventConstant.EVENT_CREATE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new SavePledgorDtlCmd();
			objArray[1] = new ReturnSecDetailCmd();
		}
		else if (EventConstant.EVENT_SEARCH_PLGDETAIL.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new SearchPledgorDetailCmd();
			objArray[1] = new PreparePledgorDtlCmd();
		}
		else if (EventConstant.EVENT_CANCEL.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReturnSecDetailCmd();
		}
		return objArray;
	}
}
