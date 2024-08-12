package com.integrosys.cms.ui.image;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.IPin;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.checklist.camreceipt.ImageTagResultPrintCommand;
import com.integrosys.cms.ui.manualinput.limit.EventConstant;
/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Govind S$<br>
 * @version $Revision 0$
 * @since $Date:2011/03/03 time :4:20:00$ Tag: $Name$
 */
public class ImageUploadAction extends CommonAction implements IPin {
	public static final String EVENT_IMAGE_UPLOAD = "image_upload";
	public static final String EVENT_LIST_IMAGE_UPLOAD = "list_image";
	public static final String EVENT_LIST_UPLOAD_PAGE = "list_upload_page";
	public static final String EVENT_SAVE_UPLOAD_PAGE = "save_image_upload";
	public static final String EVENT_GET_DOC_LINKAGE = "get_doc_linkage";
	public static final String EVENT_SUBMIT = "maker_prepare_submit";
	public static final String CHECKER_REJECT_EDIT="checker_reject_edit";
	public static final String CHECKER_REJECT_ADD="checker_reject_add";
	public static final String MAKER_CONFIRM_CLOSE="maker_confirm_close";
	public static final String CHECKER_VIEW_IMAGE_UPLOAD="checker_view_image_upload";
	public static final String MAKER_VIEW_IMAGE_UPLOAD="maker_view_image_upload";
	public static final String CHECKER_CONFIRM_APPROVE_CREATE="checker_confirm_approve_create";
	public static final String REJECTED_DELETE_READ = "rejected_delete_read";
	public static final String APPROVE_READ = "approve_read";
	public static final String MAKER_CONFIRM_SUBMIT="maker_confirm_submit";
	
	public static final String EVENT_RETRIEVE_VIEW_IMAGE_GALLARY = "retrieveViewImageGallary";
	public static final String EVENT_RETRIEVE_VIEW_INDVDL_IMAGE = "retrieveViewIndividualImage";
	//Added By Anil
	public static final String EVENT_SAVE_IMAGE_DETAILS = "save_image_details";
	public static final String EVENT_VIEW_UPLOADED_IMAGE_LISTING = "view_uploaded_image_listing";
	public static final String EVENT_REMOVE_UPLOADED_IMAGE_LISTING = "remove_uploaded_image_listing";
	public static final String EVENT_REMOVE_IMAGES = "remove_images";
	public static final String ERROR_EVENT_SAVE_UPLOAD_PAGE = "error_save_image_upload";
	
	public static final String EVENT_GET_FILTER_SUBFOLDER_NAME = "get_filter_subfolder_name";
	public static final String EVENT_GET_FILTER_DOCNAME = "get_filter_docname";
	public static final String EVENT_REFRESH_FACILITY_DOCUMENT_NAME = "refresh_facility_document_name";
	public static final String EVENT_REFRESH_SECURITY_DOCUMENT_NAME = "refresh_security_document_name";
	public static final String EVENT_CANCLEFILTER = "cancleFilter";
	public static final String EVENT_ADD_DOC_LINKAGE = "add_doc_linkage";
	public static final String EVENT_REMOVE_DOC_LINKAGE = "remove_doc_linkage";
	public static final String EVENT_SUBMIT_DOC_LINKAGE = "submit_doc_linkage";
	public static final String FWD_DOC_LINKAGE_DETAILS = "doc_linkage_details";
	public static final String ERROR_EVENT_ADD_DOC_LINKAGE = "error_add_doc_linkage";
	public static final String EVENT_REFRESH_OTHER_DOC = "refresh_other_doc";
	
	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		DefaultLogger.debug(this, "******** Event: " + event);
		if (EVENT_IMAGE_UPLOAD.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ImageUserTypeCommand") };
		}else if (EVENT_LIST_IMAGE_UPLOAD.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ImageListCustomerCommand") };
		}else if (EVENT_LIST_UPLOAD_PAGE.equals(event)) {
//			return new ICommand[] { (ICommand) getNameCommandMap().get("ImageUploadResultCommand") };
			return new ICommand[] { (ICommand) getNameCommandMap().get("CreatePreapareImageUploadDetailsCommand") };
		}else if (EVENT_SAVE_UPLOAD_PAGE.equals(event) || EVENT_GET_DOC_LINKAGE.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("CreateImageUploadAddCommand"),
					(ICommand) getNameCommandMap().get("GetDocLinkageDetailsCmd"),
					(ICommand) getNameCommandMap().get("DocLinkageDetailsDDLCmd")};
		}else if(EventConstant.EVENT_TRACK.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("CreateImageUploadAddCommand") };
		}else if ("maker_prepare_submit".equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("MakerUpdateImageUploadCommand") };
		}else if ("checker_add_read".equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("CheckerReadImageUploadCmd") };
		}else if ((event != null) && event.equals("checker_approve_add")) {
			objArray = new ICommand[1];
			return new ICommand[] { (ICommand) getNameCommandMap().get("CheckerApproveEditImageUploadCmd") };
			//objArray[0] = new CheckerApproveAddImageUploadCmd();
		}//else if ((event != null) && event.equals(CHECKER_REJECT_EDIT)) {
			else if ((event != null) && event.equals(CHECKER_REJECT_ADD)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerRejectEditImageUploadCmd");
		}else if (event.equals(MAKER_CONFIRM_CLOSE)) {
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("MakerCloseImageUploadCmd");
        }else if ((event.equals(CHECKER_VIEW_IMAGE_UPLOAD))|| event.equals(MAKER_VIEW_IMAGE_UPLOAD)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerReadImageUploadCmd");
		}else if (event.equals(CHECKER_CONFIRM_APPROVE_CREATE)) {
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveCreateImageUploadCmd");
        }else if ( event.equals(REJECTED_DELETE_READ)||event.equals(APPROVE_READ)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerReadImageUploadCmd");
		}
//        else if ( event.equals(APPROVE_READ)){
//			objArray = new ICommand[1];
//			objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveReadImageUploadCmd");
//		}
        else if ( event.equals(MAKER_CONFIRM_SUBMIT)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerCreateImageUploadDetailsCmd");
		}
		//Added by Anil
        else if ( event.equals(EVENT_SAVE_IMAGE_DETAILS)|| event.equals(ERROR_EVENT_SAVE_UPLOAD_PAGE)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ManageImageUploadDetailsCmd");
		}
        else if (event.equals(EVENT_VIEW_UPLOADED_IMAGE_LISTING)||event.equals(EVENT_REMOVE_UPLOADED_IMAGE_LISTING)||event.equals("view_uploaded_image_listing_search") || "view_uploaded_image_listing_search_page".equals(event)){
        	
        	//for image listing
        	objArray = new ICommand[3];
			objArray[0] = (ICommand) getNameCommandMap().get("CreateImageUploadAddCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("GetImageFiltersSubFolderCommand");
			objArray[2] = (ICommand) getNameCommandMap().get("GetImageFiltersDocNameCommand");
        }
        else if (event.equals(EVENT_RETRIEVE_VIEW_IMAGE_GALLARY)|| event.equals(EVENT_RETRIEVE_VIEW_INDVDL_IMAGE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("RetriveImageCommand");
		}
		else if ( event.equals(EVENT_REMOVE_IMAGES)){
        	objArray = new ICommand[0];
        	return new ICommand[] { (ICommand) getNameCommandMap().get("RemoveUploadedImagesCmd") };
        }else if ( event.equals("paginate")){
        	objArray = new ICommand[0];
        	return new ICommand[] { (ICommand) getNameCommandMap().get("PaginateImageListCommand") };
        }else if (event.equals(EVENT_GET_FILTER_SUBFOLDER_NAME)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("GetImageFiltersSubFolderCommand");
		} else if (event.equals(EVENT_GET_FILTER_DOCNAME)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("GetImageFiltersDocNameCommand");
		} else if (event.equals("downloadImage")) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("DownloadImageCommand");
		}else if ("download_image_zip".equals(event) ) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("DownloadImageCommand");


		}else if ("print_image".equals(event) || "view_image".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ImageTagResultPrintCommand();
			
		}else if (event.equals(EVENT_REFRESH_FACILITY_DOCUMENT_NAME) || event.equals(EVENT_REFRESH_SECURITY_DOCUMENT_NAME)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("GetImageFiltersDocNameCommand");
		}else if ((event.equals("list"))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ListCustomerCommand");
		}else if ((event.equals("submit"))) {
			objArray = new ICommand[1];
			//objArray[0] = (ICommand) getNameCommandMap().get("SubmitCustomerCommand");
			objArray[0] = new SubmitCustomerCommand();
		}else if (event.equals(EVENT_CANCLEFILTER)) {
			objArray = new ICommand[1];
			objArray[0] = new CancleFilterCmd();
		}else if(event.equals(EVENT_ADD_DOC_LINKAGE)) {
			objArray = new ICommand[3];
			objArray[0] = (ICommand) getNameCommandMap().get("ManageImageUploadDetailsCmd");
			objArray[1] = (ICommand) getNameCommandMap().get("AddDocLinkageDetailsToSessionCmd");
			objArray[2] = (ICommand) getNameCommandMap().get("DocLinkageDetailsDDLCmd");
		}else if(event.equals(EVENT_REMOVE_DOC_LINKAGE)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("RemoveDocLinkageDetailsToSessionCmd");
			objArray[1] = (ICommand) getNameCommandMap().get("DocLinkageDetailsDDLCmd");
		}else if(event.equals(EVENT_SUBMIT_DOC_LINKAGE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("SubmitDocLinkageDetailsCmd");
		}else if(event.equals(EVENT_REFRESH_OTHER_DOC)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("RefreshOtherDocumentCmd");
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
		return ImageUploadValidator.validateInput((CommonForm)aForm);
	}

	/**
	 * This method is used to determine Validation Required or not
	 * 
	 * @param event is of type String
	 * @return boolean
	 */
	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals(EVENT_LIST_IMAGE_UPLOAD)||event.equals("list")) {
			result = true;
		}
		else if (event.equals(EVENT_ADD_DOC_LINKAGE)) {
			result = true;
		}
		return result;
	}


	/**
	 * This method is used to determine Error Event
	 * 
	 * @param event is of type String
	 * @return String
	 */
	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if (event.equals(EVENT_LIST_IMAGE_UPLOAD)) {
			errorEvent = EVENT_IMAGE_UPLOAD;
		}else if (event.equals(EVENT_SAVE_IMAGE_DETAILS)) {
			errorEvent = EVENT_LIST_UPLOAD_PAGE;
		}
		else if (event.equals(EVENT_SAVE_UPLOAD_PAGE)) {
			errorEvent = ERROR_EVENT_SAVE_UPLOAD_PAGE;
		}
		else if (event.equals(EVENT_ADD_DOC_LINKAGE)) {
			errorEvent = ERROR_EVENT_ADD_DOC_LINKAGE;
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
	public IPage getNextPage(String event, HashMap resultMap,
			HashMap exceptionMap) {
		Page aPage = new Page();
		String nextPage = getReference(event);
		aPage.setPageReference(nextPage);
		return aPage;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = event;
		if (EVENT_IMAGE_UPLOAD.equals(event)) {
			forwardName = EVENT_IMAGE_UPLOAD;
		}
		else if (EVENT_LIST_IMAGE_UPLOAD.equals(event)) {
			forwardName = "after_list_image";
		}
		else if (EVENT_LIST_UPLOAD_PAGE.equals(event)) {
			forwardName = EVENT_LIST_UPLOAD_PAGE;
		}
		else if (EVENT_SAVE_UPLOAD_PAGE.equals(event) || EVENT_GET_DOC_LINKAGE.equals(event)
				|| EVENT_ADD_DOC_LINKAGE.equals(event) || EVENT_REMOVE_DOC_LINKAGE.equals(event) 
				|| ERROR_EVENT_ADD_DOC_LINKAGE.equals(event)) {
			forwardName = FWD_DOC_LINKAGE_DETAILS;
		}
		else if ("paginate".equals(event)) {
			forwardName = EVENT_SAVE_UPLOAD_PAGE;
		}
		else if (EVENT_SUBMIT.equals(event)||event.equals("maker_confirm_submit")||event.equals("checker_confirm_approve_create")) {
			forwardName = EVENT_SUBMIT;
		}
		else if (EventConstant.EVENT_TRACK.equals(event)) {
			forwardName = "close_page";
		}
		else if ("checker_add_read".equals(event)) {
			forwardName = "checker_add_read";
		}else if ( event.equals("checker_approve_add")) {
			forwardName = "common_approve_page";
		}else if ((event != null) && event.equals(CHECKER_REJECT_ADD)) {
			forwardName = "checker_reject_add";
		}
		else if ((event != null) && event.equals(CHECKER_REJECT_EDIT)) {
			forwardName = "checker_reject_edit";
		}else if ((event != null) && event.equals(MAKER_CONFIRM_CLOSE)) {
			forwardName = "maker_confirm_close";
		}else if ((event != null) && event.equals(MAKER_VIEW_IMAGE_UPLOAD)) {
			forwardName = "maker_view_delete_page";
		}else if ((event != null) && event.equals(CHECKER_VIEW_IMAGE_UPLOAD)) {
			forwardName = "checker_view_delete_page";
		}else if (event.equals(CHECKER_CONFIRM_APPROVE_CREATE)) {
	            return "CHECKER_CONFIRM_APPROVE_CREATE";
	    } else if ((event != null) && event.equals("rejected_delete_read")||event.equals(APPROVE_READ)) {
			forwardName = "maker_view_todo_page";
		}else if (EVENT_RETRIEVE_VIEW_INDVDL_IMAGE.equals(event)) {
			forwardName = "imagePath";
		} else if (EVENT_RETRIEVE_VIEW_IMAGE_GALLARY.equals(event) ) {
			forwardName = "image_gallary";
		}
	    else if (event.equals(ERROR_EVENT_SAVE_UPLOAD_PAGE)) {
	    	forwardName = EVENT_SAVE_IMAGE_DETAILS;
	    } else if (EVENT_GET_FILTER_DOCNAME.equals(event)) {
			forwardName = EVENT_GET_FILTER_DOCNAME;
		} else if (EVENT_GET_FILTER_SUBFOLDER_NAME.equals(event)) {
			forwardName = EVENT_GET_FILTER_SUBFOLDER_NAME;
		} else if ("downloadImage".equals(event)) {
			forwardName = "downloadImage";
		}else if ("print_image".equals(event)) {
			forwardName = "print_image";
		}else if ("view_uploaded_image_listing_search".equals(event) || "view_uploaded_image_listing_search_page".equals(event)) {
			forwardName = "view_uploaded_image_listing";
		}else if (EVENT_REFRESH_FACILITY_DOCUMENT_NAME.equals(event)) {
			forwardName = EVENT_REFRESH_FACILITY_DOCUMENT_NAME;
		}else if (EVENT_REFRESH_SECURITY_DOCUMENT_NAME.equals(event)) {
			forwardName = EVENT_REFRESH_SECURITY_DOCUMENT_NAME;
		}else if (EVENT_CANCLEFILTER.equals(event)) {
			forwardName = EVENT_CANCLEFILTER;
		}
		
		return forwardName;
	}

}