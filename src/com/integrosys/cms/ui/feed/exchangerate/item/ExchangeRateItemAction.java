package com.integrosys.cms.ui.feed.exchangerate.item;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.feed.exchangerate.ForexAction;

/**
 * This action implements ...
 */
public class ExchangeRateItemAction extends ForexAction {

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

		DefaultLogger.debug(this, "*******" + event + "================");

		if (EVENT_PREPARE.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("PrepareExchangeRateItemCommand") };
		}
		else if (EVENT_SAVE.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("SaveExchangeRateItemCommand") };
		}
		else if (EVENT_SAVE_NOOP.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("PrepareExchangeRateItemCommand") };
		}
		else if (EVENT_CANCEL.equals(event)) {
			return null;
		}
		
		 /**********File Upload****************/
		else if (event.equals(MAKER_PREPARE_UPLOAD_EXCHANGERATEITEM)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("MakerPrepareUploadExchangeRateItemCommand") };
		}
		else if (event.equals(MAKER_EVENT_UPLOAD_EXCHANGERATEITEM)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("MakerUploadExchangeRateItemCommand") };
		}
        else if (event.equals(MAKER_REJECTED_DELETE_READ)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("CheckerReadFileInsertListCommand") };
		}else if ((event != null) && event.equals(CHECKER_APPROVE_INSERT)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("CheckerApproveInsertExchangeRateItemCommand") };
		}else if ( event.equals(CHECKER_REJECT_INSERT)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("CheckerRejectInsertExchangeRateItemCommand") };
		}else if ((event != null) && event.equals(CHECKER_PROCESS_CREATE_INSERT)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("CheckerReadFileInsertListCommand") };
		}else if ((event != null) && event.equals(MAKER_PREPARE_INSERT_CLOSE)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("CheckerReadFileInsertListCommand") };
		}else if ((event != null) && (event.equals(MAKER_CONFIRM_INSERT_CLOSE))) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("MakerInsertCloseExchangeRateItemCommand") };
		}
		 /************************/

		// Unrecognized event.
		return null;

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
		DefaultLogger.debug(this, "Inside validate Input  class" + ((ExchangeRateItemForm) aForm));

		return ExchangeRateItemFormValidator.validateInput((ExchangeRateItemForm) aForm, locale);
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
		//Add by govind for file upload
		 else if ((resultMap.get("errorEveList") != null) && ((String) resultMap.get("errorEveList")).equals("errorEveList")) {
				forward = "maker_fileupload_exchangeRateItem_page";
			}
		 else if (event.equals("maker_event_upload_exchangeRateItem")) {
			 forward = "common_submit_page";
	    }
		
		
		 else if ((event != null) && event.equals("maker_prepare_upload_exchangeRateItem")) {
			 forward = "prepare_upload_exchangeRateItem_page";
			}else if ((event != null) && event.equals("maker_rejected_delete_read")) {
				forward = "maker_view_insert_todo_page";
			}else if ((event != null) && event.equals("checker_approve_insert")) {
				forward = "common_approve_page";
			}else if ((event != null) && event.equals("checker_reject_insert")) {
				forward = "common_reject_page";
			}else if (event.equals("checker_process_create_insert")) {
				forward = "checker_exchangeRateItem_insert_page";
			}else if ((event != null)
					&& (event.equals(MAKER_CONFIRM_INSERT_CLOSE))) {
				forward = "common_close_page";
			} else if ((event != null)
					&& (event.equals(MAKER_PREPARE_INSERT_CLOSE))) {
				forward = "maker_prepare_insert_close";
			}
		
		

		DefaultLogger.debug(this, "the forward is " + forward);

		Page page = new Page();
		page.setPageReference(forward);
		return page;
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
	
	public static final String MAKER_PREPARE_UPLOAD_EXCHANGERATEITEM = "maker_prepare_upload_exchangeRateItem";
	public static final String MAKER_EVENT_UPLOAD_EXCHANGERATEITEM = "maker_event_upload_exchangeRateItem";
	public static final String CHECKER_APPROVE_INSERT = "checker_approve_insert";
	public static final String MAKER_REJECTED_DELETE_READ = "maker_rejected_delete_read";
	public static final String CHECKER_PROCESS_CREATE_INSERT = "checker_process_create_insert";
	public static final String CHECKER_REJECT_INSERT = "checker_reject_insert";
	public static final String MAKER_PREPARE_INSERT_CLOSE = "maker_prepare_insert_close";
	public static final String MAKER_CONFIRM_INSERT_CLOSE = "maker_confirm_insert_close";
	
}
