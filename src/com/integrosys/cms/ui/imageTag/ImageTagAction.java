package com.integrosys.cms.ui.imageTag;

/**
 *@author abhijit.rudrakshawar 
 *$ Action Controller for Image Tag
 */
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.IPin;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.DeleteListInsuranceCommand;

public class ImageTagAction extends CommonAction implements IPin, IImageTagConstants {
	public static final String EVENT_IMAGE_TAG = "image_tag";
	public static final String EVENT_LIST_IMAGE_TAG = "list_image";
	public static final String EVENT_LIST_TAG_PAGE = "list_tag_page";
	public static final String EVENT_PAGINATE = "paginate";
	public static final String EVENT_SAVE_TAG_PAGE = "save_image_tag";
	public static final String REFRESH_CHEKLIST = "refresh_cheklist";
	public static final String REFRESH_SUBTYPE = "refresh_subtype";
	public static final String REFRESH_SECURITY_ID = "refresh_security_id";
	public static final String CHECKER_PROCESS_CREATE = "checker_process_create";
	public static final String CHECKER_CONFIRM_CREATE = "checker_confirm_create";
	public static final String CHECKER_REJECT_CREATE = "checker_reject_create";
	public static final String MAKER_PREPARE_CLOSE = "maker_prepare_close";
	public static final String MAKER_PREPARE_RESUBMIT_DELETE = "maker_prepare_resubmit_delete";
	public static final String MAKER_CONFIRM_CLOSE = "maker_confirm_close";
	public static final String REJECTED_DELETE_READ = "rejected_delete_read";
	public static final String EVENT_REFRESH_DROPDOWNS = "refresh_dropdowns";
	public static final String MAKER_CONFIRM_RESUBMIT = "maker_confirm_resubmit";
	public static final String CHECKER_PROCESS_EDIT = "checker_process_edit";
	public static final String CHECKER_CONFIRM_EDIT = "checker_confirm_edit";

	public static final String EVENT_UNTAG_CUSTOMER_SEARCH = "untag_customer_search";
	public static final String EVENT_UNTAG_CUSTOMER_SEARCH_LIST = "untag_customer_search_list";
	
	public static final String EVENT_CREATE_PREPARE_UNTAG = "create_prepare_untag";
	public static final String EVENT_PREPARE_UNTAG_IMAGE_LIST = "prepare_untag_image_list";
	public static final String EVENT_CREATE_UNTAG = "create_image_untag";
	public static final String EVENT_RETRIEVE_IMAGE = "retrieveImage";
	public static final String EVENT_RETRIEVE_IMAGE_GALLARY = "retrieveImageGallary";
	public static final String EVENT_RETRIEVE_IMAGE_GALLARY_CHECK = "retrieveImageGallaryChecker";
	public static final String EVENT_RETRIEVE_IMAGE_GALLARY_MAKER = "retrieveImageGallaryMakerTodo";
	public static final String EVENT_RETRIEVE_IMAGE_GALLARY_CLOSE = "retrieveImageGallaryMakerClose";
	public static final String EVENT_RETRIEVE_IMAGE_GALLARY_TOTRACK = "retrieveImageGallaryMakerTotrack";
	
	public static final String EVENT_RETRIEVE_UNTAG_IMAGE_GALLARY = "retrieveUntagImageGallary";
	public static final String EVENT_RETRIEVE_VIEW_IMAGE_GALLARY_CHECK = "retrieveViewImageGallaryChecker";
	public static final String EVENT_RETRIEVE_INDVDL_IMAGE = "retrieveIndividualImage";
	public static final String EVENT_RETRIEVE_INDVDL_IMAGE_MAKER = "retrieveIndividualImageMakerTodo";
	public static final String EVENT_RETRIEVE_INDVDL_IMAGE_CLOSE = "retrieveIndividualImageMakerClose";
	public static final String EVENT_RETRIEVE_INDVDL_IMAGE_TOTRACK = "retrieveIndividualImageMakerTotrack";
	public static final String EVENT_RETRIEVE_INDVDL_IMAGE_CHECK = "retrieveIndividualImageChecker";
	
	public static final String EVENT_RETRIEVE_UNTAG_INDVDL_IMAGE = "retrieveUntagIndividualImage";
	public static final String EVENT_RETRIEVE_VIEW_INDVDL_IMAGE_CHECK = "retrieveViewIndividualImageChecker";
	public static final String EVENT_GET_FILTER_SUBFOLDER_NAME = "get_filter_subfolder_name";
	public static final String EVENT_GET_FILTER_DOCNAME = "get_filter_docname";
	
	public static final String EVENT_LIST_TAG_PAGE_VIEW = "list_tag_page_view";
	public static final String EVENT_PREPARE_TAG_VIEW_IMAGE_LIST = "prepare_tag_view_image_list";
	//public static final String RETRIVE_CAM_INFO = "retrive_cam_info";
	

	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public ICommand[] getCommandChain(String event) {
		String fromServer=PropertyManager.getValue("integrosys.server.identification","app1");
		DefaultLogger.debug(this, "====================================on server====================================================="+fromServer);
		ICommand objArray[] = null;
		DefaultLogger.debug(this, "******** Event: " + event);
		if (event.equals(EVENT_IMAGE_TAG) || event.equals(EVENT_UNTAG_CUSTOMER_SEARCH)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ImageTagViewCommand") };
		} 
		else if (EVENT_LIST_IMAGE_TAG.equals(event) || event.equals(EVENT_UNTAG_CUSTOMER_SEARCH_LIST)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ImageListCustomerCommand") };
		} else if (EVENT_LIST_TAG_PAGE.equals(event)||EVENT_PAGINATE.equals(event)) {
			DefaultLogger.debug(this, "getUploadImageList() Inside command----------3.3-------->" +DateUtil.getDate().getTime());
//			return new ICommand[] { (ICommand) getNameCommandMap().get("ImageTagResultCommand") };
			objArray = new ICommand[3];
			objArray[0] = (ICommand) getNameCommandMap().get("ImageTagResultCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("GetImageFiltersSubFolderCommand");
			objArray[2] = (ICommand) getNameCommandMap().get("GetImageFiltersDocNameCommand");
		}
		else if ("print_image".equals(event) || "view_image".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = (ICommand) getNameCommandMap().get("ImageTagResultPrintCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("GetImageFiltersSubFolderPrintCommand");
			objArray[2] = (ICommand) getNameCommandMap().get("GetImageFiltersDocNamePrintCommand");
		}
		else if (EVENT_SAVE_TAG_PAGE.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("CreateImageTagAddCommand") };
		} else if (REFRESH_SECURITY_ID.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("RefreshSecSubtypeCmd") };
		} else if (event.equals(CHECKER_PROCESS_CREATE) || event.equals(CHECKER_PROCESS_EDIT)
				|| event.equals(REJECTED_DELETE_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerReadImageTagCmd");
		} else if ((event != null) && event.equals(CHECKER_CONFIRM_CREATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveCreateImageTagCmd");
		} else if ((event != null) && event.equals(CHECKER_CONFIRM_EDIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveCreateImageTagCmd");
		} else if ((event.equals(CHECKER_REJECT_CREATE))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerRejectEditImageTagCmd");
		} else if (event.equals(MAKER_PREPARE_CLOSE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerReadImageTagCmd");
		} else if (event.equals(MAKER_PREPARE_RESUBMIT_DELETE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerPreapreResubmitImageTagCmd");
		} else if ((event != null) && event.equals(MAKER_CONFIRM_CLOSE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerCloseImageTagCmd");
		} else if (event.equals(REFRESH_CHEKLIST)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("RefreshChecklistItemsCmd");
		} else if (event.equals(EVENT_REFRESH_DROPDOWNS)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("RefreshImageTagDropdownsCmd");
		} else if (event.equals(MAKER_CONFIRM_RESUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("EditImageTagCommand");
		} else if (event.equals(EVENT_CREATE_PREPARE_UNTAG)|| EVENT_LIST_TAG_PAGE_VIEW.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CreatePrepareImageUntagCommand");
		} else if (event.equals(EVENT_PREPARE_UNTAG_IMAGE_LIST)||event.equals(EVENT_PREPARE_TAG_VIEW_IMAGE_LIST)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("PrepareUntagImageListCommand");
		} else if (event.equals(EVENT_CREATE_UNTAG)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CreateImageUntagCommand");
		} else if (event.equals(EVENT_RETRIEVE_IMAGE) || event.equals(EVENT_RETRIEVE_IMAGE_GALLARY) || event.equals(EVENT_RETRIEVE_INDVDL_IMAGE) ) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("RetriveImageCommand");
		} else if (event.equals(EVENT_RETRIEVE_IMAGE_GALLARY_MAKER) || event.equals(EVENT_RETRIEVE_INDVDL_IMAGE_MAKER) ) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("RetriveImageCommand");
		}else if (event.equals(EVENT_RETRIEVE_IMAGE_GALLARY_CLOSE) || event.equals(EVENT_RETRIEVE_INDVDL_IMAGE_CLOSE) ) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("RetriveImageCommand");
		}else if (event.equals(EVENT_RETRIEVE_IMAGE_GALLARY_TOTRACK) || event.equals(EVENT_RETRIEVE_INDVDL_IMAGE_TOTRACK) ) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("RetriveImageCommand");
		}else if (event.equals(EVENT_RETRIEVE_VIEW_IMAGE_GALLARY_CHECK) || event.equals(EVENT_RETRIEVE_VIEW_INDVDL_IMAGE_CHECK) ) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("RetriveImageViewCheckerCommand");
		} else if (event.equals(EVENT_RETRIEVE_IMAGE_GALLARY_CHECK) || event.equals(EVENT_RETRIEVE_INDVDL_IMAGE_CHECK) ) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("RetriveImageCommand");
		} else if ( event.equals(EVENT_RETRIEVE_UNTAG_INDVDL_IMAGE)|| event.equals(EVENT_RETRIEVE_UNTAG_IMAGE_GALLARY)) {
			objArray = new ICommand[1];
			//objArray[0] = (ICommand) getNameCommandMap().get("PrepareUntagImageListCommand");
			objArray[0] = (ICommand) getNameCommandMap().get("RetriveUntagImageCommand");
		} else if (event.equals(EVENT_GET_FILTER_SUBFOLDER_NAME)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("GetImageFiltersSubFolderCommand");
		} else if (event.equals(EVENT_GET_FILTER_DOCNAME)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("GetImageFiltersDocNameCommand");
		} else if (REFRESH_SUBTYPE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshSecSubtypeValuesCmd();
		}else if (event.equals("downloadImage")) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("DownloadImageCommand");
		}else if ("download_image_zip".equals(event) ) {
			objArray = new ICommand[3];
			objArray[0] = new DownloadImageCommand();
			objArray[1] = (ICommand) getNameCommandMap().get("GetImageFiltersSubFolderPrintCommand");
			objArray[2] = (ICommand) getNameCommandMap().get("GetImageFiltersDocNamePrintCommand");
		}
		else if (event.equals("refresh_status_date")) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("RefreshImageStatusDateCmd");
		}
		else if (event.equals("retrive_cam_info")) {
			objArray = new ICommand[1];
			//objArray[0] = (ICommand) getNameCommandMap().get("RetriveCamCmd");
			objArray[0] = new RetriveCamCmd();
		}
		
		//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
		else if (event.equals("refresh_recc_doc_status_date")) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshRecurrentDocStatusDateCmd();
		}
		//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II
		return (objArray);
	}

	public ActionErrors validateInput(ActionForm form, Locale locale) {
		DefaultLogger.debug(this, "Inside validate Input child class");
		return ImageTagValidator.validateInput(form, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals(EVENT_LIST_IMAGE_TAG)||event.equals(EVENT_UNTAG_CUSTOMER_SEARCH_LIST )
				|| EVENT_SAVE_TAG_PAGE.equals(event)||EVENT_PREPARE_UNTAG_IMAGE_LIST.equals(event)) {
			DefaultLogger.debug(this, "Inside validate.");
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if (event.equals(EVENT_LIST_IMAGE_TAG)) {
			errorEvent = EVENT_IMAGE_TAG;
		} else if (EVENT_SAVE_TAG_PAGE.equals(event)) {
			errorEvent = EVENT_LIST_TAG_PAGE;
		} else if (EVENT_UNTAG_CUSTOMER_SEARCH_LIST .equals(event)) {
			errorEvent = EVENT_UNTAG_CUSTOMER_SEARCH;
		} else if (EVENT_PREPARE_UNTAG_IMAGE_LIST.equals(event)) {
			errorEvent = EVENT_CREATE_PREPARE_UNTAG;
		}else if(CHECKER_REJECT_CREATE.equals(event)){
			errorEvent = CHECKER_PROCESS_CREATE;
		}
		return errorEvent;
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		String docType = (String) resultMap.get("docTypeCode");
		String nextPage = "";
		DefaultLogger.debug(this, "@@@@@@@@@@@@@@@@@@@@@@@@@@" + docType);
		
		if (SECURITY_DOC.equals(docType)) {
			nextPage = "security_type_dropdown";
		} else if (FACILITY_DOC.equals(docType)) {
			nextPage = "facility_dropdown";
		} else if (CAM_DOC.equals(docType)
				||RECURRENTDOC_DOC.equals(docType)
				||OTHER_DOC.equals(docType)||LAD_DOC.equals(docType)) {
			nextPage = REFRESH_CHEKLIST;
		} else {
			nextPage = getReference(event);
		}

		Page aPage = new Page();
		if ((resultMap.get("wip") != null)
				&& (resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference(getReference("work_in_process"));
		}else {
			aPage.setPageReference(nextPage);
		}
		return aPage;
	}

	private String getReference(String event) {
		String forwardName = "submit_fail";
		if (EVENT_IMAGE_TAG.equals(event)) {
			forwardName = EVENT_IMAGE_TAG;
		} else if (EVENT_LIST_IMAGE_TAG.equals(event)) {
			forwardName = "after_list_image";
		}else if (EVENT_UNTAG_CUSTOMER_SEARCH.equals(event)) {
			forwardName = EVENT_UNTAG_CUSTOMER_SEARCH;
		} else if (EVENT_UNTAG_CUSTOMER_SEARCH_LIST.equals(event)) {
			forwardName = EVENT_UNTAG_CUSTOMER_SEARCH_LIST;
		} else if (EVENT_LIST_TAG_PAGE.equals(event) || EVENT_PAGINATE.equals(event)) {
			forwardName = EVENT_LIST_TAG_PAGE;
		}else if ("print_image".equals(event)) {
			forwardName = "print_image";
		}else if ("view_image".equals(event)) {
			forwardName = "view_image";
		}else if (EVENT_SAVE_TAG_PAGE.equals(event) || CHECKER_CONFIRM_CREATE.equals(event)
				|| CHECKER_CONFIRM_EDIT.equals(event)) {
			forwardName = EVENT_SAVE_TAG_PAGE;
		}else if (REFRESH_SECURITY_ID.equals(event)) {
			forwardName = REFRESH_SECURITY_ID;
		}else if (event.equals(CHECKER_PROCESS_CREATE) || event.equals(CHECKER_PROCESS_EDIT)) {
			forwardName = "checker_image_tag_page";
		}else if (event.equals("rejected_delete_read")) {
			forwardName = "totrack_image_tag_page";
		}else if (event.equals("checker_reject_create")) {
			forwardName = "common_reject_page";
		} else if ((event != null) && event.equals("maker_prepare_close")) {
			forwardName = "maker_prepare_close";
		} else if ((event != null) && event.equals("maker_prepare_resubmit_delete")) {
			forwardName = "maker_prepare_resubmit_delete";
		} else if ((event != null) && event.equals(MAKER_CONFIRM_CLOSE)) {
			forwardName = "common_close_page";
		} else if (REFRESH_CHEKLIST.equals(event)) {
			forwardName = REFRESH_CHEKLIST;
		} else if (EVENT_REFRESH_DROPDOWNS.equals(event)) {
			forwardName = EVENT_REFRESH_DROPDOWNS;
		} else if (MAKER_CONFIRM_RESUBMIT.equals(event)) {
			forwardName = MAKER_CONFIRM_RESUBMIT;
		} else if (EVENT_CREATE_PREPARE_UNTAG.equals(event)) {
			forwardName = EVENT_CREATE_PREPARE_UNTAG;
		} else if (EVENT_PREPARE_UNTAG_IMAGE_LIST.equals(event)) {
			forwardName = EVENT_PREPARE_UNTAG_IMAGE_LIST;
		} else if (EVENT_CREATE_UNTAG.equals(event)) {
			forwardName = EVENT_CREATE_UNTAG; 
		} else if (EVENT_GET_FILTER_DOCNAME.equals(event)) {
			forwardName = EVENT_GET_FILTER_DOCNAME;
		} else if (EVENT_GET_FILTER_SUBFOLDER_NAME.equals(event)) {
			forwardName = EVENT_GET_FILTER_SUBFOLDER_NAME;
		} else if (EVENT_LIST_TAG_PAGE_VIEW.equals(event)) {
			forwardName = EVENT_LIST_TAG_PAGE_VIEW;
		} else if (EVENT_PREPARE_TAG_VIEW_IMAGE_LIST.equals(event)) {
			forwardName = EVENT_PREPARE_TAG_VIEW_IMAGE_LIST;			
		} else if (EVENT_RETRIEVE_INDVDL_IMAGE.equals(event) || 
				EVENT_RETRIEVE_INDVDL_IMAGE_MAKER.equals(event) || 
				EVENT_RETRIEVE_INDVDL_IMAGE_CLOSE.equals(event) || 
				EVENT_RETRIEVE_INDVDL_IMAGE_TOTRACK.equals(event) || 
				event.equals(EVENT_RETRIEVE_UNTAG_INDVDL_IMAGE) ||
				EVENT_RETRIEVE_VIEW_INDVDL_IMAGE_CHECK.equals(event) ||
				EVENT_RETRIEVE_INDVDL_IMAGE_CHECK.equals(event)) {
			forwardName = "imagePath";
		} else if (EVENT_RETRIEVE_IMAGE_GALLARY.equals(event) || 
				EVENT_RETRIEVE_IMAGE_GALLARY_MAKER.equals(event) || 
				EVENT_RETRIEVE_IMAGE_GALLARY_CLOSE.equals(event) || 
				EVENT_RETRIEVE_IMAGE_GALLARY_TOTRACK.equals(event) || 
				EVENT_RETRIEVE_IMAGE_GALLARY_CHECK.equals(event) || 
				event.equals(EVENT_RETRIEVE_UNTAG_IMAGE_GALLARY) ||
				EVENT_RETRIEVE_VIEW_IMAGE_GALLARY_CHECK.equals(event) ) {
			forwardName = "image_gallary";
		} else if ((event != null) && event.equals("work_in_process")) {
			forwardName = "work_in_process_page";
		} else if ( REFRESH_SUBTYPE.equals(event)) {
			return "refresh_subtype";
		} else if ("downloadImage".equals(event)||"download_image_zip".equals(event)) {
			forwardName = "downloadImage";
		}else if ( "refresh_status_date".equals(event)) {
			return "refresh_status_date";
		}else if ( "retrive_cam_info".equals(event)) {
			return "retrive_cam_info";
		}
		//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
		else if ( "refresh_recc_doc_status_date".equals(event)) {
			return "refresh_recc_doc_status_date";
		}
		//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II
		
		return forwardName;
	}

}