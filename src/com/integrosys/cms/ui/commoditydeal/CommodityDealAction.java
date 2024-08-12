/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/CommodityDealAction.java,v 1.11 2005/10/14 03:09:46 czhou Exp $
 */
package com.integrosys.cms.ui.commoditydeal;

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
 * Description
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2005/10/14 03:09:46 $ Tag: $Name: $
 */

public class CommodityDealAction extends CommonAction implements IPin {
	public static final String EVENT_PREPARE_UPDATE = "prepare_update";

	public static final String EVENT_PREPARE_UPDATE_SUB = "prepare_update_sub";

	public static final String EVENT_PREPARE_FORM = "prepare_form";

	public static final String EVENT_UPDATE_RETURN = "update_return";

	public static final String EVENT_READ_RETURN = "read_return";

	public static final String EVENT_PROCESS_RETURN = "process_return";

	public static final String EVENT_CLOSE_RETURN = "close_return";

	public static final String EVENT_TRACK_RETURN = "track_return";

	public static final String EVENT_PROCESS_UPDATE = "process_update";

	public static final String EVENT_PREPARE_CLOSE = "prepare_close";

	public static final String EVENT_TRACK = "track";

	public static final String EVENT_CLOSE = "close";

	public static final String EVENT_DELETE_ITEM = "itemDelete";

	public static final String EVENT_REFRESH = "refresh";

	public static final String EVENT_REFRESH_COMMENT = "refresh_comment";

	public static final String EVENT_PREPARE_CAL_POSITION = "prepare_cal_position";

	public static final String EVENT_PREPARE_NOOP_CAL = "prepare_noop_cal";

	public static final String EVENT_REFRESH_CAL_POSITION = "refresh_cal_position";

	public static final String EVENT_CAL_POSITION = "calculate_position";

	public static final String EVENT_PREPARE_ADD_DEAL = "prepare_add_deal";

	public static final String EVENT_CLOSE_DEAL = "close_deal";

	public static final String EVENT_PREPARE_CLOSE_DEAL = "prepare_close_deal";

	public static final String EVENT_NEXT_PAGE = "next_page";

	public static final String EVENT_USER_PROCESS = "user_process";

	public static final String EVENT_USER_TO_TRACK = "user_to_track";

	public static final String EVENT_FORWARD_DEAL = "forward_deal";

	public static final String EVENT_LIST_CLOSED = "list_closed";

	public static final String EVENT_REVALUE_DEALS = "revalue_deals";

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this + " getCommandChain", "event is: " + event);
		ICommand objArray[] = null;
		if (EVENT_SUBMIT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SubmitCommodityDealCommand();
		}
		else if (EVENT_APPROVE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ApproveCommodityDealCommand();
		}
		else if (EVENT_REJECT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RejectCommodityDealCommand();
		}
		else if (EVENT_UPDATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateCommodityDealCommand();
		}
		else if (EVENT_CLOSE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CloseCommodityDealCommand();
		}
		else if (EVENT_LIST.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListCommodityDealCommand();
		}
		else if (EVENT_LIST_CLOSED.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListClosedCommodityDealCommand();
		}
		else if (EVENT_PREPARE_CAL_POSITION.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadCalPositionCommand();
			objArray[1] = new PrepareCalPositionCommand();
		}
		else if (EVENT_REFRESH_CAL_POSITION.equals(event) || EVENT_CAL_POSITION.equals(event)
				|| EVENT_REFRESH.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RefreshCalPositionCommand();
			objArray[1] = new PrepareCalPositionCommand();
		}
		else if (event.equals(EVENT_PREPARE_NOOP_CAL)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareCalPositionCommand();
		}
		else if (event.equals(EVENT_CLOSE_DEAL)) {
			objArray = new ICommand[1];
			objArray[0] = new CloseActiveCommodityDealCommand();
		}
		else if (event.equals(EVENT_FORWARD)) {
			objArray = new ICommand[1];
			objArray[0] = new NavigationCommodityDealcommand();
			/*
			 * } else if (EVENT_USER_PROCESS.equals(event)) { objArray = new
			 * ICommand[2]; objArray[0] = new ReadUserProcessCommand();
			 * objArray[1] = new PrepareUserProcessCommand();
			 */
		}
		else if (EVENT_REFRESH_COMMENT.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RefreshUserProcessCommand();
			objArray[1] = new PrepareUserProcessCommand();
		}
		else if (EVENT_FORWARD_DEAL.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ForwardCommodityDealCommand();
		}
		else if (EVENT_REVALUE_DEALS.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RevaluateCommodityDealsCommand();
			objArray[1] = new ListCommodityDealCommand();
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
		return CommodityDealValidator.validateInput((CommodityDealForm) aForm, locale);
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		if ((resultMap.get("wip") != null) && (resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("wip");
			return aPage;
		}
		if (event.equals(EVENT_EDIT) || event.equals(EVENT_FORWARD)) {
			DefaultLogger.debug(this, "forwardPage is: " + resultMap.get("forwardPage"));
			aPage.setPageReference((String) resultMap.get("forwardPage"));
		}
		else {
			aPage.setPageReference(getReference(event));
		}
		return aPage;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		if (event.equals(EVENT_REFRESH_CAL_POSITION) || event.equals(EVENT_PREPARE_NOOP_CAL)
				|| EVENT_REFRESH.equals(event)) {
			return EVENT_PREPARE_CAL_POSITION;
		}
		if (event.equals(EVENT_REFRESH_COMMENT)) {
			return EVENT_USER_PROCESS;
		}
		if (event.equals(EVENT_CAL_POSITION)) {
			return "calculate_position";
		}
		return event;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;
		if (event.equals(EVENT_REFRESH_CAL_POSITION) || event.equals(EVENT_CAL_POSITION) || EVENT_REFRESH.equals(event)) {
			errorEvent = EVENT_PREPARE_NOOP_CAL;
		}
		if (event.equals(EVENT_FORWARD_DEAL)) {
			errorEvent = EVENT_REFRESH_COMMENT;
		}
		return errorEvent;
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals(EVENT_REFRESH_CAL_POSITION) || event.equals(EVENT_CAL_POSITION) || EVENT_REFRESH.equals(event)) {
			result = true;
		}
		if (event.equals(EVENT_FORWARD_DEAL)) {
			result = true;
		}
		return result;
	}
}
