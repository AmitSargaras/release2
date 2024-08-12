package com.integrosys.cms.ui.feed.gold.item;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.feed.exchangerate.item.ExchangeRateItemForm;
import com.integrosys.cms.ui.feed.exchangerate.item.ExchangeRateItemFormValidator;
import com.integrosys.cms.ui.feed.gold.GoldAction;

public class GoldItemAction extends GoldAction {

	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	protected ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, "*******" + event + "================");

		if (EVENT_PREPARE.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("PrepareGoldItemCommand") };
		}
		else if (EVENT_SAVE.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("SaveGoldItemCommand") };
		}
		else if (EVENT_SAVE_NOOP.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("PrepareGoldItemCommand") };
		}
		else if (EVENT_CANCEL.equals(event)) {
			return null;
		}

		// Unrecognized event.
		return null;
	}

	protected IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		String forward = null;

		if (EVENT_PREPARE.equals(event)) {
			forward = "prepare";
		}
		else if (EVENT_SAVE.equals(event)) {
			if (exceptionMap.isEmpty()) {
				forward = "save";
			}
			else {
				forward = "prepare";
			}
		}
		else if (EVENT_SAVE_NOOP.equals(event)) {
			forward = "prepare";
		}
		else if (EVENT_CANCEL.equals(event)) {
			forward = "save";
		}

		DefaultLogger.debug(this, "the forward is " + forward);

		Page page = new Page();
		page.setPageReference(forward);
		return page;
	}

	/**
	 * This method is called only for create and Update command to validate the
	 * form and return the ActionErrors object.
	 * 
	 * @param aForm is of type ActionForm
	 * @param locale of type Locale
	 * @return ActionErrors
	 */
	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.debug(this, "Inside validate Input  class" + ((GoldItemForm) aForm));

		return GoldItemFormValidator.validateInput((GoldItemForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		return EVENT_SAVE.equals(event);
	}

	protected String getErrorEvent(String event) {
		if (EVENT_SAVE.equals(event)) {
			return EVENT_SAVE_NOOP;
		}

		return null;
	}

	public static final String EVENT_PREPARE = "prepare";

	public static final String EVENT_SAVE = "save";

	public static final String EVENT_SAVE_NOOP = "saveNoop";

	public static final String EVENT_CANCEL = "cancel";
}
