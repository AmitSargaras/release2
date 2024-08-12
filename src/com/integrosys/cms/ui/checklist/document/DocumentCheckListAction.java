/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/checklist/document/DocumentCheckListAction.java,v 1.4 2005/10/14 13:26:44 lyng Exp $
 */
package com.integrosys.cms.ui.checklist.document;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.checklist.camreceipt.DownloadImageCommand;
import com.integrosys.cms.ui.checklist.camreceipt.ImageTagResultPrintCommand;
import com.integrosys.cms.ui.checklist.document.RetriveImageCommand;

/**
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/10/14 13:26:44 $ Tag: $Name: $
 */
public class DocumentCheckListAction extends CommonAction {

	public static final String EVENT_PRINT_LIST_ALL = "print_list_all";
	
	public static final String EVENT_PRINT_LETTER_LIST = "print_letter_list";

	public static final String EVENT_LIST_MAIN_BORROWER = "main_borrower_list";

	public static final String EVENT_PRINT_LIST_MAIN_BORROWER = "print_main_borrower_list";

	public static final String EVENT_LIST_NON_BORROWER = "non_borrower_list";

	public static final String EVENT_PRINT_LIST_NON_BORROWER = "print_non_borrower_list";

	public static final String EVENT_LIST_CO_BORROWER = "co_borrower_list";

	public static final String EVENT_PRINT_LIST_CO_BORROWER = "print_co_borrower_list";

	public static final String EVENT_LIST_PLEDGOR = "pledgor_list";

	public static final String EVENT_PRINT_LIST_PLEDGOR = "print_pledgor_list";

	public static final String EVENT_LIST_SECURITIES = "securities_list";

	public static final String EVENT_PRINT_LIST_SECURITIES = "print_securities_list";

	public static final String EVENT_LIST_ALL_REQ_DOCS = "print_req_await_doc";
	
	public static final String SEARCH_CHECKLIST = "search_checklist";
	
	public static final String VIEW_CHECKLIST = "view_checklist";
	
	public static final String RETURN_VIEW = "return_view";
	
	public static final String VIEW_CHECKLIST_IMAGE = "view_checklist_image";
	
	public static final String PRINT_LIST_SEARCH = "print_list_search";
	
	public static final String EVENT_RETRIEVE_INDVDL_IMAGE_CHK = "retrieveIndividualImageCHKDoc";
	
	
	public static final String EVENT_LIST_REMINDER_LETTER_DOCS = "print_reminder_letter_doc";

	/**
	 * This method return a Array of Command Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return ICommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, "event: " + event);
		ICommand objArray[] = null;
		if (EVENT_LIST.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ListDocumentCategoriesCommand();
			objArray[1] = new ListAllDocumentsCommand();
		}
		else if(SEARCH_CHECKLIST.equals(event)||RETURN_VIEW.equals(event)||PRINT_LIST_SEARCH.equals(event)){
			objArray = new ICommand[1];
			objArray[0] = new SearchCompleteChecklist();
			
		}else if(VIEW_CHECKLIST.equals(event)){
			objArray = new ICommand[1];
			objArray[0] = new ViewCompleteChecklistDoc();
		}else if(VIEW_CHECKLIST_IMAGE.equals(event)){
			objArray = new ICommand[1];
			objArray[0] = new ViewImageCheckListCmd();
		}
		else if (EVENT_LIST_MAIN_BORROWER.equals(event) || EVENT_PRINT_LIST_MAIN_BORROWER.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListMainBorrowerDocsCommand();
		}
		else if (EVENT_LIST_NON_BORROWER.equals(event) || EVENT_PRINT_LIST_NON_BORROWER.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListNonBorrowerDocsCommand();
		}
		else if (EVENT_LIST_CO_BORROWER.equals(event) || EVENT_PRINT_LIST_CO_BORROWER.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListCoBorrowerDocsCommand();
		}
		else if (EVENT_LIST_PLEDGOR.equals(event) || EVENT_PRINT_LIST_PLEDGOR.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListPledgorDocsCommand();
		}
		else if (EVENT_LIST_SECURITIES.equals(event) || EVENT_PRINT_LIST_SECURITIES.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListSecurityDocsCommand();
		}
		else if (EVENT_PRINT_LIST_ALL.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ListDocumentCategoriesCommand();
			objArray[1] = new ListAllDocumentsCommand();
		}else if ("print_image".equals(event) || "view_image".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ImageTagResultPrintCommand();
		}else if (event.equals("downloadImage")) {
			objArray = new ICommand[1];
			objArray[0] = new DownloadImageCommand();
		}
		else if (EVENT_LIST_ALL_REQ_DOCS.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListAllReqDocumentsCommand();
		}
		else if (EVENT_PRINT_LETTER_LIST.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListReminderLetterCommand();
		}
		else if (EVENT_LIST_REMINDER_LETTER_DOCS.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListAllReqDocumentsCommand();
		}
		else if (event.equals("retrieveImageGallaryChecklistDoc")||EVENT_RETRIEVE_INDVDL_IMAGE_CHK.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RetriveImageCommand();
		}
		DefaultLogger.debug(this, "event============="+event);
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
		return null;
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		return result;
	}

	/**
	 * This method is used to determine the page to be displayed next based on
	 * the event. Result hashmap and exception hashmap. It returns the page
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
		String errorEvent = getDefaultEvent();
		return errorEvent;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = null;
		if (EVENT_LIST.equals(event)|| SEARCH_CHECKLIST.equals(event)||RETURN_VIEW.equals(event)) {
			forwardName = "after_list";
		}
		else if(VIEW_CHECKLIST.equals(event)){
			forwardName = "view_checklist";
		}
		else if(VIEW_CHECKLIST_IMAGE.equals(event)){
			forwardName = "view_checklist_image";
		}
		else if (EVENT_LIST_MAIN_BORROWER.equals(event)) {
			forwardName = "after_main_borrower_list";
		}
		else if (EVENT_LIST_NON_BORROWER.equals(event)) {
			forwardName = "after_non_borrower_list";
		}
		else if (EVENT_LIST_CO_BORROWER.equals(event)) {
			forwardName = "after_co_borrower_list";
		}
		else if (EVENT_LIST_PLEDGOR.equals(event)) {
			forwardName = "after_pledgor_list";
		}
		else if (EVENT_LIST_SECURITIES.equals(event)) {
			forwardName = "after_securities_list";
		}
		else if (EVENT_PRINT_LIST_MAIN_BORROWER.equals(event)) {
			forwardName = "after_print_main_borrower_list";
		}
		else if (EVENT_PRINT_LIST_NON_BORROWER.equals(event)) {
			forwardName = "after_print_non_borrower_list";
		}
		else if (EVENT_PRINT_LIST_CO_BORROWER.equals(event)) {
			forwardName = "after_print_co_borrower_list";
		}
		else if (EVENT_PRINT_LIST_PLEDGOR.equals(event)) {
			forwardName = "after_print_pledgor_list";
		}
		else if (EVENT_PRINT_LIST_SECURITIES.equals(event)) {
			forwardName = "after_print_securities_list";
		}
		else if (EVENT_PRINT_LIST_ALL.equals(event)||PRINT_LIST_SEARCH.equals(event)) {
			forwardName = "after_print_list_all";
		}
		else if (EVENT_LIST_ALL_REQ_DOCS.equals(event)) {
			forwardName = "after_print_list_all_req";
		}
		else if (EVENT_PRINT_LETTER_LIST.equals(event)) {
			forwardName = "after_print_letter_list";
		}else if (EVENT_RETRIEVE_INDVDL_IMAGE_CHK.equals(event)) {
			forwardName = "imagePath";
		}
		else if (EVENT_LIST_REMINDER_LETTER_DOCS.equals(event)) {
			forwardName = "after_print_letter_list_doc";
		}else if ("print_image".equals(event)) {
			forwardName = "print_image";
		}else if ("downloadImage".equals(event)) {
			forwardName = "downloadImage";
		}else if ("retrieveImageGallaryChecklistDoc".equals(event) ) {
			forwardName = "image_gallary";
		}
		return forwardName;
	}
}