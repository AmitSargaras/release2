/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/titledocument/item/TitleDocumentItemAction.java,v 1.2 2004/06/04 05:11:52 hltan Exp $
 */
package com.integrosys.cms.ui.commodityglobal.titledocument.item;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.commodityglobal.titledocument.TitleDocumentAction;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:11:52 $ Tag: $Name: $
 */

public class TitleDocumentItemAction extends TitleDocumentAction {
	public static final String EVENT_ADD = "add";

	public static final String EVENT_PREPARE_ADD = "prepare_add";

	public static final String EVENT_PREPARE_NOOP = "prepare_noop";

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (event.equals(EVENT_ADD)) {
			objArray = new ICommand[1];
			objArray[0] = new AddTitleDocumentItemCommand();
		}
		else if (event.equals(EVENT_UPDATE)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateTitleDocumentItemCommand();
		}
		else if (event.equals(EVENT_PREPARE)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadTitleDocumentItemCommand();
			objArray[1] = new PrepareTitleDocumentItemCommand();
		}
		else if (event.equals(EVENT_PREPARE_ADD) || event.equals(EVENT_PREPARE_NOOP)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareTitleDocumentItemCommand();
		}
		return objArray;
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
		return TitleDocumentItemValidator.validateInput((TitleDocumentItemForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals(EVENT_ADD) || event.equals(EVENT_UPDATE)) {
			result = true;
		}
		return result;
	}

	/**
	 * This method is used to determine which the page to be displayed next
	 * using the event Result hashmap and exception hashmap.It returns the page
	 * object .
	 * 
	 * @param event is of type String
	 * @param resultMap is of type HashMap
	 * @param exceptionMap is of type HashMap
	 * @return IPage
	 */
	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		aPage.setPageReference(getReference(event));
		return aPage;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;
		if (event.equals(EVENT_UPDATE) || event.equals(EVENT_ADD)) {
			errorEvent = EVENT_PREPARE_NOOP;
		}
		return errorEvent;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		if (event.equals(EVENT_CANCEL) || event.equals(EVENT_ADD) || event.equals(EVENT_UPDATE)) {
			return "return";
		}
		if (event.equals(EVENT_PREPARE_NOOP) || event.equals(EVENT_PREPARE_ADD)) {
			return EVENT_PREPARE;
		}
		return event;
	}
}
