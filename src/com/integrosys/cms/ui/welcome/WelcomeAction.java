package com.integrosys.cms.ui.welcome;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;

public class WelcomeAction extends CommonAction {

	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (EVENT_PREPARE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("WelcomeCommand");
		}
		else if (EVENT_READ.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("WelcomeCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("WelcomeLoadCommand");
		}
		else if (EVENT_LIST.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("WelcomeLoadCommand");
		}
		return (objArray);
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.debug(this, "Inside validate Input child class");
		return WelcomeFormValidator.validateInput((WelcomeForm) aForm, locale);
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();

		if (EVENT_LIST.equals(event)) {
			aPage.setPageReference("loadcount");
		}
		else {
			aPage.setPageReference(EVENT_PREPARE);
		}

		return aPage;
	}

}
