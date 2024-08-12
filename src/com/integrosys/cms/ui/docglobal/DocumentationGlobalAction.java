package com.integrosys.cms.ui.docglobal;

import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.checklist.recurrent.PrepareRecurrentCommand;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import java.util.Locale;
import java.util.HashMap;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/23 07:30:45 $ Tag: $Name: $
 */
public class DocumentationGlobalAction extends CommonAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if ("global_list".equals(event)||"paginate".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListGlobalCheckListCommand();
		}
		else if ("view_doc_item".equals(event) || "edit_doc_item".equals(event)|| "prepare_delete_doc_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadDocumentItemCommand();
		}else if ("checker_global_list".equals(event) || "edit_doc_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadDocumentItemCommand();
		}		
		else if ("update_doc_item".equals(event)||"maker_save_update".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateDocumentItemCommand();
		}
		else if ("create_doc_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CreateDocumentItemCommand();
		}
		else if ("process".equals(event) || "edit_staging_doc_item".equals(event)|| "maker_update_save_process".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadStagingDocumentItemCommand();
		}
		else if ("approve_doc".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ApproveDocumentItemCommand();
		}
		else if ("reject_doc".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RejectDocumentItemCommand();
		}
		else if ("update_staging_doc_item".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new CloseDocumentItemTemplateCommand();
			objArray[1] = new UpdateStagingDocumentItemCommand();
		}
		else if ("delete_staging_doc_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new DeleteStagingDocumentItemCommand();
		}
		else if ("close_doc_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CloseDocumentItemCommand();
		}
		else if ("close".equals(event) || "to_track".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadStagingDocumentItemCommand();
		}else if ("draft_doc_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new DraftDocumentItemCommand();
		}
		else if ("delete_doc_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new DeleteDocumentItemCommand();
		}else if ("checker_global_list".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new DraftDocumentItemCommand();
		}
		else if ("prepare_create_doc_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareRecurrentCommand();
		}
		
		return (objArray);
	}

	/**
	 * Method which returns default event
	 * @return default event
	 */
	public String getDefaultEvent() {
		return "global_list";
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
		return DocumentationGlobalFormValidator.validateInput((DocumentationGlobalForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals("create_doc_item") || event.equals("update_doc_item")
				|| (event.equals("update_staging_doc_item"))||event.equals("global_list")
				|| (event.equals("maker_save_update"))) {
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
		if ((resultMap.get("wip") != null)
				&& (resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference(getReference("work_in_process"));
			return aPage;
		}else{
		aPage.setPageReference(getReference(event));
		
		return aPage;
		}
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("create_doc_item".equals(event)) {
			errorEvent = "prepare_create_doc_item";
		}
		if ("update_doc_item".equals(event) || "update_staging_doc_item".equals(event)||"maker_save_update".equals(event)) {
			errorEvent = "prepare_edit_doc_item";
		}
		if ("approve_doc".equals(event)) {
			errorEvent = "process";
		}
		if (event.equals("reject_doc")) {
			errorEvent = "process";
		}
		return errorEvent;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = null;
		if ("global_list".equals(event)||"paginate".equals(event)) {
			forwardName = "after_global_list";
		}
		if ("view_doc_item".equals(event) || "close".equals(event) || "to_track".equals(event)) {
			forwardName = "after_view_doc_item";
		}
		if ("checker_global_list".equals(event) || "close".equals(event) || "to_track".equals(event)) {
			forwardName = "after_view_doc_item";
		}
		if ("edit_doc_item".equals(event) || "prepare_edit_doc_item".equals(event)) {
			forwardName = "after_edit_doc_item";
		}
		if ("create_doc_item".equals(event) || "update_doc_item".equals(event)
				|| "update_staging_doc_item".equals(event)|| "draft_doc_item".equals(event)||"maker_save_update".equals(event)) {
			forwardName = "after_create_doc_item";
		}
		if ("prepare_create_doc_item".equals(event)) {
			forwardName = "after_prepare_create_doc_item";
		}
		if ("process".equals(event)) {
			forwardName = "after_process";
		}
		if ("maker_update_save_process".equals(event)) {
			forwardName = "maker_update_save_process";
		}
		if ("edit_staging_doc_item".equals(event)) {
			forwardName = "after_edit_doc_item";
		}
		if ("approve_doc".equals(event)) {
			forwardName = "after_approve_doc";
		}
		if ("reject_doc".equals(event)) {
			forwardName = "after_reject_doc";
		}
		if ("close_doc_item".equals(event)) {
			forwardName = "after_close_doc_item";
		}
		if ( event.equals("reject_doc_error")) {
			forwardName = "after_process";
		}
		if ( event.equals("prepare_delete_doc_item")) {
			forwardName = "prepare_delete_doc_item";
		}
		if ( event.equals("delete_doc_item")) {
			forwardName = "delete_doc_item";
		}
		if ( event.equals("checker_global_list")) {
			forwardName = "after_process";
		}
		if ((event != null) && event.equals("work_in_process")) {
			forwardName = "work_in_process_page";
		} 
		return forwardName;
	}
}
