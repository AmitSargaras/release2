/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/DiaryItemAction.java,v 1.7 2005/11/13 12:06:04 jtan Exp $
 */
package com.integrosys.cms.ui.diaryitem;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.diaryitem.SubmitCustomerCommand;
import com.integrosys.cms.ui.limit.limitaccount.RefreshLimitAccountCommand;
import com.integrosys.cms.ui.diaryitem.CancleFilterCmd;

/**
 * Action class for Diary Item Use Case delegates the event handling to the
 * command classes interested in the events
 * 
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/11/13 12:06:04 $ Tag: $Name: $
 */

public class DiaryItemAction extends CommonAction {
	
	public static final String EVENT_LIST_SEGMENT_WISE = "list_segment_wise";

	public static final String EVENT_LIST_NON_EXPIRED_ITEMS = "list_non_expired";

	public static final String EVENT_LIST_DUE_ITEMS = "list_due_items";

	public static final String EVENT_NOOP = "no_op";

	public static final String EVENT_PRINT = "print";

	public static final String EVENT_GET_REPORT = "get_report";

	public static final String EVENT_CANCEL_NON_EXPIRED_ITEMS = "cancel_non_expired";

	public static final String EVENT_CANCEL_DUE_ITEMS = "cancel_due_items";

	public static final String EVENT_REFRESH = "refresh";
	
	public static final String EVENT_VIEW_OD_SCHEDULE = "view_odschedule";
	
	public static final String EVENT_TODAYS_DIARY = "todays_diary";

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
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (event.equals(EVENT_CREATE)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("CreateDiaryItemCommand") };
		}
		else if (event.equals(EVENT_LIST_NON_EXPIRED_ITEMS) ||event.equals(EVENT_CANCEL_NON_EXPIRED_ITEMS)){
			// SearchNonExpiredItemsCommand searchNonExpiredItemsCommand
			// =(SearchNonExpiredItemsCommand)getNameCommandMap().get("SearchNonExpiredItemsCommand");

			return new ICommand[] { new SearchNonExpiredItemsCommand((ListNonExpiredItemsCommand) getNameCommandMap()
					.get("ListNonExpiredItemsCommand")) };
		}
		else if (event.equals(EVENT_LIST_DUE_ITEMS) ||event.equals(EVENT_CANCEL_DUE_ITEMS)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ListDueItemsCommand") };
		}
		else if (event.equals(EVENT_UPDATE)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("UpdateDiaryItemCommand") };
		}
		else if (event.equals(EVENT_READ)||event.equals(EVENT_VIEW_OD_SCHEDULE)|| event.equals("prepare_update")||event.equals("updateError")) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadDiaryItemCommand") };
		}
		else if (event.equals(EVENT_PREPARE)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("PrepareDiaryItemCommand") };
		}
		else if (event.equals(EVENT_PRINT)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("CreateDiaryItemCommand") };
		}
		else if (event.equals(EVENT_REFRESH)) {
			return new ICommand[] { new RefreshNonExpiredItemsCommand(new SearchNonExpiredItemsCommand(
					(ListNonExpiredItemsCommand) getNameCommandMap().get("ListNonExpiredItemsCommand"))) };
		}else if ((event.equals("list"))) { 
			objArray = new ICommand[1];
			objArray[0] = new DiaryListCustomerCommand();
		}else if ((event.equals("submit"))) {
			objArray = new ICommand[1];
			objArray[0] = new SubmitCustomerCommand();
			//party-----------------------
			//guarantor-----------------------
		}else if ((event.equals("cancleFilter"))) {
			objArray = new ICommand[1];
			objArray[0] = new CancleFilterCmd();
		}else if ("refreshFacilityLine".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshFacilityLineNoCommand();
		}else if ("refreshFacilitySerialNo".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshFacilitySerialNoCommand();
		}else if ("delete".equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("DeleteDiaryItemCommand") };
		}else if (event.equals(EVENT_LIST_SEGMENT_WISE)) {
			objArray = new ICommand[2];
			objArray[0] = new ListDiarySegmentWiseCommand();
			objArray[1] =  new SearchNonExpiredItemsCommand((ListNonExpiredItemsCommand) getNameCommandMap()
					.get("ListNonExpiredItemsCommand")) ;
		}else if (event.equals(EVENT_TODAYS_DIARY)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("TodaysDiaryItemCommand") };
		}else if (event.equals("ODFileError")) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("AckODErrorCommand") };
		}


		DefaultLogger.debug(this, "Unhandled event: " + event);
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
		return DiaryItemValidator.validateInput((DiaryItemForm) aForm, locale);
	}

	/**
	 * Indicates whether validation is required
	 * @param event
	 * @return boolean
	 */
	protected boolean isValidationRequired(String event) {
		return (event.equals(EVENT_CREATE) || event.equals(EVENT_UPDATE)) || event.equals(EVENT_PRINT)
				|| event.equals(EVENT_LIST_DUE_ITEMS) || event.equals(EVENT_LIST_NON_EXPIRED_ITEMS)
				|| event.equals(EVENT_REFRESH);
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
		String nextPage = getReference(event);
		aPage.setPageReference(nextPage);
		
		return aPage;
	}

	/**
	 * Determines what event to be called next on processing errors
	 * @param event
	 * @return String - event name
	 */
	protected String getErrorEvent(String event) {
		if (event.equals(EVENT_CREATE)) {
			return EVENT_PREPARE;
		}
		if (event.equals(EVENT_PRINT)) {
			return EVENT_LIST_NON_EXPIRED_ITEMS;
		}
		if (event.equals(EVENT_REFRESH)) {
			return EVENT_LIST_NON_EXPIRED_ITEMS;
		}
		if (event.equals(EVENT_LIST_DUE_ITEMS)) {
			return EVENT_CANCEL_DUE_ITEMS;
		}
		if (event.equals(EVENT_LIST_NON_EXPIRED_ITEMS)) {
			return EVENT_CANCEL_NON_EXPIRED_ITEMS;
		}
		if (event.equals(EVENT_UPDATE)) {
			return "updateError";
		}
		
		return getDefaultEvent();
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		if (event.equals(EVENT_CREATE) || event.equals(EVENT_UPDATE)) {
			return EVENT_SUBMIT;
		}
		else if (event.equals(EVENT_READ) || event.equals(EVENT_NOOP) || event.equals(EVENT_UPDATE) ||event.equals("prepare_update")) {
			return EVENT_READ;
		}else if (event.equals("updateError")) {
			return "update_error";
		}
		else if (event.equals(EVENT_LIST_NON_EXPIRED_ITEMS)) {
			return EVENT_LIST_NON_EXPIRED_ITEMS;
		}
		else if (event.equals(EVENT_LIST_DUE_ITEMS)) {
			return EVENT_LIST_DUE_ITEMS;
		}
		else if (event.equals(EVENT_PRINT)) {
			return EVENT_GET_REPORT;
		}
		else if (event.equals(EVENT_CANCEL_DUE_ITEMS)) {
			return EVENT_LIST_DUE_ITEMS;
		}
		else if (event.equals(EVENT_CANCEL_NON_EXPIRED_ITEMS)) {
			return EVENT_LIST_NON_EXPIRED_ITEMS;
		}
		else if (event.equals(EVENT_REFRESH)) {
			return EVENT_LIST_NON_EXPIRED_ITEMS;
		}else if (event.equals("list")) {
			return "list";
		}else if("cancleFilter".equals(event)){
			return EVENT_PREPARE;
		}else if (event.equals("refreshFacilityLine")) {
			return "refreshFacilityLine";
		}else if (event.equals("refreshFacilitySerialNo")) {
			return "refreshFacilitySerialNo";
		}else if (event.equals(EVENT_LIST_SEGMENT_WISE)) {
			return EVENT_LIST_SEGMENT_WISE;
		}else if (event.equals("delete")) {
			return "delete";
		}else if (event.equals("ODFileError")) {
			return "ODFileError";
		}else if (event.equals(EVENT_TODAYS_DIARY)) {
			return EVENT_TODAYS_DIARY;
		}
		
		else if (event.equals(EVENT_VIEW_OD_SCHEDULE)) {
			return EVENT_VIEW_OD_SCHEDULE;
		}
		return EVENT_PREPARE;
	}

	/**
	 * returns the default event when none is specified
	 * @return event name
	 */
	protected String getDefaultEvent() {
		return EVENT_PREPARE;
	}
}
