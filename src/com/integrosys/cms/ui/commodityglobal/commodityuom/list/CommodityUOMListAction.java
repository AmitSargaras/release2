/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityuom/list/CommodityUOMListAction.java,v 1.6 2006/11/10 10:44:32 nkumar Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityuom.list;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.commodityglobal.commodityuom.CommodityUOMAction;

/**
 * Description
 * 
 * @author $Author: nkumar $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/11/10 10:44:32 $ Tag: $Name: $
 */

public class CommodityUOMListAction extends CommodityUOMAction {
	public final static String EVENT_SELECT = "select";

	public final static String EVENT_PRE_CLOSE = "prepare_close";

	public final static String EVENT_RETURN = "return";

	public final static String EVENT_TO_TRACK = "to_track";

	public final static String EVENT_PROCESS_UPDATE = "process_update";

	public final static String EVENT_CLOSE = "close";

	public final static String EVENT_REFRESH = "refresh";

	public final static String EVENT_PROCESS_NOOP = "process_noop";

	public final static String EVENT_PREPARE_NOOP = "prepae_noop";

	public final static String EVENT_READ = "read";

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (event.equals(EVENT_PREPARE) || event.equals(EVENT_PROCESS) || event.equals(EVENT_PROCESS_UPDATE)
				|| event.equals(EVENT_TO_TRACK) || event.equals(EVENT_PRE_CLOSE) || event.equals(EVENT_READ)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadCommodityUOMListCommand();
			objArray[1] = new PrepareCommodityUOMListCommand();
		}
		else if (event.equals(EVENT_DELETE)) {
			objArray = new ICommand[1];
			objArray[0] = new DeleteCommodityUOMListCommand();
		}
		else if (event.equals(EVENT_SUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = new SubmitCommodityUOMListCommand();
		}
		else if (EVENT_UPDATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateCommodityUOMListCommand();
		}
		else if (event.equals(EVENT_SELECT) || event.equals(EVENT_REFRESH)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareCommodityUOMCommand();
		}
		else if (event.equals(EVENT_APPROVE)) {
			objArray = new ICommand[1];
			objArray[0] = new ApproveCommodityUOMListCommand();
		}
		else if (event.equals(EVENT_REJECT)) {
			objArray = new ICommand[1];
			objArray[0] = new RejectCommodityUOMListCommand();
		}
		else if (event.equals(EVENT_CLOSE)) {
			objArray = new ICommand[1];
			objArray[0] = new CloseCommodityUOMListCommand();
		}
		else if (EVENT_PREPARE_NOOP.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareCommodityUOMListCommand();
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
		return CommodityUOMListValidator.validateInput((CommodityUOMListForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals(EVENT_PREPARE) || event.equals(EVENT_APPROVE) || event.equals(EVENT_SUBMIT)
				|| event.equals(EVENT_REJECT) || EVENT_UPDATE.equals(event) || EVENT_READ.equals(event)) {
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
		if ((resultMap.get("wip") != null) && ((String) resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("wip");
			return aPage;
		}

		aPage.setPageReference(getReference(event));
		return aPage;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if (event.equals(EVENT_PREPARE) || event.equals(EVENT_READ)) {
			return EVENT_SELECT;
		}
		if (event.equals(EVENT_APPROVE) || event.equals(EVENT_REJECT)) {
			return EVENT_PROCESS_NOOP;
		}
		if (EVENT_SUBMIT.equals(event) || EVENT_UPDATE.equals(event)) {
			return EVENT_PREPARE_NOOP;
		}
		return errorEvent;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		if (event.equals(EVENT_RETURN) || event.equals(EVENT_PROCESS_UPDATE) || event.equals(EVENT_DELETE)
				|| EVENT_PREPARE_NOOP.equals(event)) {
			return EVENT_PREPARE;
		}
		if (event.equals(EVENT_REFRESH)) {
			return EVENT_SELECT;
		}
		if (event.equals(EVENT_PROCESS_NOOP)) {
			return EVENT_PROCESS;
		}
		return event;
	}
}
