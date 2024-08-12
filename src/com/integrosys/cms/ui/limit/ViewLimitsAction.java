/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/ViewLimitsAction.java,v 1.17 2003/09/03 08:20:23 pooja Exp $
 */

package com.integrosys.cms.ui.limit;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

/**
 * @author $Author: pooja $<br>
 * @version $Revision: 1.17 $
 * @since $Date: 2003/09/03 08:20:23 $ Tag: $Name: $
 */
public class ViewLimitsAction extends CommonAction {

	/**
	 * This method return an Array of Commad Objects responsible for an event
	 * 
	 * @ param event is of type String @ return Icommand Array
	 */
	private static final String REROUTEPAGE = "reroutepage";

	private static final String SUBSTITUTEPAGE = "substitutepage";

	private static final String REASSIGNPAGE = "reassignpage";

	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;

		if ("view".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewLimitsCommand();
		}
		if ("to_track".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new MakerSubmitLimitCommand();
			objArray[1] = new MakerSubmitSystemXrefCommand();
		}
		if ("close".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new MakerSubmitLimitCommand();
			objArray[1] = new MakerSubmitSystemXrefCommand();
		}
		if ("view_coborrower".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewCoBorrowerCommand();
		}
		if ("to_track_coborrower".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewCoBorrowerCommand();
		}
		if ("edit".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new EditLimitsCommand();
			objArray[1] = new SystemXRefCommand();
		}
		if ("edit_coborrower".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewCoBorrowerCommand();
		}
		if ("close_coborrower".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewCoBorrowerCommand();
		}
		if ("save".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateLimitsCommand();
		}
		if ("save_coborrower".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateCoBorrowerCommand();
		}
		// using ChkLimitsCommand for unit testing
		if ("chkLimitList".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ChkLimitsCommand();
		}
		// using ChkLimitsCommand for unit testing
		if ("chkLimitListSub".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ChkLimitsCommand();
		}

		if ("chkLimitApRej".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerApproveRejectLimitCommand();
		}
		if ("chk_coborrower_approve_reject".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewCoBorrowerCommand();
		}
		if ("resubmit".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new MakerSubmitLimitCommand();
			objArray[1] = new MakerSubmitSystemXrefCommand();
		}
		if ("resubmit_coborrower".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewCoBorrowerCommand();
		}
		if ("submit".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SubmitMkLimitCommand();
		}

		if ("approve".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new AppChkLimitCommand();
		}
		if ("approve_coborrower".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ApproveChkCoBorrowerCommand();
		}
		if ("reject".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RejectChkLimitCommand();
		}
		if ("reject_coborrower".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RejectChkCoBorrowerCommand();
		}
		if ("cancel".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CancelMkLimitCommand();
		}
		if ("cancel_coborrower".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CancelMkCoBorrowerCommand();
		}
		// using ChkLimitsCommand for unit testing
		if ("limitprofile".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ChkLimitsCommand();
		}
		if ("prepare_form".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SystemXRefCommand();
		}
		if ("prepare_form1".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerSubmitSystemXrefCommand();
		}

		return (objArray);
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
		DefaultLogger.debug(this, "Inside validate Input child class");
		return ViewLimitsFormValidator.validateInput((ViewLimitsForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if ((event.equals("save")) || (event.equals("submit")) || (event.equals("save_coborrower"))) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("save".equals(event)) {
			errorEvent = "prepare_form";
		}
		if ("save_coborrower".equals(event)) {
			errorEvent = "prepare_form2";
		}
		if ("submit".equals(event)) {
			errorEvent = "prepare_form1";
		}
		return errorEvent;
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
		if ((resultMap.get("wip") != null) && (resultMap.get("wip")).equals("passive")) {
			DefaultLogger.debug(this, "Inside wip");
			aPage.setPageReference("wip");
			return aPage;
		}
		aPage.setPageReference(getReference(event));
		return aPage;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = "submit_fail";
		if (("view").equals(event)) {
			forwardName = "after_view";
		}
		if (("to_track").equals(event)) {
			forwardName = "after_to_track";
		}
		if (("view_coborrower").equals(event)) {
			forwardName = "after_view_coborrower";
		}
		if (("to_track_coborrower").equals(event)) {
			forwardName = "after_to_track_coborrower";
		}
		if (("close").equals(event)) {
			forwardName = "after_close";
		}

		if (("edit").equals(event) || "prepare_form".equals(event)) {
			forwardName = "after_edit";
		}
		if (("edit_coborrower").equals(event) || "prepare_form2".equals(event)) {
			forwardName = "after_edit_coborrower";
		}
		if (("close_coborrower").equals(event)) {
			forwardName = "after_close_coborrower";
		}
		if (("save").equals(event)) {
			forwardName = "after_save";
		}
		if (("save_coborrower").equals(event)) {
			forwardName = "after_save_coborrower";
		}

		if (("chkLimitList").equals(event)) {
			forwardName = "after_chkLimitList";
		}
		if (("chkLimitListSub").equals(event)) {
			forwardName = "after_chkLimitListSub";
		}
		if (("chkLimitApRej").equals(event)) {
			forwardName = "after_chkLimitApRej";
		}
		if (("chk_coborrower_approve_reject").equals(event)) {
			forwardName = "after_chk_coborrower_approve_reject";
		}

		if (("resubmit").equals(event) || "prepare_form1".equals(event)) {
			forwardName = "after_resubmit";
		}
		if (("resubmit_coborrower").equals(event)) {
			forwardName = "after_resubmit_coborrower";
		}

		if (("submit").equals(event)) {
			forwardName = "after_submit";
		}

		if (("approve").equals(event)) {
			forwardName = "after_approve";
		}
		if (("approve_coborrower").equals(event)) {
			forwardName = "after_approve_coborrower";
		}
		if (("reject").equals(event)) {
			forwardName = "after_reject";
		}
		if (("reject_coborrower").equals(event)) {
			forwardName = "after_reject_coborrower";
		}
		if (("cancel").equals(event)) {
			forwardName = "after_cancel";
		}
		if (("cancel_coborrower").equals(event)) {
			forwardName = "after_cancel_coborrower";
		}
		if (("limitprofile").equals(event)) {
			forwardName = "after_limitprofile";
		}
		if (REROUTEPAGE.equals(event) || REASSIGNPAGE.equals(event) || SUBSTITUTEPAGE.equals(event)) {
			forwardName = event;
		}

		return forwardName;
	}
}
