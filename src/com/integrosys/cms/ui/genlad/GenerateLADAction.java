/**
\ * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/genddn/GenerateDDNAction.java,v 1.13 2006/09/06 10:01:29 czhou Exp $
 */
package com.integrosys.cms.ui.genlad;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.IPin;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.common.ViewOuterLimitBCACommand;
import com.integrosys.cms.ui.genddn.PrepareDeferredListCommand;
import com.integrosys.cms.ui.genlad.ListCustomerCommand;



/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.13 $
 * @since $Date: 2006/09/06 10:01:29 $ Tag: $Name: $
 */
public class GenerateLADAction extends CommonAction implements IPin {

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
	 * @param event
	 *            is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, "Event : " + event);
		ICommand objArray[] = null;
		if ("approve".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ApproveGenerateLADCommand();
			objArray[1] = new FrameFlagSetterCommand();
		} else if ("menu".equals(event) || "menu_view".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareLADMenuCommand();
			objArray[1] = new FrameFlagSetterCommand();
		} else if ("display".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"PrepareLADListCommand");
			objArray[1] = new FrameFlagSetterCommand();
		} else if ("prepare_generate".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"PrepareGenerateLADCommand");
			objArray[1] = new FrameFlagSetterCommand();
		} else if ("generate_lad".equals(event)) {
			objArray = new ICommand[2];
//			objArray[0] = (ICommand) getNameCommandMap().get(
//					"GenerateLADCommand");
			objArray[0] = (ICommand) getNameCommandMap().get("DynamicGenerateLADCommand");
			objArray[1] = new FrameFlagSetterCommand();
		} else if ("prepare_update_lad".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"PrepareUpdateLADCommand");
			objArray[1] = new FrameFlagSetterCommand();
		} else if ("prepare_view_lad".equals(event)
				|| "prepare_view_lad_view".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("ViewLADCommand");
			objArray[1] = new FrameFlagSetterCommand();
		} else if ("update_lad".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap()
					.get("UpdateLADCommand");
			objArray[1] = new FrameFlagSetterCommand();
		} else if ("prepare_update_document_lad".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"PrepareUpdateDocumentLADCommand");
			objArray[1] = new FrameFlagSetterCommand();
		} else if ("update_document_lad".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"UpdateDocumentLADCommand");
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ((event.equals("list"))) {
			objArray = new ICommand[1];
			objArray[0] = new ListCustomerCommand();
		}else if ((event.equals("cancleFilter"))) {
			objArray = new ICommand[1];
			objArray[0] = new CancleFilterCmd();
		}
		else if ((event.equals("submit"))) {
			objArray = new ICommand[1];
			objArray[0] = new SubmitCustomerCommand();
			//party-----------------------
			//guarantor-----------------------
		}
		
		else if ((event.equals("generate_report"))) {
			DefaultLogger.debug(this,
					"before command");
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"GeneratePartyLADCommand");
			//party-----------------------
			//guarantor-----------------------
		}
		
		

		
		
		
		
		
		// else if ("view_lad".equals(event)) {
		// objArray = new ICommand[2];
		// objArray[0] = (ICommand) getNameCommandMap().get("ViewLADCommand");
		// objArray[1] = new FrameFlagSetterCommand();
		// }
		/*
		 * else if ("reject".equals(event)) { objArray = new ICommand[2];
		 * objArray[0] = new RejectGenerateLADCommand(); objArray[1] = new
		 * FrameFlagSetterCommand(); }
		 */
		/*
		 * else if ("process".equals(event) || "edit_staging".equals(event) ||
		 * "close_item".equals(event) || "to_track".equals(event)) { // objArray
		 * = new ICommand[5]; // objArray[0] = new PrepareGenerateDDNCommand();
		 * // objArray[1] = new PrepareDeferredListCommand(); // objArray[2] =
		 * new ReadStagingGenerateDDNCommand(); // objArray[3] = new
		 * RefreshGenerateDDNCommand(); // objArray[4] = new
		 * FrameFlagSetterCommand(); objArray = new ICommand[4]; objArray[0] =
		 * new PrepareDeferredListCommand(); objArray[1] = new
		 * ReadStagingGenerateLADCommand(); objArray[2] = new
		 * RefreshGenerateLADCommand(); objArray[3] = new
		 * FrameFlagSetterCommand(); }
		 */
		else if ("close".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new CloseGenerateLADCommand();
			objArray[1] = new PrepareDeferredListCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		/*
		 * else if ("prepare_form".equals(event)) { objArray = new ICommand[3];
		 * // objArray[0] = new PrepareGenerateDDNCommand(); objArray[0] = new
		 * PrepareDeferredListCommand(); objArray[1] = new
		 * RefreshGenerateLADCommand(); objArray[2] = new
		 * FrameFlagSetterCommand(); }
		 */
		else if ("print_doc".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PreparePrintGenerateLADCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		/*
		 * else if ("update_view_outer_limit_bca".equals(event)) { objArray =
		 * new ICommand[3]; objArray[0] = new SaveGenerateLADCommand();
		 * objArray[1] = new ViewOuterLimitBCACommand(); objArray[2] = new
		 * FrameFlagSetterCommand(); }
		 */
		else if ("view_outer_limit_bca".equals(event)
				|| "checker_view_outer_limit_bca".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ViewOuterLimitBCACommand();
			objArray[1] = new FrameFlagSetterCommand();
		} else if ("checker_return".equals(event)
				|| "view_return".equals(event)) {
			// objArray = new ICommand[3];
			// objArray[0] = new ViewReturnGenerateDDNCommand();
			// objArray[1] = new PrepareGenerateDDNCommand();
			// objArray[2] = new FrameFlagSetterCommand();
			objArray = new ICommand[2];
			objArray[0] = new ViewReturnGenerateLADCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		/*
		 * else if ("update_return".equals(event)) { // objArray = new
		 * ICommand[3]; // objArray[0] = new ReturnGenerateDDNCommand(); //
		 * objArray[1] = new PrepareGenerateDDNCommand(); // objArray[2] = new
		 * FrameFlagSetterCommand(); objArray = new ICommand[2]; objArray[0] =
		 * new ReturnGenerateLADCommand(); objArray[1] = new
		 * FrameFlagSetterCommand(); }
		 */
		else if ("generate_ddn".equals(event)) { // cr36
			// objArray = new ICommand[4];
			// objArray[0] = new PrepareGenerateDDNCommand();
			// objArray[1] = new PrepareDeferredListCommand();
			// objArray[2] = new GenerateDDNCommand();
			// objArray[3] = new FrameFlagSetterCommand();
			objArray = new ICommand[3];
			objArray[0] = new PrepareDeferredListCommand();
			objArray[1] = new GenerateLADCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		/*
		 * else if ("refresh_gen_ddn".equals(event)) { objArray = new
		 * ICommand[2]; objArray[0] = new RefreshGenerateLADCommand();
		 * objArray[1] = new FrameFlagSetterCommand(); }
		 */
		/*
		 * else if ("submit_ddn".equals(event)) { objArray = new ICommand[2];
		 * objArray[0] = new SubmitGenerateLADCommand(); objArray[1] = new
		 * FrameFlagSetterCommand(); }
		 */
		/*
		 * else if ("total_ddn".equals(event)) { // objArray = new ICommand[3];
		 * // objArray[0] = new PrepareGenerateDDNCommand(); // objArray[1] =
		 * new TotalGenerateDDNCommand(); // objArray[2] = new
		 * FrameFlagSetterCommand(); objArray = new ICommand[2]; objArray[0] =
		 * new TotalGenerateLADCommand(); objArray[1] = new
		 * FrameFlagSetterCommand(); }
		 */
		else if ("update_remarks".equals(event) || "view_remarks".equals(event)) {
			objArray = new ICommand[4];
			objArray[0] = new GenerateLADCommand();
			objArray[1] = new PrepareDeferredListCommand();
			objArray[2] = new PrepareRemarksGenerateLADCommand();
			objArray[3] = new FrameFlagSetterCommand();
		}
		/*
		 * else if ("submit_remarks".equals(event)) { objArray = new
		 * ICommand[2]; objArray[0] = new SubmitGenerateLADCommand();
		 * objArray[1] = new FrameFlagSetterCommand(); }
		 */
		else if ("view_generate_ddn".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new PrepareDeferredListCommand();
			objArray[1] = new GenerateLADCommand();
			objArray[2] = new FrameFlagSetterCommand();
		} else if ("download_lad_doc".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new com.integrosys.cms.ui.genlad.DownloadReportCmd();

		}else if("lad_report".equals(event)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("LADReportCommand");
		}

		return (objArray);
	}

	/**
	 * This method is called only for create and Update command to validate the
	 * form and return the ActionErrors object.
	 * 
	 * @param aForm
	 *            is of type ActionForm
	 * @param locale
	 *            of type Locale
	 * @return ActionErrors
	 */
	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.debug(this, "Inside validate Input child class");
		// return
		// SecurityReceiptFormValidator.validateInput((SecurityReceiptForm
		// )aForm,locale);
		GenerateLADForm frm = (GenerateLADForm) aForm;
		return GenerateLADFormValidator.validateInput((GenerateLADForm) aForm,
				locale);

	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		/**
		 * Chee Hong - No validation required for GenDDN in ABCLIMS
		 * */
		if ("generate_lad".equals(event) || "update_lad".equals(event)) {
			result = true;
		}
		return result;
	}

	/**
	 * This method is used to determine which the page to be displayed next
	 * using the event Result hashmap and exception hashmap.It returns the page
	 * object .
	 * 
	 * @param event
	 *            is of type String
	 * @param resultMap
	 *            is of type HashMap
	 * @param exceptionMap
	 *            is of type HashMap
	 * @return IPage
	 */
	public IPage getNextPage(String event, HashMap resultMap,
			HashMap exceptionMap) {
		Page aPage = new Page();
		
	
		// DefaultLogger.debug(this,"result map"+resultMap);
		if (resultMap.get("error") != null) {
			DefaultLogger.debug(this, "Page Reference" + "is INFO ------>");
			aPage.setPageReference("info");
			return aPage;
		}
		
		if (resultMap.get("lad_report") != null) {
			DefaultLogger.debug(this, "Page Reference" + "is INFO ------>");
			aPage.setPageReference("lad_report");
			return aPage;
		}
		
		if ((resultMap.get("wip") != null)
				&& ((String) resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("wip");
			return aPage;
		}
		
		
		

		
		
		if ((resultMap.get("no_template") != null)
				&& ((String) resultMap.get("no_template")).equals("true")) {
			aPage.setPageReference("no_template");
			return aPage;
		}
		if ((resultMap.get("frame") != null)
				&& ("false").equals((String) resultMap.get("frame"))) {
			String forwardName = frameCheck(getReference(event));
			aPage.setPageReference(forwardName);
			return aPage;
		}
		if ((resultMap.get("na") != null)
				&& ((String) resultMap.get("na")).equals("na")) {
			aPage.setPageReference("na");
			return aPage;
		}
		if ((resultMap.get("redirect") != null)
				&& resultMap.get("isChecker").equals("false")) {

			if ((resultMap.get("redirect") != null)
					&& ((String) resultMap.get("redirect")).equals("LADDUE")) {
				aPage.setPageReference("lad_due_page");
				return aPage;
			}
			if ((resultMap.get("redirect") != null)
					&& ((String) resultMap.get("redirect")).equals("LADGBNR")) {
				aPage.setPageReference("lad_gen_not_rec_page");
				return aPage;
			}
			if ((resultMap.get("redirect") != null)
					&& ((String) resultMap.get("redirect")).equals("LADREC")) {
				aPage.setPageReference("lad_rec_page");
				return aPage;
			}
		} else {
			if ((resultMap.get("redirect") != null)
					&& ((String) resultMap.get("redirect")).equals("LADDUE")) {
				aPage.setPageReference("lad_due_page_view");
				return aPage;
			}
			if ((resultMap.get("redirect") != null)
					&& ((String) resultMap.get("redirect")).equals("LADGBNR")) {
				aPage.setPageReference("lad_gen_not_rec_page_view");
				return aPage;
			}
			if ((resultMap.get("redirect") != null)
					&& ((String) resultMap.get("redirect")).equals("LADREC")) {
				aPage.setPageReference("lad_rec_page_view");
				return aPage;
			}
		}
		aPage.setPageReference(getReference(event));
		return aPage;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("generate_lad".equals(event)) {
			errorEvent = "prepare_generate";
		}
		if ("update_lad".equals(event)) {
			errorEvent = "prepare_update_lad";
		}
		return errorEvent;
	}

	/**
	 * method which determines the forward name for a particular event
	 * 
	 * @param event
	 *            as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = null;
		if("cancleFilter".equals(event)){
			return "prepare_filter";
		}else
		if ("list_ccc".equals(event)
				|| "edit_staging_checklist_item".equals(event)
				|| "view_ok".equals(event)) {
			forwardName = "after_list_ccc";
		} else if ("generate_ddn".equals(event) || "total_ddn".equals(event)
				|| "prepare_form".equals(event)
				|| "update_return".equals(event)
				|| "refresh_gen_ddn".equals(event)) {
			forwardName = "after_generate_ddn";
		} else if ("submit_ddn".equals(event) || "submit_remarks".equals(event)) {
			forwardName = "after_submit";
		} else if ("process".equals(event) || "checker_return".equals(event)) {
			forwardName = "after_process";
		} else if ("edit_staging".equals(event)) {
			forwardName = "after_edit_staging";
		} else if ("chk_view_recurrent".equals(event)) {
			forwardName = "after_chk_view_recurrent";
		} else if ("approve".equals(event)) {
			forwardName = "after_approve";
		} else if ("reject".equals(event)) {
			forwardName = "after_reject";
		} else if ("close_item".equals(event) || "to_track".equals(event)
				|| "view_return".equals(event)
				|| "view_generate_ddn".equals(event)
				|| "view_remarks".equals(event)) {
			forwardName = "after_close_item";
		} else if ("close".equals(event)) {
			forwardName = "after_close";
		} else if ("edit_covenant".equals(event)) {
			forwardName = "after_edit_covenant";
		} else if ("edit_recurrent".equals(event)) {
			forwardName = "after_edit_recurrent";
		} else if ("print_doc".equals(event)) {
			forwardName = "after_print_doc";
		} else if ("update_view_outer_limit_bca".equals(event)
				|| "view_outer_limit_bca".equals(event)) {
			forwardName = "view_outer_limit_bca";
		} else if ("checker_view_outer_limit_bca".equals(event)) {
			forwardName = "checker_view_outer_limit_bca";
		} else if ("update_remarks".equals(event)) {
			forwardName = "after_update_remarks";
		} else if ("menu".equals(event)) {
			forwardName = "menu";
		} else if ("menu_view".equals(event)) {
			forwardName = "menu_view";
		} else if ("prepare_generate".equals(event)) {
			forwardName = "prepare_generate";
		} else if ("generate_lad".equals(event)) {
			forwardName = "generate_lad";
		} else if ("update_lad".equals(event)) {
			forwardName = "update_lad";
		} else if ("view_lad".equals(event)) {
			forwardName = "view_lad";
		} else if ("prepare_update_lad".equals(event)) {
			forwardName = "prepare_update_lad";
		} else if ("prepare_view_lad".equals(event)) {
			forwardName = "prepare_view_lad";
		} else if ("prepare_view_lad_view".equals(event)) {
			forwardName = "prepare_view_lad_view";
		} else if ("prepare_update_document_lad".equals(event)) {
			forwardName = "prepare_update_document_lad";
		} else if ("update_document_lad".equals(event)) {
			forwardName = "update_document_lad";
		} else if ("download_lad_doc".equals(event)) {
			forwardName = "download_lad_doc";
		} else if ("lad_report".equals(event)) {
			forwardName = "lad_report";
		}	else{
			forwardName=event;
		}
		

		return forwardName;
	}

	private String frameCheck(String forwardName) {
		return forwardName + "_WF";
	}
	
	
	
	
	

	

}