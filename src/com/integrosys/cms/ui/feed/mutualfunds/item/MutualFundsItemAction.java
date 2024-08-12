package com.integrosys.cms.ui.feed.mutualfunds.item;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.feed.mutualfunds.MutualFundsAction;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class MutualFundsItemAction extends MutualFundsAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	
	private Map nameCommandMap;

	protected Map getNameCommandMap() {
		return this.nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}
	
	public ICommand[] getCommandChain(String event) {

		DefaultLogger.debug(this, "*******" + event + "================");

		if (EVENT_PREPARE.equals(event)) {
			return new ICommand[] {(ICommand) getNameCommandMap().get("PrepareMutualFundsItemCommand")};
		}
		else if (EVENT_SAVE.equals(event)) {
			return new ICommand[] {(ICommand) getNameCommandMap().get("SaveMutualFundsItemCommand")};
		}
		else if (EVENT_SAVE_NOOP.equals(event)) {
			ICommand[] objArray = null;
			objArray = new ICommand[2];
			objArray[0] = new SaveCurWorkingMutualFundsCmd();
			objArray[1] = new PrepareMutualFundsItemCommand();
			return objArray;
		}
		else if (EVENT_CANCEL.equals(event)) {
			return null;
		}
		 /**********File Upload****************/
		else if (event.equals(MAKER_PREPARE_UPLOAD_MUTUALFUNDSITEM)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("MakerPrepareUploadMutualFundsItemCommand") };
		}
		else if (event.equals(MAKER_EVENT_UPLOAD_MUTUALFUNDSITEM)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("MakerUploadMutualFundsItemCommand") };
		}
       else if (event.equals(MAKER_REJECTED_DELETE_READ)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("CheckerReadFileInsertListCommand") };
		}else if ((event != null) && event.equals(CHECKER_APPROVE_INSERT)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("CheckerApproveInsertMutualFundsItemCommand") };
		}else if ( event.equals(CHECKER_REJECT_INSERT)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("CheckerRejectInsertMutualFundsItemCommand") };
		}else if ((event != null) && event.equals(CHECKER_PROCESS_CREATE_INSERT)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("CheckerReadFileInsertListCommand") };
		}else if ((event != null) && event.equals(MAKER_PREPARE_INSERT_CLOSE)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("CheckerReadFileInsertListCommand") };
		}else if ((event != null) && (event.equals(MAKER_CONFIRM_INSERT_CLOSE))) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("MakerInsertCloseMutualFundsItemCommand") };
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
		DefaultLogger.debug(this, "Inside validate Input  class" + ((MutualFundsItemForm) aForm));

		return MutualFundsItemFormValidator.validateInput((MutualFundsItemForm) aForm, locale);
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
		/*Add by govind for file upload*/
		 else if ((resultMap.get("errorEveList") != null) && ((String) resultMap.get("errorEveList")).equals("errorEveList")) {
				forward = "maker_fileupload_mutualfundsItem_page";
			}
		 else if (event.equals("maker_event_upload_mutualfundsItem")) {
			 forward = "common_submit_page";
	    }
		 else if ((event != null) && event.equals("maker_prepare_upload_mutualfundsItem")) {
			 forward = "prepare_upload_mutualfundsItem_page";
			}else if ((event != null) && event.equals("maker_rejected_delete_read")) {
				forward = "maker_view_insert_todo_page";
			}else if ((event != null) && event.equals("checker_approve_insert")) {
				forward = "common_approve_page";
			}else if ((event != null) && event.equals("checker_reject_insert")) {
				forward = "common_reject_page";
			}else if (event.equals("checker_process_create_insert")) {
				forward = "checker_mutualfundsItem_insert_page";
			}else if ((event != null)
					&& (event.equals(MAKER_CONFIRM_INSERT_CLOSE))) {
				forward = "common_close_page";
			} else if ((event != null)
					&& (event.equals(MAKER_PREPARE_INSERT_CLOSE))) {
				forward = "maker_prepare_insert_close";
			}
		/*end file upload*/
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

	public static final String MAKER_PREPARE_UPLOAD_MUTUALFUNDSITEM = "maker_prepare_upload_mutualfundsItem";
	public static final String MAKER_EVENT_UPLOAD_MUTUALFUNDSITEM = "maker_event_upload_mutualfundsItem";
	public static final String CHECKER_APPROVE_INSERT = "checker_approve_insert";
	public static final String MAKER_REJECTED_DELETE_READ = "maker_rejected_delete_read";
	public static final String CHECKER_PROCESS_CREATE_INSERT = "checker_process_create_insert";
	public static final String CHECKER_REJECT_INSERT = "checker_reject_insert";
	public static final String MAKER_PREPARE_INSERT_CLOSE = "maker_prepare_insert_close";
	public static final String MAKER_CONFIRM_INSERT_CLOSE = "maker_confirm_insert_close";

}
