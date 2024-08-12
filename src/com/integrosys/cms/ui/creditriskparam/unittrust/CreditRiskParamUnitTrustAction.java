/**
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 */
package com.integrosys.cms.ui.creditriskparam.unittrust;

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
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.trx.ICreditRiskParamGroupTrxValue;

/**
 * CreditRiskParamUnitTrustAction
 * 
 * Describe this class. Purpose: for Credit Risk Parameter - Unit Trust
 * Description: Action class for Credit Risk Parameter - Unit Trust
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class CreditRiskParamUnitTrustAction extends CommonAction implements IPin {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {

		DefaultLogger.debug(this, "*******" + event + "================");

		// if (EVENT_READ.equals(event)) {
		// return new ICommand[]{new ReadUnitTrustListCommand()};
		// //return new ICommand[]{new
		// ReadActualToStagingUnitTrustListCommand()};
		// } else
		if (EVENT_PREPARE.equals(event)) {
			return new ICommand[] { new ReadCreditRiskParamUnitTrustCommand() };
		}
		// else if (EVENT_READ_NOOP.equals(event)) {
		// return null;
		// }
		else if (EVENT_READ_MAKER_EDIT.equals(event)) {
			return new ICommand[] { new ReadCreditRiskParamUnitTrustCommand() };
		}
		// else if (EVENT_ADD.equals(event)) {
		// return new ICommand[]{new AddUnitTrustListCommand()};
		// } else if (EVENT_ADD_NOOP.equals(event)) {
		// return null;
		// } else if (EVENT_REMOVE.equals(event)) {
		// return new ICommand[]{new DeleteUnitTrustListCommand()};
		// } else if (EVENT_REMOVE_NOOP.equals(event)) {
		// return null;
		// } else if (EVENT_SAVE.equals(event)) {
		// return new ICommand[]{new SaveUnitTrustListCommand()};
		// }
		else if (EVENT_PAGINATE.equals(event)) {
			return new ICommand[] { new PaginateCreditRiskParamUnitTrustCommand() };
		}
		else if (EVENT_SAVE_NOOP.equals(event)) {
			return null;
		}
		else if (EVENT_SUBMIT.equals(event)) {
			return new ICommand[] { new SubmitCreditRiskParamUnitTrustCommand() };
		}
		else if (EVENT_SUBMIT_NOOP.equals(event)) {
			return null;
		}
		else if (EVENT_READ_CHECKER_APPROVE_REJECT.equals(event)) {
			return new ICommand[] { new ReadCreditRiskParamUnitTrustCommand(),
					new CompareCreditRiskParamUnitTrustCommand() };
		}
		else if (EVENT_READ_MAKER_CLOSE.equals(event)) {
			return new ICommand[] { new ReadCreditRiskParamUnitTrustCommand() };
		}
		else if (EVENT_APPROVE.equals(event)) {
			return new ICommand[] { new ApproveCreditRiskParamUnitTrustCommand() };
		}
		else if (EVENT_REJECT.equals(event)) {
			return new ICommand[] { new RejectCreditRiskParamUnitTrustCommand() };
		}
		else if (EVENT_LIST_CHECKER_APPROVE_REJECT.equals(event)) {
			return new ICommand[] { new ListCompareCreditRiskParamUnitTrustCommand() };
		}
		else if (EVENT_LIST_MAKER_CLOSE.equals(event)) {
			return new ICommand[] { new ListViewCreditRiskParamUnitTrustCommand() };
		}
		else if (EVENT_CLOSE.equals(event)) {
			return new ICommand[] { new CloseCreditRiskParamUnitTrustCommand() };
		}
		else if (EVENT_SELECT.equals(event)) {
			return null;
		}
		else if (EVENT_SELECT_MAKER_EDIT.equals(event)) {
			return new ICommand[] { new PrepareCreditRiskParamUnitTrustCommand() };
		}
		// else if (EVENT_LIST_STAGING.equals(event)) {
		// return new ICommand[]{new ListUnitTrustListCommand()};
		// }
		else if (EVENT_TO_TRACK.equals(event)) {
			return new ICommand[] { new ReadCreditRiskParamUnitTrustCommand() };
		}
		else if (EVENT_LIST_TO_TRACK.equals(event)) {
			return new ICommand[] { new ListViewCreditRiskParamUnitTrustCommand() };
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
		DefaultLogger.debug(this, "Inside validate Input  class" + ((CreditRiskParamUnitTrustForm) aForm));

		return CreditRiskParamUnitTrustFormValidator.validateInput((CreditRiskParamUnitTrustForm) aForm, locale);
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

		ICreditRiskParamGroupTrxValue value = (ICreditRiskParamGroupTrxValue) resultMap
				.get("creditRiskParamGroupTrxValue");

		boolean isWip = false;

		if (value != null) {
			String status = value.getStatus();
			isWip = status.equals(ICMSConstant.STATE_PENDING_UPDATE) || status.equals(ICMSConstant.STATE_REJECTED);
		}

		if (EVENT_PREPARE.equals(event)) {
			if (isWip) {
				forward = "workInProgress";
			}
			else {
				forward = "list";
			}
			// } else if (EVENT_READ.equals(event)) {
			// forward = "view";
			// } else if (EVENT_READ_NOOP.equals(event)) {
			// forward = "select";
		}
		else if (EVENT_READ_MAKER_EDIT.equals(event)) {
			forward = "list";
			// } else if (EVENT_ADD.equals(event)) {
			// forward = "add";
			// } else if (EVENT_ADD_NOOP.equals(event)) {
			// forward = "list";
			// } else if (EVENT_REMOVE.equals(event)) {
			// forward = "list";
			// } else if (EVENT_REMOVE_NOOP.equals(event)) {
			// forward = "list";
			// } else if (EVENT_SAVE.equals(event)) {
			// forward = "save";
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
		else if (EVENT_SELECT_MAKER_EDIT.equals(event)) {
			forward = "select";
			// } else if (EVENT_LIST_STAGING.equals(event)) {
			// forward = "list";
		}
		else if (EVENT_TO_TRACK.equals(event) || EVENT_LIST_TO_TRACK.equals(event)) {
			forward = "view";
		}

		DefaultLogger.debug(this, "The name of struts forward is " + forward);

		Page page = new Page();
		page.setPageReference(forward);

		return page;
	}

	protected boolean isValidationRequired(String event) {
		return EVENT_PAGINATE.equals(event) || EVENT_SUBMIT.equals(event) || EVENT_APPROVE.equals(event)
				|| EVENT_REJECT.equals(event) || EVENT_CLOSE.equals(event);
	}

	protected String getErrorEvent(String event) {

		if (EVENT_PAGINATE.equals(event)) {
			return EVENT_SAVE_NOOP;
		}
		else if (EVENT_SUBMIT.equals(event)) {
			return EVENT_SUBMIT_NOOP;
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

		return null;
	}

	protected String getDefaultEvent() {
		return EVENT_SELECT;
	}

	/**
	 * For reading items.
	 */
	// public static final String EVENT_READ = "read";
	// public static final String EVENT_READ_NOOP = "readNoop";
	public static final String EVENT_READ_MAKER_EDIT = "readMakerEdit";

	// public static final String EVENT_LIST_READ = "listRead";

	/**
	 * For adding new record.
	 */
	// public static final String EVENT_ADD = "add";
	// public static final String EVENT_ADD_NOOP = "addNoop";
	/**
	 * For removing checked items.
	 */
	// public static final String EVENT_REMOVE = "remove";
	/**
	 * Just to go back to list page without reexecuting anything.
	 */
	// public static final String EVENT_REMOVE_NOOP = "removeNoop";
	/**
	 * For saving and then going to notification page.
	 */
	// public static final String EVENT_SAVE = "save";
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
	 * For selecting the unit trust exchange.
	 */
	public static final String EVENT_SELECT = "select";

	public static final String EVENT_SELECT_MAKER_EDIT = "selectMakerEdit";

	// public static final String EVENT_LIST_STAGING = "listStaging";

	/**
	 * For totrack view.
	 */
	public static final String EVENT_TO_TRACK = "to_track";

	/**
	 * For totrack view pagination.
	 */
	public static final String EVENT_LIST_TO_TRACK = "list_to_track";
}