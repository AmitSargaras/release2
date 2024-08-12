/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/customer/CustomerProcessDetailsAction.java,v 1.12 2006/08/01 03:05:53 jzhai Exp $
 */

package com.integrosys.cms.ui.customer;

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
import com.integrosys.cms.ui.customer.DownloadImageCommand;
import com.integrosys.cms.ui.customer.ImageTagResultPrintCommand;
import com.integrosys.cms.ui.customer.ReadCAMImagesCmd;
import com.integrosys.cms.ui.customer.RetriveImageCommand;

/**
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2006/08/01 03:05:53 $ Tag: $Name: $
 */
public class CustomerProcessDetailsAction extends CommonAction {

	/**
	 * This method return an Array of Commad Objects responsible for a event
	 * 
	 * @ param event is of type String @ return Icommand Array
	 */
	private static final String REROUTEPAGE = "reroutepage";

	private static final String SUBSTITUTEPAGE = "substitutepage";

	private static final String REASSIGNPAGE = "reassignpage";

	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (EVENT_PREPARE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ProcessDetailsCustomerCommand();
		}
		if ("view_borrower_list".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new BorrowerListCommand();
		}
		if ("processcusdetails".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ProcessDetailsCustomerCommand();
		}
		if ("viewsecurities".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewSecuritiesCustomerCommand();
		}
		if ("viewLimitProfile".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new BorrowerListCommand();
			// new ProcessDetailsCustomerCommand();
		}

		if ("editLimitProfile".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new EditLimitProfileCommand();
			objArray[1] = new PrepareLimitProfileCommand();
		}
		if ("updateLimitProfile".equals(event)) {
			DefaultLogger.debug(this, "accepted command");
			objArray = new ICommand[1];
			objArray[0] = new UpdateLimitProfileCommand();
		}
		// using ChkLimitProfileCommand for unit testing
		if ("chkLimitProfileList".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ChkLimitProfileCommand();
		}
		if ("chkLimitProfileApRej".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerApproveRejectLimitProfileCommand();
		}
		if ("approve".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new AppChkLimitProfileCommand();
		}
		if ("reject".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RejectChkLimitProfileCommand();
		}
		// using ChkLimitProfileCommand for unit testing
		if ("chkLimitProfileListSub".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ChkLimitProfileCommand();
		}
		if ("resubmit".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new MakerSubmitLimitProfileCommand();
			objArray[1] = new MakerSubmitPrepareLimitProfileCommand();
		}
		if ("close".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new MakerSubmitLimitProfileCommand();
			objArray[1] = new MakerSubmitPrepareLimitProfileCommand();
		}
		if ("to_track".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new MakerSubmitLimitProfileCommand();
			objArray[1] = new MakerSubmitPrepareLimitProfileCommand();
		}
		if ("submit".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SubmitMkLimitProfileCommand();
		}
		if ("cancel".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CancelMkLimitProfileCommand();
		}

		if ("prepare_form".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareLimitProfileCommand();
		}
		if ("prepare_form1".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerSubmitPrepareLimitProfileCommand();
		}
		if ("listMainBorrower".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListMainBorrowerCommand();

		}
		//Start: Uma Khot: Phase 3 CR:Customer details(summary Detail with CAM)
		if ("view_cam_image".equals(event) || "view_edit_cam_image".equals(event)) {
			// go to add
			objArray = new ICommand[1];
			objArray[0] = new ReadCAMImagesCmd();
		}
		if ("print_image".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ImageTagResultPrintCommand();
		} 
		if (event.equals("retrieveImageGallaryCAMNote")) {
			objArray = new ICommand[1];
			objArray[0] = new RetriveImageCommand();
		}
		if (event.equals("downloadImage")) {
			objArray = new ICommand[1];
			objArray[0] = new DownloadImageCommand();
		}
		//End: Uma Khot: Phase 3 CR:Customer details(summary Detail with CAM)
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
		return ProcessDetailsCustomerFormValidator.validateInput((ProcessDetailsCustomerForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if ((event.equals("updateLimitProfile")) || (event.equals("submit"))) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("updateLimitProfile".equals(event)) {
			errorEvent = "prepare_form";
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
		if (("view_borrower_list").equals(event)) {
			forwardName = "view_borrower_list";
		}
		if (("processcusdetails").equals(event)) {
			forwardName = "after_viewLimitProfile";
		}
		if (("viewsecurities").equals(event)) {
			forwardName = "after_viewsecurities";
		}
		if (("viewLimitProfile").equals(event)) {
			forwardName = "view_borrower_list";
			// "after_viewLimitProfile";
		}
		if (("editLimitProfile").equals(event) || "prepare_form".equals(event)) {
			forwardName = "after_editLimitProfile";
		}
		if (("updateLimitProfile").equals(event)) {
			forwardName = "after_updateLimitProfile";
		}
		if (("chkLimitProfileApRej").equals(event)) {
			forwardName = "after_chkLimitProfileApRej";
		}
		if (("chkLimitProfileList").equals(event)) {
			forwardName = "after_chkLimitProfileList";
		}
		if (("chkLimitProfileListSub").equals(event)) {
			forwardName = "after_chkLimitProfileListSub";
		}
		if (("resubmit").equals(event) || "prepare_form1".equals(event)) {
			forwardName = "after_resubmit";
		}
		if (("close").equals(event)) {
			forwardName = "after_close";
		}
		if (("to_track").equals(event)) {
			forwardName = "after_to_track";
		}
		if (("submit").equals(event)) {
			forwardName = "after_submit";
		}
		if (("cancel").equals(event)) {
			forwardName = "after_cancel";
		}

		if (("approve").equals(event)) {
			forwardName = "after_approve";
		}
		if (("reject").equals(event)) {
			forwardName = "after_reject";
		}

		if ("listMainBorrower".equals(event)) {
			forwardName = "listMainBorrower";
		}
		if (REROUTEPAGE.equals(event) || REASSIGNPAGE.equals(event) || SUBSTITUTEPAGE.equals(event)) {
			forwardName = event;
		}
		//Start: Uma Khot: Phase 3 CR:Customer details(summary Detail with CAM)

		if("view_cam_image".equals(event) || "view_edit_cam_image".equals(event)){
			forwardName = "view_cam_image_list";
		}
		if ("print_image".equals(event)) {
			forwardName = "print_image";
		}
		if ("retrieveImageGallaryCAMNote".equals(event) ) {
			forwardName = "image_gallary";
		}
		 if ("downloadImage".equals(event)) {
				forwardName = "downloadImage";
			}
		//End: Uma Khot: Phase 3 CR:Customer details(summary Detail with CAM)
		return forwardName;
	}
}
