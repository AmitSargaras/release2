/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/checklist/secreceipt/SecurityReceiptAction.java,v 1.38 2006/09/05 03:23:09 czhou Exp $
 */
package com.integrosys.cms.ui.checklist.facilityreceipt;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.checklist.BackToChecklistItemCommand;
import com.integrosys.cms.ui.checklist.MapChecklistItemCommand;
import com.integrosys.cms.ui.checklist.RetriveImageGalleryCommand;
import com.integrosys.cms.ui.checklist.TagUntagImageCommand;
import com.integrosys.cms.ui.checklist.camreceipt.DownloadImageCommand;
import com.integrosys.cms.ui.checklist.camreceipt.FrameFlagSetterCommand;
import com.integrosys.cms.ui.checklist.camreceipt.ImageTagResultPrintCommand;
import com.integrosys.cms.ui.checklist.camreceipt.PrepareCommand;
import com.integrosys.cms.ui.checklist.camreceipt.ReadCheckListItemCommand;

/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.38 $
 * @since $Date: 2006/09/05 03:23:09 $ Tag: $Name: $
 */
public class FacilityReceiptAction extends CommonAction {

	public final static String EVENT_REFRESH_SHARE_CHECKLIST = "refresh_share_checklist";
    public final static String EVENT_EXPRESS_COMPLETION = "express_complete";
    public static final String EVENT_RETRIEVE_IMAGE_GALLARY_FAC = "retrieveImageGallaryFacilityDoc";
	public static final String EVENT_RETRIEVE_INDVDL_IMAGE_FAC = "retrieveIndividualImageFacilityDoc";
	public final static String SEARCH_FACILITY_DOC = "search_facility_doc";
	public final static String CHECKER_SEARCH_FACILITY_DOC = "checker_search_facility_doc";
	public final static String CHECKER_SEARCH_PROCESS_FACILITY_DOC = "checker_search_process_facility_doc";
	public final static String PREPARE_INACTIVE = "prepare_inactive";
	public final static String CONFORM_INACTIVE = "conform_inactive";
	
	public final static String EVENT_PREPARE_TAG_UNTAG_IMAGE = "prepare_tag_untag_image";
	public final static String EVENT_PREPARE_TAG_UNTAG_IMAGE_ERROR = "prepare_tag_untag_image_error";
	public final static String EVENT_TAG_UNTAG_IMAGE = "tag_untag_image";
	public final static String EVENT_CANCEL_TAG_UNTAG_IMAGE = "cancel_tag_untag_image";
	public final static String EVENT_RETRIEVE_IMAGE_GALLERY_CHK_FACILITY = "retrieve_image_gallery_chk_facility";
	public final static String EVENT_RETRIEVE_INDIVIDUAL_IMAGE_CHK = "retrieve_individual_image_chk";
	public final static String EVENT_RETRIEVE_INDIVIDUAL_IMAGE_CHK_FACILITY = "retrieve_individual_image_chk_facility";
	public final static String EVENT_PREPARE_TAG_UNTAG_IMAGE_FROM_RETRIEVE = "prepare_retrieve_tag_untag_image";
	public static final String EVENT_RETRIEVE_IMAGE_GALLARY_FACILITY = "retrieveImageGallaryFacilityDoc";
	public static final String EVENT_RETRIEVE_INDVDL_IMAGE_FACILITY = "retrieveIndividualImageFacilityDoc";
	public static final String EVENT_RETRIEVE_MIG_IMAGE_GALLARY_FACILITY = "retrieveMigImageGallaryFACILITYDoc";
	public static final String EVENT_RETRIEVE_MIG_INDVDL_IMAGE_FACILITY = "retrieveMigIndividualImageFACILITYDoc";
	public final static String EVENT_PREPARE_TAG_UNTAG_IMAGE_VIEW = "prepare_tag_untag_image_view";
	public final static String EVENT_RETRIEVE_IMAGE_GALLERY_CHK_FACILITY_CHECKER_VIEW = "retrieve_image_gallery_chk_facility_checker_view";
	public final static String EVENT_PREPARE_TAG_UNTAG_IMAGE_FROM_RETRIEVE_VIEW = "prepare_retrieve_tag_untag_image_view";
	
	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		// Start for CR-17
		if (EVENT_REFRESH_SHARE_CHECKLIST.equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new PrepareCommand();
			objArray[1] = new UpdateShareCheckListCommand();
			objArray[2] = new FrameFlagSetterCommand();
			// End for CR-17
		}else if (event.equals("downloadImage")) {
			objArray = new ICommand[1];
			objArray[0] = new DownloadImageCommand();
		}
		else if ("list".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new ListFacilityReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
			objArray[2] = new PrepareCommand();
		}
		else if ("cust_list".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ListFilteredFacilityReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("prepare_update".equals(event) || "view".equals(event)
				||SEARCH_FACILITY_DOC.equals(event)||CHECKER_SEARCH_FACILITY_DOC.equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new PrepareUpdateFacilityReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
			objArray[2] = new PrepareCommand();
		}
		else if ("REMIND".equals(event) || "RECEIVE".equals(event) || "WAIVER_REQ".equals(event)
				|| "DEFER_REQ".equals(event) || "DELETE".equals(event) || "VIEW_COMPLETED".equals(event)
                || "REDEEM".equals(event) || "VIEW_REDEEMED".equals(event) || "VIEW_PENDING_REDEEM".equals(event)
				|| "VIEW_DEFERRED".equals(event) || "VIEW_DELETED".equals(event) || "VIEW_EXPIRED".equals(event)
				|| "VIEW_PENDING_COMPLETE".equals(event) || "VIEW_PENDING_DELETE".equals(event)
				|| "VIEW_PENDING_RENEWAL".equals(event) || "VIEW_PENDING_WAIVER".equals(event)
				|| "VIEW_RECEIVED".equals(event) || "VIEW_REMINDED".equals(event) || "VIEW_RENEWED".equals(event)
				|| "VIEW_WAIVED".equals(event) || "VIEW_PENDING_DEFER".equals(event)
				|| "VIEW_PENDING_DEFER_REQ".equals(event) || "VIEW_PENDING_WAIVER_REQ".equals(event)
				|| "VIEW_DEFER_REQ".equals(event) || "VIEW_WAIVER_REQ".equals(event) || "WAIVER".equals(event)
                || "DEFER".equals(event) || "UPDATE_DEFERRED".equals(event)
				|| "UPDATE_NARRATION".equals(event) || "RENEW".equals(event)
				|| "COMPLETE".equals(event) || "UPDATE".equals(event) || "VIEW_PENDING_UPDATE".equals(event)
				|| "UPDATE_RENEWAL".equals(event) || "ALLOW_PERM_UPLIFT".equals(event)
				|| "ALLOW_TEMP_UPLIFT".equals(event) || "VIEW_WAIVER_REQ_GENERATED".equals(event)
				|| "VIEW_DEFER_REQ_GENERATED".equals(event) || "ALLOW_RELODGE".equals(event)
                || "LODGE".equals(event) || "UPDATE_LODGED".equals(event) || "VIEW_LODGED".equals(event)
                || "TEMP_UPLIFT".equals(event) || "VIEW_TEMP_UPLIFTED".equals(event)
                || "PERM_UPLIFT".equals(event) || "VIEW_PERM_UPLIFTED".equals(event)
                || "VIEW_PENDING_TEMP_UPLIFT".equals(event) || "VIEW_PENDING_PERM_UPLIFT".equals(event)
                || "VIEW_AWAITING".equals(event) || "VIEW_PENDING_LODGE".equals(event)
                || "VIEW_PENDING_RELODGE".equals(event)||"prepare_inactive".equals(event)
                || "DISCREPANCY".equals(event)|| "NOT_APPLICABLE".equals(event) || "RECEIVECANCEL".equals(event)) {        // bernard
																								// -
																								// added
																								// ALLOW_RELODGE
																								// ,
																								// priya
																								// -
																								// added
																								// UPDATE_RENEWAL
																								// for
																								// CMSSP
																								// -
																								// 619
			objArray = new ICommand[4];
			objArray[0] = new PrepareCommand();
			objArray[1] = new ReadCheckListItemCommand();
            objArray[2] = new MapChecklistCommand();
			objArray[3] = new FrameFlagSetterCommand();
		}
		else if ("CAVEAT_PRINT_REMINDER".equals(event) || "VAL_PRINT_REMINDER".equals(event)
				|| "INSURANCE_PRINT_REMINDER".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PreparePrintCheckListItemReminderCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("PREMINUM_RECEIPT_PRINT_REMINDER".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrintPremiumReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("save_remind".equals(event) || "save_receive".equals(event) || "save_waiver_req".equals(event)
				|| "save_defer_req".equals(event) || "save_waiver".equals(event) || "save_defer".equals(event)
				|| "save_renew".equals(event) || "save_complete".equals(event) || "save_update".equals(event)
				|| "save_update_narration".equals(event) || "save_update_renewal".equals(event)
				|| "save_temp_uplift".equals(event) || "save_perm_uplift".equals(event) || "save_delete".equals(event)
				|| "save_relodge".equals(event) || "save_redeem".equals(event)
				|| "save_discrepancy".equals(event)) {// priya -added
													                            // save_update_renewal for
													                            // CMSSP-619
			objArray = new ICommand[4];
			objArray[0] = new UpdateShareCheckListCommand();
			objArray[1] = new SaveCheckListItemCommand();
            objArray[2] = new FrameFlagSetterCommand();
			objArray[3] = new PrepareCommand();
		}
		else if ("undo".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new UndoCheckListItemCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("save".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new SaveFacilityReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("submit".equals(event)) {
			objArray = new ICommand[2];
//			objArray[0] = new CreateImageTagAddFacReceiptCommand();
			objArray[0] = new SubmitFacilityReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}else if ("submiting".equals(event)) {
			objArray = new ICommand[2];
//			objArray[0] = new EditImageTagFacReceiptCommand();
			objArray[0] = new SubmitFacilityReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("edit".equals(event) || "close_checklist_item".equals(event) || "cancel_checklist_item".equals(event)
				|| "process".equals(event) || "to_track".equals(event)
				||CHECKER_SEARCH_PROCESS_FACILITY_DOC.equals(event)||"search_facility_doc_checker".equals(event)
				||"search_facility_doc_checker_close".equals(event)||"search_facility_doc_checker_process".equals(event)
				||"search_facility_doc_maker_close".equals(event)) { // OFFICE
			objArray = new ICommand[3];
//			objArray[0] = new CheckerReadImageTagFacReceiptCmd();
			objArray[0] = new ReadStagingFacilityReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
			objArray[2] = new PrepareCommand();
		}
		else if ("edit_staging_checklist_item".equals(event)) {
			objArray = new ICommand[3];
//			objArray[0] = new MakerPreapreResubmitImageTagFacReceiptCmd();
			objArray[0] = new ReadStagingFacilityReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
			objArray[2] = new PrepareCommand();
		}
		else if ("refresh_comment".equals(event) || "chk_view_return".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RefreshFacilityReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("close".equals(event)) {
			objArray = new ICommand[2];
//			objArray[0] = new MakerCloseImageTagFacReceiptCmd();
			objArray[0] = new CloseFacilityReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("cancel".equals(event)) {
			objArray = new ICommand[2];
//			objArray[0] = new MakerCloseImageTagFacReceiptCmd();
			objArray[0] = new CancelFacilityReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("approve_checklist_item".equals(event)) {
			objArray = new ICommand[2];
//			objArray[0] = new CheckerApproveCreateImageTagFacReceiptCmd();
			objArray[0] = new ApproveFacilityReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("reject_checklist_item".equals(event)) {
			objArray = new ICommand[2];
//			objArray[0] = new CheckerRejectEditImageTagFacReceiptCmd();
			objArray[0] = new RejectFacilityReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		// Begin of OFFICE
		else if ("forward_checklist_item".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ForwardFacilityReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		// End of OFFICE
		else if ("view_ok".equals(event) || "view_return".equals(event) || "track_refresh_comment".equals(event)
				|| "maker_refresh_comment".equals(event)||"view_return_to_track".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new FrameFlagSetterCommand();                
        }
		else if ("save_remind_prepare".equals(event) || "save_receive_prepare".equals(event)
				|| "save_waiver_req_prepare".equals(event) || "save_defer_req_prepare".equals(event)
				|| "save_waiver_prepare".equals(event) || "save_defer_prepare".equals(event)
				|| "save_renew_prepare".equals(event) || "save_complete_prepare".equals(event)
				|| "save_update_prepare".equals(event) || "save_update_renewal_prepare".equals(event)
				|| "save_temp_uplift_prepare".equals(event) || "save_perm_uplift_prepare".equals(event)
				|| "save_delete_prepare".equals(event) || "save_relodge_prepare".equals(event)
				|| "refresh_share_checklist_prepare".equals(event) || "save_discrepancy_prepare".equals(event)
				|| EVENT_PREPARE_TAG_UNTAG_IMAGE_ERROR.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("chk_view".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new CheckerViewFacilityReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
			objArray[2] = new PrepareCommand();
		}
		else if ("view_receipt".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new ViewFacilityReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
			objArray[2] = new PrepareCommand();
		}
        else if (EVENT_PREPARE.equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = new SetErrorCommand();
            objArray[1] = new FrameFlagSetterCommand();
        }
        else if(EVENT_EXPRESS_COMPLETION.equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = new ExpressCompleteCommand();
            objArray[1] = new FrameFlagSetterCommand();
        }
        else if("view_image_page".equals(event)||"view_image_page_to_track".equals(event)||"view_image_page_to_do".equals(event)||"view_image_page_to_do_process".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new ViewImageCommand();
//            objArray[1] = new FrameFlagSetterCommand();
        }else if (event.equals("retrieveImage")) {
			objArray = new ICommand[1];
			objArray[0] =new RetriveImageCommand();
		}else if (event.equals(EVENT_RETRIEVE_IMAGE_GALLARY_FAC) || event.equals(EVENT_RETRIEVE_INDVDL_IMAGE_FAC)) {
			objArray = new ICommand[1];
			objArray[0] = new RetriveImageCommand();
		}
		/*else if (event.equals("retrieveImageTagUntagFacReceipt") || event.equals("retrieveIndImageTagUntagFacReceiptPath")) {
			objArray = new ICommand[1];
			objArray[0] = new RetriveImageTagUntagFacRecCommand();
		}*/
		else if ("print_image".equals(event) || "view_image".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ImageTagResultPrintCommand();
		}else if ("version".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new VersionReceiptCommand();
			objArray[1] = new PrepareCommand();
			objArray[2] = new FrameFlagSetterCommand();
	}else if ("conform_inactive".equals(event)) {
		objArray = new ICommand[2];
		objArray[0] = new ReadCheckListItemCommand();
		objArray[1] = new FrameFlagSetterCommand();                
    }else if ("imagetaguntaglist".equals(event)) {
		objArray = new ICommand[4];
		objArray[0] = new PrepareCommand();
		objArray[1] = new ReadCheckListItemCommand();
        objArray[2] = new MapChecklistCommand();
		objArray[3] = new FrameFlagSetterCommand();
//		objArray[4] = new FacilityReceiptImageTagUntagCommand();
    }else if("facilityreceipt_image_tag_untag_submit".equals(event)) {
    	objArray = new ICommand[4];
		objArray[0] = new PrepareCommand();
		objArray[1] = new ReadCheckListItemCommand();
        objArray[2] = new MapChecklistCommand();
		objArray[3] = new FrameFlagSetterCommand();
//		objArray[4] = new FacilityReceiptImageTagUntagCommand();
    }else if(EVENT_PREPARE_TAG_UNTAG_IMAGE.equals(event) || EVENT_PREPARE_TAG_UNTAG_IMAGE_VIEW.equals(event)) {
		objArray = new ICommand[3];
		objArray[0] = new MapChecklistItemCommand();
		objArray[1] = new TagUntagImageCommand();
		objArray[2] = new FrameFlagSetterCommand();
	}else if(EVENT_PREPARE_TAG_UNTAG_IMAGE_FROM_RETRIEVE.equals(event) || EVENT_PREPARE_TAG_UNTAG_IMAGE_FROM_RETRIEVE_VIEW.equals(event)) {
		objArray = new ICommand[2];
		objArray[0] = new TagUntagImageCommand();
		objArray[1] = new FrameFlagSetterCommand();
	}else if(EVENT_TAG_UNTAG_IMAGE.equals(event)) {
		objArray = new ICommand[3];
		objArray[0] = new TagUntagImageCommand();
		objArray[1] = new BackToChecklistItemCommand();
		objArray[2] = new FrameFlagSetterCommand();
	}
	else if(EVENT_CANCEL_TAG_UNTAG_IMAGE.equals(event)) {
		objArray = new ICommand[2];
		objArray[0] = new BackToChecklistItemCommand();
		objArray[1] = new FrameFlagSetterCommand();
	}
	else if(EVENT_RETRIEVE_IMAGE_GALLERY_CHK_FACILITY.equals(event) || EVENT_RETRIEVE_INDIVIDUAL_IMAGE_CHK_FACILITY.equals(event) || EVENT_RETRIEVE_IMAGE_GALLERY_CHK_FACILITY_CHECKER_VIEW.equals(event)) {
		objArray = new ICommand[2];
		objArray[0] = new RetriveImageGalleryCommand();
		objArray[1] = new FrameFlagSetterCommand();
	}
        DefaultLogger.debug(this, "event is ============="+event);
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
		return FacilityReceiptFormValidator.validateInput((FacilityReceiptForm) aForm, locale);
		// return
		// null;//SecurityMasterFormValidator.validateInput((SecurityMasterForm
		// )aForm,locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if ("save_remind".equals(event) || "save_receive".equals(event) || "save_waiver_req".equals(event)
				|| "save_defer_req".equals(event) || "save_waiver".equals(event) || "save_defer".equals(event)
				|| "save_renew".equals(event) || "save_complete".equals(event) || "save_update".equals(event)
				|| "save_update_narration".equals(event) || "save_update_renewal".equals(event)
				|| "save_temp_uplift".equals(event) || "save_perm_uplift".equals(event) || "save_delete".equals(event)
				|| "save_relodge".equals(event) // priya -added
												// save_update_renewal for
												// CMSSP-619
                || "save_redeem".equals(event)
                || EVENT_SUBMIT.equals(event) || "save".equals(event) || EVENT_REFRESH_SHARE_CHECKLIST.equals(event)
                ||  "save_discrepancy".equals(event) || "submiting".equals(event)
                || EVENT_PREPARE_TAG_UNTAG_IMAGE.equals(event)) {

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

		if ((resultMap.get("no_template") != null) && ((String) resultMap.get("no_template")).equals("true")) {
			aPage.setPageReference("no_template");
			return aPage;
		}

		if ((resultMap.get("wip") != null) && ((String) resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("wip");
			return aPage;
		}
		// R1.5 CR17 Starts
		DefaultLogger.debug(this, ">>>>>>>>>>> EVENT (getNextPage): " + event);
		DefaultLogger.debug(this, ">>>>>>>>>>> PREV EVENT (getNextPage): " + resultMap.get("prev_event"));

		if (resultMap.get("prev_event") != null) {
			String forwardName;
			if ((resultMap.get("frame") != null) && ("false").equals((String) resultMap.get("frame"))) {
				forwardName = frameCheck(getReference((String) resultMap.get("prev_event")));
			}
			else {
				forwardName = getReference((String) resultMap.get("prev_event"));
			}

			DefaultLogger.debug(this, ">>>>>>>>>>>>> frame: " + resultMap.get("frame"));
			DefaultLogger.debug(this, ">>>>>>>>>>>>> forward name: " + forwardName);
			aPage.setPageReference(forwardName);
			return aPage;
		}
		// R1.5 CR17 Ends

		if ((resultMap.get("frame") != null) && ("false").equals((String) resultMap.get("frame"))) {
			String forwardName = frameCheck(getReference(event));
			aPage.setPageReference(forwardName);
			return aPage;
		}
		// CR CMS-662 Starts
		if ((resultMap.get("leyes") != null) && ((String) resultMap.get("leyes")).equals("leyes")) {
			aPage.setPageReference(getReference(event + "_prepare"));
			return aPage;
		}
		// CR CMS-662 Ends

		aPage.setPageReference(getReference(event));
		return aPage;
	}

	protected String getErrorEvent(String event) {
		DefaultLogger.debug(this, ">>>>>>>>>>>> IN getErrorEvent!!!");
		String errorEvent = getDefaultEvent();

		if ("save_remind".equals(event) || "save_receive".equals(event) || "save_waiver_req".equals(event)
				|| "save_defer_req".equals(event) || "save_waiver".equals(event) || "save_defer".equals(event)
				|| "save_renew".equals(event) || "save_complete".equals(event) || "save_update".equals(event)
				|| "save_update_narration".equals(event) || "save_update_renewal".equals(event)
				|| "save_temp_uplift".equals(event) || "save_perm_uplift".equals(event) || "save_delete".equals(event)
				|| "save_relodge".equals(event) || "save_redeem".equals(event)
                || EVENT_REFRESH_SHARE_CHECKLIST.equals(event) || "save_discrepancy".equals(event)) { // priya
																									// -
																									// added
																									// save_update_renewal
																									// for
																									// CMSSP
																									// -
																									// 619
			errorEvent = event + "_prepare";
		}
		else if ("save".equals(event)) {
			errorEvent = EVENT_PREPARE;  
		}
        else if (EVENT_SUBMIT.equals(event) || "submiting".equals(event)) {
            errorEvent = EVENT_PREPARE;
        }else if ("reject_checklist_item".equals(event)) {
            errorEvent = "reject_checklist_item_error";
        } else if(EVENT_PREPARE_TAG_UNTAG_IMAGE.equals(event)) {
        	errorEvent = EVENT_PREPARE_TAG_UNTAG_IMAGE_ERROR;
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
		if ("list".equals(event)) {
			forwardName = "after_list";
		}
		if ("cust_list".equals(event)) {
			forwardName = "after_cust_list";
		}
		if ("prepare_update".equals(event) || "save_remind".equals(event) || "undo".equals(event)
				|| "save_receive".equals(event) || "save_waiver_req".equals(event) || "save_defer_req".equals(event)
				|| "save_waiver".equals(event) || "save_defer".equals(event) || "save_renew".equals(event)
				|| "save_complete".equals(event) || "save_update".equals(event)
				|| "save_update_narration".equals(event) || "save_update_renewal".equals(event)
				|| "save_temp_uplift".equals(event) || "save_perm_uplift".equals(event) || "save_delete".equals(event)
				|| "view_ok".equals(event) || "edit".equals(event) || "edit_staging_checklist_item".equals(event)
				|| "maker_refresh_comment".equals(event) || "save_relodge".equals(event) || "save_redeem".equals(event)
                || EVENT_PREPARE.equals(event) || EVENT_EXPRESS_COMPLETION.equals(event)
                || SEARCH_FACILITY_DOC.equals(event)
                || "version".equals(event)||"conform_inactive".equals(event)
                || "save_discrepancy".equals(event)|| "NOT_APPLICABLE".equals(event)) {                                          
																							
			forwardName = "after_prepare_update";
		}
		if ("edit_staging_checklist_item".equals(event)||"search_facility_doc_checker_process".equals(event)) {
			forwardName = "process_update";
		}
		if ("REMIND".equals(event) || "save_remind_prepare".equals(event)) {
			forwardName = "after_REMIND";
		}
		if ("RECEIVE".equals(event) || "save_receive_prepare".equals(event) || "RECEIVECANCEL".equals(event)
				||  EVENT_PREPARE_TAG_UNTAG_IMAGE_ERROR.equals(event) || EVENT_CANCEL_TAG_UNTAG_IMAGE.equals(event) || EVENT_TAG_UNTAG_IMAGE.equals(event)) {
			forwardName = "after_RECEIVE";
		}
		if ("imagetaguntaglist".equals(event)) {
			forwardName = "after_imagetaguntaglist";
		}
		if ("facilityreceipt_image_tag_untag_submit".equals(event)) {
			//forwardName = "after_facilityreceipt_image_tag_untag_submit";
			forwardName = "after_RECEIVE";
		}
        if ("REDEEM".equals(event) || "save_redeem_prepare".equals(event)) {
            forwardName = "after_REDEEM";
        }
		if ("WAIVER_REQ".equals(event) || "save_waiver_req_prepare".equals(event)) {
			forwardName = "after_WAIVER_REQ";
		}
		if ("DEFER_REQ".equals(event) || "save_defer_req_prepare".equals(event)) {
			forwardName = "after_DEFER_REQ";
		}
		if ("WAIVER".equals(event) || "save_waiver_prepare".equals(event)) {
			forwardName = "after_WAIVER";
		}
		if ("UPDATE_NARRATION".equals(event)) {
			forwardName = "after_UPDATE_NARRATION";
		}
		// CMSSP-619
		if ("UPDATE_RENEWAL".equals(event) || "save_update_renewal_prepare".equals(event)) {
			forwardName = "after_UPDATE_RENEWAL";
		}
		if ("DEFER".equals(event) || "save_defer_prepare".equals(event) || "UPDATE_DEFERRED".equals(event)) {
			forwardName = "after_DEFER";
		}
		if ("RENEW".equals(event) || "save_renew_prepare".equals(event)) {
			forwardName = "after_RENEW";
		}
		if ("COMPLETE".equals(event) || "save_complete_prepare".equals(event)) {
			forwardName = "after_COMPLETE";
		}
		if ("UPDATE".equals(event) || "save_update_prepare".equals(event)) {
			forwardName = "after_UPDATE";
		}
		if ("ALLOW_PERM_UPLIFT".equals(event) || "save_perm_uplift_prepare".equals(event)
               || "PERM_UPLIFT".equals(event)) {
			forwardName = "after_ALLOW_PERM_UPLIFT";
		}
		if("VIEW_PENDING_PERM_UPLIFT".equals(event)|| "VIEW_PERM_UPLIFTED".equals(event)){
			forwardName = "after_VIEW_PENDING_PERM_UPLIFT";
		}
		if ("ALLOW_TEMP_UPLIFT".equals(event) || "save_temp_uplift_prepare".equals(event)
               || "TEMP_UPLIFT".equals(event)) {
			forwardName = "after_ALLOW_TEMP_UPLIFT";
		}
		if("VIEW_PENDING_TEMP_UPLIFT".equals(event)|| "VIEW_TEMP_UPLIFTED".equals(event)){
			forwardName = "after_VIEW_PENDING_TEMP_UPLIFT";
		}
		if ("ALLOW_RELODGE".equals(event) || "save_relodge_prepare".equals(event)
               || "LODGE".equals(event) || "UPDATE_LODGED".equals(event))
		{        
			forwardName = "after_ALLOW_RELODGE";
		}
		if("VIEW_LODGED".equals(event)|| "VIEW_PENDING_LODGE".equals(event) || "VIEW_PENDING_RELODGE".equals(event)){
			forwardName = "after_VIEW_RELODGE";
		}
		if ("DELETE".equals(event) || "save_delete_prepare".equals(event)) {
			forwardName = "after_DELETE";
		}
		if ("VIEW_COMPLETED".equals(event)) {
			forwardName = "after_VIEW_COMPLETED";
		}
		if ("VIEW_DEFERRED".equals(event) || "VIEW_DEFER_REQ_GENERATED".equals(event) || "VIEW_DEFER_REQ".equals(event)) {
			forwardName = "after_VIEW_DEFERRED";
		}
		if ("VIEW_DELETED".equals(event)) {
			forwardName = "after_VIEW_DELETED";
		}
		if ("VIEW_EXPIRED".equals(event)) {
			forwardName = "after_VIEW_EXPIRED";
		}
		if ("VIEW_PENDING_COMPLETE".equals(event)) {
			forwardName = "after_VIEW_PENDING_COMPLETE";
		}
		if ("VIEW_PENDING_UPDATE".equals(event)) {
			forwardName = "after_VIEW_PENDING_UPDATE";
		}
		if ("VIEW_PENDING_DEFER".equals(event)) {
			forwardName = "after_VIEW_PENDING_DEFER";
		}
		if ("VIEW_PENDING_DEFER_REQ".equals(event)) {
			forwardName = "after_VIEW_PENDING_DEFER_REQ";
		}
		if ("VIEW_PENDING_DELETE".equals(event)) {
			forwardName = "after_VIEW_PENDING_DELETE";
		}
		if ("VIEW_PENDING_RENEWAL".equals(event)) {
			forwardName = "after_VIEW_PENDING_RENEWAL";
		}
		if ("VIEW_PENDING_WAIVER".equals(event)) {
			forwardName = "after_VIEW_PENDING_WAIVER";
		}
		if ("VIEW_PENDING_WAIVER_REQ".equals(event)) {
			forwardName = "after_VIEW_PENDING_WAIVER_REQ";
		}
        if ("VIEW_PENDING_REDEEM".equals(event)) {
            forwardName = "after_VIEW_PENDING_REDEEM";
        }
		if("VIEW_AWAITING".equals(event)||"prepare_inactive".equals(event)){
			forwardName = "after_VIEW_AWAITING";
		}
		if ("VIEW_RECEIVED".equals(event)) {
			forwardName = "after_VIEW_RECEIVED";
		}
		if ("VIEW_REMINDED".equals(event)) {
			forwardName = "after_VIEW_REMINDED";
		}
		if ("VIEW_RENEWED".equals(event)) {
			forwardName = "after_VIEW_RENEWED";
		}
		if ("VIEW_WAIVED".equals(event) || "VIEW_WAIVER_REQ_GENERATED".equals(event) || "VIEW_WAIVER_REQ".equals(event)) {
			forwardName = "after_VIEW_WAIVED";
		}
        if ("VIEW_REDEEMED".equals(event)) {
			forwardName = "after_VIEW_REDEEMED";
		}
		if ("save".equals(event)) {
			forwardName = "after_save";
		}
		if ("submit".equals(event)) {
			forwardName = "after_submit";
		}
		if ("submiting".equals(event)) {
			forwardName = "after_submit";
		}
		if ("process".equals(event) || "refresh_comment".equals(event) || "chk_view_return".equals(event)
				||CHECKER_SEARCH_PROCESS_FACILITY_DOC.equals(event)||"search_facility_doc_checker".equals(event)) { // OFFICE
																												// Comment
			forwardName = "after_process";
		}
		if ("approve_checklist_item".equals(event)) {
			forwardName = "after_approve_checklist_item";
		}
		if ("reject_checklist_item".equals(event)) {
			forwardName = "after_reject_checklist_item";
		}
		// <+Begin OFFICE
		if ("forward_checklist_item".equals(event)) {
			forwardName = "after_forward_checklist_item";
		}
		// +Begin OFFICE>
		if ("cancel_checklist_item".equals(event) || "close_checklist_item".equals(event) || "to_track".equals(event)
				|| "track_refresh_comment".equals(event)||"search_facility_doc_checker_close".equals(event)
				||"search_facility_doc_maker_close".equals(event)) {// OFFICE
			forwardName = "after_close_checklist_item";
		}
		if ("close".equals(event)) {
			forwardName = "after_close";
		}
		if ("cancel".equals(event)) {
			forwardName = "after_close";
		}
		if ("view".equals(event) || "view_return".equals(event)||CHECKER_SEARCH_FACILITY_DOC.equals(event)||"checker_search_facility_doc".equals(event)
				||"view_return_to_track".equals(event)) {
			forwardName = "after_view";
		}
		if ("chk_view".equals(event)) {
			forwardName = "after_chk_view";
		}
		if ("view_receipt".equals(event)) {
			forwardName = "view_receipt";
		}
		if ("CAVEAT_PRINT_REMINDER".equals(event)) {
			forwardName = "after_print_caveat_reminder";
		}
		if ("VAL_PRINT_REMINDER".equals(event)) {
			forwardName = "after_print_valuation_reminder";
		}
		if ("INSURANCE_PRINT_REMINDER".equals(event)) {
			forwardName = "after_print_insurance_reminder";
		}
		if ("PREMINUM_RECEIPT_PRINT_REMINDER".equals(event)) {
			forwardName = "after_print_premium_receipt_reminder";
		}
		if ("view_image_page".equals(event)) {
			forwardName = "view_image_page";
		}
		if ("view_image_page_to_track".equals(event)) {
			forwardName = "view_image_page_to_track";
		}
		if ("view_image_page_to_do".equals(event)||"view_image_page_to_do_process".equals(event)) {
			forwardName = "view_image_page_to_do";
		}
		if ("retrieveImage".equals(event)) {
			forwardName = "imagePath";
		}
		if ("reject_checklist_item_error".equals(event)) {
			forwardName = "after_process";
		} else if ("downloadImage".equals(event)) {
			forwardName = "downloadImage";
		}
		else if (EVENT_RETRIEVE_INDVDL_IMAGE_FAC.equals(event)) {
			forwardName = "imagePath";
		} else if (EVENT_RETRIEVE_IMAGE_GALLARY_FAC.equals(event) ) {
			forwardName = "image_gallary";
		}else if ("retrieveIndImageTagUntagFacReceiptPath".equals(event) ) {
			forwardName = "image_gallary_fac_receipt_imgtaguntag";
		}else if ("retrieveImageTagUntagFacReceipt".equals(event) ) {
			forwardName = "image_gallary_fac_receipt_imgtaguntag";
		}else if ("print_image".equals(event)) {
			forwardName = "print_image";
		}else if ("view_image".equals(event)) {
			forwardName = "view_image";
		}else if ("DISCREPANCY".equals(event)  || "save_discrepancy_prepare".equals(event)) {
			forwardName = "after_DISCREPANCY";
		}else if ("imagetaguntaglist".equals(event)) {
			forwardName = "after_imagetaguntaglist";
		}
		else if ("facilityreceipt_image_tag_untag_submit".equals(event)) {
			//forwardName = "after_facilityreceipt_image_tag_untag_submit";
			forwardName = "after_RECEIVE";
		}else if(EVENT_PREPARE_TAG_UNTAG_IMAGE.equals(event) || EVENT_PREPARE_TAG_UNTAG_IMAGE_FROM_RETRIEVE.equals(event)) {
			forwardName = EVENT_PREPARE_TAG_UNTAG_IMAGE;
		}
		else if(EVENT_RETRIEVE_IMAGE_GALLERY_CHK_FACILITY.equals(event)) {
			forwardName = EVENT_RETRIEVE_IMAGE_GALLERY_CHK_FACILITY;
		}
		else if (EVENT_RETRIEVE_INDVDL_IMAGE_FACILITY.equals(event) || EVENT_RETRIEVE_MIG_INDVDL_IMAGE_FACILITY.equals(event) ||
				EVENT_RETRIEVE_INDIVIDUAL_IMAGE_CHK_FACILITY.equals(event)) {
		forwardName = "imagePath";
		
	} else if (EVENT_RETRIEVE_IMAGE_GALLARY_FACILITY.equals(event) || EVENT_RETRIEVE_MIG_IMAGE_GALLARY_FACILITY.equals(event)) {
		forwardName = "image_gallary";
	}else if ("print_image".equals(event)) {
		forwardName = "print_image";
	}else if ("view_image".equals(event)) {
		forwardName = "view_image";
	}
	else if(EVENT_PREPARE_TAG_UNTAG_IMAGE.equals(event) || EVENT_PREPARE_TAG_UNTAG_IMAGE_FROM_RETRIEVE.equals(event)) {
		forwardName = EVENT_PREPARE_TAG_UNTAG_IMAGE;
	}
	else if(EVENT_PREPARE_TAG_UNTAG_IMAGE_VIEW.equals(event) || EVENT_PREPARE_TAG_UNTAG_IMAGE_FROM_RETRIEVE_VIEW.equals(event)) {
		forwardName = EVENT_PREPARE_TAG_UNTAG_IMAGE_VIEW;
	}
	else if(EVENT_RETRIEVE_IMAGE_GALLERY_CHK_FACILITY_CHECKER_VIEW.equals(event)) {
		forwardName = EVENT_RETRIEVE_IMAGE_GALLERY_CHK_FACILITY_CHECKER_VIEW;
	}
		DefaultLogger.debug(this, "forwardName is  =============" + forwardName);
		return forwardName;
		
	}

	private String frameCheck(String forwardName) {
		return forwardName + "_WF";
	}
}