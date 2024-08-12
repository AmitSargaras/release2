/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

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
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Describe this class. Purpose: for AA Detail Description: Action class for AA
 * Detail
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name$
 */

public class AADetailAction extends CommonAction implements IPin {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	 public static final String EVENT_RETRIEVE_IMAGE_GALLARY_CAM = "retrieveImageGallaryCAMNote";
		public static final String EVENT_RETRIEVE_INDVDL_IMAGE_CAM = "retrieveIndividualImageCAMNote";
		
		   public static final String EVENT_RETRIEVE_MIG_IMAGE_GALLARY_CAM = "retrieveMigImageGallaryCAMDoc";
			public static final String EVENT_RETRIEVE_MIG_INDVDL_IMAGE_CAM = "retrieveMigIndividualImageCAMDoc";

	public ICommand[] getCommandChain(String event) {

		ICommand objArray[] = null;
		if (EVENT_VIEW.equals(event) || "maker_delete_aadetail".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareViewAADetailCommand();
			objArray[1] = new PrepareViewCheckListCommand();
		}
		else if ("maker_delete_aadetail_confirm".equals(event) || "maker_delete_aadetail_reject_confirm".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new DeleteAADetailCommand();

		}
		else if ("maker_edit_aadetail".equals(event) || "refresh_maker_edit_aadetail".equals(event)
				||"return_maker_edit_aadetail".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareEditAADetailCommand();
			
		}
		else if ("maker_edit_aadetail_confirm".equals(event)||"return_edit_Other_Covenant_List_to_AA_confirm".equals(event) || "maker_edit_aadetail_reject_confirm".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new EditAADetailCommand();
		}
		else if ("maker_add_aadetail".equals(event) || "refresh_maker_add_aadetail".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareCreateAADetailCommand();
		}
		else if ("maker_add_aadetail_confirm".equals(event) || "return_Other_Covenant_List_to_AA_confirm".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new AddNewAADetailCommand();

		}
		else if ("remove_agreement".equals(event) || "maker_edit_aadetail_confirm_error".equals(event)
				|| "maker_add_aadetail_confirm_error".equals(event)
				|| "maker_edit_aadetail_reject_confirm_error".equals(event)) {
			// remove from session.
			objArray = new ICommand[1];
			objArray[0] = new RefreshAADetailCommand();

		}
		else if ("checker_edit_aadetail".equals(event) || "maker_close_aadetail".equals(event) || "to_track".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new CheckerReadAADetailCommand();
			objArray[1] = new PrepareViewCheckListCommand();
		}
		else if ("checker_approve_edit_aadetail".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new CheckerApproveAADetailCommand();
			objArray[1] = new SubmitCheckListCommand();
		}
		else if ("checker_reject_edit_aadetail".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerRejectAADetailCommand();

		}
		else if ("maker_close_aadetail_confirm".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerCancelAADetailCommand();

		}
		else if ("maker_edit_aadetail_reject".equals(event) || "maker_delete_aadetail_reject".equals(event)
				|| "refresh_maker_edit_aadetail_reject".equals(event)||"return_maker_edit_aadetail_reject".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new MakerReadRejectedAADetailCommand();
			objArray[1] = new PrepareViewCheckListCommand();

		}
		else if ("edit_agreement".equals(event) || "add_agreement".equals(event)) {
			// go to edit agreement page
			objArray = new ICommand[1];
			objArray[0] = new SaveCurWorkingLmtCommand();

		}
		else if ("refresh_cam".equals(event)) {
			// go to add
			objArray = new ICommand[1];
			objArray[0] = new RefreshDetailsCmd();
		}
		else if ("view_cam_image".equals(event)||"view_edit_cam_image".equals(event)||"checker_view_cam_image".equals(event)
				||"view_edit_cam_image_reject".equals(event)||"view_close_cam_image_reject".equals(event)||"checker_edit_aadetail_view_image".equals(event)
				||"maker_to_track_view_image".equals(event)) {
			// go to add
			objArray = new ICommand[1];
			objArray[0] = new ReadCAMImagesCmd();
		}else if (event.equals("downloadImage")) {
			objArray = new ICommand[1];
			objArray[0] = new DownloadImageCommand();
		}else if ("print_image".equals(event) || "view_image".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ImageTagResultPrintCommand();
		}  else if (event.equals(EVENT_RETRIEVE_IMAGE_GALLARY_CAM) || event.equals(EVENT_RETRIEVE_INDVDL_IMAGE_CAM) ) {
			objArray = new ICommand[1];
			objArray[0] = new RetriveImageCommand();
		}  else if (event.equals("list_other_covenants") || event.equals("view_list_other_covenants")|| event.equals("list_other_covenants_edit") || event.equals("view_return_to_Other_Covenant_List") || event.equals("check_list_other_covenants_view"))
	{
			objArray = new ICommand[1];
			objArray[0] = new OtherCovenantDetailsCommand();
		}
		 else if (event.equals("prepare_add_other_covenant_details")|| event.equals("prepare_edit_other_covenant_details")) {
			 objArray = new ICommand[1];
			 objArray[0] = new PrepareCreateOtherCovenantDetailsCommand(); 
			}
		 else if (event.equals("save_other_covenant") || event.equals("other_covenant_deleted") || event.equals("save_edited_other_covenant") || event.equals("return_Other_Covenant_List_to_AA") ||"return_edit_Other_Covenant_List_to_AA".equals(event)|| event.equals("save_other_covenant_edit")) {
			 objArray = new ICommand[1];
			 objArray[0] = new SaveOtherCovenantCommand(); 
			}
		 else if (event.equals("check_list_other_covenants")) {
			 objArray = new ICommand[1];
			 objArray[0] = new CheckerListOtherCovenantDisplayCommand(); 
			}
		 else if (event.equals("checker_return_Other_Covenant_List_to_AA") || event.equals("view_return_to_Other_Covenant_List_checker") || event.equals("checker_view_return_to_Other_Covenant_List") ||event.equals("return_Other_Covenant_List_to_view_AA")) {
			 objArray = new ICommand[1];
			 objArray[0] = new CheckerReturnOtherCovenantToAA(); 
			}
		 else if (event.equals("view_other_covenant_details") || event.equals("view_other_covenant_details_checker")) {
			 objArray = new ICommand[1];
			 objArray[0] = new ViewOtherCovenantCommand(); 
			}
		 else if (event.equals("edit_other_covenant_details")) {
			 objArray = new ICommand[1];
			 objArray[0] = new PrepareEditOtherCovenantDetailsCommand(); 
			}
		 else if (event.equals("delete_other_covenant_details")) {
			 objArray = new ICommand[1];
			 objArray[0] = new DeleteOtherCovenantCommand(); 
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
		DefaultLogger.debug(this, "Inside validate input child class");
		return AADetailValidator.validateInput((AADetailForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if ("maker_edit_aadetail_confirm".equals(event) || "maker_edit_aadetail_reject_confirm".equals(event) || "return_Other_Covenant_List_to_AA_confirm".equals(event) || "return_edit_Other_Covenant_List_to_AA_confirm".equals(event)
				|| "maker_add_aadetail_confirm".equals(event) || "save_edited_other_covenant".equals(event) || "save_other_covenant".equals(event)) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("maker_edit_aadetail_confirm".equals(event)) {
			errorEvent = "maker_edit_aadetail_confirm_error";
		}else if ("display_other_covenant_details".equals(event)) {
			errorEvent = "maker_edit_covenant_details_confirm_error";
		}else if ("maker_edit_aadetail_reject_confirm".equals(event)) {
			errorEvent = "maker_edit_aadetail_reject_confirm_error";
		}
		else if ("maker_add_aadetail_confirm".equals(event)) {
			errorEvent = "maker_add_aadetail_confirm_error";
		}else if ("checker_reject_edit_aadetail".equals(event)) {
			errorEvent = "checker_reject_edit_aadetail_error";
		}
		else if ("return_Other_Covenant_List_to_AA_confirm".equals(event)) {
			errorEvent = "return_Other_Covenant_List_to_AA_confirm_error";
		}
		else if ("return_edit_Other_Covenant_List_to_AA_confirm".equals(event)) {
			errorEvent = "return_edit_Other_Covenant_List_to_AA_confirm_error";
		}
		else if ("save_edited_other_covenant".equals(event)) {
			errorEvent = "save_edited_other_covenant_error";
		}
		else if ("save_other_covenant".equals(event)) {
			errorEvent = "save_other_covenant_error";
		}
		else if ("save_other_covenant_edit".equals(event)) {
			errorEvent = "save_other_covenant_edit_error";
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
		String preEvent = (String) resultMap.get("preEvent");
		if ((resultMap.get("wip") != null) && (resultMap.get("wip")).equals("wip")) {

			aPage.setPageReference(getReference("work_in_process"));
			return aPage;

		}
		else if (resultMap.get(IGlobalConstant.REQUEST_ERROR_MSG) != null) {
			aPage.setPageReference(getReference("error_message"));
			return aPage;

		}
		else if ((resultMap.get("agreementNull") != null) && (resultMap.get("agreementNull")).equals("agreementNull")) {
			aPage.setPageReference(getReference(event + "_error"));
			return aPage;
		}
		else if ((resultMap.get("duplicateAANo") != null) && (resultMap.get("duplicateAANo")).equals("duplicateAANo")) {
			aPage.setPageReference(getReference(event + "_error"));
			return aPage;
		}
		else if ((resultMap.get("cannotDeleteAA") != null)
				&& (resultMap.get("cannotDeleteAA")).equals("cannotDeleteAA")) {
			aPage.setPageReference(getReference(event + "_error"));
			return aPage;

		}
		//Start Santosh UBS LIMIT
		else if ((resultMap.get("deactiveFacility") != null) && ((String) resultMap.get("deactiveFacility")).equals("deactive")) {
				aPage.setPageReference("deactive");
				return aPage;
		}
		//END Santosh UBS LIMIT
		else {
			aPage.setPageReference(getReference(event));
			return aPage;
		}
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */

	private String getReference(String event) {

		String forwardName = "submit_fail";
		if (EVENT_VIEW.equals(event) || "maker_delete_aadetail".equals(event) || "maker_close_aadetail".equals(event)
				|| "maker_delete_aadetail_reject".equals(event) || "maker_delete_aadetail_confirm_error".equals(event) || "return_Other_Covenant_List_to_view_AA".equals(event)
				|| "maker_delete_aadetail_reject_confirm_error".equals(event)) {
			forwardName = "view_aa";

		}
		else if ("maker_edit_aadetail".equals(event)|| "return_Other_Covenant_List_to_AA".equals(event) || "maker_edit_aadetail_confirm_error".equals(event)
				|| "maker_add_aadetail".equals(event) || "maker_add_aadetail_confirm_error".equals(event) || "return_edit_Other_Covenant_List_to_AA".equals(event)
				|| "maker_edit_aadetail_reject".equals(event) || "return_Other_Covenant_List_to_AA_confirm_error".equals(event) || "return_edit_Other_Covenant_List_to_AA_confirm_error".equals(event)
				|| "maker_edit_aadetail_reject_confirm_error".equals(event) || "remove_agreement".equals(event)
				|| "refresh_maker_edit_aadetail".equals(event) || "refresh_maker_edit_aadetail_reject".equals(event)
				|| "refresh_maker_add_aadetail".equals(event)||"return_maker_edit_aadetail".equals(event)
				||"return_maker_edit_aadetail_reject".equals(event)) {
			forwardName = "edit_aa";
			
		}
		else if ("edit_other_covenant_details".equals(event) || "save_edited_other_covenant_error".equals(event)) {
			forwardName = "edit_other_covenant_page";

		}
		else if ("prepare_add_other_covenant_details".equals(event) || "prepare_edit_other_covenant_details".equals(event) || "save_edited_other_covenant_error".equals(event)  || "save_other_covenant_error".equals(event) || "save_other_covenant_edit_error".equals(event)) {
			forwardName = "add_other_covenant_page";
		}
		else if ("list_other_covenants".equals(event) ||"list_other_covenants_edit".equals(event) || "save_other_covenant".equals(event) || "save_edited_other_covenant".equals(event) ||"view_list_other_covenants".equals(event) || "view_return_to_Other_Covenant_List".equals(event) || "save_other_covenant_edit".equals(event) || "other_covenant_deleted".equals(event) || "check_list_other_covenants_view".equals(event)) 
		{
			forwardName = "list_other_covenant_page";
		}
		else if ("view_other_covenant_details".equals(event) || "view_other_covenant_details_checker".equals(event))
		{
			forwardName = "view_other_covenant_details";
		}
		else if ("check_list_other_covenants".equals(event) || "view_return_to_Other_Covenant_List_checker".equals(event) || "checker_view_return_to_Other_Covenant_List".equals(event)) {
			forwardName = "check_list_other_covenant_page";
		}
		else if ("edit_other_covenant_details".equals(event) || "save_edited_other_covenant".equals(event)) {
			forwardName = "edit_other_covenant_page";
		}
		else if ("delete_other_covenant_details".equals(event)) {
			forwardName = "delete_other_covenant_details";
		}
		else if ("checker_edit_aadetail".equals(event)||"checker_reject_edit_aadetail_error".equals(event) || "checker_return_Other_Covenant_List_to_AA".equals(event)) {
			forwardName = "checker_edit_aadetail_page";

		}
		else if ("maker_edit_aadetail_confirm".equals(event) || "maker_edit_aadetail_reject_confirm".equals(event) || "return_edit_Other_Covenant_List_to_AA_confirm".equals(event)
				|| "maker_add_aadetail_confirm".equals(event) || "maker_delete_aadetail_confirm".equals(event) || "return_Other_Covenant_List_to_AA_confirm".equals(event)
				|| "maker_delete_aadetail_reject_confirm".equals(event)) {
			forwardName = "common_submit_page";

		}
		else if ("checker_approve_edit_aadetail".equals(event)) {
			forwardName = "common_approve_page";

		}
		else if ("checker_reject_edit_aadetail".equals(event)) {
			forwardName = "common_reject_page";

		}
		else if ("maker_close_aadetail_confirm".equals(event)) {
			forwardName = "common_close_page";

		}
		else if ("error_message".equals(event)) {
			forwardName = "error_message_page";

		}
		else if ("agreement_null".equals(event)) {
			forwardName = "error_message_page";

		}
		else if ("work_in_process".equals(event)) {
			forwardName = "work_in_process_page";

		}
		else if ("to_track".equals(event)) {
			forwardName = "after_to_track";

		}
		else if ("edit_agreement".equals(event)) {
			forwardName = "edit_agreement";

		}
		else if ("add_agreement".equals(event)) {
			forwardName = "add_agreement";

		}
		else if ("refresh_cam".equals(event)) {
			forwardName = "refresh_cam";

		}
		else if ("view_cam_image".equals(event)||"view_edit_cam_image".equals(event)||
				"view_edit_cam_image_reject".equals(event)||"view_close_cam_image_reject".equals(event)||"checker_edit_aadetail_view_image".equals(event)
				||"maker_to_track_view_image".equals(event)) {
			forwardName = "view_cam_image_list";

		} else if ("downloadImage".equals(event)) {
			forwardName = "downloadImage";
		}
		else if (EVENT_RETRIEVE_INDVDL_IMAGE_CAM.equals(event) || EVENT_RETRIEVE_MIG_INDVDL_IMAGE_CAM.equals(event)) {
			forwardName = "imagePath";
			
		} else if (EVENT_RETRIEVE_IMAGE_GALLARY_CAM.equals(event) || EVENT_RETRIEVE_MIG_IMAGE_GALLARY_CAM.equals(event)) {
			forwardName = "image_gallary";
		}else if ("print_image".equals(event)) {
			forwardName = "print_image";
		}else if ("view_image".equals(event)) {
			forwardName = "view_image";
		}
		else if ("checker_view_cam_image".equals(event)) {
			forwardName = "checker_view_cam_image_list";

		}

		return forwardName;
	}

}
