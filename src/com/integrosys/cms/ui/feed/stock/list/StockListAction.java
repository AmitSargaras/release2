package com.integrosys.cms.ui.feed.stock.list;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.stock.StockAction;

/**
 * This action implements ...
 */
public class StockListAction extends StockAction {

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

		if (EVENT_READ.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadStockListCommand") };
		}
		else if (EVENT_READ_NOOP.equals(event)) {
			return null;
		}
		else if (EVENT_READ_MAKER_EDIT.equals(event) || EVENT_PREPARE.equals(event) || EVENT_CHECKER_PREPARE.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadStockListCommand") };
		}
		else if (EVENT_ADD.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("AddStockListCommand") };
		}
		else if (EVENT_ADD_NOOP.equals(event)) {
			return null;
		}
		else if (EVENT_REMOVE.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("DeleteStockListCommand") };
		}
		else if (EVENT_REMOVE_NOOP.equals(event)) {
			return null;
		}
		else if (EVENT_SAVE.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("SaveStockListCommand") };
		}
		else if (EVENT_PAGINATE.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("PaginateStockListCommand") };
		}
		else if (EVENT_SAVE_NOOP.equals(event)) {
			return null;
		}
		else if (EVENT_SUBMIT.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("SubmitStockListCommand") };
		}
		else if (EVENT_SUBMIT_NOOP.equals(event)) {
			return null;
		}
		else if (EVENT_READ_CHECKER_APPROVE_REJECT.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadStockListCommand"),
					(ICommand) getNameCommandMap().get("CompareStockListCommand") };
		}
		else if (EVENT_READ_MAKER_CLOSE.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadStockListCommand") };
		}
		else if (EVENT_APPROVE.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ApproveStockListCommand"),
					(ICommand) getNameCommandMap().get("ReloadMarketableSecValuationProfileCommand") };
		}
		else if (EVENT_REJECT.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("RejectStockListCommand") };
		}
		else if (EVENT_LIST_CHECKER_APPROVE_REJECT.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ListCompareStockListCommand") };
		}
		else if (EVENT_LIST_MAKER_CLOSE.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ListViewStockListCommand") };
		}
		else if (EVENT_CLOSE.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("CloseStockListCommand") };
		}
		else if (EVENT_SELECT.equals(event)) {
			return null;
		}
		else if (EVENT_SELECT_MAKER_EDIT.equals(event) || EVENT_SELECT_CHECKER_EDIT.equals(event)) {
			return null;
		}
		else if (EVENT_LIST_STAGING.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ListStockListCommand") };
		}
		else if (EVENT_VIEW.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadStockListCommand") };
		}
		else if (EVENT_LIST_VIEW.equals(event) || EVENT_LIST_READ.equals(event) || EVENT_CHECKER_SELECTMAKEREDIT.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ListViewStockListCommand") };
		}
		
	
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
		DefaultLogger.debug(this, "Inside validate Input  class" + ((StockListForm) aForm));
		
		return StockListFormValidator.validateInput((StockListForm) aForm, locale);
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

		// String must be one of the struts local forwards.
		String forward = null;

		IStockFeedGroupTrxValue value = (IStockFeedGroupTrxValue) resultMap.get("stockFeedGroupTrxValue");
		
		boolean isWip = false;

		if (value != null) {
			String status = value.getStatus();
			isWip = (!(EVENT_READ.equals(event) || EVENT_LIST_READ.equals(event)))
					&& (status.equals(ICMSConstant.STATE_DRAFT) || status.equals(ICMSConstant.STATE_PENDING_UPDATE) || status
							.equals(ICMSConstant.STATE_REJECTED));
		}
		/*
		 * if (EVENT_READ.equals(event)) { if (isWip) { forward =
		 * "workInProgress"; } else { forward = "list"; } } else if
		 * (EVENT_READ_NOOP.equals(event)) { forward = "select"; } else if
		 * (EVENT_READ_MAKER_EDIT.equals(event)) { forward = "list";
		 */
		if (EVENT_PREPARE.equals(event) || EVENT_CHECKER_PREPARE.equals(event)) {
			if (isWip) {
				forward = "workInProgress";
			}
			else {
				forward = "list";
			}
		}
		else if (EVENT_READ_MAKER_EDIT.equals(event)) {
			forward = "list";
		}
		else if (EVENT_READ.equals(event)) {
			forward = "view";
		}
		else if (EVENT_READ_NOOP.equals(event)) {
			forward = "select";
		}
		else if (EVENT_ADD.equals(event)) {
			forward = "add";
		}
		else if (EVENT_ADD_NOOP.equals(event)) {
			forward = "list";
		}
		else if (EVENT_REMOVE.equals(event)) {
			forward = "list";
		}
		else if (EVENT_REMOVE_NOOP.equals(event)) {
			forward = "list";
		}
		else if (EVENT_SAVE.equals(event)) {
			forward = "save";
		}
		else if (EVENT_PAGINATE.equals(event)) {
			forward = "list";
		}
		else if (EVENT_SAVE_NOOP.equals(event)) {
			forward = "list";
		}
		else if (EVENT_SUBMIT.equals(event)) {
			forward = "submit";
		}
		else if (EVENT_SUBMIT_NOOP.equals(event)) {
			forward = "list";
		}
		else if (EVENT_READ_CHECKER_APPROVE_REJECT.equals(event)) {
			forward = "list2ForChecker";
		}
		else if (EVENT_READ_MAKER_CLOSE.equals(event)) {
			forward = "view";
		}
		else if (EVENT_APPROVE.equals(event)) {
			forward = "approve";
		}
		else if (EVENT_REJECT.equals(event)) {
			forward = "reject";
		}
		else if (EVENT_LIST_CHECKER_APPROVE_REJECT.equals(event)) {
			forward = "list2ForChecker";
		}
		else if (EVENT_LIST_MAKER_CLOSE.equals(event)) {
			forward = "view";
		}
		else if (EVENT_CLOSE.equals(event)) {
			forward = "close";
		}
		else if (EVENT_SELECT.equals(event)) {
			forward = "select";
		}

		else if (EVENT_SELECT_MAKER_EDIT.equals(event) || EVENT_SELECT_CHECKER_EDIT.equals(event)) {
			forward = "select";
		}
		else if (EVENT_LIST_STAGING.equals(event)) {
			forward = "list";
		}
		else if (EVENT_VIEW.equals(event) || EVENT_LIST_VIEW.equals(event) 
				|| EVENT_LIST_READ.equals(event) || EVENT_CHECKER_SELECTMAKEREDIT.equals(event)) {
			forward = "view";
		}
	
	
		
		else if(EVENT_SELECT_MARKETABLE_INSTRUMENT.equals(event)){
			forward = EVENT_SELECT_MARKETABLE_INSTRUMENT;
		}

		else if(EVENT_CHECKER_SELECT_MARKETABLE_INSTRUMENT.equals(event)){
			forward = EVENT_CHECKER_SELECT_MARKETABLE_INSTRUMENT;
		}
		
		DefaultLogger.debug(this, "The name of struts forward is " + forward);

		Page page = new Page();
		page.setPageReference(forward);

		return page;
	}

	protected boolean isValidationRequired(String event) {
		return EVENT_ADD.equals(event) || EVENT_REMOVE.equals(event) || EVENT_SAVE.equals(event)
				|| EVENT_PAGINATE.equals(event) || EVENT_SUBMIT.equals(event) || EVENT_APPROVE.equals(event)
				|| EVENT_REJECT.equals(event) | EVENT_CLOSE.equals(event) || EVENT_PREPARE.equals(event)
				|| EVENT_CHECKER_PREPARE.equals(event);
	}

	protected String getErrorEvent(String event) {

		if (EVENT_ADD.equals(event)) {
			return EVENT_ADD_NOOP;
		}
		else if (EVENT_REMOVE.equals(event)) {
			return EVENT_REMOVE_NOOP;
		}
		else if (EVENT_SAVE.equals(event) || EVENT_PAGINATE.equals(event)) {
			return EVENT_SAVE_NOOP;
		}
		else if (EVENT_SUBMIT.equals(event)) {
			return EVENT_SUBMIT_NOOP;
		}
		else if (EVENT_READ.equals(event)) {
			return EVENT_READ_NOOP;
		}
		else if (EVENT_APPROVE.equals(event)) {
			return EVENT_LIST_CHECKER_APPROVE_REJECT;
		}
		else if (EVENT_REJECT.equals(event)) {
			return EVENT_LIST_CHECKER_APPROVE_REJECT;
		}
		else if (EVENT_CLOSE.equals(event)) {
			return EVENT_LIST_MAKER_CLOSE;
		}
		else if (EVENT_PREPARE.equals(event)) {
			return EVENT_SELECT_MAKER_EDIT;
		}
		else if (EVENT_CHECKER_PREPARE.equals(event)) {
			return EVENT_SELECT_CHECKER_EDIT;
		} 

		return null;
	}

	protected String getDefaultEvent() {
		return EVENT_SELECT;
	}

	/**
	 * For reading items.
	 */
	public static final String EVENT_READ = "read";

	public static final String EVENT_READ_NOOP = "readNoop";

	public static final String EVENT_LIST_READ = "listRead";

	/**
	 * For reading staging items in draft to draft.
	 */
	public static final String EVENT_READ_MAKER_EDIT = "readMakerEdit";

	/**
	 * For adding new record.
	 */
	public static final String EVENT_ADD = "add";

	public static final String EVENT_ADD_NOOP = "addNoop";

	/**
	 * For removing checked items.
	 */
	public static final String EVENT_REMOVE = "remove";

	/**
	 * Just to go back to list page without reexecuting anything.
	 */
	public static final String EVENT_REMOVE_NOOP = "removeNoop";

	/**
	 * For saving and then going to notification page.
	 */
	public static final String EVENT_SAVE = "save";

	/**
	 * For saving and then going to list page.
	 */
	public static final String EVENT_PAGINATE = "paginate";

	/**
	 * Just to go back to list page.
	 */
	public static final String EVENT_SAVE_NOOP = "saveNoop";

	/**
	 * For submitting.
	 */
	public static final String EVENT_SUBMIT = "submit";

	/**
	 * Just to go back to list page.
	 */
	public static final String EVENT_SUBMIT_NOOP = "submitNoop";

	/**
     *
     */
	public static final String EVENT_READ_CHECKER_APPROVE_REJECT = "readCheckerApproveReject";

	public static final String EVENT_READ_MAKER_CLOSE = "readMakerClose";

	/**
	 * For checker approving.
	 */
	public static final String EVENT_APPROVE = "approve";

	/**
	 * For checker rejecting.
	 */
	public static final String EVENT_REJECT = "reject";

	/**
	 * For listing for checker.
	 */
	public static final String EVENT_LIST_CHECKER_APPROVE_REJECT = "listCheckerApproveReject";

	public static final String EVENT_LIST_MAKER_CLOSE = "listMakerClose";

	/**
	 * For maker closing.
	 */
	public static final String EVENT_CLOSE = "close";

	/**
	 * For selecting the stock exchange.
	 */
	public static final String EVENT_SELECT = "select";

	/**
     *
     */
	public static final String EVENT_SELECT_MAKER_EDIT = "selectMakerEdit";

	public static final String EVENT_LIST_STAGING = "listStaging";

	/**
	 * For totrack view.
	 */
	public static final String EVENT_VIEW = "view";

	/**
	 * For totrack view pagination.
	 */
	public static final String EVENT_LIST_VIEW = "listView";
	
	public static final String EVENT_SELECT_MARKETABLE_INSTRUMENT = "selectMarketableInstrument";

	public static final String EVENT_CHECKER_SELECTMAKEREDIT = "checker_selectMakerEdit";
	
	public static final String EVENT_CHECKER_SELECT_MARKETABLE_INSTRUMENT = "checkerSelectMarketableInstrument";
	
	public static final String EVENT_SELECT_CHECKER_EDIT = "selectCheckerEdit";
	
	public static final String EVENT_CHECKER_PREPARE = "checker_prepare";
	
}
